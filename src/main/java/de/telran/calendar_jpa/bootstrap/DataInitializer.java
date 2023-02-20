package de.telran.calendar_jpa.bootstrap;

import de.telran.calendar_jpa.domain.Event;
import de.telran.calendar_jpa.repositories.EventRepository;
import org.hibernate.annotations.Comment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EventRepository eventRepository;

    public DataInitializer(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Event event1 = new Event("Event1", "Berlin", "Metting",
                LocalDateTime.of(2023, 2, 20, 12, 30),
                LocalDateTime.of(2023, 2, 20, 13, 30));
        eventRepository.save(event1);

        Event event2 = new Event("Event2", "Kiev", "Class",
                LocalDateTime.of(2023, 2, 22, 10, 00),
                LocalDateTime.of(2023, 2, 22, 11, 00));
        eventRepository.save(event2);


    }
}
