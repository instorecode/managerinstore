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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void listar(Boolean datajson, int rel) {
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

    @Get
    @Restrict
    @Path("/lancamento-entidade/relatorio")
    public void relatorio(Integer id, String d1s, String d2s) {
        Date d1 = null;
        Date d2 = null;

        try {
            if (null != d1s && !d1s.isEmpty()) {
                d1 = new SimpleDateFormat("dd/MM/yyyy").parse(d1s);
            }

            if (null != d2s && !d2s.isEmpty()) {
                d2 = new SimpleDateFormat("dd/MM/yyyy").parse(d2s);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        result.include("d1", d1);
        result.include("d2", d2);

        requestLancamentoCnpj.relatorios(id, d1, d2);
    }
}
