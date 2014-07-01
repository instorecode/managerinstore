package br.com.instore.web.interceptor;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.instore.core.orm.SessionFactoryUtils;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.controller.HomeController;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.hibernate.Session;

@Intercepts
@RequestScoped
public class HibernateSession {

    @AroundCall
    public void intercept(SimpleInterceptorStack stack) {
        Session session = null;
        try {
            session = SessionFactoryUtils.getInstance().session();
            stack.next();
        } catch (Exception e) {
            e.printStackTrace();
            if (null != session.getTransaction()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (null != session) {
                
                if(session.isOpen()) {
                    if(session.getTransaction().isActive()) {
                        if(!session.getTransaction().wasCommitted() && !session.getTransaction().wasRolledBack()) {
                            session.getTransaction().rollback();
                        }
                    }
                    
                    session.clear();
                    session.close();
                }
            }
        }
    }
}
