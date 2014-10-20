package br.com.instore.web.dto;

public class OcorrenciaDTO  extends AbstractDTO {
    private String id;
    private String descricao;
    private String dataCadastro;
    private String ocorrenciaProblema;
    private String ocorrenciaSolucao;    
    private String ocorrenciaOrigem;
    private String dataResolucao;
    private String usuario;
    private String idusuario;
    private String ocorrenciaPrioridade;
    private String cliente;
    private String idcliente;
    private String statusNome;
    private String statusCor;
    private String idstatus;
    private String prioridade;
    private String idprioridade;

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
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

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
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

    public String getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(String idstatus) {
        this.idstatus = idstatus;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getIdprioridade() {
        return idprioridade;
    }

    public void setIdprioridade(String idprioridade) {
        this.idprioridade = idprioridade;
    }
}
