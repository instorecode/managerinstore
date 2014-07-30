package br.com.instore.web.dto;

import br.com.instore.core.orm.bean.property.AudiostoreCategoria;
import java.util.ArrayList;
import java.util.List;

public class CadastroMusicaDTO extends AbstractDTO {
    private List<AudiostoreCategoriaDTO> categoriaList = new ArrayList<AudiostoreCategoriaDTO>(); 
    private List<MusicaDTO> musicaList = new ArrayList<MusicaDTO>(); 
    private boolean possuiCategoria;
    private boolean existeDiretorio;
    private boolean existeArquivo;
    private boolean clienteNaoConfigurado;

    public CadastroMusicaDTO() {
    }


    public List<AudiostoreCategoriaDTO> getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List<AudiostoreCategoriaDTO> categoriaList) {
        this.categoriaList = categoriaList;
    }

    public List<MusicaDTO> getMusicaList() {
        return musicaList;
    }

    public void setMusicaList(List<MusicaDTO> musicaList) {
        this.musicaList = musicaList;
    }

    public boolean isPossuiCategoria() {
        return possuiCategoria;
    }

    public void setPossuiCategoria(boolean possuiCategoria) {
        this.possuiCategoria = possuiCategoria;
    }

    public boolean isClienteNaoConfigurado() {
        return clienteNaoConfigurado;
    }

    public void setClienteNaoConfigurado(boolean clienteNaoConfigurado) {
        this.clienteNaoConfigurado = clienteNaoConfigurado;
    }

    public boolean isExisteDiretorio() {
        return existeDiretorio;
    }

    public void setExisteDiretorio(boolean existeDiretorio) {
        this.existeDiretorio = existeDiretorio;
    }

    public boolean isExisteArquivo() {
        return existeArquivo;
    }

    public void setExisteArquivo(boolean existeArquivo) {
        this.existeArquivo = existeArquivo;
    }
    
    
}
