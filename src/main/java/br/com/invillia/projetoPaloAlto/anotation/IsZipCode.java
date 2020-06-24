package br.com.invillia.projetoPaloAlto.anotation;

import javax.validation.Payload;
import javax.validation.Constraint;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.*;
import br.com.invillia.projetoPaloAlto.utils.Messages;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import br.com.invillia.projetoPaloAlto.utils.ZipCodeValidator;

@Documented
@Constraint(validatedBy = ZipCodeValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface IsZipCode {

    String message() default Messages.NOT_DOCUMENT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
