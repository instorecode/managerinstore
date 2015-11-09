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
import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Bean;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreComercialShBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.web.tools.AjaxResult;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequestScoped
public class RequestDumpParadox {

    private final static Integer idclienteFinal = 1063;
    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;
//    private String path = "jdbc:paradox:smb://manager.instore:M#0000I@192.168.1.200/DESENVOLVIMENTO/dump_paradox/";
    private String path = "jdbc:paradox:/C:/Users/instore/Desktop/paradox2/";
    private List<String> insertsProgramacaoVersao = new ArrayList<String>();

    public ResultSet rs = null;
    public ResultSetMetaData resultSetMetaData = null;
    public int iNumCols = 0;
    private List<AudiostoreCategoriaBean> audiostoreCategoriaBeans = new ArrayList<AudiostoreCategoriaBean>();
    private List<AudiostoreComercialShBean> listaComerciaisShBean = new ArrayList<AudiostoreComercialShBean>();
    private HashMap<String, Bean> map = new HashMap<String, Bean>();

    public RequestDumpParadox(SessionRepository repository, SessionUsuario sessionUsuario, Result result) {
        this.repository = repository;
        this.sessionUsuario = sessionUsuario;
        this.result = result;
    }

    public static void main(String[] args) {
        new RequestDumpParadox(new SessionRepository(), new SessionUsuario(), new Result() {

            @Override
            public Result include(String key, Object value) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Result include(Object value) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T extends View> T use(Class<T> view) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Result on(Class<? extends Exception> exception) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean used() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Map<String, Object> included() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void forwardTo(String uri) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void redirectTo(String uri) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T forwardTo(Class<T> controller) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T redirectTo(Class<T> controller) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T of(Class<T> controller) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T redirectTo(T controller) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T forwardTo(T controller) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T of(T controller) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void nothing() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void notFound() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void permanentlyRedirectTo(String uri) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T permanentlyRedirectTo(Class<T> controller) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T permanentlyRedirectTo(T controller) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }).gerarDump();

    }

    public void gerarDump() {
        String error = null;
        try {
            popularCategoriaBean();
        } catch (SQLException e) {
            error = e.getMessage();
        } catch (InstantiationException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (IllegalAccessException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (Exception e) {
            error.concat("\n").concat(e.getMessage());
        }

        try {
            popularProgramacaoBean();
        } catch (ClassNotFoundException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (SQLException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (InstantiationException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (IllegalAccessException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (Exception e) {
            error.concat("\n").concat(e.getMessage());
        }

        try {
            popularComercialBean();
        } catch (ParseException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (SQLException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (InstantiationException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (IllegalAccessException e) {
            error.concat("\n").concat(e.getMessage());
        } catch (Exception e) {
            error.concat("\n").concat(e.getMessage());
        }

        try {
            popularComercialSh();
        } catch (Exception e) {
            error.concat("\n").concat(e.getMessage());
        }
        
        System.out.println(null == error);
        
        if(null == error || error.isEmpty()){
            //result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dump realizado com sucesso")).recursive().serialize();
        } else {
            //result.use(Results.json()).withoutRoot().from(new AjaxResult(false, error)).recursive().serialize();
        }
    }

    private void armarzenarResultSet(String tableName) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, Exception {
        Connection con = null;
        try {
            Class.forName("com.hxtt.sql.paradox.ParadoxDriver").newInstance();

            con = DriverManager.getConnection(path, "", "");

            String sql = "select * from ".concat(tableName);

            Statement stmt = con.createStatement();
            stmt.setFetchSize(10);

            rs = stmt.executeQuery(sql);

            resultSetMetaData = rs.getMetaData();
            iNumCols = resultSetMetaData.getColumnCount();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new InstantiationException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalAccessException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private void popularCategoriaBean() throws ParseException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, Exception {
        try {
            armarzenarResultSet("Categoria");
//        repository.setUsuario(sessionUsuario.getUsuarioBean());
            repository.setUsuario(new UsuarioBean(22));
            while (rs.next()) {
                AudiostoreCategoriaBean audiostoreCategoriaBean = new AudiostoreCategoriaBean();
                audiostoreCategoriaBean.setCategoria(rs.getString(2));
                audiostoreCategoriaBean.setCliente(new ClienteBean(idclienteFinal));
                audiostoreCategoriaBean.setCodInterno(new Short(rs.getShort(1)).toString());
                audiostoreCategoriaBean.setCodigo(null);
                audiostoreCategoriaBean.setDataFinal(rs.getDate(4));
                audiostoreCategoriaBean.setDataInicio(rs.getDate(3));
                audiostoreCategoriaBean.setTempo(rs.getDate(6));
                audiostoreCategoriaBean.setTipo(rs.getShort(5));

                audiostoreCategoriaBean.setCodigo(repository.save(audiostoreCategoriaBean));
                audiostoreCategoriaBeans.add(audiostoreCategoriaBean);
                map.put(audiostoreCategoriaBean.getCodInterno(), audiostoreCategoriaBean);
                repository.finalize();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new InstantiationException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalAccessException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private void popularProgramacaoBean() throws ParseException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, Exception {
        try {
            armarzenarResultSet("Programacao");
//        repository.setUsuario(sessionUsuario.getUsuarioBean());
            repository.setUsuario(new UsuarioBean(22));
            while (rs.next()) {
                AudiostoreProgramacaoBean programacao = new AudiostoreProgramacaoBean();
                programacao.setDescricao(rs.getString(1));
                programacao.setCliente(new ClienteBean(idclienteFinal));
                programacao.setDataInicio(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(4) + "-" + rs.getString(3) + "-" + rs.getString(2)));
                programacao.setDataFinal(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(7) + "-" + rs.getString(6) + "-" + rs.getString(5)));

                programacao.setHoraInicio(new SimpleDateFormat("HH:mm:ss").parse(new SimpleDateFormat("HH:mm:ss").format(rs.getDate(9))));
                programacao.setHoraFinal(new SimpleDateFormat("HH:mm:ss").parse(new SimpleDateFormat("HH:mm:ss").format(rs.getDate(10))));

                String diasSemana = rs.getString(8);
                int tamanho = diasSemana.length();

                boolean segunda = false;
                boolean terca = false;
                boolean quarta = false;
                boolean quinta = false;
                boolean sexta = false;
                boolean sabado = false;
                boolean domingo = false;

                if (null != diasSemana && tamanho < 8 && tamanho > 0) {
                    segunda = (tamanho == 1) ? diasSemana.substring(0, 1).equalsIgnoreCase("x") : diasSemana.substring(0, tamanho - (tamanho - 1)).equalsIgnoreCase("x");
                    terca = (tamanho == 1) ? diasSemana.substring(0, 1).equalsIgnoreCase("x") : (tamanho >= 2) ? diasSemana.substring(0, tamanho - (tamanho - 2)).equalsIgnoreCase("x") : false;
                    quarta = (tamanho == 1) ? diasSemana.substring(0, 1).equalsIgnoreCase("x") : (tamanho >= 3) ? diasSemana.substring(0, tamanho - (tamanho - 3)).equalsIgnoreCase("x") : false;
                    quinta = (tamanho == 1) ? diasSemana.substring(0, 1).equalsIgnoreCase("x") : (tamanho >= 4) ? diasSemana.substring(0, tamanho - (tamanho - 4)).equalsIgnoreCase("x") : false;
                    sexta = (tamanho == 1) ? diasSemana.substring(0, 1).equalsIgnoreCase("x") : (tamanho >= 5) ? diasSemana.substring(0, tamanho - (tamanho - 5)).equalsIgnoreCase("x") : false;
                    sabado = (tamanho == 1) ? diasSemana.substring(0, 1).equalsIgnoreCase("x") : (tamanho >= 6) ? diasSemana.substring(0, tamanho - (tamanho - 6)).equalsIgnoreCase("x") : false;
                    domingo = (tamanho == 7) ? diasSemana.substring(6, 7).equalsIgnoreCase("x") : (tamanho == 7) ? diasSemana.substring(6, tamanho - (tamanho - 7)).equalsIgnoreCase("x") : false;
                }

                programacao.setSegundaFeira(segunda);
                programacao.setTercaFeira(terca);
                programacao.setQuartaFeira(quarta);
                programacao.setQuintaFeira(quinta);
                programacao.setSextaFeira(sexta);
                programacao.setSabado(sabado);
                programacao.setDomingo(domingo);

                programacao.setConteudo((null == rs.getString(35) || rs.getString(35).isEmpty()) ? "" : rs.getString(35));
                programacao.setLoopback(rs.getBoolean(36));

                Integer idProgramacao = repository.save(programacao);
                repository.finalize();
                programacao.setId(idProgramacao);

                for (int i = 11; i <= 34; i++) {
                    AudiostoreCategoriaBean categoria = (AudiostoreCategoriaBean) map.get(rs.getString(i));

                    if (null != categoria) {
                        AudiostoreProgramacaoCategoriaBean programacaoCategoria = new AudiostoreProgramacaoCategoriaBean();
                        programacaoCategoria.setAudiostoreCategoria(categoria);
                        programacaoCategoria.setAudiostoreProgramacao(programacao);
                        repository.save(programacaoCategoria);
                    }
                }
                repository.finalize();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ParseException(e.getMessage(), 1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new InstantiationException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalAccessException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }

    private void popularComercialBean() throws SQLException, ParseException, InstantiationException, IllegalAccessException, Exception {
        try {
            //        repository.setUsuario(sessionUsuario.getUsuarioBean());
            repository.setUsuario(new UsuarioBean(22));
            //ClienteBean cliente = sessionUsuario.getCliente();

            armarzenarResultSet("comercial");
            while (rs.next()) {
                AudiostoreComercialBean comercial = new AudiostoreComercialBean();
                comercial.setArquivo(rs.getString(1));
                comercial.setCliente(new ClienteBean(idclienteFinal));
                comercial.setTitulo(rs.getString(3));
                comercial.setTipoInterprete(rs.getShort(4));
                comercial.setAudiostoreCategoria((AudiostoreCategoriaBean) map.get(rs.getString(5)));
                comercial.setPeriodoInicial(rs.getDate(6));
                comercial.setPeriodoFinal(rs.getDate(7));
                comercial.setTipoHorario(rs.getShort(8));

                comercial.setDiasSemana(new Short("7"));
                comercial.setDiasAlternados(rs.getBoolean(58));
                comercial.setData(rs.getDate(59));
                comercial.setUltimaExecucao(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(60)));
                comercial.setTempoTotal(new SimpleDateFormat("HH:mm:ss").parse(new SimpleDateFormat("HH:mm:ss").format(rs.getDate(61))));
                comercial.setRandom(rs.getInt(62));
                comercial.setQtdePlayer(rs.getInt(63));
                comercial.setQtde(rs.getInt(64));
                comercial.setDataVencimento(rs.getDate(65));
                comercial.setDependencia1(rs.getString(66));
                comercial.setDependencia2(rs.getString(67));
                comercial.setDependencia3(rs.getString(68));
                comercial.setFrameInicio(rs.getInt(69));
                comercial.setFrameFinal(rs.getInt(70));
                comercial.setMsg(null == rs.getString(71) ? "" : rs.getString(71));
                comercial.setSemSom(rs.getBoolean(72));
                comercial.setInterromperMusicaTocada(Boolean.TRUE);
                comercial.setTexto("");

                Integer codigoComercial = repository.save(comercial);
                repository.finalize();
                comercial.setId(codigoComercial);

                AudiostoreComercialShBean comercialShBean = null;
                String[] dias = new String[]{"Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"};
                String diasSemana = "";
                for (int i = 9; i < 57; i++) {
                    if (i % 2 == 1) {
                        String semana = (null == rs.getString(i)) ? "" : rs.getString(i);
                        diasSemana = semana;
                    } else {
                        if (!diasSemana.isEmpty()) {
                            for (int j = 1; j <= 7; j++) {
                                if (j <= diasSemana.length() && !String.valueOf(diasSemana.charAt(j - 1)).equals(" ") && !String.valueOf(diasSemana.charAt(j - 1)).isEmpty()) {
                                    comercialShBean = new AudiostoreComercialShBean();
                                    comercialShBean.setComercial(comercial);
                                    comercialShBean.setHorario(new SimpleDateFormat("HH:mm:ss").parse(rs.getString(i)));
                                    comercialShBean.setSemana(dias[j - 1]);
                                    if (String.valueOf(diasSemana.charAt(j - 1)).equalsIgnoreCase("n")) {
                                        comercialShBean.setInterromperMusicaTocada(true);
                                    } else {
                                        comercialShBean.setInterromperMusicaTocada(false);
                                    }
                                    listaComerciaisShBean.add(comercialShBean);
                                }
                            }
                        }
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ParseException(e.getMessage(), 1);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new InstantiationException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalAccessException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private void popularComercialSh() throws Exception{
        try {
            //        repository.setUsuario(sessionUsuario.getUsuarioBean());
            repository.setUsuario(new UsuarioBean(22));
            for (AudiostoreComercialShBean comercialSh : listaComerciaisShBean) {
                repository.save(comercialSh);               
            }
            repository.finalize();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }
}
