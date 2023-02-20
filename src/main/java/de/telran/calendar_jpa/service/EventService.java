package de.telran.calendar_jpa.service;

import de.telran.calendar_jpa.domain.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Event save(Event newEvent);

    List<Event> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<Event> findById(Long id);
}
