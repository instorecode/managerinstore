package br.com.instore.web.tools;

import br.com.instore.core.orm.XmlAnnotation;
import br.com.instore.core.orm.bean.HistoricoUsuarioBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.dto.MusicaDTO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import jcifs.smb.NtlmPasswordAuthentication;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Utilities {

    public static String quebrarLinhaComHexa() throws DecoderException {
        return new String(Hex.decodeHex("0d0a".toCharArray()));
    }

    public static enum TaskLock {

        CATEGORIA, PROGRAMACAO, MUSICA, COMERCIAL, GRAVADORA
    }
    public static TaskLock taskLock;

    public static String formatarHexExp(String text) throws DecoderException {

        List<String> hexaList = new ArrayList<String>();
        hexaList.add("5C");
        hexaList.add("2F");
        hexaList.add("0D");
        hexaList.add("2E");
        hexaList.add("2d");
        hexaList.add("5D");
        hexaList.add("3A");
        hexaList.add("3B");
        hexaList.add("30");
        hexaList.add("31");
        hexaList.add("32");
        hexaList.add("33");
        hexaList.add("34");
        hexaList.add("35");
        hexaList.add("36");
        hexaList.add("37");
        hexaList.add("38");
        hexaList.add("39");
        hexaList.add("41");
        hexaList.add("42");
        hexaList.add("43");
        hexaList.add("44");
        hexaList.add("45");
        hexaList.add("46");
        hexaList.add("47");
        hexaList.add("48");
        hexaList.add("49");
        hexaList.add("4A");
        hexaList.add("4B");
        hexaList.add("4C");
        hexaList.add("4D");
        hexaList.add("4E");
        hexaList.add("4F");
        hexaList.add("50");
        hexaList.add("51");
        hexaList.add("52");
        hexaList.add("53");
        hexaList.add("54");
        hexaList.add("55");
        hexaList.add("56");
        hexaList.add("57");
        hexaList.add("58");
        hexaList.add("59");
        hexaList.add("5A");
        hexaList.add("61");
        hexaList.add("62");
        hexaList.add("63");
        hexaList.add("64");
        hexaList.add("65");
        hexaList.add("66");
        hexaList.add("67");
        hexaList.add("68");
        hexaList.add("69");
        hexaList.add("6A");
        hexaList.add("6B");
        hexaList.add("6C");
        hexaList.add("6D");
        hexaList.add("6E");
        hexaList.add("6F");
        hexaList.add("70");
        hexaList.add("71");
        hexaList.add("72");
        hexaList.add("73");
        hexaList.add("74");
        hexaList.add("75");
        hexaList.add("76");
        hexaList.add("77");
        hexaList.add("78");
        hexaList.add("79");
        hexaList.add("7A");

        List<String> hexaList2 = new ArrayList<String>();

        for (String str : hexaList) {
            hexaList2.add(str.toLowerCase());
        }

        for (String str : hexaList2) {
            hexaList.add(str);
        }

        String finalText = "";
        for (char ch : text.toCharArray()) {
            String hexaCode = Integer.toHexString(ch).toString();

            if (hexaCode.length() < 2) {
                hexaCode = "0".concat(hexaCode);
            }

            if (!hexaList.contains(hexaCode.trim())) {
                finalText += new String(Hex.decodeHex("20".toCharArray()));
            } else {
                finalText += new String(Hex.decodeHex(hexaCode.toCharArray()));
            }
        }
        return finalText;
    }

    public static String removeLetrasEspeciais(String text) {
        String alfStrEsp = "áéíóúãõäëïöüàèìòùâêîôûçåæñøßÿ&¢©µ¶€£®§¥°ºª¨´`^~*#";
        alfStrEsp += alfStrEsp.toUpperCase();

        String alfStr = "aeiouaoaeiouaeiouaeioucaanobyeccupefrsy00a";
        alfStr += alfStr.toUpperCase();

        List<String> alf = Arrays.asList(alfStr.split(""));
        List<String> alfEsp = Arrays.asList(alfStrEsp.split(""));

        for (int i = 0; i < alfEsp.size(); i++) {
            String chr = alfEsp.get(i);
            if (text.contains(chr)) {
                if (i < alf.size()) {
                    text = text.replace(chr, alf.get(i));
                } else {
                    text = text.replace(chr, "");
                }
            }
        }
        return text;
    }

    public static String leftPad(Integer s) {
        return StringUtils.leftPad(s.toString(), 11, "0");
    }

    public static String leftPad(String s, String add, int size) {
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

    public static MusicaDTO getMetadataByIS(InputStream input, String name, String path) {
        MusicaDTO dto = new MusicaDTO();
        try {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata);
            input.close();

            String[] metadataNames = metadata.names();
            System.out.println("NOMES");
            for (String ns : metadataNames) {
                System.out.println(ns + " -> " + metadata.get(ns));
            }
            System.out.println("FIM NOMES");
            dto.setTitulo(metadata.get("title"));
            dto.setArtistas(metadata.get("xmpDM:artist"));
            dto.setCompositor(metadata.get("xmpDM:composer"));
            dto.setGenero(metadata.get("xmpDM:genre"));
            dto.setAlbum(metadata.get("xmpDM:album"));
            dto.setNomeArquivo(name);
            dto.setCaminhoCaminho(path);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public static NtlmPasswordAuthentication getAuthSmbDefault() {
        String user = "manager.instore";
        String pass = "M#0000I";
        return new NtlmPasswordAuthentication("", user, pass);
    }

    public static NtlmPasswordAuthentication getAuthSmbDefault1921681249() {
        String user = "admin";
        String pass = "q1a2s3";
        return new NtlmPasswordAuthentication("", user, pass);
    }

    public static NtlmPasswordAuthentication getAuthSmb(String usuario, String senha) {
        if (null == usuario || usuario.isEmpty() || null == senha || senha.isEmpty()) {
            return getAuthSmbDefault();
        } else {
            return new NtlmPasswordAuthentication("", usuario, senha);
        }
    }

    public static String formatarURLConfigCliente(String url) {
        url = url.replace("\\", "/");
        if (!url.startsWith("smb")) {
            url = "smb:".concat(url.replace("\\", "/"));
        }
        return url;
    }

    public static boolean verificarArquivoFisicoExiste(String filenameSmb) {
        try {
            SmbFile file = new SmbFile(filenameSmb, Utilities.getAuthSmbDefault());
            if (file.exists()) {
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Set<Class<?>> listaClasses() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().addUrls(ClasspathHelper.forJavaClassPath()));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(XmlAnnotation.class);

        return classes;
    }

    public static Class<?> findAnnotation() {

        for (Class<?> classe : listaClasses()) {
            for (Annotation annotation : classe.getAnnotations()) {
                if (annotation.annotationType().getName().contains("XmlAnnotation")) {
                    return classe;
                }
            }
        }
        return null;
    }

    public <T> T xmlParaObjeto(String url) {
        return xmlParaObjetoPrivate(url, findAnnotation());
    }

    public <T> T xmlParaObjeto(String url, Class<?> type) {
        return xmlParaObjetoPrivate(url, type);
    }

    private static <T> T xmlParaObjetoPrivate(String url, Class<?> type) {
        T t = null;
        if (type != null) {
            try {
                URL pagina = new URL(url);
                XStream xstream = new XStream(new DomDriver());
                xstream.processAnnotations(type);
                t = (T) type.cast(xstream.fromXML(pagina));

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }

        return t;
    }

    public static String objetoParaXml(Object obj) {
        String xml = "";
        if (obj != null) {
            XStream xstream = new XStream(new DomDriver());
            xstream.processAnnotations(obj.getClass());
            xml = xstream.toXML(obj);
            return xml;
        }
        return null;
    }

    public static void createTaskLock(String path, String username, HttpServletRequest req, TaskLock taskLock) {
        try {
            deleteTaskLock(path, taskLock);
            String content = "";
            String taskLockStrCode = "001";
            content += "[" + (new SimpleDateFormat("dd/MM/yyyy")).format(new Date()) + "]\n";
            content += "[" + (new SimpleDateFormat("HH:mm:ss")).format(new Date()) + "]\n";
            content += "[" + username + "]\n";
//            content += "[" + req.getRemoteAddr() + "]\n";

            switch (taskLock) {
                case CATEGORIA:
                    taskLockStrCode = "001";
                    break;
                case PROGRAMACAO:
                    taskLockStrCode = "002";
                    break;
                case MUSICA:
                    taskLockStrCode = "003";
                    break;
                case COMERCIAL:
                    taskLockStrCode = "004";
                    break;
                case GRAVADORA:
                    taskLockStrCode = "005";
                    break;
            }
            
            NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (path.startsWith("smb://srv-arquivos")) {
                auth = Utilities.getAuthSmbDefault();
            }

            if (path.startsWith("smb://192.168.1.249")) {
                auth = Utilities.getAuthSmbDefault1921681249();
            }
            
            SmbFile smb = new SmbFile(path.concat("/").concat("task").concat(taskLockStrCode).concat(".lock"), auth);

            SmbFileOutputStream sfos = new SmbFileOutputStream(smb);
            sfos.write(content.getBytes());
            sfos.flush();
            sfos.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTaskLock(String path, TaskLock taskLock) {
        String taskLockStrCode = "001";
        switch (taskLock) {
            case CATEGORIA:
                taskLockStrCode = "001";
                break;
            case PROGRAMACAO:
                taskLockStrCode = "002";
                break;
            case MUSICA:
                taskLockStrCode = "003";
                break;
            case COMERCIAL:
                taskLockStrCode = "004";
                break;
            case GRAVADORA:
                taskLockStrCode = "005";
                break;
        }

        try {
            NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (path.startsWith("smb://srv-arquivos")) {
                auth = Utilities.getAuthSmbDefault();
            }

            if (path.startsWith("smb://192.168.1.249")) {
                auth = Utilities.getAuthSmbDefault1921681249();
            }
            
            SmbFile smb = new SmbFile(path.concat("/").concat("task").concat(taskLockStrCode).concat(".lock"), auth);
            
            if (smb.exists()) {
                smb.delete();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyTaskLock(String path, TaskLock taskLock) {
        boolean $return = true;
        try {
            String taskLockStrCode = "001";

            switch (taskLock) {
                case CATEGORIA:
                    taskLockStrCode = "001";
                    break;
                case PROGRAMACAO:
                    taskLockStrCode = "002";
                    break;
                case MUSICA:
                    taskLockStrCode = "003";
                    break;
                case COMERCIAL:
                    taskLockStrCode = "004";
                    break;
                case GRAVADORA:
                    taskLockStrCode = "005";
                    break;
            }
            
            NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (path.startsWith("smb://srv-arquivos")) {
                auth = Utilities.getAuthSmbDefault();
            }

            if (path.startsWith("smb://192.168.1.249")) {
                auth = Utilities.getAuthSmbDefault1921681249();
            }
            
            SmbFile file = new SmbFile(path.concat("/").concat("task").concat(taskLockStrCode).concat(".lock"), auth);

            if (file.exists()) {
                $return = false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            $return = false;
        } catch (IOException e) {
            e.printStackTrace();
            $return = false;
        }

        return $return;
    }

    public static String verifyInfoMessageTaskLock(String path, TaskLock taskLock) {
        String $return = "";

        String taskLockStrCode = "001";
        switch (taskLock) {
            case CATEGORIA:
                taskLockStrCode = "001";
                break;
            case PROGRAMACAO:
                taskLockStrCode = "002";
                break;
            case MUSICA:
                taskLockStrCode = "003";
                break;
            case COMERCIAL:
                taskLockStrCode = "004";
                break;
            case GRAVADORA:
                taskLockStrCode = "005";
                break;
        }

        try {
            NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (path.startsWith("smb://srv-arquivos")) {
                auth = Utilities.getAuthSmbDefault();
            }

            if (path.startsWith("smb://192.168.1.249")) {
                auth = Utilities.getAuthSmbDefault1921681249();
            }
            
            SmbFile file = new SmbFile(path.concat("/").concat("task").concat(taskLockStrCode).concat(path), auth);

            if (file.exists()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

                List<String> lines = new ArrayList<String>();
                String line;

                while (null != (line = br.readLine())) {
                    lines.add(line.replace("[", "").replace("]", ""));
                }

                String dateStr = lines.get(0);
                String hourStr = lines.get(1);
                String usernameStr = lines.get(2);
                String machineIpStr = lines.get(3);

                $return = "Lamento, não é possivel gerar os arquivos agora, na data " + dateStr + " ás " + hourStr + " o usuário " + usernameStr + " iniciou este processo e ainda não foi finalizado. Por favor tente mais tarde.";

                br.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            $return = "Erro ao verificar a trava de segurança (exclua arquivo task.lock)";
        } catch (IOException e) {
            e.printStackTrace();
            $return = "Erro ao verificar a trava de segurança (exclua arquivo task.lock)";
        }
        return $return;
    }

    public static void createLogMusica(String path, Boolean existe, String nomeArquivo) {
        try {
            NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (path.startsWith("smb://srv-arquivos")) {
                auth = Utilities.getAuthSmbDefault();
            }

            if (path.startsWith("smb://192.168.1.249")) {
                auth = Utilities.getAuthSmbDefault1921681249();
            }
            
            SmbFile file = new SmbFile(path.concat("/").concat("musica_files_").concat((existe ? "" : "not_")).concat("exists.log"), auth);
            String content = "";
            content += "[" + (new SimpleDateFormat("dd/MM/yyyy")).format(new Date()) + "] ";
            content += "" + nomeArquivo + "";
            content += "\n";

            SmbFileOutputStream sfos = new SmbFileOutputStream(file , true);
            sfos.write(content.getBytes());
            sfos.flush();
            sfos.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createLogComercial(String path, Boolean existe, String nomeArquivo) {
        try {
            
            NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (path.startsWith("smb://srv-arquivos")) {
                auth = Utilities.getAuthSmbDefault();
            }

            if (path.startsWith("smb://192.168.1.249")) {
                auth = Utilities.getAuthSmbDefault1921681249();
            }
            
            SmbFile file = new SmbFile(path.concat("/").concat("comercial_files_").concat((existe ? "" : "not_")).concat("exists.log"), auth);
            String content = "";
            content += "[" + (new SimpleDateFormat("dd/MM/yyyy")).format(new Date()) + "] ";
            content += "" + nomeArquivo + "";
            content += "\n";

            SmbFileOutputStream sfos = new SmbFileOutputStream(file, true);
            sfos.write(content.getBytes());
            sfos.flush();
            sfos.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removerLogMusica(String path) {
        try {
            NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (path.startsWith("smb://srv-arquivos")) {
                auth = Utilities.getAuthSmbDefault();
            }

            if (path.startsWith("smb://192.168.1.249")) {
                auth = Utilities.getAuthSmbDefault1921681249();
            }
            
            SmbFile file = new SmbFile(path.concat("/").concat("comercial_files_exists.log")  , auth);
            SmbFile file2 = new SmbFile(path.concat("/").concat("comercial_files_not_exists.log") , auth);

            if (file.exists()) {
                file.delete();
            }

            if (file2.exists()) {
                file2.delete();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removerLogComercial(String path) {
        try {
            NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            if (path.startsWith("smb://srv-arquivos")) {
                auth = Utilities.getAuthSmbDefault();
            }

            if (path.startsWith("smb://192.168.1.249")) {
                auth = Utilities.getAuthSmbDefault1921681249();
            }
            
            SmbFile file = new SmbFile(path.concat("/").concat("comercial_files_exists.log")  , auth);
            SmbFile file2 = new SmbFile(path.concat("/").concat("comercial_files_not_exists.log") , auth);

            if (file.exists()) {
                file.delete();
            }

            if (file2.exists()) {
                file2.delete();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String absoluteLeftPad(String str , Integer size , String replace) {
        if(str.length() > size) {
            str = str.substring(0 , size);
        } 
        
        if(str.length() < size) {
            str = StringUtils.leftPad(str, size, replace);
        }
        return str;
    }
}
