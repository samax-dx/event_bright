package com.technext.event_bright.svc_event.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.technext.event_bright.svc_auth.annotations.Authorize;
import com.technext.event_bright.svc_event.dto.EventParticipationData;
import com.technext.event_bright.svc_event.entity.Event;
import com.technext.event_bright.svc_event.entity.EventMeta;
import com.technext.event_bright.svc_event.entity.EventParticipant;
import com.technext.event_bright.svc_event.entity.User;
import com.technext.event_bright.svc_event.dto.EventDetails;
import com.technext.event_bright.svc_event.repository.EventMetaRepository;
import com.technext.event_bright.svc_event.repository.EventParticipantRepository;
import com.technext.event_bright.svc_event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/Event")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final EventMetaRepository eventMetaRepository;
    private final EventParticipantRepository eventParticipantRepository;

    @Authorize
    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<Event> create(@RequestBody EventDetails eventDetails, @ModelAttribute User user) {
        Event event = new ObjectMapper().convertValue(eventDetails, Event.class);
        event.setUser(user);
        eventRepository.save(event);

        EventMeta eventMeta = new EventMeta();
        eventMeta.setEvent(event);
        eventMetaRepository.save(eventMeta);

        return ResponseEntity.ok(event);
    }

    @Authorize
    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/getCurrentUserEvents",
            method = RequestMethod.GET,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public List<Event> getCurrentUserEvents(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "100") int limit,
                                            @RequestParam(defaultValue = "date.asc") String order,
                                            @RequestParam(defaultValue = "%") String eventName,
                                            @ModelAttribute User user) {
        Sort sort = order.endsWith(".asc") ? Sort.by(order.replace(".asc", "")).ascending() : Sort.by(order.replace(".desc", "")).descending();
        return eventRepository.findEventsByUserIdAndName(user.getUserId(), eventName, PageRequest.of(page, limit, sort));
    }

    @Authorize
    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/getCurrentUserEventAndParticipants",
            method = RequestMethod.GET,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object getCurrentUserEventAndParticipants(@RequestParam Long eventId,
                                                      @RequestParam(defaultValue = "%") String participantName,
                                                      @ModelAttribute User user) {
        Event event = eventRepository.findEventByEventAndUserId(eventId, user.getUserId()).orElse(null);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ImmutableMap.of("event", event, "participants", eventParticipantRepository.findParticipantsByNameAndEventId(participantName, eventId));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/getEvents",
            method = RequestMethod.GET,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public Object getEvents(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "100") int limit,
                            @RequestParam(defaultValue = "date.asc") String order,
                            @RequestParam(defaultValue = "%") String eventName,
                            @ModelAttribute User user) {
        Sort sort = order.endsWith(".asc") ? Sort.by(order.replace(".asc", "")).ascending() : Sort.by(order.replace(".desc", "")).descending();
        return eventRepository.findEventsByUserName(eventName, PageRequest.of(page, limit, sort));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/participateEvent",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<EventParticipant> participateEvent(@RequestBody EventParticipationData participationData) {
        Event event = eventRepository.findById(participationData.getEventId()).orElse(null);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setEvent(event);
        eventParticipant.setName(participationData.getParticipantName());
        eventParticipant.setEmail(participationData.getParticipantEmail());
        eventParticipantRepository.save(eventParticipant);

        EventMeta eventMeta = event.getEventMeta();
        eventMeta.setParticipantCount(eventMeta.getParticipantCount() + 1);
        eventMetaRepository.save(eventMeta);

        return ResponseEntity.ok(eventParticipant);
    }

}
