package com.ingemark.webshopbasics.utils;

import java.util.stream.Collectors;

import com.ingemark.webshopbasics.dto.ItemDto;
import com.ingemark.webshopbasics.dto.OrderDto;
import com.ingemark.webshopbasics.model.Order;

public class OrderMapper {

	public static OrderDto mapOrderToOrderDto(Order pOrder) {
		OrderDto tOrderDto = new OrderDto();
		tOrderDto.setCustomerId(pOrder.getCustomer().getId());
		tOrderDto.setStatus(pOrder.getStatus().getName());
		tOrderDto.setId(pOrder.getId());
		tOrderDto.setTotalPriceEur(pOrder.getTotalPriceEur());
		tOrderDto.setTotalPriceHrk(pOrder.getTotalPriceHrk());
		tOrderDto.setProducts(pOrder.getOrderItems().stream().map(tOrderItem -> {
			ItemDto tItemDto = new ItemDto();
			tItemDto.setProductId(tOrderItem.getProduct().getId());
			tItemDto.setQuantity(tOrderItem.getQuantity());
			return tItemDto;
		}).collect(Collectors.toList()));
		return tOrderDto;
	}
}
