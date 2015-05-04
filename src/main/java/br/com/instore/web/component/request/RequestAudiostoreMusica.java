package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.MusicaGeralBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreMusicaDTO;
import br.com.instore.web.dto.AudiostoreMusicaJSON;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import br.com.caelum.vraptor.ioc.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;

@Component
@RequestScoped
public class RequestAudiostoreMusica implements java.io.Serializable {

    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;
    private HttpServletRequest httpServletRequest;

    public RequestAudiostoreMusica(SessionRepository repository, Result result, SessionUsuario sessionUsuario, HttpServletRequest httpServletRequest) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
        this.httpServletRequest = httpServletRequest;
    }

    public void cadastrar(String idmusicaGeral) {
        if (null != idmusicaGeral) {
            if ("#".equals(idmusicaGeral.trim())) {
            }

            if (!"#".equals(idmusicaGeral.trim())) {
                List<String> idStrList = Arrays.asList(idmusicaGeral.trim().split(","));
                List<Integer> idList = new ArrayList<Integer>();
                for (String id : idStrList) {
                    if (null != id && !id.trim().isEmpty()) {
                        idList.add(Integer.parseInt(id.trim()));
                    }
                }

                Integer idcliente = idList.get(idList.size() - 1);
                ClienteBean clienteBean = repository.find(ClienteBean.class, idcliente);
                result.include("categoriaList", categoriasByCliente2(idcliente));
                result.include("clienteBean", clienteBean);
                result.include("mgIdList", idList);
                List<MusicaGeralBean> lista = loadMusicaGeralList(idList);
                List<MusicaGeralItem> listaFinal = new ArrayList<MusicaGeralItem>();
                List<AudiostoreMusicaBean> audiostoreMusicaBeanList = repository.query(AudiostoreMusicaBean.class).eq("cliente.idcliente", idcliente).in("musicaGeral", idList.toArray(new Integer[idList.size()])).findAll();

                for (MusicaGeralBean bean : lista) {
                    MusicaGeralItem item = new MusicaGeralItem();
                    item.setExiste(false);
                    for (AudiostoreMusicaBean bean2 : audiostoreMusicaBeanList) {
                        if (bean2.getMusicaGeral().equals(bean.getId())) {
                            item.setExiste(true);
                            item.setMsgErro("A música já existe para o cliente selecionado.");
                            break;
                        }
                    }

                    String arq = "";
                    List<String> partss = Arrays.asList(bean.getArquivo().split("/"));
                    if (null != partss && !partss.isEmpty()) {
                        arq = partss.get(partss.size() - 1);
                    }

                    item.setter(bean.getId(),
                            bean.getCategoriaGeral(),
                            bean.getUsuario(),
                            bean.getGravadora(),
                            bean.getTitulo(),
                            bean.getInterprete(),
                            bean.getTipoInterprete(),
                            bean.getLetra(),
                            bean.getTempoTotal(),
                            bean.getAnoGravacao(),
                            bean.getAfinidade1(),
                            bean.getAfinidade2(),
                            bean.getAfinidade3(),
                            bean.getAfinidade4(),
                            arq);
                    listaFinal.add(item);
                }
                result.include("musicaGeralList", listaFinal);
            }
        }
    }

    public class MusicaGeralItem extends MusicaGeralBean {

        private Boolean existe;
        private String msgErro;

        public Boolean getExiste() {
            return existe;
        }

        public void setExiste(Boolean existe) {
            this.existe = existe;
        }

        public String getMsgErro() {
            return msgErro;
        }

        public void setMsgErro(String msgErro) {
            this.msgErro = msgErro;
        }

        public void setter(Object... values) {
            setId(Integer.parseInt(values[0].toString()));
            setCategoriaGeral(Integer.parseInt(values[1].toString()));
            setUsuario(Integer.parseInt(values[2].toString()));
            setGravadora(Integer.parseInt(values[3].toString()));
            setTitulo(values[4].toString());
            setInterprete(values[5].toString());
            setTipoInterprete(Short.parseShort(values[6].toString()));
            setLetra(values[7].toString());
            setTempoTotal(values[8].toString());
            setAnoGravacao(Integer.parseInt(values[9].toString()));
            setAfinidade1(values[10].toString());
            setAfinidade2(values[11].toString());
            setAfinidade3(values[12].toString());
            setAfinidade4(values[13].toString());
            setArquivo(values[14].toString());
        }
    }

    public List<MusicaGeralBean> loadMusicaGeralList(List<Integer> idList) {
        return repository.query(MusicaGeralBean.class).in("id", idList.toArray(new Integer[idList.size()])).findAll();
    }

    public long totalRegistros() {
        return repository.query(MusicaGeralBean.class).count();
    }

    public long totalRegistrosPorPagina() {
        return 100;
    }

    public long totalPaginas(long totalRegistros, long totalRegistrosPorPagina) {
        return (long) Math.ceil((double) totalRegistros / totalRegistrosPorPagina);
    }

    public AudiostoreMusicaJSON beanList(Boolean datajson, Boolean view, Integer page, Integer rows, Integer id, Integer idcliente, String arquivo, String nome, Integer codigo, String letra, String ultimaImportacao) {
        idcliente = sessionUsuario.getCliente().getIdcliente();
        System.out.println("FILTROS");
        System.out.println("================================================");
        System.out.println("DATAJSON: " + datajson);
        System.out.println("VIEW: " + view);
        System.out.println("PAGE: " + page);
        System.out.println("ROWS: " + rows);
        System.out.println("ID: " + id);
        System.out.println("IDCLIENTE: " + idcliente);
        System.out.println("NOME: " + nome);
        System.out.println("CODIGO: " + codigo);
        System.out.println("LETRA: " + letra);
        System.out.println("================================================");

        AudiostoreMusicaJSON json = new AudiostoreMusicaJSON();
        List<AudiostoreMusicaBean> lista = new ArrayList<AudiostoreMusicaBean>();

        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);
        Integer offset = (page - 1) * rows;

        Query q1 = repository.query(AudiostoreMusicaBean.class);
        Query q2 = repository.query(AudiostoreMusicaBean.class);

        String querySQL1 = "";
        String querySQL2 = "";
//        querySQL1 = " select \n"
//                + "     audiostore_musica.id as id_musica,\n"
//                + "	 '' as param\n"
//                + " from\n"
//                + "     audiostore_musica\n"
//                + " inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n"
//                + " inner join cliente on cliente.idcliente = audiostore_musica.cliente\n"
//                + " left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n"
//                + " left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n"
//                + " where audiostore_musica.id is not null";
//
//        querySQL2 = " select sum(__count) as count , '' as param from ( \n"
//                + " 	 select \n"
//                + "		 count(distinct audiostore_musica.id) as __count\n"
//                + "	 from\n"
//                + "		 audiostore_musica\n"
//                + "	 inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n"
//                + "	 inner join cliente on cliente.idcliente = audiostore_musica.cliente\n"
//                + "	 left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n"
//                + "	 left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n"
//                + "	 where audiostore_musica.id is not null [[MAIS_WHERE]]\n"
//                + "	 group by audiostore_musica.id\n"
//                + " ) as t";

        querySQL1 = " select \n"
                + "     audiostore_musica.id as id_musica,\n"
                + "	 '' as param\n"
                + " from\n"
                + "     audiostore_musica\n"
                + " [INNER_JOIN_MUSICA_GERAL]"
                + " [INNER_JOIN_CATEGORIA_MUSICA_GERAL]"
                + " [INNER_JOIN_CATEGORIA_GERAL]"
                + " where audiostore_musica.id is not null";

        querySQL2 = " select sum(__count) as count , '' as param from ( \n"
                + " 	 select \n"
                + "		 count(distinct audiostore_musica.id) as __count\n"
                + "	 from\n"
                + "		 audiostore_musica\n"
                + "	 where audiostore_musica.id is not null [[MAIS_WHERE]]\n"
                + "	 group by audiostore_musica.id\n"
                + " ) as t";

        if (null != idcliente && idcliente > 0) {
            querySQL1 += " and audiostore_musica.cliente = " + idcliente + " \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and audiostore_musica.cliente = " + idcliente + " [[MAIS_WHERE]] \n");
            json.setIdcliente(idcliente);
        }

        if (null != nome && !nome.trim().isEmpty()) {
            querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");

            querySQL1 += " and musica_geral.titulo like '%" + nome + "%' \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and musica_geral.titulo like '%" + nome + "%' [[MAIS_WHERE]] \n");
            json.setNome(nome);
        }

        if (null != codigo && codigo > 0) {
            querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");

            querySQL1 += " and (audiostore_musica.categoria1 = " + codigo + "";
            querySQL1 += " or audiostore_musica.categoria2 = " + codigo + ") \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and (audiostore_musica.categoria1 = " + codigo + " [[MAIS_WHERE]]");
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " or audiostore_musica.categoria2 = " + codigo + ") [[MAIS_WHERE]] \n");
            json.setCodigo(codigo);
        }

        if (null != letra && !letra.trim().isEmpty()) {
            querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");

            querySQL1 += " and musica_geral.letra like '%" + letra + "%' \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and musica_geral.letra like '%" + letra + "%' \"[[MAIS_WHERE]]\" \n");
            json.setLetra(letra);
        }

        if (null != ultimaImportacao && !ultimaImportacao.isEmpty()) {
            querySQL1 += " and audiostore_musica.ultima_importacao = '" + (ultimaImportacao.equals("true") ? "1" : "0") + "' \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and audiostore_musica.ultima_importacao = '" + (ultimaImportacao.equals("true") ? "1" : "0") + "'  [[MAIS_WHERE]]  \n");
            json.setUltimaImportacao(ultimaImportacao);
            json.setBool(ultimaImportacao);
        }

        if (null != arquivo && !arquivo.trim().isEmpty()) {
            querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");

            querySQL1 += " and musica_geral.arquivo like '%" + arquivo + "%' \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and musica_geral.arquivo like '%" + arquivo + "%' \"[[MAIS_WHERE]]\" \n");
            json.setLetra(letra);
        }

        querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", "");
        querySQL1 += " group by audiostore_musica.id \n limit " + offset + "," + rows;

        querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "");
        querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "");
        querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "");
        querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "");
        querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "");
        querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "");

        final List<BigDecimal> countBD = new ArrayList<BigDecimal>();
        final List<Integer> idList = new ArrayList<Integer>();

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("SQLDUMP");
        System.out.println(querySQL1);

        System.out.println("");
        System.out.println("");
        System.out.println("");

        System.out.println("SQLDUMP");
        System.out.println(querySQL2);

        System.out.println("");
        System.out.println("");
        System.out.println("");

        repository.query(querySQL1).executeSQL(new Each() {
            public Integer id_musica;
            public String param;

            @Override
            public void each() {
                idList.add(id_musica);
            }
        });

        repository.query(querySQL2).executeSQL(new Each() {
            public BigDecimal count;
            public String param;

            @Override
            public void each() {
                countBD.add(count);
            }
        });

        Integer iniValue = 0;
        if (null != countBD && !countBD.isEmpty()) {
            if (null != countBD.get(0)) {
                iniValue = countBD.get(0).intValue();
            }
        }

        json.setCount(iniValue);
        int size = iniValue / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        json.setPage(page);
        json.setSize(size);

        if (null != idList && !idList.isEmpty()) {
            lista = repository.query(AudiostoreMusicaBean.class).in("id", idList.toArray(new Integer[idList.size()])).findAll();
        }

        List<AudiostoreMusicaDTO> rowsList = new ArrayList<AudiostoreMusicaDTO>();

        for (AudiostoreMusicaBean bean : lista) {
            if (null != bean) {
                MusicaGeralBean mgb = repository.find(MusicaGeralBean.class, bean.getMusicaGeral());
                AudiostoreMusicaDTO dto = new AudiostoreMusicaDTO();

                dto.setId(bean.getId().toString());
                dto.setNome(mgb.getTitulo());
                dto.setIdcliente(bean.getCliente().getIdcliente());
                dto.setNomeCliente(bean.getCliente().getNome());
                dto.setCategoria1(null != bean.getCategoria1() ? bean.getCategoria1().getCategoria() : "");
                dto.setCategoria2(null != bean.getCategoria2() ? bean.getCategoria2().getCategoria() : "");
                dto.setCategoria3(null != bean.getCategoria3() ? bean.getCategoria3().getCategoria() : "");
                dto.setUltimaImportacao(bean.getUltimaImportacao() ? "sim" : "nao");
                String arq = "";
                List<String> partss = Arrays.asList(mgb.getArquivo().split("/"));
                if (null != partss && !partss.isEmpty()) {
                    arq = partss.get(partss.size() - 1);
                }
                dto.setArquivo(arq);

                rowsList.add(dto);
            }
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
        return json;
    }

    public AudiostoreMusicaJSON beanList2(Boolean datajson, Boolean view, Integer page, Integer rows, Integer id, Integer idcliente, String arquivo, String nome, Integer codigo, String letra) {
        System.out.println("FILTROS");
        System.out.println("================================================");
        System.out.println("DATAJSON: " + datajson);
        System.out.println("VIEW: " + view);
        System.out.println("PAGE: " + page);
        System.out.println("ROWS: " + rows);
        System.out.println("ID: " + id);
        System.out.println("IDCLIENTE: " + idcliente);
        System.out.println("NOME: " + nome);
        System.out.println("CODIGO: " + codigo);
        System.out.println("LETRA: " + letra);
        System.out.println("================================================");

        AudiostoreMusicaJSON json = new AudiostoreMusicaJSON();
        List<AudiostoreMusicaBean> lista = new ArrayList<AudiostoreMusicaBean>();

        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);
        Integer offset = (page - 1) * rows;

        Query q1 = repository.query(AudiostoreMusicaBean.class);
        Query q2 = repository.query(AudiostoreMusicaBean.class);

        String querySQL1 = "";
        String querySQL2 = "";
//        querySQL1 = " select \n"
//                + "     audiostore_musica.id as id_musica,\n"
//                + "	 '' as param\n"
//                + " from\n"
//                + "     audiostore_musica\n"
//                + " inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n"
//                + " inner join cliente on cliente.idcliente = audiostore_musica.cliente\n"
//                + " left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n"
//                + " left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n"
//                + " where audiostore_musica.id is not null";
//
//        querySQL2 = " select sum(__count) as count , '' as param from ( \n"
//                + " 	 select \n"
//                + "		 count(distinct audiostore_musica.id) as __count\n"
//                + "	 from\n"
//                + "		 audiostore_musica\n"
//                + "	 inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n"
//                + "	 inner join cliente on cliente.idcliente = audiostore_musica.cliente\n"
//                + "	 left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n"
//                + "	 left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n"
//                + "	 where audiostore_musica.id is not null [[MAIS_WHERE]]\n"
//                + "	 group by audiostore_musica.id\n"
//                + " ) as t";

        querySQL1 = " select \n"
                + "     audiostore_musica.id as id_musica,\n"
                + "	 '' as param\n"
                + " from\n"
                + " [INNER_JOIN_MUSICA_GERAL]"
                + " [INNER_JOIN_CATEGORIA_MUSICA_GERAL]"
                + " [INNER_JOIN_CATEGORIA_GERAL]"
                + "     audiostore_musica\n"
                + " where audiostore_musica.id is not null";

        querySQL2 = " select sum(__count) as count , '' as param from ( \n"
                + " 	 select \n"
                + "		 count(distinct audiostore_musica.id) as __count\n"
                + "	 from\n"
                + "		 audiostore_musica\n"
                + "	 where audiostore_musica.id is not null [[MAIS_WHERE]]\n"
                + "	 group by audiostore_musica.id\n"
                + " ) as t";

        if (null != idcliente && idcliente > 0) {
            querySQL1 += " and audiostore_musica.cliente = " + idcliente + " \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and audiostore_musica.cliente = " + idcliente + " [[MAIS_WHERE]] \n");
            json.setIdcliente(idcliente);
        }

        if (null != nome && !nome.trim().isEmpty()) {
            querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");

            querySQL1 += " and musica_geral.titulo like '%" + nome + "%' \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and musica_geral.titulo like '%" + nome + "%' [[MAIS_WHERE]] \n");
            json.setNome(nome);
        }

        if (null != codigo && codigo > 0) {
            querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");

            querySQL1 += " and (audiostore_musica.categoria1 = " + codigo + "";
            querySQL1 += " or audiostore_musica.categoria2 = " + codigo + ") \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and (audiostore_musica.categoria1 = " + codigo + " [[MAIS_WHERE]]");
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " or audiostore_musica.categoria2 = " + codigo + ") [[MAIS_WHERE]] \n");
            json.setCodigo(codigo);
        }

        if (null != letra && !letra.trim().isEmpty()) {
            querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");

            querySQL1 += " and musica_geral.letra like '%" + letra + "%' \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and musica_geral.letra like '%" + letra + "%' \"[[MAIS_WHERE]]\" \n");
            json.setLetra(letra);
        }

        if (null != arquivo && !arquivo.trim().isEmpty()) {
            querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "inner join musica_geral on musica_geral.id = audiostore_musica.musica_geral\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "left join categoria_musica_geral on categoria_musica_geral.musica = musica_geral.id\n");
            querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "left join categoria_geral on categoria_geral.id = categoria_musica_geral.categoria\n");

            querySQL1 += " and musica_geral.arquivo like '%" + arquivo + "%' \n";
            querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", " and musica_geral.arquivo like '%" + arquivo + "%' \"[[MAIS_WHERE]]\" \n");
            json.setLetra(letra);
        }

        querySQL2 = querySQL2.replace("[[MAIS_WHERE]]", "");
        querySQL1 += " group by audiostore_musica.id \n limit " + offset + "," + rows;

        querySQL1 = querySQL1.replace("[INNER_JOIN_MUSICA_GERAL]", "");
        querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "");
        querySQL1 = querySQL1.replace("[INNER_JOIN_CATEGORIA_GERAL]", "");
        querySQL2 = querySQL2.replace("[INNER_JOIN_MUSICA_GERAL]", "");
        querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_MUSICA_GERAL]", "");
        querySQL2 = querySQL2.replace("[INNER_JOIN_CATEGORIA_GERAL]", "");

        final List<BigDecimal> countBD = new ArrayList<BigDecimal>();
        final List<Integer> idList = new ArrayList<Integer>();

        repository.query(querySQL1).executeSQL(new Each() {
            public Integer id_musica;
            public String param;

            @Override
            public void each() {
                idList.add(id_musica);
            }
        });

        repository.query(querySQL2).executeSQL(new Each() {
            public BigDecimal count;
            public String param;

            @Override
            public void each() {
                countBD.add(count);
            }
        });

        Integer iniValue = 0;
        if (null != countBD && !countBD.isEmpty()) {
            if (null != countBD.get(0)) {
                iniValue = countBD.get(0).intValue();
            }
        }

        json.setCount(iniValue);
        int size = iniValue / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        json.setPage(page);
        json.setSize(size);

        if (null != idList && !idList.isEmpty()) {
            lista = repository.query(AudiostoreMusicaBean.class).in("id", idList.toArray(new Integer[idList.size()])).findAll();
        }

        List<AudiostoreMusicaDTO> rowsList = new ArrayList<AudiostoreMusicaDTO>();

        for (AudiostoreMusicaBean bean : lista) {
            if (null != bean) {
                MusicaGeralBean mgb = repository.find(MusicaGeralBean.class, bean.getMusicaGeral());
                AudiostoreMusicaDTO dto = new AudiostoreMusicaDTO();

                dto.setId(bean.getId().toString());
                dto.setNome(mgb.getTitulo());
                dto.setIdcliente(bean.getCliente().getIdcliente());
                dto.setNomeCliente(bean.getCliente().getNome());
                dto.setCategoria1(null != bean.getCategoria1() ? bean.getCategoria1().getCategoria() : "");
                dto.setCategoria2(null != bean.getCategoria2() ? bean.getCategoria2().getCategoria() : "");
                dto.setCategoria3(null != bean.getCategoria3() ? bean.getCategoria3().getCategoria() : "");
                String arq = "";
                List<String> partss = Arrays.asList(mgb.getArquivo().split("/"));
                if (null != partss && !partss.isEmpty()) {
                    arq = partss.get(partss.size() - 1);
                }
                dto.setArquivo(arq);

                rowsList.add(dto);
            }
        }
        json.setRows(rowsList);
        return json;
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        return clienteBeanList;
    }

    public MusicaGeralBean musicaGeralBean(Integer id) {
        MusicaGeralBean b = repository.find(MusicaGeralBean.class, id);
        return b;
    }

    public void categoriasByCliente(Integer id) {
        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", id).eq("tipo", new Short("1")).findAll();
        result.use(Results.json()).withoutRoot().from(audiostoreCategoriaBeanList).recursive().serialize();
    }

    public List<AudiostoreCategoriaBean> categoriasByCliente2(Integer id) {
        return repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", id).eq("tipo", new Short("1")).findAll();
    }

    public List<AudiostoreCategoriaBean> categorias() {
        return repository.query(AudiostoreCategoriaBean.class).eq("tipo", new Short("1")).findAll();
    }

    public List<AudiostoreCategoriaBean> categoriasByCliente5(Integer idcliente) {
        return repository.query(AudiostoreCategoriaBean.class).eq("tipo", new Short("1")).eq("cliente.idcliente", idcliente).findAll();
    }

    public List<AudiostoreGravadoraBean> gravadoraBeanList() {
        List<AudiostoreGravadoraBean> audiostoreCategoriaBeanList = repository.query(AudiostoreGravadoraBean.class).findAll();
        return audiostoreCategoriaBeanList;
    }

    public AudiostoreMusicaBean bean(Integer id) {
        return repository.find(AudiostoreMusicaBean.class, id);
    }

    public AudiostoreMusicaDTO bean2(Integer id) {
        AudiostoreMusicaBean bean = repository.find(AudiostoreMusicaBean.class, id);
        AudiostoreMusicaDTO dto = new AudiostoreMusicaDTO();

        if (null != bean) {
            MusicaGeralBean mgb = repository.find(MusicaGeralBean.class, bean.getMusicaGeral());
            dto.setId(bean.getId().toString());
            dto.setNome(mgb.getTitulo());
            dto.setIdcliente(bean.getCliente().getIdcliente());
            dto.setNomeCliente(bean.getCliente().getNome());
            dto.setCategoria1(null != bean.getCategoria1() ? bean.getCategoria1().getCategoria() : "");
            dto.setCategoria2(null != bean.getCategoria2() ? bean.getCategoria2().getCategoria() : "");
            dto.setCategoria3(null != bean.getCategoria3() ? bean.getCategoria3().getCategoria() : "");
            dto.setLetra(mgb.getLetra());
        }

        return dto;
    }

    public MusicaGeralBean mgBean(Integer id) {
        return repository.find(MusicaGeralBean.class, id);
    }

    public void salvar(AudiostoreMusicaBean[] beans, Boolean updateAll) {
        repository.setUsuario(sessionUsuario.getUsuarioBean());
        try {
            int ixy = 0;
            for (AudiostoreMusicaBean bean : beans) {
                if (null != bean) {
                    if (ixy == 0 && updateAll) {
                        repository.query("update audiostore_musica set ultima_importacao = 0 where id >= 1 and cliente = " + bean.getCliente().getIdcliente() + " and ultima_importacao = 1;").executeSQLCommand();
                    }
                    ixy++;
                    // verifica se ja existe
                    if (null == bean.getId() || bean.getId() <= 0) {
                        if (repository.query(AudiostoreMusicaBean.class).eq("cliente.idcliente", bean.getCliente().getIdcliente()).eq("musicaGeral", bean.getMusicaGeral()).count() > 0) {
                            continue;
                        }
                    }

                    if (null == bean) {
                        continue;
                    }

                    if (null == bean.getDiasExecucao1()) {
                        bean.setDiasExecucao1(1);
                    }

                    if (null == bean.getDiasExecucao2()) {
                        bean.setDiasExecucao2(1);
                    }

                    if (null == bean.getCrossover()) {
                        bean.setCrossover(Boolean.FALSE);
                        bean.setDataVencimentoCrossover(new Date());
                    }

                    if (null == bean.getQtde()) {
                        bean.setQtde(999);
                    }

                    if (null == bean.getQtdePlayer()) {
                        bean.setQtdePlayer(999);
                    }

                    if (null == bean.getUltimaExecucao()) {
                        bean.setUltimaExecucao(new Date());
                    }

                    if (null == bean.getUltimaExecucaoData()) {
                        bean.setUltimaExecucaoData(new Date());
                    }

                    if (null == bean.getDataVencimento()) {
                        bean.setDataVencimento(new SimpleDateFormat("yyyy-MM-dd").parse("2050-12-31"));
                    }

                    if (null == bean.getData()) {
                        bean.setData(new Date());
                    }

                    if (bean.getCrossover()) {
                        if (null == bean.getDataVencimentoCrossover()) {
                            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Se estiver usando crossover, é obrigatório informar a data de vencimento!")).recursive().serialize();
                            return;
                        }
                    } else {
                        bean.setDataVencimentoCrossover(new Date());
                    }

                    if (null == bean.getCliente() || null == bean.getCliente().getIdcliente() || bean.getCliente().getIdcliente() <= 0) {
                        result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Selecione um cliente!")).recursive().serialize();
                        return;
                    } else {
                        if ((null == bean.getCategoria1() || null == bean.getCategoria1().getCodigo() || bean.getCategoria1().getCodigo() <= 0)) {
                            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Catégoria primária é obrigatório!")).recursive().serialize();
                            return;
                        }
                    }

                    bean.setFrameInicio(0);
                    bean.setFrameFinal(0);

                    if (null == bean.getMsg()) {
                        bean.setMsg("");
                    }

                    if (bean.getMsg().length() > 40) {
                        bean.setMsg(bean.getMsg().substring(0, 40));
                    }

                    if (null == bean.getSemSom()) {
                        bean.setSemSom(Boolean.FALSE);
                    }

                    bean.setUltimaExecucaoData(bean.getUltimaExecucao());
                    bean.setQtde(bean.getQtdePlayer());
                    bean.setSuperCrossover(Boolean.FALSE);
                    bean.setRandom(new Random().nextInt());

                    // SCRIP SQL
                    repository.setUsuario(sessionUsuario.getUsuarioBean());
                    String sql = "";

                    if (null != bean.getId() && bean.getId() > 0) {
                        sql = "DELETE FROM audiostore_musica WHERE id = " + bean.getId();
                        repository.query(sql).executeSQLCommand2();
                    }

                    sql = "";
                    sql = "INSERT INTO audiostore_musica VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    sql = sql.replaceFirst("\\?", "null"); // id
                    sql = sql.replaceFirst("\\?", bean.getMusicaGeral().toString()); // musica_geral
                    sql = sql.replaceFirst("\\?", bean.getCategoria1().getCodigo().toString()); // categoria1
                    sql = sql.replaceFirst("\\?", (null != bean.getCategoria2() && null != bean.getCategoria2().getCodigo() && bean.getCategoria2().getCodigo() > 0 ? bean.getCategoria2().getCodigo().toString() : "null")); // categoria2
                    sql = sql.replaceFirst("\\?", (null != bean.getCategoria3() && null != bean.getCategoria3().getCodigo() && bean.getCategoria3().getCodigo() > 0 ? bean.getCategoria3().getCodigo().toString() : "null")); // categoria3
                    sql = sql.replaceFirst("\\?", (bean.getCut() ? "1" : "0")); // cut
                    sql = sql.replaceFirst("\\?", (bean.getCrossover() ? "1" : "0")); // crossover
                    sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(bean.getDataVencimentoCrossover()) + "'"); // data_vencimento_crossover
                    sql = sql.replaceFirst("\\?", bean.getDiasExecucao1().toString()); // dias_execucao1
                    sql = sql.replaceFirst("\\?", bean.getDiasExecucao2().toString()); // dias_execucao2
                    sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(bean.getData()) + "'"); // data
                    sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd HH:m:ss").format(bean.getUltimaExecucao()) + "'"); // ultima_execucao
                    sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(bean.getUltimaExecucaoData()) + "'"); // ultima_execucao
                    sql = sql.replaceFirst("\\?", bean.getRandom().toString()); // random
                    sql = sql.replaceFirst("\\?", bean.getQtdePlayer().toString()); // qtde_player
                    sql = sql.replaceFirst("\\?", bean.getQtde().toString()); // qtde
                    sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(bean.getDataVencimento()) + "'"); // data_vencimento
                    sql = sql.replaceFirst("\\?", "0"); // frame_inicio
                    sql = sql.replaceFirst("\\?", "0"); // frame_final
                    sql = sql.replaceFirst("\\?", "'" + bean.getMsg() + "'"); // msg
                    sql = sql.replaceFirst("\\?", bean.getSemSom() ? "1" : "0"); // sem_som
                    sql = sql.replaceFirst("\\?", bean.getSuperCrossover() ? "1" : "0"); // super_crossover
                    sql = sql.replaceFirst("\\?", bean.getCliente().getIdcliente().toString()); // cliente
                    sql = sql.replaceFirst("\\?", bean.getUltimaImportacao() ? "1" : "0"); // cliente

                    System.out.println("SCRIPT");
                    System.out.println(sql);
                    repository.query(sql).executeSQLCommand2();
                }
            }

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }

    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            repository.query("DELETE FROM audiostore_musica WHERE id = " + id).executeSQLCommand2();
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Música removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a música!")).recursive().serialize();
        }
    }

    public InputStreamDownload download(Integer id) {
        InputStreamDownload inputStreamDownload = null;
        try {
            AudiostoreMusicaBean bean = bean(id);
            if (bean != null) {
//                String arquivo = bean.getArquivo();
//                if (arquivo.length() < 30) {
//                    arquivo = StringUtils.leftPad(arquivo, 30, " ");
//                } else {
//                    if (arquivo.length() > 30) {
//                        arquivo = arquivo.substring(0, 30);
//                    }
//                }
//
//                String interprete = bean.getInterprete();
//                if (interprete.length() < 30) {
//                    interprete = StringUtils.leftPad(interprete, 30, " ");
//                } else {
//                    if (interprete.length() > 30) {
//                        interprete = interprete.substring(0, 30);
//                    }
//                }
//
//                String titulo = bean.getTitulo();
//                if (titulo.length() < 30) {
//                    titulo = StringUtils.leftPad(titulo, 30, " ");
//                } else {
//                    if (titulo.length() > 30) {
//                        titulo = titulo.substring(0, 30);
//                    }
//                }
//
//                String afinidade1 = bean.getAfinidade1();
//                if (afinidade1.length() < 30) {
//                    afinidade1 = StringUtils.leftPad(afinidade1, 30, " ");
//                } else {
//                    if (afinidade1.length() > 30) {
//                        afinidade1 = afinidade1.substring(0, 30);
//                    }
//                }
//
//                String afinidade2 = bean.getAfinidade2();
//                if (afinidade2.length() < 30) {
//                    afinidade2 = StringUtils.leftPad(afinidade2, 30, " ");
//                } else {
//                    if (afinidade2.length() > 30) {
//                        afinidade2 = afinidade2.substring(0, 30);
//                    }
//                }
//
//                String afinidade3 = bean.getAfinidade3();
//                if (afinidade3.length() < 30) {
//                    afinidade3 = StringUtils.leftPad(afinidade3, 30, " ");
//                } else {
//                    if (afinidade3.length() > 30) {
//                        afinidade3 = afinidade3.substring(0, 30);
//                    }
//                }
//
//                String afinidade4 = bean.getAfinidade4();
//                if (afinidade4.length() < 30) {
//                    afinidade4 = StringUtils.leftPad(afinidade4, 30, " ");
//                } else {
//                    if (afinidade4.length() > 30) {
//                        afinidade4 = afinidade4.substring(0, 30);
//                    }
//                }
//
//                String msg = bean.getMsg();
//                if (msg.length() < 40) {
//                    msg = StringUtils.leftPad(msg, 40, " ");
//                } else {
//                    if (msg.length() > 40) {
//                        msg = msg.substring(0, 40);
//                    }
//                }
//
//                String conteudo = "";
//                conteudo += "";
//                conteudo += arquivo;
//                conteudo += interprete;
//                conteudo += bean.getTipoInterprete();
//                conteudo += titulo;
//                conteudo += bean.getCut() ? "nao" : "sim";
//                conteudo += (null != bean.getCategoria1() ? StringUtils.leftPad(bean.getCategoria1().getCodigo().toString(), 3, " ") : "   ");
//                conteudo += (null != bean.getCategoria2() ? StringUtils.leftPad(bean.getCategoria2().getCodigo().toString(), 3, " ") : "   ");
//                conteudo += (null != bean.getCategoria3() ? StringUtils.leftPad(bean.getCategoria3().getCodigo().toString(), 3, " ") : "   ");
//                conteudo += bean.getCrossover() ? "nao" : "sim";
//                conteudo += StringUtils.leftPad(bean.getDiasExecucao1().toString(), 4, " ");
//                conteudo += StringUtils.leftPad(bean.getDiasExecucao2().toString(), 4, " ");
//                conteudo += afinidade1;
//                conteudo += afinidade2;
//                conteudo += afinidade3;
//                conteudo += afinidade4;
//                conteudo += StringUtils.leftPad(bean.getGravadora().getId().toString(), 3, " ");
//                conteudo += bean.getAnoGravacao();
//                conteudo += bean.getVelocidade();
//                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getData());
//                conteudo += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucao());
//                conteudo += new SimpleDateFormat("HH:mm:ss").format(bean.getTempoTotal());
//                conteudo += StringUtils.leftPad(bean.getQtdePlayer().toString(), 3, " ");
//                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimento());
//                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimentoCrossover());
//                conteudo += StringUtils.leftPad(bean.getFrameInicio().toString(), 8, " ");
//                conteudo += StringUtils.leftPad(bean.getFrameFinal().toString(), 8, " ");
//                conteudo += msg;
//                conteudo += bean.getSemSom() ? 1 : 0;
//
//                inputStreamDownload = new InputStreamDownload(new ByteArrayInputStream(conteudo.getBytes()), "application/exp", bean.getTitulo() + "+.exp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStreamDownload;
    }

    public void upload(Integer id) {
        try {
//            ConfigAppBean config = repository.find(ConfigAppBean.class, 1);
//            AudiostoreMusicaBean bean = bean(id);
//            if (bean != null) {
//
//                String arquivo = bean.getArquivo();
//                if (arquivo.length() < 30) {
//                    arquivo = StringUtils.leftPad(arquivo, 30, " ");
//                } else {
//                    if (arquivo.length() > 30) {
//                        arquivo = arquivo.substring(0, 30);
//                    }
//                }
//
//                String interprete = bean.getInterprete();
//                if (interprete.length() < 30) {
//                    interprete = StringUtils.leftPad(interprete, 30, " ");
//                } else {
//                    if (interprete.length() > 30) {
//                        interprete = interprete.substring(0, 30);
//                    }
//                }
//
//                String titulo = bean.getTitulo();
//                if (titulo.length() < 30) {
//                    titulo = StringUtils.leftPad(titulo, 30, " ");
//                } else {
//                    if (titulo.length() > 30) {
//                        titulo = titulo.substring(0, 30);
//                    }
//                }
//
//                String afinidade1 = bean.getAfinidade1();
//                if (afinidade1.length() < 30) {
//                    afinidade1 = StringUtils.leftPad(afinidade1, 30, " ");
//                } else {
//                    if (afinidade1.length() > 30) {
//                        afinidade1 = afinidade1.substring(0, 30);
//                    }
//                }
//
//                String afinidade2 = bean.getAfinidade2();
//                if (afinidade2.length() < 30) {
//                    afinidade2 = StringUtils.leftPad(afinidade2, 30, " ");
//                } else {
//                    if (afinidade2.length() > 30) {
//                        afinidade2 = afinidade2.substring(0, 30);
//                    }
//                }
//
//                String afinidade3 = bean.getAfinidade3();
//                if (afinidade3.length() < 30) {
//                    afinidade3 = StringUtils.leftPad(afinidade3, 30, " ");
//                } else {
//                    if (afinidade3.length() > 30) {
//                        afinidade3 = afinidade3.substring(0, 30);
//                    }
//                }
//
//                String afinidade4 = bean.getAfinidade4();
//                if (afinidade4.length() < 30) {
//                    afinidade4 = StringUtils.leftPad(afinidade4, 30, " ");
//                } else {
//                    if (afinidade4.length() > 30) {
//                        afinidade4 = afinidade4.substring(0, 30);
//                    }
//                }
//
//                String msg = bean.getMsg();
//                if (msg.length() < 40) {
//                    msg = StringUtils.leftPad(msg, 40, " ");
//                } else {
//                    if (msg.length() > 40) {
//                        msg = msg.substring(0, 40);
//                    }
//                }
//
//                String conteudo = "";
//                conteudo += "";
//                conteudo += arquivo;
//                conteudo += interprete;
//                conteudo += bean.getTipoInterprete();
//                conteudo += titulo;
//                conteudo += bean.getCut() ? "nao" : "sim";
//                conteudo += (null != bean.getCategoria1() ? StringUtils.leftPad(bean.getCategoria1().getCodigo().toString(), 3, " ") : "   ");
//                conteudo += (null != bean.getCategoria2() ? StringUtils.leftPad(bean.getCategoria2().getCodigo().toString(), 3, " ") : "   ");
//                conteudo += (null != bean.getCategoria3() ? StringUtils.leftPad(bean.getCategoria3().getCodigo().toString(), 3, " ") : "   ");
//                conteudo += bean.getCrossover() ? "nao" : "sim";
//                conteudo += StringUtils.leftPad(bean.getDiasExecucao1().toString(), 4, " ");
//                conteudo += StringUtils.leftPad(bean.getDiasExecucao2().toString(), 4, " ");
//                conteudo += afinidade1;
//                conteudo += afinidade2;
//                conteudo += afinidade3;
//                conteudo += afinidade4;
//                conteudo += StringUtils.leftPad(bean.getGravadora().getId().toString(), 3, " ");
//                conteudo += bean.getAnoGravacao();
//                conteudo += bean.getVelocidade();
//                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getData());
//                conteudo += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucao());
//                conteudo += new SimpleDateFormat("HH:mm:ss").format(bean.getTempoTotal());
//                conteudo += StringUtils.leftPad(bean.getQtdePlayer().toString(), 3, " ");
//                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimento());
//                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimentoCrossover());
//                conteudo += StringUtils.leftPad(bean.getFrameInicio().toString(), 8, " ");
//                conteudo += StringUtils.leftPad(bean.getFrameFinal().toString(), 8, " ");
//                conteudo += msg;
//                conteudo += bean.getSemSom() ? 1 : 0;
//
//                File dir = new File(config.getDataPath() + "\\musica-exp\\");
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//
//                InputStream is = new ByteArrayInputStream(conteudo.getBytes());
//                FileOutputStream fos = new FileOutputStream(new File(config.getDataPath() + "\\musica-exp\\" + StringUtils.leftPad(bean.getId().toString(), 11, "0") + ".exp"));
//
//                org.apache.commons.io.IOUtils.copy(is, fos);
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "")).recursive().serialize();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void carregarInforWizard(Integer id) {
//        List<AudiostoreCategoriaBean> categoriaLista = repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", id).findAll();
//
//        if (null == categoriaLista || categoriaLista.isEmpty()) {
//            CadastroMusicaDTO dto = new CadastroMusicaDTO();
//            dto.setPossuiCategoria(false);
//
//            result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
//        } else {
//            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", id).findOne();
//
//            if (null == dados) {
//                CadastroMusicaDTO dto = new CadastroMusicaDTO();
//                dto.setPossuiCategoria(true);
//                dto.setExisteDiretorio(false);
//                result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
//            } else {
//                if (null == dados.getLocalOrigemMusica() || dados.getLocalOrigemMusica().isEmpty()) {
//                    CadastroMusicaDTO dto = new CadastroMusicaDTO();
//                    dto.setPossuiCategoria(true);
//                    dto.setExisteDiretorio(false);
//                    result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
//                } else {
//                    File dir = new File(dados.getLocalOrigemMusica());
//                    if (!dir.exists()) {
//                        CadastroMusicaDTO dto = new CadastroMusicaDTO();
//                        dto.setPossuiCategoria(true);
//                        dto.setExisteDiretorio(false);
//                        result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
//                    } else {
//
//                        if (null == dir.listFiles() || dir.listFiles().length == 0) {
//                            CadastroMusicaDTO dto = new CadastroMusicaDTO();
//                            dto.setPossuiCategoria(true);
//                            dto.setExisteDiretorio(true);
//                            dto.setExisteArquivo(false);
//                            result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
//                        } else {
//                            CadastroMusicaDTO dto = new CadastroMusicaDTO();
//                            dto.setPossuiCategoria(true);
//                            dto.setExisteDiretorio(true);
//                            dto.setExisteArquivo(true);
//
//                            for (AudiostoreCategoriaBean item : categoriaLista) {
//                                AudiostoreCategoriaDTO dtoItem = new AudiostoreCategoriaDTO();
//                                dtoItem.setCategoria(item.getCategoria());
//                                dtoItem.setCodigo(item.getCodigo());
//                                dto.getCategoriaList().add(dtoItem);
//                            }
//                            dto.setMusicaList(Utilities.getMetadata(dados.getLocalOrigemMusica()));
//                            result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
//                        }
//                    }
//                }
//            }
//        }
//    }
    public void validarMsc(Integer[] id_list, Boolean expArquivoAudio, Integer idcliente, String arquivo, String nome, Integer codigo) {
        List<AudiostoreMusicaBean> list = null;

        if (null != id_list && id_list.length > 0) {
            list = repository.query(AudiostoreMusicaBean.class).in("id", id_list).findAll();
        } else {
            AudiostoreMusicaJSON json = beanList2(true, false, 1, 999999999, null, idcliente, arquivo, nome, codigo, null);
            if (null != json.getRows() && !json.getRows().isEmpty()) {
                id_list = new Integer[json.getRows().size()];
                int i = 0;
                for (AudiostoreMusicaDTO dto : json.getRows()) {
                    id_list[i] = Integer.parseInt(dto.getId().trim());
                    i++;
                }
            }
            list = repository.query(AudiostoreMusicaBean.class).in("id", id_list).findAll();
        }

        boolean ajaxResultBool = true;
        String ajaxResultStr = "";
        Object ajaxResultObject = null;

        // verifica se todos são do mesmo cliente
        List<AudiostoreMusicaBean> beanList = repository.query(AudiostoreMusicaBean.class).in("id", id_list).findAll();
        Integer idclienteAux = 0;
        if (ajaxResultBool) {

            System.out.println("IDENT CLIENTE::" + idcliente);
            if (null != beanList && !beanList.isEmpty()) {
                for (AudiostoreMusicaBean bean : beanList) {
                    if (null != idcliente && idcliente > 0) {
                        if (!idcliente.equals(bean.getCliente().getIdcliente())) {
                            ajaxResultBool = false;
                            ajaxResultStr = "Você selecionou músicas de clientes diferentes!";
                        }
                    } else {
                        ajaxResultBool = false;
                        ajaxResultStr = "Selecione um cliente!";
                        break;
                    }
                }
            } else {
                ajaxResultBool = false;
                ajaxResultStr = "Nenhuma música foi selecionada!";
            }
        }

        DadosClienteBean dados = null;
        if (ajaxResultBool) {
            if (null == idcliente || 0 == idcliente) {
                idclienteAux = idcliente;
            }

            dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", idcliente).findOne();
            String destino = dados.getLocalDestinoExp();
            if (!Utilities.verifyTaskLock(dados.getLocalDestinoExp(), Utilities.TaskLock.MUSICA)) {
                ajaxResultBool = false;
                ajaxResultStr = Utilities.verifyInfoMessageTaskLock(destino, Utilities.TaskLock.MUSICA);
            }
        }

        if (ajaxResultBool) {
            try {
                Utilities.createTaskLock(dados.getLocalDestinoExp(), sessionUsuario.getUsuarioBean().getNome(), httpServletRequest, Utilities.TaskLock.MUSICA);

                Utilities.removerLogMusica(dados.getLocalDestinoExp());

                upload(beanList, expArquivoAudio, idcliente, dados);
                ajaxResultBool = true;
                ajaxResultStr = "Arquivo exportando com sucesso!";
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResultBool = false;
                ajaxResultStr = e.getMessage();
            } finally {
                Utilities.deleteTaskLock(dados.getLocalDestinoExp(), Utilities.TaskLock.MUSICA);
            }
        }

        result.use(Results.json()).withoutRoot().from(new AjaxResult(ajaxResultBool, ajaxResultStr, ajaxResultObject)).recursive().serialize();
    }

    public void upload(List<AudiostoreMusicaBean> list, Boolean expArquivoAudio, Integer idcliente, DadosClienteBean dados) throws SmbException, MalformedURLException, UnknownHostException, IOException, Exception {
        try {

            StringBuffer conteudo = new StringBuffer();
            String quebraLinha = "";

            for (AudiostoreMusicaBean bean : list) {
                if (bean != null) {
                    MusicaGeralBean mgb = repository.query(MusicaGeralBean.class).in("id", bean.getMusicaGeral()).findOne();
                    if (mgb != null) {

                        conteudo.append(quebraLinha);

                        // arquivo
                        if (Arrays.asList(mgb.getArquivo().split("/")).size() > 0) {
                            List<String> lista = Arrays.asList(mgb.getArquivo().split("/"));
                            String arq = lista.get(lista.size() - 1);

                            if (arq.length() > 30) {
                                arq = arq.substring(0, 30);
                            }

                            if (arq.length() < 30) {
                                arq = StringUtils.leftPad(arq, 30, " ");
                            }
                            conteudo.append(Utilities.formatarHexExp(arq));
                        }

                        // interprete
                        String interprete = mgb.getInterprete();
                        if (interprete.length() > 30) {
                            interprete = interprete.substring(0, 30);
                        }

                        if (interprete.length() < 30) {
                            interprete = StringUtils.leftPad(interprete, 30, " ");
                        }
                        interprete = Utilities.formatarHexExp(interprete);
                        conteudo.append(interprete);

                        // tipo interprete
                        conteudo.append(mgb.getTipoInterprete());

                        // titulo
                        String titulo = mgb.getTitulo();
                        if (titulo.length() > 30) {
                            titulo = titulo.substring(0, 30);
                        }

                        if (titulo.length() < 30) {
                            titulo = StringUtils.leftPad(titulo, 30, " ");
                        }
                        titulo = Utilities.formatarHexExp(titulo);
                        conteudo.append(titulo);

                        // cut
                        conteudo.append(bean.getCut() ? "sim" : "nao");

                        // categoria 1 
                        conteudo.append((null == bean.getCategoria1() ? "000" : bean.getCategoria1().getCodInterno()));

                        // categoria 2
                        conteudo.append((null == bean.getCategoria2() ? "000" : bean.getCategoria2().getCodInterno()));

                        // categoria 3
                        conteudo.append((null == bean.getCategoria3() ? "000" : bean.getCategoria3().getCodInterno()));

                        // crossover
                        conteudo.append((bean.getCrossover() ? "sim" : "nao"));

                        // dias de execucao  
                        conteudo.append(StringUtils.leftPad(bean.getDiasExecucao1().toString(), 4, " "));

                        // dias de execucao 2
                        conteudo.append(StringUtils.leftPad(bean.getDiasExecucao2().toString(), 4, " "));

                        // afinidade1
                        String afinidade1 = mgb.getAfinidade1();
                        if (afinidade1.length() > 30) {
                            afinidade1 = afinidade1.substring(0, 30);
                        }

                        if (afinidade1.length() < 30) {
                            afinidade1 = StringUtils.leftPad(afinidade1, 30, " ");
                        }
                        afinidade1 = Utilities.formatarHexExp(afinidade1);
                        conteudo.append(afinidade1);

                        // afinidade2
                        String afinidade2 = mgb.getAfinidade2();
                        if (afinidade2.length() > 30) {
                            afinidade2 = afinidade2.substring(0, 30);
                        }

                        if (afinidade2.length() < 30) {
                            afinidade2 = StringUtils.leftPad(afinidade2, 30, " ");
                        }
                        afinidade2 = Utilities.formatarHexExp(afinidade2);
                        conteudo.append(afinidade2);

                        // afinidade3
                        String afinidade3 = mgb.getAfinidade3();
                        if (afinidade3.length() > 30) {
                            afinidade3 = afinidade2.substring(0, 30);
                        }

                        if (afinidade3.length() < 30) {
                            afinidade3 = StringUtils.leftPad(afinidade3, 30, " ");
                        }
                        afinidade3 = Utilities.formatarHexExp(afinidade3);
                        conteudo.append(afinidade3);

                        // afinidade4
                        String afinidade4 = mgb.getAfinidade4();
                        if (afinidade4.length() > 30) {
                            afinidade4 = afinidade4.substring(0, 30);
                        }

                        if (afinidade4.length() < 30) {
                            afinidade4 = StringUtils.leftPad(afinidade4, 30, " ");
                        }
                        afinidade4 = Utilities.formatarHexExp(afinidade4);
                        conteudo.append(afinidade4);

                        // gravadora
                        conteudo.append(null != mgb.getGravadora() ? StringUtils.leftPad(mgb.getGravadora().toString(), 3, "0") : "000");

                        // ano gravacao
                        conteudo.append(StringUtils.leftPad(mgb.getAnoGravacao().toString(), 4, "0"));

                        // ano velocidade
                        conteudo.append((mgb.getBpm() > 180 ? 3 : (mgb.getBpm() > 120 ? 2 : 1)));

                        // data
                        conteudo.append(new SimpleDateFormat("dd/MM/yy").format(bean.getData()));

                        // data ultima execução
                        conteudo.append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucaoData()));

                        // tempo
                        String tempo = mgb.getTempoTotal();
                        if (tempo.length() > 8) {
                            tempo = tempo.substring(0, 8);
                        }

                        if (tempo.length() < 8) {

                            tempo = "00:" + tempo;
                        }
                        conteudo.append(tempo);

                        // qtde de player total   
                        conteudo.append(StringUtils.leftPad(bean.getQtdePlayer().toString(), 3, "0"));

                        // data vencimento
                        conteudo.append(new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimento()));

                        // data vencimento crossover
                        conteudo.append(new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimentoCrossover()));

                        // frame inicial
                        conteudo.append(StringUtils.leftPad(bean.getFrameInicio().toString(), 8, "0"));

                        // frame final
                        conteudo.append(StringUtils.leftPad(bean.getFrameFinal().toString(), 8, "0"));

                        // msg
                        String msg = bean.getMsg();

                        if (null == msg) {
                            msg = "";
                        }

                        if (msg.length() > 40) {
                            msg = msg.substring(0, 40);
                        }

                        if (msg.length() < 40) {
                            msg = StringUtils.leftPad(msg, 40, " ");
                        }
                        // sem som
                        msg = Utilities.formatarHexExp(msg);
                        conteudo.append(msg);
                        conteudo.append(bean.getSemSom() ? "sim" : "nao");
                    }

                    if (expArquivoAudio) {

                        NtlmPasswordAuthentication auth1 = Utilities.getAuthSmbDefault();
                        NtlmPasswordAuthentication auth2 = Utilities.getAuthSmbDefault();

                        String origem = mgb.getArquivo();
                        String destino = dados.getLocalDestinoExp();

                        if (origem.endsWith("/")) {
                            origem = origem.substring(0, origem.length() - 1);
                        }

                        if (origem.startsWith("smb://srv-arquivos")) {
                            auth1 = Utilities.getAuthSmbDefault();
                        }

                        if (origem.startsWith("smb://192.168.1.249")) {
                            auth1 = Utilities.getAuthSmbDefault1921681249();
                        }

                        // -- 
                        if (destino.endsWith("/")) {
                            destino = destino.substring(0, destino.length() - 1);
                        }

                        if (destino.startsWith("smb://srv-arquivos")) {
                            auth2 = Utilities.getAuthSmbDefault();
                        }

                        if (destino.startsWith("smb://192.168.1.249")) {
                            auth2 = Utilities.getAuthSmbDefault1921681249();
                        }

                        SmbFile smbOrigem = new SmbFile(origem, auth1);
                        if (smbOrigem.exists()) {
                            Utilities.createLogMusica(dados.getLocalDestinoExp(), true, smbOrigem.getName());
                            SmbFile smbDestino = new SmbFile(destino.concat("/").concat(smbOrigem.getName()), auth2);

                            SmbFileInputStream sfis = new SmbFileInputStream(smbOrigem);
                            SmbFileOutputStream sfous = new SmbFileOutputStream(smbDestino, true);
                            IOUtils.copy(sfis, sfous);

                            sfis.close();
                            sfous.flush();
                            sfous.close();
                        } else {
                            Utilities.createLogMusica(dados.getLocalDestinoExp(), false, smbOrigem.getName());
                        }
                    }
                }
                quebraLinha = Utilities.quebrarLinhaComHexa();
            }

            if (null != list && !list.isEmpty()) {

                String destino = dados.getLocalDestinoExp();

                NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

                if (destino.endsWith("/")) {
                    destino = destino.substring(0, destino.length() - 1);
                }

                if (destino.startsWith("smb://srv-arquivos")) {
                    auth = Utilities.getAuthSmbDefault();
                }

                if (destino.startsWith("smb://192.168.1.249")) {
                    auth = Utilities.getAuthSmbDefault1921681249();
                }

                SmbFile smb = new SmbFile(destino, auth);
                SmbFile smb2 = new SmbFile(destino.concat("/").concat("musica.exp"), auth);

                if (!smb.exists()) {
                    smb.mkdirs();
                }

                if (smb2.exists()) {
                    StringBuffer aux = conteudo;
                    conteudo = new StringBuffer();
                    conteudo.append(Utilities.quebrarLinhaComHexa());
                    conteudo.append(aux.toString());
                }

                SmbFileOutputStream sfous = new SmbFileOutputStream(smb2, true);
                sfous.write(conteudo.toString().getBytes());

                sfous.flush();
                sfous.close();
            }
        } catch (SmbException e) {
            e.printStackTrace();
            throw e;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw e;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void logs(Integer idcliente) {
        ClienteBean cliente = repository.find(ClienteBean.class, idcliente);
    }
}
