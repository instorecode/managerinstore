package br.com.instore.web.component.session;

import br.com.instore.core.orm.bean.UsuarioBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class SessionUsuario implements java.io.Serializable {
    private boolean logado = false; 
    private UsuarioBean usuarioBean; 

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

}
