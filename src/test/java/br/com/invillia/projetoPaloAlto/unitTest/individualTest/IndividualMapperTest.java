package br.com.invillia.projetoPaloAlto.unitTest.individualTest;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Address;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;
import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IndividualMapperTest {

    private Faker faker = new Faker();

    public Individual createIndividual(Long id) {

        Individual individual = new Individual();

        individual.setId(id);
        individual.setActive(true);
        individual.setName(faker.name().name());
        individual.setCreatedAt(LocalDateTime.now());
        individual.setRg(faker.number().digits(9));
        individual.setDocument(faker.number().digits(11));
        individual.setMotherName(faker.name().fullName());
        individual.setBirthDate(LocalDate.now());
        individual.setAddresses(createListAddress());

        for(Address address : individual.getAddresses()){
            address.setIndividual(individual);
        }

        return individual;

    }

    public List<Address> createListAddress() {

        List<Address> addresses = new ArrayList<>();

        addresses.add(createAddress(1L, true));

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

    public IndividualDTO createIndividualDTO() {

        IndividualDTO individualDTO = new IndividualDTO();

        individualDTO.setName(faker.name().name());
        individualDTO.setRg(faker.number().digits(9));
        individualDTO.setDocument(faker.number().digits(11));
        individualDTO.setMotherName(faker.name().name());
        individualDTO.setBirthDate(LocalDate.now());
        individualDTO.setActive(true);
        individualDTO.setAddressesDTO(createListAddressDTO());

        for(AddressDTO addressDTO : individualDTO.getAddressesDTO()){
            addressDTO.setIndividualDTO(individualDTO);
        }

        return individualDTO;
    }

    public List<AddressDTO> createListAddressDTO() {

        List<AddressDTO> addresses = new ArrayList<>();

        addresses.add(createAddressDTO(1L,true));

        return addresses;
    }

    public AddressDTO createAddressDTO(Long id, Boolean main) {

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setMain(main);
        addressDTO.setDistrict(faker.address().fullAddress());
        addressDTO.setNumber(faker.address().buildingNumber());
        addressDTO.setCity(faker.address().cityName());
        addressDTO.setState(faker.address().state());
        addressDTO.setZipCode(faker.address().zipCode());

        return addressDTO;
    }

    public AddressDTO createAddressDTO(Boolean main) {

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setDistrict(faker.name().name());
        addressDTO.setMain(main);
        addressDTO.setNumber(faker.number().digit());
        addressDTO.setCity(faker.name().name());
        addressDTO.setState(faker.name().name());
        addressDTO.setZipCode(faker.code().gtin8());

        return addressDTO;
    }

    public Address createAddress(Boolean main, Long id) {

        Address address = new Address();

        address.setId(id);
        address.setDistrict(faker.name().name());
        address.setMain(main);
        address.setNumber(faker.number().digit());
        address.setCity(faker.name().name());
        address.setState(faker.name().name());
        address.setZipCode(faker.code().gtin8());

        return address;
    }

    public List<Individual> createListIndividual() {

        List<Individual> individuals = new ArrayList<>();

        individuals.add(createIndividual(1L));

        return individuals;
    }

    public List<IndividualDTO> createListIndividualsDTO() {

        List<IndividualDTO> individualsDTO = new ArrayList<>();

        individualsDTO.add(createIndividualDTO());

        return individualsDTO;
    }

    public Individual newIndividual(Individual individual1) {

        Individual individual = new Individual();

        individual.setId(individual1.getId());
        individual.setName(individual1.getName());
        individual.setMotherName(individual1.getMotherName());
        individual.setCreatedAt(individual1.getCreatedAt());
        individual.setRg(individual1.getRg());
        individual.setDocument(individual1.getDocument());
        individual.setBirthDate(individual1.getBirthDate());
        individual.setUpdatedAt(individual1.getUpdatedAt());
        individual.setActive(individual1.getActive());
        individual.setAddresses(newListAddress(individual1.getAddresses()));

        for(Address address : individual.getAddresses()){
            address.setIndividual(individual);
        }

        return individual;
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

//    private Individual createIndividual() {
//
//        Individual individual = new Individual();
//
//        individual.setId(1L);
//        individual.setActive(true);
//        individual.setMotherName(faker.name().name());
//        individual.setName(faker.name().name());
//        individual.setCreatedAt(LocalDateTime.now());
//        individual.setDocument(faker.number().digits(11));
//        individual.setRg(faker.number().digits(9));
//        individual.setBirthDate(LocalDate.now());
//        individual.setAddresses(createListAddress());
//
//        for(Address address : individual.getAddresses()){
//            address.setIndividual(individual);
//        }
//
//        return individual;
//    }
//
//    private List<Address> createListAddress() {
//
//        List<Address> addresses = new ArrayList<>();
//
//        addresses.add(createAddress(1L,true));
//
//        return addresses;
//    }
//
//    private Address createAddress(Long id, Boolean main) {
//
//        Address address = new Address();
//
//        address.setId(id);
//        address.setMain(main);
//        address.setDistrict(faker.address().fullAddress());
//        address.setNumber(faker.address().buildingNumber());
//        address.setCity(faker.address().city());
//        address.setState(faker.address().state());
//        address.setZipCode(faker.address().zipCode());
//
//        return address;
//    }
//
//    private IndividualDTO createIndividualDTO() {
//
//        IndividualDTO individualDTO = new IndividualDTO();
//
//        individualDTO.setName(faker.name().name());
//        individualDTO.setMotherName(faker.name().fullName());
//        individualDTO.setActive(true);
//        individualDTO.setDocument(faker.number().digits(11));
//        individualDTO.setBirthDate(LocalDate.now());
//        individualDTO.setRg(faker.number().digits(9));
//        individualDTO.setAddressesDTO(createListAddressDTO());
//
//        for (AddressDTO addressDTO : individualDTO.getAddressesDTO()){
//            addressDTO.setIndividualDTO(individualDTO);
//        }
//
//        return individualDTO;
//    }
//
//    private List<AddressDTO> createListAddressDTO() {
//
//        List<AddressDTO> addressDTOS = new ArrayList<>();
//
//        addressDTOS.add(createAddressDTO(true));
//
//        return addressDTOS;
//    }
//
//    private AddressDTO createAddressDTO(Boolean main) {
//
//        AddressDTO addressDTO = new AddressDTO();
//
//        addressDTO.setMain(main);
//        addressDTO.setDistrict(faker.address().fullAddress());
//        addressDTO.setNumber(faker.address().buildingNumber());
//        addressDTO.setCity(faker.address().city());
//        addressDTO.setState(faker.address().state());
//        addressDTO.setZipCode(faker.address().zipCode());
//
//        return addressDTO;
//    }

}
