package br.com.instore.web.annotation;

import br.com.caelum.vraptor.interceptor.AcceptsConstraint;
import br.com.caelum.vraptor.interceptor.WithAnnotationAcceptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface NotRestrict {
    
}
