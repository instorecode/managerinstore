package br.com.instore.web.interceptor;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.instore.web.annotation.NotRestrict;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.controller.HomeController;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Intercepts(after = GC.class)
@RequestScoped
@AcceptsWithAnnotations(NotRestrict.class)
public class NotRestrictAccessValidator implements java.io.Serializable {
    @Inject 
    private SessionUsuario sessionUsuario;
    
    @Inject 
    private Result result;

    @AroundCall
    public void intercept(SimpleInterceptorStack stack) {
        if(sessionUsuario.isLogado()) {
            result.redirectTo(HomeController.class).dashboard();
        } else {
            stack.next(); 
        }
    }
}
