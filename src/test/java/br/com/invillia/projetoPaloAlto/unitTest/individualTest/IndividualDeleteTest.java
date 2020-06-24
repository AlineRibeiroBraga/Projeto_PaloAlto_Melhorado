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
public class IndividualDeleteTest {

    private Faker faker;

    private IndividualMapperTest individualMapperTest;

    @Mock
    private IndividualRepository individualRepository;

    @Spy
    @InjectMocks
    private IndividualService individualService;

    @Spy
    @InjectMocks
    private IndividualMapper individualMapper;

    @Spy
    private AddressMapper addressMapper;

    @BeforeAll
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.faker = new Faker();
        this.individualMapperTest = new IndividualMapperTest();
    }

    @Test
    public void deleteByDocumentExists(){

        Individual individual = individualMapperTest.createIndividual(1L);

        when(individualRepository.findByDocument(individual.getDocument())).thenReturn(Optional.of(individual));

        String document = individualService.deleteByDocument(individual.getDocument()).getResponse();
        IndividualDTO individualDTO = individualService.findByDocument(document);

        fieldsValidator(individualDTO,individual,0);

        verify(individualService,times(1)).deleteByDocument(individual.getDocument());
    }

    @Test
    public void deleteByDocumentAlreadyDeleted(){

        Individual individual = individualMapperTest.createIndividual(1L);
        individual.setActive(false);

        when(individualRepository.findByDocument(individual.getDocument())).thenReturn(Optional.of(individual));

        Assertions.assertThrows(IndividualException.class,
                ()-> individualService.deleteByDocument(individual.getDocument()));
    }

    @Test
    public void deleteByDocumentNotExists(){

        when(individualRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(IndividualException.class,
                ()-> individualService.deleteByDocument(Mockito.anyString()));
    }

    @Test
    public void deleteByIdExists(){

        Individual individual = individualMapperTest.createIndividual(1L);

        when(individualRepository.findById(individual.getId())).thenReturn(Optional.of(individual));

        Long id = Long.valueOf(individualService.deleteById(individual.getId()).getResponse());
        IndividualDTO individualDTO = individualService.findById(id);

        fieldsValidator(individualDTO,individual,0);

        verify(individualService,times(1)).deleteById(individual.getId());
    }

    @Test
    public void deleteByIdAlreadyDeleted(){

        Individual individual = individualMapperTest.createIndividual(1L);
        individual.setActive(false);

        when(individualRepository.findById(individual.getId())).thenReturn(Optional.of(individual));

        Assertions.assertThrows(IndividualException.class,
                ()-> individualService.deleteById(individual.getId()));
    }

    @Test
    public void deleteByIdNotExists(){

        when(individualRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IndividualException.class,
                ()-> individualService.deleteById(1L));
    }

    private void fieldsValidator(IndividualDTO individualDTO, Individual individual,int index) {

        individualValidator(individualDTO,individual);

        Address address1 = individual.getAddresses().get(index);
        AddressDTO addressDTO1 = individualDTO.getAddressesDTO().get(index);

        addressValidator(address1,addressDTO1);

        Individual individual1 = address1.getIndividual();
        IndividualDTO individualDTO1 = addressDTO1.getIndividualDTO();

        individualValidator(individualDTO1,individual1);
    }

    private void individualValidator(IndividualDTO individualDTO, Individual individual) {

        Assertions.assertEquals(individualDTO.getActive(),individual.getActive());
        Assertions.assertEquals(individualDTO.getBirthDate(),individual.getBirthDate());
        Assertions.assertEquals(individualDTO.getDocument(),individual.getDocument());
        Assertions.assertEquals(individualDTO.getMotherName(),individual.getMotherName());
        Assertions.assertEquals(individualDTO.getRg(),individual.getRg());
        Assertions.assertEquals(individualDTO.getName(),individual.getName());
    }

    private void addressValidator(Address address, AddressDTO addressDTO) {

        Assertions.assertEquals(addressDTO.getDistrict(),address.getDistrict());
        Assertions.assertEquals(addressDTO.getNumber(),address.getNumber());
        Assertions.assertEquals(addressDTO.getCity(),address.getCity());
        Assertions.assertEquals(addressDTO.getState(),address.getState());
        Assertions.assertEquals(addressDTO.getZipCode(),address.getZipCode());
        Assertions.assertEquals(addressDTO.getMain(),address.getMain());
    }
}