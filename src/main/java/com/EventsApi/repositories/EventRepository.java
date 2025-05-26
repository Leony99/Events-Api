package com.EventsApi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EventsApi.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, UUID> {

}
