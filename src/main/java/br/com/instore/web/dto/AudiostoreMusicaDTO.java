package br.com.instore.web.dto;

import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreComercialShBean;
import br.com.instore.core.orm.bean.ClienteBean;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AudiostoreMusicaDTO extends AbstractDTO {
    private String id;
    private String cliente;
    private String categoria1;
    private String categoria2;
    private String categoria3;
    private String arquivo;
    private String interprete;
    private String tipoInterprete;
    private String titulo;
    private String cut;
    private String crossover;
    private String dataVencimentoCrossover;
    private String diaExecucao1;
    private String diaExecucao2;
    private String afinidade1;
    private String afinidade2;
    private String afinidade3;
    private String afinidade4;
    private String anoGravacao;
    private String velocidade;
    private String data;
    private String ultimaExecucao;
    private String ultimaExecucaoDia;
    private String tempoTotal;
    private String random;
    private String qtdePlayer;
    private String qtde;
    private String dataVencimento;
    private String frameInicio;
    private String frameFinal;
    private String msg;
    private String semSom;
    private String superCrossover;

    public AudiostoreMusicaDTO() {
    }

    public AudiostoreMusicaDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCategoria1() {
        return categoria1;
    }

    public void setCategoria1(String categoria1) {
        this.categoria1 = categoria1;
    }

    public String getCategoria2() {
        return categoria2;
    }

    public void setCategoria2(String categoria2) {
        this.categoria2 = categoria2;
    }

    public String getCategoria3() {
        return categoria3;
    }

    public void setCategoria3(String categoria3) {
        this.categoria3 = categoria3;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getCrossover() {
        return crossover;
    }

    public void setCrossover(String crossover) {
        this.crossover = crossover;
    }

    public String getDataVencimentoCrossover() {
        return dataVencimentoCrossover;
    }

    public void setDataVencimentoCrossover(String dataVencimentoCrossover) {
        this.dataVencimentoCrossover = dataVencimentoCrossover;
    }

    public String getDiaExecucao1() {
        return diaExecucao1;
    }

    public void setDiaExecucao1(String diaExecucao1) {
        this.diaExecucao1 = diaExecucao1;
    }

    public String getDiaExecucao2() {
        return diaExecucao2;
    }

    public void setDiaExecucao2(String diaExecucao2) {
        this.diaExecucao2 = diaExecucao2;
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

    public String getAnoGravacao() {
        return anoGravacao;
    }

    public void setAnoGravacao(String anoGravacao) {
        this.anoGravacao = anoGravacao;
    }

    public String getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(String velocidade) {
        this.velocidade = velocidade;
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

    public String getUltimaExecucaoDia() {
        return ultimaExecucaoDia;
    }

    public void setUltimaExecucaoDia(String ultimaExecucaoDia) {
        this.ultimaExecucaoDia = ultimaExecucaoDia;
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

    public String getQtdePlayer() {
        return qtdePlayer;
    }

    public void setQtdePlayer(String qtdePlayer) {
        this.qtdePlayer = qtdePlayer;
    }

    public String getQtde() {
        return qtde;
    }

    public void setQtde(String qtde) {
        this.qtde = qtde;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getFrameInicio() {
        return frameInicio;
    }

    public void setFrameInicio(String frameInicio) {
        this.frameInicio = frameInicio;
    }

    public String getFrameFinal() {
        return frameFinal;
    }

    public void setFrameFinal(String frameFinal) {
        this.frameFinal = frameFinal;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSemSom() {
        return semSom;
    }

    public void setSemSom(String semSom) {
        this.semSom = semSom;
    }

    public String getSuperCrossover() {
        return superCrossover;
    }

    public void setSuperCrossover(String superCrossover) {
        this.superCrossover = superCrossover;
    }
}
