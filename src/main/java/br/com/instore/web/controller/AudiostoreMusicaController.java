package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreMusica;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import jcifs.smb.SmbException;

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
        result.include("cadastrar", true);
        result.include("clienteBeanList", requestAudiostoreMusica.clienteBeanList());
        result.include("gravadoraBeanList", requestAudiostoreMusica.gravadoraBeanList());
    }

    @Post
    @Restrict
    @Path("/audiostore-musica/cadastrar")
    public void cadastrar(AudiostoreMusicaBean audiostoreMusicaBean, String tempoTotal) {
        requestAudiostoreMusica.salvar(audiostoreMusicaBean, tempoTotal);
    }

    @Get
    @Restrict
    @Path("/audiostore-musica/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("cadastrar", false);
        result.include("gravadoraBeanList", requestAudiostoreMusica.gravadoraBeanList());
        result.include("clienteBeanList", requestAudiostoreMusica.clienteBeanList());
        result.include("audiostoreMusicaBean", requestAudiostoreMusica.bean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-musica/atualizar/{id}")
    public void cadastrar(Integer id, AudiostoreMusicaBean audiostoreMusicaBean, String tempoTotal) {
        requestAudiostoreMusica.salvar(audiostoreMusicaBean, tempoTotal);
    }

    @Get
    @Restrict
    @Path("/audiostore-musica/remover/{id}")
    public void remover(Integer id) {
        result.include("audiostoreMusicaBean", requestAudiostoreMusica.bean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-musica/remover/{id}")
    public void remover(Integer id, String param) {
        requestAudiostoreMusica.remover(id);
    }

    @Get
    @Restrict
    @Path("/audiostore-musica/download-exp/{id}")
    public InputStreamDownload download(Integer id) {
        return requestAudiostoreMusica.download(id);
    }

    @Get
    @Restrict
    @Path("/audiostore-musica/upload-exp/{id}")
    public void upload(Integer id) {
        requestAudiostoreMusica.upload(id);
    }

    @Get
    @Path("/audiostore-musica/informacao")
    public void informacao(Integer idcliente) {
        try {
            requestAudiostoreMusica.carregarInforWizard(idcliente);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Get
    @Path("/audiostore-musica/loadmusica")
    public InputStreamDownload loadmusica(String url) {       
        try {
            return requestAudiostoreMusica.loadMusica(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
