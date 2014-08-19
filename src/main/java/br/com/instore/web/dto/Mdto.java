package br.com.instore.web.dto;

import java.io.InputStream;


public class Mdto extends AbstractDTO {
    private String nome;
    private String path;
    private InputStream is;

    public Mdto() {
    }

    public Mdto(String nome, String path, InputStream is) {
        this.nome = nome;
        this.path = path;
        this.is = is;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }
}
