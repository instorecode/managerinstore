package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.OcorrenciaBean;
import br.com.instore.core.orm.bean.OcorrenciaBean;
import br.com.instore.core.orm.bean.OcorrenciaOrigemBean;
import br.com.instore.core.orm.bean.OcorrenciaPrioridadeBean;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.core.orm.bean.OcorrenciaSolucaoBean;
import br.com.instore.core.orm.bean.OcorrenciaUsuarioBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestOcorrencia implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestOcorrencia() {
    }

    public RequestOcorrencia(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public List<OcorrenciaDTO> beanList() {
        List<OcorrenciaBean> lista = new ArrayList<OcorrenciaBean>();
        List<OcorrenciaDTO> lista2 = new ArrayList<OcorrenciaDTO>();
        lista = repository.query(OcorrenciaBean.class).findAll();
        for (OcorrenciaBean bean : lista) {
            OcorrenciaDTO dto = new OcorrenciaDTO();
            
            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            dto.setCliente(bean.getCliente().getNome());
            dto.setDataCadastro( new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataCadastro()));
            dto.setDataResolucao(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataResolucao()));
            dto.setOcorrenciaPrioridade(bean.getOcorrenciaPrioridade().getDescricao());
            dto.setUsuarioCriacao(bean.getUsuarioCriacao().getNome());
            dto.setOcorrenciaOrigem(bean.getOcorrenciaOrigem().getDescricao());
            
            if(null!=bean.getOcorrenciaProblema()&& bean.getOcorrenciaProblema() > 0) {
                OcorrenciaProblemaBean ob = repository.find(OcorrenciaProblemaBean.class, bean.getOcorrenciaProblema());
                dto.setOcorrenciaProblema(ob.getDescricao());
            }
            
            if(null!=bean.getOcorrenciaSolucao()&& bean.getOcorrenciaSolucao() > 0) {
                OcorrenciaSolucaoBean ob = repository.find(OcorrenciaSolucaoBean.class, bean.getOcorrenciaSolucao());
                dto.setOcorrenciaSolucao(ob.getDescricao());
            }
                        
            lista2.add(dto);
        }
        return lista2;
    }

    public OcorrenciaBean bean(Integer id) {
        return repository.find(OcorrenciaBean.class, id);
    }
    
    public List<ClienteBean> clienteList() {
        return repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
    }
    
    public List<OcorrenciaOrigemBean> ocorrenciaOrigemList() {
        return repository.query(OcorrenciaOrigemBean.class).findAll();
    }
    
    public List<OcorrenciaPrioridadeBean> ocorrenciaPrioridadeList() {
        return repository.query(OcorrenciaPrioridadeBean.class).findAll();
    }
    
    public List<OcorrenciaProblemaBean> ocorrenciaProblemaList() {
        return repository.query(OcorrenciaProblemaBean.class).findAll();
    }
    
    public List<OcorrenciaSolucaoBean> ocorrenciaSolucaoList() {
        return repository.query(OcorrenciaSolucaoBean.class).findAll();
    }
    
    public List<OcorrenciaUsuarioBean> ocorrenciaUsuarioList() {
        return repository.query(OcorrenciaUsuarioBean.class).findAll();
    }
    
    public List<UsuarioBean> usuarioList() {
        return repository.query(UsuarioBean.class).findAll();
    }

    public void salvar(OcorrenciaBean bean, Integer [] usuarioList) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(null==bean.getOcorrenciaProblema()) {
                bean.setOcorrenciaProblema(0);
            }
            if(null==bean.getOcorrenciaSolucao()) {
                bean.setOcorrenciaSolucao(0);
            }
            
            if(bean != null && bean.getId()!= null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                bean.setDataCadastro(new Date());
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
            
            OcorrenciaBean bean = repository.marge((OcorrenciaBean) repository.find(OcorrenciaBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Orrencia removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a ocorrencia!")).recursive().serialize();
        }
    }
}
