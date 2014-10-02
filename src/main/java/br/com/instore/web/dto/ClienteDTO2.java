package br.com.instore.web.dto;

public class ClienteDTO2 extends AbstractDTO {
    private Integer idcliente;
    private Integer parente;
    private String nome;

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Integer getParente() {
        return parente;
    }

    public void setParente(Integer parente) {
        this.parente = parente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
