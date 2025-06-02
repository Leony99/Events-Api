package com.EventsApi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EventsApi.model.address.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
