package com.wt.bl.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 泛型 MyConstraint = 自定义注解； Object = 作用在什么类型的字段上
 * implements ConstraintValidator 默认被Bean管理
 *
 * @author WangTao
 *         Created at 18/10/29 上午11:43.
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // TODO add some self logic
        // false 标识验证不通过; true 标识验证通过
        return false;
    }

    // 初始化注解
    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        // TODO add some self logic
    }
}
