package br.com.invillia.projetoPaloAlto.mapper.interfaces;

import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IndividualMapperInterface extends EntityMapper<IndividualDTO, Individual>{

    IndividualMapperInterface INSTANCE = Mappers.getMapper( IndividualMapperInterface.class );

    @Mapping(source = "addresses", target = "individualDTO")
    IndividualDTO toDTO(Individual individual);

    @InheritInverseConfiguration
    Individual toEntity(IndividualDTO individualDTO);

//    List<IndividualDTO> toListDTO(List<Individual> individuals);
//
//    List<Individual> toListEntity(List<IndividualDTO> addressesDTO);
}
