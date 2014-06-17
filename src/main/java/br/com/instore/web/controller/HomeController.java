package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.instore.web.annotation.NotRestrict;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestUsuario;
import javax.inject.Inject;

@Controller
public class HomeController implements java.io.Serializable {
    
    @Inject
    private RequestUsuario requestUsuario;
    @Inject
    private Result result;
    
    public HomeController() {
    }
    
    public HomeController(RequestUsuario requestUsuario, Result result) {
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
}
