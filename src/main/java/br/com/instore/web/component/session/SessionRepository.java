package br.com.instore.web.component.session;

import br.com.instore.core.orm.RepositoryViewer;
import br.com.instore.web.component.session.SessionUsuario;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SessionScoped
public class SessionRepository extends RepositoryViewer implements java.io.Serializable {
    
    @Inject
    private SessionUsuario sessaoUsuario;

    public SessionRepository() {
    }
 
    public SessionRepository(SessionUsuario sessaoUsuario) {
        this.sessaoUsuario = sessaoUsuario;
    }
    
    @PostConstruct
    public void postConstruct() {
        verifySession();
    }
    
    @PreDestroy
    public void preDestroy() {
        finalize();
    }         
}
