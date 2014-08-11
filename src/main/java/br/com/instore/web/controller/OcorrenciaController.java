package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaBean;
import br.com.instore.core.orm.bean.OcorrenciaUsuarioBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestOcorrencia;
import javax.inject.Inject;

@Controller
public class OcorrenciaController implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private RequestOcorrencia requestOcorrencia;

    public OcorrenciaController() {
    }

    public OcorrenciaController(Result result, RequestOcorrencia requestOcorrencia) {
        this.result = result;
        this.requestOcorrencia = requestOcorrencia;
    }

    @Get
    @Restrict
    @Path("/ocorrencia")
    public void listar(Boolean datajson) {
        requestOcorrencia.totalPorStatus();
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestOcorrencia.beanList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/ocorrencia/cadastrar")
    public void cadastrar() {
        result.include("cadastrar", true);
        result.include("clienteList", requestOcorrencia.clienteList());
        result.include("ocorrenciaStatusList", requestOcorrencia.ocorrenciaStatusList());
        result.include("ocorrenciaOrigemList", requestOcorrencia.ocorrenciaOrigemList());
        result.include("ocorrenciaPrioridadeList", requestOcorrencia.ocorrenciaPrioridadeList());
        result.include("ocorrenciaProblemaList", requestOcorrencia.ocorrenciaProblemaList());
        result.include("ocorrenciaSolucaoList", requestOcorrencia.ocorrenciaSolucaoList());
        result.include("usuarioList", requestOcorrencia.usuarioList());
    }

    @Post
    @Restrict
    @Path("/ocorrencia/cadastrar")
    public void cadastrar(OcorrenciaBean ocorrenciaBean,  Integer idstatus) {
        requestOcorrencia.salvar(ocorrenciaBean, idstatus);
    }

    @Get
    @Restrict
    @Path("/ocorrencia/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("cadastrar", false);
        result.include("clienteList", requestOcorrencia.clienteList());
        result.include("ocorrenciaStatusList", requestOcorrencia.ocorrenciaStatusList());
        result.include("ocorrenciaOrigemList", requestOcorrencia.ocorrenciaOrigemList());
        result.include("ocorrenciaPrioridadeList", requestOcorrencia.ocorrenciaPrioridadeList());
        result.include("ocorrenciaProblemaList", requestOcorrencia.ocorrenciaProblemaList());
        result.include("ocorrenciaSolucaoList", requestOcorrencia.ocorrenciaSolucaoList());
        result.include("ocorrenciaUsuario", requestOcorrencia.ocorrenciaUsuario(id));
        result.include("usuarioList", requestOcorrencia.usuarioList());
        result.include("ocorrenciaBean", requestOcorrencia.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia/atualizar/{id}")
    public void cadastrar(Integer id, OcorrenciaBean ocorrenciaBean, Integer idstatus, String prazoPesolucaoString) {
        requestOcorrencia.salvar(ocorrenciaBean, idstatus);
    }

    @Get
    @Restrict
    @Path("/ocorrencia/remover/{id}")
    public void remover(Integer id) {
        result.include("ocorrenciaBean", requestOcorrencia.bean(id));
    }

    @Post
    @Restrict
    @Path("/ocorrencia/remover/{id}")
    public void remover(Integer id, String param) {
        requestOcorrencia.remover(id);
    }
    
    @Get
    @Restrict
    @Path("/minha-ocorrencia")
    public void listar2(Boolean datajson) {
        requestOcorrencia.totalPorStatus();
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestOcorrencia.beanList2()).recursive().serialize();
        }
    }
    
    // aqui 
    @Get
    @Restrict
    @Path("/minha-ocorrencia/cadastrar")
    public void cadastrar2() {
        result.include("cadastrar", true);
        result.include("clienteList", requestOcorrencia.clienteList());
        result.include("ocorrenciaStatusList", requestOcorrencia.ocorrenciaStatusList());
        result.include("ocorrenciaOrigemList", requestOcorrencia.ocorrenciaOrigemList());
        result.include("ocorrenciaPrioridadeList", requestOcorrencia.ocorrenciaPrioridadeList());
        result.include("ocorrenciaProblemaList", requestOcorrencia.ocorrenciaProblemaList());
        result.include("ocorrenciaSolucaoList", requestOcorrencia.ocorrenciaSolucaoList());
        result.include("usuarioList", requestOcorrencia.usuarioList());
    }

    @Post
    @Restrict
    @Path("/minha-ocorrencia/cadastrar")
    public void cadastrar2(OcorrenciaBean ocorrenciaBean,  Integer idstatus) {
        requestOcorrencia.salvar(ocorrenciaBean, idstatus);
    }

    @Get
    @Restrict
    @Path("/minha-ocorrencia/atualizar/{id}")
    public void cadastrar2(Integer id) {
        result.include("cadastrar", false);
        result.include("clienteList", requestOcorrencia.clienteList());
        result.include("ocorrenciaStatusList", requestOcorrencia.ocorrenciaStatusList());
        result.include("ocorrenciaOrigemList", requestOcorrencia.ocorrenciaOrigemList());
        result.include("ocorrenciaPrioridadeList", requestOcorrencia.ocorrenciaPrioridadeList());
        result.include("ocorrenciaProblemaList", requestOcorrencia.ocorrenciaProblemaList());
        result.include("ocorrenciaSolucaoList", requestOcorrencia.ocorrenciaSolucaoList());
        result.include("ocorrenciaUsuario", requestOcorrencia.ocorrenciaUsuario(id));
        result.include("usuarioList", requestOcorrencia.usuarioList());
        result.include("ocorrenciaBean", requestOcorrencia.bean(id));
    }

    @Post
    @Restrict
    @Path("/minha-ocorrencia/atualizar/{id}")
    public void cadastrar2(Integer id, OcorrenciaBean ocorrenciaBean, Integer idstatus, String prazoPesolucaoString) {
        requestOcorrencia.salvar(ocorrenciaBean, idstatus);
    }

    @Get
    @Restrict
    @Path("/minha-ocorrencia/remover/{id}")
    public void remover2(Integer id) {
        result.include("ocorrenciaBean", requestOcorrencia.bean(id));
    }

    @Post
    @Restrict
    @Path("/minha-ocorrencia/remover/{id}")
    public void remover2(Integer id, String param) {
        requestOcorrencia.remover(id);
    }
}
