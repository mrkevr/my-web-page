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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrkevr.customer.entity.Parcel;
import com.mrkevr.customer.entity.Status;
import com.mrkevr.customer.exception.ParcelNotFoundException;
import com.mrkevr.customer.model.assembler.ParcelModelAssembler;
import com.mrkevr.customer.repository.ParcelRepository;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RestController
@RequestMapping("parcels")
public class ParcelController implements ResponseEntityController<Parcel>{
	
	private final ParcelRepository parcelRepo;
	private final ParcelModelAssembler parcelAssembler;
	
	//ROOT
	@Override
	@GetMapping
	public ResponseEntity<?> all() {
		
		List<EntityModel<Parcel>> list = parcelRepo.findAll().stream()
				.map(p -> parcelAssembler.toModel(p))
				.collect(Collectors.toList());
		
		CollectionModel<EntityModel<Parcel>> model = CollectionModel.of(list, linkTo(methodOn(ParcelController.class).all()).withSelfRel());
		
		return ResponseEntity
				.created(model.getRequiredLink(IanaLinkRelations.SELF)
						.toUri())
						.body(model);
	}
	
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> one(@PathVariable Long id) {
		
		EntityModel<Parcel> model = parcelAssembler.toModel(parcelRepo.findById(id)
				.orElseThrow(() -> new ParcelNotFoundException(id)));

		return ResponseEntity
				.created(model.getRequiredLink(IanaLinkRelations.SELF)
						.toUri())
						.body(model);
	}
	
	@Override
	public ResponseEntity<?> save(Parcel parcel) {
		
		parcel.setStatus(Status.PROCESSING);
		EntityModel<Parcel> model = parcelAssembler.toModel(parcelRepo.save(parcel));
		
		return ResponseEntity
				.created(model.getRequiredLink(IanaLinkRelations.SELF)
						.toUri())
						.body(model);
	}
	
	@Override
	public ResponseEntity<?> update(Long id, Parcel parcel) {
		Parcel updateParcel = parcelRepo.findById(id).orElse(new Parcel());
		
		//id, description, sender, receiver, address, status
		updateParcel.setDescription(parcel.getDescription());
		updateParcel.setSender(parcel.getSender());
		updateParcel.setReceiver(parcel.getReceiver());
		updateParcel.setAddress(parcel.getAddress());
		updateParcel.setStatus(parcel.getStatus());
		updateParcel.setLastUpdated(LocalDate.now());

		Parcel updatedParcel = parcelRepo.save(updateParcel);
		EntityModel<Parcel> model = parcelAssembler.toModel(updatedParcel);

		return ResponseEntity
				.created(model.getRequiredLink(IanaLinkRelations.SELF)
						.toUri())
						.body(model);
	}
	
	@Override
	public ResponseEntity<?> delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
