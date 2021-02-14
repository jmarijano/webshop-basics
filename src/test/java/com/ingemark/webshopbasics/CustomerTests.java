package com.ingemark.webshopbasics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ingemark.webshopbasics.controller.CustomerController;
import com.ingemark.webshopbasics.dto.CustomerDto;
import com.ingemark.webshopbasics.repository.CustomerRepository;
import com.ingemark.webshopbasics.service.CustomerService;

@SpringBootTest()
public class CustomerTests {

	@Autowired
	private CustomerService iCustomerService;

	@MockBean
	private CustomerRepository iCustomerRepository;

	@Autowired
	private CustomerController iCustomerController;

	@Autowired
	private Validator iValidator;

	@Test
	void check_that_service_is_not_null() {
		assertThat(iCustomerService).as("Check that service is not null").isNotNull();

	}

	@Test
	void check_that_repository_is_not_null() {
		assertThat(iCustomerRepository).as("Check that repository is not null").isNotNull();
	}

	@Test
	void check_that_customer_dto_has_validation_errors() {
		CustomerDto tCustomerDto = new CustomerDto();
		Set<ConstraintViolation<CustomerDto>> violations = iValidator.validate(tCustomerDto);
		assertThat(violations).as("Check that customerDto has validation errors").isNotNull().isNotEmpty();

	}

	@Test
	void check_that_product_dto_has_no_validation_errors() {
		CustomerDto tCustomerDto = new CustomerDto();
		tCustomerDto.setEmail("marijanovicjosip98@gmail.com");
		tCustomerDto.setFirstName("Josip");
		tCustomerDto.setLastName("Marijanović");
		Set<ConstraintViolation<CustomerDto>> violations = iValidator.validate(tCustomerDto);
		assertThat(violations).as("Check that customerDto has no validation errors").isEmpty();

	}

	@Test
	void proba() {
		CustomerDto tProduct = new CustomerDto();
		assertThatThrownBy(() -> {
			iCustomerService.save(tProduct);
		}).isInstanceOf(ConstraintViolationException.class);
	}
}
