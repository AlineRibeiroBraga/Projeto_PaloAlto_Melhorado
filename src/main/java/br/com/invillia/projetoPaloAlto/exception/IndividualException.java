package br.com.invillia.projetoPaloAlto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IndividualException extends RuntimeException {

    public IndividualException(String title) {
        super(title);
    }
}
