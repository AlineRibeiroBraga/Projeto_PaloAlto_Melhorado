package br.com.invillia.projetoPaloAlto.mapper.interfaces;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Address;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface AddressMapperInterface extends EntityMapper<AddressDTO, Address> {

    AddressMapperInterface INSTANCE = Mappers.getMapper( AddressMapperInterface.class );

    @Mapping(source = "legalEntity", target = "legalEntityDTO", ignore = true)
    @Mapping(source = "individual", target = "individualDTO", ignore = true)
    AddressDTO toDTO(Address address);

//    @InheritInverseConfiguration
//    Address toEntity(AddressDTO addressDTO);

//    List<AddressDTO> toListDTO(List<Address> addresses);
//
//    List<Address> toListEntity(List<AddressDTO> addressesDTO);
}
