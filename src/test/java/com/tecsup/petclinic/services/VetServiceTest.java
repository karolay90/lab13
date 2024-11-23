package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.*;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class VetServiceTest {
    @Autowired
    private VetService vetService;
    
    /*
    @Test
    public void testFindVetByFirstName() {
        String FIND_FIRST_NAME = "Linda";
        int SIZE_EXPECTED = 4;
        List<Vet> vets = this.vetService.findByFirstName(FIND_FIRST_NAME);
        assertEquals(SIZE_EXPECTED, vets.size());
    }
     */
    @Test
    public void testCreateVet() {
        String FIRST_NAME = "Anna";
        String LAST_NAME = "Smith";

        Vet vet = new Vet(FIRST_NAME, LAST_NAME);

        Vet vetCreated = this.vetService.create(vet);

        log.info("VET CREATED: " + vetCreated);

        assertNotNull(vetCreated.getId());
        assertEquals(FIRST_NAME, vetCreated.getFirstName());
        assertEquals(LAST_NAME, vetCreated.getLastName());
    }
    @Test
    public void testUpdateVet() {
        String FIRST_NAME = "Paul";
        String LAST_NAME = "Taylor";

        String UP_FIRST_NAME = "Paul Updated";
        String UP_LAST_NAME = "Taylor Updated";

        Vet vet = new Vet(FIRST_NAME, LAST_NAME);

        log.info(">" + vet);
        Vet vetCreated = this.vetService.create(vet);
        log.info(">>" + vetCreated);

        vetCreated.setFirstName(UP_FIRST_NAME);
        vetCreated.setLastName(UP_LAST_NAME);

        Vet updatedVet = this.vetService.update(vetCreated);
        log.info(">>>>" + updatedVet);

        assertEquals(UP_FIRST_NAME, updatedVet.getFirstName());
        assertEquals(UP_LAST_NAME, updatedVet.getLastName());
    }

    @Test
    public void testDeleteVet() {
        String FIRST_NAME = "Michael";
        String LAST_NAME = "Brown";

        Vet vet = new Vet(FIRST_NAME, LAST_NAME);
        vet = this.vetService.create(vet);
        log.info("" + vet);

        try {
            this.vetService.delete(vet.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            this.vetService.findById(vet.getId());
            fail("Expected VetNotFoundException");
        } catch (VetNotFoundException e) {
            assertTrue(true);
        }
    }
}
