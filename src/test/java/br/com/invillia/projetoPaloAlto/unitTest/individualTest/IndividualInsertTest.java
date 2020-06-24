package br.com.invillia.projetoPaloAlto.unitTest.individualTest;

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

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
public class IndividualInsertTest {

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
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.faker = new Faker();
        this.individualMapperTest = new IndividualMapperTest();
    }

    @Test
    public void insertOkay(){

        IndividualDTO individualDTO = individualMapperTest.createIndividualDTO();

        when(individualRepository.existsByDocument(individualDTO.getDocument())).thenReturn(false);
        when(individualRepository.existsByRg(individualDTO.getRg())).thenReturn(false);
        when(individualRepository.save(Mockito.any(Individual.class))).thenReturn(individualMapperTest.createIndividual(1L));

        Long id = Long.valueOf(individualService.insert(individualDTO).getResponse());

        Assertions.assertNotNull(id);
        verify(individualRepository,times(1)).save(Mockito.any(Individual.class));
    }

    @Test
    public void insertDocumentAlreadyExists(){

        IndividualDTO individualDTO = individualMapperTest.createIndividualDTO();

        when(individualRepository.existsByDocument(individualDTO.getDocument())).thenReturn(true);

        Assertions.assertThrows(IndividualException.class, () -> individualService.insert(individualDTO));
    }

    @Test
    public void insertRgAlreadyExists(){

        IndividualDTO individualDTO = individualMapperTest.createIndividualDTO();

        when(individualRepository.existsByDocument(individualDTO.getDocument())).thenReturn(false);
        when(individualRepository.existsByRg(individualDTO.getRg())).thenReturn(true);

        Assertions.assertThrows(IndividualException.class, () -> individualService.insert(individualDTO));
    }

    @Test
    public void insertMainAddressValidator(){

        IndividualDTO individualDTO = individualMapperTest.createIndividualDTO();
        individualDTO.getAddressesDTO().add(individualMapperTest.createAddressDTO(2L,true));

        when(individualRepository.existsByDocument(individualDTO.getDocument())).thenReturn(false);
        when(individualRepository.existsByRg(individualDTO.getRg())).thenReturn(false);

        Assertions.assertThrows(AddressException.class, () -> individualService.insert(individualDTO));
    }

    @Test
    public void insertMainAddressValidatorNoMainAddress(){

        IndividualDTO individualDTO = individualMapperTest.createIndividualDTO();
        individualDTO.getAddressesDTO().get(0).setMain(false);

        when(individualRepository.existsByDocument(individualDTO.getDocument())).thenReturn(false);
        when(individualRepository.existsByRg(individualDTO.getRg())).thenReturn(false);

        Assertions.assertThrows(AddressException.class, () -> individualService.insert(individualDTO));
    }
}