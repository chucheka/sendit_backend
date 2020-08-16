package com.myapp.sendit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.sendit.dto.ParcelDto;
import com.myapp.sendit.dto.ParcelLocationDto;
import com.myapp.sendit.dto.StatusDto;
import com.myapp.sendit.exception.ActionNotAllowedException;
import com.myapp.sendit.exception.ResourceNotFoundException;
import com.myapp.sendit.model.Customer;
import com.myapp.sendit.model.Parcel;
import com.myapp.sendit.model.Status;
import com.myapp.sendit.model.User;
import com.myapp.sendit.repository.CustomerRepository;
import com.myapp.sendit.repository.ParcelRepository;
import com.myapp.sendit.repository.UserRepository;
import com.myapp.sendit.security.UserPrincipal;



@Service
public class ParcelService {

	@Autowired
	EmailService emailService;
	
	@Autowired
	ParcelRepository parcelRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	// ** GET /api/v1/parcels/{parcelId} ** 
	public Parcel getParcelById(Long parcelId) throws ResourceNotFoundException,Exception{
		Parcel parcel = parcelRepository.findById(parcelId)
				.orElseThrow(()->new ResourceNotFoundException("Parcel with ID: "+parcelId+" does not exist!"));
		return parcel;
	}
	
	// ** POST /api/v1/parcels ** 
	public Parcel createParcelOrder(ParcelDto newParcel,UserPrincipal currentUser) throws Exception{
		Customer customer =  customerRepository.findById(currentUser.getId())
				.orElseThrow(()-> new ResourceNotFoundException("Customer creating parcel is not registered!"));
		
		 Parcel parcel = new Parcel();
		 parcel.setCustomer(customer);
		 parcel.setDestination(newParcel.getDest_address().replaceAll(","," ")+","+newParcel.getDest_city().replaceAll(","," ")+","+newParcel.getDest_state().replaceAll(","," "));
		 parcel.setPickupLocation(newParcel.getPickup_address().replaceAll(","," ")+","+newParcel.getPickup_city().replaceAll(","," ")+","+newParcel.getPickup_state().replaceAll(","," "));
		 parcel.setPresentLocation(newParcel.getPickup_address().replaceAll(","," ")+","+newParcel.getPickup_city().replaceAll(","," ")+","+newParcel.getPickup_state().replaceAll(","," "));
		 parcel.setStatus(Status.AT_PICKUP);
		 parcel.setPrice(newParcel.getPrice());
		
		try {
			return parcelRepository.save(parcel);
		
		}catch(Exception exc) {
			throw new Exception("Internal Server Error \n"+exc.getCause());
		}
	}
	
	
	// ** GET /api/v1/parcels ** 
	public List<Parcel> getAllParcelOrders(){
		List<Parcel> parcels = parcelRepository.findAll();
		return parcels;
	}
	
	
	// ** GET /api/v1/parcels/cancel ** 
	public Parcel cancelParcelOrder(Long parcelId,UserPrincipal currentUser) throws ResourceNotFoundException,Exception{	
		Parcel dbParcel = parcelRepository.findById(parcelId)
				.orElseThrow(()->new ResourceNotFoundException("Parcel with ID: "+parcelId+" does not exist!"));
		User authUser = userRepository
				.findById(currentUser.getId())
				.orElseThrow(()->new ResourceNotFoundException("This user does not exist"));
		Customer parcelOwner = dbParcel.getCustomer();
		
		if(!parcelOwner.equals(authUser)) {
			throw new ActionNotAllowedException("Only user who created parcel order can cancel order");
	}
		
		if(Status.DELIVERED.equals(dbParcel.getStatus())) {
			throw new ActionNotAllowedException("Parcel has already been delivered");
		}else {
			try {
				dbParcel.setStatus(Status.CANCELLED);
				return parcelRepository.save(dbParcel);
				
			}catch(Exception exc) {
				throw new Exception("Internal Server Error \n"+exc.getCause());
			}
	}
}
	
	
	// ** GET /api/v1/users/{userId}/parcels ** 
	public List<Parcel> getParcelByUserId(Long userId) throws ResourceNotFoundException{
		//Find the user by user id
   	 Customer customer = customerRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User does not exist"));
			
   	 List<Parcel> customerParcels = customer.getParcels();
				
//				if(userParcels.isEmpty()) {
//					throw new ResourceNotFoundException("No parcel order(s) for this user");
//				}
				
					return customerParcels;
	}
	
	
	// ** GET /api/v1/parcels/status ** 
	public Parcel changeParcelStatus(StatusDto statusDto,Long parcelId){
		Parcel parcel = parcelRepository.findById(parcelId)
				.orElseThrow(()->new ResourceNotFoundException("Parcel to update not found!!"));
		if(Status.DELIVERED.equals(parcel.getStatus())) {
			throw new ActionNotAllowedException("Cannot changed status of parcel already delivered");
		}
		if(statusDto.getStatus().equals(Status.DELIVERED)) {
			parcel.setPresentLocation(parcel.getDestination());
		}
		// Update the status
        parcel.setStatus(statusDto.getStatus());
        
        Parcel updatedParcel = parcelRepository.save(parcel);
        
        if(updatedParcel != null) {
        	
        	Customer customer = updatedParcel.getCustomer();
//            emailService.sendEmail(customer.getEmail(), "UPDATES FROM SENDIT COURIER", "Parcel Status: " +updatedParcel.getStatus());
        }
        System.out.println();
		return updatedParcel;
	
	}
	
	
	// ** GET /api/v1/parcels/present_location ** 
	public Parcel updateParcelLocation(ParcelLocationDto newLocationDto,Long parcelId,UserPrincipal currentUser) throws ResourceNotFoundException{
		
		//Fetch the parcel with particular Id
		Optional<Parcel> parcel = parcelRepository.findById(parcelId);
		//Check if parcel exist
		if(parcel.isPresent()) 
        {
            Parcel updatedParcel = parcel.get();
            updatedParcel.setStatus(Status.TRANSIT);
            updatedParcel.setPresentLocation(newLocationDto.getAddress());
            updatedParcel = parcelRepository.save(updatedParcel);
            return updatedParcel;
        } else {
        	throw new ResourceNotFoundException("Parcel to change location doen not exist");
        }
	}
	
	
	
	// ** GET /api/v1/parcels/destination ** 
	public Parcel changeDestination(ParcelLocationDto newDestinationDto,Long parcelId,UserPrincipal currentUser) throws ResourceNotFoundException{
		//Fetch the parcel with particular Id
				Optional<Parcel> parcel = parcelRepository.findById(parcelId);
				//Check if parcel exist
					if(parcel.isPresent()) 
			        {
			            Parcel updatedParcel = parcel.get();
			            if(Status.DELIVERED.equals(updatedParcel.getStatus())) {
			            	throw new ActionNotAllowedException("Cannot change destination of already delivered parcel");
			            }
			            
			            updatedParcel.setDestination(newDestinationDto.getAddress()+", "+newDestinationDto.getCity()+", "+newDestinationDto.getState());
			            updatedParcel = parcelRepository.save(updatedParcel);
			            return updatedParcel;
			        } else {
			        	throw new ResourceNotFoundException("Parcel to change destination doen not exist");
			        }
	}
}
