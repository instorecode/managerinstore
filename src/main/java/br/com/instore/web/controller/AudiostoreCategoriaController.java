package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.ContatoClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreCategoria;
import br.com.instore.web.component.request.RequestCliente;
import br.com.instore.web.component.request.RequestContatoCliente;
import javax.inject.Inject;

@Controller
public class AudiostoreCategoriaController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestAudiostoreCategoria requestAudiostoreCategoria;

    public AudiostoreCategoriaController() {
    }

    public AudiostoreCategoriaController(Result result, RequestAudiostoreCategoria requestAudiostoreCategoria) {
        this.result = result;
        this.requestAudiostoreCategoria = requestAudiostoreCategoria;
    }


    @Get
    @Restrict
    @Path("/audiostore-categorias")
    public void categorias(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreCategoria.categoriaDTOList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/audiostore-categoria/cadastrar")
    public void cadastrar() {
        result.include("clienteBeanList", requestAudiostoreCategoria.clienteBeanList());
    }

    @Post
    @Restrict
    @Path("/audiostore-categoria/cadastrar")
    public void cadastrar(AudiostoreCategoriaBean audiostoreCategoriaBean , String tempo) {
        requestAudiostoreCategoria.salvar(audiostoreCategoriaBean,tempo);
    }

    @Get
    @Restrict
    @Path("/audiostore-categoria/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("audiostoreCategoriaBean", requestAudiostoreCategoria.audiostoreCategoriaBean(id));
        result.include("clienteBeanList", requestAudiostoreCategoria.clienteBeanList());
    }

    @Post
    @Restrict
    @Path("/audiostore-categoria/atualizar/{id}")
    public void cadastrar(Integer id , AudiostoreCategoriaBean audiostoreCategoriaBean , String tempo) {
        requestAudiostoreCategoria.salvar(audiostoreCategoriaBean,tempo);
    }

    @Get
    @Restrict
    @Path("/audiostore-categoria/remover/{id}")
    public void remover(Integer id) {
        result.include("audiostoreCategoriaBean", requestAudiostoreCategoria.audiostoreCategoriaBean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-categoria/remover/{id}")
    public void remover(Integer id, String param) {
        requestAudiostoreCategoria.remover(id);
    }
}
