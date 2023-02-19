package com.masai.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	private String email;
	
	@NotNull
	@Pattern(regexp = "[a-zA-Z]{3,12}", message = "Must not contains numbers or special characters")
	private String firstName;
	
	@NotNull
	@Pattern(regexp = "[a-zA-Z]{3,12}", message = "Must not contains numbers or special characters")
	private String lastName;
	
	@Pattern(regexp = "[6-9]{1}[0-9]{9}", message = "Mobile number must have 10 digits")
	private String mobileNumber;
	private String password;
	
	@NotNull
	@Past
	private LocalDate dateOfBirth;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	List<Event> events;
	
}
