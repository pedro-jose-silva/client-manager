package pt.multicert.clientmanager.service;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.multicert.clientmanager.client.ClientListType;
import pt.multicert.clientmanager.client.ClientType;
import pt.multicert.clientmanager.model.Client;

@Service("clientService")
public class ClientServiceImpl implements IClientService{
	

    @Autowired
    private SessionFactory sessionFactory;
 
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Transactional
	@Override
	public Response addClient(ClientType clientType) {
		
		Client newClient = new Client(clientType.getNif(), clientType.getName(), 
				clientType.getAddress(), clientType.getPhone());
		
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            
            transaction = session.beginTransaction();
            
            session.save(newClient);
            
            transaction.commit();
            
        } catch (ConstraintViolationException cve) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Response.serverError().entity("Client information cannot be empty.").build();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Response.serverError().entity(e.getMessage()).build();
        }
		
		return Response.ok().build();
	}
    
    @Transactional
	@Override
	public ClientType getClient(int clientNif) {
    	ClientType clientType = new ClientType();
		
    	try(Session session = getSessionFactory().openSession()) {
    		
    		CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = builder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            criteriaQuery.select(root).where(builder.equal(root.get("nif"), clientNif));
            Query query = session.createQuery(criteriaQuery);
            Client client = (Client) query.getSingleResult();
    		
            clientType.setClientId(client.getClientId());
        	clientType.setNif(client.getNif());
        	clientType.setName(client.getName());
        	clientType.setAddress(client.getAddress());
        	clientType.setPhone(client.getPhone());
    		
    		return clientType;
    		
    	} catch (Exception e) {}

        return clientType;
	}

    @Transactional
	@Override
	public Response updateClient(ClientType clientType) {
    	
    	Client client = new Client(clientType.getClientId(), clientType.getNif(), clientType.getName(), 
    			clientType.getAddress(), clientType.getPhone());
		
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            
            transaction = session.beginTransaction();
            
            session.update(client);
            
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Response.serverError().entity(e.getMessage()).build();
        }
		
		return Response.ok().build();
	}

    @Transactional
	@Override
	public Response deleteClient(ClientType clientType) {
    	
    	Client client = new Client(clientType.getClientId(), clientType.getNif(), clientType.getName(), 
    			clientType.getAddress(), clientType.getPhone());
		
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            
            transaction = session.beginTransaction();
            
            session.delete(client);
            
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Response.serverError().entity(e.getMessage()).build();
        }

        return Response.ok().build();
	}

    @Transactional
	@Override
	public ClientListType getAllClients() {
    	
    	ClientListType clientListType = new ClientListType();
		
    	try(Session session = getSessionFactory().openSession()) {
    		
    		List<Client> clientList = session.createQuery("from Client", Client.class).list();
    		
    		for(Client client : clientList) {
            	ClientType clientType = new ClientType();
            	clientType.setClientId(client.getClientId());
            	clientType.setNif(client.getNif());
            	clientType.setName(client.getName());
            	clientType.setAddress(client.getAddress());
            	clientType.setPhone(client.getPhone());
                clientListType.getClientType().add(clientType);
            }
    		
    		return clientListType;
    		
    	} catch (Exception e) {System.out.println(e);}

        return clientListType;
	}

    @Transactional
	@Override
	public ClientListType getAllClientsWithName(String clientName) {
    	
    	ClientListType clientListType = new ClientListType();
		
    	try(Session session = getSessionFactory().openSession()) {
    		
    		CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = builder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            criteriaQuery.select(root).where(builder.like(root.get("name"), "%"+clientName+"%"));
            Query query = session.createQuery(criteriaQuery);
            List<Client> clientList = query.getResultList();
            
    		for(Client client : clientList) {
            	ClientType clientType = new ClientType();
            	clientType.setClientId(client.getClientId());
            	clientType.setNif(client.getNif());
            	clientType.setName(client.getName());
            	clientType.setAddress(client.getAddress());
            	clientType.setPhone(client.getPhone());
                clientListType.getClientType().add(clientType);
            }
    		
    		return clientListType;
    		
    	} catch (Exception e) {}

        return clientListType;
	}

}
