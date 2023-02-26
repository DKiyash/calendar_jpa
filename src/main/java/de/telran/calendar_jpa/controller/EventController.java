package de.telran.calendar_jpa.controller;

import de.telran.calendar_jpa.domain.Event;
import de.telran.calendar_jpa.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //Получение события по ID
    @GetMapping("/{id}")
    ResponseEntity<?> getEventById(@PathVariable Long id) {
        Optional<Event> result = eventService.findById(id);
        //Если событие есть, то вернуть его
        if (result.isPresent()){
            Event event = result.get();
            return ResponseEntity.ok(event);
        }
        //Если события нет, то вернуть "NOT_FOUND"
        else return ResponseEntity.notFound().build();
    }

    //Получение списка всех событий
    @GetMapping()
    ResponseEntity<?> getAllEvents() {
        //Если список событий не пустой, то вернуть его
        List<Event> eventList = eventService.findAll();
        if (!eventList.isEmpty()){
            return ResponseEntity.ok(eventList);
        }
        //Если список событий пустой, то вернуть "NOT_FOUND"
        else return ResponseEntity.notFound().build();
    }

    //Создание нового события или изменение существующего
    @PostMapping()
    ResponseEntity<?> createEvent(@RequestBody Event newEvent) {
        //Если новое событие добавлено, то вернуть код 201 и location
        Event event = eventService.save(newEvent);
        if (event != null){
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(event.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
        //Если новое событие не добавлено, то вернуть "500 Internal Server Error"
        else {
            return ResponseEntity.internalServerError().build();
        }
//        Варианты ответов
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
  }

    //Обновление существующего события
    @PutMapping("/{id}")
    ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event newEvent) {
        //Если событие есть в базе, то обновить его
        if (eventService.existsById(id)){
            Event event=eventService.save(newEvent);
            return ResponseEntity.ok(event);
        }
        //Если события нет, то вернуть "NOT_FOUND"
        else return ResponseEntity.notFound().build();
    }

    //Удаление события по ID
    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        //Если событие есть в базе, то удалить его
        if (eventService.existsById(id)){
            eventService.deleteById(id);
            return ResponseEntity.ok().build();
            //еще вариант ответа
//          return new ResponseEntity<>(HttpStatus.OK);
        }
        //Если события нет, то вернуть "NOT_FOUND"
        else return ResponseEntity.notFound().build();
        //еще вариант ответа
//        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}


/*
    @PostMapping(value = "/events")
    public ResponseEntity<?> create(@RequestBody Event event){
        eventService.create(event);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/events/{id}")
    public ResponseEntity<Event> read(@PathVariable(name = "id") int id) {
        final Event event = eventService.read(id);
        return event != null
                ? new ResponseEntity<>(event, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/events/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Event event) {
        final boolean updated = eventService.update(event, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/events/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        final boolean deleted = eventService.delete(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

 */


/*
ChatGPT по запросу

@GetMapping("/api/v1/getall")
public ResponseEntity<List<Event>> getAll() {
    List<Event> events = eventService.findAll();
    if (events.isEmpty()) {
        return ResponseEntity.notFound().build();
    } else {
        return ResponseEntity.ok(events);
    }
}
 */

/*
Задание 1: Написать EventService, в котором должны быть методы создание,
получения по id, получения списком по фильтру, удаления, замены и модификации событий
Эти методы должны взаимодействовать со списком событий,
который так же должен находиться в классе сервиса

Задание 2: Привести контроллер в форму, соответствующую примеру на гите

Задание 3: Покрыть контроллеры тестами по образцу из гита

 */