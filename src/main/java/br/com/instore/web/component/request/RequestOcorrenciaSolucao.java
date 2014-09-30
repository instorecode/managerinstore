package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.core.orm.bean.OcorrenciaProblemaSolucaoBean;
import br.com.instore.core.orm.bean.OcorrenciaSolucaoBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaSolucaoDTO;
import br.com.instore.web.dto.OcorrenciaSolucaoJSON;
import br.com.instore.web.tools.AjaxResult;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestOcorrenciaSolucao implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestOcorrenciaSolucao() {
    }

    public RequestOcorrenciaSolucao(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public List<OcorrenciaProblemaBean> problemaList() {
        return repository.query(OcorrenciaProblemaBean.class).findAll();
    }

    public List<OcorrenciaProblemaBean> problemaSolucaoList() {
        return repository.query(OcorrenciaProblemaSolucaoBean.class).findAll();
    }

    public void beanList(Integer page, Integer rows, Integer id, String descricao, String prazo) {
        OcorrenciaSolucaoJSON json = new OcorrenciaSolucaoJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<OcorrenciaSolucaoBean> lista = new ArrayList<OcorrenciaSolucaoBean>();

        Query q1 = repository.query(OcorrenciaSolucaoBean.class);
        Query q2 = repository.query(OcorrenciaSolucaoBean.class);

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

        if (null != prazo && !prazo.isEmpty()) {
            Date d;
            try {
                d = new SimpleDateFormat("HH:mm:ss").parse(prazo);
                q1.eq("nivel", d);
                q2.eq("nivel", d);
                json.setPrazo(prazo);
            } catch (ParseException ex) {
                Logger.getLogger(RequestOcorrenciaSolucao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();


        json.setPage(page);
        json.setSize(size);

        List<OcorrenciaSolucaoDTO> rowsList = new ArrayList<OcorrenciaSolucaoDTO>();
        for (OcorrenciaSolucaoBean bean : lista) {

            OcorrenciaSolucaoDTO dto = new OcorrenciaSolucaoDTO();
            dto.setId(bean.getId().toString());
            dto.setDescricao(bean.getDescricao());
            dto.setPrazo(new SimpleDateFormat("HH:mm:ss").format(bean.getPrazoPesolucao()));

            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public OcorrenciaSolucaoDTO bean(Integer id) {
        OcorrenciaSolucaoBean bean = repository.find(OcorrenciaSolucaoBean.class, id);
        
        OcorrenciaSolucaoDTO dto = new OcorrenciaSolucaoDTO();
        dto.setId(bean.getId().toString());
        dto.setDescricao(bean.getDescricao());
        dto.setPrazo(new SimpleDateFormat("HH:mm:ss").format(bean.getPrazoPesolucao()));

        return dto;
    }

    public void salvar(OcorrenciaSolucaoBean bean, Integer[] problemaList, String prazo) {
        try {
            bean.setPrazoPesolucao(new SimpleDateFormat("HH:mm:ss").parse(prazo));
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (bean != null && bean.getId() != null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }

            repository.query("delete from ocorrencia_problema_solucao where ocorrencia_solucao = " + bean.getId()).executeSQLCommand();

            if (null != problemaList && problemaList.length > 0) {

                for (Integer id : problemaList) {
                    OcorrenciaProblemaSolucaoBean item = new OcorrenciaProblemaSolucaoBean();
                    item.setOcorrenciaProblema(new OcorrenciaProblemaBean(id));
                    item.setOcorrenciaSolucao(bean);
                    repository.save(item);
                }
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
            
            List<OcorrenciaProblemaSolucaoBean> list = repository.query(OcorrenciaProblemaSolucaoBean.class).eq("ocorrenciaSolucao.id", id).findAll();
            for (OcorrenciaProblemaSolucaoBean bean : list) {
                repository.delete(bean);
            }

            OcorrenciaSolucaoBean bean = repository.marge((OcorrenciaSolucaoBean) repository.find(OcorrenciaSolucaoBean.class, id));
            repository.delete(bean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Problema da ocorrencia removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a problema da ocorrencia!")).recursive().serialize();
        }
    }
    
    public void sp(Integer id) {
        result.use(Results.json()).withoutRoot().from(repository.query(OcorrenciaProblemaSolucaoBean.class).eq("ocorrenciaSolucao.id", id).findAll()).recursive().serialize();
    }
}
