
import br.com.instore.core.orm.bean.MusicaGeralBean;
import br.com.instore.web.tools.Utilities;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class Main {

    public static void main(String[] args) {
        double d = -39235.45;
        System.out.println(formatDecimal(d));;

        double money = d;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(money);
        System.out.println(moneyString);

        if (moneyString.endsWith(".00")) {
            int centsIndex = moneyString.lastIndexOf(".00");
            if (centsIndex != -1) {
                moneyString = moneyString.substring(1, centsIndex);
            }
        }
        
        System.out.println(moneyString);
    }

    public static String formatDecimal(double number) {
        float epsilon = 0.004f; // 4 tenths of a cent
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%10.0f", number); // sdb
        } else {
            return String.format("%10.2f", number); // dj_segfault
        }
    }

    public static void main2(String[] args) {
        String dirPath = "smb://192.168.1.249/Clientes/audiostore/teste_alex/ibm/musicas/";
        List<MusicaGeralBean> musicaGeralBeanList = new ArrayList<MusicaGeralBean>();
        findFile(dirPath, musicaGeralBeanList);

        for (MusicaGeralBean mus : musicaGeralBeanList) {
            System.out.println(mus);
        }
    }

    public static void findFile(String dirPath, List<MusicaGeralBean> musicaGeralBeanList) {
        try {
            SmbFile smbDir = new SmbFile(dirPath, Utilities.getAuthSmb());

            for (SmbFile item : smbDir.listFiles()) {
                if (item.isFile()) {
                    if (item.getName().indexOf(".mp3") != -1
                            || item.getName().indexOf(".wave") != -1
                            || item.getName().indexOf(".wav") != -1
                            || item.getName().indexOf(".mp4") != -1) {
                        MusicaGeralBean m = new MusicaGeralBean();
                        m.setCategoriaGeral(0);
                        m.setGravadora(0);
                        m.setUsuario(0);
                        m.setArquivo(dirPath + item.getName() + "/");
                        m.setTitulo(item.getName());
                        m.setInterprete("");
                        m.setTipoInterprete(new Short("1"));
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
                        findFile(item.getPath(), musicaGeralBeanList);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        }
    }

    public static void main3(String[] args) {
        try {
            Class.forName("com.hxtt.sql.paradox.ParadoxDriver").newInstance();

            String url = "jdbc:paradox:/C:/Users/TI-Caio/Desktop/banco_paradox/";

            Connection con = DriverManager.getConnection(url, "", "");

            String sql = "select * from musica";
            Statement stmt = con.createStatement();
            stmt.setFetchSize(10);
            ResultSet rs = stmt.executeQuery(sql);


            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int iNumCols = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= iNumCols; i++) {
                System.out.println("COLUNA " + resultSetMetaData.getColumnLabel(i) + " TIPO " + resultSetMetaData.getColumnTypeName(i));
            }

            System.out.println("--------------------------------------------------------------");

            Object colval;
            while (rs.next()) {
                for (int i = 1; i <= iNumCols; i++) {
                    colval = rs.getObject(i);
                    System.out.print(colval + " | ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
//    public static void main1(String[] args) {
//        try {
//            String user = "administrativo";
//            String pass = "";
//
//            String sharedFolder = "shared";
//            String path = "smb://ftp/" + sharedFolder + "/test.txt";
//            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", user, pass);
//            SmbFile smbFile = new SmbFile(path, auth);
//            if (!smbFile.exists()) {
//            } else {
//
//                for (SmbFile item : smbFile.listFiles()) {
//                }
//            }
//            SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
//            smbfos.write("testing....and writing to a file".getBytes());
//            System.out.println("completed ...nice !");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (SmbException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
