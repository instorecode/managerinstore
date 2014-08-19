package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.CategoriaMusicaGeralBean;
import br.com.instore.core.orm.bean.MusicaGeralBean;
import br.com.instore.web.annotation.NaoDeslogar;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestMusicaGeral;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

@Controller
public class MusicaController implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private RequestMusicaGeral requestMusicaGeral;

    public MusicaController() {
    }

    public MusicaController(Result result, RequestMusicaGeral requestMusicaGeral) {
        this.result = result;
        this.requestMusicaGeral = requestMusicaGeral;
    }



    @Get
    @Restrict
    @Path("/musica")
    public void listar(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestMusicaGeral.beanList()).recursive().serialize();
        }
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
    public void cadastrar(MusicaGeralBean musicaGeralBean , String categorias) {
        requestMusicaGeral.salvar(musicaGeralBean , categorias);
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
        requestMusicaGeral.salvar(musicaGeralBean , categorias);
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
    
    @Post
    @Path("/musica/sinc")
    public void sinc(String dir) {
        requestMusicaGeral.sinc(dir);
    }
    
    @Get
    @NaoDeslogar
    @Path("/musica/stream/{id}")
    public InputStreamDownload stream(Integer id) {
        try { 
            String caminho = requestMusicaGeral.bean(id).getArquivo();
            SmbFile smbDir = new SmbFile(caminho, Utilities.getAuthSmb());
            SmbFileInputStream fileInputStream = new SmbFileInputStream(smbDir);
            
            if(smbDir.exists()) {
                return new InputStreamDownload(fileInputStream, smbDir.getContentType(), smbDir.getName());
            } else {
                return new InputStreamDownload( new ByteArrayInputStream("Arquivo não encontrado".getBytes()), "", "text.txt");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new InputStreamDownload( new ByteArrayInputStream("Arquivo não encontrado".getBytes()), "", "text.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return new InputStreamDownload( new ByteArrayInputStream("Arquivo não encontrado".getBytes()), "", "text.txt");
        }
    }
}