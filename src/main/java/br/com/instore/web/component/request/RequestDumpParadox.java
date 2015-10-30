package br.com.instore.web.component.request;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import org.apache.commons.lang.StringUtils;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.web.tools.AjaxResult;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequestScoped
public class RequestDumpParadox {

    public final static Integer idclienteFinal = 1063;
    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;

    public RequestDumpParadox(SessionRepository repository, SessionUsuario sessionUsuario, Result result) {
        this.repository = repository;
        this.sessionUsuario = sessionUsuario;
        this.result = result;
    }

    public void gerarDump(String path) {
        String msg = "OK";
        Boolean success = true;
        // String urlparadox = "jdbc:paradox:/c:/Users/instore/Desktop/paradox";
        try {
            System.out.println(path);
            lerCategoriasDoBanco(path);
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dump realizado com sucesso")).recursive().serialize();
        } catch (Exception e) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, e.getMessage())).recursive().serialize();
        }
    }

    public void lerCategoriasDoBanco(String url) throws Exception {
        try {
            Class.forName("com.hxtt.sql.paradox.ParadoxDriver").newInstance();

            Connection con = DriverManager.getConnection(url, "", "");

            String sql = "select * from categoria";

            Statement stmt = con.createStatement();
            stmt.setFetchSize(10);

            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int iNumCols = resultSetMetaData.getColumnCount();

            String inserts = "";
            String inserts2 = "";
            while (rs.next()) {
                inserts += "\n";
                inserts2 += "\n";
                inserts += "INSERT INTO categoria_geral VALUES([C],1,[T]);";
                inserts2 += "INSERT INTO audiostore_categoria VALUES([codigo], " + idclienteFinal + ", [categoria],  [data_inicio],  [data_final],  [tipo],  [tempo],  [cod_interno]);";

                for (int i = 1; i <= iNumCols; i++) {

                    if ("Codigo".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + (rs.getShort(i) + idclienteFinal);
                        String cintern = ("" + rs.getShort(i)).replace("'", "\"");

                        if (cintern.length() > 3) {
                            cintern = cintern.substring(0, 3);
                        } else {
                            if (cintern.length() < 3) {
                                cintern = StringUtils.leftPad(cintern, 3, "0");
                            }
                        }

                        inserts = inserts.replace("[C]", "'" + value.replace("'", "\"") + "'");
                        inserts2 = inserts2.replace("[codigo]", "'" + value.replace("'", "\"") + "'");
                        inserts2 = inserts2.replace("[cod_interno]", "'" + cintern + "'");
                    }

                    if ("Categoria".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = rs.getString(i);
                        inserts = inserts.replace("[T]", "'" + value.replace("'", "\"") + "'");
                        inserts2 = inserts2.replace("[categoria]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("DataInicio".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate(i));
                        inserts2 = inserts2.replace("[data_inicio]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("DataFinal".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate(i));
                        inserts2 = inserts2.replace("[data_final]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Tipo".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = rs.getString(i);
                        inserts2 = inserts2.replace("[tipo]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Tempo".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = (null != rs.getDate(i) ? new SimpleDateFormat("HH:mm:ss").format(rs.getDate(i)) : "00:00:00");
                        inserts2 = inserts2.replace("[tempo]", "'" + value.replace("'", "\"") + "'");
                    }
                }
            }
            System.out.println(inserts);
            rs.close();
            con.close();
        } catch (Exception e) {
            throw new Exception("Arquivo não encontrado");
        }
    }
}
