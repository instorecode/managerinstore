package br.com.instore.web.dto;

import java.util.ArrayList;
import java.util.List;


public class OcorrenciaSituacaoJSON {
    private Integer page;
    private Integer size;
    private List<OcorrenciaStatusDTO> rows = new ArrayList<OcorrenciaStatusDTO>();
    private String id = null;
    private String descricao = null;
    private String cor = null;

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

    public List<OcorrenciaStatusDTO> getRows() {
        return rows;
    }

    public void setRows(List<OcorrenciaStatusDTO> rows) {
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
    
    
}
