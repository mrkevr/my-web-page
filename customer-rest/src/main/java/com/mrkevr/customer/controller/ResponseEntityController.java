package com.mrkevr.customer.controller;

import org.springframework.http.ResponseEntity;

public interface ResponseEntityController<T> {

	ResponseEntity<?> all();

	ResponseEntity<?> one(Long id);

	ResponseEntity<?> save(T object);

	ResponseEntity<?> update(Long id, T object);

	ResponseEntity<?> delete(Long id);

}
