package br.com.invillia.projetoPaloAlto.api.rest;

import br.com.invillia.projetoPaloAlto.anotation.IsCNPJ;
import br.com.invillia.projetoPaloAlto.domain.dto.LegalEntityDTO;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;
import br.com.invillia.projetoPaloAlto.domain.model.LegalEntity;
import br.com.invillia.projetoPaloAlto.domain.response.Response;
import br.com.invillia.projetoPaloAlto.repository.LegalEntityRepository;
import br.com.invillia.projetoPaloAlto.service.LegalEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/legal-entity")
public class LegalEntityController {

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired LegalEntityRepository legalEntityRepository;

    @PostMapping
    public Response insert(@RequestBody @Valid LegalEntityDTO legalEntityDTO){
        return legalEntityService.insert(legalEntityDTO);
    }

    @GetMapping("/document/{document}")
    public LegalEntityDTO findByDocument(@PathVariable  @IsCNPJ String document){
        return legalEntityService.findByDocument(document);
    }

    @GetMapping("/{id}")
    public LegalEntityDTO findByDocument(@PathVariable Long id){
        return legalEntityService.findById(id);
    }

    @DeleteMapping("/document/{document}")
    public Response deleteByDocument(@PathVariable String document){
        return legalEntityService.deleteByDocument(document);
    }

    @DeleteMapping("/{id}")
    public Response deleteById(@PathVariable Long id){
        return legalEntityService.deleteById(id);
    }

    @PutMapping("/document")
    public Response updateByDocument(@RequestBody LegalEntityDTO legalEntityDTO){
        return legalEntityService.updateByDocument(legalEntityDTO);
    }

    @PutMapping("/{id}")
    public Response updateById(@RequestBody LegalEntityDTO legalEntityDTO, @PathVariable Long id){
        return legalEntityService.updateById(id,legalEntityDTO);
    }

    @GetMapping("/todos")
    public List<LegalEntity> findAll(){
        return legalEntityRepository.findAll();
    }
}
