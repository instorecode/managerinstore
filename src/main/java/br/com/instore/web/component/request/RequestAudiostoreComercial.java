package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreComercialShBean;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.ArquivoMusicaDTO;
import br.com.instore.web.dto.AudiostoreComercialDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.commons.compress.utils.IOUtils;

@RequestScoped
public class RequestAudiostoreComercial implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestAudiostoreComercial() {
    }

    public RequestAudiostoreComercial(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    
    public List<AudiostoreComercialDTO> beanList() {
        List<AudiostoreComercialBean> lista = new ArrayList<AudiostoreComercialBean>();
        List<AudiostoreComercialDTO> lista2 = new ArrayList<AudiostoreComercialDTO>();
        lista = repository.query(AudiostoreComercialBean.class).findAll();
        for (AudiostoreComercialBean bean : lista) {
            AudiostoreComercialDTO dto = new AudiostoreComercialDTO();            
            
            dto.setId( Utilities.leftPad( bean.getId() ));
            dto.setArquivo(bean.getArquivo());
            if(null!=bean.getAudiostoreCategoria()) {
                dto.setCategoriaNome(bean.getAudiostoreCategoria().getCategoria());
            }
            dto.setData( new SimpleDateFormat("dd/MM/yyyy").format(bean.getData()));
            dto.setDataVencimento( new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataVencimento()));
            dto.setDiasSemana(""+bean.getDiasSemana());
            dto.setDiasAlternados(bean.getDiasAlternados() ? "Sim" : "Não");
            dto.setFrameFinal(bean.getFrameFinal().toString());
            dto.setFrameInicial(bean.getFrameInicio().toString());
            dto.setMsg(bean.getMsg());
            dto.setQtd(bean.getQtde().toString());
            dto.setRandom(bean.getRandom().toString());
            dto.setSemSom(bean.getSemSom() ? "Sim" : "Não");
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
            dto.setDependencia1(bean.getDependencia1());
            dto.setDependencia2(bean.getDependencia2());
            dto.setDependencia3(bean.getDependencia3());
            
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
    
    public List<AudiostoreComercialShBean> audiostoreComercialShBeanList() {
        List<AudiostoreComercialShBean> audiostoreComercialShBeanList = repository.query(AudiostoreComercialShBean.class).findAll();
        return audiostoreComercialShBeanList;
    }

    public AudiostoreComercialBean bean(Integer id) {
        return repository.find(AudiostoreComercialBean.class, id);
    }

    public void salvar(AudiostoreComercialBean bean , String tempoTotalString , AudiostoreComercialShBean sh) {
        try {
            
            Date tempoTotal = new SimpleDateFormat("HH:mm:ss").parse(tempoTotalString);
            bean.setTempoTotal(tempoTotal);
            
            
            
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(bean != null && bean.getId()!= null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }
            ConfigAppBean cfg = repository.find(ConfigAppBean.class, 1);
            
            String origem = cfg.getAudiostoreMusicaDirOrigem()   +"\\"+ bean.getArquivo();
            String destino = cfg.getAudiostoreMusicaDirDestino() +"\\"+ bean.getArquivo();
            
            File dir = new File(destino);
            if(!dir.exists()) {
                dir.mkdirs();
            }
            
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
            
            AudiostoreComercialBean bean = repository.marge((AudiostoreComercialBean) repository.find(AudiostoreComercialBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Comercial removido com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a comercial!")).recursive().serialize();
        }
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