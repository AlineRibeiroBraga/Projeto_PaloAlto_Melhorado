package br.com.invillia.projetoPaloAlto.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import br.com.invillia.projetoPaloAlto.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByNumber(String number);
}
