package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreGravadora;
import br.com.instore.web.component.request.RequestUsuario;
import javax.inject.Inject;


@Controller
public class UsuarioController {
    @Inject
    private Result result;
    
    @Inject
    private RequestUsuario requestUsuario;

    public UsuarioController() {
    }

    public UsuarioController(Result result, RequestUsuario requestUsuario) {
        this.result = result;
        this.requestUsuario = requestUsuario;
    }
    

    @Get
    @Restrict
    @Path("/usuario")
    public void usuarios(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestUsuario.beanList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/usuario/cadastrar")
    public void cadastrar() {
        result.include("estadoBeanList", requestUsuario.estadoBeanList());
    }

    @Post
    @Restrict
    @Path("/usuario/cadastrar")
    public void cadastrar(UsuarioBean usuarioBean ) {
        requestUsuario.salvar(usuarioBean);
    }

    @Get
    @Restrict
    @Path("/usuario/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("usuarioBean", requestUsuario.bean(id));
        result.include("estadoBeanList", requestUsuario.estadoBeanList());
    }

    @Post
    @Restrict
    @Path("/usuario/atualizar/{id}")
    public void cadastrar(Integer id , UsuarioBean usuarioBean  ) {
        requestUsuario.salvar(usuarioBean);
    }

    @Get
    @Restrict
    @Path("/usuario/remover/{id}")
    public void remover(Integer id) {
        result.include("usuarioBean", requestUsuario.bean(id));
    }

    @Post
    @Restrict
    @Path("/usuario/remover/{id}")
    public void remover(Integer id, String param) {
        requestUsuario.remover(id);
    }
}
