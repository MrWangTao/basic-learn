package com.wt.bl.annotation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author WangTao
 *         Created at 18/10/29 上午11:41.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MyConstraintValidator.class}) // 处理约束的类
public @interface MyConstraint {

    String message() default "{javax.validation.constraints.NotBlank.message}";

}
