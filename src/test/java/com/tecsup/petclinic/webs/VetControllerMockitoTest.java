package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VetControllerMockitoTest {
    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VetService vetService;
    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }
    @AfterEach
    void tearDown() {
    }
    @Test
    public void testFindAllVets() throws Exception {
        List<Vet> vets = Arrays.asList(
                new Vet(1, "James", "Carter"),
                new Vet(2, "Linda", "Douglas")
        );
        Mockito.when(vetService.findAll()).thenReturn(vets);
        this.mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(vets.size())))
                .andExpect(jsonPath("$[0].id", is(vets.get(0).getId())))
                .andExpect(jsonPath("$[0].firstName", is(vets.get(0).getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(vets.get(1).getLastName())));
    }
    @Test
    public void testFindVetByIdOK() throws Exception {
        Vet vet = new Vet(1, "James", "Carter");

        Mockito.when(vetService.findById(1)).thenReturn(vet);

        mockMvc.perform(get("/vets/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(vet.getId())))
                .andExpect(jsonPath("$.firstName", is(vet.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(vet.getLastName())));
    }

    @Test
    public void testFindVetByIdNotFound() throws Exception {
        int ID_NOT_EXIST = 666;

        Mockito.when(vetService.findById(ID_NOT_EXIST))
                .thenThrow(new VetNotFoundException("Vet not found"));

        mockMvc.perform(get("/vets/" + ID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVet() throws Exception {
        Vet newVet = new Vet(null, "Anna", "Smith");
        Vet savedVet = new Vet(1, "Anna", "Smith");

        Mockito.when(vetService.create(Mockito.any(Vet.class))).thenReturn(savedVet);
        mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVet))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(savedVet.getId())))
                .andExpect(jsonPath("$.firstName", is(savedVet.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(savedVet.getLastName())));
    }
    @Test
    public void testDeleteVet() throws Exception {
        int id = 1;

        Mockito.doNothing().when(vetService).delete(id);

        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteVetNotFound() throws Exception {
        int ID_NOT_EXIST = 666;

        Mockito.doThrow(new VetNotFoundException("Vet not found"))
                .when(vetService).delete(ID_NOT_EXIST);

        mockMvc.perform(delete("/vets/" + ID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }
}
