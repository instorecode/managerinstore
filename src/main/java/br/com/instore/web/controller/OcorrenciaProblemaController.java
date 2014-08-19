package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestOcorrenciaOrigem;
import br.com.instore.web.component.request.RequestOcorrenciaProblema;
import javax.inject.Inject;

@Controller
public class OcorrenciaProblemaController implements java.io.Serializable {
    @Inject
    private Result result;
    
    @Inject
    private RequestOcorrenciaProblema requestOcorrenciaProblema;

    public OcorrenciaProblemaController() {
    }

    public OcorrenciaProblemaController(Result result, RequestOcorrenciaProblema requestOcorrenciaProblema) {
        this.result = result;
        this.requestOcorrenciaProblema = requestOcorrenciaProblema;
    }

    @Get
    @Restrict
    @Path("/ocorrencia-problema")
    public void listar(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestOcorrenciaProblema.beanList()).recursive().serialize();
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
