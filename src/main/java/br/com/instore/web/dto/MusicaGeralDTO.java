package br.com.instore.web.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MusicaGeralDTO extends AbstractDTO {
    private String id;
    private String categoriaGeral;
    private String usuario;
    private String gravadora;
    private String titulo;
    private String interprete;
    private String tipoInterprete;
    private String letra;
    private String bpm;
    private String tempoTotal;
    private String anoGravacao;
    private String afinidade1;
    private String afinidade2;
    private String afinidade3;
    private String afinidade4;
    private String arquivo;
    private String dataCadastro;

    public MusicaGeralDTO() {
    }

    public MusicaGeralDTO(String id, String categoriaGeral, String usuario, String gravadora, String titulo, String interprete, String tipoInterprete, String letra, String bpm, String tempoTotal, String anoGravacao, String afinidade1, String afinidade2, String afinidade3, String afinidade4, String arquivo) {
        this.id = id;
        this.categoriaGeral = categoriaGeral;
        this.usuario = usuario;
        this.gravadora = gravadora;
        this.titulo = titulo;
        this.interprete = interprete;
        this.tipoInterprete = tipoInterprete;
        this.letra = letra;
        this.bpm = bpm;
        this.tempoTotal = tempoTotal;
        this.anoGravacao = anoGravacao;
        this.afinidade1 = afinidade1;
        this.afinidade2 = afinidade2;
        this.afinidade3 = afinidade3;
        this.afinidade4 = afinidade4;
        this.arquivo = arquivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoriaGeral() {
        return categoriaGeral;
    }

    public void setCategoriaGeral(String categoriaGeral) {
        this.categoriaGeral = categoriaGeral;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getGravadora() {
        return gravadora;
    }

    public void setGravadora(String gravadora) {
        this.gravadora = gravadora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    public String getTipoInterprete() {
        return tipoInterprete;
    }

    public void setTipoInterprete(String tipoInterprete) {
        this.tipoInterprete = tipoInterprete;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(String tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    public String getAnoGravacao() {
        return anoGravacao;
    }

    public void setAnoGravacao(String anoGravacao) {
        this.anoGravacao = anoGravacao;
    }

    public String getAfinidade1() {
        return afinidade1;
    }

    public void setAfinidade1(String afinidade1) {
        this.afinidade1 = afinidade1;
    }

    public String getAfinidade2() {
        return afinidade2;
    }

    public void setAfinidade2(String afinidade2) {
        this.afinidade2 = afinidade2;
    }

    public String getAfinidade3() {
        return afinidade3;
    }

    public void setAfinidade3(String afinidade3) {
        this.afinidade3 = afinidade3;
    }

    public String getAfinidade4() {
        return afinidade4;
    }

    public void setAfinidade4(String afinidade4) {
        this.afinidade4 = afinidade4;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
