package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.OcorrenciaStatusBean;
import br.com.instore.core.orm.bean.OcorrenciaStatusBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
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
    
    public List<OcorrenciaStatusDTO> beanList() {
        List<OcorrenciaStatusBean> lista = new ArrayList<OcorrenciaStatusBean>();
        List<OcorrenciaStatusDTO> lista2 = new ArrayList<OcorrenciaStatusDTO>();
        lista = repository.query(OcorrenciaStatusBean.class).findAll();
        for (OcorrenciaStatusBean bean : lista) {
            OcorrenciaStatusDTO dto = new OcorrenciaStatusDTO();
            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            if("#4D90FD".equals(bean.getCor())) {
                dto.setCor("Azul");
            }
            if("#54A754".equals(bean.getCor())) {
                dto.setCor("Verde");
            }
            if("#ffa800".equals(bean.getCor())) {
                dto.setCor("Amarelo");
            }
            if("#e64d35".equals(bean.getCor())) {
                dto.setCor("Vermelho");
            }
            if("#000000".equals(bean.getCor())) {
                dto.setCor("Preto");
            }
            
            
            
            lista2.add(dto);
        }
        return lista2;
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
