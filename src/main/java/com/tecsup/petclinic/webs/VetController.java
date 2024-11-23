package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.mapper.VetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.petclinic.domain.VetTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;

import java.util.List;

@RestController
@Slf4j
public class VetController {

    private final VetService vetService;

    private final VetMapper mapper;

    public VetController(VetService vetService, VetMapper mapper) {
        this.vetService = vetService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/vets")
    public ResponseEntity<List<VetTO>> findAllVets() {

        List<Vet> vets = vetService.findAll();
        log.info("vets: {}", vets);

        List<VetTO> vetsTO = this.mapper.toVetTOList(vets);
        log.info("vetsTO: {}", vetsTO);

        return ResponseEntity.ok(vetsTO);
    }

    @PostMapping(value = "/vets")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VetTO> create(@RequestBody VetTO vetTO) {

        Vet newVet = this.mapper.toVet(vetTO);
        VetTO newVetTO = this.mapper.toVetTO(vetService.create(newVet));

        return ResponseEntity.status(HttpStatus.CREATED).body(newVetTO);
    }


    @GetMapping(value = "/vets/{id}")
    public ResponseEntity<VetTO> findById(@PathVariable Integer id) {

        VetTO vetTO;

        try {
            Vet vet = vetService.findById(id);
            vetTO = this.mapper.toVetTO(vet);

        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vetTO);
    }


    @PutMapping(value = "/vets/{id}")
    public ResponseEntity<VetTO> update(@RequestBody VetTO vetTO, @PathVariable Integer id) {

        VetTO updatedVetTO;

        try {
            Vet existingVet = vetService.findById(id);

            existingVet.setFirstName(vetTO.getFirstName());
            existingVet.setLastName(vetTO.getLastName());

            vetService.update(existingVet);

            updatedVetTO = this.mapper.toVetTO(existingVet);

        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedVetTO);
    }


    @DeleteMapping(value = "/vets/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {

        try {
            vetService.delete(id);
            return ResponseEntity.ok("Deleted Vet ID: " + id);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
