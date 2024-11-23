package com.tecsup.petclinic.services;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exception.SpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
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
class SpecialtyServiceTest {
    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyServiceImpl specialtyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testFindAllSpecialties() {
        List<Specialty> specialties = Arrays.asList(
                new Specialty(1, "Radiology"),
                new Specialty(2, "Surgery")
        );
        when(specialtyRepository.findAll()).thenReturn(specialties);
        List<Specialty> result = specialtyService.findAll();
        assertEquals(2, result.size());
        verify(specialtyRepository, times(1)).findAll();
    }
    @Test
    void testFindSpecialtyById() {
        Specialty specialty = new Specialty(1, "Radiology");
        when(specialtyRepository.findById(1)).thenReturn(Optional.of(specialty));
        Specialty result = specialtyService.findById(1);
        assertNotNull(result);
        assertEquals("Radiology", result.getName());
        verify(specialtyRepository, times(1)).findById(1);
    }
    @Test
    void testFindSpecialtyByIdNotFound() {
        when(specialtyRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(SpecialtyNotFoundException.class, () -> specialtyService.findById(999));
        verify(specialtyRepository, times(1)).findById(999);
    }
    @Test
    void testCreateSpecialty() {
        Specialty specialty = new Specialty(null, "Dentistry");
        when(specialtyRepository.save(specialty)).thenReturn(new Specialty(3, "Dentistry"));
        Specialty result = specialtyService.create(specialty);
        assertNotNull(result.getId());
        assertEquals("Dentistry", result.getName());
        verify(specialtyRepository, times(1)).save(specialty);
    }
}
