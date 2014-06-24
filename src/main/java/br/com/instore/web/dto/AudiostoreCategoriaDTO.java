package br.com.instore.web.dto;

import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AudiostoreCategoriaDTO extends AbstractDTO {

    private Integer codigo;
    private String categoria;
    private String dataInicio;
    private String dataFinal;
    private String tipo;
    private String tempo;
    private String clienteNome;


    public AudiostoreCategoriaDTO() {
    }

    public AudiostoreCategoriaDTO(Integer codigo, String categoria, String dataInicio, String dataFinal, String tipo, String tempo, String clienteNome) {
        this.codigo = codigo;
        this.categoria = categoria;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.tipo = tipo;
        this.tempo = tempo;
        this.clienteNome = clienteNome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
}
