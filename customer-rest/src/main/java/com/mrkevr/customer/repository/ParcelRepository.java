package com.mrkevr.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrkevr.customer.entity.Parcel;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {

}
