package br.com.invillia.projetoPaloAlto.mapper;

import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Address;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;
import br.com.invillia.projetoPaloAlto.mapper.interfaces.IndividualMapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class IndividualMapper implements IndividualMapperInterface {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public IndividualDTO toDTO(Individual individual) {
       return INSTANCE.toDTO(individual);
    }

    @Override
    public Individual toEntity(IndividualDTO individualDTO) {
        return INSTANCE.toEntity(individualDTO);
    }

    @Override
    public List<IndividualDTO> toListDTO(List<Individual> individuals) {
        return null;
    }

    @Override
    public List<Individual> toListEntity(List<IndividualDTO> addressesDTO) {
        return null;
    }

    public Individual individualDTOToIndividual(IndividualDTO individualDTO) {

        Individual individual = new Individual();

        individual.setDocument(individualDTO.getDocument());
        individual.setMotherName(individualDTO.getMotherName());
        individual.setRg(individualDTO.getRg());
        individual.setBirthDate(individualDTO.getBirthDate());
        individual.setName(individualDTO.getName());
        individual.setActive(individualDTO.getActive());
        individual.setCreatedAt(LocalDateTime.now());
        individual.setAddresses(addressMapper.listAddressDTOToListAddress(individualDTO.getAddressesDTO()));

        individual.getAddresses().forEach( address -> address.setIndividual(individual));

        return individual;
    }

    public List<Individual> listIndividualDTOToListIndividual(List<IndividualDTO> individualsDTO) {

        List<Individual> individuals = new ArrayList<>();

        individualsDTO.forEach(individualDTO -> individuals.add(individualDTOToIndividual(individualDTO)));

        return individuals;
    }


    public IndividualDTO individualToIndividualDTO(Individual individual) {

        IndividualDTO individualDTO = new IndividualDTO();

        individualDTO.setName(individual.getName());
        individualDTO.setRg(individual.getRg());
        individualDTO.setMotherName(individual.getMotherName());
        individualDTO.setDocument(individual.getDocument());
        individualDTO.setBirthDate(individual.getBirthDate());
        individualDTO.setActive(individual.getActive());
        individualDTO.setAddressesDTO(addressMapper.listAddressToListAddressDTO(individual.getAddresses()));

        individualDTO.getAddressesDTO().forEach( addressDTO -> addressDTO.setIndividualDTO(individualDTO));

        return individualDTO;
    }

    public List<IndividualDTO> listIndividualToListIndividualDTO(List<Individual> individuals) {

        List<IndividualDTO> individualsDTO = new ArrayList<>();

        individuals.forEach( individual -> individualsDTO.add(individualToIndividualDTO(individual)));

        return individualsDTO;
    }

    public void update(Individual individual, IndividualDTO individualDTO) {

        individual.setName(individualDTO.getName());
        individual.setMotherName(individualDTO.getMotherName());
        individual.setUpdatedAt(LocalDateTime.now());
        individual.setActive(true);

        addressMapper.update(individual.getAddresses(),individualDTO.getAddressesDTO());

        for(Address address : individual.getAddresses()){
            if(address.getIndividual() == null){
                address.setIndividual(individual);
            }
        }
    }

    public void partners(List<Individual> individuals, List<IndividualDTO> individualsDTO) {

        if(individualsDTO != null){

            Boolean flg;

            for(IndividualDTO individualDTO : individualsDTO){
                flg = false;
                for(Individual individual : individuals){
                    if(individualDTO.getDocument().equals(individual.getDocument()) &&
                            individualDTO.getRg().equals(individual.getRg())){
                        update(individual,individualDTO);
                        flg = true;
                    }
                }

                if(!flg){
                    individuals.add(individualDTOToIndividual(individualDTO));
                }
            }
        }
    }

    public void updateIndividual(Individual individual, Individual individualR) {

        individualR.setName(individual.getName());
        individualR.setMotherName(individual.getMotherName());
        individualR.setUpdatedAt(LocalDateTime.now());
        individualR.setActive(individual.getActive());

        addressMapper.updateAddress(individual.getAddresses(),individualR.getAddresses());

        individualR.getAddresses().forEach( address -> address.setIndividual(individualR));
    }


}