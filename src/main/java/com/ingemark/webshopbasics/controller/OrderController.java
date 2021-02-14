package com.ingemark.webshopbasics.controller;

import javax.validation.Valid;

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
import com.ingemark.webshopbasics.rest.model.RestResponse;
import com.ingemark.webshopbasics.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {

	@Autowired
	private OrderService iOrderService;

	@GetMapping()
	public ResponseEntity<RestResponse> readAllOrders() {
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(iOrderService.findAll()).build(),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestResponse> readOrder(@PathVariable("id") Long pIdOrder) {
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(iOrderService.getOne(pIdOrder)).build(),
				HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<RestResponse> createOrder(@Valid @RequestBody OrderDto pOrderDto) {
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(iOrderService.save(pOrderDto)).build(),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long pIdOrder) {
		iOrderService.delete(pIdOrder);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestResponse> updateOrder(@PathVariable("id") Long pIdOrder,
			@Valid @RequestBody OrderDto pOrderDto) {
		OrderDto tOrderDto = iOrderService.update(pIdOrder, pOrderDto);
		if (tOrderDto.getId().equals(pIdOrder)) {
			return new ResponseEntity<RestResponse>(RestResponse.builder().data(tOrderDto).build(), HttpStatus.OK);
		}
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(tOrderDto).build(), HttpStatus.CREATED);
	}

	@GetMapping("/{id}/finalization")
	public ResponseEntity<RestResponse> finalizeOrder(@PathVariable("id") Long pIdOrder) {
		return new ResponseEntity<RestResponse>(RestResponse.builder().data(iOrderService.finalize(pIdOrder)).build(),
				HttpStatus.OK);

	}
}
