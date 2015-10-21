package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ProjetoBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestProjeto;
import java.text.SimpleDateFormat;

@Resource
public class ProjetoController implements java.io.Serializable {

    private Result result;
    private RequestProjeto requestProjeto;

    public ProjetoController(Result result, RequestProjeto requestProjeto) {
        this.result = result;
        this.requestProjeto = requestProjeto;
    }

    @Get
    @Restrict
    @Path("/projetos")
    public void listar(Boolean datajson, Boolean view, Integer page, Integer rows, Integer id, String descricao, String nome, String idUsuario, String linkDocumentacao, String dataCriacao, Integer pk) {
        if (null != datajson && datajson) {
            requestProjeto.beanList(page, rows, id, descricao, nome, idUsuario, linkDocumentacao, dataCriacao);
        }

        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestProjeto.bean(pk)).recursive().serialize();
        }
    }
    
    @Get
    @Restrict
    @Path("/projeto/cadastrar")
    public void cadastrar() {
    }

    @Post
    @Restrict
    @Path("/projeto/cadastrar")
    public void cadastrar(ProjetoBean projetoBean ) {
        requestProjeto.salvar(projetoBean, null);
    }

    @Get
    @Restrict
    @Path("/projeto/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("projetoBean", requestProjeto.bean(id));
    }

    @Post
    @Restrict
    @Path("/projeto/atualizar/{id}")
    public void cadastrar(Integer id , String dataCriacao , ProjetoBean projetoBean ) {
        requestProjeto.salvar(projetoBean,dataCriacao);
    }

    @Get
    @Restrict
    @Path("/projeto/remover/{id}")
    public void remover(Integer id) {
        result.include("projetoBean", requestProjeto.bean(id));
    }

    @Post
    @Restrict
    @Path("/projeto/remover/{id}")
    public void remover(Integer id, String param) {
        requestProjeto.remover(id);
    }
}
