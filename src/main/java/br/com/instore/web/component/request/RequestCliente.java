package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.EstadoBean;
import br.com.instore.web.dto.ClienteDTO;
import br.com.instore.web.tools.AjaxResult;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestCliente implements java.io.Serializable {
    
    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;

    public RequestCliente() {
    }

    public RequestCliente(SessionRepository repository, Result result) {
        this.repository = repository;
        this.result = result;
    }
    
    public List<ClienteDTO> clienteDTOList() {
        List<ClienteDTO> clienteDTOList = new ArrayList<ClienteDTO>();
        List<ClienteBean> clienteBeanList =repository.query(ClienteBean.class).findAll();
        if(null != clienteBeanList && !clienteBeanList.isEmpty()) {
            for (ClienteBean clienteBean : clienteBeanList) {
                ClienteDTO dto = new ClienteDTO();
                dto.setIdcliente(clienteBean.getIdcliente());
                dto.setInstore(clienteBean.getInstore() ? "Sim" : "Não");
                dto.setMatriz(clienteBean.getMatriz() ? "Sim" : "Não");
                dto.setNome(clienteBean.getNome());
                for (ClienteBean c : clienteBeanList) {
                    if(c.getIdcliente() == clienteBean.getParente() ) {
                        dto.setParente(c.getNome());
                        break;
                    }
                }
                clienteDTOList.add(dto);
            }
        }
        return clienteDTOList;
    }
    
    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList =repository.query(ClienteBean.class).findAll();
        return clienteBeanList;
    }
    
    public List<EstadoBean> estadoBeanList() {
        List<EstadoBean> estadoBeanList =repository.query(EstadoBean.class).findAll();
        return estadoBeanList;
    }
    
    public void salvar(ClienteBean cliente , DadosClienteBean dadosCliente) {
        try {
            cliente.setInstore(Boolean.FALSE);
            
            if(cliente.getParente() != null && cliente.getParente() > 0) {
                cliente.setMatriz(Boolean.FALSE);
            } else {
                cliente.setMatriz(Boolean.TRUE);
            }
            repository.save(cliente.getEndereco());
            repository.save(cliente);
            
            dadosCliente.setCliente(cliente);
            repository.save(dadosCliente);
            
            repository.finalize();
           result.use(Results.json()).withoutRoot().from( new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from( new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }
}
