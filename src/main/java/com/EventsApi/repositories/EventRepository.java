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
    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.address a WHERE e.date >= CURRENT_TIMESTAMP ORDER BY e.date ASC")
    public Page<Event> findUpcomingEvents(@Param("CurrentDate") Date currentDate, Pageable pageable);   

    @Query("SELECT e FROM Event e " +
            "LEFT JOIN e.address a " +
            "WHERE e.date >= :CurrentDate AND " +
            "(:title IS NULL OR e.title LIKE %:title% AND " +
            "(:city IS NULL OR a.city LIKE %:city%) AND " +
            "(:uf IS NULL OR a.uf LIKE %:uf%) AND " +
            "(:startDate IS NULL OR e.date >= :startDate) AND " +
            "(:endDate IS NULL OR e.date <= :endDate))")
    Page<Event> findFilteredEvents(@Param("CurrentDate") Date currentDate,
                                   @Param("title") String title,
                                   @Param("city") String city,
                                   @Param("uf") String uf,
                                   @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate,
                                   Pageable pageable);
}
