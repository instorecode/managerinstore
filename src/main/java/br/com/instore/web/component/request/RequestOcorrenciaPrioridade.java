package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaPrioridadeBean;
import br.com.instore.core.orm.bean.OcorrenciaPrioridadeBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaPrioridadeDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestOcorrenciaPrioridade implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestOcorrenciaPrioridade() {
    }

    public RequestOcorrenciaPrioridade(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public List<OcorrenciaPrioridadeDTO> beanList() {
        List<OcorrenciaPrioridadeBean> lista = new ArrayList<OcorrenciaPrioridadeBean>();
        List<OcorrenciaPrioridadeDTO> lista2 = new ArrayList<OcorrenciaPrioridadeDTO>();
        lista = repository.query(OcorrenciaPrioridadeBean.class).findAll();
        for (OcorrenciaPrioridadeBean bean : lista) {
            OcorrenciaPrioridadeDTO dto = new OcorrenciaPrioridadeDTO();
            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            dto.setNivel(bean.getNivel());
            
            lista2.add(dto);
        }
        return lista2;
    }

    public OcorrenciaPrioridadeBean bean(Integer id) {
        return repository.find(OcorrenciaPrioridadeBean.class, id);
    }

    public void salvar(OcorrenciaPrioridadeBean bean) {
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
            
            OcorrenciaPrioridadeBean bean = repository.marge((OcorrenciaPrioridadeBean) repository.find(OcorrenciaPrioridadeBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Prioridade da ocorrencia removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a prioridade da ocorrencia!")).recursive().serialize();
        }
    }
}
