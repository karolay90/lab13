package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.domain.SpecialtyTO;
import com.tecsup.petclinic.entities.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SpecialtyMapper {

    SpecialtyMapper INSTANCE = Mappers.getMapper(SpecialtyMapper.class);

    SpecialtyTO toSpecialtyTO(Specialty specialty);

    Specialty toSpecialty(SpecialtyTO specialtyTO);

    List<SpecialtyTO> toSpecialtyTOList(List<Specialty> specialties);

    List<Specialty> toSpecialtyList(List<SpecialtyTO> specialtyTOs);
}
