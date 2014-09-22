package br.com.instore.web.component.request;

import br.com.instore.web.dto.OcorrenciaOrigemJSON;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.OcorrenciaOrigemBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaOrigemDTO;
import br.com.instore.web.tools.AjaxResult;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.swing.text.Utilities;

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

    public void beanList(Integer page, Integer rows, Integer id, String descricao) {
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<OcorrenciaOrigemBean> lista = new ArrayList<OcorrenciaOrigemBean>();

        Query q1 = repository.query(OcorrenciaOrigemBean.class);
        Query q2 = repository.query(OcorrenciaOrigemBean.class);

        if (null != id && id > 0) {
            q2.eq("id", id);
        }

        if (null != descricao && !descricao.isEmpty()) {
            q2.ilikeAnyWhere("descricao", descricao);
        }

        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1); 
        lista = q2.limit(offset, rows).findAll();

        OcorrenciaOrigemJSON json = new OcorrenciaOrigemJSON();
        json.setPage(page);
        json.setSize(size);

        List<OcorrenciaOrigemDTO> rowsList = new ArrayList<OcorrenciaOrigemDTO>();
        for (OcorrenciaOrigemBean bean : lista) {
            
            OcorrenciaOrigemDTO dto = new OcorrenciaOrigemDTO();
            dto.setId(bean.getId().toString());
            dto.setDescricao(bean.getDescricao());

            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public OcorrenciaOrigemBean bean(Integer id) {
        return repository.find(OcorrenciaOrigemBean.class, id);
    }

    public void salvar(OcorrenciaOrigemBean bean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (bean != null && bean.getId() != null && bean.getId() > 0) {
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
