package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.VersaoJSON;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import br.com.instore.core.orm.bean.VersaoBean;
import br.com.instore.core.orm.bean.ProjetoBean;
import br.com.instore.core.orm.Query;
import br.com.instore.web.dto.VersaoDTO;
import java.text.SimpleDateFormat;

@Component
@RequestScoped
public class RequestVersao implements java.io.Serializable {

    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;

    public RequestVersao(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public void beanList(Integer page, Integer rows, Integer id, Date dataCriacao, String nome, String descricao, Integer idProjeto, String linkSvn, Boolean download) {
        VersaoJSON json = new VersaoJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<VersaoBean> lista = new ArrayList<VersaoBean>();

        Query q1 = repository.query(VersaoBean.class);
        Query q2 = repository.query(VersaoBean.class);

        if (null != id && id > 0) {
            q1.eq("id", id);
            q2.eq("id", id);
            json.setId(id.toString());
        }

        if (null != dataCriacao) {
            q1.eq("dataCriacao", dataCriacao);
            q2.eq("dataCriacao", dataCriacao);
            json.setDataCriacao(dataCriacao.toString());
        }

        if (null != nome && !nome.isEmpty()) {
            q1.likeAnyWhere("nome", nome);
            q2.likeAnyWhere("nome", nome);
            json.setNome(nome);
        }

        if (null != descricao && !descricao.isEmpty()) {
            q1.likeAnyWhere("descricao", descricao);
            q2.likeAnyWhere("descricao", descricao);
            json.setDescricao(descricao);
        }

        if (null != idProjeto && idProjeto > 0) {
            q1.eq("idProjeto", idProjeto);
            q2.eq("idProjeto", idProjeto);
            json.setIdProjeto(id.toString());
        }

        if (null != linkSvn && !linkSvn.isEmpty()) {
            q1.likeAnyWhere("linkSvn", linkSvn);
            q2.likeAnyWhere("linkSvn", linkSvn);
            json.setLinkSvn(linkSvn);
        }

        if (null != download) {
            q1.eq("download", download);
            q2.eq("download", download);
            json.setDownload(download.toString());
        }

        json.setCount(q1.count().intValue());
        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();

        json.setPage(page);
        json.setSize(size);

        List<VersaoDTO> rowsList = new ArrayList<VersaoDTO>();
        for (VersaoBean bean : lista) {
            VersaoDTO dto = new VersaoDTO();
            dto.setId(bean.getId().toString());
            dto.setDataCriacao(bean.getDataCriacao().toString());
            dto.setNome(bean.getNome());
            dto.setDescricao(bean.getDescricao());
            dto.setIdProjeto(bean.getIdProjeto().toString());
            dto.setLinkSvn(bean.getLinkSvn());
            dto.setDownload(bean.getDownload().toString());
            rowsList.add(dto);
        }

        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public VersaoDTO bean(Integer id) {
        VersaoBean bean = repository.find(VersaoBean.class, id);
        VersaoDTO dto = new VersaoDTO();
        dto.setId(bean.getId().toString());
        dto.setDataCriacao(bean.getDataCriacao().toString());
        dto.setNome(bean.getNome());
        dto.setDescricao(bean.getDescricao());
        dto.setIdProjeto(bean.getIdProjeto().toString());
        dto.setLinkSvn(bean.getLinkSvn());
        dto.setDownload(bean.getDownload().toString());
        return dto;
    }

    public void salvar(VersaoBean bean) {
        
    }

    public List<ProjetoBean> listaProjetos() {
        List<ProjetoBean> lista = repository.query(ProjetoBean.class).findAll();
        System.out.println(lista);
        return lista;
    }

    public String criarNomeVersao(String nome) {
        String nomeVersao = null;        
        String code = nome.replaceAll("\\s+", "").trim().toUpperCase();
        if (null != code && code.length() > 0) {
            Integer halfStringLength = new Double(Math.ceil(code.length() / (double) 2)).intValue();
            String firstLetter = code.substring(0, 1);
            String secondLetter = code.substring(halfStringLength - 1, halfStringLength);
            String finalLetter = code.substring(code.length() - 1);
            String data = "D".concat(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            String hora = "H".concat(new SimpleDateFormat("HHmmss").format(new Date()));   
            nomeVersao = firstLetter.concat(secondLetter).concat(finalLetter).concat(".").concat(data).concat(".").concat(hora);            
        }
        return nomeVersao;
    }
}
