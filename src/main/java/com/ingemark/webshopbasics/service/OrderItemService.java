package com.ingemark.webshopbasics.service;

import java.math.BigDecimal;

public interface OrderItemService {

	BigDecimal selectTotalByOrderId(Long pIdOrder);
}
