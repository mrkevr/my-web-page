package com.mrkevr.customer.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
import com.mrkevr.customer.model.assembler.CustomerModelAssembler2;
import com.mrkevr.customer.repository.CustomerRepository;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RestController
@AllArgsConstructor
@RequestMapping("customers2")
public class CustomerController2 implements ResponseEntityController<Customer>{

	private final CustomerRepository customerRepo;
	private final CustomerModelAssembler2 customerAssembler;
	
	// ROOT
	@GetMapping
	public ResponseEntity<?> all() {
		
		List<EntityModel<Customer>> customerList = customerRepo.findAll().stream()
				.map(c -> customerAssembler.toModel(c))
				.collect(Collectors.toList());
		
		CollectionModel<EntityModel<Customer>> model = CollectionModel.of(customerList, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
		
		return ResponseEntity
				.created(model.getRequiredLink(IanaLinkRelations.SELF)
						.toUri())
						.body(model);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> one(@PathVariable Long id) {

		EntityModel<Customer> model = customerAssembler.toModel(customerRepo.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException(id)));

		return ResponseEntity
				.created(model.getRequiredLink(IanaLinkRelations.SELF)
						.toUri())
						.body(model);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Customer customer) {
		
		EntityModel<Customer> model = customerAssembler.toModel(customerRepo.save(customer));
		
		return ResponseEntity
				.created(model.getRequiredLink(IanaLinkRelations.SELF)
						.toUri())
						.body(model);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Customer customer) {

		Customer updateCustomer = customerRepo.findById(id).orElse(new Customer());

		updateCustomer.setName(customer.getName());
		updateCustomer.setPhone(customer.getPhone());
		updateCustomer.setEmail(customer.getEmail());
		updateCustomer.setBalance(customer.getBalance());
		updateCustomer.setLastUpdated(LocalDate.now());

		Customer updatedCustomer = customerRepo.save(updateCustomer);
		EntityModel<Customer> model = customerAssembler.toModel(updatedCustomer);

		return ResponseEntity
				.created(model.getRequiredLink(IanaLinkRelations.SELF)
						.toUri())
						.body(model);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		customerRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
