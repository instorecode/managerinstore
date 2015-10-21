package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.OcorrenciaStatusBean;
import br.com.instore.core.orm.bean.ProdutoBean;
import br.com.instore.core.orm.bean.ProjetoBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OcorrenciaSituacaoJSON;
import br.com.instore.web.dto.OcorrenciaStatusDTO;
import br.com.instore.web.dto.ProjetoDTO;
import br.com.instore.web.dto.ProjetoJSON;
import br.com.instore.web.tools.AjaxResult;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

@Component
@RequestScoped
public class RequestProjeto implements java.io.Serializable {

    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;

    public RequestProjeto(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public void beanList(Integer page, Integer rows, Integer id, String descricao, String nome, String idUsuario, String linkDocumentacao, String dataCriacao) {
        ProjetoJSON json = new ProjetoJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<ProjetoBean> lista = new ArrayList<ProjetoBean>();

        Query q1 = repository.query(ProjetoBean.class);
        Query q2 = repository.query(ProjetoBean.class);

        if (null != id && id > 0) {
            q1.eq("id", id);
            q2.eq("id", id);
            json.setId(id.toString());
        }

        if (null != descricao && !descricao.isEmpty()) {
            q1.ilikeAnyWhere("descricao", descricao);
            q2.ilikeAnyWhere("descricao", descricao);
            json.setDescricao(descricao);
        }

        if (null != nome && !nome.isEmpty()) {
            q1.ilikeAnyWhere("nome", nome);
            q2.ilikeAnyWhere("nome", nome);
            json.setNome(nome);
        }

        if (null != idUsuario && !idUsuario.isEmpty()) {
            q1.eq("idUsuario", idUsuario);
            q2.eq("idUsuario", idUsuario);
            json.setIdUsuario(idUsuario);
        }

        if (null != linkDocumentacao && !linkDocumentacao.isEmpty()) {
            q1.ilikeAnyWhere("linkDocumentacao", linkDocumentacao);
            q2.ilikeAnyWhere("linkDocumentacao", linkDocumentacao);
            json.setLinkDocumentacao(linkDocumentacao);
        }

        json.setCount(q1.count().intValue());
        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();

        json.setPage(page);
        json.setSize(size);

        List<ProjetoDTO> rowsList = new ArrayList<ProjetoDTO>();
        for (ProjetoBean bean : lista) {
            ProjetoDTO dto = new ProjetoDTO();
            dto.setId(bean.getId().toString());
            dto.setDescricao(bean.getDescricao());
            dto.setNome(bean.getNome());
            dto.setLinkDocumentacao(bean.getLinkDocumentacao());
            dto.setDataCriacao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getDataCriacao()));
            dto.setIdUsuario(bean.getIdUsuario().toString());
            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public ProjetoDTO bean(Integer id) {
        ProjetoBean bean = repository.find(ProjetoBean.class, id);

        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(bean.getId().toString());
        dto.setDescricao(bean.getDescricao());
        dto.setNome(bean.getNome());
        dto.setLinkDocumentacao(bean.getLinkDocumentacao());
        dto.setDataCriacao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getDataCriacao()));
        dto.setIdUsuario(bean.getIdUsuario().toString());
        return dto;
    }

    public static void main(String[] args) {

    }

    public void salvar(ProjetoBean bean, String dataCriacao) {
        try {
            String urlRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            String nomeProjeto = bean.getNome().trim().replaceAll("\n", "").replaceAll("\\s", "");

            if (nomeProjeto.matches("[0-9]+") || nomeProjeto.length() < 3) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O nome do projeto não pode ser composto por apenas números!")).recursive().serialize();
                return;
            }

            if (!bean.getLinkDocumentacao().matches(urlRegex)) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Url da documentação não é válida!")).recursive().serialize();
                return;
            }

            Query q = repository.query(ProjetoBean.class);
            q.eq("nome", nomeProjeto);
            if (null != bean.getId()) {
                q.not().eq("id", bean.getId());
            }
            
            if (q.count() > 0) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O nome do projeto já está em uso!")).recursive().serialize();
                return;
            }
            
            if (null != dataCriacao) {
                try {
                    bean.setDataCriacao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dataCriacao));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (null == bean.getDataCriacao()) {
                bean.setDataCriacao(new Date());
            }

            bean.setIdUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (bean != null && bean.getId() != null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
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
            ProjetoBean projetoBean = repository.find(ProjetoBean.class, id);
            repository.delete(projetoBean);
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Projeto removido com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover o projeto!")).recursive().serialize();
        }
    }
}
