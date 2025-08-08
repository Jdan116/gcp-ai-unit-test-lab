package com.example.lab.controller;

import com.example.lab.exception.EmailAlreadyExistsException;
import com.example.lab.model.User;
import com.example.lab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean UserService userService;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        User user = new User("Frank", "frank@example.com");
        when(userService.createUser(any())).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Frank\",\"email\":\"frank@example.com\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value("frank@example.com"));
    }


    @Test
    void shouldReturnConflictForDuplicateEmail() throws Exception {
        when(userService.createUser(any())).thenThrow(new EmailAlreadyExistsException("Email exists"));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Frank\",\"email\":\"frank@example.com\"}"))
            .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnInternalServerErrorForUnexpectedException() throws Exception {
        when(userService.createUser(any())).thenThrow(new RuntimeException("Unexpected error"));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Frank\",\"email\":\"frank@example.com\"}"))
            .andExpect(status().isInternalServerError());
    }
}