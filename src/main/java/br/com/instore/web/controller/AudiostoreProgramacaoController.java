package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreCategoria;
import br.com.instore.web.component.request.RequestAudiostoreProgramacao;
import javax.inject.Inject;

@Controller
public class AudiostoreProgramacaoController implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private RequestAudiostoreProgramacao requestAudiostoreProgramacao;

    public AudiostoreProgramacaoController() {
    }

    public AudiostoreProgramacaoController(Result result, RequestAudiostoreProgramacao requestAudiostoreProgramacao) {
        this.result = result;
        this.requestAudiostoreProgramacao = requestAudiostoreProgramacao;
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao")
    public void programacoes(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreProgramacao.programacaoDTOList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao/cadastrar")
    public void cadastrar(Integer clonar, String paramInut) {
        result.include("isPageCadastro", true);

        if (null != clonar && clonar > 0) {
            result.include("programacaoCategoriaBeanList", requestAudiostoreProgramacao.programacaoCategoriaBeanList(clonar));
            result.include("audiostoreProgramacaoBean", requestAudiostoreProgramacao.audiostoreProgramacaoBean(clonar));
        }

        result.include("clienteBeanList", requestAudiostoreProgramacao.clienteBeanList());
        result.include("categoriaBeanList", requestAudiostoreProgramacao.categoriaBeanList());
    }

    @Post
    @Restrict
    @Path("/audiostore-programacao/cadastrar")
    public void cadastrar(AudiostoreProgramacaoBean audiostoreProgramacaoBean, String horaInicio, String horaFinal, Integer[] categorias, Integer[] diasSemana) {
        requestAudiostoreProgramacao.salvar(audiostoreProgramacaoBean, horaInicio, horaFinal, categorias, diasSemana);
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("isPageCadastro", false);
        result.include("programacaoCategoriaBeanList", requestAudiostoreProgramacao.programacaoCategoriaBeanList(id));
        result.include("audiostoreProgramacaoBean", requestAudiostoreProgramacao.audiostoreProgramacaoBean(id));
        result.include("clienteBeanList", requestAudiostoreProgramacao.clienteBeanList());
        result.include("categoriaBeanList", requestAudiostoreProgramacao.categoriaBeanList());
    }

    @Post
    @Restrict
    @Path("/audiostore-programacao/atualizar/{id}")
    public void cadastrar(Integer id, AudiostoreProgramacaoBean audiostoreProgramacaoBean, String horaInicio, String horaFinal, Integer[] categorias, Integer[] diasSemana) {
        requestAudiostoreProgramacao.salvar(audiostoreProgramacaoBean, horaInicio, horaFinal, categorias, diasSemana);
    }

    @Get
    @Restrict
    @Path("/audiostore-programacao/remover/{id}")
    public void remover(Integer id) {
        result.include("audiostoreProgramacaoBean", requestAudiostoreProgramacao.audiostoreProgramacaoBean(id));
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
        requestAudiostoreProgramacao.upload(id);
    }

    @Get
    @Path("/audiostore-programacao/categorias/{id}")
    public void categorias(Integer id) {
        result.use(Results.json()).withoutRoot().from(requestAudiostoreProgramacao.categorias(id)).recursive().serialize();
    }
}
