package com.ingemark.webshopbasics.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "product")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "code", length = 10, unique = true)
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "price_hrk")
	private Double priceHrk;

	@Column(name = "description")
	private String description;

	@Column(name = "is_available")
	private Boolean isAvailable;

}
