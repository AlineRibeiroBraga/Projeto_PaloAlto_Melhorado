package br.com.invillia.projetoPaloAlto.mapper;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.invillia.projetoPaloAlto.domain.model.LegalEntity;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;

@Component
public class LegalEntityMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private IndividualMapper individualMapper;

    public LegalEntity legalEntityDTOTolegalEntity(LegalEntityDTO legalEntityDTO) {

        LegalEntity legalEntity = new LegalEntity();

        legalEntity.setName(legalEntityDTO.getName());
        legalEntity.setDocument(legalEntityDTO.getDocument());
        legalEntity.setTradeName(legalEntityDTO.getTradeName());
        legalEntity.setCreatedAt(LocalDateTime.now());
        legalEntity.setActive(legalEntityDTO.getActive());
        legalEntity.setAddresses(addressMapper.listAddressDTOToListAddress(legalEntityDTO.getAddressesDTO()));

        addressMapper.setAddressLegalEntity(legalEntity.getAddresses(),legalEntity);
        setIndividualsLegalEntity(legalEntity,legalEntityDTO.getIndividualsDTO());

        return legalEntity;
    }

    private void setIndividualsLegalEntity(LegalEntity legalEntity, List<IndividualDTO> individualsDTO) {

        if(individualsDTO != null){
            legalEntity.setIndividuals(individualMapper.listIndividualDTOToListIndividual(individualsDTO));
        }
    }

    public List<LegalEntity> listLegalEntityDTOToListLegalEntity(List<LegalEntityDTO> legalEntitiesDTO) {

        List<LegalEntity> legalEntities = new ArrayList<>();

        for(LegalEntityDTO legalEntityDTO : legalEntitiesDTO){
            legalEntities.add(legalEntityDTOTolegalEntity(legalEntityDTO));
        }

        return legalEntities;
    }

    public LegalEntityDTO legalEntityToLegalEntityDTO(LegalEntity legalEntity){

        LegalEntityDTO legalEntityDTO = new LegalEntityDTO();

        legalEntityDTO.setActive(legalEntity.getActive());
        legalEntityDTO.setName(legalEntity.getName());
        legalEntityDTO.setDocument(legalEntity.getDocument());
        legalEntityDTO.setTradeName(legalEntity.getTradeName());
        legalEntityDTO.setAddressesDTO(addressMapper.listAddressToListAddressDTO(legalEntity.getAddresses()));

        for(AddressDTO addressDTO : legalEntityDTO.getAddressesDTO()){
            addressDTO.setLegalEntityDTO(legalEntityDTO);
        }

        if(legalEntity.getIndividuals() != null){
            legalEntityDTO.setIndividualsDTO(individualMapper.listIndividualToListIndividualDTO(legalEntity.getIndividuals()));
        }

        return legalEntityDTO;
    }

    public List<LegalEntityDTO> listLegalEntityToListLegalEntityDTO(List<LegalEntity> legalEntities) {

        List<LegalEntityDTO> legalEntitiesDTO = new ArrayList<>();

        for(LegalEntity legalEntity : legalEntities){
            legalEntitiesDTO.add(legalEntityToLegalEntityDTO(legalEntity));
        }

        return legalEntitiesDTO;
    }

    public void update(LegalEntity legalEntity, LegalEntityDTO legalEntityDTO) {

        legalEntity.setName(legalEntityDTO.getName());
        legalEntity.setTradeName(legalEntityDTO.getTradeName());

        addressMapper.update(legalEntity.getAddresses(),legalEntityDTO.getAddressesDTO());

        for(Address address : legalEntity.getAddresses()){
            if(address.getIndividual() == null){
                address.setLegalEntity(legalEntity);
            }
        }

        individualMapper.partners(legalEntity.getIndividuals(),legalEntityDTO.getIndividualsDTO());
    }
}
