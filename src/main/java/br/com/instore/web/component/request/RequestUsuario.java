package br.com.instore.web.component.request;

import br.com.instore.web.component.session.SessionRepository;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.FuncionalidadeBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.core.orm.bean.property.Usuario;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.swing.tree.TreeNode;

@RequestScoped
public class RequestUsuario implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private SessionRepository requestRepository;
    @Inject
    private SessionUsuario sessionUsuario;
    @Inject
    private HttpSession httpSession;

    public RequestUsuario() {
    }

    public RequestUsuario(Result result, SessionRepository requestRepository, SessionUsuario sessionUsuario, HttpSession httpSession) {
        this.result = result;
        this.requestRepository = requestRepository;
        this.sessionUsuario = sessionUsuario;
        this.httpSession = httpSession;
    }

    public void logIn(String email, String senha) {
        try {
            senha = Utilities.md5(senha);
            if (requestRepository.query(UsuarioBean.class).eq(Usuario.EMAIL, email).and().eq(Usuario.SENHA, senha).count() > 0) {
                UsuarioBean usuario = requestRepository.query(UsuarioBean.class).eq(Usuario.EMAIL, email).and().eq(Usuario.SENHA, senha).findOne();
                usuario.getPerfilBeanList();
                sessionUsuario.setUsuarioBean(usuario);
                sessionUsuario.setLogado(true);

                result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Usuário logado com sucesso")).recursive().serialize();
            } else {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "E-mail / Senha inválidos.")).recursive().serialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "E-mail / Senha inválidos.")).recursive().serialize();
        }
    }
    
    public void logOut() {
        httpSession.invalidate();
        result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "LogOut efetuado com sucesso.")).recursive().serialize();        
    }
}
