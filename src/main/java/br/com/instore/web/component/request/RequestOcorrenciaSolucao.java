package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.core.orm.bean.OcorrenciaProblemaSolucaoBean;
import br.com.instore.core.orm.bean.OcorrenciaSolucaoBean;
import br.com.instore.core.orm.bean.OcorrenciaSolucaoBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaSolucaoDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
    public List<OcorrenciaSolucaoDTO> beanList() {
        List<OcorrenciaSolucaoBean> lista = new ArrayList<OcorrenciaSolucaoBean>();
        List<OcorrenciaSolucaoDTO> lista2 = new ArrayList<OcorrenciaSolucaoDTO>();
        lista = repository.query(OcorrenciaSolucaoBean.class).findAll();
        for (OcorrenciaSolucaoBean bean : lista) {
            OcorrenciaSolucaoDTO dto = new OcorrenciaSolucaoDTO();
            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            dto.setPrazoResolucao( new SimpleDateFormat("HH:mm:ss").format(bean.getPrazoPesolucao()));
            
            lista2.add(dto);
        }
        return lista2;
    }

    public OcorrenciaSolucaoBean bean(Integer id) {
        return repository.find(OcorrenciaSolucaoBean.class, id);
    }

    public void salvar(OcorrenciaSolucaoBean bean , Integer [] problemaList , String prazo) {
        try {
            bean.setPrazoPesolucao(new SimpleDateFormat("HH:mm:ss").parse(prazo));
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(bean != null && bean.getId()!= null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }
            
            repository.query("delete from ocorrencia_problema_solucao where ocorrencia_solucao = "+bean.getId()).executeSQLCommand();
            
            if(null != problemaList && problemaList.length > 0) {
                
                for (Integer id : problemaList) {
                    OcorrenciaProblemaSolucaoBean item = new OcorrenciaProblemaSolucaoBean();
                    item.setOcorrenciaProblema( new OcorrenciaProblemaBean(id));
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
            
            OcorrenciaSolucaoBean bean = repository.marge((OcorrenciaSolucaoBean) repository.find(OcorrenciaSolucaoBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Problema da ocorrencia removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a problema da ocorrencia!")).recursive().serialize();
        }
    }
}
