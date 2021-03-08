package com.ingemark.webshopbasics.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ingemark.webshopbasics.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	@Query(nativeQuery = true)
	Optional<BigDecimal> selectTotalByOrderId(Long pIdOrder);
}
