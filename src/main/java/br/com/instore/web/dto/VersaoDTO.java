package br.com.instore.web.dto;

public class VersaoDTO extends AbstractDTO{
    
    private String id;
    private String dataCriacao;
    private String nome;
    private String descricao;
    private String idProjeto;
    private String linkSvn;
    private String download;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(String idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getLinkSvn() {
        return linkSvn;
    }

    public void setLinkSvn(String linkSvn) {
        this.linkSvn = linkSvn;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
    
    
    
}
