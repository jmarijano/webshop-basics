package com.ingemark.webshopbasics.service;

import java.util.List;

import com.ingemark.webshopbasics.dto.OrderDto;

public interface OrderService {

	List<OrderDto> findAll();

	OrderDto getOne(Long pOrderId);

	OrderDto save(OrderDto pOrderId);

	OrderDto update(Long pOrderId, OrderDto pOrderDto);

	void delete(Long pOrderId);

	OrderDto finalize(Long pOrderId);
}
