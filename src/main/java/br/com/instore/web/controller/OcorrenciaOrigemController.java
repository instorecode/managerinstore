package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaOrigemBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestOcorrenciaOrigem;
import javax.inject.Inject;

@Controller
public class OcorrenciaOrigemController implements java.io.Serializable {
    @Inject
    private Result result;
    
    @Inject
    private RequestOcorrenciaOrigem requestOcorrenciaOrigem;

    public OcorrenciaOrigemController() {
    }

    public OcorrenciaOrigemController(Result result, RequestOcorrenciaOrigem requestOcorrenciaOrigem) {
        this.result = result;
        this.requestOcorrenciaOrigem = requestOcorrenciaOrigem;
    }

    @Get
    @Restrict
    @Path("/ocorrencia-origem")
    public void listar(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestOcorrenciaOrigem.beanList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/ocorrencia-origem/cadastrar")
    public void cadastrar() {
    }

    @Post
    @Restrict
    @Path("/ocorrencia-origem/cadastrar")
    public void cadastrar(OcorrenciaOrigemBean ocorrenciaOrigemBean ) {
        requestOcorrenciaOrigem.salvar(ocorrenciaOrigemBean);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-origem/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("ocorrenciaOrigemBean", requestOcorrenciaOrigem.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-origem/atualizar/{id}")
    public void cadastrar(Integer id , OcorrenciaOrigemBean ocorrenciaOrigemBean ) {
        requestOcorrenciaOrigem.salvar(ocorrenciaOrigemBean);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-origem/remover/{id}")
    public void remover(Integer id) {
        result.include("ocorrenciaOrigemBean", requestOcorrenciaOrigem.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-origem/remover/{id}")
    public void remover(Integer id, String param) {
        requestOcorrenciaOrigem.remover(id);
    }
}
