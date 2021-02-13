package com.ingemark.webshopbasics.service;

import java.util.List;

import com.ingemark.webshopbasics.dto.CustomerDto;

public interface CustomerService {

	List<CustomerDto> findAll();

	CustomerDto getOne(Long pCustomerId);

	CustomerDto save(CustomerDto pCustomerDto);

	CustomerDto update(Long pCustomerId, CustomerDto pCustomerDto);

	void delete(Long pCustomerId);

}
