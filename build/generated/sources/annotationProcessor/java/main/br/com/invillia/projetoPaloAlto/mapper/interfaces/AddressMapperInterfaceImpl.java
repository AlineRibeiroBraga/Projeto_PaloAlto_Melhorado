package br.com.invillia.projetoPaloAlto.mapper.interfaces;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Address;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-06-24T15:15:44-0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.7 (Ubuntu)"
)
public class AddressMapperInterfaceImpl implements AddressMapperInterface {

    @Override
    public Address toEntity(AddressDTO DTO) {
        if ( DTO == null ) {
            return null;
        }

        Address address = new Address();

        address.setDistrict( DTO.getDistrict() );
        address.setNumber( DTO.getNumber() );
        address.setCity( DTO.getCity() );
        address.setState( DTO.getState() );
        address.setZipCode( DTO.getZipCode() );
        address.setMain( DTO.getMain() );

        return address;
    }

    @Override
    public List<Address> toListEntity(List<AddressDTO> listDTO) {
        if ( listDTO == null ) {
            return null;
        }

        List<Address> list = new ArrayList<Address>( listDTO.size() );
        for ( AddressDTO addressDTO : listDTO ) {
            list.add( toEntity( addressDTO ) );
        }

        return list;
    }

    @Override
    public List<AddressDTO> toListDTO(List<Address> listEntity) {
        if ( listEntity == null ) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>( listEntity.size() );
        for ( Address address : listEntity ) {
            list.add( toDTO( address ) );
        }

        return list;
    }

    @Override
    public AddressDTO toDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setDistrict( address.getDistrict() );
        addressDTO.setNumber( address.getNumber() );
        addressDTO.setCity( address.getCity() );
        addressDTO.setState( address.getState() );
        addressDTO.setZipCode( address.getZipCode() );
        addressDTO.setMain( address.getMain() );

        return addressDTO;
    }
}
