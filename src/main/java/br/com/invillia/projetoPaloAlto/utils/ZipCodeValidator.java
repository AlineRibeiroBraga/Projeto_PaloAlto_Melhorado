package br.com.invillia.projetoPaloAlto.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static org.apache.commons.lang3.StringUtils.length;
import br.com.invillia.projetoPaloAlto.anotation.IsZipCode;
import static org.apache.logging.log4j.util.Strings.isBlank;

public class ZipCodeValidator implements ConstraintValidator<IsZipCode, String> {

    @Override
    public void initialize(final IsZipCode constraintAnnotation) {
        // initialize
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return !isBlank(value) && length(value) == 9;
    }
}
