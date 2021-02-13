package com.ingemark.webshopbasics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

	private Integer quantity;
	private Long productId;

	@Override
	public boolean equals(Object pObj) {
		return pObj instanceof ItemDto && ((ItemDto) pObj).getProductId() == this.productId ? true : false;

	}

	@Override
	public int hashCode() {
		return this.productId.intValue();
	}
}
