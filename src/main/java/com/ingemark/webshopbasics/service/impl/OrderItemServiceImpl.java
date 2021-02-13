package com.ingemark.webshopbasics.service.impl;

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
	public Double selectTotalByOrderId(Long pIdOrder) {
		Optional<Double> tOutputOptional = iOrderItemRepository.selectTotalByOrderId(pIdOrder);
		return tOutputOptional.isPresent() ? tOutputOptional.get() : Double.valueOf(0L);
	}

}
