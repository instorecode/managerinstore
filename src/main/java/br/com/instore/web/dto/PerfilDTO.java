package br.com.instore.web.dto;


public class PerfilDTO extends AbstractDTO {
    private String id;
    private String nome;
    private String Funcionalidade;

    public PerfilDTO() {
    }

    public PerfilDTO(String id, String nome, String Funcionalidade) {
        this.id = id;
        this.nome = nome;
        this.Funcionalidade = Funcionalidade;
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

    public String getFuncionalidade() {
        return Funcionalidade;
    }

    public void setFuncionalidade(String Funcionalidade) {
        this.Funcionalidade = Funcionalidade;
    }
}
