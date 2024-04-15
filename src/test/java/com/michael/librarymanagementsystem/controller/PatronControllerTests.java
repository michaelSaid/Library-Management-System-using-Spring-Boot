package com.michael.librarymanagementsystem.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.librarymanagementsystem.exception.PatronNotFoundException;
import com.michael.librarymanagementsystem.model.Patron;
import com.michael.librarymanagementsystem.service.PatronService;

@WebMvcTest(PatronController.class)
@AutoConfigureMockMvc
public class PatronControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenPatronObject_whenCreatePatron_thenReturnSavedPatron() throws Exception {

        // Given
        Patron patron = Patron.builder()
            .name("Michael Soad")
            .address("Alex")
            .phoneNumber("01234567")
            .emailAddress("michael@mail.com")
            .build();

        given(patronService.addPatron(any(Patron.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));

        // When
        ResultActions response = mockMvc.perform(post("/api/patrons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patron)));

        // Then
        response.andDo(print()).
            andExpect(status().isCreated())
            .andExpect(jsonPath("$.name",
                is(patron.getName())))
            .andExpect(jsonPath("$.address",
                is(patron.getAddress())))
            .andExpect(jsonPath("$.phoneNumber",
                is(patron.getPhoneNumber())))
            .andExpect(jsonPath("$.emailAddress",
                is(patron.getEmailAddress())));

    }

    @Test
    public void givenListOfPatrons_whenGetAllPatrons_thenReturnPatronsList() throws Exception {
        // Given
        List<Patron> listOfPatrons = new ArrayList<>();
        listOfPatrons.add(Patron.builder().name("Michael Soad").address("Alex").phoneNumber("01234567").emailAddress("michael@mail.com").build());
        listOfPatrons.add(Patron.builder().name("Michael Said 2").address("Alex 2").phoneNumber("012345673").emailAddress("michael@mail.com 2").build());
        given(patronService.getAllPatrons()).willReturn(listOfPatrons);

        // When
        ResultActions response = mockMvc.perform(get("/api/patrons"));

        // Then
        response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.size()",
                is(listOfPatrons.size())));

    }

    @Test
    public void givenPatronId_whenGetPatronById_thenReturnPatronObject() throws Exception {
        // Given
        long patronId = 1L;
        Patron patron = Patron.builder()
            .name("Michael Soad")
            .address("Alex")
            .phoneNumber("01234567")
            .emailAddress("michael@mail.com")
            .build();

        given(patronService.getPatronById(patronId)).willReturn(patron);

        // When
        ResultActions response = mockMvc.perform(get("/api/patrons/{id}", patronId));

        // Then
        response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.name", is(patron.getName())))
            .andExpect(jsonPath("$.address", is(patron.getAddress())))
            .andExpect(jsonPath("$.phoneNumber", is(patron.getPhoneNumber())))
            .andExpect(jsonPath("$.emailAddress", is(patron.getEmailAddress())));

    }

    @Test
    public void givenInvalidPatronId_whenGetPatronsById_thenReturnEmpty() throws Exception {
        // Given
        long patronId = 1L;

        given(patronService.getPatronById(patronId)).willThrow(PatronNotFoundException.class);

        // When
        ResultActions response = mockMvc.perform(get("/api/patrons/{id}", patronId));

        // Then
        response.andExpect(status().isNotFound())
            .andDo(print());

    }

    @Test
    public void givenUpdatedPatron_whenUpdatePatron_thenReturnUpdatePatronObject() throws Exception {
        // Given
        long patronId = 1L;
        Patron savedPatron = Patron.builder()
            .name("Michael Soad")
            .address("Alex")
            .phoneNumber("01234567")
            .emailAddress("michael@mail.com")
            .build();

        Patron updatedPatron = Patron.builder()
            .address("Alex22")
            .build();

        given(patronService.getPatronById(patronId)).willReturn(savedPatron);
        given(patronService.updatePatron(anyLong(), any(Patron.class)))
            .willAnswer((invocation) -> invocation.getArgument(1));

        // When
        ResultActions response = mockMvc.perform(put("/api/patrons/{id}", patronId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedPatron)));

        // Then
        response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.address", is(updatedPatron.getAddress())));
    }

    @Test
    public void givenUpdatedPatron_whenUpdatePatron_thenReturn404() throws Exception {
        // Given
        long patronId = 1L;

        Patron updatedPatron = Patron.builder()
            .name("Michael Soad")
            .address("Alex")
            .phoneNumber("01234567")
            .emailAddress("michael@mail.com")
            .build();

        given(patronService.updatePatron(anyLong(), any(Patron.class))).willThrow(PatronNotFoundException.class);

        // When
        ResultActions response = mockMvc.perform(put("/api/patrons/{id}", patronId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedPatron)));

        // Then
        response.andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    public void givenPatronId_whenDeletePatron_thenReturn200() throws Exception {
        // Given
        long patronId = 1L;
        willDoNothing().given(patronService).deletePatron(patronId);

        // When
        ResultActions response = mockMvc.perform(delete("/api/patrons/{id}", patronId));

        // Then
        response.andExpect(status().isOk())
            .andDo(print());
    }
}
