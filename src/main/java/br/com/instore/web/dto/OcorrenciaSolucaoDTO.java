package br.com.instore.web.dto;

public class OcorrenciaSolucaoDTO  extends AbstractDTO {

    private String id;
    private String descricao;
    private String prazoResolucao;

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

    public String getPrazoResolucao() {
        return prazoResolucao;
    }

    public void setPrazoResolucao(String prazoResolucao) {
        this.prazoResolucao = prazoResolucao;
    }
}
