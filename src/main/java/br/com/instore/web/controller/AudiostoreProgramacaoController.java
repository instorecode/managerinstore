package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreProgramacao;
import javax.inject.Inject;

@Resource
public class AudiostoreProgramacaoController implements java.io.Serializable {

    private Result result;
    private RequestAudiostoreProgramacao requestAudiostoreProgramacao;

    public AudiostoreProgramacaoController(Result result, RequestAudiostoreProgramacao requestAudiostoreProgramacao) {
        this.result = result;
        this.requestAudiostoreProgramacao = requestAudiostoreProgramacao;
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao")
    public void programacoes(Boolean datajson, Boolean view, Boolean clientes, Integer page, Integer rows, Integer id, Integer idcliente, String descricao, Integer pk) {
        if (null != datajson && datajson) {
            requestAudiostoreProgramacao.beanList(page, rows, id, idcliente, descricao);
        } else {
            result.include("clienteBeanList", requestAudiostoreProgramacao.clienteBeanList());
        }

        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreProgramacao.beanDto(pk)).recursive().serialize();
        } else {
            result.include("clienteBeanList", requestAudiostoreProgramacao.clienteBeanList());
        }

        if (null != clientes && clientes) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreProgramacao.clienteBeanList()).recursive().serialize();
        } else {
            result.include("clienteBeanList", requestAudiostoreProgramacao.clienteBeanList());
        }
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao/cadastrar")
    public void cadastrar(Integer clonar, String paramInut) {
        result.include("isPageCadastro", true);

        if (null != clonar && clonar > 0) {
            result.include("programacaoCategoriaBeanList", requestAudiostoreProgramacao.programacaoCategoriaBeanList(clonar));
            result.include("audiostoreProgramacaoBean", requestAudiostoreProgramacao.bean(clonar));
        }

        result.include("clienteBeanList", requestAudiostoreProgramacao.clienteBeanList());
        result.include("categoriaBeanList", requestAudiostoreProgramacao.categoriaBeanList());
    }
    
    @Get
    @Restrict
    @Path("/audiostore-programacao/cadastrar/pagina-unica")
    public void cadastroPaginaUnica() {
    }

    @Post
    @Restrict
    @Path("/audiostore-programacao/cadastrar")
    public void cadastrar(AudiostoreProgramacaoBean audiostoreProgramacaoBean, String horaInicio, String horaFinal, Integer[] categorias, Integer[] diasSemana, String comercialHorarioA , String comercialHorarioB) {
        requestAudiostoreProgramacao.salvar(audiostoreProgramacaoBean, horaInicio, horaFinal, categorias, diasSemana);
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao/atualizar/{id}")
    public void cadastrar(Integer id) {
        AudiostoreProgramacaoBean bean = requestAudiostoreProgramacao.bean(id);
        result.include("audiostoreProgramacaoBean", bean);
        result.include("isPageCadastro", false);
//        result.include("programacaoCategoriaBeanList", requestAudiostoreProgramacao.programacaoCategoriaBeanList(id));
//        
//        result.include("clienteBeanList", requestAudiostoreProgramacao.clienteBeanList());
//        result.include("categoriaBeanList", requestAudiostoreProgramacao.categoriaBeanList());
//        result.include("comercialList", requestAudiostoreProgramacao.comercialList(bean));
//        result.include("comercialVinculadoList", requestAudiostoreProgramacao.comercialVinculadosAProgramacao(bean));
    }

    @Post
    @Restrict
    @Path("/audiostore-programacao/atualizar/{id}")
    public void cadastrar(Integer id, AudiostoreProgramacaoBean audiostoreProgramacaoBean, String horaInicio, String horaFinal, Integer[] categorias, Integer[] diasSemana, String comercialHorarioA , String comercialHorarioB) {
        requestAudiostoreProgramacao.salvar2(audiostoreProgramacaoBean, horaInicio, horaFinal, categorias, diasSemana , comercialHorarioA , comercialHorarioB);
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao/remover/{id}")
    public void remover(Integer id) {
        result.include("audiostoreProgramacaoBean", requestAudiostoreProgramacao.bean(id));
    }

    @Post
    @Restrict
    @Path("/audiostore-programacao/remover/{id}")
    public void remover(Integer id, String param) {
        requestAudiostoreProgramacao.remover(id);
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao/download-exp/{id}")
    public InputStreamDownload download(Integer id) {
        return requestAudiostoreProgramacao.download(id);
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao/upload-exp/{id}")
    public void upload(Integer id) {
//        requestAudiostoreProgramacao.upload(id);
    }

    @Get
    @Path("/audiostore-programacao/categorias/{id}")
    public void categorias(Integer id) {
        result.use(Results.json()).withoutRoot().from(requestAudiostoreProgramacao.categorias(id)).recursive().serialize();
    }

    @Post
    @Path("/audiostore-programacao/vld-prg")
    public void validarProgramacao(Integer[] id_list , Integer idcliente, String descricao , boolean expArquivoAudio) {
        requestAudiostoreProgramacao.validarProgramacao(id_list, descricao , expArquivoAudio);
    }
    
    
    @Post
    @Path("/audiostore-programacao-nodejs")
    public void nodejs(String sqls) {
        requestAudiostoreProgramacao.nodejs(sqls);
    }
}
