package br.com.instore.web.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ClienteDTOInternal extends AbstractDTO {
    private String idcliente;
    private String parente;
    private String nome;
    private String matriz;
    private String instore;
    private String nomeFantasia;
    private String cnpj;
    private String dataInicioContrato;
    private String dataTerminoContrato;
    private String indiceReajusteContrato;
    private String renovacaoAutomatica;
    private String razaoSocial;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String complemento;
    private String Situacao;
    private String local1;
    private String local2;
    private String local3;
    private String local4;
    private String local5;
    private String codigoInterno;
    private String codigoExterno;
    private String nomeParente;
    private Integer totalFiliaisAtivas = 0;
    private Integer totalFiliaisInativas = 0;

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getParente() {
        return parente;
    }

    public void setParente(String parente) {
        this.parente = parente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatriz() {
        return matriz;
    }

    public void setMatriz(String matriz) {
        this.matriz = matriz;
    }

    public String getInstore() {
        return instore;
    }

    public void setInstore(String instore) {
        this.instore = instore;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDataInicioContrato() {
        return dataInicioContrato;
    }

    public void setDataInicioContrato(String dataInicioContrato) {
        this.dataInicioContrato = dataInicioContrato;
    }

    public String getDataTerminoContrato() {
        return dataTerminoContrato;
    }

    public void setDataTerminoContrato(String dataTerminoContrato) {
        this.dataTerminoContrato = dataTerminoContrato;
    }

    public String getIndiceReajusteContrato() {
        return indiceReajusteContrato;
    }

    public void setIndiceReajusteContrato(String indiceReajusteContrato) {
        this.indiceReajusteContrato = indiceReajusteContrato;
    }

    public String getRenovacaoAutomatica() {
        return renovacaoAutomatica;
    }

    public void setRenovacaoAutomatica(String renovacaoAutomatica) {
        this.renovacaoAutomatica = renovacaoAutomatica;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getSituacao() {
        return Situacao;
    }

    public void setSituacao(String Situacao) {
        this.Situacao = Situacao;
    }

    public String getLocal1() {
        return local1;
    }

    public void setLocal1(String local1) {
        this.local1 = local1;
    }

    public String getLocal2() {
        return local2;
    }

    public void setLocal2(String local2) {
        this.local2 = local2;
    }

    public String getLocal3() {
        return local3;
    }

    public void setLocal3(String local3) {
        this.local3 = local3;
    }

    public String getLocal4() {
        return local4;
    }

    public void setLocal4(String local4) {
        this.local4 = local4;
    }

    public String getLocal5() {
        return local5;
    }

    public void setLocal5(String local5) {
        this.local5 = local5;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigoExterno() {
        return codigoExterno;
    }

    public void setCodigoExterno(String codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    public String getNomeParente() {
        return nomeParente;
    }

    public void setNomeParente(String nomeParente) {
        this.nomeParente = nomeParente;
    }

    public Integer getTotalFiliaisAtivas() {
        return totalFiliaisAtivas;
    }

    public void setTotalFiliaisAtivas(Integer totalFiliaisAtivas) {
        this.totalFiliaisAtivas = totalFiliaisAtivas;
    }

    public Integer getTotalFiliaisInativas() {
        return totalFiliaisInativas;
    }

    public void setTotalFiliaisInativas(Integer totalFiliaisInativas) {
        this.totalFiliaisInativas = totalFiliaisInativas;
    }

   
}
