package br.com.invillia.projetoPaloAlto.unitTest.legalEntityTest;

import br.com.invillia.projetoPaloAlto.unitTest.individualTest.IndividualMapperTest;
import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Address;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;
import br.com.invillia.projetoPaloAlto.domain.model.LegalEntity;
import br.com.invillia.projetoPaloAlto.exception.LegalEntityException;
import br.com.invillia.projetoPaloAlto.mapper.AddressMapper;
import br.com.invillia.projetoPaloAlto.mapper.IndividualMapper;
import br.com.invillia.projetoPaloAlto.mapper.LegalEntityMapper;
import br.com.invillia.projetoPaloAlto.repository.LegalEntityRepository;
import br.com.invillia.projetoPaloAlto.service.LegalEntityService;
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
public class LegalEntityFindByTest {

    private Faker faker;

    private LegalEntityMapperTest legalEntityMapperTest;

    private IndividualMapperTest individualMapperTest;

    @Mock
    private LegalEntityRepository legalEntityRepository;

    @Spy
    @InjectMocks
    private LegalEntityService legalEntityService;

    @Spy
    @InjectMocks
    private LegalEntityMapper legalEntityMapper;

    @Spy
    @InjectMocks
    private AddressMapper addressMapper;

    @Spy
    @InjectMocks
    private IndividualMapper individualMapper;

    @BeforeAll
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.faker = new Faker();
        this.legalEntityMapperTest = new LegalEntityMapperTest();
        this.individualMapperTest = new IndividualMapperTest();
    }

    @Test
    public void findByIdExistsWithoutIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);

        when(legalEntityRepository.findById(1L)).thenReturn(Optional.of(legalEntity));

        LegalEntityDTO legalEntityDTO = legalEntityService.findById(1L);

        fieldsValidator(legalEntityDTO,legalEntity,0);

        verify(legalEntityRepository,times(1)).findById(1L);
    }

    @Test
    public void findByIdExistsWithIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());

        when(legalEntityRepository.findById(1L)).thenReturn(Optional.of(legalEntity));

        LegalEntityDTO legalEntityDTO = legalEntityService.findById(1L);

        fieldsValidator(legalEntityDTO,legalEntity,0);
        fieldsPartnersValidator(legalEntityDTO,legalEntity,0);

        verify(legalEntityRepository,times(1)).findById(1L);
    }

    @Test
    public void findByIdNotExists(){

        when(legalEntityRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(LegalEntityException.class, ()-> legalEntityService.findById(1L));
    }

    @Test
    public void findByDocumentExistsWithoutIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));

        LegalEntityDTO legalEntityDTO = legalEntityService.findByDocument(legalEntity.getDocument());

        fieldsValidator(legalEntityDTO,legalEntity,0);

        verify(legalEntityRepository,times(1)).findByDocument(legalEntity.getDocument());
    }

    @Test
    public void findByDocumentExistsWithIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));

        LegalEntityDTO legalEntityDTO = legalEntityService.findByDocument(legalEntity.getDocument());

        fieldsValidator(legalEntityDTO,legalEntity,0);
        fieldsPartnersValidator(legalEntityDTO,legalEntity,0);

        verify(legalEntityRepository,times(1)).findByDocument(legalEntity.getDocument());
    }

    @Test
    public void findByDocumentNotExists(){

        when(legalEntityRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(LegalEntityException.class, ()-> legalEntityService.findByDocument(Mockito.anyString()));
    }

    private void fieldsValidator(LegalEntityDTO legalEntityDTO, LegalEntity legalEntity,int index) {

        legalEntityValidator(legalEntityDTO,legalEntity);

        Address address1 = legalEntity.getAddresses().get(index);
        AddressDTO addressDTO1 = legalEntityDTO.getAddressesDTO().get(index);

        addressesValidator(addressDTO1,address1);

        LegalEntity legalEntity1 = address1.getLegalEntity();
        LegalEntityDTO legalEntityDTO1 = addressDTO1.getLegalEntityDTO();

        legalEntityValidator(legalEntityDTO1,legalEntity1);
    }

    private void fieldsPartnersValidator(LegalEntityDTO legalEntityDTO, LegalEntity legalEntity, int index){

        Individual individual1 = legalEntity.getIndividuals().get(index);
        IndividualDTO individualDTO1 = legalEntityDTO.getIndividualsDTO().get(index);

        individualsValidator(individualDTO1,individual1);

        Address address11 = individual1.getAddresses().get(0);
        AddressDTO addressDTO11 = individualDTO1.getAddressesDTO().get(0);

        addressesValidator(addressDTO11,address11);
    }

    private void legalEntityValidator(LegalEntityDTO legalEntityDTO, LegalEntity legalEntity) {

        Assertions.assertEquals(legalEntityDTO.getActive(),legalEntity.getActive());
        Assertions.assertEquals(legalEntityDTO.getTradeName(),legalEntity.getTradeName());
        Assertions.assertEquals(legalEntityDTO.getName(),legalEntity.getName());
        Assertions.assertEquals(legalEntityDTO.getDocument(),legalEntity.getDocument());
    }

    private void addressesValidator(AddressDTO addressDTO, Address address) {

        Assertions.assertEquals(addressDTO.getMain(), address.getMain());
        Assertions.assertEquals(addressDTO.getDistrict(), address.getDistrict());
        Assertions.assertEquals(addressDTO.getNumber(),address.getNumber());
        Assertions.assertEquals(addressDTO.getCity(),address.getCity());
        Assertions.assertEquals(addressDTO.getState(),address.getState());
        Assertions.assertEquals(addressDTO.getZipCode(),address.getZipCode());
    }

    private void individualsValidator(IndividualDTO individualDTO, Individual individual) {

        Assertions.assertEquals(individualDTO.getActive(),individual.getActive());
        Assertions.assertEquals(individualDTO.getName(),individual.getName());
        Assertions.assertEquals(individualDTO.getDocument(),individual.getDocument());
        Assertions.assertEquals(individualDTO.getBirthDate(),individual.getBirthDate());
        Assertions.assertEquals(individualDTO.getMotherName(),individual.getMotherName());
    }
}