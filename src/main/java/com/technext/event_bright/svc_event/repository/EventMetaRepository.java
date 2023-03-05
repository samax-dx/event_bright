package com.technext.event_bright.svc_event.repository;

import com.technext.event_bright.svc_event.entity.EventMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventMetaRepository extends JpaRepository<EventMeta, Long> {
}
