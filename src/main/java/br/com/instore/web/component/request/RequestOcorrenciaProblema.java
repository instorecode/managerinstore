package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaProblemaDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestOcorrenciaProblema implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestOcorrenciaProblema() {
    }

    public RequestOcorrenciaProblema(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public List<OcorrenciaProblemaDTO> beanList() {
        List<OcorrenciaProblemaBean> lista = new ArrayList<OcorrenciaProblemaBean>();
        List<OcorrenciaProblemaDTO> lista2 = new ArrayList<OcorrenciaProblemaDTO>();
        lista = repository.query(OcorrenciaProblemaBean.class).findAll();
        for (OcorrenciaProblemaBean bean : lista) {
            OcorrenciaProblemaDTO dto = new OcorrenciaProblemaDTO();
            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            
            lista2.add(dto);
        }
        return lista2;
    }

    public OcorrenciaProblemaBean bean(Integer id) {
        return repository.find(OcorrenciaProblemaBean.class, id);
    }

    public void salvar(OcorrenciaProblemaBean bean) {
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
            
            OcorrenciaProblemaBean bean = repository.marge((OcorrenciaProblemaBean) repository.find(OcorrenciaProblemaBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Problema da ocorrencia removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a problema da ocorrencia!")).recursive().serialize();
        }
    }
}
