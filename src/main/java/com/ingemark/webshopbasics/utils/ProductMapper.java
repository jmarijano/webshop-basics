package com.ingemark.webshopbasics.utils;

import com.ingemark.webshopbasics.dto.ProductDto;
import com.ingemark.webshopbasics.model.Product;

public class ProductMapper {

	public static Product mapProductDtoToProduct(ProductDto pProductDto) {
		Product tOutput = new Product();
		tOutput.setCode(pProductDto.getCode());
		tOutput.setDescription(pProductDto.getDescription());
		tOutput.setIsAvailable(pProductDto.getIsAvailable());
		tOutput.setName(pProductDto.getName());
		tOutput.setPriceHrk(pProductDto.getPrice());
		return tOutput;
	}

	public static ProductDto mapProductToProductDto(Product pProduct) {
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
