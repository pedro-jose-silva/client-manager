package pt.multicert.clientmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import pt.multicert.clientmanager.client.ClientListType;
import pt.multicert.clientmanager.client.ClientType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-context.xml"})
public class ClientServiceTest {
	
	private static String addClientUrl;
	private static String getClientUrl;
	private static String deleteClientUrl;
	private static RestTemplate restTemplate;
	private static HttpHeaders headers;
	private static JSONObject clientJsonObject;
	
	@BeforeClass
	public static void runBeforeAllTestMethods() {
		addClientUrl = "http://localhost:8080/client-manager/clientService/addClient";
		getClientUrl = "http://localhost:8080/client-manager/clientService/getClient";
		deleteClientUrl = "http://localhost:8080/client-manager/clientService/deleteClient";
		
	    restTemplate = new RestTemplate();
	    headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
	    
	    clientJsonObject = new JSONObject();
	    clientJsonObject.put("nif", "236079727");
	    clientJsonObject.put("name", "John Doe");
	    clientJsonObject.put("address", "Main Street, N1, SimCity");
	    clientJsonObject.put("phone", "912345678");
	}

	
	@Test
	public void testRestAddClient() {
		
		HttpEntity<String> request = new HttpEntity<String>(clientJsonObject.toString(), headers);
		ResponseEntity<Response> response = restTemplate.postForEntity(addClientUrl, request, Response.class);
		
		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void testRestGetClient() {
		
		ResponseEntity<ClientType> response = restTemplate.getForEntity(getClientUrl+"/236079727", ClientType.class);
		
		assertNotNull(response);
		ClientType client = response.getBody();
		assertNotNull(client);
		assertEquals(client.getNif(), clientJsonObject.get("nif"));
		assertEquals(client.getName(), clientJsonObject.get("name"));
		assertEquals(client.getAddress(), clientJsonObject.get("address"));
		assertEquals(client.getPhone(), clientJsonObject.get("phone"));
	}
	
	@Test
	public void testRestDeleteClient() {
		
		ResponseEntity<ClientType> responseGet = restTemplate.getForEntity(getClientUrl+"/236079727", ClientType.class);
		clientJsonObject.put("clientId", responseGet.getBody().getClientId());
		
		HttpEntity<String> request = new HttpEntity<String>(clientJsonObject.toString(), headers);
		ResponseEntity<Response> response  = restTemplate.exchange(deleteClientUrl, HttpMethod.DELETE, request, Response.class);

		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
