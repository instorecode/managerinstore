package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreGravadoraDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestAudiostoreGravadora implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestAudiostoreGravadora() {
    }

    public RequestAudiostoreGravadora(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    
    public List<AudiostoreGravadoraDTO> beanList() {
        List<AudiostoreGravadoraBean> lista = new ArrayList<AudiostoreGravadoraBean>();
        List<AudiostoreGravadoraDTO> lista2 = new ArrayList<AudiostoreGravadoraDTO>();
        lista = repository.query(AudiostoreGravadoraBean.class).findAll();
        for (AudiostoreGravadoraBean bean : lista) {
            AudiostoreGravadoraDTO dto = new AudiostoreGravadoraDTO( Utilities.leftPad(bean.getId()) , bean.getNome() );            
            lista2.add(dto);
        }
        return lista2;
    }

    public AudiostoreGravadoraBean bean(Integer id) {
        return repository.find(AudiostoreGravadoraBean.class, id);
    }

    public void salvar(AudiostoreGravadoraBean bean) {
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
            
            AudiostoreGravadoraBean bean = repository.marge((AudiostoreGravadoraBean) repository.find(AudiostoreGravadoraBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Voz removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a voz!")).recursive().serialize();
        }
    }
}
