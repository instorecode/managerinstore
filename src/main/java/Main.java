
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.RepositoryViewer;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        for (int riv = 0; riv < 10; riv++) {
            String dialog1 =  ""+riv;
            String dialog2 =  ""+riv;
            System.out.println("to chamando meu dialog"+dialog1);
            System.out.println("to chamando meu dialog"+dialog2);
        }
    }
}
