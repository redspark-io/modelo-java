package io.redspark.controller.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude="city")
@ToString(exclude="city")
public class HotelDTO {

    private Long id;

    @NotNull(message = "property name can't be null")
    private String name;
    
    @NotNull(message = "property address can't be null")
    private String address;
    
    @NotNull(message = "property zip can't be null")
    private String zip;
    
    private CityDTO city;

}
