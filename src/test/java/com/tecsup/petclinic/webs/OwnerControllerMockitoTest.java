package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
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
public class OwnerControllerMockitoTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void testFindAllOwners() throws Exception {
        List<Owner> owners = Arrays.asList(
                new Owner(1, "George", "Franklin", "110 W.Liberty St.", "Madison", "6085551023"),
                new Owner(2, "Betty", "Davis", "638.Cardinal Ave.", "Sun Prairie", "6085551749")
        );

        Mockito.when(ownerService.findAll()).thenReturn(owners);

        this.mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(owners.size())))
                .andExpect(jsonPath("$[0].id", is(owners.get(0).getId())))
                .andExpect(jsonPath("$[1].city", is(owners.get(1).getCity())));
    }

    @Test
    public void testFindOwnerById() throws Exception {
        Owner owner = new Owner(1, "George", "Franklin", "110 W.Liberty St.", "Madison", "6085551023");

        Mockito.when(ownerService.findById(1)).thenReturn(owner);

        this.mockMvc.perform(get("/owners/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(owner.getId())))
                .andExpect(jsonPath("$.firstName", is(owner.getFirstName())));
    }

    @Test
    public void testCreateOwner() throws Exception {
        Owner newOwner = new Owner(null, "Eduardo", "Davis", "2693 Commerce St.", "McFarlant", "6085558763");
        Owner savedOwner = new Owner(3, "Eduardo", "Davis", "123 Main St", "McFarlant", "6085558763");

        Mockito.when(ownerService.create(Mockito.any(Owner.class))).thenReturn(savedOwner);

        mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwner))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(savedOwner.getId())));
    }
}
