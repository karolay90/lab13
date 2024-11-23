package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllOwners() {
        List<Owner> owners = Arrays.asList(
                new Owner(1, "George", "Franklin", "110 W.Liberty St.", "Madison", "6085551023"),
                new Owner(2, "Betty", "Davis", "638.Cardinal Ave.", "Sun Prairie", "6085551749")
        );

        when(ownerRepository.findAll()).thenReturn(owners);

        List<Owner> result = ownerService.findAll();

        assertEquals(2, result.size());
        verify(ownerRepository, times(1)).findAll();
    }
    @Test
    void testFindOwnerById() throws OwnerNotFoundException {
        Owner owner = new Owner(1, "George", "Franklin", "110 W.Liberty St.", "Madison", "6085551023");

        when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));

        Owner result = ownerService.findById(1);

        assertNotNull(result);
        assertEquals("George", result.getFirstName());
        verify(ownerRepository, times(1)).findById(1);
    }

    @Test
    void testFindOwnerByIdNotFound() {
        when(ownerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> ownerService.findById(999));
        verify(ownerRepository, times(1)).findById(999);
    }
    @Test
    void testCreateOwner() {
        Owner owner = new Owner(null, "Eduardo", "Davis", "2693 Commerce St.", "McFarlant", "6085558763");

        when(ownerRepository.save(owner)).thenReturn(new Owner(3, "Eduardo", "Davis", "2693 Commerce St.", "McFarlant", "6085558763"));

        Owner result = ownerService.create(owner);

        assertNotNull(result.getId());
        assertEquals("Eduardo", result.getFirstName());
        verify(ownerRepository, times(1)).save(owner);
    }
    @Test
    void testUpdateOwner() {
        Owner owner = new Owner(3, "Eduardo", "Davis", "2693 Commerce St.", "McFarlant", "6085558763");

        when(ownerRepository.existsById(3)).thenReturn(true);
        when(ownerRepository.save(owner)).thenReturn(owner);

        Owner result = ownerService.update(owner);

        assertEquals("Eduardo", result.getFirstName());
        verify(ownerRepository, times(1)).existsById(3);
        verify(ownerRepository, times(1)).save(owner);
    }
    @Test
    void testDeleteOwner() throws OwnerNotFoundException {
        when(ownerRepository.existsById(1)).thenReturn(true);
        doNothing().when(ownerRepository).deleteById(1);
        ownerService.delete(1);
        verify(ownerRepository, times(1)).existsById(1);
        verify(ownerRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteOwnerNotFound() {
        when(ownerRepository.existsById(999)).thenReturn(false);

        assertThrows(OwnerNotFoundException.class, () -> ownerService.delete(999));
        verify(ownerRepository, times(1)).existsById(999);
    }
}
