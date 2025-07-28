package com.EventsApi.repositories;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.EventsApi.model.event.Event;

public interface EventRepository extends JpaRepository<Event, UUID> {
        @Query("SELECT e FROM Event e LEFT JOIN FETCH e.address a WHERE e.date >= :currentDate ORDER BY e.date ASC")
        public Page<Event> findUpcomingEvents(@Param("currentDate") Date currentDate, Pageable pageable);   

        @Query("SELECT e FROM Event e LEFT JOIN e.address a WHERE " +
                "e.date >= :currentDate AND " +
                "(COALESCE(:title, '') = '' OR e.title LIKE %:title%) AND " +
                "(COALESCE(:city, '') = '' OR a.city LIKE %:city%) AND " +
                "(COALESCE(:uf, '') = '' OR a.uf LIKE %:uf%)" + 
                "ORDER BY e.date ASC")
        Page<Event> findFilteredUpcomingEvents(@Param("currentDate") Date currentDate,
                @Param("title") String title,
                @Param("city") String city,
                @Param("uf") String uf,
                Pageable pageable);
}
