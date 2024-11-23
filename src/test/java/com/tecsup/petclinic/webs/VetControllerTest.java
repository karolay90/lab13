package com.tecsup.petclinic.webs;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.VetTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VetControllerTest {
    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testFindVetKO() throws Exception {
        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testCreateVet() throws Exception {
        String FIRST_NAME = "Linda";
        String LAST_NAME = "Douglas";
        VetTO newVetTO = new VetTO();
        newVetTO.setFirstName(FIRST_NAME);
        newVetTO.setLastName(LAST_NAME);
        mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)));
    }
    @Test
    public void testDeleteVet() throws Exception {
        String FIRST_NAME = "Michael";
        String LAST_NAME = "Brown";
        VetTO newVetTO = new VetTO();
        newVetTO.setFirstName(FIRST_NAME);
        newVetTO.setLastName(LAST_NAME);
        ResultActions mvcActions = mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");
        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteVetKO() throws Exception {
        mockMvc.perform(delete("/vets/1000"))
                .andExpect(status().isNotFound());
    }
}
