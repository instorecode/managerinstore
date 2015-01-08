package br.com.instore.web.component.session;

import br.com.instore.core.orm.RepositoryViewer;
import br.com.instore.web.component.application.Initialize;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
 
@RequestScoped
public class SessionRepository extends RepositoryViewer implements java.io.Serializable {
    
    @Inject
    private SessionUsuario sessaoUsuario;

    
    public SessionRepository() {
        
    }

    public SessionRepository(SessionUsuario sessaoUsuario) {
        this.sessaoUsuario = sessaoUsuario;
        this.sessaoUsuario = sessaoUsuario;
        setUsuario(sessaoUsuario.getUsuarioBean());
    }
    
    public void cleanAndClose() {
        if (null != super.getSession() && super.getSession().isOpen()) {
            super.getSession().clear();
            super.getSession().close();
            setSession(null);
        }
    }
}
