package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;

import java.util.List;


public interface VetService {

    Vet create(Vet vet);

    Vet update(Vet vet);

    void delete(Integer id) throws VetNotFoundException;

    Vet findById(Integer id) throws VetNotFoundException;

    List<Vet> findByFirstName(String firstName);

    List<Vet> findByLastName(String lastName);

    List<Vet> findAll();
}
