package com.ingemark.webshopbasics.exception;

public class SystemErrorException extends RuntimeException {

	private static final long serialVersionUID = 6670133388680883652L;

	public SystemErrorException(String pMsg) {
		super(pMsg);
	}
}
