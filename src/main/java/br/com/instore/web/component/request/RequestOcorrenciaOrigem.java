package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaOrigemBean;
import br.com.instore.core.orm.bean.OcorrenciaOrigemBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaOrigemDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestOcorrenciaOrigem implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestOcorrenciaOrigem() {
    }

    public RequestOcorrenciaOrigem(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public List<OcorrenciaOrigemDTO> beanList() {
        List<OcorrenciaOrigemBean> lista = new ArrayList<OcorrenciaOrigemBean>();
        List<OcorrenciaOrigemDTO> lista2 = new ArrayList<OcorrenciaOrigemDTO>();
        lista = repository.query(OcorrenciaOrigemBean.class).findAll();
        for (OcorrenciaOrigemBean bean : lista) {
            OcorrenciaOrigemDTO dto = new OcorrenciaOrigemDTO();
            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            
            lista2.add(dto);
        }
        return lista2;
    }

    public OcorrenciaOrigemBean bean(Integer id) {
        return repository.find(OcorrenciaOrigemBean.class, id);
    }

    public void salvar(OcorrenciaOrigemBean bean) {
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
            
            OcorrenciaOrigemBean bean = repository.marge((OcorrenciaOrigemBean) repository.find(OcorrenciaOrigemBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Origem da ocorrencia removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a origem da ocorrencia!")).recursive().serialize();
        }
    }
}
