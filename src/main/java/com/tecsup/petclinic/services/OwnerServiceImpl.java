package com.tecsup.petclinic.services;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Override
    public Owner create(Owner owner) {
        return ownerRepository.save(owner);
    }
    @Override
    public Owner update(Owner owner) throws OwnerNotFoundException {
        if (owner.getId() == null || !ownerRepository.existsById(owner.getId())) {
            throw new OwnerNotFoundException("Owner with ID " + owner.getId() + " not found");
        }
        return ownerRepository.save(owner);
    }
    @Override
    public void delete(Integer id) throws OwnerNotFoundException {
        if (!ownerRepository.existsById(id)) {
            throw new OwnerNotFoundException("Owner with ID " + id + " not found");
        }
        ownerRepository.deleteById(id);
    }
    @Override
    public Owner findById(Integer id) throws OwnerNotFoundException {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner with ID " + id + " not found"));
    }
    @Override
    public List<Owner> findByFirstName(String firstName) {
        return ownerRepository.findByFirstName(firstName);
    }
    @Override
    public List<Owner> findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }
    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
}
