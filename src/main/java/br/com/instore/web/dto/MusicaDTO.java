package br.com.instore.web.dto;

public class MusicaDTO extends AbstractDTO {
    private String nomeArquivo;
    private String caminhoCaminho;
    private String Titulo;
    private String Artistas;
    private String Compositor;
    private String Genero;
    private String Album;
    private String caminhoFull;

    public MusicaDTO() {
    }

    public MusicaDTO(String nomeArquivo, String caminhoCaminho, String Titulo, String Artistas, String Compositor, String Genero, String Album) {
        this.nomeArquivo = nomeArquivo;
        this.caminhoCaminho = caminhoCaminho;
        this.Titulo = Titulo;
        this.Artistas = Artistas;
        this.Compositor = Compositor;
        this.Genero = Genero;
        this.Album = Album;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminhoCaminho() {
        return caminhoCaminho;
    }

    public void setCaminhoCaminho(String caminhoCaminho) {
        this.caminhoCaminho = caminhoCaminho;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getArtistas() {
        return Artistas;
    }

    public void setArtistas(String Artistas) {
        this.Artistas = Artistas;
    }

    public String getCompositor() {
        return Compositor;
    }

    public void setCompositor(String Compositor) {
        this.Compositor = Compositor;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String Album) {
        this.Album = Album;
    }

    public String getCaminhoFull() {
        return caminhoFull;
    }

    public void setCaminhoFull(String caminhoFull) {
        this.caminhoFull = caminhoFull;
    }
    
    
}
