package br.com.instore.web.interceptor;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.instore.core.orm.SessionFactoryUtils;
import br.com.instore.web.annotation.NaoDeslogar;
import br.com.instore.web.controller.HomeController;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

@Intercepts
@RequestScoped
public class HibernateSession {

    @Inject
    private HttpSession httpSession;
    @Inject
    private Result result;

    @AroundCall
    public void intercept(SimpleInterceptorStack stack, ControllerMethod method) {

        Session session = null;
        try {
            session = SessionFactoryUtils.getInstance().session();
            stack.next();
        } catch (Exception e) {
            e.printStackTrace();
            if (null != session) {
                if (session.isOpen()) {
                    if (session.getTransaction().isActive()) {
                        if (!session.getTransaction().wasCommitted() && !session.getTransaction().wasRolledBack()) {
                            session.getTransaction().rollback();
                        }
                    }

                    try {
                        ((SessionImpl) session).connection().close();
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }

                    session.clear();
                    session.close();
                }
            }
            if (!method.getMethod().isAnnotationPresent(NaoDeslogar.class)) {
//                httpSession.invalidate();
                result.redirectTo(HomeController.class).index();
            }
        } finally {
            if (null != session) {

                if (session.isOpen()) {
                    if (session.getTransaction().isActive()) {
                        if (!session.getTransaction().wasCommitted() && !session.getTransaction().wasRolledBack()) {
                            session.getTransaction().rollback();
                        }
                    }

                    try {
                        ((SessionImpl) session).connection().close();
                        
                        session.clear();
                        session.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
