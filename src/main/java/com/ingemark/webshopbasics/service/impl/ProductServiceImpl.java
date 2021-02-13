package com.ingemark.webshopbasics.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ingemark.webshopbasics.dto.ProductDto;
import com.ingemark.webshopbasics.exception.ResourceNotFoundException;
import com.ingemark.webshopbasics.exception.SystemErrorException;
import com.ingemark.webshopbasics.model.Product;
import com.ingemark.webshopbasics.repository.ProductRepository;
import com.ingemark.webshopbasics.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository iProductRepository;

	@Override
	public List<ProductDto> findAll() {
		return iProductRepository.findAll().stream().map(tProduct -> mapProductToProductDto(tProduct))
				.collect(Collectors.toList());
	}

	@Override
	public ProductDto getOne(Long pId) {
		return iProductRepository.findById(pId).map(tProduct -> mapProductToProductDto(tProduct))
				.orElseThrow(() -> new ResourceNotFoundException("resource.not.found"));
	}

	@Override
	public ProductDto save(ProductDto pProductDto) {

		Product tProduct = new Product();
		tProduct.setCode(pProductDto.getCode());
		tProduct.setDescription(pProductDto.getDescription());
		tProduct.setIsAvailable(pProductDto.getIsAvailable());
		tProduct.setName(pProductDto.getName());
		tProduct.setPriceHrk(pProductDto.getPrice());
		try {
			Product save = iProductRepository.save(tProduct);
			return mapProductToProductDto(save);
		} catch (DataIntegrityViolationException e) {
			throw new SystemErrorException("data.integrity.product.code");
		}

	}

	@Override
	public ProductDto update(Long pProductId, ProductDto pProductDto) {
		return iProductRepository.findById(pProductId).map(tProduct -> {
			tProduct.setCode(pProductDto.getCode());
			tProduct.setDescription(pProductDto.getDescription());
			tProduct.setIsAvailable(pProductDto.getIsAvailable());
			tProduct.setName(pProductDto.getName());
			tProduct.setPriceHrk(pProductDto.getPrice());
			try {
				Product save = iProductRepository.save(tProduct);
				return mapProductToProductDto(save);
			} catch (DataIntegrityViolationException e) {
				throw new SystemErrorException("data.integrity.product.code");
			}
		}).orElseGet(() -> {
			Product tProduct = mapProductDtoToProduct(pProductDto);
			try {
				Product save = iProductRepository.save(tProduct);
				return mapProductToProductDto(save);
			} catch (DataIntegrityViolationException e) {
				throw new SystemErrorException("data.integrity.product.code");
			}
		});

	}

	@Override
	public void delete(Long pProductId) {
		try {

			iProductRepository.deleteById(pProductId);
		} catch (Exception e) {

		}
	}

	private Product mapProductDtoToProduct(ProductDto pProductDto) {
		Product tOutput = new Product();
		tOutput.setCode(pProductDto.getCode());
		tOutput.setDescription(pProductDto.getDescription());
		tOutput.setIsAvailable(pProductDto.getIsAvailable());
		tOutput.setName(pProductDto.getName());
		tOutput.setPriceHrk(pProductDto.getPrice());
		return tOutput;
	}

	private ProductDto mapProductToProductDto(Product pProduct) {
		ProductDto tOutput = new ProductDto();
		tOutput.setId(pProduct.getId());
		tOutput.setCode(pProduct.getCode());
		tOutput.setDescription(pProduct.getDescription());
		tOutput.setIsAvailable(pProduct.getIsAvailable());
		tOutput.setName(pProduct.getName());
		tOutput.setPrice(pProduct.getPriceHrk());
		return tOutput;
	}

}
