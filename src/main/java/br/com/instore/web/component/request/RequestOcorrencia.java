package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.OcorrenciaBean;
import br.com.instore.core.orm.bean.OcorrenciaOrigemBean;
import br.com.instore.core.orm.bean.OcorrenciaPrioridadeBean;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.core.orm.bean.OcorrenciaSolucaoBean;
import br.com.instore.core.orm.bean.OcorrenciaStatusBean;
import br.com.instore.core.orm.bean.OcorrenciaUsuarioBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaDTO;
import br.com.instore.web.dto.OcorrenciaFormDTO;
import br.com.instore.web.dto.OcorrenciaJSON;
import br.com.instore.web.dto.OcorrenciaRelatorioRapidoDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestOcorrencia implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestOcorrencia() {
    }

    public RequestOcorrencia(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public void beanList(Integer page, Integer rows, Integer id, String descricao, Integer idcliente, Integer idusuario, Integer idprioridade, Integer idstatus) {
        System.out.println("identificador status " + idstatus);
        OcorrenciaJSON json = new OcorrenciaJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<OcorrenciaBean> lista = new ArrayList<OcorrenciaBean>();

        Query q1 = repository.query(OcorrenciaBean.class);
        Query q2 = repository.query(OcorrenciaBean.class);

        if (null != id && id > 0) {
            q1.eq("id", id);
            q2.eq("id", id);
            json.setId(id.toString());
        }

        if (null != idcliente && idcliente > 0) {
            q1.eq("cliente.idcliente", idcliente);
            q2.eq("cliente.idcliente", idcliente);
            json.setIdcliente(idcliente.toString());
        }

        if (null != descricao && !descricao.isEmpty()) {
            q1.ilikeAnyWhere("descricao", descricao);
            q2.ilikeAnyWhere("descricao", descricao);
            json.setDescricao(descricao);
        }

        if (null != idusuario && idusuario > 0) {
            q1.eq("usuarioCriacao.idusuario", idusuario);
            q2.eq("usuarioCriacao.idusuario", idusuario);
            json.setIdusuario(idusuario.toString());
        }

        if (null != idprioridade && idprioridade > 0) {
            q1.eq("ocorrenciaPrioridade.id", idprioridade);
            q2.eq("ocorrenciaPrioridade.id", idprioridade);
            json.setIdprioridade(idprioridade.toString());
        }

        if (null != idstatus && idstatus > 0) {
            final List<Integer> id_list = new ArrayList<Integer>();

            repository.query("select ocorrencia as id , '' as param from ocorrencia_usuario where ocorrencia_status = " + idstatus.toString()).executeSQL(new Each() {
                public Integer id;
                public String param;

                @Override
                public void each() {
                    id_list.add(id);
                }
            });
            
            if(null != id_list && !id_list.isEmpty()) {
                q1.in("id", id_list.toArray(new Integer[id_list.size()]));
                q2.in("id", id_list.toArray(new Integer[id_list.size()]));
            } else {
                q2.eq("id", 0);
            }
            
            json.setIdstatus(idstatus.toString());
        }
        
        json.setCount(q1.count().intValue());
        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();


        json.setPage(page);
        json.setSize(size);

        List<OcorrenciaDTO> rowsList = new ArrayList<OcorrenciaDTO>();
        for (OcorrenciaBean bean : lista) {

            OcorrenciaDTO dto = new OcorrenciaDTO();

            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            dto.setCliente(bean.getCliente().getNome());
            dto.setIdcliente(bean.getCliente().getIdcliente().toString());
            dto.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataCadastro()));
            dto.setDataResolucao(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataResolucao()));
            dto.setOcorrenciaPrioridade(bean.getOcorrenciaPrioridade().getDescricao());
            dto.setUsuario(bean.getUsuarioCriacao().getNome());
            dto.setIdusuario(bean.getUsuarioCriacao().getIdusuario().toString());
            dto.setOcorrenciaOrigem(bean.getOcorrenciaOrigem().getDescricao());

            if (null != bean.getOcorrenciaProblema() && bean.getOcorrenciaProblema() > 0) {
                OcorrenciaProblemaBean ob = repository.find(OcorrenciaProblemaBean.class, bean.getOcorrenciaProblema());
                dto.setOcorrenciaProblema(ob.getDescricao());
            }

            if (null != bean.getOcorrenciaSolucao() && bean.getOcorrenciaSolucao() > 0) {
                OcorrenciaSolucaoBean ob = repository.find(OcorrenciaSolucaoBean.class, bean.getOcorrenciaSolucao());
                dto.setOcorrenciaSolucao(ob.getDescricao());
            }

            OcorrenciaUsuarioBean oub = repository.query(OcorrenciaUsuarioBean.class).eq("ocorrenciaBean.id", bean.getId()).findOne();
            if (null != oub) {
                dto.setStatusNome(oub.getOcorrenciaStatus().getDescricao());
                dto.setStatusCor(oub.getOcorrenciaStatus().getCor());

            }
            dto.setPrioridade(bean.getOcorrenciaPrioridade().getDescricao());
            dto.setIdprioridade(bean.getOcorrenciaPrioridade().getId().toString());

            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public OcorrenciaDTO dto(Integer id) {
        OcorrenciaBean bean = repository.find(OcorrenciaBean.class, id);

        OcorrenciaDTO dto = new OcorrenciaDTO();

        dto.setId(Utilities.leftPad(bean.getId()));
        dto.setDescricao(bean.getDescricao());
        dto.setCliente(bean.getCliente().getNome());
        dto.setIdcliente(bean.getCliente().getIdcliente().toString());
        dto.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataCadastro()));
        dto.setDataResolucao(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataResolucao()));
        dto.setOcorrenciaPrioridade(bean.getOcorrenciaPrioridade().getDescricao());
        dto.setUsuario(bean.getUsuarioCriacao().getNome());
        dto.setIdusuario(bean.getUsuarioCriacao().getIdusuario().toString());
        dto.setOcorrenciaOrigem(bean.getOcorrenciaOrigem().getDescricao());

        if (null != bean.getOcorrenciaProblema() && bean.getOcorrenciaProblema() > 0) {
            OcorrenciaProblemaBean ob = repository.find(OcorrenciaProblemaBean.class, bean.getOcorrenciaProblema());
            dto.setOcorrenciaProblema(ob.getDescricao());
        }

        if (null != bean.getOcorrenciaSolucao() && bean.getOcorrenciaSolucao() > 0) {
            OcorrenciaSolucaoBean ob = repository.find(OcorrenciaSolucaoBean.class, bean.getOcorrenciaSolucao());
            dto.setOcorrenciaSolucao(ob.getDescricao());
        }

        OcorrenciaUsuarioBean oub = repository.query(OcorrenciaUsuarioBean.class).eq("ocorrenciaBean.id", bean.getId()).findOne();
        if (null != oub) {
            dto.setStatusNome(oub.getOcorrenciaStatus().getDescricao());
            dto.setStatusCor(oub.getOcorrenciaStatus().getCor());

        }
        dto.setPrioridade(bean.getOcorrenciaPrioridade().getDescricao());
        dto.setIdprioridade(bean.getOcorrenciaPrioridade().getId().toString());
        return dto;
    }

    public OcorrenciaBean bean(Integer id) {
        return repository.find(OcorrenciaBean.class, id);
    }

    public List<ClienteBean> clienteList() {
        return repository.query(ClienteBean.class).orderAsc("nome").findAll();
    }

    public List<OcorrenciaOrigemBean> ocorrenciaOrigemList() {
        return repository.query(OcorrenciaOrigemBean.class).findAll();
    }

    public List<OcorrenciaPrioridadeBean> ocorrenciaPrioridadeList() {
        return repository.query(OcorrenciaPrioridadeBean.class).findAll();
    }

    public List<OcorrenciaProblemaBean> ocorrenciaProblemaList() {
        return repository.query(OcorrenciaProblemaBean.class).findAll();
    }

    public List<OcorrenciaSolucaoBean> ocorrenciaSolucaoList() {
        return repository.query(OcorrenciaSolucaoBean.class).findAll();
    }

    public List<OcorrenciaStatusBean> ocorrenciaStatusList() {
        return repository.query(OcorrenciaStatusBean.class).findAll();
    }

    public List<UsuarioBean> usuarioList() {
        return repository.query(UsuarioBean.class).findAll();
    }

    public void salvar(OcorrenciaBean bean, Integer idstatus) {
        if (null == bean) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Os dados não podem ser nulos!")).recursive().serialize();
            return;
        }
        
        if (null == bean.getCliente() || null == bean.getCliente().getIdcliente() || bean.getCliente().getIdcliente() <= 0 ) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Selecione um cliente!")).recursive().serialize();
            return;
        }

        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (null == bean.getOcorrenciaProblema()) {
                bean.setOcorrenciaProblema(0);
            }
            if (null == bean.getOcorrenciaSolucao()) {
                bean.setOcorrenciaSolucao(0);
            }

            if (bean != null && bean.getId() != null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
                repository.query("delete from ocorrencia_usuario where ocorrencia = " + bean.getId()).executeSQLCommand2();
            } else {
                bean.setDataCadastro(new Date());
                repository.save(bean);
            }

            OcorrenciaUsuarioBean bean2 = new OcorrenciaUsuarioBean();
            bean2.setOcorrenciaBean(bean);
            bean2.setOcorrenciaStatus(new OcorrenciaStatusBean(idstatus));
            bean2.setUsuario(sessionUsuario.getUsuarioBean());
            repository.save(bean2);

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

            List<OcorrenciaUsuarioBean> lista = repository.query(OcorrenciaUsuarioBean.class).eq("ocorrenciaBean.id", id).findAll();
            for (OcorrenciaUsuarioBean bbean : lista) {
                repository.delete(bbean);
            }

            OcorrenciaBean bean = repository.marge((OcorrenciaBean) repository.find(OcorrenciaBean.class, id));
            repository.delete(bean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Orrencia removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a ocorrencia!")).recursive().serialize();
        }
    }

    public List<OcorrenciaFormDTO> listaUsuario(Integer id) {
        List<OcorrenciaFormDTO> ocorrenciaFormDTOList = new ArrayList<OcorrenciaFormDTO>();
        try {

            List<UsuarioBean> usuarioBeanList = repository.query(UsuarioBean.class).findAll();

            for (UsuarioBean item : usuarioBeanList) {
                OcorrenciaFormDTO dto = new OcorrenciaFormDTO();
                if (id == null) {
                    dto.setTem(false);
                    dto.setUsuario(item);
                    dto.setOcorrenciaUsuario(null);
                } else {
                    if (repository.query(OcorrenciaUsuarioBean.class).eq("usuario.idusuario", item.getIdusuario()).eq("ocorrenciaBean.id", id).count() > 0) {
                        dto.setTem(true);
                        dto.setUsuario(item);
                        dto.setOcorrenciaUsuario((OcorrenciaUsuarioBean) repository.query(OcorrenciaUsuarioBean.class).eq("usuario.idusuario", item.getIdusuario()).eq("ocorrenciaBean.id", id).findOne());
                    } else {
                        dto.setTem(false);
                        dto.setUsuario(item);
                        dto.setOcorrenciaUsuario(null);
                    }
                }

                ocorrenciaFormDTOList.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a ocorrencia!")).recursive().serialize();
        }
        return ocorrenciaFormDTOList;
    }

    public OcorrenciaUsuarioBean ocorrenciaUsuario(Integer id) {
        return repository.query(OcorrenciaUsuarioBean.class).eq("ocorrenciaBean.id", id).findOne();
    }

    public void totalPorStatus() {
        List<OcorrenciaStatusBean> lista = repository.query(OcorrenciaStatusBean.class).findAll();
        List<OcorrenciaRelatorioRapidoDTO> lista2 = new ArrayList<OcorrenciaRelatorioRapidoDTO>();

        for (OcorrenciaStatusBean item : lista) {
            Long total = repository.query(OcorrenciaUsuarioBean.class).eq("ocorrenciaStatus.id", item.getId()).count();
            OcorrenciaRelatorioRapidoDTO dto = new OcorrenciaRelatorioRapidoDTO();
            dto.setOcorrenciaStatus(item);
            dto.setTotal(total);
            lista2.add(dto);
        }

        result.include("ocorrenciaRelatorioRapidoList", lista2);
    }

    public List<OcorrenciaDTO> beanList2() {
        List<OcorrenciaBean> lista = new ArrayList<OcorrenciaBean>();
        List<OcorrenciaDTO> lista2 = new ArrayList<OcorrenciaDTO>();
        List<OcorrenciaUsuarioBean> ocorrenciaUsuarioBeanList = repository.query(OcorrenciaUsuarioBean.class).eq("usuario.idusuario", sessionUsuario.getUsuarioBean().getIdusuario()).findAll();
        for (OcorrenciaUsuarioBean item : ocorrenciaUsuarioBeanList) {
            lista.add(item.getOcorrenciaBean());
        }
        for (OcorrenciaBean bean : lista) {
            OcorrenciaDTO dto = new OcorrenciaDTO();

            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            dto.setCliente(bean.getCliente().getNome());
            dto.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataCadastro()));
            dto.setDataResolucao(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataResolucao()));
            dto.setOcorrenciaPrioridade(bean.getOcorrenciaPrioridade().getDescricao());
            dto.setUsuario(bean.getUsuarioCriacao().getNome());
            dto.setOcorrenciaOrigem(bean.getOcorrenciaOrigem().getDescricao());

            if (null != bean.getOcorrenciaProblema() && bean.getOcorrenciaProblema() > 0) {
                OcorrenciaProblemaBean ob = repository.find(OcorrenciaProblemaBean.class, bean.getOcorrenciaProblema());
                dto.setOcorrenciaProblema(ob.getDescricao());
            }

            if (null != bean.getOcorrenciaSolucao() && bean.getOcorrenciaSolucao() > 0) {
                OcorrenciaSolucaoBean ob = repository.find(OcorrenciaSolucaoBean.class, bean.getOcorrenciaSolucao());
                dto.setOcorrenciaSolucao(ob.getDescricao());
            }

            OcorrenciaUsuarioBean oub = repository.query(OcorrenciaUsuarioBean.class).eq("ocorrenciaBean.id", bean.getId()).findOne();
            dto.setStatusNome(oub.getOcorrenciaStatus().getDescricao());
            dto.setStatusCor(oub.getOcorrenciaStatus().getCor());
            dto.setPrioridade(bean.getOcorrenciaPrioridade().getDescricao());

            lista2.add(dto);
        }
        return lista2;
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        return clienteBeanList;
    }

    public List<OcorrenciaPrioridadeBean> prioridadeBeanList() {
        List<OcorrenciaPrioridadeBean> prioridadeBeanList = repository.query(OcorrenciaPrioridadeBean.class).findAll();
        return prioridadeBeanList;
    }

    public List<OcorrenciaStatusBean> statusBeanList() {
        List<OcorrenciaStatusBean> statusBeanList = repository.query(OcorrenciaStatusBean.class).findAll();
        return statusBeanList;
    }

    public List<UsuarioBean> usuarioBeanList() {
        List<UsuarioBean> usuarioBeanList = repository.query(UsuarioBean.class).findAll();
        return usuarioBeanList;
    }

    public void loadCliente(Integer idcliente) {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("id", idcliente).or().eq("parente", idcliente).findAll();
        result.use(Results.json()).withoutRoot().from(clienteBeanList).recursive().serialize();
    }
}
