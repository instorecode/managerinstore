package br.com.instore.web.dto;

public class AudiostoreComercialDTO extends AbstractDTO {
    private String id;
    private String clienteNome;
    private String categoriaNome;
    private String semanaHora;
    private String arquivo;
    private String titulo;
    private String tipoInterprete;
    private String periodoInicial;
    private String periodoFinal;
    private String tipoHorario;
    private String diasSemana;
    private String diasAlternados;
    private String data;
    private String ultimaExecucao;
    private String tempoTotal;
    private String random;
    private String qtd;
    private String dataVencimento;
    private String dependencia1;
    private String dependencia2;
    private String dependencia3;
    private String frameInicial;
    private String frameFinal;
    private String semSom;
    private String msg;

    public AudiostoreComercialDTO() {
    }

    public AudiostoreComercialDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public String getSemanaHora() {
        return semanaHora;
    }

    public void setSemanaHora(String semanaHora) {
        this.semanaHora = semanaHora;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipoInterprete() {
        return tipoInterprete;
    }

    public void setTipoInterprete(String tipoInterprete) {
        this.tipoInterprete = tipoInterprete;
    }

    public String getPeriodoInicial() {
        return periodoInicial;
    }

    public void setPeriodoInicial(String periodoInicial) {
        this.periodoInicial = periodoInicial;
    }

    public String getPeriodoFinal() {
        return periodoFinal;
    }

    public void setPeriodoFinal(String periodoFinal) {
        this.periodoFinal = periodoFinal;
    }

    public String getTipoHorario() {
        return tipoHorario;
    }

    public void setTipoHorario(String tipoHorario) {
        this.tipoHorario = tipoHorario;
    }

    public String getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public String getDiasAlternados() {
        return diasAlternados;
    }

    public void setDiasAlternados(String diasAlternados) {
        this.diasAlternados = diasAlternados;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUltimaExecucao() {
        return ultimaExecucao;
    }

    public void setUltimaExecucao(String ultimaExecucao) {
        this.ultimaExecucao = ultimaExecucao;
    }

    public String getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(String tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDependencia1() {
        return dependencia1;
    }

    public void setDependencia1(String dependencia1) {
        this.dependencia1 = dependencia1;
    }

    public String getDependencia2() {
        return dependencia2;
    }

    public void setDependencia2(String dependencia2) {
        this.dependencia2 = dependencia2;
    }

    public String getDependencia3() {
        return dependencia3;
    }

    public void setDependencia3(String dependencia3) {
        this.dependencia3 = dependencia3;
    }

    public String getFrameInicial() {
        return frameInicial;
    }

    public void setFrameInicial(String frameInicial) {
        this.frameInicial = frameInicial;
    }

    public String getFrameFinal() {
        return frameFinal;
    }

    public void setFrameFinal(String frameFinal) {
        this.frameFinal = frameFinal;
    }

    public String getSemSom() {
        return semSom;
    }

    public void setSemSom(String semSom) {
        this.semSom = semSom;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
