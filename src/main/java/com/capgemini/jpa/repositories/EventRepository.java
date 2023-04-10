package com.capgemini.jpa.repositories;

import com.capgemini.jpa.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findEventsByTimeBetweenAndAnalysisRequiredEquals(
            LocalDateTime start, LocalDateTime end, boolean toBeAnalyzed, Pageable pageable
    );

    void deleteByTimeBefore(LocalDateTime givenDate);

    @Modifying
    @Query("update Event e set e.analysisRequired = true where type (e) = ?1")
    void queryAllByDurationGreaterThan(Class<? extends Event> clazz, int duration);

    @Query(value =
        "SELECT new com.capgemini.jpa.repositories.ServerStatistic(e.server, count (e)) FROM Event e GROUP BY e.server"
    )
    List<ServerStatistic> groupByEventWithJPQL();

}
