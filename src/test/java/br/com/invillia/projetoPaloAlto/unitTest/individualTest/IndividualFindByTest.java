package br.com.invillia.projetoPaloAlto.unitTest.individualTest;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Address;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;
import br.com.invillia.projetoPaloAlto.exception.IndividualException;
import br.com.invillia.projetoPaloAlto.mapper.AddressMapper;
import br.com.invillia.projetoPaloAlto.mapper.IndividualMapper;
import br.com.invillia.projetoPaloAlto.repository.IndividualRepository;
import br.com.invillia.projetoPaloAlto.service.IndividualService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
public class IndividualFindByTest {

    private Faker faker;

    private IndividualMapperTest individualMapperTest;

    @Mock
    private IndividualRepository individualRepository;

    @Spy
    @InjectMocks
    private IndividualMapper individualMapper;

    @Spy
    @InjectMocks
    private IndividualService individualService;

    @Spy
    private AddressMapper addressMapper;

    @BeforeAll
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.faker = new Faker();
        this.individualMapperTest = new IndividualMapperTest();
    }

    @Test
    public void findByIdExists(){

        Individual individual = individualMapperTest.createIndividual(1L);

        when(individualRepository.findById(1L)).thenReturn(Optional.of(individual));

        IndividualDTO individualDTO = individualService.findById(1L);

        fieldsValidator(individualDTO, individual,0);

        verify(individualRepository,times(1)).findById(1L);
    }

    @Test
    public void findByIdNotExists(){

        when(individualRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IndividualException.class, ()-> individualService.findById(1L));
    }

    @Test
    public void findByDocumentExists(){

        Individual individual  = individualMapperTest.createIndividual(1L);

        when(individualRepository.findByDocument(individual.getDocument())).thenReturn(Optional.of(individual));

        IndividualDTO individualDTO = individualService.findByDocument(individual.getDocument());

        fieldsValidator(individualDTO,individual,0);

        verify(individualRepository,times(1)).findByDocument(individualDTO.getDocument());
    }

    @Test
    public void findByDocumentNotExists(){

        when(individualRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(IndividualException.class,
                () -> individualService.findByDocument(faker.number().digits(11)));
    }

    private void fieldsValidator(IndividualDTO individualDTO, Individual individual, int index) {

        individualsValidator(individualDTO,individual);

        AddressDTO addressDTO1 = individualDTO.getAddressesDTO().get(index);
        Address address1 = individual.getAddresses().get(index);

        addressesValidator(addressDTO1,address1);

        IndividualDTO individualDTO1 = addressDTO1.getIndividualDTO();
        Individual individual1 = address1.getIndividual();

        individualsValidator(individualDTO1,individual1);
    }

    private void individualsValidator(IndividualDTO individualDTO, Individual individual) {

        Assertions.assertEquals(individualDTO.getActive(),individual.getActive());
        Assertions.assertEquals(individualDTO.getName(),individual.getName());
        Assertions.assertEquals(individualDTO.getDocument(),individual.getDocument());
        Assertions.assertEquals(individualDTO.getBirthDate(),individual.getBirthDate());
        Assertions.assertEquals(individualDTO.getMotherName(),individual.getMotherName());
    }

    private void addressesValidator(AddressDTO addressDTO, Address address) {

        Assertions.assertEquals(addressDTO.getMain(), address.getMain());
        Assertions.assertEquals(addressDTO.getDistrict(), address.getDistrict());
        Assertions.assertEquals(addressDTO.getNumber(),address.getNumber());
        Assertions.assertEquals(addressDTO.getCity(),address.getCity());
        Assertions.assertEquals(addressDTO.getState(),address.getState());
        Assertions.assertEquals(addressDTO.getZipCode(),address.getZipCode());
    }
}