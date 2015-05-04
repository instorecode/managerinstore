package br.com.instore.web.dto;

import java.util.ArrayList;
import java.util.List;

public class FuncionalidadeTreeDTO {
    private Integer idfuncionalidade;
    private String nome;
    private Boolean perfilTem;
    private Integer parente;
    private List<FuncionalidadeTreeDTO> filhos = new ArrayList<FuncionalidadeTreeDTO>();

    public Integer getIdfuncionalidade() {
        return idfuncionalidade;
    }

    public void setIdfuncionalidade(Integer idfuncionalidade) {
        this.idfuncionalidade = idfuncionalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getPerfilTem() {
        return perfilTem;
    }

    public void setPerfilTem(Boolean perfilTem) {
        this.perfilTem = perfilTem;
    }

    public Integer getParente() {
        return parente;
    }

    public void setParente(Integer parente) {
        this.parente = parente;
    }

    public List<FuncionalidadeTreeDTO> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<FuncionalidadeTreeDTO> filhos) {
        this.filhos = filhos;
    } 
}
