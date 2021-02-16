package com.ingemark.webshopbasics.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingemark.webshopbasics.repository.OrderItemRepository;
import com.ingemark.webshopbasics.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository iOrderItemRepository;

	@Override
	public BigDecimal selectTotalByOrderId(Long pIdOrder) {
		Optional<BigDecimal> tOutputOptional = iOrderItemRepository.selectTotalByOrderId(pIdOrder);
		return tOutputOptional.isPresent() ? tOutputOptional.get() : BigDecimal.valueOf(0L);
	}

}
