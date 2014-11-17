package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.OcorrenciaOrigemBean;
import br.com.instore.core.orm.bean.OcorrenciaStatusBean;
import br.com.instore.core.orm.bean.OcorrenciaStatusBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaOrigemDTO;
import br.com.instore.web.dto.OcorrenciaOrigemJSON;
import br.com.instore.web.dto.OcorrenciaSituacaoJSON;
import br.com.instore.web.dto.OcorrenciaStatusDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestOcorrenciaStatus implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestOcorrenciaStatus() {
    }

    public RequestOcorrenciaStatus(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public void beanList(Integer page, Integer rows, Integer id, String descricao, String cor) {
        OcorrenciaSituacaoJSON json = new OcorrenciaSituacaoJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<OcorrenciaStatusBean> lista = new ArrayList<OcorrenciaStatusBean>();

        Query q1 = repository.query(OcorrenciaStatusBean.class);
        Query q2 = repository.query(OcorrenciaStatusBean.class);

        if (null != id && id > 0) {
            q1.eq("id", id);
            q2.eq("id", id);
            json.setId(id.toString());
        }

        if (null != descricao && !descricao.isEmpty()) {
            q1.ilikeAnyWhere("descricao", descricao);
            q2.ilikeAnyWhere("descricao", descricao);
            json.setDescricao(descricao);
        }

        if (null != descricao && !descricao.isEmpty()) {
            q1.ilikeAnyWhere("cor", cor);
            q2.ilikeAnyWhere("cor", cor);
            json.setCor(cor);
        }
        
        json.setCount(q1.count().intValue());
        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1); 
        lista = q2.limit(offset, rows).findAll();

        
        json.setPage(page);
        json.setSize(size);

        List<OcorrenciaStatusDTO> rowsList = new ArrayList<OcorrenciaStatusDTO>();
        for (OcorrenciaStatusBean bean : lista) {
            
            OcorrenciaStatusDTO dto = new OcorrenciaStatusDTO();
            dto.setId(bean.getId().toString());
            dto.setDescricao(bean.getDescricao());
            
            if("#4D90FD".equals(bean.getCor())) {
                dto.setCor("Azul");
            } else if("#54A754".equals(bean.getCor())) {
                dto.setCor("Verde");
            } else if("#ffa800".equals(bean.getCor())) {
                dto.setCor("Amarelo");
            } else if("#e64d35".equals(bean.getCor())) {
                dto.setCor("Vermelho");
            } else if("#000000".equals(bean.getCor())) {
                dto.setCor("Preto");
            }
            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public OcorrenciaStatusBean bean(Integer id) {
        return repository.find(OcorrenciaStatusBean.class, id);
    }

    public void salvar(OcorrenciaStatusBean bean) {
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
            
            OcorrenciaStatusBean bean = repository.marge((OcorrenciaStatusBean) repository.find(OcorrenciaStatusBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Status da ocorrencia removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a status da ocorrencia!")).recursive().serialize();
        }
    }
}
