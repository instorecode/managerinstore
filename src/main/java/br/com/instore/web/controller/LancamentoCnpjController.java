package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.LancamentoCnpjBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestLancamentoCnpj;
import javax.inject.Inject;

@Controller
public class LancamentoCnpjController implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private RequestLancamentoCnpj requestLancamentoCnpj;

    public LancamentoCnpjController() {
    }

    public LancamentoCnpjController(Result result, RequestLancamentoCnpj requestLancamentoCnpj) {
        this.result = result;
        this.requestLancamentoCnpj = requestLancamentoCnpj;
    }

    @Get
    @Restrict
    @Path("/lancamento-entidade")
    public void listar(Boolean datajson , int rel) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestLancamentoCnpj.beanList()).recursive().serialize();
        }
        
        if (rel == 1) {
            result.use(Results.json()).withoutRoot().from(requestLancamentoCnpj.relatorio1()).recursive().serialize();
        }
    }


    @Get
    @Restrict
    @Path("/lancamento-entidade/cadastrar")
    public void cadastrar() {
    }

    @Post
    @Restrict
    @Path("/lancamento-entidade/cadastrar")
    public void cadastrar(LancamentoCnpjBean lancamentoCnpjBean) {
        requestLancamentoCnpj.salvar(lancamentoCnpjBean);
    }

    @Get
    @Restrict
    @Path("/lancamento-entidade/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("lancamentoCnpjBean", requestLancamentoCnpj.bean(id));
    }

    @Post
    @Restrict
    @Path("/lancamento-entidade/atualizar/{id}")
    public void cadastrar(Integer id, LancamentoCnpjBean lancamentoCnpjBean) {
        requestLancamentoCnpj.salvar(lancamentoCnpjBean);
    }

    @Get
    @Restrict
    @Path("/lancamento-entidade/remover/{id}")
    public void remover(Integer id) {
        result.include("lancamentoCnpjBean", requestLancamentoCnpj.bean(id));
    }

    @Post
    @Restrict
    @Path("/lancamento-entidade/remover/{id}")
    public void remover(Integer id, String param) {
        requestLancamentoCnpj.remover(id);
    }
}
