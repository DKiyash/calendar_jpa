package de.telran.calendar_jpa.controller;

import de.telran.calendar_jpa.domain.Event;
import de.telran.calendar_jpa.service.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = EventController.class)
class EventControllerTest {
    static final String API_PATH = "/api/v1/events";


    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventService eventService;

    //Тестирование метода GetByIdTest
    @Nested
    class GetByIdTest{
        @DisplayName("getEventById found and return Event and Response 200 (OK)")
        @Test
        void getEventByIdTest_ShouldReturnEventAndStatus200() throws Exception { //HTTP запрос - /1
            Event event = new Event();
            event.setId(1L);
            event.setName("Test Event");
            event.setDescription("Meeting");
            event.setLocation("Berlin");

            Mockito.when(eventService.findById(1L)).thenReturn(Optional.of(event));

            mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value("1"))
                    .andExpect(jsonPath("$.name").value("Test Event"))
                    .andExpect(jsonPath("$.description").value("Meeting"))
                    .andExpect(jsonPath("$.location").value("Berlin"))
                    .andDo(MockMvcResultHandlers.print());
            //Проверка, что метод вызывался только 1 раз
            Mockito.verify(eventService, Mockito.times(1)).findById(1L);
        }

        @DisplayName("getEventById notfound and return 404 (NOT_FOUND)")
        @Test
        public void getEventByIdTest_ShouldReturnNotFound() throws Exception {
            Mockito.when(eventService.findById(1L)).thenReturn(Optional.empty());
            mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/1"))
                    .andExpect(status().isNotFound())
                    .andDo(MockMvcResultHandlers.print());
            //Проверка, что метод вызывался только 1 раз
            Mockito.verify(eventService, Mockito.times(1)).findById(1L);
        }
    }

    //Тестирование метода getAllEvent
    @Nested
    class getAllEventsTest{
        @DisplayName("getEventById found and return list of Events and Response 200 (OK)")
        @Test
        void getAllEventsTest_ShouldReturnListOfEventsAndStatus200() throws Exception { //HTTP запрос - empty
            List<Event> eventList = new ArrayList<>();
            for (long i = 0; i < 2; i++) {
                Event event = new Event();
                event.setId(i);
                event.setName("Test Event" + i);
                event.setDescription("Meeting" + i);
                event.setLocation("Berlin" + i);
                eventList.add(event);
            }
            Mockito.when(eventService.findAll()).thenReturn(eventList);

            mockMvc.perform(MockMvcRequestBuilders.get(API_PATH))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id").value(eventList.get(0).getId()))
                    .andExpect(jsonPath("$[0].name").value(eventList.get(0).getName()))
                    .andExpect(jsonPath("$[0].description").value(eventList.get(0).getDescription()))
                    .andExpect(jsonPath("$[0].location").value(eventList.get(0).getLocation()))
                    .andExpect(jsonPath("$[1].id").value(eventList.get(1).getId()))
                    .andExpect(jsonPath("$[1].name").value(eventList.get(1).getName()))
                    .andExpect(jsonPath("$[1].description").value(eventList.get(1).getDescription()))
                    .andExpect(jsonPath("$[1].location").value(eventList.get(1).getLocation()))
                    .andDo(MockMvcResultHandlers.print());
            //Проверка, что метод вызывался только 1 раз
            Mockito.verify(eventService, Mockito.times(1)).findAll();
        }

        @DisplayName("getAllEvents notfound and returned 404 (NOT_FOUND)")
        @Test
        public void getAllEventsTest_ShouldReturnNotFound() throws Exception {
            List<Event> eventList = new ArrayList<>();
            Mockito.when(eventService.findAll()).thenReturn(eventList);
            mockMvc.perform(MockMvcRequestBuilders.get(API_PATH))
                    .andExpect(status().isNotFound())
                    .andDo(MockMvcResultHandlers.print());
            //Проверка, что метод вызывался только 1 раз
            Mockito.verify(eventService, Mockito.times(1)).findAll();
        }
    }
}