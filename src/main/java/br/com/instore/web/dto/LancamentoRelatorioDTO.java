package br.com.instore.web.dto;

import java.math.BigDecimal;
import java.util.Date;

public class LancamentoRelatorioDTO extends AbstractDTO {

    private Date data;
    private String historico;
    private BigDecimal credito;
    private BigDecimal debito;
    private BigDecimal saldo;
    private BigDecimal saldoCalculado;
    private Byte positivo;
    private Date fechamento;

    public LancamentoRelatorioDTO() {
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public BigDecimal getDebito() {
        return debito;
    }

    public void setDebito(BigDecimal debito) {
        this.debito = debito;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaldoCalculado() {
        return saldoCalculado;
    }

    public void setSaldoCalculado(BigDecimal saldoCalculado) {
        this.saldoCalculado = saldoCalculado;
    }

    public Byte getPositivo() {
        return positivo;
    }

    public void setPositivo(Byte positivo) {
        this.positivo = positivo;
    }

    public Date getFechamento() {
        return fechamento;
    }

    public void setFechamento(Date fechamento) {
        this.fechamento = fechamento;
    }
}
