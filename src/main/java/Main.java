
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

public class Main {
    public static void main(String[] args) {
      
    }
    public static void main2(String[] args) {
        SmbFileOutputStream sfous = null;
        try {
            sfous = new SmbFileOutputStream(new SmbFile("smb://192.168.1.249/Clientes/POSTOSANMARINO/ENVIO/Intranet/musica.exp" , Utilities.getAuthSmbDefault()), true);
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
