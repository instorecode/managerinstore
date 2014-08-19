package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.VozDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestVoz implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestVoz() {
    }

    public RequestVoz(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> lista = new ArrayList<ClienteBean>();
        lista = repository.query(ClienteBean.class).findAll();
        return lista;
    }
    
    public List<VozDTO> beanList() {
        List<VozBean> lista = new ArrayList<VozBean>();
        List<VozDTO> lista2 = new ArrayList<VozDTO>();
        lista = repository.query(VozBean.class).findAll();
        for (VozBean voz : lista) {
            VozDTO dto = new VozDTO();
            
            dto.setClienteNome(voz.getCliente().getNome());
            dto.setEmail(voz.getEmail());
            dto.setGenero(voz.isGenero() ? "Masculino" : "Feminino");
            dto.setIdvoz(Utilities.leftPad(voz.getIdvoz()));
            dto.setNome(voz.getNome());
            dto.setTel(voz.getTel());
            dto.setTipo("A");
            
            lista2.add(dto);
        }
        return lista2;
    }

    public VozBean voz(Integer id) {
        return repository.find(VozBean.class, id);
    }

    public void salvar(VozBean bean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(bean != null && bean.getIdvoz()!= null && bean.getIdvoz() > 0) {
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
            
            VozBean bean = repository.marge((VozBean) repository.find(VozBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Voz removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a voz!")).recursive().serialize();
        }
    }
}
