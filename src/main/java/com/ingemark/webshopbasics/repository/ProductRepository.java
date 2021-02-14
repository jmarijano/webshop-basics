package com.ingemark.webshopbasics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingemark.webshopbasics.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByIdInAndIsAvailable(List<Long> pIds, Boolean pIsAvailable);
}
