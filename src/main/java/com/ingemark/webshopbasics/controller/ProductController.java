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

import com.ingemark.webshopbasics.dto.ProductDto;
import com.ingemark.webshopbasics.rest.model.RestResponse;
import com.ingemark.webshopbasics.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

	@Autowired
	private ProductService iProductService;

	@GetMapping()
	public ResponseEntity<RestResponse> findAll() {
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(iProductService.findAll()).build(),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestResponse> getOne(@PathVariable("id") Long pId) {
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(iProductService.getOne(pId)).build(),
				HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<RestResponse> save(@Valid @RequestBody ProductDto pProductDto) {
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(iProductService.save(pProductDto)).build(),
				HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestResponse> update(@Valid @PathVariable("id") Long pProductId,
			@Valid @RequestBody ProductDto pProductDto) {
		ProductDto tResponse = iProductService.update(pProductId, pProductDto);
		if (tResponse.getId().equals(pProductId))
			return new ResponseEntity<RestResponse>(RestResponse.builder().data(tResponse).build(), HttpStatus.OK);
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(tResponse).build(), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable("id") Long pProductId) {

		iProductService.delete(pProductId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
