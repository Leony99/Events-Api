package com.EventsApi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EventsApi.domain.address.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
