
package br.com.instore.web.dto;

public class AjudaDTO extends AbstractDTO{
    
    private Integer id;
    private String titulo;
    private String idfuncionalidade;
    private String texto;

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


    public String getIdfuncionalidade() {
        return idfuncionalidade;
    }

    public void setIdfuncionalidade(String idfuncionalidade) {
        this.idfuncionalidade = idfuncionalidade;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    
}
