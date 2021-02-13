package com.ingemark.webshopbasics.service;

import java.util.List;

import com.ingemark.webshopbasics.dto.ProductDto;

public interface ProductService {

	List<ProductDto> findAll();

	ProductDto getOne(Long pId);

	ProductDto save(ProductDto pProductDto);

	ProductDto update(Long pProductId, ProductDto pProductDto);

	void delete(Long pProductId);
}
