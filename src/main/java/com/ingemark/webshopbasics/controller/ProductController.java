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

import com.ingemark.webshopbasics.dto.ProductDto;
import com.ingemark.webshopbasics.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

	@Autowired
	private ProductService iProductService;

	@GetMapping()
	public ResponseEntity<List<ProductDto>> findAll() {
		return ResponseEntity.ok(iProductService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getOne(@PathVariable("id") Long pId) {
		return new ResponseEntity<ProductDto>(iProductService.getOne(pId), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<ProductDto> save(@Valid @RequestBody ProductDto pProductDto) {
		return new ResponseEntity<ProductDto>(iProductService.save(pProductDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> update(@Valid @PathVariable("id") Long pProductId,
			@Valid @RequestBody ProductDto pProductDto) {
		ProductDto tResponse = iProductService.update(pProductId, pProductDto);
		if (tResponse.getId().equals(pProductId))
			return new ResponseEntity<ProductDto>(tResponse, HttpStatus.OK);
		return new ResponseEntity<ProductDto>(tResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable("id") Long pProductId) {

		iProductService.delete(pProductId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
