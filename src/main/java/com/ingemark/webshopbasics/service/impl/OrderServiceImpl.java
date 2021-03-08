package com.ingemark.webshopbasics.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ingemark.webshopbasics.dto.CustomerDto;
import com.ingemark.webshopbasics.dto.ItemDto;
import com.ingemark.webshopbasics.dto.OrderDto;
import com.ingemark.webshopbasics.enums.OrderStatusEnum;
import com.ingemark.webshopbasics.exception.ResourceNotFoundException;
import com.ingemark.webshopbasics.exception.SystemErrorException;
import com.ingemark.webshopbasics.hnb.client.HnbResponseObject;
import com.ingemark.webshopbasics.model.Customer;
import com.ingemark.webshopbasics.model.Order;
import com.ingemark.webshopbasics.model.OrderItem;
import com.ingemark.webshopbasics.model.Product;
import com.ingemark.webshopbasics.repository.OrderRepository;
import com.ingemark.webshopbasics.service.CustomerService;
import com.ingemark.webshopbasics.service.OrderItemService;
import com.ingemark.webshopbasics.service.OrderService;
import com.ingemark.webshopbasics.service.ProductService;
import com.ingemark.webshopbasics.utils.OrderMapper;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger iLogger = LoggerFactory.getLogger(OrderServiceImpl.class);

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

	@Value("${hnb.tecajna.lista.url}")
	private String iHnbUri;

	@Override
	public List<OrderDto> findAll() {
		return iOrderRepository.findAll().stream().map(tOrder -> OrderMapper.mapOrderToOrderDto(tOrder))
				.collect(Collectors.toList());
	}

	@Override
	public OrderDto getOne(Long pOrderId) {
		return iOrderRepository.findById(pOrderId).map(tOrder -> OrderMapper.mapOrderToOrderDto(tOrder))
				.orElseThrow(() -> new ResourceNotFoundException("resource.not.found"));
	}

	@Override
	public OrderDto save(OrderDto pOrderDto) {
		if (pOrderDto.getProducts().size() == 0) {
			throw new SystemErrorException("empty.order.products");
		}
		CustomerDto tCustomerDto = iCustomerService.getOne(pOrderDto.getCustomerId());
		List<Long> tProductsIds = iProductService.findAllByIdInAndIsAvailable(
				pOrderDto.getProducts().stream().map(tProduct -> tProduct.getProductId()).collect(Collectors.toList()),
				true).stream().map(x -> x.getId()).collect(Collectors.toList());
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

		return OrderMapper.mapOrderToOrderDto(iOrderRepository.save(tOrder));
	}

	@Override
	public OrderDto update(Long pOrderId, OrderDto pOrderDto) {
		Optional<Order> tOptionalOrder = iOrderRepository.findById(pOrderId);
		if (tOptionalOrder.isEmpty()) {
			throw new ResourceNotFoundException("resource.not.found");
		}
		Order tOrder = tOptionalOrder.get();
		if (tOrder.getStatus().equals(OrderStatusEnum.SUBMITTED)) {
			throw new SystemErrorException("order.already.submitted");
		}
		if (!pOrderDto.getProducts().isEmpty()) {
			tOrder.getOrderItems().clear();
			List<Long> tProductsIds = iProductService
					.findAllByIdInAndIsAvailable(pOrderDto.getProducts().stream()
							.map(tProduct -> tProduct.getProductId()).collect(Collectors.toList()), true)
					.stream().map(x -> x.getId()).collect(Collectors.toList());
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

		return OrderMapper.mapOrderToOrderDto(iOrderRepository.save(tOrder));

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
		BigDecimal tTotalPriceHrk = iOrderItemService.selectTotalByOrderId(pOrderId);
		tOrder.setTotalPriceHrk(tTotalPriceHrk);
		RestTemplate tRestTemplate = new RestTemplate();
		URI tTargetUri = UriComponentsBuilder.fromUriString(iHnbUri).queryParam("valuta", "EUR").build().toUri();

		ResponseEntity<HnbResponseObject[]> tResponseEntityResponse = tRestTemplate.getForEntity(tTargetUri,
				HnbResponseObject[].class);
		if (!tResponseEntityResponse.hasBody()) {
			throw new SystemErrorException("no.body.hnb.response");
		}
		HnbResponseObject[] tResponse = tResponseEntityResponse.getBody();
		if (tResponse == null || tResponse.length == 0 || tResponse.length > 1) {
			throw new SystemErrorException("wrong.hnb.response");
		}

		BigDecimal tKupovniTecaj = new BigDecimal(tResponse[0].getKupovniZaDevize().replace(",", "."));
		tOrder.setTotalPriceEur(tTotalPriceHrk.divide(tKupovniTecaj, MathContext.DECIMAL128));

		return OrderMapper.mapOrderToOrderDto(iOrderRepository.save(tOrder));
	}

}
