package com.ingemark.webshopbasics.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.ingemark.webshopbasics.dto.OrderDto;

@Validated
public interface OrderService {

	List<OrderDto> findAll();

	OrderDto getOne(Long pOrderId);

	OrderDto save(@Valid OrderDto pOrderId);

	OrderDto update(Long pOrderId, @Valid OrderDto pOrderDto);

	void delete(Long pOrderId);

	OrderDto finalize(Long pOrderId);
}
