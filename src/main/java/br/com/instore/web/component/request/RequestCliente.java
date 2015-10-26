package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.DataValidatorException;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.bean.AcessoRemotoBean;
import br.com.instore.core.orm.bean.BairroBean;
import br.com.instore.core.orm.bean.CepBean;
import br.com.instore.core.orm.bean.CidadeBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.ClienteSuspensoBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.EnderecoBean;
import br.com.instore.core.orm.bean.EstadoBean;
import br.com.instore.core.orm.bean.IndiceReajusteBean;
import br.com.instore.core.orm.bean.ProdutoBean;
import br.com.instore.core.orm.bean.ProdutoClienteBean;
import br.com.instore.core.orm.bean.TipoAcessoRemotoBean;
import br.com.instore.core.orm.property.DadosCliente;
import br.com.instore.core.orm.property.Estado;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.ClienteDTO;
import br.com.instore.web.dto.ClienteDTO2;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.instore.web.dto.ClienteDTOInternal;
import java.net.MalformedURLException;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

@Component
@RequestScoped
public class RequestCliente implements java.io.Serializable {

    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;

    public RequestCliente(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public List<ClienteDTO> filialDTOList() {
        List<ClienteDTO> clienteDTOList = new ArrayList<ClienteDTO>();
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", false).findAll();
        if (null != clienteBeanList && !clienteBeanList.isEmpty()) {
            for (ClienteBean clienteBean : clienteBeanList) {
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq(DadosCliente.CLIENTE, clienteBean.getIdcliente()).findOne();
                ClienteDTO dto = new ClienteDTO();
                dto.setIdcliente(Utilities.leftPad(clienteBean.getIdcliente()));
                dto.setInstore(clienteBean.getInstore() ? "Sim" : "Não");
                dto.setMatriz(clienteBean.getMatriz() ? "Sim" : "Não");
                dto.setNome(clienteBean.getNome());
                dto.setParente(clienteBean.getParente().toString());

                dto.setCodigoInterno(clienteBean.getCodigoInterno());
                dto.setCodigoExterno(clienteBean.getCodigoExterno());
                for (ClienteBean c : clienteBeanList) {
                    if (c.getIdcliente() == clienteBean.getParente()) {
                        dto.setParente(c.getNome());
                        break;
                    }
                }

                dto.setSituacao(clienteBean.getSituacao() ? "Cliente ativo " : "Cliente inativo");

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

    

    public List<ClienteDTOInternal> clienteDTOList() {
        List<ClienteDTOInternal> clienteDTOList = new ArrayList<ClienteDTOInternal>();
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        if (null != clienteBeanList && !clienteBeanList.isEmpty()) {
            for (ClienteBean clienteBean : clienteBeanList) {
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq(DadosCliente.CLIENTE, clienteBean.getIdcliente()).findOne();

                String qs = "select \n"
                        + "	idcliente, \n"
                        + "	'' as p \n"
                        + "from \n"
                        + "	cliente \n"
                        + "where \n"
                        + "	parente = " + clienteBean.getIdcliente();

                System.out.println("---------------------------------------------------------------");
                System.out.println(qs);
                System.out.println("---------------------------------------------------------------");
                final List<Integer> idUnidades = new ArrayList<Integer>();

                repository.query(qs).executeSQL(new Each() {
                    private Integer idcliente;
                    private String p;

                    @Override
                    public void each() {
                        idUnidades.add(idcliente);
                    }
                });

                ClienteDTOInternal dto = new ClienteDTOInternal();

                if (null != idUnidades && !idUnidades.isEmpty()) {
                    for (Integer id : idUnidades) {
                        ClienteSuspensoBean clienteSuspensoBean = repository.query(ClienteSuspensoBean.class).eq("cliente.idcliente", id).orderDesc("data").limit(1).findOne();
                        if (null != clienteSuspensoBean) {
                            if (clienteSuspensoBean.getSuspenso() && new Date().after(clienteSuspensoBean.getDataFim())) {
                                dto.setTotalFiliaisInativas(dto.getTotalFiliaisInativas() + 1);
                            } else {
                                dto.setTotalFiliaisAtivas(dto.getTotalFiliaisAtivas() + 1);
                            }
                        } else {
                            dto.setTotalFiliaisAtivas(dto.getTotalFiliaisAtivas() + 1);
                        }
                    }
                }

                dto.setIdcliente(Utilities.leftPad(clienteBean.getIdcliente()));
                dto.setInstore(clienteBean.getInstore() ? "Sim" : "Não");
                dto.setMatriz(clienteBean.getMatriz() ? "Sim" : "Não");
                dto.setNome(clienteBean.getNome());
                dto.setCodigoInterno(clienteBean.getCodigoInterno());
                dto.setCodigoExterno(clienteBean.getCodigoExterno());
                for (ClienteBean c : clienteBeanList) {
                    if (c.getIdcliente() == clienteBean.getParente()) {
                        dto.setParente(c.getNome());
                        break;
                    }
                }

                dto.setSituacao(clienteBean.getSituacao() ? "Cliente ativo " : "Cliente inativo");

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
                    dto.setRazaoSocial(dados.getRazaoSocial());
                    dto.setLocal1(dados.getLocalOrigemMusica());
                    dto.setLocal2(dados.getLocalDestinoMusica());
                    dto.setLocal3(dados.getLocalOrigemSpot());
                    dto.setLocal4(dados.getLocalDestinoSpot());
                    dto.setLocal5(dados.getLocalDestinoExp());
                }
                clienteDTOList.add(dto);
            }
        }
        return clienteDTOList;
    }

    public List<ClienteDTO> clienteDTOList(Integer id) {
        List<ClienteDTO> clienteDTOList = new ArrayList<ClienteDTO>();
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("parente", id).findAll();

        if (null != clienteBeanList && !clienteBeanList.isEmpty()) {
            for (ClienteBean clienteBean : clienteBeanList) {
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq(DadosCliente.CLIENTE, clienteBean.getIdcliente()).findOne();
                ClienteDTO dto = new ClienteDTO();
                dto.setIdcliente(Utilities.leftPad(clienteBean.getIdcliente()));
                dto.setInstore(clienteBean.getInstore() ? "Sim" : "Não");
                dto.setMatriz(clienteBean.getMatriz() ? "Sim" : "Não");
                dto.setNome(clienteBean.getNome());
                dto.setCodigoInterno(clienteBean.getCodigoInterno());
                dto.setCodigoExterno(clienteBean.getCodigoExterno());
                for (ClienteBean c : clienteBeanList) {
                    if (c.getIdcliente() == clienteBean.getParente()) {
                        dto.setParente(c.getNome());
                        break;
                    }
                }

                dto.setSituacao(clienteBean.getSituacao() ? "Cliente ativo " : "Cliente inativo");

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
                    dto.setLocal1(dados.getLocalOrigemMusica());
                    dto.setLocal2(dados.getLocalDestinoMusica());
                    dto.setLocal3(dados.getLocalOrigemSpot());
                    dto.setLocal4(dados.getLocalDestinoSpot());
                    dto.setLocal5(dados.getLocalDestinoExp());
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

    public List<IndiceReajusteBean> indiceReajusteList() {
        List<IndiceReajusteBean> indiceReajusteList = repository.query(IndiceReajusteBean.class).findAll();
        return indiceReajusteList;
    }

    public ClienteBean clienteBean(Integer id) {
        return repository.find(ClienteBean.class, id);
    }

    public DadosClienteBean dadosClienteBean(Integer id) {
        return repository.query(DadosClienteBean.class).eq(DadosCliente.CLIENTE, id).findOne();
    }

    public void salvar(ClienteBean cliente, DadosClienteBean dadosCliente, Integer[] filialList) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            cliente.setInstore(Boolean.FALSE);

            if (null == cliente.getSituacao()) {
                cliente.setSituacao(Boolean.TRUE);
            }

            cliente.setParente(0);
            cliente.setMatriz(Boolean.TRUE);

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
            if (end.getComplemento() == null || end.getComplemento().isEmpty()) {
                end.setComplemento("");
            }
            end.setCep(cep);
            repository.save(end);

            if (cliente.getIdcliente() != null && cliente.getIdcliente() > 0) {
                cliente = repository.marge(cliente);
            }
            cliente.setEndereco(end);
            repository.save(cliente);

            if (null == dadosCliente.getLocalOrigemMusica() || dadosCliente.getLocalOrigemMusica().isEmpty()) {
                dadosCliente.setLocalOrigemMusica("");
            }

            if (null == dadosCliente.getLocalDestinoMusica() || dadosCliente.getLocalDestinoMusica().isEmpty()) {
                dadosCliente.setLocalDestinoMusica("");
            }

            if (null == dadosCliente.getLocalOrigemSpot() || dadosCliente.getLocalOrigemSpot().isEmpty()) {
                dadosCliente.setLocalOrigemSpot("");
            }

            if (null == dadosCliente.getLocalDestinoSpot() || dadosCliente.getLocalDestinoSpot().isEmpty()) {
                dadosCliente.setLocalDestinoSpot("");
            }

            if (null == dadosCliente.getLocalDestinoExp() || dadosCliente.getLocalDestinoExp().isEmpty()) {
                dadosCliente.setLocalDestinoExp("");
            }

            if (dadosCliente.getIddadosCliente() != null && dadosCliente.getIddadosCliente() > 0) {
                dadosCliente = repository.marge(dadosCliente);
            }

            if (dadosCliente.getIndiceReajusteContrato() == null) {
                dadosCliente.setIndiceReajusteContrato(0.0);
            }

            dadosCliente.setCliente(cliente);
            repository.save(dadosCliente);

            String sql = "update cliente set parente = 0 where parente = " + cliente.getIdcliente();
            repository.query(sql).executeSQLCommand();

            if (null != filialList && filialList.length > 0) {
                for (Integer id : filialList) {
                    ClienteBean item = repository.find(ClienteBean.class, id);
                    item.setParente(cliente.getIdcliente());
                }
            }

            repository.finalize();
            
            criarDiretorio(dadosCliente.getLocalOrigemMusica());
            criarDiretorio(dadosCliente.getLocalOrigemSpot());
            criarDiretorio(dadosCliente.getLocalDestinoMusica());
            criarDiretorio(dadosCliente.getLocalDestinoSpot());
            criarDiretorio(dadosCliente.getLocalDestinoExp());
            
            List<ClienteBean> list = repository.query(ClienteBean.class).orderDesc("idcliente").limit(1).findAll();
            if (null != list && !list.isEmpty() && null != list.get(0)) {
                sessionUsuario.setCliente(list.get(0));
            }

            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }

    }

    public void criarDiretorio(String path) {
        if (null != path && !path.isEmpty()) {
            try {
                NtlmPasswordAuthentication auth = null;
                
                if(null != path && path.startsWith("smb://192.168.1.249")) {
                    auth = Utilities.getAuthSmbDefault1921681249();
                } else {
                    auth = Utilities.getAuthSmbDefault();
                }
                
                SmbFile sf = new SmbFile(path, auth);
                if (!sf.exists()) {
                    sf.mkdirs();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (SmbException e) {
                e.printStackTrace();
            }
        }
    }

    public void salvar2(ClienteBean cliente, DadosClienteBean dadosCliente) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            dadosCliente.setLocalDestinoExp("");
            dadosCliente.setLocalDestinoMusica("");
            dadosCliente.setLocalDestinoSpot("");
            dadosCliente.setLocalOrigemMusica("");
            dadosCliente.setLocalOrigemSpot("");

            cliente.setInstore(Boolean.FALSE);

            if (null == cliente.getSituacao()) {
                cliente.setSituacao(Boolean.TRUE);
            }

            if (null == cliente.getParente() || cliente.getParente() < 1) {
                cliente.setParente(1);
            }
            cliente.setMatriz(Boolean.FALSE);
            cliente.setFaturamenoMatriz(Boolean.FALSE);

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

            if (bairro.getTipo() == null || bairro.getTipo().isEmpty()) {
                bairro.setTipo("NÃO FOI INFORMADO");
            }
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

            if (null == end.getComplemento() || end.getComplemento().isEmpty()) {
                end.setComplemento("NÃO FOI INFORMADO");
            }

            if (null == end.getNumero() || end.getNumero().isEmpty()) {
                end.setNumero("000");
            }

            if (end.getIdendereco() != null && end.getIdendereco() > 0) {

                end = repository.marge(end);
            }
            end.setCep(cep);
            repository.save(end);

            boolean incluirDados = false;
            if (cliente.getIdcliente() != null && cliente.getIdcliente() > 0) {
                cliente = repository.marge(cliente);
            } else {
                incluirDados = true;
            }
            cliente.setEndereco(end);
            repository.save(cliente);

            if (incluirDados) {
                DadosClienteBean dcb1 = repository.query(DadosClienteBean.class).eq("cliente.idcliente", cliente.getParente()).findOne();
                dadosCliente = new DadosClienteBean();
                dadosCliente.setIddadosCliente(null);
                dadosCliente.setCliente(cliente);
                dadosCliente.setCnpj(dcb1.getCnpj());
                dadosCliente.setDataInicioContrato(dcb1.getDataInicioContrato());
                dadosCliente.setDataTerminoContrato(dcb1.getDataTerminoContrato());
                dadosCliente.setIndiceReajusteContrato(dcb1.getIndiceReajusteContrato());
                dadosCliente.setNomeFantasia(dcb1.getNomeFantasia());
                dadosCliente.setRazaoSocial(dcb1.getRazaoSocial());
                dadosCliente.setRenovacaoAutomatica(dcb1.getRenovacaoAutomatica());
                dadosCliente.setLocalDestinoExp("");
                dadosCliente.setLocalDestinoMusica("");
                dadosCliente.setLocalDestinoSpot("");
                dadosCliente.setLocalOrigemMusica("");
                dadosCliente.setLocalOrigemSpot("");
                dadosCliente.setValorContrato(0.0);
                repository.save(dadosCliente);
            }

//            if (dadosCliente.getIddadosCliente() != null && dadosCliente.getIddadosCliente() > 0) {
//                dadosCliente = repository.marge(dadosCliente);
//            }
//            dadosCliente.setCliente(cliente);
//            repository.save(dadosCliente);
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

    public List<ClienteDTO> filialDTOListAux(Integer id, Boolean cadastrando, Boolean lista1) {
        List<ClienteDTO> clienteDTOList = new ArrayList<ClienteDTO>();
        List<ClienteBean> clienteBeanList;

        if (cadastrando) {
            clienteBeanList = repository.query(ClienteBean.class).eq("matriz", false).findAll();
        } else {
            if (lista1) {
                clienteBeanList = repository.query(ClienteBean.class).eq("matriz", false).eq("parente", id).findAll();
            } else {
                clienteBeanList = repository.query(ClienteBean.class).eq("matriz", false).not().eq("parente", id).findAll();
            }
        }

        if (null != clienteBeanList && !clienteBeanList.isEmpty()) {
            for (ClienteBean clienteBean : clienteBeanList) {
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq(DadosCliente.CLIENTE, clienteBean.getIdcliente()).findOne();
                ClienteDTO dto = new ClienteDTO();
                dto.setIdcliente(Utilities.leftPad(clienteBean.getIdcliente()));
                dto.setInstore(clienteBean.getInstore() ? "Sim" : "Não");
                dto.setMatriz(clienteBean.getMatriz() ? "Sim" : "Não");
                dto.setNome(clienteBean.getNome());
                dto.setParente(clienteBean.getParente().toString());
                if (null != clienteBean.getParente() && clienteBean.getParente() > 0) {
                    ClienteBean clienteAux = repository.find(ClienteBean.class, clienteBean.getParente());
                    dto.setNomeParente(clienteAux.getNome());
                }

                for (ClienteBean c : clienteBeanList) {
                    if (c.getIdcliente() == clienteBean.getParente()) {
                        dto.setParente(c.getNome());
                        break;
                    }
                }

                dto.setSituacao(clienteBean.getSituacao() ? "Cliente ativo " : "Cliente inativo");

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

    public void salvar3(Integer id, String p1, String p2, String p3, String p4, String p5) {
        try {
            
            if (null == p1 || p1.isEmpty()) {
                p1 = "";
            }

            if (null == p2 || p2.isEmpty()) {
                p2 = "";
            }
            
            if (null == p3 || p3.isEmpty()) {
                p3 = "";
            }
            
            if (null == p4 || p4.isEmpty()) {
                p4 = "";
            }
            
            if (null == p5 || p5.isEmpty()) {
                p5 = "";
            }

            p1 = Utilities.formatarURLConfigCliente(p1);
            p2 = Utilities.formatarURLConfigCliente(p2);
            p3 = Utilities.formatarURLConfigCliente(p3);
            p4 = Utilities.formatarURLConfigCliente(p4);
            p5 = Utilities.formatarURLConfigCliente(p5);
            
            criarDiretorio(p1);
            criarDiretorio(p2);
            criarDiretorio(p3);
            criarDiretorio(p4);
            criarDiretorio(p5);

            if ("".equals(p1.replace("\\s", "")) && "smb:".equals(p1.replace("\\s", ""))) {
                p1 = "";
            }
            
            if ("".equals(p2.replace("\\s", "")) && "smb:".equals(p2.replace("\\s", ""))) {
                p2 = "";
            }
            
            if ("".equals(p3.replace("\\s", "")) && "smb:".equals(p3.replace("\\s", ""))) {
                p3 = "";
            }
            
            if ("".equals(p4.replace("\\s", "")) && "smb:".equals(p4.replace("\\s", ""))) {
                p4 = "";
            }
            
            if ("".equals(p5.replace("\\s", "")) && "smb:".equals(p5.replace("\\s", ""))) {
                p5 = "";
            }
            
//            p1 = p1.toLowerCase();
//            p2 = p2.toLowerCase();
//            p3 = p3.toLowerCase();
//            p4 = p4.toLowerCase();
//            p5 = p5.toLowerCase();
            
            
            DadosClienteBean dcb = repository.query(DadosClienteBean.class).eq("cliente.idcliente", clienteBean(id).getIdcliente()).findOne();
            dcb.setLocalOrigemMusica(p1);
            dcb.setLocalDestinoMusica(p2);
            dcb.setLocalOrigemSpot(p3);
            dcb.setLocalDestinoSpot(p4);
            dcb.setLocalDestinoExp(p5);
            
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            repository.save(dcb);
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }

    public List<ClienteSuspensoBean> suspenderList(Integer id) {
        return repository.query(ClienteSuspensoBean.class).eq("cliente.idcliente", id).orderDesc("data").findAll();
    }

    public void suspender(ClienteSuspensoBean clienteSuspensoBean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            clienteSuspensoBean.setUsuario(sessionUsuario.getUsuarioBean());
            clienteSuspensoBean.setData(new Date());

            if (clienteSuspensoBean.getSuspenso()) {
                if (clienteSuspensoBean.getDataInicio() == null) {
                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe a data de inicio!")).recursive().serialize();
                    return;
                }

                if (clienteSuspensoBean.getDataFim() == null) {
                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe a data final!")).recursive().serialize();
                    return;
                }
            }

            repository.save(clienteSuspensoBean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }

    public List<ClienteDTO2> paginaCadastroListaFilial() {
        List<ClienteDTO2> lista = repository.query("select idcliente , parente , nome , codigo_interno as codigoInterno  from cliente where matriz = 0").executeSQL(ClienteDTO2.class);
        return lista;
    }

    public List<ClienteDTO2> paginaCadastroListaFilial2(Integer id) {
        List<ClienteDTO2> lista = repository.query("select idcliente , parente , nome , codigo_interno as codigoInterno  from cliente where matriz = 0 and parente = " + id).executeSQL(ClienteDTO2.class);
        return lista;
    }

    public List<ClienteDTO2> paginaCadastroListaFilial3(Integer id) {
        List<ClienteDTO2> lista = repository.query("select idcliente , parente , nome , codigo_interno as codigoInterno from cliente where matriz = 0 and parente != " + id).executeSQL(ClienteDTO2.class);
        return lista;
    }

    public class ClienteConf {

        private ClienteBean cliente;
        private List<ProdutoClienteBean> produtoClienteList;
        private List<AcessoRemotoBean> acessoRemotoList;

        public ClienteBean getCliente() {
            return cliente;
        }

        public void setCliente(ClienteBean cliente) {
            this.cliente = cliente;
        }

        public List<ProdutoClienteBean> getProdutoClienteList() {
            return produtoClienteList;
        }

        public void setProdutoClienteList(List<ProdutoClienteBean> produtoClienteList) {
            this.produtoClienteList = produtoClienteList;
        }

        public List<AcessoRemotoBean> getAcessoRemotoList() {
            return acessoRemotoList;
        }

        public void setAcessoRemotoList(List<AcessoRemotoBean> acessoRemotoList) {
            this.acessoRemotoList = acessoRemotoList;
        }
    }

    public List<ClienteConf> carregaUnidades(Integer cliente) {
        List<ClienteConf> lista = new ArrayList<ClienteConf>();
        List<ClienteBean> clienteLista = new ArrayList<ClienteBean>();
        clienteLista = repository.query(ClienteBean.class).eq("parente", cliente).eq("matriz", false).findAll();

        for (ClienteBean item : clienteLista) {
            ClienteConf cc = new ClienteConf();
            cc.setCliente(item);
            cc.setProdutoClienteList((List<ProdutoClienteBean>) repository.query(ProdutoClienteBean.class).eq("cliente.idcliente", item.getIdcliente()).findAll());
            cc.setAcessoRemotoList((List<AcessoRemotoBean>) repository.query(AcessoRemotoBean.class).eq("cliente.idcliente", item.getIdcliente()).findAll());
            lista.add(cc);
        }

        return lista;
    }

    public List<ProdutoBean> carregaProduto() {
        List<ProdutoBean> lista = new ArrayList<ProdutoBean>();
        lista = repository.query(ProdutoBean.class).findAll();
        return lista;
    }

    public List<ProdutoClienteBean> carregaProdutoCliente(Integer cliente) {
        List<ProdutoClienteBean> lista = new ArrayList<ProdutoClienteBean>();

        List<ClienteBean> clienteLista = new ArrayList<ClienteBean>();
        clienteLista = repository.query(ClienteBean.class).eq("parente", cliente).eq("matriz", false).orderAsc("idcliente").findAll();
        Integer[] in = new Integer[(null != clienteLista ? clienteLista.size() : 0)];
        int i = 0;
        for (ClienteBean item : clienteLista) {
            in[i] = item.getIdcliente();
            i++;
        }

        if (in.length > 0) {
            lista = repository.query(ProdutoClienteBean.class).in("cliente.idcliente", in).findAll();
        }

        return lista;
    }

    public List<TipoAcessoRemotoBean> carregaTipoAcessoRemoto() {
        List<TipoAcessoRemotoBean> lista = new ArrayList<TipoAcessoRemotoBean>();
        lista = repository.query(TipoAcessoRemotoBean.class).orderAsc("id").findAll();
        return lista;
    }

    public List<AcessoRemotoBean> carregaAcessoRemoto(Integer cliente) {
        List<AcessoRemotoBean> lista = new ArrayList<AcessoRemotoBean>();

        List<ClienteBean> clienteLista = new ArrayList<ClienteBean>();
        clienteLista = repository.query(ClienteBean.class).eq("parente", cliente).eq("matriz", false).orderAsc("idcliente").findAll();
        Integer[] in = new Integer[(null != clienteLista ? clienteLista.size() : 0)];

        int i = 0;
        for (ClienteBean item : clienteLista) {
            in[i] = item.getIdcliente();
            i++;
        }

        if (in.length > 0) {
            lista = repository.query(AcessoRemotoBean.class).in("cliente.idcliente", in).findAll();
        }
        return lista;
    }

    public void configuracaoAcessoProduto(Integer cliente, List<ProdutoClienteBean> produtoClienteBeanList, List<AcessoRemotoBean> acessoRemotoBeanList) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            repository.query("delete from produto_cliente where cliente in ((select idcliente from cliente where parente = " + cliente + "))").executeSQLCommand();
            repository.query("delete from acesso_remoto where cliente in ((select idcliente from cliente where parente = " + cliente + "))").executeSQLCommand();

            if (null != produtoClienteBeanList) {
                for (ProdutoClienteBean produtoClienteBean : produtoClienteBeanList) {
                    if (null != produtoClienteBean) {
                        repository.save(produtoClienteBean);
                    }
                }
            }

            if (null != acessoRemotoBeanList) {
                for (AcessoRemotoBean acessoRemotoBean : acessoRemotoBeanList) {
                    if (null != acessoRemotoBean) {
                        repository.save(acessoRemotoBean);
                    }
                }
            }
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (DataValidatorException e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }
}
