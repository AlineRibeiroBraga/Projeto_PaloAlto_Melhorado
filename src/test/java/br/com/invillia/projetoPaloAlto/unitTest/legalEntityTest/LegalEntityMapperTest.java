package br.com.invillia.projetoPaloAlto.unitTest.legalEntityTest;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Address;
import br.com.invillia.projetoPaloAlto.domain.model.LegalEntity;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LegalEntityMapperTest {

    private Faker faker = new Faker();

    public LegalEntity createLegalEntity(Long id) {

        LegalEntity legalEntity = new LegalEntity();

        legalEntity.setId(id);
        legalEntity.setActive(true);
        legalEntity.setDocument(faker.number().digits(14));
        legalEntity.setCreatedAt(LocalDateTime.now());
        legalEntity.setTradeName(faker.name().name());
        legalEntity.setName(faker.name().name());
        legalEntity.setAddresses(createListAddress());

        for(Address address : legalEntity.getAddresses()){
            address.setLegalEntity(legalEntity);
        }

        return legalEntity;
    }

    public List<Address> createListAddress() {

        List<Address> addresses = new ArrayList<>();

        addresses.add(createAddress(1L,true));

        return addresses;
    }

    public Address createAddress(Long id, Boolean main) {

        Address address = new Address();

        address.setId(id);
        address.setMain(main);
        address.setDistrict(faker.address().fullAddress());
        address.setNumber(faker.address().buildingNumber());
        address.setCity(faker.address().city());
        address.setState(faker.address().state());
        address.setZipCode(faker.address().zipCode());

        return address;
    }

    public LegalEntityDTO createLegalEntityDTO(){

        LegalEntityDTO legalEntityDTO = new LegalEntityDTO();

        legalEntityDTO.setName(faker.name().name());
        legalEntityDTO.setDocument(faker.number().digits(14));
        legalEntityDTO.setTradeName(faker.name().name());
        legalEntityDTO.setActive(true);
        legalEntityDTO.setAddressesDTO(createListAddressDTO());

        for(AddressDTO addressDTO : legalEntityDTO.getAddressesDTO()){
            addressDTO.setLegalEntityDTO(legalEntityDTO);
        }

        return legalEntityDTO;
    }

    public List<AddressDTO> createListAddressDTO() {
        List<AddressDTO> addressesDTO = new ArrayList<>();

        addressesDTO.add(createAddressDTO(true));

        return addressesDTO;
    }

    public AddressDTO createAddressDTO(Boolean main) {

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setDistrict(faker.name().name());
        addressDTO.setNumber(faker.number().digit());
        addressDTO.setCity(faker.name().name());
        addressDTO.setState(faker.name().name());
        addressDTO.setZipCode(faker.code().gtin8());
        addressDTO.setMain(main);

        return addressDTO;
    }

    private List<Address> newListAddress(List<Address> addresses1) {

        List<Address> addresses = new ArrayList<>();

        for(Address address : addresses1){
            addresses.add(newAddress(address));
        }

        return addresses;
    }

    private Address newAddress(Address address1) {

        Address address = new Address();

        address.setId(address1.getId());
        address.setMain(address1.getMain());
        address.setDistrict(address1.getDistrict());
        address.setNumber(address1.getNumber());
        address.setCity(address1.getCity());
        address.setState(address1.getState());
        address.setZipCode(address1.getZipCode());

        return address;
    }
}
