package com.ingemark.webshopbasics.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.ingemark.webshopbasics.dto.CustomerDto;

@Validated
public interface CustomerService {

	List<CustomerDto> findAll();

	CustomerDto getOne(Long pCustomerId);

	CustomerDto save(@Valid CustomerDto pCustomerDto);

	CustomerDto update(Long pCustomerId, @Valid CustomerDto pCustomerDto);

	void delete(Long pCustomerId);

}
