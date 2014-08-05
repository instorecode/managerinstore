package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestCliente;
import br.com.instore.web.dto.ClienteDTO;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@Controller
public class ClienteController implements java.io.Serializable {

    @Inject
    private Result result;
    
    @Inject
    private RequestCliente requestCliente;

    public ClienteController() {
    }

    public ClienteController(Result result, RequestCliente requestCliente) {
        this.result = result;
        this.requestCliente = requestCliente;
    }

    @Get
    @Restrict
    @Path("/clientes")
    public void clientes(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestCliente.clienteDTOList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/cliente/cadastrar")
    public void cadastrar() {
        result.include("isPageCadastro", true);
        result.include("filialBeanList2", requestCliente.filialDTOListAux(null, true , null));
        result.include("filialBeanList1", new ArrayList<ClienteDTO>());
        result.include("estadoBeanList", requestCliente.estadoBeanList());
    }

    @Post
    @Restrict
    @Path("/cliente/cadastrar")
    public void cadastrar(ClienteBean cliente , DadosClienteBean dadosCliente , Integer [] filialList) {
        requestCliente.salvar(cliente, dadosCliente, filialList);
    }

    @Get
    @Restrict
    @Path("/cliente/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("isPageCadastro", false);
        ClienteBean cliente = requestCliente.clienteBean(id);
        Integer contador1 = 0;
        Integer contador2 = 0;
        
        List<ClienteDTO> filialBeanList1 = requestCliente.filialDTOListAux(id, false , true);
        List<ClienteDTO> filialBeanList2 = requestCliente.filialDTOListAux(id, false , false);
        
        for (ClienteDTO dto : filialBeanList1) {
            contador1++;
        }
        
        for (ClienteDTO dto : filialBeanList2) {
            contador2++;
        }
        
        
        
        result.include("filialBeanList1", filialBeanList1);
        result.include("filialBeanList2", filialBeanList2);
        
        
        result.include("contador1", contador1);
        result.include("contador2", contador2);
        result.include("estadoBeanList", requestCliente.estadoBeanList());
        result.include("cliente", cliente);
        result.include("dadosCliente", requestCliente.dadosClienteBean(id));
    }

    @Post
    @Restrict
    @Path("/cliente/atualizar/{id}")
    public void cadastrar(Integer id , ClienteBean cliente , DadosClienteBean dadosCliente, Integer [] filialList) {
        requestCliente.salvar(cliente, dadosCliente, filialList);
    }

    @Get
    @Restrict
    @Path("/cliente/remover/{id}")
    public void remover(Integer id) {
        result.include("cliente", requestCliente.clienteBean(id));
        result.include("dadosCliente", requestCliente.dadosClienteBean(id));
    }

    @Post
    @Restrict
    @Path("/cliente/remover/{id}")
    public void remover(Integer id, String param) {
        requestCliente.desabilitar(id);
    }
    
    @Get
    @Restrict
    @Path("/cliente-configuracao/{id}")
    public void configuracao(Integer id) {
        ClienteBean cliente = requestCliente.clienteBean(id);
        result.include("cliente", cliente);
        result.include("dadosCliente", requestCliente.dadosClienteBean(id));
    }
    
    @Post
    @Restrict
    @Path("/cliente-configuracao/{id}")
    public void configuracao(Integer id, String p1,String p2,String p3,String p4,String p5) {
        requestCliente.salvar3(id, p1, p2, p3, p4, p5);
    }
}
