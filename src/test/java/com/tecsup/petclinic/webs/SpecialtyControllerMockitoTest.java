package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.domain.SpecialtyTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.mapper.SpecialtyMapper;
import com.tecsup.petclinic.services.SpecialtyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class SpecialtyControllerMockitoTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecialtyService specialtyService;

    private SpecialtyMapper mapper = Mappers.getMapper(SpecialtyMapper.class);

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testFindAllSpecialties() throws Exception {
        List<Specialty> specialties = Arrays.asList(
                new Specialty(1, "Radiology"),
                new Specialty(2, "Surgery")
        );

        Mockito.when(specialtyService.findAll()).thenReturn(specialties);

        mockMvc.perform(get("/specialties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Radiology")))
                .andExpect(jsonPath("$[1].name", is("Surgery")));
    }

    @Test
    public void testFindSpecialtyById() throws Exception {
        Specialty specialty = new Specialty(1, "Radiology");

        Mockito.when(specialtyService.findById(1)).thenReturn(specialty);

        mockMvc.perform(get("/specialties/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Radiology")));
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        SpecialtyTO specialtyTO = new SpecialtyTO(null, "Dentistry");
        Specialty specialty = mapper.toSpecialty(specialtyTO);

        Mockito.when(specialtyService.create(specialty)).thenReturn(new Specialty(3, "Dentistry"));

        mockMvc.perform(post("/specialties")
                        .content(om.writeValueAsString(specialtyTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Dentistry")));
    }

    @Test
    public void testUpdateSpecialty() throws Exception {
        SpecialtyTO specialtyTO = new SpecialtyTO(1, "Updated Radiology");
        Specialty specialty = mapper.toSpecialty(specialtyTO);

        Mockito.when(specialtyService.update(specialty)).thenReturn(specialty);

        mockMvc.perform(put("/specialties/1")
                        .content(om.writeValueAsString(specialtyTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Radiology")));
    }

    @Test
    public void testDeleteSpecialty() throws Exception {
        Mockito.doNothing().when(specialtyService).delete(1);

        mockMvc.perform(delete("/specialties/1"))
                .andExpect(status().isOk());
    }
}
