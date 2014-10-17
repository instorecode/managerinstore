package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.web.annotation.NotRestrict;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestUsuario;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.tools.AjaxResult;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
    
    @Inject
    private HttpServletRequest httpServletRequest;
    
    public HomeController() {
    }

    public HomeController(SessionRepository repository, SessionUsuario sessionUsuario, RequestUsuario requestUsuario, Result result) {
        this.repository = repository;
        this.sessionUsuario = sessionUsuario;
        this.requestUsuario = requestUsuario;
        this.result = result;
    }
    
    @Get
    @Path("/test")
    public void test() {
    }
    
    @Get
    @Path("/404")
    public void page404() {
    }
    
    @Get
    @Path("/500")
    public void page500() {   
    }
    
    @Get
    @Path("/")
    @NotRestrict
    public void index() {
        boolean temEmail = false; 
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
    
    @Get
    @Path("/meus-dados")
    @Restrict
    public void meusDados() {
        result.include("estadoBeanList", requestUsuario.estadoBeanList());
        result.include("usuarioBean", sessionUsuario.getUsuarioBean());
    }
   
    @Post
    @Path("/meus-dados")
    @Restrict
    public void meusDados(UsuarioBean usuarioBean) {
        requestUsuario.meusDados(usuarioBean);
    }
    
    @Get
    @Path("/minha-senha")
    @Restrict
    public void minhaSenha() {
        
    }
   
    @Post
    @Path("/minha-senha")
    @Restrict
    public void minhaSenha(String senha_atual , String nova_senha, String conf_senha) {
        requestUsuario.minhaSenha(senha_atual , nova_senha , conf_senha);
    }
}
