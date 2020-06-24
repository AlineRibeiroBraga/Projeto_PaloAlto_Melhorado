package br.com.invillia.projetoPaloAlto.repository;

import br.com.invillia.projetoPaloAlto.domain.model.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long> {

    Boolean existsByDocument(String document);

    Optional<LegalEntity> findByDocument(String document);
}
