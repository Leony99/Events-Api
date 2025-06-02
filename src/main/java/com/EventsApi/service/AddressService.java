package com.EventsApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventsApi.model.address.Address;
import com.EventsApi.model.event.Event;
import com.EventsApi.model.event.EventRequestDTO;
import com.EventsApi.repositories.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(EventRequestDTO data, Event event) {
        Address newAddress = new Address();
        newAddress.setCity(data.city());
        newAddress.setUf(data.uf());
        newAddress.setEvent(event);
        
        addressRepository.save(newAddress);
        return newAddress;
    }

}
