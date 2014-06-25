package br.com.instore.web.dto;

public class AudiostoreProgramacaoDTO extends AbstractDTO {

    private Integer id;
    private String descricao;
    private String clienteNome;
    private String dataInicio;
    private String dataFinal;
    private String horaInicio;
    private String horaFinal;
    private String diasSemana;

    public AudiostoreProgramacaoDTO() {
    }

    public AudiostoreProgramacaoDTO(Integer id, String descricao, String clienteNome, String dataInicio, String dataFinal, String horaInicio, String horaFinal, String diasSemana) {
        this.id = id;
        this.descricao = descricao;
        this.clienteNome = clienteNome;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.diasSemana = diasSemana;
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

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
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

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }
}
