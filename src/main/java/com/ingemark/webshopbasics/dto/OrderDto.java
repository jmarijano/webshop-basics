package com.ingemark.webshopbasics.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

	private Long id;
	@NotNull(message = "{empty.order.customer.id}")
	private Long customerId;
	@NotNull(message = "{null.order.products}")
	private List<ItemDto> products;

	private String status;
	private BigDecimal totalPriceHrk;
	private BigDecimal totalPriceEur;
}
