package com.myapp.sendit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.sendit.exceptions.BadRequestException;
import com.myapp.sendit.exceptions.ResourceNotFoundException;
import com.myapp.sendit.model.Parcel;
import com.myapp.sendit.model.Rider;
import com.myapp.sendit.repository.ParcelRepository;
import com.myapp.sendit.repository.RiderRepository;
import com.myapp.sendit.repository.UserRepository;


@Service
public class RiderService {

	
    @Autowired
    UserRepository userRepository;

    @Autowired
    RiderRepository riderRepository;
    
    @Autowired
	ParcelRepository parcelRepository;
    
 
 	public List<Parcel> getAssignedParcels(Long riderId) throws ResourceNotFoundException,Exception{
 		
 		
 		Rider rider = riderRepository.findById(riderId).orElseThrow(()->new ResourceNotFoundException("No parcels assigned to this user yet!"));
 		
 		List<Long> assignedParcelIds = rider.getparcelIds();
 		
 		List<Parcel> assignedParcels = parcelRepository.findByParcelIdIn(assignedParcelIds);
 		
 		return assignedParcels;
 	}
 	public Rider assignParcelByRiderId(Long riderId,Long parcelId) throws ResourceNotFoundException,Exception{
 		Parcel parcel = parcelRepository.findById(parcelId).orElseThrow(()->new ResourceNotFoundException("Parcel with ID: "+parcelId+" does not exist!"));
 		
 		Rider rider = riderRepository.findById(riderId).orElseThrow(()->new ResourceNotFoundException("Rider with ID: "+riderId+" does not exist!"));
 		
 		List<Long> assignedParcelIds = rider.getparcelIds();
 		if(assignedParcelIds.contains(parcelId)) {
 			throw new BadRequestException("Parcel already assigned to this rider");
 		}
 		
 		assignedParcelIds.add(parcelId);
 		parcel.setAssigned(true);
 		return riderRepository.save(rider);
 		
 	}
 	public List<Rider> searchForRider() throws ResourceNotFoundException,Exception{
 		List<Rider> riders = riderRepository.findAll();
 		return riders;
 		
 	}
}
