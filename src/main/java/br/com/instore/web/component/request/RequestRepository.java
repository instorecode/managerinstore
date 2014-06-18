package br.com.instore.web.component.request;

import br.com.instore.core.orm.RepositoryViewer;
import br.com.instore.web.component.session.SessionUsuario;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestRepository extends RepositoryViewer implements java.io.Serializable {
    
    @Inject
    private SessionUsuario sessaoUsuario;

    public RequestRepository() {
    }
 
    public RequestRepository(SessionUsuario sessaoUsuario) {
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
