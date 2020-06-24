package br.com.invillia.projetoPaloAlto.domain.dto;

import br.com.invillia.projetoPaloAlto.anotation.IsCPF;
import br.com.invillia.projetoPaloAlto.anotation.IsRG;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class IndividualDTO extends CustomerDTO{

    @IsCPF(message = "Invalided CPF!")
    @NotNull
    private String document;

    @NotNull
    private String motherName;

    @IsRG(message = "Invalided RG!")
    @NotNull
    private String rg;

    @NotNull
    private LocalDate birthDate;
}
