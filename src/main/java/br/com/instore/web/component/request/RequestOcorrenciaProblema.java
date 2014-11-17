package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaProblemaDTO;
import br.com.instore.web.dto.OcorrenciaProblemaJSON;
import br.com.instore.web.tools.AjaxResult;
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
    
    public void beanList(Integer page, Integer rows, Integer id, String descricao) {
        OcorrenciaProblemaJSON json = new OcorrenciaProblemaJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<OcorrenciaProblemaBean> lista = new ArrayList<OcorrenciaProblemaBean>();

        Query q1 = repository.query(OcorrenciaProblemaBean.class);
        Query q2 = repository.query(OcorrenciaProblemaBean.class);

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
        
        json.setCount(q1.count().intValue());
        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1); 
        lista = q2.limit(offset, rows).findAll();

        
        json.setPage(page);
        json.setSize(size);

        List<OcorrenciaProblemaDTO> rowsList = new ArrayList<OcorrenciaProblemaDTO>();
        for (OcorrenciaProblemaBean bean : lista) {
            
            OcorrenciaProblemaDTO dto = new OcorrenciaProblemaDTO();
            dto.setId(bean.getId().toString());
            dto.setDescricao(bean.getDescricao());

            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
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
