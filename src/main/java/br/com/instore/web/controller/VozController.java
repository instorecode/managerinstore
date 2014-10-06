package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ContatoClienteBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestContatoCliente;
import br.com.instore.web.component.request.RequestVoz;
import javax.inject.Inject;

@Controller
public class VozController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestVoz requestVoz;

    public VozController() {
    }

    public VozController(Result result, RequestVoz requestVoz) {
        this.result = result;
        this.requestVoz = requestVoz;
    }

    

    @Get
    @Restrict
    @Path("/voz")
    public void listar (Boolean datajson , Boolean view, Integer page, Integer rows, Integer idvoz, String clienteNome, String genero, String tipo, String nome, String email, String tel, Integer pk) {
        
        if (null != datajson && datajson) {
            requestVoz.beanList(page, rows, idvoz, clienteNome, genero, tipo, nome, email, tel);
            //result.use(Results.json()).withoutRoot().from(requestVoz.beanList()).recursive().serialize();
        }else{
            result.include("clienteBeanList", requestVoz.clienteBeanList());
        }
        
        if(null != view && view){
            result.use(Results.json()).withoutRoot().from(requestVoz.bean(pk)).recursive().serialize();
        }else{
            result.include("clienteBeanList", requestVoz.clienteBeanList());
        }
        
    }

    @Get
    @Restrict
    @Path("/voz/cadastrar")
    public void cadastrar() {
        result.include("clienteBeanList", requestVoz.clienteBeanList());
    }

    @Post
    @Restrict
    @Path("/voz/cadastrar")
    public void cadastrar(VozBean vozBean ) {
        requestVoz.salvar(vozBean);
    }

    @Get
    @Restrict
    @Path("/voz/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("vozBean", requestVoz.bean(id));
        result.include("clienteBeanList", requestVoz.clienteBeanList());
    }

    @Post
    @Restrict
    @Path("/voz/atualizar/{id}")
    public void cadastrar(Integer id , VozBean vozBean ) {
        requestVoz.salvar(vozBean);
    }

    @Get
    @Restrict
    @Path("/voz/remover/{id}")
    public void remover(Integer id) {
        result.include("vozBean", requestVoz.bean(id));
    }

    @Post
    @Restrict
    @Path("/voz/remover/{id}")
    public void remover(Integer id, String param) {
        requestVoz.remover(id);
    }
}
