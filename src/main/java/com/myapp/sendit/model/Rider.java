package com.myapp.sendit.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(	name = "riders")
public class Rider extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String photoUrl;
	
	private  String idCardImageUrl;
	
	private String address;
	
	
	private String city;
	
	private String state;

	@ElementCollection
	private List<Long> parcelIds = new ArrayList<Long>();
	
	public Rider() {
	}


	public String getPhotoUrl() {
		return photoUrl;
	}


	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}


	public String getIdCardImageUrl() {
		return idCardImageUrl;
	}


	public void setIdCardImageUrl(String idCardImageUrl) {
		this.idCardImageUrl = idCardImageUrl;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public List<Long> getparcelIds() {
		return parcelIds;
	}


	public void setparcelIds(List<Long> parcelIds) {
		this.parcelIds = parcelIds;
	}
	
}

