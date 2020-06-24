package br.com.invillia.projetoPaloAlto.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.com.invillia.projetoPaloAlto.anotation.IsCPF;
import static org.apache.commons.lang3.StringUtils.length;
import static org.apache.logging.log4j.util.Strings.isBlank;

public class CPFValidator implements ConstraintValidator<IsCPF, String>{

    @Override
    public void initialize(final IsCPF constraintAnnotation) {
        // initialize
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return !isBlank(value) && length(value) == 11;
    }
}