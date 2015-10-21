package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreComercialShBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreComercial;

@Resource
public class AudiostoreComercialController implements java.io.Serializable {

    private Result result;
    private RequestAudiostoreComercial requestAudiostoreComercial;

    public AudiostoreComercialController(Result result, RequestAudiostoreComercial requestAudiostoreComercial) {
        this.result = result;
        this.requestAudiostoreComercial = requestAudiostoreComercial;
    }

    @Get
    @Restrict
    @Path("/audiostore-comercial")
    public void listar(Boolean datajson, Boolean view, Boolean clientes, Integer page, Boolean categorias,  Integer rows, Integer id, Integer idcliente, String titulo, String arquivo, Integer codigo, Integer pk) {
        if (null != datajson && datajson) {
            requestAudiostoreComercial.beanList(page, rows, id, titulo, arquivo , codigo , idcliente );
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreComercial.bean2(pk)).recursive().serialize();
        }
        
        if (null != categorias && categorias) {
            if(null != idcliente && idcliente > 0) {
                result.use(Results.json()).withoutRoot().from(requestAudiostoreComercial.categoriaBeanListByCliente(idcliente)).recursive().serialize();
            } else {
                result.use(Results.json()).withoutRoot().from(requestAudiostoreComercial.categoriaBeanList()).recursive().serialize();
            }
            
        }
        
        if (null != clientes && clientes) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreComercial.clienteBeanList2()).recursive().serialize();
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
        result.include("categoriaBeanList", requestAudiostoreComercial.categoriaBeanList());
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
    public void validarComercial(Integer[] id_list  , Boolean exp_arquivo_audio , Integer idcliente, String titulo, String arquivo, Integer codigo) {
        requestAudiostoreComercial.validarComercial(id_list, exp_arquivo_audio, idcliente, titulo, arquivo, codigo);
    }
    
    @Post
    @Path("/audiostore-comercial/vld-categ")
    public void validarCateg(Integer idcliente) {
        requestAudiostoreComercial.validarCateg(idcliente);
    }
}
