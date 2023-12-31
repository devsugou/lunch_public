package com.station.lunch.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/**
 * カスタムアノテーション
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ImageFileValidator.class)
public @interface ValidImage {

    String message() default "Invalid image file.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
