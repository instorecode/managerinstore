
package br.com.instore.web.dto;

public class OSFila extends AbstractDTO {
    private String cod;
    private String  nome;
    private String  dataDist;
    private Integer  explodiu;
    private Integer  prioridade;
    private Integer  situacao;

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
}
