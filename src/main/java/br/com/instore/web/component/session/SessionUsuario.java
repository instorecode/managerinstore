package br.com.instore.web.component.session;

import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jcifs.smb.SmbFile;

@SessionScoped
@Named
public class SessionUsuario implements java.io.Serializable {
    private boolean logado = false; 
    private UsuarioBean usuarioBean; 
    private SmbFile smbFile;
    private ClienteBean cliente;

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

    public SmbFile getSmbFile() {
        return smbFile;
    }

    public void setSmbFile(SmbFile smbFile) {
        this.smbFile = smbFile;
    }

    public void setCliente(ClienteBean cliente) {
        this.cliente = cliente;
    }

    public ClienteBean getCliente() {
        return cliente;
    }
}
