package com.mrkevr.customer.exception;

public class ParcelNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1021486629337494866L;
	
	public ParcelNotFoundException(Long id) {
		super("Could not find parcel with id number " + id);
	}

}
