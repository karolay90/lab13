package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.entities.Owner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerTO toOwnerTO(Owner owner);

    Owner toOwner(OwnerTO ownerTO);

    List<OwnerTO> toOwnerTOList(List<Owner> owners);

    List<Owner> toOwnerList(List<OwnerTO> ownerTOs);
}
