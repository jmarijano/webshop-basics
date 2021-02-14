package com.ingemark.webshopbasics.controller;

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
import com.ingemark.webshopbasics.rest.model.RestResponse;
import com.ingemark.webshopbasics.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService iCustomerService;

	@GetMapping()
	public ResponseEntity<RestResponse> findAll() {
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(iCustomerService.findAll()).build(),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestResponse> getOne(@PathVariable("id") Long pId) {
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(iCustomerService.getOne(pId)).build(),
				HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<RestResponse> save(@Valid @RequestBody CustomerDto pCustomerDto) {
		return new ResponseEntity<RestResponse>(
				RestResponse.builder().data(iCustomerService.save(pCustomerDto)).build(), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestResponse> save(@PathVariable("id") Long pCustomerId,
			@Valid @RequestBody CustomerDto pCustomerDto) {
		CustomerDto tResponse = iCustomerService.update(pCustomerId, pCustomerDto);
		if (tResponse.getId().equals(pCustomerId))
			return new ResponseEntity<RestResponse>(RestResponse.builder().data(tResponse).build(), HttpStatus.OK);
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(tResponse).build(), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable("id") Long pProductId) {

		iCustomerService.delete(pProductId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
