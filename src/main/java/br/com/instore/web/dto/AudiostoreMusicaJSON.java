package br.com.instore.web.dto;

import java.util.ArrayList;
import java.util.List;


public class AudiostoreMusicaJSON {
    private Integer page;
    private Integer size;
    private List<AudiostoreMusicaDTO> rows = new ArrayList<AudiostoreMusicaDTO>();
    private Integer id;
    private Integer idcliente;
    private String nomeCliente;
    private String nome;
    private Integer idcategoria;
    private String nomeCategoria;
    private Integer idCategoriaArquivo;
    private String nomeCategoriaArquivo;
    private String letra;

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

    public List<AudiostoreMusicaDTO> getRows() {
        return rows;
    }

    public void setRows(List<AudiostoreMusicaDTO> rows) {
        this.rows = rows;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Integer getIdCategoriaArquivo() {
        return idCategoriaArquivo;
    }

    public void setIdCategoriaArquivo(Integer idCategoriaArquivo) {
        this.idCategoriaArquivo = idCategoriaArquivo;
    }

    public String getNomeCategoriaArquivo() {
        return nomeCategoriaArquivo;
    }

    public void setNomeCategoriaArquivo(String nomeCategoriaArquivo) {
        this.nomeCategoriaArquivo = nomeCategoriaArquivo;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }
}
