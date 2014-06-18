package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.web.annotation.Restrict;
import javax.inject.Inject;

@Controller
public class ClienteController implements java.io.Serializable {
    @Inject
    private Result result;

    public ClienteController(Result result) {
        this.result = result;
    }
    
    @Get
    @Restrict
    @Path("/clientes")
    public void clientes() {
        
    }
    
    @Get
    @Restrict
    @Path("/cliente/cadastrar")
    public void clienteCadastrar() {
        
    }
    
    @Post
    @Restrict
    @Path("/cliente/cadastrar")
    public void clienteCadastrar(ClienteBean clienteBean) {
        
    }
    
    @Get
    @Restrict
    @Path("/cliente/atualizar")
    public void clienteAtualizar() {
        
    }
    
    @Post
    @Restrict
    @Path("/cliente/atualizar")
    public void clienteAtualizar(ClienteBean clienteBean) {
        
    }
    
    @Get
    @Restrict
    @Path("/cliente/remover")
    public void clienteRemover() {
        
    }
    
    @Post
    @Restrict
    @Path("/cliente/remover")
    public void clienteRemover(Integer id) {
        
    }
}
