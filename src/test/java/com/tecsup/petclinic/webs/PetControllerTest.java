package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.PetTO;
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
/**
 * 
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class PetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	

	/**
	 * 
	 * @throws Exception
	 * 
	 */
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindPetKO() throws Exception {

		mockMvc.perform(get("/pets/666"))
				.andExpect(status().isNotFound());

	}
	
	/**
	 * @throws Exception
	 */


	/**
     * 
     * @throws Exception
     */


	@Test
	public void testDeletePetKO() throws Exception {

		mockMvc.perform(delete("/pets/" + "1000" ))
				/*.andDo(print())*/
				.andExpect(status().isNotFound());
	}
}
    