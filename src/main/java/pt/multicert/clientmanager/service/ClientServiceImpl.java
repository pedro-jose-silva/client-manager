package pt.multicert.clientmanager.service;

import java.util.List;

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
 
    //@Transactional
	@Override
	public Response addClient(ClientType clientType) {
		
		Client newClient = new Client();
		newClient.setNif(clientType.getNif());
		newClient.setName(clientType.getName());
		newClient.setAddress(clientType.getAddress());
		newClient.setPhone(clientType.getPhone());
		
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            
            transaction = session.beginTransaction();
            
            session.save(newClient);
            
            transaction.commit();
            
            session.close();
//        } 
//        catch (ConstraintViolationException cve) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            return Response.serverError().entity("Client information cannot be empty.").build();
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
		// TODO Auto-generated method stub
		return null;
	}

    @Transactional
	@Override
	public String updateClient(ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}

    @Transactional
	@Override
	public String deleteClient(ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}

    @Transactional
	@Override
	public ClientListType getAllClients() {
    	
    	ClientListType clientListType = new ClientListType();
		
        //List<Client> clientList = sessionFactory.getCurrentSession().createCriteria(Client.class).list();
    	System.out.println("Im here...");
    	try(Session session = getSessionFactory().openSession()) {
    		List<Client> clientList = session.createQuery("from client", Client.class).list();
    		System.out.println(clientList);
    		for(Client client : clientList) {
            	ClientType clientType = new ClientType();
            	clientType.setNif(client.getNif());
            	clientType.setName(client.getName());
            	clientType.setAddress(client.getAddress());
            	clientType.setPhone(client.getPhone());
                clientListType.getClientType().add(clientType);
            }
    		
    		session.close();
    		
    		return clientListType;
    		
    	} catch (Exception e) {}

        return clientListType;
	}

    @Transactional
	@Override
	public ClientListType getAllClientsWithName(String clientName) {
		// TODO Auto-generated method stub
		return null;
	}

}
