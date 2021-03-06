package com.ingemark.webshopbasics.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "code", length = 10, unique = true, nullable = false)
	private String code;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price_hrk", nullable = false)
	private BigDecimal priceHrk;

	@Column(name = "description")
	private String description;

	@Column(name = "is_available",nullable = false)
	private Boolean isAvailable;

}
