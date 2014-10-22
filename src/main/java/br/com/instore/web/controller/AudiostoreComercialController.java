package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreComercialShBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreComercial;
import br.com.instore.web.tools.Utilities;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile; 

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
    public void listar(Boolean datajson, Boolean view, Boolean clientes, Integer page, Integer rows, Integer id, String titulo, String arquivo, Integer pk) {
        if (null != datajson && datajson) {
            requestAudiostoreComercial.beanList(page, rows, id, titulo, arquivo);
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreComercial.bean2(pk)).recursive().serialize();
        }
    }

    @Get
    @Restrict 
    @Path("/audiostore-comercial/cadastrar")
    public void cadastrar() {
        result.include("isPageCadastro", true);
        result.include("categoriaBeanList", requestAudiostoreComercial.categoriaBeanList());
        result.include("clienteBeanList", requestAudiostoreComercial.clienteBeanList());
        result.include("audiostoreComercialShBeanList", requestAudiostoreComercial.audiostoreComercialShBeanList());
    }

    @Get
    @Path("/audiostore-comercial/cadastrar/validador")
    public void validador(String titulo , String periodoInicial , String periodoFinal , String nomeArquivo , Integer idcliente) {
        requestAudiostoreComercial.validador(titulo, periodoInicial, periodoFinal, nomeArquivo, idcliente);
    }

    @Post
    @Restrict
    @Path("/audiostore-comercial/cadastrar")
    public void cadastrar(AudiostoreComercialBean audiostoreComercialBean, String tempoTotal, AudiostoreComercialShBean[] sh, UploadedFile arquivo) {
        requestAudiostoreComercial.salvar(audiostoreComercialBean, tempoTotal, sh, arquivo);
    }

    @Get
    @Restrict
    @Path("/audiostore-comercial/atualizar/{id}")
    public void cadastrar(Integer id) {
        AudiostoreComercialBean bean = requestAudiostoreComercial.bean(id);
        result.include("audiostoreComercialBean", bean);
        result.include("isPageCadastro", false);
        result.include("shs", requestAudiostoreComercial.shs(id));
        result.include("categoriaBeanList", requestAudiostoreComercial.categoriaBeanList(bean.getId()));
        result.include("clienteBeanList", requestAudiostoreComercial.clienteBeanList());
        result.include("audiostoreComercialShBeanList", requestAudiostoreComercial.audiostoreComercialShBeanList());
        
    }

    @Post
    @Restrict
    @Path("/audiostore-comercial/atualizar/{id}")
    public void cadastrar(Integer id, AudiostoreComercialBean audiostoreComercialBean, String tempoTotal, AudiostoreComercialShBean[] sh) {
        requestAudiostoreComercial.salvar2(audiostoreComercialBean, tempoTotal, sh);
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
//        requestAudiostoreComercial.upload(id);
    }
    
    @Get
    @Path("/audiostore-comercial/dep/{idcliente}/{id}")
    public void dep(Integer idcliente, Integer id) {
        requestAudiostoreComercial.dep(idcliente , id);
    }
    
    @Post
    @Path("/audiostore-comercial/vld-comm")
    public void validarComercial(Integer[] id_list  , Boolean exp_arquivo_audio) {
        requestAudiostoreComercial.validarComercial(id_list , exp_arquivo_audio);
    }
    
    @Post
    @Path("/audiostore-comercial/vld-categ")
    public void validarCateg(Integer idcliente) {
        requestAudiostoreComercial.validarCateg(idcliente);
    }
}
