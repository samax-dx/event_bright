package com.technext.event_bright.svc_event.repository;

import com.technext.event_bright.svc_event.entity.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    @Query("select ep from EventParticipant ep where ep.name like ?1 and ep.event.eventId=?2")
    List<EventParticipant> findParticipantsByNameAndEventId(String participantName, Long eventId);
}
