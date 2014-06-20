package br.com.instore.web.component.request;

import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.web.dto.ClienteDTO;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestCliente implements java.io.Serializable {
    
    @Inject
    private SessionRepository repository;

    public RequestCliente() {
    }

    public RequestCliente(SessionRepository repository) {
        this.repository = repository;
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
}
