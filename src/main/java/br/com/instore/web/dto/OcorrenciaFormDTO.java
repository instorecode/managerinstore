package br.com.instore.web.dto;

import br.com.instore.core.orm.bean.OcorrenciaUsuarioBean;
import br.com.instore.core.orm.bean.UsuarioBean;

public class OcorrenciaFormDTO extends AbstractDTO {
    private UsuarioBean usuario;
    private OcorrenciaUsuarioBean ocorrenciaUsuario;
    private boolean tem ;

    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }

    public OcorrenciaUsuarioBean getOcorrenciaUsuario() {
        return ocorrenciaUsuario;
    }

    public void setOcorrenciaUsuario(OcorrenciaUsuarioBean ocorrenciaUsuario) {
        this.ocorrenciaUsuario = ocorrenciaUsuario;
    }

    public boolean isTem() {
        return tem;
    }

    public void setTem(boolean tem) {
        this.tem = tem;
    }
}
