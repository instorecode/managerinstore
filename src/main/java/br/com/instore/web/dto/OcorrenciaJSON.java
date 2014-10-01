package br.com.instore.web.dto;

import br.com.instore.web.dto.OcorrenciaOrigemDTO;
import java.util.ArrayList;
import java.util.List;


public class OcorrenciaJSON {
    private Integer page;
    private Integer size;
    private List<OcorrenciaDTO> rows = new ArrayList<OcorrenciaDTO>();
    private Integer id;
    private String descricao;
    private String dataCadastro;
    private String ocorrenciaProblema;
    private String ocorrenciaSolucao;    
    private String ocorrenciaOrigem;
    private String dataResolucao;
    private String usuarioCriacao;
    private String ocorrenciaPrioridade;
    private String cliente;
    private String statusNome;
    private String statusCor;
    private String statusUsuarioNome;
    private String prioridade;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<OcorrenciaDTO> getRows() {
        return rows;
    }

    public void setRows(List<OcorrenciaDTO> rows) {
        this.rows = rows;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getStatusNome() {
        return statusNome;
    }

    public void setStatusNome(String statusNome) {
        this.statusNome = statusNome;
    }

    public String getStatusCor() {
        return statusCor;
    }

    public void setStatusCor(String statusCor) {
        this.statusCor = statusCor;
    }

    public String getStatusUsuarioNome() {
        return statusUsuarioNome;
    }

    public void setStatusUsuarioNome(String statusUsuarioNome) {
        this.statusUsuarioNome = statusUsuarioNome;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

   
}
