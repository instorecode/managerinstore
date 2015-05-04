package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaOrigemBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestOcorrenciaOrigem;

@Resource
public class OcorrenciaOrigemController implements java.io.Serializable {
    
    private Result result;
    private RequestOcorrenciaOrigem requestOcorrenciaOrigem;

    public OcorrenciaOrigemController(Result result, RequestOcorrenciaOrigem requestOcorrenciaOrigem) {
        this.result = result;
        this.requestOcorrenciaOrigem = requestOcorrenciaOrigem;
    }

    @Get
    @Restrict
    @Path("/ocorrencia-origem")
    public void listar(Boolean datajson , Boolean view , Integer page, Integer rows, Integer id, String descricao , Integer pk) {
        if (null != datajson && datajson) {
            requestOcorrenciaOrigem.beanList(page, rows, id, descricao);
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestOcorrenciaOrigem.bean(pk)).recursive().serialize();
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
