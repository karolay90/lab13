package com.tecsup.petclinic.services;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exception.SpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public Specialty create(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public Specialty update(Specialty specialty) {
        if (specialty.getId() == null || !specialtyRepository.existsById(specialty.getId())) {
            throw new SpecialtyNotFoundException("Specialty with ID " + specialty.getId() + " not found");
        }
        return specialtyRepository.save(specialty);
    }

    @Override
    public void delete(Integer id) {
        if (!specialtyRepository.existsById(id)) {
            throw new SpecialtyNotFoundException("Specialty with ID " + id + " not found");
        }
        specialtyRepository.deleteById(id);
    }

    @Override
    public Specialty findById(Integer id) {
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty with ID " + id + " not found"));
    }

    @Override
    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }
}
