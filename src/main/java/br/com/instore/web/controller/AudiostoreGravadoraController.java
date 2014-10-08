package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.ContatoClienteBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreGravadora;
import br.com.instore.web.component.request.RequestContatoCliente;
import br.com.instore.web.component.request.RequestVoz;
import javax.inject.Inject;

@Controller
public class AudiostoreGravadoraController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestAudiostoreGravadora requestAudiostoreGravadora;

    public AudiostoreGravadoraController() {
    }

    public AudiostoreGravadoraController(Result result, RequestAudiostoreGravadora requestAudiostoreGravadora) {
        this.result = result;
        this.requestAudiostoreGravadora = requestAudiostoreGravadora;
    }

    @Post
    @Path("/audiostore-gravadora/download")
    public void downloadExp (){
        requestAudiostoreGravadora.gerarExp();
    }
    
    @Get
    @Restrict
    @Path("/audiostore-gravadora")
    public void listar(Boolean datajson, Boolean view, Integer page, Integer rows, Integer id, String nome, Integer pk) {

        if (null != datajson && datajson) {
            requestAudiostoreGravadora.beanList(datajson, view, page, rows, id, nome);
        } else {
            result.include("clienteBeanList", requestAudiostoreGravadora.clienteBeanList());
        }

        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreGravadora.beanDTO(pk)).recursive().serialize();
        } else {
            result.include("clienteBeanList", requestAudiostoreGravadora.clienteBeanList());
        }


    }

    @Get
    @Restrict
    @Path("/audiostore-gravadora/cadastrar")
    public void cadastrar() {
        
    }

    @Post
    @Restrict
    @Path("/audiostore-gravadora/cadastrar")
    public void cadastrar(AudiostoreGravadoraBean audiostoreGravadoraBean) {
        requestAudiostoreGravadora.salvar(audiostoreGravadoraBean);
    }

    @Get
    @Restrict
    @Path("/audiostore-gravadora/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("audiostoreGravadoraBean", requestAudiostoreGravadora.bean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-gravadora/atualizar/{id}")
    public void cadastrar(Integer id, AudiostoreGravadoraBean audiostoreGravadoraBean) {
        requestAudiostoreGravadora.salvar(audiostoreGravadoraBean);
    }

    @Get
    @Restrict
    @Path("/audiostore-gravadora/remover/{id}")
    public void remover(Integer id) {
        result.include("audiostoreGravadoraBean", requestAudiostoreGravadora.bean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-gravadora/remover/{id}")
    public void remover(Integer id, String param) {
        requestAudiostoreGravadora.remover(id);
    }

    @Get
    @Restrict
    @Path("/audiostore-gravadora/download-exp/{id}")
    public InputStreamDownload download(Integer id) {
        return requestAudiostoreGravadora.download(id);
    }

    @Get
    @Restrict
    @Path("/audiostore-gravadora/upload-exp/{id}")
    public void upload(Integer id) {
        requestAudiostoreGravadora.upload(id);
    }
}
