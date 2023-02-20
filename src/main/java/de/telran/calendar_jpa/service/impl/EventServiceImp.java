package de.telran.calendar_jpa.service.impl;

import de.telran.calendar_jpa.domain.Event;
import de.telran.calendar_jpa.repositories.EventRepository;
import de.telran.calendar_jpa.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImp implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImp(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event save(Event newEvent) {
        return eventRepository.save(newEvent);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return eventRepository.existsById(id);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
}
