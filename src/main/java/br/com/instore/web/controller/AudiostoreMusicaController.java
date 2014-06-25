package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.core.orm.bean.ContatoClienteBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreGravadora;
import br.com.instore.web.component.request.RequestAudiostoreMusica;
import br.com.instore.web.component.request.RequestContatoCliente;
import br.com.instore.web.component.request.RequestVoz;
import javax.inject.Inject;

@Controller
public class AudiostoreMusicaController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestAudiostoreMusica requestAudiostoreMusica;

    public AudiostoreMusicaController() {
    }

    public AudiostoreMusicaController(Result result, RequestAudiostoreMusica requestAudiostoreMusica) {
        this.result = result;
        this.requestAudiostoreMusica = requestAudiostoreMusica;
    }

    @Get
    @Restrict
    @Path("/audiostore-musica")
    public void musicas(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreMusica.beanList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/audiostore-musica/cadastrar")
    public void cadastrar() {
        result.include("categoriaBeanList", requestAudiostoreMusica.categoriaBeanList());
        result.include("clienteBeanList", requestAudiostoreMusica.clienteBeanList());
        result.include("gravadoraBeanList", requestAudiostoreMusica.gravadoraBeanList());
    }

    @Post
    @Restrict
    @Path("/audiostore-musica/cadastrar")
    public void cadastrar(AudiostoreMusicaBean audiostoreMusicaBean ) {
        requestAudiostoreMusica.salvar(audiostoreMusicaBean);
    }

    @Get
    @Restrict
    @Path("/audiostore-musica/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("categoriaBeanList", requestAudiostoreMusica.categoriaBeanList());
        result.include("clienteBeanList", requestAudiostoreMusica.clienteBeanList());
        result.include("gravadoraBeanList", requestAudiostoreMusica.gravadoraBeanList());
        result.include("audiostoreGravadoraBean", requestAudiostoreMusica.bean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-musica/atualizar/{id}")
    public void cadastrar(Integer id , AudiostoreMusicaBean audiostoreMusicaBean  ) {
        requestAudiostoreMusica.salvar(audiostoreMusicaBean);
    }

    @Get
    @Restrict
    @Path("/audiostore-musica/remover/{id}")
    public void remover(Integer id) {
        result.include("audiostoreGravadoraBean", requestAudiostoreMusica.bean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-musica/remover/{id}")
    public void remover(Integer id, String param) {
        requestAudiostoreMusica.remover(id);
    }
}
