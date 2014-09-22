package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreCategoria;
import br.com.instore.web.tools.AjaxResult;
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
    public void categorias(Boolean datajson , Boolean view , Integer page, Integer rows, Integer id , Integer cliente ,  String nome , Integer tipo , String duracao , String dataInicio, String dataFinal , Integer pk) {
        if (null != datajson && datajson) {
            requestAudiostoreCategoria.beanList(page, rows, id, cliente, nome, tipo, duracao, dataInicio, dataFinal);
        } else {
            result.include("clienteBeanList", requestAudiostoreCategoria.clienteBeanList());
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreCategoria.audiostoreCategoriaBean(pk)).recursive().serialize();
        } else {
            result.include("clienteBeanList", requestAudiostoreCategoria.clienteBeanList());
        }
    }

    @Get
    @Restrict
    @Path("/audiostore-categoria/cadastrar")
    public void cadastrar() {
        result.include("isPageCadastro", true);
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
        result.include("isPageCadastro", false);
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
    
    @Get
    @Restrict
    @Path("/audiostore-categoria/download-exp/{id}")
    public InputStreamDownload download(Integer id) {
        return requestAudiostoreCategoria.download(id);
    }
    
    @Get
    @Restrict
    @Path("/audiostore-categoria/upload-exp/{id}")
    public void upload(Integer id) {
//        requestAudiostoreCategoria.upload(id);
    }
    
    @Post
    @Path("/audiostore-categoria/sinc")
    public void upload(UploadedFile file) {
//        requestAudiostoreCategoria.upload(file);
    }
}
