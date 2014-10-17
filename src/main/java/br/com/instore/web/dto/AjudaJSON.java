
package br.com.instore.web.dto;

import java.util.ArrayList;
import java.util.List;

public class AjudaJSON {
    
    private Integer page;
    private Integer size;
    private List<AjudaDTO> rows = new ArrayList<AjudaDTO>();
    private Integer id = null;
    private String titulo = null;
    private String texto  = null;
    private String idfuncionalidade = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<AjudaDTO> getRows() {
        return rows;
    }

    public void setRows(List<AjudaDTO> rows) {
        this.rows = rows;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getIdfuncionalidade() {
        return idfuncionalidade;
    }

    public void setIdfuncionalidade(String idfuncionalidade) {
        this.idfuncionalidade = idfuncionalidade;
    }
    
    
    
}
