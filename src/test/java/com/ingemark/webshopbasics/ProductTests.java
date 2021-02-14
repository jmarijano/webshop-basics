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
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.ingemark.webshopbasics.controller.ProductController;
import com.ingemark.webshopbasics.dto.ProductDto;
import com.ingemark.webshopbasics.repository.ProductRepository;
import com.ingemark.webshopbasics.service.ProductService;

@SpringBootTest()
public class ProductTests {

	@Autowired
	private ProductService iProductService;

	@MockBean
	private ProductRepository iProductRepository;

	@Autowired
	private ProductController iProductController;

	@Autowired
	private Validator iValidator;

	@Test
	void check_that_service_is_not_null() {
		assertThat(iProductService).as("Check that service is not null").isNotNull();

	}

	@Test
	void check_that_repository_is_not_null() {
		assertThat(iProductRepository).as("Check that repository is not null").isNotNull();
	}

	@Test
	void check_that_product_dto_has_validation_errors() {
		ProductDto tProductDto = new ProductDto();
		Set<ConstraintViolation<ProductDto>> violations = iValidator.validate(tProductDto);
		assertThat(violations).as("Check that productDto has validation errors").isNotNull().isNotEmpty();

	}

	@Test
	void check_that_product_dto_has_no_validation_errors() {
		ProductDto tProductDto = new ProductDto();
		tProductDto.setCode("123456790!");
		tProductDto.setIsAvailable(true);
		tProductDto.setPrice(0.1);
		tProductDto.setName("Product 1");
		Set<ConstraintViolation<ProductDto>> violations = iValidator.validate(tProductDto);
		assertThat(violations).as("Check that productDto has no validation errors").isEmpty();

	}

	@Test
	void proba() {
		ProductDto tProduct = new ProductDto();
		assertThatThrownBy(() -> {
			iProductService.save(tProduct);
		}).isInstanceOf(ConstraintViolationException.class);
	}
}
