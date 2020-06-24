package br.com.invillia.projetoPaloAlto.api.graphql;

import br.com.invillia.projetoPaloAlto.domain.dto.IndividualDTO;
import br.com.invillia.projetoPaloAlto.service.IndividualService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class individualGraphQl  implements GraphQLQueryResolver {

    @Autowired
    private IndividualService individualService;

    public IndividualDTO findIndividualDTOById(@PathVariable Long id) {
        return individualService.findIndividualDTOById(id);
    }
}
