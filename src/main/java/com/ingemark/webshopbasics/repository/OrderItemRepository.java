package com.ingemark.webshopbasics.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ingemark.webshopbasics.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	@Query(nativeQuery = true, value = "select \r\n" + "	SUM(p.price_hrk * oi.quantity)\r\n" + "from \r\n"
			+ "	webshop_order wo \r\n" + "	inner join order_item oi on wo.id = oi.order_id \r\n"
			+ "	inner join product p on oi.product_id = p.id \r\n" + "where\r\n" + "	wo.id =?1")
	Optional<BigDecimal> selectTotalByOrderId(Long pIdOrder);
}
