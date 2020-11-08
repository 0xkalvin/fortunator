package com.fortunator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fortunator.api.service.GoalService;

@WebMvcTest(GoalController.class)
public class GoalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoalService service;

    @Test
    public void shouldCreateGoalSuccessfully() throws Exception {

        String body = "{\"description\":\"Comprar um corsa tunado\",\"amount\":10000,\"date\":\"2020-11-07T18:39:11.003Z\",\"type\":\"BUDGET\",\"user\":{\"id\":1}}";

        MockHttpServletRequestBuilder request = post("/goals").contentType(MediaType.APPLICATION_JSON).content(body)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnErrorDueToMissingUser() throws Exception {

        String body = "{\"description\":\"Comprar um corsa tunado\",\"amount\":10000,\"date\":\"2020-11-07T18:39:11.003Z\",\"type\":\"BUDGET\"}";

        MockHttpServletRequestBuilder request = post("/goals").contentType(MediaType.APPLICATION_JSON).content(body)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNoContentWhenUserDoesNotHaveGoals() throws Exception {

        MockHttpServletRequestBuilder request = get("/goals?user_id=1").contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request).andExpect(status().isNoContent());

    }

    @Test
    public void shouldUpdateGoalStatusSuccessfully() throws Exception {

        String body = "{\"status\":\"DONE\",\"user\":{\"id\":1}}";

        MockHttpServletRequestBuilder request = put("/goals/1").contentType(MediaType.APPLICATION_JSON).content(body)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request).andExpect(status().isOk());
    }
}
