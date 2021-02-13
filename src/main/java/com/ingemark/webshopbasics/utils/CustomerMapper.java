package com.ingemark.webshopbasics.utils;

import com.ingemark.webshopbasics.dto.CustomerDto;
import com.ingemark.webshopbasics.model.Customer;

public class CustomerMapper {

	public static Customer mapCustomertDtoToCustomer(CustomerDto pCustomerDto) {
		Customer tOutput = new Customer();
		tOutput.setEmail(pCustomerDto.getEmail());
		tOutput.setFirstName(pCustomerDto.getFirstName());
		tOutput.setLastName(pCustomerDto.getLastName());
		return tOutput;
	}

	public static CustomerDto mapCustomerToCustomerDto(Customer pCustomer) {
		CustomerDto tOutput = new CustomerDto();
		tOutput.setEmail(pCustomer.getEmail());
		tOutput.setFirstName(pCustomer.getFirstName());
		tOutput.setId(pCustomer.getId());
		tOutput.setLastName(pCustomer.getLastName());
		return tOutput;
	}

}
