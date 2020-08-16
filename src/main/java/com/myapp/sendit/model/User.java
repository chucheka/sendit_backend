package com.myapp.sendit.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.myapp.sendit.audit.AuditModel;


@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(	name = "users",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User extends AuditModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	 @GeneratedValue(generator = "user_generator")
     @SequenceGenerator(
    		name = "user_generator",
    		sequenceName = "user_sequence",
    		initialValue = 1000
    		)
	private Long userId;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@ElementCollection
	@CollectionTable(name = "user_phone_numbers",joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "phone_number",nullable = false)
	private Set<String> phoneNumbers = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles  = new HashSet<>();
	
		
	public User() {
		
	}
	

	public User(String username,String email,String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Set<String> getPhoneNumbers() {
		return phoneNumbers;
	}


	public void setPhoneNumbers(Set<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
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


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

//	@Override 
//	public boolean equal(Object o) {
//		if(this == o) return true;
//		if(o==null || getClass()!=o.getClass()) return false;
//		return id!=null && id.equals((User) o).id);
//	}

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
//				+ ", roles=" + roles + ", parcels=" + parcels + "]";
//	}

	

}