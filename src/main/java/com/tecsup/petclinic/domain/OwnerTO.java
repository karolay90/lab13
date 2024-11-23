package com.tecsup.petclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
}
