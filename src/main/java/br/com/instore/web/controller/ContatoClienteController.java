package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.ContatoClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestCliente;
import br.com.instore.web.component.request.RequestContatoCliente;
import javax.inject.Inject;

@Controller
public class ContatoClienteController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestContatoCliente requestContatoCliente;

    public ContatoClienteController() {
    }

    public ContatoClienteController(Result result, RequestContatoCliente requestContatoCliente) {
        this.result = result;
        this.requestContatoCliente = requestContatoCliente;
    }

    @Get
    @Restrict
    @Path("/contatos/{id}")
    public void contatos(Integer id, Boolean datajson) {
        result.include("id",id);
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestContatoCliente.contatoClienteDTOList(id)).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/contato/cadastrar/{id}")
    public void cadastrar(Integer id) {
        result.include("id",id);
        result.include("dadosClienteBeanList", requestContatoCliente.dadosClienteBeanList());
    }

    @Post
    @Restrict
    @Path("/contato/cadastrar/{id}")
    public void cadastrar(ContatoClienteBean contatoClienteBean ) {
        requestContatoCliente.salvar(contatoClienteBean);
    }

    @Get
    @Restrict
    @Path("/contato/atualizar/{id}")
    public void cadastrar(Integer id, String param1) {
        result.include("id",id);
        result.include("contatoClienteBean", requestContatoCliente.contatoClienteBean(id));
        result.include("dadosClienteBeanList", requestContatoCliente.dadosClienteBeanList());
    }

    @Post
    @Restrict
    @Path("/contato/atualizar/{id}")
    public void cadastrar(Integer id , ContatoClienteBean contatoClienteBean ) {
        result.include("id",id);
        requestContatoCliente.salvar(contatoClienteBean);
    }

    @Get
    @Restrict
    @Path("/contato/remover/{id}")
    public void remover(Integer id) {
        result.include("id",id);
        result.include("contatoClienteBean", requestContatoCliente.contatoClienteBean(id));
    }

    @Post
    @Restrict
    @Path("/contato/remover/{id}")
    public void remover(Integer id, String param) {
        requestContatoCliente.remover(id);
    }
}
