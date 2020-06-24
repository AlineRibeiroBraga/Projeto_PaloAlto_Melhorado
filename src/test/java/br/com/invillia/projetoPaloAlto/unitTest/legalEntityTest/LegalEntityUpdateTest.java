package br.com.invillia.projetoPaloAlto.unitTest.legalEntityTest;

import br.com.invillia.projetoPaloAlto.unitTest.individualTest.IndividualMapperTest;
import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;
import br.com.invillia.projetoPaloAlto.domain.model.LegalEntity;
import br.com.invillia.projetoPaloAlto.exception.AddressException;
import br.com.invillia.projetoPaloAlto.exception.IndividualException;
import br.com.invillia.projetoPaloAlto.exception.LegalEntityException;
import br.com.invillia.projetoPaloAlto.mapper.AddressMapper;
import br.com.invillia.projetoPaloAlto.mapper.IndividualMapper;
import br.com.invillia.projetoPaloAlto.mapper.LegalEntityMapper;
import br.com.invillia.projetoPaloAlto.repository.IndividualRepository;
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
public class LegalEntityUpdateTest {

    private Faker faker;

    private LegalEntityMapperTest legalEntityMapperTest;

    private IndividualMapperTest individualMapperTest;

    @Mock
    private LegalEntityRepository legalEntityRepository;

    @Mock
    private IndividualRepository individualRepository;

    @Spy
    @InjectMocks
    private LegalEntityService legalEntityService;

    @Spy
    @InjectMocks
    private LegalEntityMapper legalEntityMapper;

    @Spy
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
    public void updateByDocumentExistsWithExistsAddressWithoutIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));

        String document = legalEntityService.updateByDocument(legalEntityDTO).getResponse();
        LegalEntityDTO legalEntityDTOUpdate = legalEntityService.findByDocument(document);

        fieldsValidator(legalEntityDTOUpdate,legalEntityDTO,0);

        verify(legalEntityService, times(1)).updateByDocument(legalEntityDTO);

    }

    @Test
    public void updateByDocumentExistsWithoutExistsAddressWithoutIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.getAddresses().add(legalEntityMapperTest.createAddress(2L,false));
        legalEntity.getAddresses().get(1).setLegalEntity(legalEntity);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));

        String document = legalEntityService.updateByDocument(legalEntityDTO).getResponse();
        LegalEntityDTO legalEntityDTOUpdate = legalEntityService.findByDocument(document);

        fieldsValidator(legalEntityDTOUpdate,legalEntityDTO,0);
        fieldsValidator(legalEntityDTOUpdate,legalEntityDTO,1);

        verify(legalEntityService, times(1)).updateByDocument(legalEntityDTO);
    }

    @Test
    public void updateByDocumentExistsWithExistsAddressWithIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1l);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().add(individualMapperTest.createIndividual(2L));
        legalEntity.getIndividuals().get(1).setId(null);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
        Individual individualR = individualMapperTest.newIndividual(legalEntity.getIndividuals().get(1));

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));
        when(individualRepository.findByDocument(individualR.getDocument())).thenReturn(Optional.of(individualR));
        when(individualRepository.findByRg(individualR.getRg())).thenReturn(Optional.of(individualR));

        String document = legalEntityService.updateByDocument(legalEntityDTO).getResponse();
        LegalEntityDTO legalEntityDTOUpdate = legalEntityService.findByDocument(document);

        fieldsValidator(legalEntityDTOUpdate,legalEntityDTO,0);

        fieldsPartnersValidator(legalEntityDTOUpdate,legalEntityDTO,0);
        fieldsPartnersValidator(legalEntityDTOUpdate,legalEntityDTO,1);

        verify(legalEntityService, times(1)).updateByDocument(legalEntityDTO);
    }

    @Test
    public void updateByDocumentNotFound(){

        LegalEntityDTO legalEntityDTO = legalEntityMapperTest.createLegalEntityDTO();

        when(legalEntityRepository.findByDocument(legalEntityDTO.getDocument())).thenReturn(Optional.empty());

        Assertions.assertThrows(LegalEntityException.class, () -> legalEntityService.updateByDocument(legalEntityDTO));
    }

    @Test
    public void updateByDocumentInvalidedDocument(){

        when(legalEntityRepository.findByDocument(Mockito.anyString()))
                .thenReturn(Optional.of(legalEntityMapperTest.createLegalEntity(1L)));

        Assertions.assertThrows(LegalEntityException.class,
                () -> legalEntityService.updateByDocument(legalEntityMapperTest.createLegalEntityDTO()));
    }

    @Test
    public void updateByDocumentNotAticve(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setActive(false);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(LegalEntityException.class, () -> legalEntityService.updateByDocument(legalEntityDTO));
    }

    @Test
    public void updateByDocumentMoreThanOneMainAddressLegalEntity(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.getAddresses().add(legalEntityMapperTest.createAddress(2L,true));
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(AddressException.class, ()->legalEntityService.updateByDocument(legalEntityDTO));
    }

    @Test
    public void updateByDocumentNoOneMainAddressLegalEntity(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.getAddresses().get(0).setMain(false);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(AddressException.class, ()->legalEntityService.updateByDocument(legalEntityDTO));
    }

    @Test
    public void updateByDocumentMoreThanOneMainAddressIndividual(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).getAddresses().add(legalEntityMapperTest.createAddress(2L,true));
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(AddressException.class, ()->legalEntityService.updateByDocument(legalEntityDTO));
    }

    @Test
    public void updateByDocumentNoOneMainAddressIndividual(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).getAddresses().get(0).setMain(false);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(AddressException.class, ()->legalEntityService.updateByDocument(legalEntityDTO));
    }

    @Test
    public void updateByDocumentInvalidedPartnersEmpty1(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).setId(null);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
        Individual individualR = individualMapperTest.newIndividual(legalEntity.getIndividuals().get(0));

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));
        when(individualRepository.findByDocument(individualR.getDocument())).thenReturn(Optional.empty());
        when(individualRepository.findByRg(individualR.getRg())).thenReturn(Optional.of(individualR));

        Assertions.assertThrows(IndividualException.class, ()-> legalEntityService.updateByDocument(legalEntityDTO));
    }

    @Test
    public void updateByDocumentInvalidedPartnersEmpty2(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).setId(null);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
        Individual individualR = individualMapperTest.newIndividual(legalEntity.getIndividuals().get(0));

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));
        when(individualRepository.findByDocument(individualR.getDocument())).thenReturn(Optional.of(individualR));
        when(individualRepository.findByRg(individualR.getRg())).thenReturn(Optional.empty());

        Assertions.assertThrows(IndividualException.class, ()-> legalEntityService.updateByDocument(legalEntityDTO));
    }

    @Test
    public void updateByDocumentInvalidedPartnersDifferent(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).setId(null);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
        Individual individual = legalEntity.getIndividuals().get(0);

        when(legalEntityRepository.findByDocument(legalEntity.getDocument())).thenReturn(Optional.of(legalEntity));
        when(individualRepository.findByDocument(individual.getDocument())).thenReturn(Optional.of(individual));
        when(individualRepository.findByRg(individual.getRg()))
                .thenReturn(Optional.of(individualMapperTest.createIndividual(2L)));

        Assertions.assertThrows(IndividualException.class, ()-> legalEntityService.updateByDocument(legalEntityDTO));
    }

    @Test
    public void updateByIdExistsWithExistsAddressWithoutIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));

        Long id = Long.valueOf(legalEntityService.updateById(1L,legalEntityDTO).getResponse());
        LegalEntityDTO legalEntityDTOUpdate = legalEntityService.findById(id);

        fieldsValidator(legalEntityDTOUpdate,legalEntityDTO,0);

        verify(legalEntityService, times(1)).updateById(1L,legalEntityDTO);
    }


    @Test
    public void updateByIdExistsWithoutExistsAddressWithoutIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.getAddresses().add(legalEntityMapperTest.createAddress(2L,false));
        legalEntity.getAddresses().get(1).setLegalEntity(legalEntity);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));

        Long id = Long.valueOf(legalEntityService.updateById(1L,legalEntityDTO).getResponse());
        LegalEntityDTO legalEntityDTOUpdate = legalEntityService.findById(id);

        fieldsValidator(legalEntityDTOUpdate,legalEntityDTO,0);
        fieldsValidator(legalEntityDTOUpdate,legalEntityDTO,1);

        verify(legalEntityService, times(1)).updateById(1L,legalEntityDTO);
    }

    @Test
    public void updateByIdExistsWithExistsAddressWithIndividuals(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().add(individualMapperTest.createIndividual(2L));
        legalEntity.getIndividuals().get(1).setId(null);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
        Individual individualR = individualMapperTest.newIndividual(legalEntity.getIndividuals().get(1));

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));
        when(individualRepository.findByDocument(individualR.getDocument())).thenReturn(Optional.of(individualR));
        when(individualRepository.findByRg(individualR.getRg())).thenReturn(Optional.of(individualR));

        Long id = Long.valueOf(legalEntityService.updateById(1L,legalEntityDTO).getResponse());
        LegalEntityDTO legalEntityDTOUpdate = legalEntityService.findById(id);

        fieldsValidator(legalEntityDTOUpdate,legalEntityDTO,0);

        fieldsPartnersValidator(legalEntityDTOUpdate,legalEntityDTO,0);
        fieldsPartnersValidator(legalEntityDTOUpdate,legalEntityDTO,1);

        verify(legalEntityService, times(1)).updateById(1L,legalEntityDTO);
    }

    @Test
    public void updateByIdNotFound(){

        LegalEntityDTO legalEntityDTO = legalEntityMapperTest.createLegalEntityDTO();

        when(legalEntityRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(LegalEntityException.class, () -> legalEntityService.updateById(1L,legalEntityDTO));
    }

    @Test
    public void updateByIdInvalidedId(){

        when(legalEntityRepository.findById(1L)).thenReturn(Optional.of(legalEntityMapperTest.createLegalEntity(1L)));

        Assertions.assertThrows(LegalEntityException.class,
                () -> legalEntityService.updateById(1L,legalEntityMapperTest.createLegalEntityDTO()));
    }

    @Test
    public void updateByIdNotAticve(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setActive(false);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(LegalEntityException.class, () -> legalEntityService.updateById(1L,legalEntityDTO));
    }

    @Test
    public void updateByIdMoreThanOneMainAddressLegalEntity(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.getAddresses().add(legalEntityMapperTest.createAddress(2L,true));
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(AddressException.class, ()->legalEntityService.updateById(1L,legalEntityDTO));
    }

    @Test
    public void updateByIdNoOneMainAddressLegalEntity(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.getAddresses().get(0).setMain(false);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(AddressException.class, ()->legalEntityService.updateById(1L,legalEntityDTO));
    }

    @Test
    public void updateByIdMoreThanOneMainAddressIndividual(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).getAddresses().add(legalEntityMapperTest.createAddress(2L,true));
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(AddressException.class, ()->legalEntityService.updateById(1L,legalEntityDTO));
    }

    @Test
    public void updateByIdNoOneMainAddressIndividual(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).getAddresses().get(0).setMain(false);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));

        Assertions.assertThrows(AddressException.class, ()->legalEntityService.updateById(1L,legalEntityDTO));
    }

    @Test
    public void updateByIdInvalidedPartnersEmpty1(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).setId(null);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
        Individual individualR = individualMapperTest.newIndividual(legalEntity.getIndividuals().get(0));

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));
        when(individualRepository.findByDocument(individualR.getDocument())).thenReturn(Optional.empty());
        when(individualRepository.findByRg(individualR.getRg())).thenReturn(Optional.of(individualR));

        Assertions.assertThrows(IndividualException.class, ()-> legalEntityService.updateById(1L,legalEntityDTO));
    }

    @Test
    public void updateByIdInvalidedPartnersEmpty2(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).setId(null);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
        Individual individualR = individualMapperTest.newIndividual(legalEntity.getIndividuals().get(0));

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));
        when(individualRepository.findByDocument(individualR.getDocument())).thenReturn(Optional.of(individualR));
        when(individualRepository.findByRg(individualR.getRg())).thenReturn(Optional.empty());

        Assertions.assertThrows(IndividualException.class, ()-> legalEntityService.updateById(1L,legalEntityDTO));
    }

    @Test
    public void updateByIdInvalidedPartnersDifferent(){

        LegalEntity legalEntity = legalEntityMapperTest.createLegalEntity(1L);
        legalEntity.setIndividuals(individualMapperTest.createListIndividual());
        legalEntity.getIndividuals().get(0).setId(null);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
        Individual individualR = individualMapperTest.newIndividual(legalEntity.getIndividuals().get(0));

        when(legalEntityRepository.findById(legalEntity.getId())).thenReturn(Optional.of(legalEntity));
        when(individualRepository.findByDocument(individualR.getDocument())).thenReturn(Optional.of(individualR));
        when(individualRepository.findByRg(individualR.getRg()))
                .thenReturn(Optional.of(individualMapperTest.createIndividual(2L)));

        Assertions.assertThrows(IndividualException.class, ()-> legalEntityService.updateById(1L,legalEntityDTO));
    }


    private void fieldsValidator(LegalEntityDTO legalEntityDTOUpdate, LegalEntityDTO legalEntityDTO, int index) {

        legalEntityDTOValidator(legalEntityDTOUpdate,legalEntityDTO);

        AddressDTO addressDTOUpdate1 = legalEntityDTOUpdate.getAddressesDTO().get(index);
        AddressDTO addressDTO1 = legalEntityDTO.getAddressesDTO().get(index);

        addressDTOValidator(addressDTOUpdate1,addressDTO1);

        LegalEntityDTO legalEntityDTOUpdate1 = addressDTOUpdate1.getLegalEntityDTO();
        LegalEntityDTO legalEntityDTO1 = addressDTO1.getLegalEntityDTO();

        legalEntityDTOValidator(legalEntityDTOUpdate1,legalEntityDTO1);
    }

    private void fieldsPartnersValidator(LegalEntityDTO legalEntityDTOUpdate, LegalEntityDTO legalEntityDTO, int index) {

        IndividualDTO individualDTOUpdate1 = legalEntityDTOUpdate.getIndividualsDTO().get(index);
        IndividualDTO individualDTO1 = legalEntityDTO.getIndividualsDTO().get(index);

        individualValidator(individualDTOUpdate1,individualDTO1);

        AddressDTO addressDTOUpdate11 = individualDTOUpdate1.getAddressesDTO().get(0);
        AddressDTO addressDTO11 = individualDTO1.getAddressesDTO().get(0);

        addressDTOValidator(addressDTOUpdate11,addressDTO11);

        IndividualDTO individualDTOUpdate11 = addressDTOUpdate11.getIndividualDTO();
        IndividualDTO individualDTO11 = addressDTO11.getIndividualDTO();

        individualValidator(individualDTOUpdate11,individualDTO11);
    }

    private void individualValidator(IndividualDTO individualDTOUpdate, IndividualDTO individualDTO) {

        Assertions.assertEquals(individualDTOUpdate.getName(),individualDTO.getName());
        Assertions.assertEquals(individualDTOUpdate.getMotherName(),individualDTO.getMotherName());
    }

    private void addressDTOValidator(AddressDTO addressDTOUpdate, AddressDTO addressDTO) {

        Assertions.assertEquals(addressDTOUpdate.getMain(), addressDTO.getMain());
        Assertions.assertEquals(addressDTOUpdate.getDistrict(), addressDTO.getDistrict());
        Assertions.assertEquals(addressDTOUpdate.getNumber(), addressDTO.getNumber());
        Assertions.assertEquals(addressDTOUpdate.getCity(), addressDTO.getCity());
        Assertions.assertEquals(addressDTOUpdate.getState(), addressDTO.getState());
        Assertions.assertEquals(addressDTOUpdate.getZipCode(), addressDTO.getZipCode());
    }

    private void legalEntityDTOValidator(LegalEntityDTO legalEntityDTOUpdate, LegalEntityDTO legalEntityDTO) {

        Assertions.assertEquals(legalEntityDTOUpdate.getActive(),legalEntityDTO.getActive());
        Assertions.assertEquals(legalEntityDTOUpdate.getName(),legalEntityDTO.getName());
        Assertions.assertEquals(legalEntityDTOUpdate.getTradeName(),legalEntityDTO.getTradeName());
    }
}