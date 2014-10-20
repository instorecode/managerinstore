
import org.apache.commons.lang.StringUtils;

public class Main {

    public static void main(String[] args) {
//        \\192.168.1.249\Clientes\audiostore\NovoSistema\Impecavel\musica
        String texto =  formatarURLConfigCliente("aa");
        
        if(texto.equals("smb://")){
             System.out.println("erro "+texto);
           }else{
            System.out.println("validou "+texto);
        }
        
     

    }

    public static String formatarURLConfigCliente(String url) {
        if (!url.endsWith("/")) {
            url += "/";
            
        }

        url = url.replace("smb://", "$$");
        url = url.replace("\\", "/");
        url = url.replace("//", "/");
        url = url.replace("$$", "smb://");

        if (url.startsWith("/")) {
            url = url.substring(1, url.length());
        }

        if (!url.startsWith("smb://")) {
            url = "smb://" + url;
        }
        System.out.println(StringUtils.countMatches(url, "smb://"));
        if (StringUtils.countMatches(url, "smb://") > 1) {            
            url = url.replace("smb://", "");
            url = "smb://" + url;
        }
        return  url;
    }
}
