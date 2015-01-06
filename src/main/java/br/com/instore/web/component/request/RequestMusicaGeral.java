package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.CategoriaGeralBean;
import br.com.instore.core.orm.bean.CategoriaMusicaGeralBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.MusicaGeralBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.MusicaGeralDTO;
import br.com.instore.web.dto.MusicaId;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@RequestScoped
public class RequestMusicaGeral implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestMusicaGeral() {
    }

    public RequestMusicaGeral(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public class MusicaGeralDTOInternal extends MusicaGeralDTO {

        private String nomeArquivoFormatado;

        public String getNomeArquivoFormatado() {
            return nomeArquivoFormatado;
        }

        public void setNomeArquivoFormatado(String nomeArquivoFormatado) {
            this.nomeArquivoFormatado = nomeArquivoFormatado;
        }
    }

    public List<MusicaGeralDTO> beanList() {
        List<MusicaGeralBean> lista = new ArrayList<MusicaGeralBean>();
        List<MusicaGeralDTO> lista2 = new ArrayList<MusicaGeralDTO>();
        lista = repository.query(MusicaGeralBean.class).findAll();
        for (MusicaGeralBean bean : lista) {
            MusicaGeralDTOInternal dto = new MusicaGeralDTOInternal();

            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setAfinidade1(bean.getAfinidade1().isEmpty() ? "Sem afinidade definida" : bean.getAfinidade1());
            dto.setAfinidade2(bean.getAfinidade2().isEmpty() ? "Sem afinidade definida" : bean.getAfinidade2());
            dto.setAfinidade3(bean.getAfinidade3().isEmpty() ? "Sem afinidade definida" : bean.getAfinidade3());
            dto.setAfinidade4(bean.getAfinidade4().isEmpty() ? "Sem afinidade definida" : bean.getAfinidade4());
            dto.setAnoGravacao(bean.getAnoGravacao().toString());
            dto.setArquivo(bean.getArquivo());
            dto.setBpm("" + bean.getBpm() + " BPM");

            AudiostoreGravadoraBean agb = repository.find(AudiostoreGravadoraBean.class, bean.getGravadora());
            UsuarioBean u = repository.find(UsuarioBean.class, bean.getUsuario());

            String categorias = "";
            String virgula = "";
            List<CategoriaMusicaGeralBean> categoriaMusicaGeralBeanList = repository.query(CategoriaMusicaGeralBean.class).eq("musica", bean.getId()).findAll();
            if (null != categoriaMusicaGeralBeanList && !categoriaMusicaGeralBeanList.isEmpty()) {
                for (CategoriaMusicaGeralBean item : categoriaMusicaGeralBeanList) {
                    CategoriaGeralBean cbean = repository.find(CategoriaGeralBean.class, item.getCategoria());
                    if (cbean != null) {
                        categorias += virgula + cbean.getNome();
                        virgula = ", ";
                    }
                }
            }

            dto.setCategoriaGeral((categorias != null && !categorias.isEmpty() ? categorias : "sem categoria definida"));
            dto.setGravadora((agb != null ? agb.getNome() : "sem gravadora definida"));
            dto.setInterprete(bean.getInterprete());
            dto.setLetra(bean.getLetra().isEmpty() ? "Sem letra definida" : bean.getLetra());
            dto.setTempoTotal(bean.getTempoTotal());

            String ti = "";
            switch (bean.getTipoInterprete()) {
                case 1:
                    ti = "Masculino";
                case 2:
                    ti = "Feminino";
                case 3:
                    ti = "Grupo";
                case 4:
                    ti = "Instrumental";
                case 5:
                    ti = "Jingle";
            }
            dto.setTipoInterprete(ti);
            dto.setTitulo(bean.getTitulo());
            dto.setUsuario(u != null ? u.getNome() : "sem usuário definida");

            List<String> partss = Arrays.asList(dto.getArquivo().split("/"));
            if (null != partss && !partss.isEmpty()) {
                dto.setNomeArquivoFormatado(partss.get(partss.size() - 1));
            }

            lista2.add(dto);


        }
        return lista2;
    }

    public List<CategoriaGeralBean> categorias() {
        return repository.query(CategoriaGeralBean.class).findAll();
    }

    public List<CategoriaMusicaGeralBean> categoriasDaMusica(Integer id) {
        return repository.query(CategoriaMusicaGeralBean.class).eq("musica", id).findAll();
    }

    public CategoriaGeralBean categoria(Integer id) {
        return repository.find(CategoriaGeralBean.class, id);
    }

    public List<AudiostoreGravadoraBean> gravadoras() {
        return repository.query(AudiostoreGravadoraBean.class).findAll();
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        return clienteBeanList;
    }

    public List<AudiostoreCategoriaBean> categoriaBeanList() {
        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).findAll();
        return audiostoreCategoriaBeanList;
    }

    public List<AudiostoreGravadoraBean> gravadoraBeanList() {
        List<AudiostoreGravadoraBean> audiostoreCategoriaBeanList = repository.query(AudiostoreGravadoraBean.class).findAll();
        return audiostoreCategoriaBeanList;
    }

    public MusicaGeralBean bean(Integer id) {
        return repository.find(MusicaGeralBean.class, id);
    }

    public void salvar(MusicaGeralBean bean, String categoria) {

        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (null == categoria || categoria.isEmpty()) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe uma categoria")).recursive().serialize();
                return;
            }

            bean.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
            if (null == bean.getAfinidade1() || bean.getAfinidade1().isEmpty()) {
                bean.setAfinidade1("");
            }

            if (null == bean.getAfinidade2() || bean.getAfinidade2().isEmpty()) {
                bean.setAfinidade2("");
            }

            if (null == bean.getAfinidade3() || bean.getAfinidade3().isEmpty()) {
                bean.setAfinidade3("");
            }

            if (null == bean.getAfinidade4() || bean.getAfinidade4().isEmpty()) {
                bean.setAfinidade4("");
            }

            if (null == bean.getLetra() || bean.getLetra().isEmpty()) {
                bean.setLetra("");
            }

            if (bean.getId() != null && bean.getId() > 0) {
                bean = repository.marge(bean);
            }
            bean.setLetra(bean.getLetra().replace("\"", "'"));
            repository.save(bean);


            repository.query("delete from categoria_musica_geral where musica = " + bean.getId()).executeSQLCommand();

            categoria = categoria.trim();
            List<String> categoriasNomes = Arrays.asList(categoria.split(","));
            for (String categ : categoriasNomes) {
                CategoriaMusicaGeralBean cmg = new CategoriaMusicaGeralBean();
                cmg.setMusica(bean.getId());

                if (repository.query(CategoriaGeralBean.class).eq("nome", categ.trim()).count() > 0) {
                    CategoriaGeralBean cgb = repository.query(CategoriaGeralBean.class).eq("nome", categ.trim()).findOne();

                    if (null != cgb) {
                        cmg.setCategoria(cgb.getId());
                        repository.save(cmg);
                    } else {
                        CategoriaGeralBean cat = new CategoriaGeralBean();
                        cat.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
                        cat.setNome(categ);
                        repository.save(cat);

                        cmg.setMusica(bean.getId());
                        cmg.setCategoria(cat.getId());
                        repository.save(cmg);
                    }
                } else {
                    CategoriaGeralBean cat = new CategoriaGeralBean();
                    cat.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
                    cat.setNome(categ);
                    repository.save(cat);

                    cmg.setMusica(bean.getId());
                    cmg.setCategoria(cat.getId());
                    repository.save(cmg);
                }
                repository.save(cmg);
            }

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }

    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            MusicaGeralBean bean = repository.marge((MusicaGeralBean) repository.find(MusicaGeralBean.class, id));
            repository.delete(bean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Música removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a música!")).recursive().serialize();
        }
    }

    public void sinc(String dir, String usuario, String senha) {
        try {
            if (!dir.endsWith("/")) {
                dir += "/";
            }

            dir = dir.replace("\\", "/");
            dir = dir.substring(2, dir.length());
            dir = "smb://" + dir;

            List<MusicaGeralBean> musicaGeralBeanList = new ArrayList<MusicaGeralBean>();
            sinc(dir, musicaGeralBeanList, usuario, senha);
//            repository.setUsuario(sessionUsuario.getUsuarioBean());

            StringBuilder inserts = new StringBuilder();
            inserts.append("INSERT INTO musica_geral VALUES");
            String comma = "";
            for (MusicaGeralBean item : musicaGeralBeanList) {
                inserts.append(comma + "(null , 0 , " + sessionUsuario.getUsuarioBean().getIdusuario() + ", 0, '" + item.getTitulo() + "', '', 0, '', 120, '00:00',1990,'','','','','" + item.getArquivo() + "')");
                comma = ",\n";
            }
            inserts.append(";");
            repository.query(inserts.toString()).executeSQLCommand2();

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Sincronização finalizada com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, e.getMessage())).recursive().serialize();
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, UnsupportedTagException, InvalidDataException {
        Mp3File mp3file = new Mp3File("C:\\Users\\TI-Caio\\Desktop\\download\\angra.mp3");

        System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
        System.out.println("Bitrate: " + mp3file.getLengthInSeconds() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
        System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
        System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
        System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
        System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));

        if (mp3file.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mp3file.getId3v1Tag();
            System.out.println("Track: " + id3v1Tag.getTrack());
            System.out.println("Artist: " + id3v1Tag.getArtist());
            System.out.println("Title: " + id3v1Tag.getTitle());
            System.out.println("Album: " + id3v1Tag.getAlbum());
            System.out.println("Year: " + id3v1Tag.getYear());
            System.out.println("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
            System.out.println("Comment: " + id3v1Tag.getComment());
        }

        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            System.out.println("Track: " + id3v2Tag.getTrack());
            System.out.println("Artist: " + id3v2Tag.getArtist());
            System.out.println("Title: " + id3v2Tag.getTitle());
            System.out.println("Album: " + id3v2Tag.getAlbum());
            System.out.println("Year: " + id3v2Tag.getYear());
            System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
            System.out.println("Comment: " + id3v2Tag.getComment());
            System.out.println("Composer: " + id3v2Tag.getComposer());
            System.out.println("Publisher: " + id3v2Tag.getPublisher());
            System.out.println("Original artist: " + id3v2Tag.getOriginalArtist());
            System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
            System.out.println("Copyright: " + id3v2Tag.getCopyright());
            System.out.println("URL: " + id3v2Tag.getUrl());
            System.out.println("Encoder: " + id3v2Tag.getEncoder());
            byte[] albumImageData = id3v2Tag.getAlbumImage();
            if (albumImageData != null) {
                System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
                System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
            }
        }
    }

    public static void metadata(InputStream input) {
        try {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata);
            input.close();

            // List all metadata
            String[] metadataNames = metadata.names();

            for (String name : metadataNames) {
                System.out.println(name + "::::" + metadata.get(name));
            }

            // Retrieve the necessary info from metadata
            // Names - title, xmpDM:artist etc. - mentioned below may differ based
            System.out.println("----------------------------------------------");
            System.out.println("Title: " + metadata.get("title"));
            System.out.println("Artists: " + metadata.get("xmpDM:artist"));
            System.out.println("Composer : " + metadata.get("xmpDM:composer"));
            System.out.println("Genre : " + metadata.get("xmpDM:genre"));
            System.out.println("Album : " + metadata.get("xmpDM:album"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

    private static void sinc(String dirPath, List<MusicaGeralBean> musicaGeralBeanList, String usuario, String senha) throws Exception {
        try {

            SmbFile smbDir = null;

            smbDir = new SmbFile(dirPath, Utilities.getAuthSmb(usuario, senha));

            if (!smbDir.exists()) {
                throw new Exception("O diretório informado não existe ou não pode ser acessado!");
            }

            if (smbDir.listFiles().length <= 0) {
                throw new Exception("O diretório informado está vazío!");
            }
            for (SmbFile item : smbDir.listFiles()) {
                System.out.println("======================");
                System.out.println("ARQUIVO");
                System.out.println(item.getName());
                metadata(item.getInputStream());
                System.out.println("======================");
                if (item.isFile()) {
                    if (item.getName().indexOf(".mp3") != -1 || item.getName().indexOf(".wav") != -1) {
                        MusicaGeralBean m = new MusicaGeralBean();
                        m.setCategoriaGeral(0);
                        m.setGravadora(0);
                        m.setUsuario(0);
                        m.setArquivo(dirPath + item.getName() + "/");
                        m.setTitulo(item.getName());
                        m.setInterprete("");
                        m.setTipoInterprete(new Short("0"));
                        m.setLetra("Não identificado");
                        m.setBpm(new Short("120"));
                        m.setTempoTotal("00:00:00");
                        m.setAnoGravacao(1990);
                        m.setAfinidade1("");
                        m.setAfinidade2("");
                        m.setAfinidade3("");
                        m.setAfinidade4("");
                        musicaGeralBeanList.add(m);
                    }
                } else {
                    if (item.isDirectory()) {
                        sinc(item.getPath(), musicaGeralBeanList, usuario, senha);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
            throw new Exception("O diretório informado não existe ou não pode ser acessado!");
        }
    }

    public long totalRegistros() {
        return repository.query(MusicaGeralBean.class).count();
    }

    public long totalRegistrosPorPagina() {
        return 100;
    }

    public long totalPaginas(long totalRegistros, long totalRegistrosPorPagina) {
        return (long) Math.ceil((double) totalRegistros / totalRegistrosPorPagina);
    }

    public void list(int pagina, int qtd, int order, String titulo, String interprete, String velocidade, String anoGravacao, String letra, String categoria) {

        if (pagina == 0) {
            pagina = 1;
        }

        if (qtd == 0) {
            qtd = 10;
        }

        if (order == 0) {
            order = 10;
        }


        long totalRegistrosPorPagina = Long.parseLong("" + qtd);


        Integer end = pagina * (int) totalRegistrosPorPagina;
        Integer begin = end - (int) totalRegistrosPorPagina;

        List<MusicaGeralBean> lista = new ArrayList<MusicaGeralBean>();
        Query query = repository.query(MusicaGeralBean.class);
        Query query2 = repository.query(MusicaGeralBean.class);
        query.limit(begin, qtd);

        switch (order) {
            case 1:
                query.orderAsc("titulo");
                query2.orderAsc("titulo");
                break;
            case 2:
                query.orderDesc("titulo");
                query2.orderDesc("titulo");
                break;
            case 3:
                query.orderAsc("titulo");
                query2.orderAsc("titulo");
                break;
            case 4:
                query.orderDesc("titulo");
                query2.orderDesc("titulo");
                break;
            case 5:
                query.orderAsc("interprete");
                query2.orderAsc("interprete");
                break;
            case 6:
                query.orderDesc("interprete");
                query2.orderDesc("interprete");
                break;
            case 7:
                query.orderAsc("bpm");
                query2.orderAsc("bpm");
                break;
            case 8:
                query.orderDesc("bpm");
                query2.orderDesc("bpm");
                break;
            case 9:
                query.orderAsc("bpm");
                query2.orderAsc("bpm");
                break;
            case 10:
                query.orderDesc("bpm");
                query2.orderDesc("bpm");
                break;
            case 11:
                query.orderAsc("letra");
                query2.orderAsc("letra");
                break;
            case 12:
                query.orderDesc("letra");
                query2.orderDesc("letra");
                break;
        }

        if (null != titulo && !titulo.isEmpty()) {
            query.likeAnyWhere("titulo", titulo);
            query2.likeAnyWhere("titulo", titulo);
        }

        if (null != interprete && !interprete.isEmpty()) {
            query.likeAnyWhere("interprete", interprete);
            query2.likeAnyWhere("interprete", interprete);
        }

        if (null != velocidade && !velocidade.isEmpty()) {
            try {
                query.eq("velocidade", Short.parseShort(velocidade));
                query2.eq("velocidade", Short.parseShort(velocidade));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (null != anoGravacao && !anoGravacao.isEmpty()) {
            try {
                query.eq("anoGravacao", Integer.parseInt(anoGravacao));
                query2.eq("anoGravacao", Integer.parseInt(anoGravacao));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (null != letra && !letra.isEmpty()) {
            query.likeAnyWhere("letra", letra);
            query2.likeAnyWhere("letra", letra);
        }

        if (null != categoria && !categoria.isEmpty()) {
            String[] categs = categoria.split(",");
            List<Integer> integerList = new ArrayList<Integer>();

            for (String categ : categs) {
                List<MusicaId> tests = repository.query("select \n"
                        + "    mg.id as id,\n"
                        + "	'param_a' as param_a,\n"
                        + "	'param_b' as param_b,\n"
                        + "	'param_c' as param_c\n"
                        + "from\n"
                        + "    categoria_musica_geral as cmg\n"
                        + "inner join categoria_geral as cg on cg.id = cmg.categoria\n"
                        + "inner join musica_geral as mg on mg.id = cmg.musica\n"
                        + "where cg.nome like  '%" + categ + "%'").executeSQL(MusicaId.class);

                if (null != tests && !tests.isEmpty()) {
                    for (MusicaId item : tests) {
                        if (!integerList.contains(item)) {
                            integerList.add(item.id);
                        }
                    }
                }

            }
            if (null != integerList && !integerList.isEmpty()) {
                query.in("id", integerList.toArray(new Integer[integerList.size()])).findAll();
                query2.in("id", integerList.toArray(new Integer[integerList.size()])).findAll();
            }
        }

        List<MusicaGeralDTO> lista2 = new ArrayList<MusicaGeralDTO>();
        lista = query.findAll();
        for (MusicaGeralBean bean : lista) {
            MusicaGeralDTOInternal dto = new MusicaGeralDTOInternal();

            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setAfinidade1(bean.getAfinidade1().isEmpty() ? "Sem afinidade definida" : bean.getAfinidade1());
            dto.setAfinidade2(bean.getAfinidade2().isEmpty() ? "Sem afinidade definida" : bean.getAfinidade2());
            dto.setAfinidade3(bean.getAfinidade3().isEmpty() ? "Sem afinidade definida" : bean.getAfinidade3());
            dto.setAfinidade4(bean.getAfinidade4().isEmpty() ? "Sem afinidade definida" : bean.getAfinidade4());
            dto.setAnoGravacao(bean.getAnoGravacao().toString());
            dto.setArquivo(bean.getArquivo());
            dto.setBpm("" + bean.getBpm() + " BPM");

            AudiostoreGravadoraBean agb = repository.find(AudiostoreGravadoraBean.class, bean.getGravadora());
            UsuarioBean u = repository.find(UsuarioBean.class, bean.getUsuario());

            String categorias = "";
            String virgula = "";
            List<CategoriaMusicaGeralBean> categoriaMusicaGeralBeanList = repository.query(CategoriaMusicaGeralBean.class).eq("musica", bean.getId()).findAll();
            if (null != categoriaMusicaGeralBeanList && !categoriaMusicaGeralBeanList.isEmpty()) {
                for (CategoriaMusicaGeralBean item : categoriaMusicaGeralBeanList) {
                    CategoriaGeralBean cbean = repository.find(CategoriaGeralBean.class, item.getCategoria());
                    if (cbean != null) {
                        categorias += virgula + cbean.getNome();
                        virgula = ", ";
                    }
                }
            }

            dto.setCategoriaGeral((categorias != null && !categorias.isEmpty() ? categorias : "sem categoria definida"));
            dto.setGravadora((agb != null ? agb.getNome() : "sem gravadora definida"));
            dto.setInterprete(bean.getInterprete());
            dto.setLetra(bean.getLetra().isEmpty() ? "Sem letra definida" : bean.getLetra());
            dto.setTempoTotal(bean.getTempoTotal());

            String ti = "";
            switch (bean.getTipoInterprete()) {
                case 1:
                    ti = "Masculino";
                case 2:
                    ti = "Feminino";
                case 3:
                    ti = "Grupo";
                case 4:
                    ti = "Instrumental";
                case 5:
                    ti = "Jingle";
            }
            dto.setTipoInterprete(ti);
            dto.setTitulo(bean.getTitulo());
            dto.setUsuario(u != null ? u.getNome() : "sem usuário definida");

            List<String> partss = Arrays.asList(dto.getArquivo().split("/"));
            if (null != partss && !partss.isEmpty()) {
                dto.setNomeArquivoFormatado(partss.get(partss.size() - 1));
            }

            lista2.add(dto);
        }

        long totalRegistros = query2.count();
        long totalPaginas = totalPaginas(totalRegistros, totalRegistrosPorPagina);

        result.include("lista2", lista2);
        result.include("totalRegistros", totalRegistros);
        result.include("totalRegistrosPorPagina", totalRegistrosPorPagina);
        result.include("totalPaginas", totalPaginas);
        result.include("paginaAtual", pagina);
    }
}