package br.com.instore.web.component.request;

import br.com.instore.core.orm.DataValidatorException;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import java.util.List;
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
import br.com.instore.core.orm.Each;
import br.com.instore.web.tools.AjaxResult;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

@Component
@RequestScoped
public class RequestDumpParadox {

    private final static Integer idclienteFinal = 1063;
    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;
//c:/Users/instore/Desktop/paradox

    public RequestDumpParadox(SessionRepository repository, SessionUsuario sessionUsuario, Result result) {
        this.repository = repository;
        this.sessionUsuario = sessionUsuario;
        this.result = result;
    }

    public void gerarDump(String path) {

        path = "jdbc:paradox:/".concat(path);
        try {
            lerCategoriasDoBanco(path);
            lerProgramacaoDoBanco(path);
            lerComercialDoBanco(path);
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
            rs.close();
            con.close();
        } catch (Exception e) {
            throw new Exception("Arquivo não encontrado");
        }
    }

    public void lerProgramacaoDoBanco(String url) throws Exception {
        try {
            Class.forName("com.hxtt.sql.paradox.ParadoxDriver").newInstance();

            Connection con = DriverManager.getConnection(url, "", "");

            String sql = "select * from programacao";

            Statement stmt = con.createStatement();
            stmt.setFetchSize(10);

            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int iNumCols = resultSetMetaData.getColumnCount();

            String inserts = "";
            while (rs.next()) {
                Short dia = null, mes = null, ano = null, diaf = null, mesf = null, anof = null;
                inserts = "INSERT INTO audiostore_programacao VALUES(null, [descricao], " + idclienteFinal + ", [data_inicio], [data_final], [hora_inicio] , [hora_final] , [segunda_feira], [terca_feira] , [quarta_feira], [quinta_feira], [sexta_feira] , [sabado] , [domingo] , [conteudo], [loopback]);";
                // [id], [descricao], [idcliente], [data_inicio], [data_final], [hora_inicio] , [hora_final] , [segunda_feira], [terca_feira] , [quarta_feira], [quinta_feira], [sexta_feira] , [sabado] , [domingo] , [conteudo], [loopback]
                for (int i = 1; i <= iNumCols; i++) {

                    if ("Descricao".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getString(i);
                        inserts = inserts.replace("[descricao]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Dia".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        dia = rs.getShort(i);
                    }

                    if ("Mes".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        mes = rs.getShort(i);
                    }

                    if ("Ano".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        ano = rs.getShort(i);
                    }

                    if ("Diaf".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        diaf = rs.getShort(i);
                    }

                    if ("Mesf".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        mesf = rs.getShort(i);
                    }

                    if ("Anof".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        anof = rs.getShort(i);
                    }

                    if (null != dia && null != mes && null != ano) {
                        String di = "" + ano + "-" + mes + "-" + dia;
                        inserts = inserts.replace("[data_inicio]", "'" + di + "'");
                    }

                    if (null != diaf && null != mesf && null != anof) {
                        String df = "" + anof + "-" + mesf + "-" + diaf;
                        inserts = inserts.replace("[data_final]", "'" + df + "'");
                    }

                    if ("HoraInicio".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + new SimpleDateFormat("HH:mm:ss").format(rs.getDate(i));
                        inserts = inserts.replace("[hora_inicio]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("HoraFinal".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + new SimpleDateFormat("HH:mm:ss").format(rs.getDate(i));
                        inserts = inserts.replace("[hora_final]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("DiaSemana".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getString(i);
                        try {
                            if ("X".equals(value.substring(0, 1))) {
                                inserts = inserts.replace("[segunda_feira]", "'1'");
                            } else {
                                inserts = inserts.replace("[segunda_feira]", "'0'");
                            }

                        } catch (StringIndexOutOfBoundsException e) {
                            inserts = inserts.replace("[segunda_feira]", "'0'");
                        }

                        try {
                            if ("X".equals(value.substring(1, 2))) {
                                inserts = inserts.replace("[terca_feira]", "'1'");
                            } else {
                                inserts = inserts.replace("[terca_feira]", "'0'");
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            inserts = inserts.replace("[terca_feira]", "'0'");
                        }

                        try {
                            if ("X".equals(value.substring(2, 3))) {
                                inserts = inserts.replace("[quarta_feira]", "'1'");
                            } else {
                                inserts = inserts.replace("[quarta_feira]", "'0'");
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            inserts = inserts.replace("[quarta_feira]", "'0'");
                        }

                        try {
                            if ("X".equals(value.substring(3, 4))) {
                                inserts = inserts.replace("[quinta_feira]", "'1'");
                            } else {
                                inserts = inserts.replace("[quinta_feira]", "'0'");
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            inserts = inserts.replace("[quinta_feira]", "'0'");
                        }

                        try {
                            if ("X".equals(value.substring(4, 5))) {
                                inserts = inserts.replace("[sexta_feira]", "'1'");
                            } else {
                                inserts = inserts.replace("[sexta_feira]", "'0'");
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            inserts = inserts.replace("[sexta_feira]", "'0'");
                        }

                        try {
                            if ("X".equals(value.substring(5, 6))) {
                                inserts = inserts.replace("[sabado]", "'1'");
                            } else {
                                inserts = inserts.replace("[sabado]", "'0'");
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            inserts = inserts.replace("[sabado]", "'0'");
                        }

                        try {
                            if ("X".equals(value.substring(6, 7))) {
                                inserts = inserts.replace("[domingo]", "'1'");
                            } else {
                                inserts = inserts.replace("[domingo]", "'0'");
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            inserts = inserts.replace("[domingo]", "'0'");
                        }
                    }

                    if ("Conteudo".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + (null == rs.getString(i) ? "" : rs.getString(i));
                        inserts = inserts.replace("[conteudo]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("LoopBack".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + (rs.getBoolean(i) ? "1" : "0");
                        inserts = inserts.replace("[loopback]", "'" + value.replace("'", "\"") + "'");
                    }

                    if (resultSetMetaData.getColumnLabel(i).trim().contains("Categoria")) {
                        String value = "" + (0 == rs.getShort(i) ? "" : "" + (rs.getShort(i) + idclienteFinal));

                        if (null != value && !value.trim().isEmpty()) {
                            inserts += "\nINSERT INTO audiostore_programacao_categoria VALUES(null, [ccc], (select id from audiostore_programacao order by id desc limit 1));";
                            inserts = inserts.replace("[ccc]", "'" + value.replace("'", "\"") + "'");
                        }
                    }
                }
            }

            rs.close();
            con.close();
        } catch (Exception e) {
            throw new Exception("Arquivo não encontrado");
        }
    }

    public void lerComercialDoBanco(String url) throws Exception, DataValidatorException {
        final List<Integer> countList = new ArrayList<Integer>();

        repository.query("select count(codigo) as count, '' as param from audiostore_categoria where idcliente = " + idclienteFinal + " and categoria = 'NENHUM'").executeSQL(new Each() {
            public BigInteger count;
            public String param;

            public void each() {
                countList.add(count.intValue());

            }
        });

        if (null != countList && !countList.isEmpty()) {
            if (countList.get(0) <= 0) {
                try {
                    repository.query("INSERT INTO audiostore_categoria VALUES (null, '" + idclienteFinal + "', 'NENHUM', '2001-01-01', '2050-12-31', '1', '00:00:00', '001');").executeSQLCommand2();
                } catch (DataValidatorException e) {
                    e.printStackTrace();
                    throw new DataValidatorException("Arquivo não encontrado");
                }
            }
        } else {
            try {
                repository.query("INSERT INTO audiostore_categoria VALUES (null, '" + idclienteFinal + "', 'NENHUM', '2001-01-01', '2050-12-31', '1', '00:00:00', '001');").executeSQLCommand2();
            } catch (DataValidatorException e) {
                e.printStackTrace();
                throw new DataValidatorException("Arquivo não encontrado");
            }
        }

        try {
            Class.forName("com.hxtt.sql.paradox.ParadoxDriver").newInstance();

            Connection con = DriverManager.getConnection(url, "", "");

            String sql = "select * from comercial";

            Statement stmt = con.createStatement();
            stmt.setFetchSize(10);

            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int iNumCols = resultSetMetaData.getColumnCount();

            for (int i = 1; i <= iNumCols; i++) {
                // System.out.println(resultSetMetaData.getColumnLabel(i) + "  " + resultSetMetaData.getColumnTypeName(i));
            }

            String inserts = "";
            while (rs.next()) {
                inserts = "\nINSERT INTO audiostore_comercial VALUES(null , [categoria] , [arquivo], [titulo] , [tipo_interprete] , [periodo_inicial] , [periodo_final] , [tipo_horario] , [dias_semana] , [dias_alternados] , [data] , [ultima_execucao] , [tempo_total] , [random] , [qtde_player] , [qtde] , [data_vencimento], [dependencia1] , [dependencia2] , [dependencia3] , [frame_inicio] , [frame_final] , [msg] , [sem_som] , " + idclienteFinal + " , 0  , '' )";

                for (int i = 1; i <= iNumCols; i++) {

                    if ("Random".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + (rs.getInt(i));
                        inserts = inserts.replace("[random]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Categoria".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + (rs.getShort(i) + idclienteFinal);
                        if (0 == rs.getShort(i)) {
                            inserts = inserts.replace("[categoria]", "(select codigo from audiostore_categoria where idcliente = " + idclienteFinal + " order by codigo desc limit 1)");
                        } else {
                            inserts = inserts.replace("[categoria]", "'" + value.replace("'", "\"") + "'");
                        }

                    }

                    if ("Arquivo".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getString(i);
                        inserts = inserts.replace("[arquivo]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Titulo".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getString(i);
                        inserts = inserts.replace("[titulo]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("TipoInterprete".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getShort(i);
                        inserts = inserts.replace("[tipo_interprete]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("PeriodoInicial".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getDate(i));
                        inserts = inserts.replace("[periodo_inicial]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("PeriodoFinal".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getDate(i));
                        inserts = inserts.replace("[periodo_final]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("TipoHorario".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getShort(i);
                        inserts = inserts.replace("[tipo_horario]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("DiaSemana".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getString(i);
                        inserts = inserts.replace("[dias_semana]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("DiasAlternados".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + (rs.getBoolean(i) ? "1" : "0");
                        inserts = inserts.replace("[dias_alternados]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Data".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getDate(i));
                        inserts = inserts.replace("[data]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("UltimaExecucao".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getDate(i));
                        inserts = inserts.replace("[ultima_execucao]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("TempoTotal".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + new SimpleDateFormat("HH:mm:ss").format(rs.getDate(i));
                        inserts = inserts.replace("[tempo_total]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("QtdePlayer".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getInt(i);
                        inserts = inserts.replace("[qtde_player]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Qtde".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getInt(i);
                        inserts = inserts.replace("[qtde]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("DataVencto".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getDate(i));
                        inserts = inserts.replace("[data_vencimento]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Dependencia1".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getString(i);
                        inserts = inserts.replace("[dependencia1]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Dependencia2".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getString(i);
                        inserts = inserts.replace("[dependencia2]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Dependencia3".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getString(i);
                        inserts = inserts.replace("[dependencia3]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("FrameInicio".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getInt(i);
                        inserts = inserts.replace("[frame_inicio]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("FrameFinal".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + rs.getInt(i);
                        inserts = inserts.replace("[frame_final]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("Msg".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + (null == rs.getString(i) ? "" : rs.getString(i));
                        inserts = inserts.replace("[msg]", "'" + value.replace("'", "\"") + "'");
                    }

                    if ("SemSom".equals(resultSetMetaData.getColumnLabel(i).trim())) {
                        String value = "" + (rs.getBoolean(i) ? "1" : "0");
                        inserts = inserts.replace("[sem_som]", "'" + value.replace("'", "\"") + "'");
                    }
                }
                inserts += ";";

                for (int i = 8; i < 56; i = i + 2) {
                    String sm1 = (null == rs.getString(8) ? "0" : rs.getString(i + 1));
                    Date hr1d = rs.getDate(i + 2);
//                    System.out.println(resultSetMetaData.getColumnLabel(1).trim() +" -> " + rs.getString(1) + "  -> " + resultSetMetaData.getColumnLabel(i+1).trim() + " : " + sm1);
                    String hr1 = "" + new SimpleDateFormat("HH:mm:ss").format(null == hr1d ? new Date() : hr1d);

                    if (null != sm1 && !sm1.trim().equals("0")) {

                        boolean segunda = (!sm1.substring(0, 1).equals(" ") ? true : false);
                        boolean segundaNX = (!sm1.substring(0, 1).equals("N") ? true : false);

                        boolean terca = (!sm1.substring(1, 2).equals(" ") ? true : false);
                        boolean tercaNX = (!sm1.substring(1, 2).equals("N") ? true : false);

                        boolean quarta = (!sm1.substring(2, 3).equals(" ") ? true : false);
                        boolean quartaNX = (!sm1.substring(2, 3).equals("N") ? true : false);

                        boolean quinta = (!sm1.substring(3, 4).equals(" ") ? true : false);
                        boolean quintaNX = (!sm1.substring(3, 4).equals("N") ? true : false);

                        boolean sexta = (!sm1.substring(4, 5).equals(" ") ? true : false);
                        boolean sextaNX = (!sm1.substring(4, 5).equals("N") ? true : false);

                        boolean sabado = (!sm1.substring(5, 6).equals(" ") ? true : false);
                        boolean sabadoNX = (!sm1.substring(5, 6).equals("N") ? true : false);

                        boolean domingo = (!sm1.substring(6, 7).equals(" ") ? true : false);
                        boolean domingoNX = (!sm1.substring(6, 7).equals("N") ? true : false);

                        if (segunda) {
                            inserts += "\nINSERT INTO audiostore_comercial_sh VALUES(null , (select id from audiostore_comercial order by id desc limit 1) , 'segunda' , '" + hr1 + "' , '" + (segundaNX ? 1 : 0) + "' );";
                        }

                        if (terca) {
                            inserts += "\nINSERT INTO audiostore_comercial_sh VALUES(null , (select id from audiostore_comercial order by id desc limit 1) , 'terca' , '" + hr1 + "' , '" + (tercaNX ? 1 : 0) + "' );";
                        }

                        if (quarta) {
                            inserts += "\nINSERT INTO audiostore_comercial_sh VALUES(null , (select id from audiostore_comercial order by id desc limit 1) , 'quarta' , '" + hr1 + "' , '" + (quartaNX ? 1 : 0) + "' );";
                        }

                        if (quinta) {
                            inserts += "\nINSERT INTO audiostore_comercial_sh VALUES(null , (select id from audiostore_comercial order by id desc limit 1) , 'quinta' , '" + hr1 + "' , '" + (quintaNX ? 1 : 0) + "' );";
                        }

                        if (sexta) {
                            inserts += "\nINSERT INTO audiostore_comercial_sh VALUES(null , (select id from audiostore_comercial order by id desc limit 1) , 'sexta' , '" + hr1 + "' , '" + (sextaNX ? 1 : 0) + "' );";
                        }

                        if (sabado) {
                            inserts += "\nINSERT INTO audiostore_comercial_sh VALUES(null , (select id from audiostore_comercial order by id desc limit 1) , 'sabado' , '" + hr1 + "' , '" + (sabadoNX ? 1 : 0) + "' );";
                        }

                        if (domingo) {
                            inserts += "\nINSERT INTO audiostore_comercial_sh VALUES(null , (select id from audiostore_comercial order by id desc limit 1) , 'domingo' , '" + hr1 + "' , '" + (domingoNX ? 1 : 0) + "' );";
                        }
                    }
                }
                System.out.println(inserts);
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Arquivo não encontrado");
        }
    }
}
