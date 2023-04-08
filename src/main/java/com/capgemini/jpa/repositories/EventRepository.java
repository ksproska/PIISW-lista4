package com.capgemini.jpa.repositories;

import com.capgemini.jpa.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findEventsByTimeBetweenAndAnalysisRequiredEquals(
            LocalDateTime start, LocalDateTime end, boolean toBeAnalyzed, Pageable pageable
    );
}
