package br.com.instore.web.dto;

import java.math.BigInteger;

public class OSFila extends AbstractDTO {

    private String cod;
    private String nome;
    private String dataDist;
    private Integer explodiu;
    private Integer prioridade;
    private Integer situacao;
    private Integer cliente;
    private Integer enviarEmail;
    private Integer avaliacaoCount;
    private String avaliacaoData;
    private String avaliacaoEmail;
    private Integer avaliacaoSituacao;
    private String avaliacaoObs;
    public Integer avaliacaoId;
    public String avaliacaoPrimId;
    public String avaliacaoPrimData;
    public String avaliacaoPrimUs;
    public String avaliacaoSecId;
    public String avaliacaoSecData;
    public String avaliacaoSecUs;

    public OSFila() {
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataDist() {
        return dataDist;
    }

    public void setDataDist(String dataDist) {
        this.dataDist = dataDist;
    }

    public Integer getExplodiu() {
        return explodiu;
    }

    public void setExplodiu(Integer explodiu) {
        this.explodiu = explodiu;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public Integer getSituacao() {
        return situacao;
    }

    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }

    public Integer getEnviarEmail() {
        return enviarEmail;
    }

    public void setEnviarEmail(Integer enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    public Integer getAvaliacaoCount() {
        return avaliacaoCount;
    }

    public void setAvaliacaoCount(Integer avaliacaoCount) {
        this.avaliacaoCount = avaliacaoCount;
    }

    public String getAvaliacaoData() {
        return avaliacaoData;
    }

    public void setAvaliacaoData(String avaliacaoData) {
        this.avaliacaoData = avaliacaoData;
    }

    public String getAvaliacaoEmail() {
        return avaliacaoEmail;
    }

    public void setAvaliacaoEmail(String avaliacaoEmail) {
        this.avaliacaoEmail = avaliacaoEmail;
    }

    public Integer getAvaliacaoSituacao() {
        return avaliacaoSituacao;
    }

    public void setAvaliacaoSituacao(Integer avaliacaoSituacao) {
        this.avaliacaoSituacao = avaliacaoSituacao;
    }

    public String getAvaliacaoObs() {
        return avaliacaoObs;
    }

    public void setAvaliacaoObs(String avaliacaoObs) {
        this.avaliacaoObs = avaliacaoObs;
    }

    public Integer getAvaliacaoId() {
        return avaliacaoId;
    }

    public void setAvaliacaoId(Integer avaliacaoId) {
        this.avaliacaoId = avaliacaoId;
    }

    public String getAvaliacaoPrimId() {
        return avaliacaoPrimId;
    }

    public void setAvaliacaoPrimId(String avaliacaoPrimId) {
        this.avaliacaoPrimId = avaliacaoPrimId;
    }

    public String getAvaliacaoPrimData() {
        return avaliacaoPrimData;
    }

    public void setAvaliacaoPrimData(String avaliacaoPrimData) {
        this.avaliacaoPrimData = avaliacaoPrimData;
    }

    public String getAvaliacaoPrimUs() {
        return avaliacaoPrimUs;
    }

    public void setAvaliacaoPrimUs(String avaliacaoPrimUs) {
        this.avaliacaoPrimUs = avaliacaoPrimUs;
    }

    public String getAvaliacaoSecId() {
        return avaliacaoSecId;
    }

    public void setAvaliacaoSecId(String avaliacaoSecId) {
        this.avaliacaoSecId = avaliacaoSecId;
    }

    public String getAvaliacaoSecData() {
        return avaliacaoSecData;
    }

    public void setAvaliacaoSecData(String avaliacaoSecData) {
        this.avaliacaoSecData = avaliacaoSecData;
    }

    public String getAvaliacaoSecUs() {
        return avaliacaoSecUs;
    }

    public void setAvaliacaoSecUs(String avaliacaoSecUs) {
        this.avaliacaoSecUs = avaliacaoSecUs;
    }    

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

}
