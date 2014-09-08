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
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreComercialShBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreComercial;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

@Controller
public class AudiostoreComercialController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestAudiostoreComercial requestAudiostoreComercial;

    public AudiostoreComercialController() {
    }

    public AudiostoreComercialController(Result result, RequestAudiostoreComercial requestAudiostoreComercial) {
        this.result = result;
        this.requestAudiostoreComercial = requestAudiostoreComercial;
    }

    @Get
    @Restrict
    @Path("/audiostore-comercial")
    public void listar(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreComercial.beanList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/audiostore-comercial/cadastrar")
    public void cadastrar() {
//        AudiostoreComercialBean a = new AudiostoreComercialBean();
//        a.setArquivo("xx.mp3");
//        a.setAudiostoreCategoria( new AudiostoreCategoriaBean(1));
//        a.setData(new Date());
//        a.setDataVencimento(new Date());
//        a.setDependencia1("a");
//        a.setDependencia2("b");
//        a.setDependencia3("c");
//        a.setDiasAlternados(Boolean.FALSE);
//        a.setDiasSemana( new Short("1"));
//        a.setFrameFinal(1);
//        a.setFrameInicio(1);
//        a.setMsg("aaa");
//        a.setPeriodoFinal(new Date());
//        a.setPeriodoInicial(new Date());
//        a.setQtde(1);
//        a.setRandom(10);
//        a.setSemSom(Boolean.FALSE);
//        a.setTempoTotal(new Date());
//        a.setTipoInterprete(new Short("1"));
//        a.setTitulo("xx.mp3");
//        a.setUltimaExecucao(new Date());
        
//        result.include("audiostoreComercialBean", a);
        
        result.include("isPageCadastro", true);
        result.include("categoriaBeanList", requestAudiostoreComercial.categoriaBeanList());
        result.include("clienteBeanList", requestAudiostoreComercial.clienteBeanList());
        result.include("audiostoreComercialShBeanList", requestAudiostoreComercial.audiostoreComercialShBeanList());
    }

    @Post
    @Restrict
    @Path("/audiostore-comercial/cadastrar")
    public void cadastrar(AudiostoreComercialBean audiostoreComercialBean , String tempoTotal , AudiostoreComercialShBean []  sh, UploadedFile arquivo) {
        requestAudiostoreComercial.salvar(audiostoreComercialBean  , tempoTotal , sh , arquivo);
    }

    @Get
    @Restrict
    @Path("/audiostore-comercial/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("isPageCadastro", false);
        result.include("shs", requestAudiostoreComercial.shs(id));
        result.include("categoriaBeanList", requestAudiostoreComercial.categoriaBeanList());
        result.include("clienteBeanList", requestAudiostoreComercial.clienteBeanList());
        result.include("audiostoreComercialShBeanList", requestAudiostoreComercial.audiostoreComercialShBeanList());
        result.include("audiostoreComercialBean", requestAudiostoreComercial.bean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-comercial/atualizar/{id}")
    public void cadastrar(Integer id , AudiostoreComercialBean audiostoreComercialBean , String tempoTotal  , AudiostoreComercialShBean [] sh) {
        requestAudiostoreComercial.salvar2(audiostoreComercialBean,tempoTotal,sh);
    }

    @Get
    @Restrict
    @Path("/audiostore-comercial/remover/{id}")
    public void remover(Integer id) {
        result.include("audiostoreComercialBean", requestAudiostoreComercial.bean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-comercial/remover/{id}")
    public void remover(Integer id, String param) {
        requestAudiostoreComercial.remover(id);
    }
    
    @Get
    @Restrict
    @Path("/audiostore-comercial/download-exp/{id}")
    public InputStreamDownload download(Integer id) {
//        return requestAudiostoreComercial.download(id);
        return null;
    }
    
    @Get
    @Restrict
    @Path("/audiostore-comercial/upload-exp/{id}")
    public void upload(Integer id) {
        requestAudiostoreComercial.upload(id);
    }
}
