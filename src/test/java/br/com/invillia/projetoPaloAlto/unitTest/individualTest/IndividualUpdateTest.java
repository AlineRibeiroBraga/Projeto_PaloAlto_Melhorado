package br.com.invillia.projetoPaloAlto.unitTest.individualTest;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;
import br.com.invillia.projetoPaloAlto.exception.AddressException;
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
public class IndividualUpdateTest {

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
    public void updateByDocument(){

        Individual individual = individualMapperTest.createIndividual(1L);
        IndividualDTO individualDTO = individualMapper.individualToIndividualDTO(individual);

        when(individualRepository.findByDocument(individualDTO.getDocument())).thenReturn(Optional.of(individual));

        String document = individualService.updateByDocument(individualDTO).getResponse();
        IndividualDTO individualDTOUpdate = individualService.findByDocument(document);

        fieldsValidator(individualDTOUpdate,individualDTO);

        verify(individualService,times(1)).updateByDocument(individualDTO);
    }

    @Test
    public void updateByDocumentNotExists(){

        when(individualRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(IndividualException.class,
                () -> individualService.updateByDocument(individualMapperTest.createIndividualDTO()));
    }

    @Test
    public void updateByDocumentInvalided(){

        when(individualRepository.findByDocument(Mockito.anyString()))
                .thenReturn(Optional.of(individualMapperTest.createIndividual(1L)));

        Assertions.assertThrows(IndividualException.class,
                () -> individualService.updateByDocument(individualMapperTest.createIndividualDTO()));
    }

    @Test
    public void updateByDocumentNotActive(){
        Individual individual = individualMapperTest.createIndividual(1L);
        individual.setActive(false);
        IndividualDTO individualDTO = individualMapper.individualToIndividualDTO(individual);

        when(individualRepository.findByDocument(individual.getDocument())).thenReturn(Optional.of(individual));

        Assertions.assertThrows(IndividualException.class,
                () -> individualService.updateByDocument(individualDTO));
    }

    @Test
    public void updateByDocumentMoreThanOneMainAddress(){

        Individual individual = individualMapperTest.createIndividual(1L);
        individual.getAddresses().add(individualMapperTest.createAddress(2L,true));
        IndividualDTO individualDTO = individualMapper.individualToIndividualDTO(individual);

        when(individualRepository.findByDocument(individual.getDocument())).thenReturn(Optional.of(individual));

        Assertions.assertThrows(AddressException.class,
                () -> individualService.updateByDocument(individualDTO));
    }

    @Test
    public void updateByDocumentNoMainAddress(){

        Individual individual = individualMapperTest.createIndividual(1L);
        IndividualDTO individualDTO = individualMapper.individualToIndividualDTO(individual);

        individualDTO.getAddressesDTO().get(0).setMain(false);

        when(individualRepository.findByDocument(individual.getDocument())).thenReturn(Optional.of(individual));

        Assertions.assertThrows(AddressException.class,
                () -> individualService.updateByDocument(individualDTO));
    }

    @Test
    public void updateById(){

        Individual individual = individualMapperTest.createIndividual(1L);
        IndividualDTO individualDTO = individualMapper.individualToIndividualDTO(individual);

        when(individualRepository.findById(individual.getId())).thenReturn(Optional.of(individual));

        Long id = Long.valueOf(individualService.updateById(individual.getId(),individualDTO).getResponse());
        IndividualDTO individualDTOUpdate = individualService.findById(id);

        fieldsValidator(individualDTOUpdate,individualDTO);

        verify(individualService,times(1)).updateById(individual.getId(),individualDTO);
    }

    @Test
    public void updateByIdNotExists(){

        when(individualRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(IndividualException.class,
                () -> individualService.updateById(1L, individualMapperTest.createIndividualDTO()));
    }

    @Test
    public void updateByIdDocumentInvalided(){

        when(individualRepository.findById(1L)).thenReturn(Optional.of(individualMapperTest.createIndividual(1L)));

        Assertions.assertThrows(IndividualException.class,
                () -> individualService.updateById(1L, individualMapperTest.createIndividualDTO()));
    }

    @Test
    public void updateByIdNotActive(){
        Individual individual = individualMapperTest.createIndividual(1L);
        individual.setActive(false);
        IndividualDTO individualDTO = individualMapper.individualToIndividualDTO(individual);

        when(individualRepository.findById(1L)).thenReturn(Optional.of(individual));

        Assertions.assertThrows(IndividualException.class,
                () -> individualService.updateById(1L,individualDTO));
    }

    @Test
    public void updateByIdMoreThanOneMainAddress(){

        Individual individual = individualMapperTest.createIndividual(1L);
        individual.getAddresses().add(individualMapperTest.createAddress(2L,true));
        IndividualDTO individualDTO = individualMapper.individualToIndividualDTO(individual);

        when(individualRepository.findById(individual.getId())).thenReturn(Optional.of(individual));

        Assertions.assertThrows(AddressException.class,
                () -> individualService.updateById(individual.getId(),individualDTO));
    }

    @Test
    public void updateByIdNoMainAddress(){

        Individual individual = individualMapperTest.createIndividual(1L);
        IndividualDTO individualDTO = individualMapper.individualToIndividualDTO(individual);

        individualDTO.getAddressesDTO().get(0).setMain(false);

        when(individualRepository.findById(individual.getId())).thenReturn(Optional.of(individual));

        Assertions.assertThrows(AddressException.class,
                () -> individualService.updateById(individual.getId(),individualDTO));
    }

    private void fieldsValidator(IndividualDTO individualDTOUpdate, IndividualDTO individualDTO) {

        individualsDTOValidator(individualDTOUpdate,individualDTO);

        AddressDTO addressDTOUpdate1 = individualDTOUpdate.getAddressesDTO().get(0);
        AddressDTO addressDTO1 = individualDTO.getAddressesDTO().get(0);

        addressesDTOValidator(addressDTOUpdate1,addressDTO1);

        IndividualDTO individualDTOUpdate1 = addressDTOUpdate1.getIndividualDTO();
        IndividualDTO individualDTO1 = addressDTO1.getIndividualDTO();

        individualsDTOValidator(individualDTOUpdate1,individualDTO1);
    }

    private void individualsDTOValidator(IndividualDTO individualDTOUpdate, IndividualDTO individual) {

        Assertions.assertEquals(individualDTOUpdate.getActive(),individual.getActive());
        Assertions.assertEquals(individualDTOUpdate.getName(),individual.getName());
        Assertions.assertEquals(individualDTOUpdate.getDocument(),individual.getDocument());
        Assertions.assertEquals(individualDTOUpdate.getBirthDate(),individual.getBirthDate());
        Assertions.assertEquals(individualDTOUpdate.getMotherName(),individual.getMotherName());
    }

    private void addressesDTOValidator(AddressDTO addressDTOUpdate, AddressDTO address) {

        Assertions.assertEquals(addressDTOUpdate.getMain(), address.getMain());
        Assertions.assertEquals(addressDTOUpdate.getDistrict(), address.getDistrict());
        Assertions.assertEquals(addressDTOUpdate.getNumber(),address.getNumber());
        Assertions.assertEquals(addressDTOUpdate.getCity(),address.getCity());
        Assertions.assertEquals(addressDTOUpdate.getState(),address.getState());
        Assertions.assertEquals(addressDTOUpdate.getZipCode(),address.getZipCode());
    }
}