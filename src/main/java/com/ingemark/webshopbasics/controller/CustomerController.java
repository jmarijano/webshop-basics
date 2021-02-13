package com.ingemark.webshopbasics.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ingemark.webshopbasics.dto.CustomerDto;
import com.ingemark.webshopbasics.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService iCustomerService;

	@GetMapping()
	public ResponseEntity<List<CustomerDto>> findAll() {
		return ResponseEntity.ok(iCustomerService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> getOne(@PathVariable("id") Long pId) {
		return new ResponseEntity<CustomerDto>(iCustomerService.getOne(pId), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<CustomerDto> save(@Valid @RequestBody CustomerDto pCustomerDto) {
		return new ResponseEntity<CustomerDto>(iCustomerService.save(pCustomerDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerDto> save(@PathVariable("id") Long pCustomerId,
			@Valid @RequestBody CustomerDto pCustomerDto) {
		CustomerDto tResponse = iCustomerService.update(pCustomerId, pCustomerDto);
		if (tResponse.getId().equals(pCustomerId))
			return new ResponseEntity<CustomerDto>(tResponse, HttpStatus.OK);
		return new ResponseEntity<CustomerDto>(tResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable("id") Long pProductId) {

		iCustomerService.delete(pProductId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
