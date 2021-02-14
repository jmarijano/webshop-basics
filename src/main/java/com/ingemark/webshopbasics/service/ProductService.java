package com.ingemark.webshopbasics.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.ingemark.webshopbasics.dto.ProductDto;

@Validated
public interface ProductService {

	List<ProductDto> findAll();

	ProductDto getOne(Long pId);

	ProductDto save(@Valid ProductDto pProductDto);

	ProductDto update(Long pProductId,@Valid ProductDto pProductDto);

	void delete(Long pProductId);

	List<ProductDto> findAllByIds(List<Long> pIds);

	List<ProductDto> findAllByIdInAndIsAvailable(List<Long> pIds, Boolean pIsAvailable);
}
