package com.mrkevr.customer.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.mrkevr.customer.controller.CustomerController2;
import com.mrkevr.customer.entity.Customer;

@Component
public class CustomerModelAssembler2 implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

	@Override
	public EntityModel<Customer> toModel(Customer customer) {

		return EntityModel.of(
				customer,
				linkTo(methodOn(CustomerController2.class).one(customer.getId())).withSelfRel(),
				linkTo(methodOn(CustomerController2.class).all()).withRel("customers"));
	}
}
