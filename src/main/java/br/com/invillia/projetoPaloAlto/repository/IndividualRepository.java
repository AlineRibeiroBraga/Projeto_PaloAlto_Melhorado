package br.com.invillia.projetoPaloAlto.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.invillia.projetoPaloAlto.domain.model.Individual;

import java.util.Optional;
import java.util.concurrent.Future;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long> {

    Boolean existsByDocument(String document);

    Boolean existsByRg(String rg);

    Optional<Individual> findByDocument(String document);

    @Query(value = "select count (idt_legal_entity) > 1 from individual_legal_entity " +
            "where idt_individual = ?1 group by idt_individual", nativeQuery = true)
    Boolean findLegalEntityById(Long id);

    Optional<Individual> findByRg(String rg);

    String findNameById(Long id);
}
