package br.com.instore.web.dto;

public class VozDTO extends AbstractDTO {
    private Integer idvoz;
    private String clienteNome;
    private String genero;
    private String tipo;
    private String nome;
    private String email;
    private String tel;

    public VozDTO() {
    }

    public VozDTO(Integer idvoz, String clienteNome, String genero, String tipo, String nome, String email, String tel) {
        this.idvoz = idvoz;
        this.clienteNome = clienteNome;
        this.genero = genero;
        this.tipo = tipo;
        this.nome = nome;
        this.email = email;
        this.tel = tel;
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
