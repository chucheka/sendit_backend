package com.myapp.sendit.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myapp.sendit.dto.BecomeRiderDto;
import com.myapp.sendit.dto.LoginRequest;
import com.myapp.sendit.dto.SignUpRequest;
import com.myapp.sendit.exceptions.ResourceNotFoundException;
import com.myapp.sendit.model.Customer;
import com.myapp.sendit.model.Rider;
import com.myapp.sendit.model.Role;
import com.myapp.sendit.model.RoleName;
import com.myapp.sendit.model.User;
import com.myapp.sendit.payload.ApiResponse;
import com.myapp.sendit.payload.ErrorResponse;
import com.myapp.sendit.payload.JwtAuthenticationResponse;
import com.myapp.sendit.repository.CustomerRepository;
import com.myapp.sendit.repository.RiderRepository;
import com.myapp.sendit.repository.RoleRepository;
import com.myapp.sendit.repository.UserRepository;
import com.myapp.sendit.security.JwtTokenProvider;
import com.myapp.sendit.security.UserPrincipal;
import com.myapp.sendit.utils.Utilities;



@Service
public class AuthService {
	 @Autowired
	    AuthenticationManager authenticationManager;

		@Autowired
		private Utilities utilities;
		
	    @Autowired
	    UserRepository userRepository;
	    
	    @Autowired
	    CustomerRepository customerRepository;
	    
	    @Autowired
	    RiderRepository riderRepository;

	    @Autowired
	    RoleRepository roleRepository;

	    @Autowired
	    PasswordEncoder passwordEncoder;

	    @Autowired
	    JwtTokenProvider tokenProvider;

	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
		

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		

		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));



	}

	public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
		
		if(userRepository.existsByUsername(signUpRequest.getUsername())) {
        	ErrorResponse response = new ErrorResponse();
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setMessage("Username is already taken!");
			List<String> errors = new ArrayList<>();
			errors.add("Username is already taken!");
			response.setErrors(errors);
			return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
        	ErrorResponse response = new ErrorResponse();
        			response.setStatus(HttpStatus.BAD_REQUEST.value());
        			response.setMessage("Email Address already in use!");
        			List<String> errors = new ArrayList<>();
        			errors.add("Email Address already in use!");
        			response.setErrors(errors);
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> strPhoneNumbers = new HashSet<>();
        strPhoneNumbers.add(signUpRequest.getPhoneNumber1());
        strPhoneNumbers.add(signUpRequest.getPhoneNumber2());
        
        
        
        Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new ResourceNotFoundException("User Role not set."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
							.orElseThrow(() -> new ResourceNotFoundException("Admin Role not set."));
					roles.add(adminRole);
					break;
				case "rider":
					Role riderRole = roleRepository.findByName(RoleName.ROLE_RIDER)
							.orElseThrow(() -> new ResourceNotFoundException("Rider Role not set."));
					roles.add(riderRole);
					break;
				default:
					Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
							.orElseThrow(() -> new com.myapp.sendit.exceptions.ResourceNotFoundException("User Role not set."));
					roles.add(userRole);
				}
			});
		}
		
		
		
		user.getPhoneNumbers().addAll(strPhoneNumbers);
		
		user.setRoles(roles);
		
		
        User result = userRepository.save(user);
        
        result.setPassword("********");
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("User registration successfully created");
        response.setPayload(result);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
 
	}
	public ResponseEntity<?> registerAsRider(BecomeRiderDto riderDto) {
		if(riderRepository.existsByUsername(riderDto.getUsername())) {
        	ErrorResponse response = new ErrorResponse();
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setMessage("Username is already taken!");
			List<String> errors = new ArrayList<>();
			errors.add("Username is already taken!");
			response.setErrors(errors);
			return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }

        if(riderRepository.existsByEmail(riderDto.getEmail())) {
        	ErrorResponse response = new ErrorResponse();
        			response.setStatus(HttpStatus.BAD_REQUEST.value());
        			response.setMessage("Email Address already in use!");
        			List<String> errors = new ArrayList<>();
        			errors.add("Email Address already in use!");
        			response.setErrors(errors);
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }

        // Expand this to include fields for rider
        Rider rider = new Rider();
        rider.setUsername(riderDto.getUsername());
        rider.setEmail(riderDto.getEmail());
        rider.setPassword(riderDto.getPassword());
        rider.setPassword(passwordEncoder.encode(rider.getPassword()));
		rider.setAddress(riderDto.getaddress());
		rider.setCity(riderDto.getCity());
		rider.setState(riderDto.getState());

		
        Set<String> strPhoneNumbers = new HashSet<>();
        strPhoneNumbers.add(riderDto.getPhoneNumber1());
        strPhoneNumbers.add(riderDto.getPhoneNumber2());
//        
//		List<MultipartFile> files = Arrays.asList(riderDto.getIdCardImage());
//		
//		//Save files to cloudinary
//		
//		for(MultipartFile file : files) {
//			
//			List<String> result = utilities.validateAndUploadFile(file);
//			
//			if( result!= null) {
//				ErrorResponse response = new ErrorResponse();
//				response.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
//				response.setMessage("File upload failed");
//				List<String> errors = new ArrayList<>();
//				errors.add("Username is already taken!");
//				response.setErrors(result);
//				return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
//			}
//			
//		}
//		try {
//			rider.setIdCardImageUrl(utilities.uploadFileToCloud(riderDto.getIdCardImage()));
//		} catch (IOException e) {
//			throw new FileUploadException(e.getMessage());
//		}
//		        
        
		Set<Role> roles = new HashSet<>();
			Role riderRole = roleRepository.findByName(RoleName.ROLE_RIDER)
					.orElseThrow(() -> new com.myapp.sendit.exceptions.ResourceNotFoundException("Rider Role not set."));
			roles.add(riderRole);
		
		rider.getPhoneNumbers().addAll(strPhoneNumbers);
		
		rider.setRoles(roles);
		
        Rider result = riderRepository.save(rider);
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Successfully registered as a rider");
        response.setPayload(result);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
 
	}

	public ResponseEntity<?> registerAsCustomer(@Valid SignUpRequest customerDto) {
		System.out.println(customerDto);
		if(customerRepository.existsByUsername(customerDto.getUsername())) {
        	ErrorResponse response = new ErrorResponse();
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setMessage("Username is already taken!");
			List<String> errors = new ArrayList<>();
			errors.add("Username is already taken!");
			response.setErrors(errors);
			return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }

        if(customerRepository.existsByEmail(customerDto.getEmail())) {
        	ErrorResponse response = new ErrorResponse();
        			response.setStatus(HttpStatus.BAD_REQUEST.value());
        			response.setMessage("Email Address already in use!");
        			List<String> errors = new ArrayList<>();
        			errors.add("Email Address already in use!");
        			response.setErrors(errors);
            return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        Customer customer = new Customer();
		customer.setUsername(customerDto.getUsername());
		customer.setEmail(customerDto.getEmail());
		customer.setPassword(customerDto.getPassword());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        Set<String> strPhoneNumbers = new HashSet<>();
        strPhoneNumbers.add(customerDto.getPhoneNumber1());
        strPhoneNumbers.add(customerDto.getPhoneNumber2());
        
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new ResourceNotFoundException("User Role not set."));
		roles.add(userRole);
				
		
		
		
		customer.getPhoneNumbers().addAll(strPhoneNumbers);
		
		customer.setRoles(roles);
		
        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("User registration successfully created");
        response.setPayload(customerRepository.save(customer));
        return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);

	}
}
