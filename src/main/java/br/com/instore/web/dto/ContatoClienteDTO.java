package br.com.instore.web.dto;

public class ContatoClienteDTO extends AbstractDTO {
    private String idcontatoCliente;
    private String clienteNome;
    private String clienteNomeFantasia;
    private String principal;
    private String email;
    private String tel;
    private String setor;
    private String nome;

    public ContatoClienteDTO() {
    }

    public ContatoClienteDTO(String idcontatoCliente, String clienteNome, String clienteNomeFantasia, String principal, String email, String tel, String setor) {
        this.idcontatoCliente = idcontatoCliente;
        this.clienteNome = clienteNome;
        this.clienteNomeFantasia = clienteNomeFantasia;
        this.principal = principal;
        this.email = email;
        this.tel = tel;
        this.setor = setor;
    }

    public String getIdcontatoCliente() {
        return idcontatoCliente;
    }

    public void setIdcontatoCliente(String idcontatoCliente) {
        this.idcontatoCliente = idcontatoCliente;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getClienteNomeFantasia() {
        return clienteNomeFantasia;
    }

    public void setClienteNomeFantasia(String clienteNomeFantasia) {
        this.clienteNomeFantasia = clienteNomeFantasia;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
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

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }   
}