package com.mrkevr.customer.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrkevr.customer.entity.Customer;
import com.mrkevr.customer.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AppTransactionController {

	private final CustomerRepository customerRepo;

//	@PutMapping("/payment/{id}/{amount}")
//	private ResponseEntity<Customer> payBalance(@PathVariable int id, @PathVariable BigDecimal amount) {
//		try {
//			Customer customer = customerRepo.findById(id).get();
//			customer.setBalance(customer.getBalance().subtract(amount));
//			Customer updatedCustomer = customerRepo.save(customer);
//			return ResponseEntity.ok(updatedCustomer);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().build();
//		}
//	}

	@PutMapping("/payment/{id}/{amount}")
	private ResponseEntity<Void> payBalance(@PathVariable Long id, @PathVariable BigDecimal amount) {
		
		Optional<Customer> opt = customerRepo.findById(id);
		
		if(opt.isEmpty()) { // presence check
			return ResponseEntity.badRequest().build();
		}
		if(opt.get().getBalance().compareTo(amount) < 0) { // balance < payment
			return ResponseEntity.badRequest().build();
		}
		
		try {
			Customer customer = opt.get();
			BigDecimal newBalance =  customer.getBalance().subtract(amount);
			customer.setBalance(newBalance);
			customer.setLastUpdated(LocalDate.now());
			customerRepo.save(customer);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
}
