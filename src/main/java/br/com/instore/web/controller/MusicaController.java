package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.instore.core.orm.bean.CategoriaMusicaGeralBean;
import br.com.instore.core.orm.bean.MusicaGeralBean;
import br.com.instore.web.annotation.NaoDeslogar;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreMusica;
import br.com.instore.web.component.request.RequestMusicaGeral;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.inject.Inject;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

@Controller
public class MusicaController implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private RequestMusicaGeral requestMusicaGeral;
    @Inject
    private RequestAudiostoreMusica requestAudiostoreMusica;

    public MusicaController() {
    }

    public MusicaController(Result result, RequestMusicaGeral requestMusicaGeral, RequestAudiostoreMusica requestAudiostoreMusica) {
        this.result = result;
        this.requestMusicaGeral = requestMusicaGeral;
        this.requestAudiostoreMusica = requestAudiostoreMusica;
    }

    @Get
    @Restrict
    @Path("/musica")
    public void listar(Boolean datajson, int pagina, int qtd, int order, String titulo, String interprete, String velocidade, String anoGravacao, String letra, String categoria , String dataCadastro) {
//        if (null != datajson && datajson) {
//            result.use(Results.json()).withoutRoot().from(requestMusicaGeral.beanList()).recursive().serialize();
//        }
        requestMusicaGeral.list(pagina, qtd, order, titulo, interprete, velocidade, anoGravacao, letra, categoria, dataCadastro);
        result.include("order", order);
        result.include("titulo", titulo);
        result.include("interprete", interprete);
        result.include("velocidade", velocidade);
        result.include("anoGravacao", anoGravacao);
        result.include("letra", letra);
        result.include("categoria", categoria);
        result.include("dataCadastro", dataCadastro);
        result.include("categorias", requestMusicaGeral.categorias());
    }

    @Get
    @Restrict
    @Path("/musica/cadastrar")
    public void cadastrar() {
        result.include("isPageCadastro", true);
        result.include("categorias", requestMusicaGeral.categorias());
        result.include("gravadoras", requestMusicaGeral.gravadoras());
    }

    @Post
    @Restrict
    @Path("/musica/cadastrar")
    public void cadastrar(MusicaGeralBean musicaGeralBean, String categorias) {
        requestMusicaGeral.salvar(musicaGeralBean, categorias);
    }

    @Get
    @Restrict
    @Path("/musica/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("isPageCadastro", false);
        MusicaGeralBean musicaGeralBean = requestMusicaGeral.bean(id);
        result.include("musicaGeralBean", musicaGeralBean);

        result.include("categorias", requestMusicaGeral.categorias());
        result.include("gravadoras", requestMusicaGeral.gravadoras());

        String categoriasDaMusica = "";
        String virgula = "";
        List<CategoriaMusicaGeralBean> lista = requestMusicaGeral.categoriasDaMusica(id);
        for (CategoriaMusicaGeralBean item : lista) {
            categoriasDaMusica += virgula + requestMusicaGeral.categoria(item.getCategoria()).getNome();
            virgula = ",";
        }

        result.include("categoriasDaMusica", categoriasDaMusica);
    }

    @Post
    @Restrict
    @Path("/musica/atualizar/{id}")
    public void cadastrar(Integer id, MusicaGeralBean musicaGeralBean, String categorias) {
        requestMusicaGeral.salvar(musicaGeralBean, categorias);
    }

    @Get
    @Restrict
    @Path("/musica/remover/{id}")
    public void remover(Integer id) {
        result.include("musicaGeralBean", requestMusicaGeral.bean(id));
    }

    @Post
    @Restrict
    @Path("/musica/remover/{id}")
    public void remover(Integer id, String param) {
        requestMusicaGeral.remover(id);
    }

    @Get
    @Path("/musica/sinc")
    public void sinc(String dir, String usuario, String senha) {
        requestMusicaGeral.sinc(dir, usuario, senha);
    }

    @Get
    @NaoDeslogar
    @Path("/musica/stream/{id}")
    public InputStreamDownload stream(Integer id) {
        try {
            String caminho = requestMusicaGeral.bean(id).getArquivo();
            SmbFile smbDir = new SmbFile(caminho, Utilities.getAuthSmbDefault());
            SmbFileInputStream fileInputStream = new SmbFileInputStream(smbDir);
            if (smbDir.exists()) {
                return new InputStreamDownload(fileInputStream, smbDir.getContentType(), smbDir.getName());
            } else {
                return new InputStreamDownload(new ByteArrayInputStream("Arquivo não encontrado".getBytes()), "", "text.txt");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new InputStreamDownload(new ByteArrayInputStream("Arquivo não encontrado".getBytes()), "", "text.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return new InputStreamDownload(new ByteArrayInputStream("Arquivo não encontrado".getBytes()), "", "text.txt");
        }
    }

    @Post
    @Path("/musica/vld-msc")
    public void validarMsc(Integer[] id_list  , Boolean exp_arquivo_audio , Integer idcliente,  String arquivo,  String nome, Integer codigo) {
        requestAudiostoreMusica.validarMsc(id_list, exp_arquivo_audio, idcliente, arquivo, nome, codigo);
        
    }
}
