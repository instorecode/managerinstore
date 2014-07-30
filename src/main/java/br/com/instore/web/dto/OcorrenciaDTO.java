package br.com.instore.web.dto;

import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.OcorrenciaOrigemBean;
import br.com.instore.core.orm.bean.OcorrenciaPrioridadeBean;
import br.com.instore.core.orm.bean.OcorrenciaProblemaBean;
import br.com.instore.core.orm.bean.OcorrenciaSolucaoBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class OcorrenciaDTO  extends AbstractDTO {
    private String id;
    private String descricao;
    private String dataCadastro;
    private String ocorrenciaProblema;
    private String ocorrenciaSolucao;    
    private String ocorrenciaOrigem;
    private String dataResolucao;
    private String usuarioCriacao;
    private String ocorrenciaPrioridade;
    private String cliente;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getOcorrenciaProblema() {
        return ocorrenciaProblema;
    }

    public void setOcorrenciaProblema(String ocorrenciaProblema) {
        this.ocorrenciaProblema = ocorrenciaProblema;
    }

    public String getOcorrenciaSolucao() {
        return ocorrenciaSolucao;
    }

    public void setOcorrenciaSolucao(String ocorrenciaSolucao) {
        this.ocorrenciaSolucao = ocorrenciaSolucao;
    }

    public String getOcorrenciaOrigem() {
        return ocorrenciaOrigem;
    }

    public void setOcorrenciaOrigem(String ocorrenciaOrigem) {
        this.ocorrenciaOrigem = ocorrenciaOrigem;
    }

    public String getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(String dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    public String getUsuarioCriacao() {
        return usuarioCriacao;
    }

    public void setUsuarioCriacao(String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    public String getOcorrenciaPrioridade() {
        return ocorrenciaPrioridade;
    }

    public void setOcorrenciaPrioridade(String ocorrenciaPrioridade) {
        this.ocorrenciaPrioridade = ocorrenciaPrioridade;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
