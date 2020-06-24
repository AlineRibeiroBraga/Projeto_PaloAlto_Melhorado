package br.com.invillia.projetoPaloAlto.unitTest.legalEntityTest;

import br.com.invillia.projetoPaloAlto.unitTest.individualTest.IndividualMapperTest;
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
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
public class LegalEntityInsertTest {

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
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.faker = new Faker();
        this.legalEntityMapperTest = new LegalEntityMapperTest();
        this.individualMapperTest = new IndividualMapperTest();
    }


    @Test
    public void InsertOkayWithIndividualsSaved(){

        LegalEntityDTO legalEntityDTO = legalEntityMapperTest.createLegalEntityDTO();
        legalEntityDTO.setIndividualsDTO(individualMapperTest.createListIndividualsDTO());
        Individual individual = individualMapper.individualDTOToIndividual(legalEntityDTO.getIndividualsDTO().get(0));
        individual.setId(1L);

        when(legalEntityRepository.existsByDocument(legalEntityDTO.getDocument())).thenReturn(false);
        when(individualRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.of(individual));
        when(individualRepository.findByRg(Mockito.anyString())).thenReturn(Optional.of(individual));
        when(legalEntityRepository.save(Mockito.any(LegalEntity.class))).thenReturn(legalEntityMapperTest.createLegalEntity(1L));

        Long id = Long.valueOf(legalEntityService.insert(legalEntityDTO).getResponse());

        Assert.assertNotNull(id);
        Mockito.verify(legalEntityRepository,times(1)).save(Mockito.any(LegalEntity.class));
    }

    @Test
    public void InsertOkayWithInvalidedRgIndividuals(){

        LegalEntityDTO legalEntityDTO = legalEntityMapperTest.createLegalEntityDTO();
        legalEntityDTO.setIndividualsDTO(individualMapperTest.createListIndividualsDTO());

        when(legalEntityRepository.existsByDocument(legalEntityDTO.getDocument())).thenReturn(false);
        when(individualRepository.findByDocument(Mockito.anyString()))
                .thenReturn(Optional.of(individualMapperTest.createIndividual(1L)));
        when(individualRepository.findByRg(Mockito.anyString())).thenReturn(Optional.empty());

       Assertions.assertThrows(IndividualException.class, () -> legalEntityService.insert(legalEntityDTO));
    }

    @Test
    public void InsertOkayWithInvalidedDocumentIndividuals(){

        LegalEntityDTO legalEntityDTO = legalEntityMapperTest.createLegalEntityDTO();
        legalEntityDTO.setIndividualsDTO(individualMapperTest.createListIndividualsDTO());

        when(legalEntityRepository.existsByDocument(legalEntityDTO.getDocument())).thenReturn(false);
        when(individualRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.empty());
        when(individualRepository.findByRg(Mockito.anyString()))
                .thenReturn(Optional.of(individualMapperTest.createIndividual(1L)));

        Assertions.assertThrows(IndividualException.class, () -> legalEntityService.insert(legalEntityDTO));
    }

    @Test
    public void InsertOkayWithIndividualsNotSave(){

        LegalEntityDTO legalEntityDTO = legalEntityMapperTest.createLegalEntityDTO();
        legalEntityDTO.setIndividualsDTO(individualMapperTest.createListIndividualsDTO());

        when(legalEntityRepository.existsByDocument(legalEntityDTO.getDocument())).thenReturn(false);
        when(legalEntityRepository.save(Mockito.any(LegalEntity.class)))
                .thenReturn(legalEntityMapperTest.createLegalEntity(1L));
        when(individualRepository.findByDocument(Mockito.anyString())).thenReturn(Optional.empty());

        Long id = Long.valueOf(legalEntityService.insert(legalEntityDTO).getResponse());

        Assert.assertNotNull(id);
        Mockito.verify(legalEntityRepository,times(1)).save(Mockito.any(LegalEntity.class));
    }

    @Test
    public void insertDocumentAreadyExists(){

        LegalEntityDTO legalEntityDTO = legalEntityMapperTest.createLegalEntityDTO();

        when(legalEntityRepository.existsByDocument(legalEntityDTO.getDocument())).thenReturn(true);

        Assertions.assertThrows(LegalEntityException.class, ()-> legalEntityService.insert(legalEntityDTO));
    }

    @Test
    public void inserMainAddressValidator(){

        LegalEntityDTO legalEntityDTO = legalEntityMapperTest.createLegalEntityDTO();
        legalEntityDTO.getAddressesDTO().add(legalEntityMapperTest.createAddressDTO(true));

        when(legalEntityRepository.existsByDocument(legalEntityDTO.getDocument())).thenReturn(false);

        Assertions.assertThrows(AddressException.class, () -> legalEntityService.insert(legalEntityDTO));
    }

    @Test
    public void InsertOkayWithoutIndividuals(){

        LegalEntityDTO legalEntityDTO = legalEntityMapperTest.createLegalEntityDTO();

        when(legalEntityRepository.existsByDocument(legalEntityDTO.getDocument())).thenReturn(false);
        when(legalEntityRepository.save(Mockito.any(LegalEntity.class)))
                .thenReturn(legalEntityMapperTest.createLegalEntity(1L));

        Long id = Long.valueOf(legalEntityService.insert(legalEntityDTO).getResponse());

        Assert.assertNotNull(id);
        Mockito.verify(legalEntityRepository,times(1)).save(Mockito.any(LegalEntity.class));
    }
}