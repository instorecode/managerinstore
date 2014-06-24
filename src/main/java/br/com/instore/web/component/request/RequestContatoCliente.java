package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.ContatoClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.ContatoClienteDTO;
import br.com.instore.web.tools.AjaxResult;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestContatoCliente implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestContatoCliente() {
    }

    public RequestContatoCliente(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public List<DadosClienteBean> dadosClienteBeanList() {
        List<DadosClienteBean> lista = new ArrayList<DadosClienteBean>();
        lista = repository.query(DadosClienteBean.class).findAll();
        return lista;
    }
    
    public List<ContatoClienteDTO> contatoClienteDTOList() {
        List<ContatoClienteBean> lista = new ArrayList<ContatoClienteBean>();
        List<ContatoClienteDTO> lista2 = new ArrayList<ContatoClienteDTO>();
        lista = repository.query(ContatoClienteBean.class).findAll();
        for (ContatoClienteBean ccb : lista) {
            ContatoClienteDTO dto = new ContatoClienteDTO();
            dto.setClienteNome(ccb.getDadosCliente().getCliente().getNome());
            dto.setClienteNomeFantasia(ccb.getDadosCliente().getNomeFantasia());
            dto.setEmail(ccb.getEmail());
            dto.setIdcontatoCliente(ccb.getIdcontatoCliente());
            dto.setPrincipal(ccb.isPrincipal() ? "Sim" : "Não");
            dto.setSetor(ccb.getSetor());
            dto.setTel(ccb.getTel());
            dto.setNome(ccb.getNome());
            lista2.add(dto);        
        }
        return lista2;
    }

    public ContatoClienteBean contatoClienteBean(Integer id) {
        return repository.find(ContatoClienteBean.class, id);
    }

    public void salvar(ContatoClienteBean contato) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(contato != null && contato.getIdcontatoCliente() != null && contato.getIdcontatoCliente() > 0) {
                repository.save(repository.marge(contato));
            } else {
                repository.save(contato);
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
            
            ContatoClienteBean contato = repository.marge((ContatoClienteBean) repository.find(ContatoClienteBean.class, id));
            repository.delete(contato);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Contato removido com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover o contato!")).recursive().serialize();
        }
    }
}
