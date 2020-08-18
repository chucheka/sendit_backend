package com.myapp.sendit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.sendit.payload.ApiResponse;
import com.myapp.sendit.services.RiderService;


@RestController
@RequestMapping("/api/v1/riders")
public class RiderController {
	@Autowired
	private RiderService riderService;
	
	@PutMapping("/{riderId}/{parcelId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> assignParcel(@PathVariable("riderId") Long riderId,@PathVariable("parcelId") Long parcelId) throws Exception {
		ApiResponse response = new ApiResponse(HttpStatus.OK.value(),"Parcel assigned successfully!",riderService.assignParcelByRiderId(riderId, parcelId));
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
	@GetMapping("/{riderId}/parcels")
	@PreAuthorize("hasRole('RIDER')")
	public ResponseEntity<ApiResponse> getRiderParcels(@PathVariable("riderId") Long riderId) throws Exception {
		ApiResponse response = new ApiResponse(HttpStatus.OK.value(),"All Riders parcels",riderService.getAssignedParcels(riderId));
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
	
	
	@GetMapping("/search")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> searchRiders() throws Exception {
		ApiResponse response = new ApiResponse(HttpStatus.OK.value(),"All matched riders fetched!",riderService.searchForRider());
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
}
	