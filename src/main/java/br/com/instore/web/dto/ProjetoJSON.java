package br.com.instore.web.dto;

import java.util.ArrayList;
import java.util.List;

public class ProjetoJSON {
    private Integer page;
    private Integer size;
    private List<ProjetoDTO> rows = new ArrayList<ProjetoDTO>();
    private String id = null;
    private String descricao = null;
    private String nome = null;
    private String dataCriacao = null;
    private String linkDocumentacao = null;
    private String idUsuario = null;    
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

    public List<ProjetoDTO> getRows() {
        return rows;
    }

    public void setRows(List<ProjetoDTO> rows) {
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getLinkDocumentacao() {
        return linkDocumentacao;
    }

    public void setLinkDocumentacao(String linkDocumentacao) {
        this.linkDocumentacao = linkDocumentacao;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    
  

}
