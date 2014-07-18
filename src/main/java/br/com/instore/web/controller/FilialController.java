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
import javax.inject.Inject;

@Controller
public class FilialController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestCliente requestCliente;

    public FilialController() {
    }

    public FilialController(Result result, RequestCliente requestCliente) {
        this.result = result;
        this.requestCliente = requestCliente;
    }

    @Get
    @Restrict
    @Path("/filial/{id}")
    public void lista(Integer id , Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestCliente.clienteDTOList(id)).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/filial/cadastrar/{id}")
    public void cadastrar() {
        result.include("clienteBeanList", requestCliente.clienteBeanList());
        result.include("estadoBeanList", requestCliente.estadoBeanList());
    }

    @Post
    @Restrict
    @Path("/filial/cadastrar/{id}")
    public void cadastrar(ClienteBean cliente , DadosClienteBean dadosCliente) {
        requestCliente.salvar(cliente, dadosCliente);
    }

    @Get
    @Restrict
    @Path("/filial/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("clienteBeanList", requestCliente.clienteBeanList());
        result.include("estadoBeanList", requestCliente.estadoBeanList());
        result.include("cliente", requestCliente.clienteBean(id));
        result.include("dadosCliente", requestCliente.dadosClienteBean(id));
    }

    @Post
    @Restrict
    @Path("/filial/atualizar/{id}")
    public void cadastrar(Integer id , ClienteBean cliente , DadosClienteBean dadosCliente) {
        requestCliente.salvar(cliente, dadosCliente);
    }

    @Get
    @Restrict
    @Path("/filial/remover/{id}")
    public void remover(Integer id) {
        result.include("cliente", requestCliente.clienteBean(id));
        result.include("dadosCliente", requestCliente.dadosClienteBean(id));
    }

    @Post
    @Restrict
    @Path("/filial/remover/{id}")
    public void remover(Integer id, String param) {
        requestCliente.desabilitar(id);
    }
}