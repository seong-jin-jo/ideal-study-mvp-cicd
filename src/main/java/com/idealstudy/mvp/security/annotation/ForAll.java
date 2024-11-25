package com.idealstudy.mvp.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 우선순위가 Filter에 밀려서 사용할 수 없음. 이 어노테이션은 Controller로 들어온 이후부터 사용할 수 있기 때문.
 */
@Deprecated
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("permitAll")
public @interface ForAll {

}
