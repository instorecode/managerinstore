
package br.com.instore.web.dto;

import br.com.instore.core.orm.bean.OrdemServicoObsBean;
import br.com.instore.core.orm.bean.UsuarioBean;

public class OrdenServicoObsBeanExtended extends OrdemServicoObsBean {
    private UsuarioBean usuarioBean;

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
   
}
