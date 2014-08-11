
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

public class Main {

    public static void main(String[] args) {
        

    }

    public static void main1(String[] args) {
        try {
            String user = "administrativo";
            String pass = "";

            String sharedFolder = "shared";
            String path = "smb://ftp/" + sharedFolder + "/test.txt";
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", user, pass);
            SmbFile smbFile = new SmbFile(path, auth);
            if (!smbFile.exists()) {
            } else {

                for (SmbFile item : smbFile.listFiles()) {
                }
            }
            SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
            smbfos.write("testing....and writing to a file".getBytes());
            System.out.println("completed ...nice !");
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
}
