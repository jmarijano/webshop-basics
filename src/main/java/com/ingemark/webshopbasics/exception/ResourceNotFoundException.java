package com.ingemark.webshopbasics.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5877960981441471995L;

	public ResourceNotFoundException(String pMsg) {
		super(pMsg);
	}
}
