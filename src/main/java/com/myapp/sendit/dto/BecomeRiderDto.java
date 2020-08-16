package com.myapp.sendit.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


//@PasswordMatches(message="Password fields must match")
public class BecomeRiderDto {

	@NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40,message="Email is too long")
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    private String confirmPassword;
 
	@NotBlank
	@Size(min=11,max=14,message="Phone number must not be more then 14 digits")
	private String phoneNumber1;
	
	
	@Size(min=11,max=14,message="Phone number must not be more then 14 digits")
	private String phoneNumber2;
	
	
//	@NotNull
//	private  MultipartFile idCardImage;
	
	@NotNull
	@Size(max = 150,message = "Address must be 150 characters long")
	private String address;
	
	@NotNull
	@Size(max = 40)
	private String city;
	
	@NotNull
	@Size(max = 40)
	private String state;

	public BecomeRiderDto() {
	}

	public BecomeRiderDto(String username,String email,String password, String confirmPassword,String phoneNumber1,String phoneNumber2,String address,String city,String state) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.phoneNumber1 = phoneNumber1;
		this.phoneNumber2 = phoneNumber2;
//		this.idCardImage = idCardImage;
		this.address = address;
		this.city = city;
		this.state = state;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

//		public MultipartFile getIdCardImage() {
//		return idCardImage;
//	}
//
//	public void setIdCardImage(MultipartFile idCardImage) {
//		this.idCardImage = idCardImage;
//	}

	public String getaddress() {
		return address;
	}

	public void setaddress(String address) {
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
}
