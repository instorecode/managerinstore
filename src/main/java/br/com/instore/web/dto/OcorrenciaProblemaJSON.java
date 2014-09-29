package br.com.instore.web.dto;

import br.com.instore.web.dto.OcorrenciaOrigemDTO;
import java.util.ArrayList;
import java.util.List;


public class OcorrenciaProblemaJSON {
    private Integer page;
    private Integer size;
    private List<OcorrenciaProblemaDTO> rows = new ArrayList<OcorrenciaProblemaDTO>();
    private Integer id = null;
    private String descricao = null;

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

    public List<OcorrenciaProblemaDTO> getRows() {
        return rows;
    }

    public void setRows(List<OcorrenciaProblemaDTO> rows) {
        this.rows = rows;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
