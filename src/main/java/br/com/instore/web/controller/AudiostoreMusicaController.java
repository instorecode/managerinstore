package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.core.orm.bean.MusicaGeralBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreMusica;
import java.io.IOException;
import java.net.MalformedURLException;
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
    @Path("/musica/programacao-audiostore")
    public void listar(Integer idmusicaGeral, Boolean clientes, Boolean categorias, Boolean datajson, Boolean view, Integer page, Integer rows, Integer id, Integer idcliente,  String arquivo,  String nome, Integer codigo, String letra , Integer pk) {
        if (null != datajson && datajson) {
            requestAudiostoreMusica.beanList(datajson, view, page, rows, id, idcliente, arquivo , nome, codigo, letra);
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreMusica.bean2(pk)).recursive().serialize();
        }

        if (null != clientes && clientes) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreMusica.clienteBeanList()).recursive().serialize();
        }

        if (null != categorias && categorias) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreMusica.categorias()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/musica/programacao-audiostore/cadastrar/{idmusicaGeral}")
    public void cadastrar(String idmusicaGeral, Integer clonar) {
        if (null != clonar && clonar > 0) {
            AudiostoreMusicaBean audiostoreMusicaBean = requestAudiostoreMusica.bean(clonar);
            result.include("cadastrar", false);
            result.include("idmusicaGeral", idmusicaGeral);
            result.include("clienteBeanList", requestAudiostoreMusica.clienteBeanList());
            result.include("audiostoreMusicaBean", audiostoreMusicaBean);
            result.include("categoriasByCliente2", requestAudiostoreMusica.categoriasByCliente2(audiostoreMusicaBean.getCliente().getIdcliente()));
        } else {
            requestAudiostoreMusica.cadastrar(idmusicaGeral);
        }

//        result.include("cadastrar", true);
//        result.include("idmusicaGeral", idmusicaGeral);
//        result.include("clienteBeanList", requestAudiostoreMusica.clienteBeanList());
//        result.include("gravadoraBeanList", requestAudiostoreMusica.gravadoraBeanList()); 
    }

    @Post
    @Restrict
    @Path("/musica/programacao-audiostore/cadastrar/{idmusicaGeral}")
    public void cadastrar(String idmusicaGeral, AudiostoreMusicaBean[] audiostoreMusicaBeanList) {
        requestAudiostoreMusica.salvar(audiostoreMusicaBeanList);
    }

    @Get
    @Restrict
    @Path("/musica/programacao-audiostore/atualizar/{id}")
    public void cadastrar(Integer idmusicaGeral, Integer id) {
        AudiostoreMusicaBean audiostoreMusicaBean = requestAudiostoreMusica.bean(id);
        result.include("cadastrar", false);
        result.include("idmusicaGeral", idmusicaGeral);
        result.include("musicaGeralBean", requestAudiostoreMusica.mgBean(audiostoreMusicaBean.getMusicaGeral()));
        result.include("clienteBeanList", requestAudiostoreMusica.clienteBeanList());
        result.include("audiostoreMusicaBean", audiostoreMusicaBean);
        result.include("categoriasByCliente2", requestAudiostoreMusica.categoriasByCliente2(audiostoreMusicaBean.getCliente().getIdcliente()));
    }

    @Post
    @Restrict
    @Path("/musica/programacao-audiostore/atualizar/{id}")
    public void cadastrar(Integer id, AudiostoreMusicaBean audiostoreMusicaBean) {
        AudiostoreMusicaBean[] audiostoreMusicaBeanList = new AudiostoreMusicaBean[1];
        audiostoreMusicaBeanList[0] = audiostoreMusicaBean;
        requestAudiostoreMusica.salvar(audiostoreMusicaBeanList);
    }

    @Get
    @Restrict
    @Path("/musica/programacao-audiostore/remover/{id}")
    public void remover(Integer idmusicaGeral, Integer id) {
        AudiostoreMusicaBean audiostoreMusicaBean = requestAudiostoreMusica.bean(id);
        MusicaGeralBean musicaGeralBean = requestAudiostoreMusica.musicaGeralBean(audiostoreMusicaBean.getMusicaGeral());
        result.include("audiostoreMusicaBean", audiostoreMusicaBean);
        result.include("musicaGeralBean", musicaGeralBean);
        result.include("idmusicaGeral", idmusicaGeral);
    }

    @Post
    @Restrict
    @Path("/musica/programacao-audiostore/remover/{id}")
    public void remover(Integer idmusicaGeral, Integer id, String param) {
        requestAudiostoreMusica.remover(id);
    }

    @Post
    @Path("/musica/programacao-audiostore/categorias-by-cliente/")
    public void categoriasByCliente(Integer clienteid) {
        requestAudiostoreMusica.categoriasByCliente(clienteid);
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
    @Path("/audiostore-musica/informacao/{idcliente}")
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
    @Path("/audiostore-musica/loadmusica/{id}/{nome}")
    public InputStreamDownload loadmusica(Integer id, String nome) {
        try {
            System.out.println("ID_EH " + id);
            System.out.println("NOME_EH" + nome);
            return requestAudiostoreMusica.loadMusica(id, nome);
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
