package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.PerfilBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreGravadora;
import br.com.instore.web.component.request.RequestPerfil;
import javax.inject.Inject;


@Controller
public class PerfilController {
    @Inject
    private Result result;
    
    @Inject
    private RequestPerfil requestPerfil;

    public PerfilController() {
    }

    public PerfilController(Result result, RequestPerfil requestPerfil) {
        this.result = result;
        this.requestPerfil = requestPerfil;
    }

    @Get
    @Restrict
    @Path("/perfil")
    public void perfis(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestPerfil.beanList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/perfil/cadastrar")
    public void cadastrar() {
        
    }

    @Post
    @Restrict
    @Path("/perfil/cadastrar")
    public void cadastrar(PerfilBean perfilBean ) {
        requestPerfil.salvar(perfilBean);
    }

    @Get
    @Restrict
    @Path("/perfil/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("perfilBean", requestPerfil.bean(id));
    }

    @Post
    @Restrict
    @Path("/perfil/atualizar/{id}")
    public void cadastrar(Integer id , PerfilBean perfilBean  ) {
        requestPerfil.salvar(perfilBean);
    }

    @Get
    @Restrict
    @Path("/perfil/remover/{id}")
    public void remover(Integer id) {
        result.include("perfilBean", requestPerfil.bean(id));
    }

    @Post
    @Restrict
    @Path("/perfil/remover/{id}")
    public void remover(Integer id, String param) {
        requestPerfil.remover(id);
    }
}
