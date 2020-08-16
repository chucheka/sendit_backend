package com.myapp.sendit.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(	name = "customers")
public class Customer extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "customer")
	@JsonManagedReference
    private List<Parcel> parcels = new ArrayList<>();
	
	

	public Customer() {
		
	}


	public List<Parcel> getParcels() {
		return parcels;
	}


	public void setParcels(List<Parcel> parcels) {
		this.parcels = parcels;
	}
	public void addParcel(Parcel parcel) {
		
		parcels.add(parcel);
		parcel.setCustomer(this);
	}
	
	public void removeParcel(Parcel parcel) {
		parcels.remove(parcel);
		parcel.setCustomer(null);
	}
}

