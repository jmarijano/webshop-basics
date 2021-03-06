package com.ingemark.webshopbasics.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ProductDto {

	private Long id;

	@NotNull(message = "{empty.product.code}")
	@Size(min = 10, max = 10, message = "{invalid.product.code.length}")
	private String code;
	@NotEmpty(message = "{empty.product.name}")
	private String name;

	@NotNull(message = "{empty.product.price}")
	@DecimalMin(value = "0.0", inclusive = false, message = "{invalid.product.price}")
	private BigDecimal price;
	private String description;
	@NotNull(message = "{empty.product.is.available}")
	private Boolean isAvailable;
}
