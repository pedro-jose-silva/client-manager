package pt.multicert.clientmanager.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.multicert.clientmanager.client.ClientListType;
import pt.multicert.clientmanager.client.ClientType;

@Path("/clientService")
public interface IClientService {
	
	@POST
    @Path("addClient")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
    public Response addClient(ClientType clientType);
 
    @GET
    @Path("getClient/{nif}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ClientType getClient(@PathParam("nif") int clientNif);
 
    @PUT
    @Path("updateClient")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
    public Response updateClient(ClientType clientType);
 
    @DELETE
    @Path("deleteClient")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,})
    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
    public Response deleteClient(ClientType clientType);
 
    @GET
    @Path("getAllClients")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ClientListType getAllClients();
    
    @GET
    @Path("getAllClientsWithName/{name}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ClientListType getAllClientsWithName(@PathParam("name") String clientName);
}
