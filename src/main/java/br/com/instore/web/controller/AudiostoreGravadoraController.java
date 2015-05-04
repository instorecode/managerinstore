package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreGravadora;
import javax.inject.Inject;

@Resource
public class AudiostoreGravadoraController implements java.io.Serializable {

    private Result result;
    private RequestAudiostoreGravadora requestAudiostoreGravadora;

    public AudiostoreGravadoraController(Result result, RequestAudiostoreGravadora requestAudiostoreGravadora) {
        this.result = result;
        this.requestAudiostoreGravadora = requestAudiostoreGravadora;
    }
    
    @Get
    @Restrict
    @Path("/audiostore-gravadora")
    public void listar(Boolean datajson, Boolean view, Integer page, Integer rows, Integer id, String nome, Integer pk) {

        if (null != datajson && datajson) {
            requestAudiostoreGravadora.beanList(datajson, view, page, rows, id, nome);
        } else {
            result.include("clienteBeanList", requestAudiostoreGravadora.clienteMatrizBeanList());
        }

        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreGravadora.beanDTO(pk)).recursive().serialize();
        } else {
            result.include("clienteBeanList", requestAudiostoreGravadora.clienteMatrizBeanList());
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
    
    @Post
    @Path("/audiostore-gravadora/exp")
    public void gerarExp (Integer [] id_list, Integer id_cliente){
//        System.out.println("id cliente " + id_cliente);
//        System.out.println("id list " + id_list);
       requestAudiostoreGravadora.validarGravadora(id_list, id_cliente);
    }
}
