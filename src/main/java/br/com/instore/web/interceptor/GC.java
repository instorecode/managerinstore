package br.com.instore.web.interceptor;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import javax.enterprise.context.RequestScoped;

@Intercepts
@RequestScoped
public class GC {

    @AroundCall
    public void intercept(SimpleInterceptorStack stack, ControllerMethod method) {
        System.gc();
        stack.next();
        System.gc();
    }
}
