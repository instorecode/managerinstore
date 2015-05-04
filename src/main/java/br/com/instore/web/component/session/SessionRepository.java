package br.com.instore.web.component.session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.instore.core.orm.RepositoryViewer;
import org.hibernate.Session;

 
@Component
@RequestScoped
public class SessionRepository extends RepositoryViewer implements java.io.Serializable {

    public SessionRepository() {
        verifySession();
    }
    
    public Session getSession() {
        return getSession();
    }
    
    public void cleanAndClose() {
        if (null != super.getSession() && super.getSession().isOpen()) {
            super.getSession().clear();
            super.getSession().close();
            setSession(null);
        }
    }
}
