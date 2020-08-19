package com.myapp.sendit.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.myapp.sendit.interfaces.FieldsValueMatch;
import com.myapp.sendit.interfaces.PasswordMatches;


@FieldsValueMatch.List({ 
    @FieldsValueMatch(
      field = "newPassword", 
      fieldMatch = "confirmNewPassword", 
      message = "New Passwords do not match!"
    )
})
public class SetNewPasswordPayload {
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@NotBlank
    @Size(min = 6, max = 20)
    private String newPassword;
    private String confirmNewPassword;
	
    public SetNewPasswordPayload() {
	
	}
	
    public SetNewPasswordPayload( String email,String newPassword, String confirmNewPassword) {
		super();
		this.email = email;
		this.newPassword = newPassword;
		this.confirmNewPassword = confirmNewPassword;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	
}
