package com.ingemark.webshopbasics.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingemark.webshopbasics.dto.OrderDto;
import com.ingemark.webshopbasics.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {

	@Autowired
	private OrderService iOrderService;

	@GetMapping()
	public ResponseEntity<List<OrderDto>> readAllOrders() {
		return new ResponseEntity<List<OrderDto>>(iOrderService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDto> readOrder(@PathVariable("id") Long pIdOrder) {
		return new ResponseEntity<OrderDto>(iOrderService.getOne(pIdOrder), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto pOrderDto) {
		return new ResponseEntity<OrderDto>(iOrderService.save(pOrderDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long pIdOrder) {
		iOrderService.delete(pIdOrder);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OrderDto> updateOrder(@PathVariable("id") Long pIdOrder,
			@Valid @RequestBody OrderDto pOrderDto) {
		OrderDto tOrderDto = iOrderService.update(pIdOrder, pOrderDto);
		if (tOrderDto.getId().equals(pIdOrder)) {
			return new ResponseEntity<OrderDto>(tOrderDto, HttpStatus.OK);
		}
		return new ResponseEntity<OrderDto>(tOrderDto, HttpStatus.CREATED);
	}

	@GetMapping("/{id}/finalization")
	public ResponseEntity<OrderDto> finalizeOrder(@PathVariable("id") Long pIdOrder) {
		return new ResponseEntity<OrderDto>(iOrderService.finalize(pIdOrder), HttpStatus.OK);

	}
}
