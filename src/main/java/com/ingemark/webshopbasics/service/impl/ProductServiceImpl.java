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
import com.ingemark.webshopbasics.utils.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository iProductRepository;

	@Override
	public List<ProductDto> findAll() {
		return iProductRepository.findAll().stream().map(tProduct -> ProductMapper.mapProductToProductDto(tProduct))
				.collect(Collectors.toList());
	}

	@Override
	public ProductDto getOne(Long pId) {
		return iProductRepository.findById(pId).map(tProduct -> ProductMapper.mapProductToProductDto(tProduct))
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
			return ProductMapper.mapProductToProductDto(save);
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
				return ProductMapper.mapProductToProductDto(save);
			} catch (DataIntegrityViolationException e) {
				throw new SystemErrorException("data.integrity.product.code");
			}
		}).orElseGet(() -> {
			Product tProduct = ProductMapper.mapProductDtoToProduct(pProductDto);
			try {
				Product save = iProductRepository.save(tProduct);
				return ProductMapper.mapProductToProductDto(save);
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

	@Override
	public List<ProductDto> findAllByIds(List<Long> pIds) {
		return iProductRepository.findAllById(pIds).stream()
				.map(tProduct -> ProductMapper.mapProductToProductDto(tProduct)).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> findAllByIdInAndIsAvailable(List<Long> pIds, Boolean pIsAvailable) {
		return iProductRepository.findAllByIdInAndIsAvailable(pIds,
				pIsAvailable).stream()
				.map(tProduct -> ProductMapper.mapProductToProductDto(tProduct)).collect(Collectors.toList());
	}

}
