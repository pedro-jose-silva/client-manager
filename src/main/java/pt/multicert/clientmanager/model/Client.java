package pt.multicert.clientmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT")
public class Client {

	@Id
    @GeneratedValue
    @Column(name = "CLIENT_ID")
    private int clientId;
	
	@Column(name = "NIF", unique = true)
    private String nif;
	
	@Column(name = "NAME")
    private String name;
	
	@Column(name = "ADDRESS")
    private String address;
	
	@Column(name = "PHONE")
    private String phone;
 
    public Client() {
        super();
    }
    
    public Client(String nif, String name, String address, String phone) {
    	super();
    	this.nif = nif;
    	this.name = name;
    	this.address = address;
    	this.phone = phone;
    }
    
    public Client(int clientId, String nif, String name, String address, String phone) {
    	super();
    	this.clientId = clientId;
    	this.nif = nif;
    	this.name = name;
    	this.address = address;
    	this.phone = phone;
    }

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
