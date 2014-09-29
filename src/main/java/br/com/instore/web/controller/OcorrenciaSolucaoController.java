package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaSolucaoBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestOcorrenciaOrigem;
import br.com.instore.web.component.request.RequestOcorrenciaProblema;
import br.com.instore.web.component.request.RequestOcorrenciaSolucao;
import javax.inject.Inject;

@Controller
public class OcorrenciaSolucaoController implements java.io.Serializable {
    @Inject
    private Result result;
    
    @Inject
    private RequestOcorrenciaSolucao requestOcorrenciaSolucao;

    public OcorrenciaSolucaoController() {
    }

    public OcorrenciaSolucaoController(Result result, RequestOcorrenciaSolucao requestOcorrenciaSolucao) {
        this.result = result;
        this.requestOcorrenciaSolucao = requestOcorrenciaSolucao;
    }

    @Get
    @Restrict
    @Path("/ocorrencia-solucao")
    public void listar(Boolean datajson , Boolean view , Integer page, Integer rows, Integer id, String prazo, String descricao , Integer pk) {
        if (null != datajson && datajson) {
            requestOcorrenciaSolucao.beanList(page, rows, id, descricao, prazo);
        } else {
            result.include("problemaList", requestOcorrenciaSolucao.problemaList());
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestOcorrenciaSolucao.bean(pk)).recursive().serialize();
        } else {
            result.include("problemaList", requestOcorrenciaSolucao.problemaList());
        }
    }

    @Get
    @Restrict
    @Path("/ocorrencia-solucao/cadastrar")
    public void cadastrar() {
        result.include("problemaList", requestOcorrenciaSolucao.problemaList());
    }

    @Post
    @Restrict
    @Path("/ocorrencia-solucao/cadastrar")
    public void cadastrar(OcorrenciaSolucaoBean ocorrenciaSolucaoBean , Integer [] problemaList , String prazo) {
        requestOcorrenciaSolucao.salvar(ocorrenciaSolucaoBean , problemaList , prazo);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-solucao/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("problemaList", requestOcorrenciaSolucao.problemaList());
        result.include("problemaSolucaoList", requestOcorrenciaSolucao.problemaSolucaoList());
        result.include("ocorrenciaSolucaoBean", requestOcorrenciaSolucao.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-solucao/atualizar/{id}")
    public void cadastrar(Integer id , OcorrenciaSolucaoBean ocorrenciaSolucaoBean  , Integer [] problemaList , String prazo) {
        requestOcorrenciaSolucao.salvar(ocorrenciaSolucaoBean , problemaList , prazo);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-solucao/remover/{id}")
    public void remover(Integer id) {
        result.include("ocorrenciaSolucaoBean", requestOcorrenciaSolucao.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-solucao/remover/{id}")
    public void remover(Integer id, String param) {
        requestOcorrenciaSolucao.remover(id);
    }
}
