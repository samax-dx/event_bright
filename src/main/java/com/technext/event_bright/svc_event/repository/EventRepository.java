package com.technext.event_bright.svc_event.repository;

import com.technext.event_bright.svc_event.entity.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e where e.name like ?1")
    List<Event> findEventsByUserName(String name, Pageable pageable);
    @Query("select e from Event e where e.user.userId = ?1 and e.name like ?2")
    List<Event> findEventsByUserIdAndName(long userId, String name, Pageable pageable);

    @Query("select e from Event e where e.eventId=?1 and e.user.userId = ?2")
    Optional<Event> findEventByEventAndUserId(Long eventId, Long userId);
}
