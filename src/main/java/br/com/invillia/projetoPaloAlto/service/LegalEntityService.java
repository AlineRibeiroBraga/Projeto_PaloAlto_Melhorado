package br.com.invillia.projetoPaloAlto.service;

import br.com.invillia.projetoPaloAlto.domain.dto.AddressDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;
import br.com.invillia.projetoPaloAlto.domain.model.LegalEntity;
import br.com.invillia.projetoPaloAlto.domain.response.Response;
import br.com.invillia.projetoPaloAlto.exception.AddressException;
import br.com.invillia.projetoPaloAlto.exception.IndividualException;
import br.com.invillia.projetoPaloAlto.exception.LegalEntityException;
import br.com.invillia.projetoPaloAlto.mapper.IndividualMapper;
import br.com.invillia.projetoPaloAlto.mapper.LegalEntityMapper;
import br.com.invillia.projetoPaloAlto.repository.IndividualRepository;
import br.com.invillia.projetoPaloAlto.repository.LegalEntityRepository;
import br.com.invillia.projetoPaloAlto.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LegalEntityService {

    @Autowired
    LegalEntityRepository legalEntityRepository;

    @Autowired
    LegalEntityMapper legalEntityMapper;

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    IndividualMapper individualMapper;

    public Response insert(LegalEntityDTO legalEntityDTO) {

        if(legalEntityRepository.existsByDocument(legalEntityDTO.getDocument())) {
            throw new LegalEntityException(Messages.DOCUMENT_ALREADY_EXISTS);
        }

        if(!mainAddressValidator(legalEntityDTO.getAddressesDTO())) {
            throw new AddressException(Messages.MUCH_MAIN_ADDRESS);
        }

        if(!legalEntityDTO.getActive()){
            throw new LegalEntityException(Messages.FALSE_LEGAL_ENTITY);
        }

        if(!addressPartners(legalEntityDTO.getIndividualsDTO())){
            throw new IndividualException(Messages.INVALIDATED_INDIVIDUAL);
        }

        LegalEntity legalEntity = legalEntityMapper.legalEntityDTOTolegalEntity(legalEntityDTO);

        if(!partners(legalEntity.getIndividuals())){
            throw new IndividualException(Messages.INVALIDATED_INDIVIDUAL);
        }

        return convertObject(legalEntityRepository.save(legalEntity).getId().toString());
    }

    private Boolean addressPartners(List<IndividualDTO> individualsDTO) {

        if(individualsDTO == null){
            return true;
        }

        for(IndividualDTO individualDTO : individualsDTO){
            if(!mainAddressValidator(individualDTO.getAddressesDTO())){
                return false;
            }
        }

        return true;
    }

    public LegalEntityDTO findById(Long id) {
        LegalEntity legalEntity = legalEntityRepository.findById(id)
                .orElseThrow(() -> new LegalEntityException(Messages.LEGAL_ENTITY_WAS_NOT_FOUND));

        return legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
    }

    public LegalEntityDTO findByDocument(String document) {
        LegalEntity legalEntity = legalEntityRepository.findByDocument(document)
                .orElseThrow(()->new LegalEntityException(Messages.LEGAL_ENTITY_WAS_NOT_FOUND));

        return legalEntityMapper.legalEntityToLegalEntityDTO(legalEntity);
    }

    public Response deleteByDocument(String document) {

        LegalEntity legalEntity = legalEntityRepository.findByDocument(document).
                orElseThrow(() -> new LegalEntityException(Messages.LEGAL_ENTITY_WAS_NOT_FOUND));

        deleteIndividuals(legalEntity);

        if(legalEntity.getActive()){
            legalEntity.setActive(false);

            return convertObject(legalEntityRepository.save(legalEntity).getDocument());
        }

        throw new LegalEntityException(Messages.LEGAL_ENTITY_WAS_ALREADY_DELETED);
    }

    public Response deleteById(Long id) {

        LegalEntity legalEntity = legalEntityRepository.findById(id).
                orElseThrow(() -> new LegalEntityException(Messages.LEGAL_ENTITY_WAS_NOT_FOUND));

        deleteIndividuals(legalEntity);

        if(legalEntity.getActive()){
            legalEntity.setActive(false);

            return convertObject(legalEntityRepository.save(legalEntity).getId().toString());
        }

        throw new LegalEntityException(Messages.LEGAL_ENTITY_WAS_ALREADY_DELETED);
    }

    private void deleteIndividuals(LegalEntity legalEntity) {

        if(legalEntity.getIndividuals() != null){

            for (Individual individual : legalEntity.getIndividuals()){

                if(!individualRepository.findLegalEntityById(individual.getId())){
                    if(individual.getActive()){
                        individual.setActive(false);
                    }
                }
            }
        }
    }

    public Response updateByDocument(LegalEntityDTO legalEntityDTO) {

        LegalEntity legalEntity = legalEntityRepository.findByDocument(legalEntityDTO.getDocument())
                .orElseThrow(() -> new LegalEntityException(Messages.LEGAL_ENTITY_WAS_NOT_FOUND));

        if(!legalEntityDTO.getDocument().equals(legalEntity.getDocument()) || !legalEntity.getActive()){
            throw new LegalEntityException(Messages.INVALIDATED_LEGAL_ENTITY);
        }

        if(!mainAddressValidator(legalEntityDTO.getAddressesDTO()) ||
                !addressesIndividualsValidator(legalEntityDTO.getIndividualsDTO())) {
            throw new AddressException(Messages.MUCH_MAIN_ADDRESS);
        }

        legalEntityMapper.update(legalEntity,legalEntityDTO);

        if(!partners(legalEntity.getIndividuals())){
            throw new IndividualException(Messages.INVALIDATED_INDIVIDUAL);
        }

        legalEntityRepository.save(legalEntity);

        return convertObject(legalEntity.getDocument());
    }

    private Boolean partners(List<Individual> individuals) {

        if(individuals == null){
            return true;
        }

        int tam = individuals.size();

        Optional<Individual> individual1;
        Optional<Individual> individual2;

        for(int i=0; i < tam; i++){
            Individual individual = individuals.get(i);

            if(!individual.getActive()) return false;

            if(individual.getId() == null){
                individual1 = individualRepository.findByDocument(individual.getDocument());
                individual2 = individualRepository.findByRg(individual.getRg());

                if(!individual1.isEmpty() && individual2.isEmpty()) return false;
                if(individual1.isEmpty() && !individual2.isEmpty()) return false;

                if(!individual1.isEmpty() && !individual2.isEmpty()) {
                    if (!individual1.get().getDocument().equals(individual2.get().getDocument()) ||
                            !individual1.get().getRg().equals(individual2.get().getRg()))
                        return false;

                    Individual individualR1 = individual1.get();

                    individualMapper.updateIndividual(individual, individualR1);
                    individuals.remove(i);
                    individuals.add(i, individualR1);
                }
            }
        }

        return true;
    }

    private boolean mainAddressValidator(List<AddressDTO> addressesDTO) {

        int main = 0;
        if(addressesDTO != null){

            for(AddressDTO addressDTO :  addressesDTO){
                if(addressDTO.getMain()){
                    ++main;
                }
            }
        }

        return main == 1;
    }

    private Boolean addressesIndividualsValidator(List<IndividualDTO> individualsDTO) {

        if(individualsDTO == null){
           return true;
        }

        for(IndividualDTO individual : individualsDTO){
            if(!mainAddressValidator(individual.getAddressesDTO())){
                return false;
            }
        }

        return true;
    }

    public Response updateById(Long id, LegalEntityDTO legalEntityDTO) {

        LegalEntity legalEntity = legalEntityRepository.findById(id)
                .orElseThrow(() -> new LegalEntityException(Messages.LEGAL_ENTITY_WAS_NOT_FOUND));

        if(!legalEntityDTO.getDocument().equals(legalEntity.getDocument()) || !legalEntity.getActive()){
            throw new LegalEntityException(Messages.INVALIDATED_LEGAL_ENTITY);
        }

        if(!mainAddressValidator(legalEntityDTO.getAddressesDTO()) ||
                !addressesIndividualsValidator(legalEntityDTO.getIndividualsDTO())) {
            throw new AddressException(Messages.MUCH_MAIN_ADDRESS);
        }

        legalEntityMapper.update(legalEntity,legalEntityDTO);

        if(!partners(legalEntity.getIndividuals())){
            throw new IndividualException(Messages.INVALIDATED_INDIVIDUAL);
        }

        legalEntityRepository.save(legalEntity);

        return convertObject(legalEntity.getId().toString());
    }

    private Response convertObject(String document) {
        return new Response(document);
    }
}