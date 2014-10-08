/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.instore.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class AudiostoreGravadoraJSON {
    
    private Integer page;
    private Integer size;
    private List<AudiostoreGravadoraDTO> rows = new ArrayList<AudiostoreGravadoraDTO>();
    private Integer id;
    private String nome;

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

    public List<AudiostoreGravadoraDTO> getRows() {
        return rows;
    }

    public void setRows(List<AudiostoreGravadoraDTO> rows) {
        this.rows = rows;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
