package br.com.instore.web.dto;

public class UsuarioDTO extends AbstractDTO {
    private String id;
    private String nome;
    private String dataCadastro;
    private String cpf;
    private String email;
    private String endereco;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String id, String nome, String dataCadastro, String cpf, String email, String endereco) {
        this.id = id;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.cpf = cpf;
        this.email = email;
        this.endereco = endereco;
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

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
