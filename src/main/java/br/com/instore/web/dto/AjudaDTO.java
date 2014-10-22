
package br.com.instore.web.dto;

public class AjudaDTO extends AbstractDTO{
    
    private Integer id;
    private String titulo;
    private Integer idfuncionalidade;
    private String texto;
    private String nome;
    
    public AjudaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdfuncionalidade() {
        return idfuncionalidade;
    }

    public void setIdfuncionalidade(Integer idfuncionalidade) {
        this.idfuncionalidade = idfuncionalidade;
    }
    
    
}
