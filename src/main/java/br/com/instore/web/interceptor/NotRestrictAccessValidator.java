package br.com.instore.web.interceptor;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.instore.web.annotation.NotRestrict;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.controller.HomeController;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Intercepts
@RequestScoped
public class NotRestrictAccessValidator  implements Interceptor {
   
    private SessionUsuario sessionUsuario;
    private Result result;

    public NotRestrictAccessValidator(SessionUsuario sessionUsuario, Result result) {
        this.sessionUsuario = sessionUsuario;
        this.result = result;
    }
    
    public boolean accepts(ResourceMethod method) {
        return method.containsAnnotation(NotRestrict.class);
    }


    public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
        if(sessionUsuario.isLogado()) {
            result.redirectTo(HomeController.class).dashboard();
        } else {
            stack.next(method, resourceInstance);
        }
    }
}
