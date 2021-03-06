package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.BairroBean;
import br.com.instore.core.orm.bean.CepBean;
import br.com.instore.core.orm.bean.CidadeBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.EnderecoBean;
import br.com.instore.core.orm.bean.EstadoBean;
import br.com.instore.core.orm.bean.property.DadosCliente;
import br.com.instore.core.orm.bean.property.Estado;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.ClienteDTO;
import br.com.instore.web.tools.AjaxResult;
import java.text.SimpleDateFormat;
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
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestCliente() {
    }

    public RequestCliente(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public List<ClienteDTO> clienteDTOList() {
        List<ClienteDTO> clienteDTOList = new ArrayList<ClienteDTO>();
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).findAll();
        if (null != clienteBeanList && !clienteBeanList.isEmpty()) {
            for (ClienteBean clienteBean : clienteBeanList) {
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq(DadosCliente.IDCLIENTE, clienteBean.getIdcliente()).findOne();
                ClienteDTO dto = new ClienteDTO();
                dto.setIdcliente(clienteBean.getIdcliente());
                dto.setInstore(clienteBean.getInstore() ? "Sim" : "Não");
                dto.setMatriz(clienteBean.getMatriz() ? "Sim" : "Não");
                dto.setNome(clienteBean.getNome());
                for (ClienteBean c : clienteBeanList) {
                    if (c.getIdcliente() == clienteBean.getParente()) {
                        dto.setParente(c.getNome());
                        break;
                    }
                }
                
                dto.setSituacao( clienteBean.getSituacao() ? "Cliente ativo " : "Cliente inativo");
                
                if (clienteBean.getEndereco() != null) {
                    dto.setCep(clienteBean.getEndereco().getCep().getNumero());
                    dto.setEstado(clienteBean.getEndereco().getCep().getBairro().getCidade().getEstado().getNome());
                    dto.setCidade(clienteBean.getEndereco().getCep().getBairro().getCidade().getNome());
                    dto.setBairro(clienteBean.getEndereco().getCep().getBairro().getNome());
                    dto.setLogradouro(clienteBean.getEndereco().getCep().getBairro().getRua());
                    dto.setNumero(clienteBean.getEndereco().getNumero());
                    dto.setComplemento(clienteBean.getEndereco().getComplemento());
                }


                if (dados != null) {
                    dto.setNomeFantasia(dados.getNomeFantasia());
                    dto.setCnpj(dados.getCnpj());
                    dto.setDataInicioContrato(new SimpleDateFormat("dd/MM/yyyy").format(dados.getDataInicioContrato()));
                    dto.setDataTerminoContrato(new SimpleDateFormat("dd/MM/yyyy").format(dados.getDataTerminoContrato()));
                    dto.setIndiceReajusteContrato(dados.getIndiceReajusteContrato().toString());
                    dto.setRenovacaoAutomatica(dados.getRenovacaoAutomatica() ? "Sim" : "Não");
                    dto.setRazaoSocial(dados.getRazaoSocial());
                }
                clienteDTOList.add(dto);
            }
        }
        return clienteDTOList;
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).findAll();
        return clienteBeanList;
    }

    public List<EstadoBean> estadoBeanList() {
        List<EstadoBean> estadoBeanList = repository.query(EstadoBean.class).findAll();
        return estadoBeanList;
    }

    public ClienteBean clienteBean(Integer id) {
        return repository.find(ClienteBean.class, id);
    }

    public DadosClienteBean dadosClienteBean(Integer id) {
        return repository.query(DadosClienteBean.class).eq(DadosCliente.IDCLIENTE, id).findOne();
    }

    public void salvar(ClienteBean cliente, DadosClienteBean dadosCliente) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            cliente.setInstore(Boolean.FALSE);
            
            if(null == cliente.getSituacao()) {
                cliente.setSituacao(Boolean.TRUE);
            }
            
            if (!(cliente.getParente() != null && cliente.getParente() > 0)) {
                cliente.setParente(1);
                cliente.setMatriz(Boolean.TRUE);
            } else {
                cliente.setMatriz(Boolean.FALSE);
            }

            EnderecoBean end = cliente.getEndereco();
            CepBean cep = cliente.getEndereco().getCep();
            BairroBean bairro = cliente.getEndereco().getCep().getBairro();
            CidadeBean cidade = cliente.getEndereco().getCep().getBairro().getCidade();

            if (repository.query(EstadoBean.class).eq(Estado.IDESTADO, cidade.getEstado().getIdestado()).count() > 0) {
                EstadoBean est = repository.query(EstadoBean.class).eq(Estado.IDESTADO, cidade.getEstado().getIdestado()).findOne();
                cidade.setEstado(est);
            }

            if (cidade.getIdcidade() != null && cidade.getIdcidade() > 0) {
                cidade = repository.marge(cidade);
            }
            repository.save(cidade);


            if (bairro.getIdbairro() != null && bairro.getIdbairro() > 0) {
                bairro = repository.marge(bairro);
            }
            bairro.setCidade(cidade);
            repository.save(bairro);


            if (cep.getIdcep() != null && cep.getIdcep() > 0) {
                cep = repository.marge(cep);
            }
            cep.setBairro(bairro);
            repository.save(cep);


            if (end.getIdendereco() != null && end.getIdendereco() > 0) {
                end = repository.marge(end);
            }
            end.setCep(cep);
            repository.save(end);


            if (cliente.getIdcliente() != null && cliente.getIdcliente() > 0) {
                cliente = repository.marge(cliente);
            }
            cliente.setEndereco(end);
            repository.save(cliente);


            if (dadosCliente.getIddadosCliente() != null && dadosCliente.getIddadosCliente() > 0) {
                dadosCliente = repository.marge(dadosCliente);
            }
            dadosCliente.setCliente(cliente);
            repository.save(dadosCliente);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }
    
    public void desabilitar(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            ClienteBean cliente = repository.marge((ClienteBean) repository.find(ClienteBean.class, id));
            cliente.setSituacao(Boolean.FALSE);
            repository.save(cliente);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Cliente desabilitado com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel desabilitar o cliente!")).recursive().serialize();
        }
    }
}
