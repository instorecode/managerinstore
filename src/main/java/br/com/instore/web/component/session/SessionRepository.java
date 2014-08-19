package br.com.instore.web.component.session;

import br.com.instore.core.orm.RepositoryViewer;
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
        setUsuario(sessaoUsuario.getUsuarioBean());
    }       
}
