package com.ingemark.webshopbasics;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ingemark.webshopbasics.controller.OrderController;
import com.ingemark.webshopbasics.dto.OrderDto;
import com.ingemark.webshopbasics.repository.OrderRepository;
import com.ingemark.webshopbasics.service.OrderService;

@SpringBootTest()
public class OrderTests {

	@Autowired
	private OrderService iOrderService;

	@MockBean
	private OrderRepository iOrderRepository;

	@Autowired
	private OrderController iOrderController;

	@Autowired
	private Validator iValidator;

	@Test
	void check_that_service_is_not_null() {
		assertThat(iOrderService).as("Check that service is not null").isNotNull();

	}

	@Test
	void check_that_repository_is_not_null() {
		assertThat(iOrderRepository).as("Check that repository is not null").isNotNull();
	}

	@Test
	void check_that_order_dto_has_validation_errors() {
		OrderDto tOrderDto = new OrderDto();
		Set<ConstraintViolation<OrderDto>> violations = iValidator.validate(tOrderDto);
		assertThat(violations).as("Check that orderDto has validation errors").isNotNull().isNotEmpty();

	}

	@Test
	void check_that_order_dto_has_no_validation_errors() {
		OrderDto tOrderDto = new OrderDto();
		tOrderDto.setCustomerId(1L);
		tOrderDto.setProducts(new ArrayList<>());
		Set<ConstraintViolation<OrderDto>> violations = iValidator.validate(tOrderDto);
		assertThat(violations).as("Check that orderDto has no validation errors").isEmpty();

	}
}
