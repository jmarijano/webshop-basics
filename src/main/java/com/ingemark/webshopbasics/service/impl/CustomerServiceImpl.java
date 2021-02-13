package com.ingemark.webshopbasics.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ingemark.webshopbasics.dto.CustomerDto;
import com.ingemark.webshopbasics.exception.ResourceNotFoundException;
import com.ingemark.webshopbasics.exception.SystemErrorException;
import com.ingemark.webshopbasics.model.Customer;
import com.ingemark.webshopbasics.repository.CustomerRepository;
import com.ingemark.webshopbasics.service.CustomerService;
import com.ingemark.webshopbasics.utils.CustomerMapper;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository iCustomerRepository;

	@Override
	public List<CustomerDto> findAll() {
		return iCustomerRepository.findAll().stream().map(tProduct -> CustomerMapper.mapCustomerToCustomerDto(tProduct))
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDto getOne(Long pId) {
		return iCustomerRepository.findById(pId).map(tProduct -> CustomerMapper.mapCustomerToCustomerDto(tProduct))
				.orElseThrow(() -> new ResourceNotFoundException("resource.not.found"));
	}

	@Override
	public CustomerDto save(CustomerDto pProductDto) {

		Customer tProduct = CustomerMapper.mapCustomertDtoToCustomer(pProductDto);
		try {
			Customer save = iCustomerRepository.save(tProduct);
			return CustomerMapper.mapCustomerToCustomerDto(save);
		} catch (DataIntegrityViolationException e) {
			throw new SystemErrorException("data.integrity.customer.email");
		}

	}

	@Override
	public CustomerDto update(Long pProductId, CustomerDto pProductDto) {
		return iCustomerRepository.findById(pProductId).map(tProduct -> {
			tProduct.setEmail(pProductDto.getEmail());
			tProduct.setFirstName(pProductDto.getFirstName());
			tProduct.setLastName(pProductDto.getLastName());
			try {
				Customer save = iCustomerRepository.save(tProduct);
				return CustomerMapper.mapCustomerToCustomerDto(save);
			} catch (DataIntegrityViolationException e) {
				throw new SystemErrorException("data.integrity.customer.email");
			}
		}).orElseGet(() -> {
			Customer tProduct = CustomerMapper.mapCustomertDtoToCustomer(pProductDto);
			try {
				Customer save = iCustomerRepository.save(tProduct);
				return CustomerMapper.mapCustomerToCustomerDto(save);
			} catch (DataIntegrityViolationException e) {
				throw new SystemErrorException("data.integrity.customer.email");
			}
		});

	}

	@Override
	public void delete(Long pProductId) {
		try {

			iCustomerRepository.deleteById(pProductId);
		} catch (Exception e) {

		}
	}

}
