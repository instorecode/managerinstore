package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaPrioridadeBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestOcorrenciaOrigem;
import br.com.instore.web.component.request.RequestOcorrenciaPrioridade;
import br.com.instore.web.component.request.RequestOcorrenciaStatus;
import br.com.instore.web.component.request.RequestVoz;
import javax.inject.Inject;

@Controller
public class OcorrenciaPrioridadeController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestOcorrenciaPrioridade requestOcorrenciaPrioridade;

    public OcorrenciaPrioridadeController() {
    }

    public OcorrenciaPrioridadeController(Result result, RequestOcorrenciaPrioridade requestOcorrenciaPrioridade) {
        this.result = result;
        this.requestOcorrenciaPrioridade = requestOcorrenciaPrioridade;
    }

    @Get
    @Restrict
    @Path("/ocorrencia-prioridade")
    public void listar(Boolean datajson , Boolean view , Integer page, Integer rows, Integer id, Integer nivel, String descricao , Integer pk) {
        if (null != datajson && datajson) {
            requestOcorrenciaPrioridade.beanList(page, rows, id, descricao, nivel);
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestOcorrenciaPrioridade.bean(pk)).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/ocorrencia-prioridade/cadastrar")
    public void cadastrar() {
    }

    @Post
    @Restrict
    @Path("/ocorrencia-prioridade/cadastrar")
    public void cadastrar(OcorrenciaPrioridadeBean ocorrenciaPrioridadeBean ) {
        requestOcorrenciaPrioridade.salvar(ocorrenciaPrioridadeBean);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-prioridade/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("ocorrenciaPrioridadeBean", requestOcorrenciaPrioridade.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-prioridade/atualizar/{id}")
    public void cadastrar(Integer id , OcorrenciaPrioridadeBean ocorrenciaPrioridadeBean ) {
        requestOcorrenciaPrioridade.salvar(ocorrenciaPrioridadeBean);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-prioridade/remover/{id}")
    public void remover(Integer id) {
        result.include("ocorrenciaPrioridadeBean", requestOcorrenciaPrioridade.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-prioridade/remover/{id}")
    public void remover(Integer id, String param) {
        requestOcorrenciaPrioridade.remover(id);
    }
}
