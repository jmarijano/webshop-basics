package com.ingemark.webshopbasics.enums;

public enum OrderStatusEnum {

	DRAFT(1, "DRAFT"), SUBMITTED(2, "SUBMITTED");

	private Integer code;
	private String name;

	private OrderStatusEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
