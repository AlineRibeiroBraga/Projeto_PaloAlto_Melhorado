package br.com.invillia.projetoPaloAlto.domain.dto;

import br.com.invillia.projetoPaloAlto.anotation.IsZipCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AddressDTO {

    @NotNull
    private String district;

    @NotNull
    private String number;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    @IsZipCode
    private String zipCode;

    @NotNull
    private Boolean main;

    @JsonIgnore
    private LegalEntityDTO legalEntityDTO;

    @JsonIgnore
    private IndividualDTO individualDTO;
}
