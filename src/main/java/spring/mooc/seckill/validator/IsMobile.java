package spring.mooc.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author dk
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class })
//调用这个校验器 来进行校验
public @interface  IsMobile {
	
	boolean required() default true;
	
	String message() default "手机号码格式错误";
	//校验不通过 返回的信息

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
