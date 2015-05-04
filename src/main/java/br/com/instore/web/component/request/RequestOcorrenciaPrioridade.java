package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.OcorrenciaPrioridadeBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaPrioridadeDTO;
import br.com.instore.web.dto.OcorrenciaPrioridadeJSON;
import br.com.instore.web.tools.AjaxResult;
import java.util.ArrayList;
import java.util.List;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class RequestOcorrenciaPrioridade implements java.io.Serializable {

    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;

    public RequestOcorrenciaPrioridade(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public void beanList(Integer page, Integer rows, Integer id, String descricao , Integer nivel) {
        OcorrenciaPrioridadeJSON json = new OcorrenciaPrioridadeJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<OcorrenciaPrioridadeBean> lista = new ArrayList<OcorrenciaPrioridadeBean>();

        Query q1 = repository.query(OcorrenciaPrioridadeBean.class);
        Query q2 = repository.query(OcorrenciaPrioridadeBean.class);

        if (null != id && id > 0) {
            q1.eq("id", id);
            q2.eq("id", id);
            json.setId(id);
        }

        if (null != descricao && !descricao.isEmpty()) {
            q1.ilikeAnyWhere("descricao", descricao);
            q2.ilikeAnyWhere("descricao", descricao);
            json.setDescricao(descricao);
        }

        if (null != nivel) {
            q1.eq("nivel", nivel);
            q2.eq("nivel", nivel);
            json.setNivel(nivel);
        }
        
        json.setCount(q1.count().intValue());
        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1); 
        lista = q2.limit(offset, rows).findAll();

        
        json.setPage(page);
        json.setSize(size);

        List<OcorrenciaPrioridadeDTO> rowsList = new ArrayList<OcorrenciaPrioridadeDTO>();
        for (OcorrenciaPrioridadeBean bean : lista) {
            
            OcorrenciaPrioridadeDTO dto = new OcorrenciaPrioridadeDTO();
            dto.setId(bean.getId().toString());
            dto.setDescricao(bean.getDescricao());
            dto.setNivel(bean.getNivel());

            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
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
