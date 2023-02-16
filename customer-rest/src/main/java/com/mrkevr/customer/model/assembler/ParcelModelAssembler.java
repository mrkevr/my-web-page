package com.mrkevr.customer.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.mrkevr.customer.controller.ParcelController;
import com.mrkevr.customer.entity.Parcel;

@Component
public class ParcelModelAssembler implements RepresentationModelAssembler<Parcel, EntityModel<Parcel>> {

	@Override
	public EntityModel<Parcel> toModel(Parcel parcel) {
		
		return EntityModel.of(
				parcel,
				linkTo(methodOn(ParcelController.class).one(parcel.getId())).withSelfRel(),
				linkTo(methodOn(ParcelController.class).all()).withRel("customers")
				
				
				
				);
		
	}

}
