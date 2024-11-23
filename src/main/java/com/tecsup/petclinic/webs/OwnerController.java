package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping
    public List<Owner> findAll() {
        return ownerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> findById(@PathVariable Integer id) throws OwnerNotFoundException {
        return ResponseEntity.ok(ownerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Owner> create(@RequestBody Owner owner) {
        return ResponseEntity.status(201).body(ownerService.create(owner));
    }

    @PutMapping
    public ResponseEntity<Owner> update(@RequestBody Owner owner) throws OwnerNotFoundException {
        return ResponseEntity.ok(ownerService.update(owner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws OwnerNotFoundException {
        ownerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
