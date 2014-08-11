package br.com.instore.web.interceptor;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.instore.core.orm.SessionFactoryUtils;
import javax.enterprise.context.RequestScoped;
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
            if (null != session) {
                if(session.isOpen()) {
                    if(session.getTransaction().isActive()) {
                        if(!session.getTransaction().wasCommitted() && !session.getTransaction().wasRolledBack()) {
                            session.getTransaction().rollback();
                        }
                    }
                }
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
