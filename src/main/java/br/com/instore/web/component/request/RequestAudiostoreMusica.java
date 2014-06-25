package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreMusicaDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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
            dto.setAfinidade1(bean.getAfinidade1());
            dto.setAfinidade2(bean.getAfinidade2());
            dto.setAfinidade3(bean.getAfinidade3());
            dto.setAfinidade4(bean.getAfinidade4());
            dto.setAnoGravacao(bean.getAnoGravacao().toString());
            dto.setArquivo(bean.getArquivo());
            dto.setCategoria1(bean.getCategoria1().getCategoria());
            dto.setCategoria2(bean.getCategoria2().getCategoria());
            dto.setCategoria3(bean.getCategoria3().getCategoria());
            dto.setCliente(bean.getCliente().getNome());
            dto.setCrossover(bean.getCrossover() ? "Sim" : "Não");
            dto.setCut(bean.getCut() ? "Sim" : "Não");
            dto.setData( new SimpleDateFormat("dd/MM/yyyy").format(bean.getData()));
            dto.setDataVencimento( new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataVencimento()));
            dto.setDataVencimentoCrossover(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataVencimentoCrossover()));
            dto.setDiaExecucao1(bean.getDiaExecucao1().toString());
            dto.setDiaExecucao2(bean.getDiaExecucao2().toString());
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
            dto.setTipoInterprete(""+bean.getTipoInterprete());
            dto.setTitulo(bean.getTitulo());
            dto.setUltimaExecucao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucao()));
            dto.setUltimaExecucaoDia(new SimpleDateFormat("dd/MM/yyyy").format(bean.getUltimaExecucaoDia()));
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

    public void salvar(AudiostoreMusicaBean bean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(bean != null && bean.getId()!= null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
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
}
