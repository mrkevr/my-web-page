package com.mrkevr.customer.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrkevr.customer.entity.Customer;
import com.mrkevr.customer.exception.CustomerNotFoundException;
import com.mrkevr.customer.model.assembler.CustomerModelAssembler;
import com.mrkevr.customer.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("customers")
public class CustomerController {

	private final CustomerRepository customerRepo;
	private final CustomerModelAssembler customerAssembler;
	
	// Aggregate root
	@GetMapping
	public CollectionModel<EntityModel<Customer>> all() {
		
		List<EntityModel<Customer>> customerList = customerRepo.findAll().stream()
				.map(c -> customerAssembler.toModel(c))
				.collect(Collectors.toList());
		
		return CollectionModel.of(customerList, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<Customer> one(@PathVariable Long id) {

		Customer customer = customerRepo.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

		return customerAssembler.toModel(customer);
	}

	@PostMapping
	public EntityModel<Customer> add(@RequestBody Customer customer) {

		Customer newCustomer = customerRepo.save(customer);

		return customerAssembler.toModel(newCustomer);
	}

	@PutMapping("/{id}")
	public EntityModel<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {

		Customer updateCustomer = customerRepo.findById(id).orElse(new Customer());

		updateCustomer.setName(customer.getName());
		updateCustomer.setPhone(customer.getPhone());
		updateCustomer.setEmail(customer.getEmail());
		updateCustomer.setBalance(customer.getBalance());
		updateCustomer.setLastUpdated(LocalDate.now());

		Customer updatedCustomer = customerRepo.save(updateCustomer);

		return customerAssembler.toModel(updatedCustomer);
	}

	@DeleteMapping("/delete/{id}")
	public EntityModel<Void> delete(@PathVariable Long id) {
		customerRepo.deleteById(id);

		return EntityModel.of(null);
	}
}
