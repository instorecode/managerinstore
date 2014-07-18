package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.web.annotation.NotRestrict;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestUsuario;
import br.com.instore.web.component.session.ApplicationResources;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.tools.AjaxResult;
import javax.inject.Inject;

@Controller
public class HomeController implements java.io.Serializable {
    
    @Inject
    private SessionRepository repository;
    @Inject
    private SessionUsuario sessionUsuario;
    @Inject
    private RequestUsuario requestUsuario;
    @Inject
    private Result result;
    
    public HomeController() {
    }

    public HomeController(SessionRepository repository, SessionUsuario sessionUsuario, RequestUsuario requestUsuario, Result result) {
        this.repository = repository;
        this.sessionUsuario = sessionUsuario;
        this.requestUsuario = requestUsuario;
        this.result = result;
    }
    
    @Get
    @Path("/")
    @NotRestrict
    public void index() {
    }
    
    @Post
    @Path("/")
    @NotRestrict
    public void index(String email, String senha) {
        requestUsuario.logIn(email, senha);
    }
    
    @Get
    @Path("/dashboard")
    @Restrict
    public void dashboard() {
    }
    
    @Get
    @Path("/sair")
    @Restrict
    public void sair() {
        requestUsuario.logOut();
    }
    
    @Get
    @Path("/configuracao-interna")
    @Restrict
    public void configuracaoInterna() {
        result.include("configAppBean", repository.find(ConfigAppBean.class, 1));
    }
    
    @Post
    @Path("/configuracao-interna")
    @Restrict
    public void configuracaoInterna(ConfigAppBean configAppBean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(configAppBean != null && configAppBean.getId()!= null && configAppBean.getId() > 0) {
                repository.save(repository.marge(configAppBean));
            } else {
                repository.save(configAppBean);
            }
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar as configurações!")).recursive().serialize();
        }
    }
    
 
}
