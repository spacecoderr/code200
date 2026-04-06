package com.company.HospitalManagement.CalendarApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventJpaRepository eventRepository;

    @GetMapping("/allevents")
    public List<Event> allEvents() {
        return eventRepository.findAll();
    }

    @PostMapping("/event")
    public Event addEvent(@RequestBody Event event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        event.setName(authentication.getName());
        return eventRepository.save(event);
    }

    // Add the rest of your mapping methods (PATCH, DELETE, GET /events) here...
}