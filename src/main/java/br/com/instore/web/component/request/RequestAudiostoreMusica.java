package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.ArquivoMusicaDTO;
import br.com.instore.web.dto.AudiostoreMusicaDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;

@RequestScoped
public class RequestAudiostoreMusica implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestAudiostoreMusica() {
    }

    public RequestAudiostoreMusica(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    
    public List<AudiostoreMusicaDTO> beanList() {
        List<AudiostoreMusicaBean> lista = new ArrayList<AudiostoreMusicaBean>();
        List<AudiostoreMusicaDTO> lista2 = new ArrayList<AudiostoreMusicaDTO>();
        lista = repository.query(AudiostoreMusicaBean.class).findAll();
        for (AudiostoreMusicaBean bean : lista) {
            AudiostoreMusicaDTO dto = new AudiostoreMusicaDTO();            
            
            dto.setId( Utilities.leftPad( bean.getId() ));
            dto.setGravadora( bean.getGravadora().getNome() );
            dto.setAfinidade1(bean.getAfinidade1());
            dto.setAfinidade2(bean.getAfinidade2());
            dto.setAfinidade3(bean.getAfinidade3());
            dto.setAfinidade4(bean.getAfinidade4());
            dto.setAnoGravacao(bean.getAnoGravacao().toString());
            dto.setArquivo(bean.getArquivo());
            if(null!=bean.getCategoria1()) {
                dto.setCategoria1(bean.getCategoria1().getCategoria());
            }
            if(null!=bean.getCategoria2()) {
                dto.setCategoria2(bean.getCategoria2().getCategoria());
            }
            if(null!=bean.getCategoria3()) {
                dto.setCategoria3(bean.getCategoria3().getCategoria());
            }
            dto.setCrossover(bean.getCrossover() ? "Sim" : "Não");
            dto.setCut(bean.getCut() ? "Cut" : "FadeOut");
            dto.setData( new SimpleDateFormat("dd/MM/yyyy").format(bean.getData()));
            dto.setDataVencimento( new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataVencimento()));
            dto.setDataVencimentoCrossover(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataVencimentoCrossover()));
            dto.setDiasExecucao1(bean.getDiasExecucao1().toString());
            dto.setDiasExecucao2(bean.getDiasExecucao2().toString());
            dto.setFrameFinal(bean.getFrameFinal().toString());
            dto.setFrameInicio(bean.getFrameInicio().toString());
            dto.setInterprete(bean.getInterprete());
            dto.setMsg(bean.getMsg());
            dto.setQtde(bean.getQtde().toString());
            dto.setQtdePlayer(bean.getQtdePlayer().toString());
            dto.setRandom(bean.getRandom().toString());
            dto.setSemSom(bean.getSemSom() ? "Sim" : "Não");
            dto.setSuperCrossover(bean.getSuperCrossover() ? "sim" : "Não");
            dto.setTempoTotal( new SimpleDateFormat("HH:mm:ss").format(bean.getTempoTotal()));
            
            switch(bean.getTipoInterprete()) {
                case 1: dto.setTipoInterprete("Masculino"); break;
                case 2: dto.setTipoInterprete("Feminino"); break;
                case 3: dto.setTipoInterprete("Grupo"); break;
                case 4: dto.setTipoInterprete("Instrumental"); break;
                case 5: dto.setTipoInterprete("Jingle"); break;
            }
            
            
            
            dto.setTitulo(bean.getTitulo());
            dto.setUltimaExecucao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucao()));
            dto.setUltimaExecucaoData(new SimpleDateFormat("dd/MM/yyyy").format(bean.getUltimaExecucaoData()));
            dto.setVelocidade(""+bean.getVelocidade());
            
            lista2.add(dto);
        }
        return lista2;
    }
    
    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).findAll();
        return clienteBeanList;
    }
    
    public List<AudiostoreCategoriaBean> categoriaBeanList() {
        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).findAll();
        return audiostoreCategoriaBeanList;
    }
    
    public List<AudiostoreGravadoraBean> gravadoraBeanList() {
        List<AudiostoreGravadoraBean> audiostoreCategoriaBeanList = repository.query(AudiostoreGravadoraBean.class).findAll();
        return audiostoreCategoriaBeanList;
    }

    public AudiostoreMusicaBean bean(Integer id) {
        return repository.find(AudiostoreMusicaBean.class, id);
    }

    public void salvar(AudiostoreMusicaBean bean , String tempoTotalString) {
        try {
            
            Date tempoTotal = new SimpleDateFormat("HH:mm:ss").parse(tempoTotalString);
            bean.setTempoTotal(tempoTotal);
            
            bean.setSemSom(Boolean.FALSE);
            bean.setMsg("");
            bean.setUltimaExecucaoData(bean.getUltimaExecucao());
            bean.setQtde(bean.getQtdePlayer());
            bean.setSuperCrossover(Boolean.FALSE);
            bean.setRandom(new Random().nextInt());
            
            if( !(null != bean.getAfinidade1() && !bean.getAfinidade1().isEmpty()) ) {
                bean.setAfinidade1("");
            }
            
            if( !(null != bean.getAfinidade2() && !bean.getAfinidade2().isEmpty()) ) {
                bean.setAfinidade2("");
            }
            
            if( !(null != bean.getAfinidade3() && !bean.getAfinidade3().isEmpty()) ) {
                bean.setAfinidade3("");
            }
            
            if( !(null != bean.getAfinidade4() && !bean.getAfinidade4().isEmpty()) ) {
                bean.setAfinidade4("");
            }
            
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(bean != null && bean.getId()!= null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }
            ConfigAppBean cfg = repository.find(ConfigAppBean.class, 1);
            
            String origem = cfg.getAudiostoreMusicaDirOrigem()   +"\\"+ bean.getArquivo();
            String destino = cfg.getAudiostoreMusicaDirDestino() +"\\"+ bean.getArquivo();
            
            File f = new File(destino);
            if(f.exists()) {
                f.delete();
            }
            
            FileInputStream fis = new FileInputStream( new File(origem));
            FileOutputStream fos = new FileOutputStream( new File(destino));
            IOUtils.copy(fis, fos);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }
    
    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            AudiostoreMusicaBean bean = repository.marge((AudiostoreMusicaBean) repository.find(AudiostoreMusicaBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Voz removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a voz!")).recursive().serialize();
        }
    }
    
    public InputStreamDownload download(Integer id) {
        InputStreamDownload inputStreamDownload = null;
        try {
            AudiostoreMusicaBean bean = bean(id);
            if (bean != null) {

                String conteudo = "";
                conteudo += "";
                conteudo += StringUtils.leftPad(bean.getArquivo(), 30, " ");
                conteudo += StringUtils.leftPad(bean.getInterprete(), 30, " ");
                conteudo += StringUtils.leftPad(bean.getTitulo(), 30, " ");
                conteudo += StringUtils.leftPad(bean.getTitulo(), 30, " ");
                
                
                inputStreamDownload = new InputStreamDownload(new ByteArrayInputStream(conteudo.getBytes()), "application/exp", bean.getTitulo()+ "+.exp");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStreamDownload;
    }
    
    public List<ArquivoMusicaDTO> arquivoMusicaList() {
        ConfigAppBean cfg = repository.find(ConfigAppBean.class, 1);
        
        File dir = new File(cfg.getAudiostoreMusicaDirOrigem());
        List<ArquivoMusicaDTO> lista = new ArrayList<ArquivoMusicaDTO>();
        if(dir.exists()) {
            for(File file : dir.listFiles()) {
                if(file.getName().endsWith(".mp3")) {
                    ArquivoMusicaDTO dto = new ArquivoMusicaDTO(file.getName().replaceAll("([.]{1})([.,a-z,A-Z,0-9,_,-]+)", "") , file.getName());
                    lista.add(dto);
                }
            }
        }
        return lista;
    }
}
