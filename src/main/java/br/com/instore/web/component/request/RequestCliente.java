package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
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
import br.com.instore.core.orm.bean.property.DadosCliente;
import br.com.instore.core.orm.bean.property.Estado;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.ClienteDTO;
import br.com.instore.web.dto.ClienteDTO2;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

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

    public List<ClienteDTO> filialDTOList() {
        List<ClienteDTO> clienteDTOList = new ArrayList<ClienteDTO>();
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", false).findAll();
        if (null != clienteBeanList && !clienteBeanList.isEmpty()) {
            for (ClienteBean clienteBean : clienteBeanList) {
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq(DadosCliente.IDCLIENTE, clienteBean.getIdcliente()).findOne();
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

    public class ClienteDTOInternal extends ClienteDTO {

        private Integer totalFiliaisAtivas = 0;
        private Integer totalFiliaisInativas = 0;

        public Integer getTotalFiliaisAtivas() {
            return totalFiliaisAtivas;
        }

        public void setTotalFiliaisAtivas(Integer totalFiliaisAtivas) {
            this.totalFiliaisAtivas = totalFiliaisAtivas;
        }

        public Integer getTotalFiliaisInativas() {
            return totalFiliaisInativas;
        }

        public void setTotalFiliaisInativas(Integer totalFiliaisInativas) {
            this.totalFiliaisInativas = totalFiliaisInativas;
        }
    }

    public List<ClienteDTOInternal> clienteDTOList() {
        List<ClienteDTOInternal> clienteDTOList = new ArrayList<ClienteDTOInternal>();
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        if (null != clienteBeanList && !clienteBeanList.isEmpty()) {
            for (ClienteBean clienteBean : clienteBeanList) {
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq(DadosCliente.IDCLIENTE, clienteBean.getIdcliente()).findOne();

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
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq(DadosCliente.IDCLIENTE, clienteBean.getIdcliente()).findOne();
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
        return repository.query(DadosClienteBean.class).eq(DadosCliente.IDCLIENTE, id).findOne();
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
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }

    public void salvar2(ClienteBean cliente, DadosClienteBean dadosCliente) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
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
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq(DadosCliente.IDCLIENTE, clienteBean.getIdcliente()).findOne();
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

            DadosClienteBean dcb = repository.query(DadosClienteBean.class).eq("cliente.idcliente", clienteBean(id).getIdcliente()).findOne();
            dcb.setLocalOrigemMusica(p1);
            dcb.setLocalDestinoMusica(p2);
            dcb.setLocalOrigemSpot(p3);
            dcb.setLocalDestinoSpot(p4);
            dcb.setLocalDestinoExp(p5);

//            SmbFile smbP1 = new SmbFile(p1, Utilities.getAuthSmbDefault());



//            if (!smbP1.exists() || smbP1.isFile()) {
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel encontrar o diretório " + p1 + " ou caminho do diretório não pode ter o nome de um arquivo!")).recursive().serialize();
//                return;
//            }
//
//            SmbFile smbP2 = new SmbFile(p2, Utilities.getAuthSmbDefault());
//            if (!smbP2.exists() || smbP2.isFile()) {
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel encontrar o diretório " + p2 + " ou caminho do diretório não pode ter o nome de um arquivo!")).recursive().serialize();
//                return;
//            }
//
//            SmbFile smbP3 = new SmbFile(p3, Utilities.getAuthSmbDefault());
//            if (!smbP3.exists() || smbP3.isFile()) {
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel encontrar o diretório " + p3 + " ou caminho do diretório não pode ter o nome de um arquivo!")).recursive().serialize();
//                return;
//            }
//
//            SmbFile smbP4 = new SmbFile(p4, Utilities.getAuthSmbDefault());
//            smbP4.isDirectory();
//            if (!smbP4.exists() || smbP4.isFile()) {
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel encontrar o diretório " + p4 + " ou caminho do diretório não pode ter o nome de um arquivo!")).recursive().serialize();
//                return;
//            }
//
//            SmbFile smbP5 = new SmbFile(p5, Utilities.getAuthSmbDefault());
//            if (!smbP5.exists() || smbP5.isFile()) {
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel encontrar o diretório " + p5 + " ou caminho do diretório não pode ter o nome de um arquivo!")).recursive().serialize();
//                return;
//            }

            repository.save(dcb);
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Diretório Inválido")).recursive().serialize();
//        } catch (SmbException e) {
//            System.out.println("aqui o erro samba");
//            e.printStackTrace();
//            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Diretório Inválido")).recursive().serialize();
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
        List<ClienteDTO2> lista = repository.query("select idcliente , parente , nome  from cliente where matriz = 0").executeSQL(ClienteDTO2.class);
        return lista;
    }

    public List<ClienteDTO2> paginaCadastroListaFilial2(Integer id) {
        List<ClienteDTO2> lista = repository.query("select idcliente , parente , nome  from cliente where matriz = 0 and parente = " + id).executeSQL(ClienteDTO2.class);
        return lista;
    }

    public List<ClienteDTO2> paginaCadastroListaFilial3(Integer id) {
        List<ClienteDTO2> lista = repository.query("select idcliente , parente , nome , codigo_interno as codigoInterno from cliente where matriz = 0 and parente != " + id).executeSQL(ClienteDTO2.class);
        return lista;
    }
}
