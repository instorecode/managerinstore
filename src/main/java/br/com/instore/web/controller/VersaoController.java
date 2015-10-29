package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestVersao;
import java.util.Date;
import br.com.instore.core.orm.bean.VersaoBean;

@Resource
public class VersaoController implements java.io.Serializable {

    private Result result;
    private RequestVersao requestVersao;

    public VersaoController(Result result, RequestVersao requestVersao) {
        this.result = result;
        this.requestVersao = requestVersao;
    }

    @Get
    @Restrict
    @Path("/versoes")
    public void listar(Boolean datajson, Boolean view, Integer page, Integer rows, Integer id, Date dataCriacao, String nome, String descricao, Integer idProjeto, String linkSvn, Boolean download, Integer pk) {
        if (null != datajson && datajson) {
            requestVersao.beanList(page, rows, id, dataCriacao, nome, descricao, idProjeto, linkSvn, download);
        } else {
//            result.include();
            result.include("countProjetos", requestVersao.listaProjetos().size());
            result.include("projetos", requestVersao.listaProjetos());
        }

        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestVersao.bean(pk)).recursive().serialize();
        }

    }

    @Get
    @Restrict
    @Path("versao/cadastrar")
    public void cadastrar() {

    }

    @Post
    @Restrict
    @Path("versao/cadastrar")
    public void cadastrar(VersaoBean bean) {
        requestVersao.salvar(bean);
    }
}
