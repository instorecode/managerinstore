package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.MusicaGeralBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAudiostoreMusica;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.dto.UltimpDTO;
import br.com.instore.web.tools.Utilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

@Controller
public class AudiostoreMusicaController implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private RequestAudiostoreMusica requestAudiostoreMusica;
    @Inject
    private SessionRepository repository;

    public AudiostoreMusicaController() {
    }

    public AudiostoreMusicaController(Result result, RequestAudiostoreMusica requestAudiostoreMusica, SessionRepository repository) {
        this.result = result;
        this.requestAudiostoreMusica = requestAudiostoreMusica;
        this.repository = repository;
    }

    @Get
    @Restrict
    @Path("/musica/programacao-audiostore")
    public void listar(Integer idmusicaGeral, Boolean clientes, Boolean categorias, Boolean ultimp , Boolean datajson, Boolean view, Integer page, Integer rows, Integer id, Integer idcliente,  String arquivo,  String nome, Integer codigo, String letra , String bool , Integer pk) {
        if (null != datajson && datajson) {
            requestAudiostoreMusica.beanList(datajson, view, page, rows, id, idcliente, arquivo , nome, codigo, letra , bool);
        }
        
        if (null != view && view) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreMusica.bean2(pk)).recursive().serialize();
        }
        
        if (null != ultimp && ultimp) {
            List<UltimpDTO> lista = new ArrayList<UltimpDTO>();
            UltimpDTO sim = new UltimpDTO();
            UltimpDTO nao = new UltimpDTO();
            
            sim.setBool(true);
            sim.setLabel("sim");
            
            nao.setBool(true);
            nao.setLabel("nao");
            
            lista.add(sim);
            lista.add(nao);
            result.use(Results.json()).withoutRoot().from(lista).recursive().serialize();
        }

        if (null != clientes && clientes) {
            result.use(Results.json()).withoutRoot().from(requestAudiostoreMusica.clienteBeanList()).recursive().serialize();
        }

        if (null != categorias && categorias) {
            if (null != idcliente && idcliente > 0) {
                result.use(Results.json()).withoutRoot().from(requestAudiostoreMusica.categoriasByCliente5(idcliente)).recursive().serialize();
            } else {
                result.use(Results.json()).withoutRoot().from(requestAudiostoreMusica.categorias()).recursive().serialize();
            }
            
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
        requestAudiostoreMusica.salvar(audiostoreMusicaBeanList, true);
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
        requestAudiostoreMusica.salvar(audiostoreMusicaBeanList , false);
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
    @Path("/musica/programacao-audiostore/logs/{idcliente}")
    public void logs(Integer idcliente) {
        if (null != idcliente && idcliente > 0) {
            ClienteBean cliente = repository.find(ClienteBean.class, idcliente);
            if (null != cliente) {
                try {
                    DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", cliente.getIdcliente()).findOne();
                    SmbFile file = new SmbFile(dados.getLocalDestinoMusica()+"/musica_files_exists.log" , Utilities.getAuthSmbDefault());
                    if (file.exists()) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
                        List<String> lines = new ArrayList<String>();
                        String line;
                        
                        while(null != (line = br.readLine())) {
                            lines.add(line);
                        } 
                        result.include("lines_file_exists", lines);
                    }
                    
                    SmbFile file2 = new SmbFile(dados.getLocalDestinoMusica()+"/musica_files_not_exists.log" , Utilities.getAuthSmbDefault());
                    if (file2.exists()) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(file2.getInputStream()));
                        List<String> lines = new ArrayList<String>();
                        String line;
                        
                        while(null != (line = br.readLine())) {
                            lines.add(line);
                        } 
                        result.include("lines_file_not_exists", lines);
                    }
                    
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (SmbException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
        }
    }
}
