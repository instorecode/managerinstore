package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestCliente;
import javax.inject.Inject;

@Controller
public class ClienteController implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private RequestCliente requestCliente;

    public ClienteController() {
    }

    public ClienteController(Result result, RequestCliente requestCliente) {
        this.result = result;
        this.requestCliente = requestCliente;
    }

    @Get
    @Restrict
    @Path("/clientes")
    public void clientes(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestCliente.clienteDTOList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/cliente/cadastrar")
    public void cadastrar() {
    }

    @Post
    @Restrict
    @Path("/cliente/cadastrar")
    public void cadastrar(ClienteBean cliente , DadosClienteBean dadosCliente) {
    }

    @Get
    @Restrict
    @Path("/cliente/atualizar/{id}")
    public void atualizar(Integer id) {
    }

    @Post
    @Restrict
    @Path("/cliente/atualizar")
    public void atualizar(ClienteBean cliente , DadosClienteBean dadosCliente) {
    }

    @Get
    @Restrict
    @Path("/cliente/remover/{id}")
    public void remover(Integer id) {
    }

    @Post
    @Restrict
    @Path("/cliente/remover/{id}")
    public void remover(Integer id, String param) {
    }
}
