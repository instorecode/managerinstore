package br.com.instore.web.dto;

public class IndiceReajusteDTO  extends AbstractDTO {

    private String id;
    private String tipo;
    private String descricao;
    private String percentual;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPercentual() {
        return percentual;
    }

    public void setPercentual(String percentual) {
        this.percentual = percentual;
    }
}
