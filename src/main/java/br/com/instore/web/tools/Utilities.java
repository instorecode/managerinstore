package br.com.instore.web.tools;

import br.com.instore.core.orm.bean.HistoricoUsuarioBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.dto.MusicaDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Utilities {
    public static void main(String[] args) {
        System.out.println("ola mundo");
    }
    
    public static String leftPad(Integer s ) {
        return StringUtils.leftPad(s.toString(), 11, "0");
    }
    
    public static String leftPad(String s , String add, int size) {
        return StringUtils.leftPad(s, size, add);
    }
    
    public static String md5(String t) throws NoSuchAlgorithmException {

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(t.getBytes());
        byte[] digest = m.digest();

        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        return hashtext;
    }
    
    public static void historicoUsuarioLogin(SessionRepository repository) throws NoSuchAlgorithmException {
        HistoricoUsuarioBean historico = new HistoricoUsuarioBean();
        historico.setLogin(new Date());
        historico.setUsuario(repository.getUsuario());
        repository.save(historico);
        repository.finalize();
    }
    
    public static void historicoUsuarioLogOut(SessionRepository repository) throws NoSuchAlgorithmException {
        HistoricoUsuarioBean historico = new HistoricoUsuarioBean();
        historico.setLogout(new Date());
        historico.setUsuario(repository.getUsuario());
        repository.save(historico);
        repository.finalize();
    }
    
    public static List<MusicaDTO> getMetadata(String path) {
        List<MusicaDTO> list = new ArrayList<MusicaDTO>();
        File dir = new File(path);
        for (File f : dir.listFiles()) {
            try {

                InputStream input = new FileInputStream(f);
                ContentHandler handler = new DefaultHandler();
                Metadata metadata = new Metadata();
                Parser parser = new Mp3Parser();
                ParseContext parseCtx = new ParseContext();
                parser.parse(input, handler, metadata);
                input.close();

                String[] metadataNames = metadata.names();

                MusicaDTO dto = new MusicaDTO();
                dto.setTitulo(metadata.get("title"));
                dto.setArtistas(metadata.get("xmpDM:artist"));
                dto.setCompositor(metadata.get("xmpDM:composer"));
                dto.setGenero(metadata.get("xmpDM:genre"));
                dto.setAlbum(metadata.get("xmpDM:album"));
                dto.setNomeArquivo(f.getName());
                dto.setCaminhoCaminho(f.getPath());
                list.add(dto);

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
        return list;
    }
}
