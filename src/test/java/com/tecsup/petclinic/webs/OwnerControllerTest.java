package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.services.OwnerService;
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

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    private OwnerMapper mapper = Mappers.getMapper(OwnerMapper.class);

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testFindAllOwners() throws Exception {
        List<Owner> owners = Arrays.asList(
                new Owner(1, "George", "Franklin", "110 W.Liberty St.", "Madison", "6085551023"),
                new Owner(2, "Betty", "Davis", "638.Cardinal Ave.", "Sun Prairie", "6085551749")
        );

        Mockito.when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].firstName", is("George")));
    }

    @Test
    public void testFindOwnerById() throws Exception {
        Owner owner = new Owner(1, "George", "Franklin", "110 W.Liberty St.", "Madison", "6085551023");

        Mockito.when(ownerService.findById(1)).thenReturn(owner);

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.firstName", is("George")));
    }

    @Test
    public void testCreateOwner() throws Exception {
        OwnerTO newOwnerTO = new OwnerTO(null, "Eduardo", "Davis", "123 Main St", "McFarlant", "6085558763");

        Owner newOwner = mapper.toOwner(newOwnerTO);

        Mockito.when(ownerService.create(newOwner)).thenReturn(new Owner(3, "Eduardo", "Davis", "123 Main St", "McFarlant", "6085558763"));

        mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.firstName", is("Eduardo")));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        Mockito.doNothing().when(ownerService).delete(1);

        mockMvc.perform(delete("/owners/1"))
                .andExpect(status().isOk());
    }
}
