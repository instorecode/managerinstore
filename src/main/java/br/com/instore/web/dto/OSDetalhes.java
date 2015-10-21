package br.com.instore.web.dto;

import br.com.instore.core.orm.bean.OrdemServicoObsBean;
import java.util.ArrayList;
import java.util.List;

public class OSDetalhes extends AbstractDTO {

    private String osn;
    private String cliente;
    private String data;
    private String quandoSolicitou;
    private String aprovadoEm;
    private String praca;
    private String frequencia;
    private String radio;
    private String dataDistr;
    private String det;
    private String texto;
    private String clientesCod;
    private List<OrdenServicoObsBeanExtended> obsList = new ArrayList<OrdenServicoObsBeanExtended>();

    public String getOsn() {
        return osn;
    }

    public void setOsn(String osn) {
        this.osn = osn;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getQuandoSolicitou() {
        return quandoSolicitou;
    }

    public void setQuandoSolicitou(String quandoSolicitou) {
        this.quandoSolicitou = quandoSolicitou;
    }

    public String getAprovadoEm() {
        return aprovadoEm;
    }

    public void setAprovadoEm(String aprovadoEm) {
        this.aprovadoEm = aprovadoEm;
    }

    public String getPraca() {
        return praca;
    }

    public void setPraca(String praca) {
        this.praca = praca;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getDataDistr() {
        return dataDistr;
    }

    public void setDataDistr(String dataDistr) {
        this.dataDistr = dataDistr;
    }

    public String getDet() {
        return det;
    }

    public void setDet(String det) {
        this.det = det;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getClientesCod() {
        return clientesCod;
    }

    public void setClientesCod(String clientesCod) {
        this.clientesCod = clientesCod;
    }

    public List<OrdenServicoObsBeanExtended> getObsList() {
        return obsList;
    }

    public void setObsList(List<OrdenServicoObsBeanExtended> obsList) {
        this.obsList = obsList;
    }

}
