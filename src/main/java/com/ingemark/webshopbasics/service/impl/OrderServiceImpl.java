package com.ingemark.webshopbasics.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingemark.webshopbasics.dto.CustomerDto;
import com.ingemark.webshopbasics.dto.ItemDto;
import com.ingemark.webshopbasics.dto.OrderDto;
import com.ingemark.webshopbasics.enums.OrderStatusEnum;
import com.ingemark.webshopbasics.exception.ResourceNotFoundException;
import com.ingemark.webshopbasics.exception.SystemErrorException;
import com.ingemark.webshopbasics.model.Customer;
import com.ingemark.webshopbasics.model.Order;
import com.ingemark.webshopbasics.model.OrderItem;
import com.ingemark.webshopbasics.model.Product;
import com.ingemark.webshopbasics.repository.OrderRepository;
import com.ingemark.webshopbasics.service.CustomerService;
import com.ingemark.webshopbasics.service.OrderItemService;
import com.ingemark.webshopbasics.service.OrderService;
import com.ingemark.webshopbasics.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository iOrderRepository;

	@Autowired
	private CustomerService iCustomerService;

	@Autowired
	private ProductService iProductService;

	@Autowired
	private OrderItemService iOrderItemService;

	@Autowired
	private EntityManager iEntityManager;

	@Override
	public List<OrderDto> findAll() {
		return iOrderRepository.findAll().stream().map(tOrder -> {
			OrderDto tOrderDto = new OrderDto();
			tOrderDto.setCustomerId(tOrder.getCustomer().getId());
			tOrderDto.setStatus(tOrder.getStatus().getName());
			tOrderDto.setId(tOrder.getId());
			tOrderDto.setProducts(tOrder.getOrderItems().stream().map(tOrderItem -> {
				ItemDto tItemDto = new ItemDto();
				tItemDto.setProductId(tOrderItem.getProduct().getId());
				tItemDto.setQuantity(tOrderItem.getQuantity());
				return tItemDto;
			}).collect(Collectors.toList()));
			return tOrderDto;
		}).collect(Collectors.toList());
	}

	@Override
	public OrderDto getOne(Long pOrderId) {
		return iOrderRepository.findById(pOrderId).map(tOrder -> {
			return new OrderDto();
		}).orElseThrow(() -> new ResourceNotFoundException("resource.not.found"));
	}

	@Override
	public OrderDto save(OrderDto pOrderDto) {
		if (pOrderDto.getProducts().size() == 0) {
			throw new SystemErrorException("empty.order.products");
		}
		CustomerDto tCustomerDto = iCustomerService.getOne(pOrderDto.getCustomerId());
		List<Long> tProductsIds = iProductService.findAllByIds(
				pOrderDto.getProducts().stream().map(tProduct -> tProduct.getProductId()).collect(Collectors.toList()))
				.stream().map(x -> x.getId()).collect(Collectors.toList());
		if (tProductsIds.size() == 0) {
			throw new SystemErrorException("no.order.products.found");
		}

		List<ItemDto> tFilteredItemDtos = pOrderDto.getProducts().stream()
				.filter(x -> x.getQuantity() > 0 && tProductsIds.contains(x.getProductId())).distinct()
				.collect(Collectors.toList());

		Order tOrder = new Order();
		Customer tCustomer = iEntityManager.getReference(Customer.class, tCustomerDto.getId());
		tOrder.setCustomer(tCustomer);
		tOrder.setStatus(OrderStatusEnum.DRAFT);
		tFilteredItemDtos.forEach(x -> {
			OrderItem tOrderItem = new OrderItem();
			tOrderItem.setProduct(iEntityManager.getReference(Product.class, x.getProductId()));
			tOrderItem.setQuantity(x.getQuantity());
			tOrder.addOrderItem(tOrderItem);
		});
		iOrderRepository.save(tOrder);

		return null;
	}

	@Override
	public OrderDto update(Long pOrderId, OrderDto pOrderDto) {
		Optional<Order> tOptionalOrder = iOrderRepository.findById(pOrderId);
		if (tOptionalOrder.isEmpty()) {
			throw new ResourceNotFoundException("resource.not.found");
		}
		Order tOrder = tOptionalOrder.get();
		if (!pOrderDto.getProducts().isEmpty()) {
			tOrder.getOrderItems().clear();
			List<Long> tProductsIds = iProductService.findAllByIds(pOrderDto.getProducts().stream()
					.map(tProduct -> tProduct.getProductId()).collect(Collectors.toList())).stream().map(x -> x.getId())
					.collect(Collectors.toList());
			List<ItemDto> tFilteredItemDtos = pOrderDto.getProducts().stream()
					.filter(x -> x.getQuantity() > 0 && tProductsIds.contains(x.getProductId())).distinct()
					.collect(Collectors.toList());
			tFilteredItemDtos.forEach(x -> {
				OrderItem tOrderItem = new OrderItem();
				tOrderItem.setProduct(iEntityManager.getReference(Product.class, x.getProductId()));
				tOrderItem.setQuantity(x.getQuantity());
				tOrder.addOrderItem(tOrderItem);
			});
		} else {
			tOrder.getOrderItems().clear();
		}
		iOrderRepository.save(tOrder);
		return null;

	}

	@Override
	public void delete(Long pOrderId) {
		try {
			iOrderRepository.deleteById(pOrderId);
		} catch (Exception e) {
		}

	}

	@Override
	public OrderDto finalize(Long pOrderId) {
		Optional<Order> tOptionalOrder = iOrderRepository.findById(pOrderId);
		if (tOptionalOrder.isEmpty()) {
			throw new ResourceNotFoundException("resource.not.found");
		}
		Order tOrder = tOptionalOrder.get();
		tOrder.setStatus(OrderStatusEnum.SUBMITTED);
		tOrder.setTotalPriceHrk(iOrderItemService.selectTotalByOrderId(pOrderId));
		return null;
	}

}
