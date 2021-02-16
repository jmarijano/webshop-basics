package com.ingemark.webshopbasics.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ingemark.webshopbasics.enums.OrderStatusEnum;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity(name = "webshop_order")
@Table(name = "webshop_order")
@Data
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Customer customer;

	@Enumerated(EnumType.ORDINAL)
	private OrderStatusEnum status;

	@Column(name = "total_price_hrk")
	private BigDecimal totalPriceHrk;

	@Column(name = "total_price_eur")
	private BigDecimal totalPriceEur;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

	public void addOrderItem(OrderItem pOrderItem) {
		orderItems.add(pOrderItem);
		pOrderItem.setOrder(this);
	}
}
