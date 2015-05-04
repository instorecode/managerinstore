package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AcessoRemotoBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.ClienteSuspensoBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.ProdutoClienteBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestCliente;
import br.com.instore.web.dto.ClienteDTO;
import br.com.instore.web.dto.ClienteDTO2;
import br.com.instore.web.dto.ClienteDTOInternal;
import java.util.ArrayList;
import java.util.List;

@Resource
public class ClienteController implements java.io.Serializable {

    private Result result;
    private RequestCliente requestCliente;

    public ClienteController(Result result, RequestCliente requestCliente) {
        this.result = result;
        this.requestCliente = requestCliente;
    }

    @Get
    @Restrict
    @Path("/clientes")
    public void clientes(Boolean datajson) {
        if (null != datajson && datajson) {
            List<ClienteDTOInternal> list = requestCliente.clienteDTOList();
            result.use(Results.json()).withoutRoot().from(list).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/cliente/cadastrar")
    public void cadastrar() {
        result.include("isPageCadastro", true);
        result.include("filialBeanList2", requestCliente.paginaCadastroListaFilial());
        result.include("filialBeanList1", new ArrayList<ClienteDTO>());
        result.include("estadoBeanList", requestCliente.estadoBeanList());
        result.include("indiceReajusteList", requestCliente.indiceReajusteList());
    }

    @Post
    @Restrict
    @Path("/cliente/cadastrar")
    public void cadastrar(ClienteBean cliente, DadosClienteBean dadosCliente, Integer[] filialList) {
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

        List<ClienteDTO2> filialBeanList2 = requestCliente.paginaCadastroListaFilial2(id);
        List<ClienteDTO2> filialBeanList3 = requestCliente.paginaCadastroListaFilial3(id);

        for (ClienteDTO2 dto : filialBeanList2) {
            contador1++;
        }

        for (ClienteDTO2 dto : filialBeanList3) {
            contador2++;
        }

        result.include("filialBeanList1", filialBeanList2);
        result.include("filialBeanList2", filialBeanList3);

        result.include("contador1", contador1);
        result.include("contador2", contador2);
        result.include("estadoBeanList", requestCliente.estadoBeanList());
        result.include("cliente", cliente);
        result.include("dadosCliente", requestCliente.dadosClienteBean(id));
        result.include("indiceReajusteList", requestCliente.indiceReajusteList());
    }

    @Post
    @Restrict
    @Path("/cliente/atualizar/{id}")
    public void cadastrar(Integer id, ClienteBean cliente, DadosClienteBean dadosCliente, Integer[] filialList) {
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
    public void configuracao(Integer id, String p1, String p2, String p3, String p4, String p5) {
        requestCliente.salvar3(id, p1, p2, p3, p4, p5);
    }

    @Get
    @Restrict
    @Path("/cliente-ou-filial/suspender/{id}")
    public void suspender(Integer id) {
        result.include("cliente", requestCliente.clienteBean(id));
        result.include("suspenderList", requestCliente.suspenderList(id));
    }

    @Post
    @Restrict
    @Path("/cliente-ou-filial/suspender/{id}")
    public void suspender(ClienteSuspensoBean clienteSuspenso) {
        requestCliente.suspender(clienteSuspenso);
    }

    @Get
    @Restrict
    @Path("/cliente/configuracao/acesso/produto/{cliente}")
    public void configuracaoAcessoProduto(Integer cliente) {
        result.include("cliente", cliente);
        result.include("unidadeList", requestCliente.carregaUnidades(cliente));
        result.include("produtoList", requestCliente.carregaProduto());
        result.include("produtoClienteList", requestCliente.carregaProdutoCliente(cliente));
        result.include("tipoAcessoRemotoList", requestCliente.carregaTipoAcessoRemoto());
        result.include("acessoRemotoList", requestCliente.carregaAcessoRemoto(cliente));
    }

    @Post
    @Restrict
    @Path("/cliente/configuracao/acesso/produto/{cliente}")
    public void configuracaoAcessoProduto(Integer cliente, List<ProdutoClienteBean> produtoClienteBeanList, List<AcessoRemotoBean> acessoRemotoBeanList) {
        requestCliente.configuracaoAcessoProduto(cliente, produtoClienteBeanList, acessoRemotoBeanList);
    }
}
