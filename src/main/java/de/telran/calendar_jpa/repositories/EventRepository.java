package de.telran.calendar_jpa.repositories;

import de.telran.calendar_jpa.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
