package com.ingemark.webshopbasics.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CustomerDto {

	private Long id;

	@NotEmpty(message = "{empty.customer.first.name}")
	private String firstName;

	@NotEmpty(message = "{empty.customer.last.name}")
	private String lastName;

	@NotEmpty(message = "{empty.customer.email}")
	@Email(message = "{invalid.customer.email.format}")
	private String email;

}
