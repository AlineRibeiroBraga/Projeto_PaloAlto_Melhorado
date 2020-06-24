package br.com.invillia.projetoPaloAlto.anotation;

import javax.validation.Payload;
import javax.validation.Constraint;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.*;
import br.com.invillia.projetoPaloAlto.utils.Messages;
import br.com.invillia.projetoPaloAlto.utils.RGValidator;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = RGValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface IsRG{

    String message() default Messages.NOT_DOCUMENT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
