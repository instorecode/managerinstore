
package br.com.instore.web.dto;

import java.util.ArrayList;
import java.util.List;


public class VozJSON {
    
    private Integer page;
    private Integer size;
    private List<VozDTO> rows = new ArrayList<VozDTO>();
    private Integer idvoz = null;
    private String clienteNome = null;
    private String genero = null;
    private String tipo = null;
    private String nome = null;
    private String email = null;
    private String tel = null;

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

    public List<VozDTO> getRows() {
        return rows;
    }

    public void setRows(List<VozDTO> rows) {
        this.rows = rows;
    }

    public Integer getIdvoz() {
        return idvoz;
    }

    public void setIdvoz(Integer idvoz) {
        this.idvoz = idvoz;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    
    
    
}
