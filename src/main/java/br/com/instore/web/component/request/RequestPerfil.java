package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.FuncionalidadeBean;
import br.com.instore.core.orm.bean.PerfilBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.PerfilDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestPerfil implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestPerfil() {
    }

    public RequestPerfil(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    
    public List<PerfilDTO> beanList() {
        List<PerfilBean> lista = new ArrayList<PerfilBean>();
        List<PerfilDTO> lista2 = new ArrayList<PerfilDTO>();
        lista = repository.query(PerfilBean.class).findAll();
        for (PerfilBean bean : lista) {
            PerfilDTO dto = new PerfilDTO();            
            dto.setId( Utilities.leftPad(bean.getIdperfil() ) );
            dto.setNome(bean.getNome());
            
            String fs = "<ul>";
            for(FuncionalidadeBean f : bean.getFuncionalidadeBeanList()) {
                fs += "<li>"+f.getNome()+"</li>";
            }
            fs += "</ul>";
            dto.setFuncionalidade(fs);
            lista2.add(dto);
        }
        return lista2;
    }

    public PerfilBean bean(Integer id) {
        return repository.find(PerfilBean.class, id);
    }

    public void salvar(PerfilBean bean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(bean != null && bean.getIdperfil()!= null && bean.getIdperfil() > 0) {
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
            
            PerfilBean bean = repository.marge((PerfilBean) repository.find(PerfilBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Perfil removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover o perfil!")).recursive().serialize();
        }
    }
}