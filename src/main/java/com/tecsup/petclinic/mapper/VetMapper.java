package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.domain.VetTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface VetMapper {

    VetMapper INSTANCE = Mappers.getMapper(VetMapper.class);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    Vet toVet(VetTO vetTO);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    VetTO toVetTO(Vet vet);

    List<VetTO> toVetTOList(List<Vet> vetList);

    List<Vet> toVetList(List<VetTO> vetTOList);
}
