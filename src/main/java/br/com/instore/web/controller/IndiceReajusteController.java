package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.IndiceReajusteBean;
import br.com.instore.core.orm.bean.OcorrenciaUsuarioBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestIndiceReajuste;
import br.com.instore.web.component.request.RequestOcorrencia;
import javax.inject.Inject;

@Controller
public class IndiceReajusteController implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private RequestIndiceReajuste requestIndiceReajuste;

    public IndiceReajusteController() {
    }

    public IndiceReajusteController(Result result, RequestIndiceReajuste requestIndiceReajuste) {
        this.result = result;
        this.requestIndiceReajuste = requestIndiceReajuste;
    }

   

    @Get
    @Restrict
    @Path("/indice-reajuste")
    public void listar(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestIndiceReajuste.beanList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/indice-reajuste/cadastrar")
    public void cadastrar() {
        result.include("isPageCadastro", true);
    }

    @Post
    @Restrict
    @Path("/indice-reajuste/cadastrar")
    public void cadastrar(IndiceReajusteBean indiceReajusteBean) {
        requestIndiceReajuste.salvar(indiceReajusteBean);
    }

    @Get
    @Restrict
    @Path("/indice-reajuste/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("isPageCadastro", false);
        result.include("indiceReajusteBean", requestIndiceReajuste.bean(id));
    }

    @Post
    @Restrict
    @Path("/indice-reajuste/atualizar/{id}")
    public void cadastrar(Integer id, IndiceReajusteBean indiceReajusteBean) {
        requestIndiceReajuste.salvar(indiceReajusteBean);
    }

    @Get
    @Restrict
    @Path("/indice-reajuste/remover/{id}")
    public void remover(Integer id) {
        result.include("indiceReajusteBean", requestIndiceReajuste.bean(id));
        result.include("podeRemover", requestIndiceReajuste.podeRemover(id));
    }

    @Post
    @Restrict
    @Path("/indice-reajuste/remover/{id}")
    public void remover(Integer id, String param) {
        requestIndiceReajuste.remover(id);
    }
}
