
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String string = "01/02/2014";
        try {
            System.out.println( new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
