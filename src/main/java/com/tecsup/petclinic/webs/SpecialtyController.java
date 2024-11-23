package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    public List<Specialty> getAllSpecialties() {
        return specialtyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialty> getSpecialtyById(@PathVariable Integer id) {
        return ResponseEntity.ok(specialtyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Specialty> createSpecialty(@RequestBody Specialty specialty) {
        Specialty created = specialtyService.create(specialty);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialty> updateSpecialty(@PathVariable Integer id, @RequestBody Specialty specialty) {
        specialty.setId(id);
        return ResponseEntity.ok(specialtyService.update(specialty));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Integer id) {
        specialtyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
