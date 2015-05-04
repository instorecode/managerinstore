
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException {
    
    }

    static {
        System.load("C:\\WINDOWS\\system32\\kernel32.dll");
    }

    native private static short GetSystemDefaultLangID();

    public static void main22(String[] args) {
        System.out.println();
    }

    public static void main3(String[] args) {
        try {
            SmbFile sf = new SmbFile("smb://srv-arquivos/TESTE/musicas/", Utilities.getAuthSmbDefault());
            int i = 1;
            for (SmbFile item : sf.listFiles()) {
                System.out.println("INSERT INTO musica_geral (id,comun_jamendo_megatrax,codigo_interno,codigo_externo,velocidade,categoria_geral,usuario,gravadora,titulo,interprete,tipo_interprete,letra,bpm,tempo_total,ano_gravacao,afinidade1,afinidade2,afinidade3,afinidade4,arquivo,ultima_importacao,data_cadastro) VALUES (" + i + ",NULL,NULL,NULL,NULL,7,1,0,'POR TESTE - " + item.getName() + "','" + item.getName() + "',0,'',0,'00:03:49',0,'','','','','" + item.getPath() + "',0,'2015-01-22');");
                i++;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        }
    }

    public static void main2(String[] args) {
        SmbFileOutputStream sfous = null;
        try {
            sfous = new SmbFileOutputStream(new SmbFile("smb://192.168.1.249/Clientes/POSTOSANMARINO/ENVIO/Intranet/musica.exp", Utilities.getAuthSmbDefault()), true);
            StringBuilder conteudo = new StringBuilder();
            for (int i = 0; i < 20000; i++) {
                InputStream is = new ByteArrayInputStream("Em um ninho de mafagafos haviam sete mafagafinhos; quem amafagafar mais mafagafinhos, bom amagafanhador será. Em um ninho de mafagafos haviam sete mafagafinhos; quem amafagafar mais mafagafinhos, bom amagafanhador será. Em um ninho de mafagafos haviam sete mafagafinhos; quem amafagafar mais mafagafinhos, bom amagafanhador será. Em um ninho de mafagafos haviam sete mafagafinhos; quem amafagafar mais mafagafinhos, bom amagafanhador será. Em um ninho de mafagafos haviam sete mafagafinhos; quem amafagafar mais mafagafinhos, bom amagafanhador será. Em um ninho de mafagafos haviam sete mafagafinhos; quem amafagafar mais mafagafinhos, bom amagafanhador será".getBytes());

                byte[] buf = new byte[4096];
                int len;

                while ((len = is.read(buf, 0, buf.length)) >= 0) {
                    sfous.write(buf, 0, len);
                }
            }
            sfous.flush();
            sfous.close();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sfous.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
