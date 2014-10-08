package br.com.instore.web.dto;

public class AudiostoreGravadoraDTO extends AbstractDTO {
    private Integer id;
    private String nome;

    public AudiostoreGravadoraDTO() {
    }

    public AudiostoreGravadoraDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
