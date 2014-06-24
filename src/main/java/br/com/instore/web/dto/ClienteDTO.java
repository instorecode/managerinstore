package br.com.instore.web.dto;

import br.com.instore.core.orm.bean.EnderecoBean;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ClienteDTO extends AbstractDTO {
    private Integer idcliente;
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

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
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
}
