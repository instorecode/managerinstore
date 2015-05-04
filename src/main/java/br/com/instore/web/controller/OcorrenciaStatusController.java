package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaStatusBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestOcorrenciaStatus;

@Resource
public class OcorrenciaStatusController implements java.io.Serializable {


    private Result result;
    private RequestOcorrenciaStatus requestOcorrenciaStatus;

    public OcorrenciaStatusController(Result result, RequestOcorrenciaStatus requestOcorrenciaStatus) {
        this.result = result;
        this.requestOcorrenciaStatus = requestOcorrenciaStatus;
    }

    @Get
    @Restrict
    @Path("/ocorrencia-status")
    public void listar(Boolean datajson , Boolean view , Integer page, Integer rows, Integer id, String descricao, String cor , Integer pk) {
        if (null != datajson && datajson) {
            requestOcorrenciaStatus.beanList(page, rows, id, descricao ,cor);
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestOcorrenciaStatus.bean(pk)).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/ocorrencia-status/cadastrar")
    public void cadastrar() {
    }

    @Post
    @Restrict
    @Path("/ocorrencia-status/cadastrar")
    public void cadastrar(OcorrenciaStatusBean ocorrenciaStatusBean ) {
        requestOcorrenciaStatus.salvar(ocorrenciaStatusBean);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-status/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("ocorrenciaStatusBean", requestOcorrenciaStatus.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-status/atualizar/{id}")
    public void cadastrar(Integer id , OcorrenciaStatusBean ocorrenciaStatusBean ) {
        requestOcorrenciaStatus.salvar(ocorrenciaStatusBean);
    }

    @Get
    @Restrict
    @Path("/ocorrencia-status/remover/{id}")
    public void remover(Integer id) {
        result.include("ocorrenciaStatusBean", requestOcorrenciaStatus.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia-status/remover/{id}")
    public void remover(Integer id, String param) {
        requestOcorrenciaStatus.remover(id);
    }
}
