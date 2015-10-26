package br.com.instore.web.dto;

import java.util.List;
import java.util.ArrayList;

public class VersaoJSON {

    private Integer page;
    private Integer size;
    private List<VersaoDTO> rows = new ArrayList<VersaoDTO>();
    private String id = null;
    private String dataCriacao = null;
    private String nome = null;
    private String descricao = null;
    private String idProjeto = null;
    private String linkSvn = null;
    private String download = null;
    private Integer count;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<VersaoDTO> getRows() {
        return rows;
    }

    public void setRows(List<VersaoDTO> rows) {
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(String idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getLinkSvn() {
        return linkSvn;
    }

    public void setLinkSvn(String linkSvn) {
        this.linkSvn = linkSvn;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
    

}
