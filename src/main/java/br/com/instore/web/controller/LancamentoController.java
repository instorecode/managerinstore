package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.LancamentoBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestLancamento;
import java.util.Date;
import javax.inject.Inject;

@Controller
public class LancamentoController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestLancamento requestLancamento;

    public LancamentoController() {
    }

    public LancamentoController(Result result, RequestLancamento requestLancamento) {
        this.result = result;
        this.requestLancamento = requestLancamento;
    }

    @Get
    @Restrict
    @Path("/lancamento")
    public void listar(Boolean datajson , Boolean efetuar , Integer id , Boolean efetuarJa) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestLancamento.beanList()).recursive().serialize();
        }
        if (null != efetuar && efetuar) {
            result.use(Results.json()).withoutRoot().from(requestLancamento.efetuarLancamento(id)).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/lancamento/cadastrar")
    public void cadastrar() {
        result.include("cadastrar", true);
        result.include("lancamentoCnpjList", requestLancamento.lancamentoCnpjList());
    }

    @Post
    @Restrict
    @Path("/lancamento/cadastrar")
    public void cadastrar(LancamentoBean lancamentoBean , Date d1 , Date d2) {
        requestLancamento.salvar(lancamentoBean , d1 , d2);
    }

    @Get
    @Restrict
    @Path("/lancamento/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("cadastrar", false);
        result.include("lancamentoBean", requestLancamento.bean(id));
        result.include("lancamentoCnpjList", requestLancamento.lancamentoCnpjList());
    }

    @Post
    @Restrict
    @Path("/lancamento/atualizar/{id}")
    public void cadastrar(Integer id , LancamentoBean lancamentoBean  , Date d1 , Date d2 ) {
        requestLancamento.salvar(lancamentoBean , d1 , d2);
    }

    @Get
    @Restrict
    @Path("/lancamento/remover/{id}")
    public void remover(Integer id) {
        result.include("lancamentoBean", requestLancamento.bean(id));
    }

    @Post
    @Restrict
    @Path("/lancamento/remover/{id}")
    public void remover(Integer id, String param) {
        requestLancamento.remover(id);
    }
}
