package br.com.instore.web.controller;


import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestOcorrenciaProblema;

@Resource
public class OcorrenciaProblemaController implements java.io.Serializable {
    
    private Result result;
    private RequestOcorrenciaProblema requestOcorrenciaProblema;

    public OcorrenciaProblemaController(Result result, RequestOcorrenciaProblema requestOcorrenciaProblema) {
        this.result = result;
        this.requestOcorrenciaProblema = requestOcorrenciaProblema;
    }

    @Get
    @Restrict
    @Path("/ocorrencia-problema")
    public void listar(Boolean datajson , Boolean view , Integer page, Integer rows, Integer id, String descricao , Integer pk) {
        if (null != datajson && datajson) {
            requestOcorrenciaProblema.beanList(page, rows, id, descricao);
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestOcorrenciaProblema.bean(pk)).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/ocorrencia-problema/cadastrar")
    public void cadastrar() {
    }

    @Post
    @Restrict
    @Path("/ocorrencia-problema/cadastrar")
    public void cadastrar(OcorrenciaProblemaBean ocorrenciaProblemaBean ) {
        requestOcorrenciaProblema.salvar(ocorrenciaProblemaBean);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-problema/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("ocorrenciaProblemaBean", requestOcorrenciaProblema.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-problema/atualizar/{id}")
    public void cadastrar(Integer id , OcorrenciaProblemaBean ocorrenciaProblemaBean ) {
        requestOcorrenciaProblema.salvar(ocorrenciaProblemaBean);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-problema/remover/{id}")
    public void remover(Integer id) {
        result.include("ocorrenciaProblemaBean", requestOcorrenciaProblema.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-problema/remover/{id}")
    public void remover(Integer id, String param) {
        requestOcorrenciaProblema.remover(id);
    }
}
