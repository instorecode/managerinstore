package br.com.instore.web.dto;

import br.com.instore.web.dto.OcorrenciaOrigemDTO;
import java.util.ArrayList;
import java.util.List;


public class OcorrenciaPrioridadeJSON {
    private Integer page;
    private Integer size;
    private List<OcorrenciaPrioridadeDTO> rows = new ArrayList<OcorrenciaPrioridadeDTO>();
    private Integer id = null;
    private String descricao = null;
    private Integer nivel = null;
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

    public List<OcorrenciaPrioridadeDTO> getRows() {
        return rows;
    }

    public void setRows(List<OcorrenciaPrioridadeDTO> rows) {
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
