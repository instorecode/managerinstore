package br.com.instore.web.dto;

public class ProjetoDTO extends AbstractDTO{
    
    private String id;
    private String descricao;
    private String nome;
    private String dataCriacao;
    private String linkDocumentacao;
    private String idUsuario;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getLinkDocumentacao() {
        return linkDocumentacao;
    }

    public void setLinkDocumentacao(String linkDocumentacao) {
        this.linkDocumentacao = linkDocumentacao;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    

}
