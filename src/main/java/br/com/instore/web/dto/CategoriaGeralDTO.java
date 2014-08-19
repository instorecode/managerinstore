package br.com.instore.web.dto;

public class CategoriaGeralDTO extends AbstractDTO {
    private String id;
    private String usuario;
    private String nome;

    public CategoriaGeralDTO() {
    }

    public CategoriaGeralDTO(String id, String usuario, String nome) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
