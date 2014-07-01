
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] infoBin = null;
        infoBin = "ola mundo".getBytes("UTF-8");
        String binary = "";
        for (byte b : infoBin) {
            binary += " " + Integer.toBinaryString(b).toString();
        }
        System.out.println(binary);
    }
}
