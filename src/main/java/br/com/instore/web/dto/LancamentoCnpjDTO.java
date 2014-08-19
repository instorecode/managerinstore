package br.com.instore.web.dto;

public class LancamentoCnpjDTO extends AbstractDTO {
    private String id;
    private String nome;
    private String saldoDisponivel;
    private String cnpj;

    public LancamentoCnpjDTO() {
    }

    public LancamentoCnpjDTO(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(String saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
