package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
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

    public List<OcorrenciaDTO> beanList() {
        List<OcorrenciaBean> lista = new ArrayList<OcorrenciaBean>();
        List<OcorrenciaDTO> lista2 = new ArrayList<OcorrenciaDTO>();
        lista = repository.query(OcorrenciaBean.class).findAll();
        for (OcorrenciaBean bean : lista) {
            OcorrenciaDTO dto = new OcorrenciaDTO();

            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            dto.setCliente(bean.getCliente().getNome());
            dto.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataCadastro()));
            dto.setDataResolucao(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataResolucao()));
            dto.setOcorrenciaPrioridade(bean.getOcorrenciaPrioridade().getDescricao());
            dto.setUsuarioCriacao(bean.getUsuarioCriacao().getNome());
            dto.setOcorrenciaOrigem(bean.getOcorrenciaOrigem().getDescricao());

            if (null != bean.getOcorrenciaProblema() && bean.getOcorrenciaProblema() > 0) {
                OcorrenciaProblemaBean ob = repository.find(OcorrenciaProblemaBean.class, bean.getOcorrenciaProblema());
                dto.setOcorrenciaProblema(ob.getDescricao());
            }

            if (null != bean.getOcorrenciaSolucao() && bean.getOcorrenciaSolucao() > 0) {
                OcorrenciaSolucaoBean ob = repository.find(OcorrenciaSolucaoBean.class, bean.getOcorrenciaSolucao());
                dto.setOcorrenciaSolucao(ob.getDescricao());
            }
            
            OcorrenciaUsuarioBean oub  = repository.query(OcorrenciaUsuarioBean.class).eq("ocorrenciaBean.id", bean.getId()).findOne();
            dto.setStatusNome(oub.getOcorrenciaStatus().getDescricao());
            dto.setStatusCor(oub.getOcorrenciaStatus().getCor());
            dto.setStatusUsuarioNome(oub.getUsuario().getNome());
            dto.setPrioridade(bean.getOcorrenciaPrioridade().getDescricao());
                    
            
            lista2.add(dto);
        }
        return lista2;
    }

    public OcorrenciaBean bean(Integer id) {
        return repository.find(OcorrenciaBean.class, id);
    }

    public List<ClienteBean> clienteList() {
        return repository.query(ClienteBean.class).findAll();
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
            } else {
                bean.setDataCadastro(new Date());
                repository.save(bean);
            }
            
            List<OcorrenciaUsuarioBean> listaDeletar = repository.query(OcorrenciaUsuarioBean.class).eq("ocorrenciaBean.id", bean.getId()).findAll();
            for (OcorrenciaUsuarioBean item : listaDeletar) {
                repository.delete(item);
            }
            
            OcorrenciaUsuarioBean  bean2 = new OcorrenciaUsuarioBean();
            bean2.setOcorrenciaBean(bean);
            bean2.setOcorrenciaStatus( new OcorrenciaStatusBean(idstatus));
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
            dto.setUsuarioCriacao(bean.getUsuarioCriacao().getNome());
            dto.setOcorrenciaOrigem(bean.getOcorrenciaOrigem().getDescricao());

            if (null != bean.getOcorrenciaProblema() && bean.getOcorrenciaProblema() > 0) {
                OcorrenciaProblemaBean ob = repository.find(OcorrenciaProblemaBean.class, bean.getOcorrenciaProblema());
                dto.setOcorrenciaProblema(ob.getDescricao());
            }

            if (null != bean.getOcorrenciaSolucao() && bean.getOcorrenciaSolucao() > 0) {
                OcorrenciaSolucaoBean ob = repository.find(OcorrenciaSolucaoBean.class, bean.getOcorrenciaSolucao());
                dto.setOcorrenciaSolucao(ob.getDescricao());
            }
            
            OcorrenciaUsuarioBean oub  = repository.query(OcorrenciaUsuarioBean.class).eq("ocorrenciaBean.id", bean.getId()).findOne();
            dto.setStatusNome(oub.getOcorrenciaStatus().getDescricao());
            dto.setStatusCor(oub.getOcorrenciaStatus().getCor());
            dto.setStatusUsuarioNome(oub.getUsuario().getNome());
            dto.setPrioridade(bean.getOcorrenciaPrioridade().getDescricao());
            
            lista2.add(dto);
        }
        return lista2;
    }
}
