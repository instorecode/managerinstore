package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoComercialBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.property.AudiostoreProgramacaoCategoria;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreProgramacaoDTO;
import br.com.instore.web.dto.AudiostoreProgramacaoJSON;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.lang.StringUtils;

@RequestScoped
public class RequestAudiostoreProgramacao implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestAudiostoreProgramacao() {
    }

    public RequestAudiostoreProgramacao(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public AudiostoreProgramacaoJSON beanList(Integer page, Integer rows, Integer id, Integer idcliente, String descricao) {
        AudiostoreProgramacaoJSON json = new AudiostoreProgramacaoJSON();

        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<AudiostoreProgramacaoBean> lista = new ArrayList<AudiostoreProgramacaoBean>();

        Query q1 = repository.query(AudiostoreProgramacaoBean.class);
        Query q2 = repository.query(AudiostoreProgramacaoBean.class);

        if (null != id && id > 0) {
            q1.eq("id", id);
            q2.eq("id", id);
            json.setId(id);
        }

        if (null != idcliente && idcliente > 0) {
            q1.eq("cliente.idcliente", idcliente);
            q2.eq("cliente.idcliente", idcliente);
            json.setIdcliente(idcliente);
        }

        if (null != descricao && !descricao.isEmpty()) {
            q1.ilikeAnyWhere("descricao", descricao);
            q2.ilikeAnyWhere("descricao", descricao);
            json.setDescricao(descricao);
        }

        json.setCount(q1.count().intValue());
        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();


        json.setPage(page);
        json.setSize(size);

        List<AudiostoreProgramacaoDTO> rowsList = new ArrayList<AudiostoreProgramacaoDTO>();
        for (AudiostoreProgramacaoBean prog : lista) {

            AudiostoreProgramacaoDTO dto = new AudiostoreProgramacaoDTO();

            dto.setClienteNome(prog.getCliente().getNome());
            dto.setDataFinal(new SimpleDateFormat("dd/MM/yyyy").format(prog.getDataFinal()));
            dto.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").format(prog.getDataInicio()));
            dto.setDescricao(prog.getDescricao());

            String diasSemana = "";
            String connector = "";

            if (prog.getSegundaFeira()) {
                diasSemana += connector + "Segunda Feira";
                connector = ", ";
            }

            if (prog.getTercaFeira()) {
                diasSemana += connector + "Terça Feira";
                connector = ", ";
            }

            if (prog.getQuartaFeira()) {
                diasSemana += connector + "Quarta Feira";
                connector = ", ";
            }

            if (prog.getQuintaFeira()) {
                diasSemana += connector + "Quinta Feira";
                connector = ", ";
            }

            if (prog.getSextaFeira()) {
                diasSemana += connector + "Sexta Feira";
                connector = ", ";
            }

            if (prog.getSabado()) {
                diasSemana += connector + "Sabado";
                connector = ", ";
            }

            if (prog.getDomingo()) {
                diasSemana += connector + "Domingo";
                connector = ", ";
            }

            dto.setDiasSemana(diasSemana);
            dto.setHoraFinal(new SimpleDateFormat("HH:mm:ss").format(prog.getHoraFinal()));
            dto.setHoraInicio(new SimpleDateFormat("HH:mm:ss").format(prog.getHoraInicio()));
            dto.setId(prog.getId());

            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
        return json;
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        return clienteBeanList;
    }

    public List<AudiostoreCategoriaBean> categoriaBeanList() {
        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).findAll();
        return audiostoreCategoriaBeanList;
    }

    public List<AudiostoreProgramacaoCategoriaBean> programacaoCategoriaBeanList(Integer id) {
        List<AudiostoreProgramacaoCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreProgramacaoCategoriaBean.class).eq(AudiostoreProgramacaoCategoria.ID, id).findAll();
        return audiostoreCategoriaBeanList;
    }

    public AudiostoreProgramacaoBean bean(Integer id) {
        return repository.find(AudiostoreProgramacaoBean.class, id);
    }

    public AudiostoreProgramacaoDTO beanDto(Integer id) {
        AudiostoreProgramacaoBean prog = repository.find(AudiostoreProgramacaoBean.class, id);
        AudiostoreProgramacaoDTO dto = new AudiostoreProgramacaoDTO();
        if (null != prog) {


            dto.setClienteNome(prog.getCliente().getNome());
            dto.setDataFinal(new SimpleDateFormat("dd/MM/yyyy").format(prog.getDataFinal()));
            dto.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").format(prog.getDataInicio()));
            dto.setDescricao(prog.getDescricao());

            String diasSemana = "";
            String connector = "";

            if (prog.getSegundaFeira()) {
                diasSemana += connector + "Segunda Feira";
                connector = ", ";
            }

            if (prog.getTercaFeira()) {
                diasSemana += connector + "Terça Feira";
                connector = ", ";
            }

            if (prog.getQuartaFeira()) {
                diasSemana += connector + "Quarta Feira";
                connector = ", ";
            }

            if (prog.getQuintaFeira()) {
                diasSemana += connector + "Quinta Feira";
                connector = ", ";
            }

            if (prog.getSextaFeira()) {
                diasSemana += connector + "Sexta Feira";
                connector = ", ";
            }

            if (prog.getSabado()) {
                diasSemana += connector + "Sabado";
                connector = ", ";
            }

            if (prog.getDomingo()) {
                diasSemana += connector + "Domingo";
                connector = ", ";
            }

            dto.setDiasSemana(diasSemana);
            dto.setHoraFinal(new SimpleDateFormat("HH:mm:ss").format(prog.getHoraFinal()));
            dto.setHoraInicio(new SimpleDateFormat("HH:mm:ss").format(prog.getHoraInicio()));
            dto.setId(prog.getId());
        }


        return dto;
    }

    public void salvar(AudiostoreProgramacaoBean audiostoreProgramacaoBean, String horaInicio, String horaFinal, Integer[] categorias, Integer[] diasSemana) {
        try {
            if (null == audiostoreProgramacaoBean.getConteudo()) {
                audiostoreProgramacaoBean.setConteudo(" ");
            }

            if (null == categorias || categorias.length <= 0) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe pelomenos uma categoria!")).recursive().serialize();
                return;
            }

            if (null == diasSemana || diasSemana.length <= 0) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe pelomenos um dia da semana!")).recursive().serialize();
                return;
            }

            if (audiostoreProgramacaoBean.getDataInicio().after(audiostoreProgramacaoBean.getDataFinal())) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "A data inicial deve ser menor que a data final!")).recursive().serialize();
                return;
            }

            repository.setUsuario(sessionUsuario.getUsuarioBean());
            audiostoreProgramacaoBean.setHoraInicio(new SimpleDateFormat("HH:mm:ss").parse(horaInicio));
            audiostoreProgramacaoBean.setHoraFinal(new SimpleDateFormat("HH:mm:ss").parse(horaFinal));

            audiostoreProgramacaoBean.setSegundaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setTercaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setQuartaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setQuintaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setSextaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setSabado(Boolean.FALSE);
            audiostoreProgramacaoBean.setDomingo(Boolean.FALSE);

            for (Integer dia : diasSemana) {
                if (null != dia && dia == 1) {
                    audiostoreProgramacaoBean.setSegundaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 2) {
                    audiostoreProgramacaoBean.setTercaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 3) {
                    audiostoreProgramacaoBean.setQuartaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 4) {
                    audiostoreProgramacaoBean.setQuintaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 5) {
                    audiostoreProgramacaoBean.setSextaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 6) {
                    audiostoreProgramacaoBean.setSabado(Boolean.TRUE);
                }

                if (null != dia && dia == 7) {
                    audiostoreProgramacaoBean.setDomingo(Boolean.TRUE);
                }
            }


            String sql = "";

            if (null != audiostoreProgramacaoBean && null != audiostoreProgramacaoBean.getId() && audiostoreProgramacaoBean.getId() > 0) {
                sql = "DELETE FROM audiostore_programacao_categoria WHERE id = " + audiostoreProgramacaoBean.getId() + ";";
                repository.query(sql).executeSQLCommand2();

                sql = "DELETE FROM audiostore_programacao WHERE id = " + audiostoreProgramacaoBean.getId() + "; ";
                repository.query(sql).executeSQLCommand2();
            }

            sql = "INSERT INTO audiostore_programacao VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            sql = sql.replaceFirst("\\?", "null"); // id
            sql = sql.replaceFirst("\\?", "'" + audiostoreProgramacaoBean.getDescricao() + "'"); // descricao
            sql = sql.replaceFirst("\\?", audiostoreProgramacaoBean.getCliente().getIdcliente().toString()); // idcliente
            sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(audiostoreProgramacaoBean.getDataInicio()) + "'"); // data inicio
            sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(audiostoreProgramacaoBean.getDataFinal()) + "'"); // data final
            sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("HH:mm:ss").format(audiostoreProgramacaoBean.getHoraInicio()) + "'"); // hora inicio
            sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("HH:mm:ss").format(audiostoreProgramacaoBean.getHoraFinal()) + "'"); // hora final
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getSegundaFeira() ? "1" : "0")); // segunda
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getTercaFeira() ? "1" : "0")); // terca
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getQuartaFeira() ? "1" : "0")); // quarta
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getQuintaFeira() ? "1" : "0")); // quinta
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getSextaFeira() ? "1" : "0")); // sexta
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getSabado() ? "1" : "0")); // sabado
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getDomingo() ? "1" : "0")); // domingo
            sql = sql.replaceFirst("\\?", (null != audiostoreProgramacaoBean.getConteudo() ? "'" + audiostoreProgramacaoBean.getConteudo() + "'" : "''")); // conteudo
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getLoopback() ? "1" : "0")); // loopback
            repository.query(sql).executeSQLCommand2();

            for (Integer codigo : categorias) {
                if (null != codigo && codigo > 0) {
                    sql = "INSERT INTO audiostore_programacao_categoria VALUES(?,?,?);";
                    sql = sql.replaceFirst("\\?", "null");
                    sql = sql.replaceFirst("\\?", "'" + codigo + "'");
                    sql = sql.replaceFirst("\\?", "(select id from audiostore_programacao order by id desc limit 1)");
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

            List<AudiostoreProgramacaoCategoriaBean> lista = repository.query(AudiostoreProgramacaoCategoriaBean.class).eq(AudiostoreProgramacaoCategoria.ID, id).findAll();
            for (AudiostoreProgramacaoCategoriaBean bean : lista) {
                repository.delete(bean);
            }

            AudiostoreProgramacaoBean audiostoreProgramacaoBean = repository.marge((AudiostoreProgramacaoBean) repository.find(AudiostoreProgramacaoBean.class, id));
            repository.delete(audiostoreProgramacaoBean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Audiostore categoria removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a audiostore programação!")).recursive().serialize();
            repository.finalize();
        }
    }

    public InputStreamDownload download(Integer id) {
        InputStreamDownload inputStreamDownload = null;

        try {
            AudiostoreProgramacaoBean audiostoreProgramacaoBean = bean(id);
            if (audiostoreProgramacaoBean != null) {
                String alphab = "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
                List<String> alphabList = Arrays.asList(alphab.split(""));
                String conteudo = "";
                String lineBreak = "";
                List<AudiostoreProgramacaoCategoriaBean> audiostoreProgramacaoCategoriaBeanList = repository.query(AudiostoreProgramacaoCategoriaBean.class).eq(AudiostoreProgramacaoCategoria.ID, id).findAll();
                if (audiostoreProgramacaoCategoriaBeanList.size() > 24) {
                    int ia = 1;
                    String descr = audiostoreProgramacaoBean.getDescricao() + "-" + alphabList.get(ia);
                    if (descr.length() < 20) {
                        descr = StringUtils.leftPad(descr, 20, " ");
                    } else {
                        if (descr.length() > 20) {
                            descr = descr.substring(0, 20);
                        }
                    }

                    String conteudoo = audiostoreProgramacaoBean.getConteudo();
                    if (conteudoo.length() < 70) {
                        conteudoo = StringUtils.leftPad(conteudoo, 70, " ");
                    } else {
                        if (conteudoo.length() > 70) {
                            conteudoo = conteudoo.substring(0, 70);
                        }
                    }

                    for (int i = 0; i < audiostoreProgramacaoCategoriaBeanList.size(); i++) {
                        AudiostoreProgramacaoCategoriaBean pcBean = audiostoreProgramacaoCategoriaBeanList.get(i);
                        if ((i % 24) == 0) {
                            conteudo += lineBreak;
                            conteudo += descr;

                            conteudo += (new SimpleDateFormat("ddMMyy")).format(audiostoreProgramacaoBean.getDataInicio());
                            conteudo += (new SimpleDateFormat("ddMMyy")).format(audiostoreProgramacaoBean.getDataFinal());
                            conteudo += (audiostoreProgramacaoBean.getSegundaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getTercaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getQuartaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getQuintaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getSextaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getSabado() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getDomingo() ? "X" : " ");
                            conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraInicio());
                            conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraFinal());
                            conteudo += StringUtils.leftPad(pcBean.getAudiostoreCategoria().getCodigo().toString(), 3, "0");
                            conteudo += conteudoo;
                            conteudo += audiostoreProgramacaoBean.getLoopback() ? 1 : 0;
                            lineBreak = "\n";
                            ia++;
                        } else {
                            conteudo += StringUtils.leftPad(pcBean.getAudiostoreCategoria().getCodigo().toString(), 3, "0");
                        }
                    }
                } else {
                    String descr = audiostoreProgramacaoBean.getDescricao();
                    if (descr.length() < 20) {
                        descr = StringUtils.leftPad(descr, 20, " ");
                    } else {
                        if (descr.length() > 20) {
                            descr = descr.substring(0, 20);
                        }
                    }

                    String conteudoo = audiostoreProgramacaoBean.getConteudo();
                    if (conteudoo.length() < 70) {
                        conteudoo = StringUtils.leftPad(conteudoo, 70, " ");
                    } else {
                        if (conteudoo.length() > 70) {
                            conteudoo = conteudoo.substring(0, 70);
                        }
                    }
                    conteudo = "";
                    conteudo += descr;
                    conteudo += (new SimpleDateFormat("ddMMyy")).format(audiostoreProgramacaoBean.getDataInicio());
                    conteudo += (new SimpleDateFormat("ddMMyy")).format(audiostoreProgramacaoBean.getDataFinal());
                    conteudo += (audiostoreProgramacaoBean.getSegundaFeira() ? "X" : " ");
                    conteudo += (audiostoreProgramacaoBean.getTercaFeira() ? "X" : " ");
                    conteudo += (audiostoreProgramacaoBean.getQuartaFeira() ? "X" : " ");
                    conteudo += (audiostoreProgramacaoBean.getQuintaFeira() ? "X" : " ");
                    conteudo += (audiostoreProgramacaoBean.getSextaFeira() ? "X" : " ");
                    conteudo += (audiostoreProgramacaoBean.getSabado() ? "X" : " ");
                    conteudo += (audiostoreProgramacaoBean.getDomingo() ? "X" : " ");
                    conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraInicio());
                    conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraFinal());

                    for (AudiostoreProgramacaoCategoriaBean pcBean : audiostoreProgramacaoCategoriaBeanList) {
                        conteudo += StringUtils.leftPad(pcBean.getAudiostoreCategoria().getCodigo().toString(), 3, "0");
                    }

                    conteudo += conteudoo;
                    conteudo += audiostoreProgramacaoBean.getLoopback() ? 1 : 0;
                }
                inputStreamDownload = new InputStreamDownload(new ByteArrayInputStream(conteudo.getBytes()), "application/exp", audiostoreProgramacaoBean.getDescricao() + ".exp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputStreamDownload;
    }

    public void upload(Integer[] id_list) {
        try {
            String conteudo = "";
            String quebraLinha = "";
            List<AudiostoreProgramacaoBean> list = repository.query(AudiostoreProgramacaoBean.class).in("id", id_list).findAll();
            for (AudiostoreProgramacaoBean audiostoreProgramacaoBean : list) {
                conteudo += quebraLinha;
                if (audiostoreProgramacaoBean != null) {
                    String alphab = "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
                    List<String> alphabList = Arrays.asList(alphab.split(""));

                    String lineBreak = "";
                    List<AudiostoreProgramacaoCategoriaBean> audiostoreProgramacaoCategoriaBeanList = repository.query(AudiostoreProgramacaoCategoriaBean.class).eq(AudiostoreProgramacaoCategoria.ID, audiostoreProgramacaoBean.getId()).findAll();


                    if (audiostoreProgramacaoCategoriaBeanList.size() > 24) {
                        List<List<AudiostoreProgramacaoCategoriaBean>> listaDeLista = new ArrayList<List<AudiostoreProgramacaoCategoriaBean>>();
                        int contador_aux = 0;
                        List<AudiostoreProgramacaoCategoriaBean> __lista = new ArrayList<AudiostoreProgramacaoCategoriaBean>();

                        for (AudiostoreProgramacaoCategoriaBean b : audiostoreProgramacaoCategoriaBeanList) {
                            if (contador_aux < 24) {
                                __lista.add(b);
                                contador_aux++;
                            } else {
                                contador_aux = 1;
                                listaDeLista.add(__lista);
                                __lista = new ArrayList<AudiostoreProgramacaoCategoriaBean>();
                                __lista.add(b);
                            }
                        }

                        listaDeLista.add(__lista);

                        contador_aux = 1;

                        for (List<AudiostoreProgramacaoCategoriaBean> itemLista : listaDeLista) {
                            String descr = audiostoreProgramacaoBean.getDescricao();
                            if (descr.length() < 20) {
                                descr += "-" + alphabList.get(contador_aux);
                                descr = StringUtils.leftPad(descr, 20, " ");
                            } else {
                                if (descr.length() > 20) {
                                    descr = descr.substring(0, 18);
                                    descr += "-" + alphabList.get(contador_aux);
                                }
                            }

                            String conteudoo = audiostoreProgramacaoBean.getConteudo();
                            if (conteudoo.length() < 70) {
                                conteudoo = StringUtils.leftPad(conteudoo, 70, " ");
                            } else {
                                if (conteudoo.length() > 70) {
                                    conteudoo = conteudoo.substring(0, 70);
                                }
                            }
                            conteudo += lineBreak;
                            conteudo += Utilities.formatarHexExp(descr);;
                            conteudo += (new SimpleDateFormat("ddMMyy")).format(audiostoreProgramacaoBean.getDataInicio());
                            conteudo += (new SimpleDateFormat("ddMMyy")).format(audiostoreProgramacaoBean.getDataFinal());
                            conteudo += (audiostoreProgramacaoBean.getSegundaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getTercaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getQuartaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getQuintaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getSextaFeira() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getSabado() ? "X" : " ");
                            conteudo += (audiostoreProgramacaoBean.getDomingo() ? "X" : " ");
                            conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraInicio());
                            conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraFinal());

                            int categ24 = 0;
                            for (AudiostoreProgramacaoCategoriaBean pcBean : itemLista) {
                                conteudo += StringUtils.leftPad(pcBean.getAudiostoreCategoria().getCodInterno().toString(), 3, "0");
                                categ24++;
                            }

                            if (categ24 < 24) {
                                for (int k = 1; k <= (24 - categ24); k++) {
                                    conteudo += "000";
                                }
                            }

                            conteudo += conteudoo;
                            conteudo += audiostoreProgramacaoBean.getLoopback() ? 1 : 0;

                            contador_aux++;
                            lineBreak = Utilities.quebrarLinhaComHexa();
                        }

                        int ia = 1;
                        String descr = audiostoreProgramacaoBean.getDescricao() + "-" + alphabList.get(ia);
                        if (descr.length() < 20) {
                            descr = StringUtils.leftPad(descr, 20, " ");
                        } else {
                            if (descr.length() > 20) {
                                descr = descr.substring(0, 20);
                            }
                        }

                        String conteudoo = audiostoreProgramacaoBean.getConteudo();
                        if (conteudoo.length() < 70) {
                            conteudoo = StringUtils.leftPad(conteudoo, 70, " ");
                        } else {
                            if (conteudoo.length() > 70) {
                                conteudoo = conteudoo.substring(0, 70);
                            }
                        }

                    } else {
                        String descr = audiostoreProgramacaoBean.getDescricao();
                        if (descr.length() < 20) {
                            descr = StringUtils.leftPad(descr, 20, " ");
                        } else {
                            if (descr.length() > 20) {
                                descr = descr.substring(0, 20);
                            }
                        }

                        String conteudoo = audiostoreProgramacaoBean.getConteudo();
                        if (conteudoo.length() < 70) {
                            conteudoo = StringUtils.leftPad(conteudoo, 70, " ");
                        } else {
                            if (conteudoo.length() > 70) {
                                conteudoo = conteudoo.substring(0, 70);
                            }
                        }

                        conteudo += lineBreak;
                        conteudo += descr;
                        conteudo += (new SimpleDateFormat("ddMMyy")).format(audiostoreProgramacaoBean.getDataInicio());
                        conteudo += (new SimpleDateFormat("ddMMyy")).format(audiostoreProgramacaoBean.getDataFinal());
                        conteudo += (audiostoreProgramacaoBean.getSegundaFeira() ? "X" : " ");
                        conteudo += (audiostoreProgramacaoBean.getTercaFeira() ? "X" : " ");
                        conteudo += (audiostoreProgramacaoBean.getQuartaFeira() ? "X" : " ");
                        conteudo += (audiostoreProgramacaoBean.getQuintaFeira() ? "X" : " ");
                        conteudo += (audiostoreProgramacaoBean.getSextaFeira() ? "X" : " ");
                        conteudo += (audiostoreProgramacaoBean.getSabado() ? "X" : " ");
                        conteudo += (audiostoreProgramacaoBean.getDomingo() ? "X" : " ");
                        conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraInicio());
                        conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraFinal());

                        int categ24 = 0;
                        for (AudiostoreProgramacaoCategoriaBean pcBean : audiostoreProgramacaoCategoriaBeanList) {
                            conteudo += StringUtils.leftPad(pcBean.getAudiostoreCategoria().getCodInterno().toString(), 3, "0");
                            categ24++;
                        }

                        if (categ24 < 24) {
                            for (int k = 1; k <= (24 - categ24); k++) {
                                conteudo += "000";
                            }
                        }

                        conteudo += conteudoo;
                        conteudo += audiostoreProgramacaoBean.getLoopback() ? 1 : 0;
                        lineBreak = "\r\n";
                    }
                }

                quebraLinha = "\r\n";
            }

            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", list.get(0).getCliente().getIdcliente()).findOne();
            String destino = dados.getLocalDestinoExp();
            SmbFile smb = new SmbFile(destino, Utilities.getAuthSmbDefault());
            SmbFile smb2 = new SmbFile(destino + "programacao.exp", Utilities.getAuthSmbDefault());

            if (!smb.exists()) {
                smb.mkdirs();
            }

            SmbFileOutputStream sfous = new SmbFileOutputStream(smb2);
            sfous.write(conteudo.getBytes());
            sfous.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AudiostoreCategoriaBean> categorias(Integer id) {
        return repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", id).findAll();
    }

    public void validarProgramacao(Integer[] id_list, Integer idcliente, String descricao) {

        List<AudiostoreProgramacaoBean> list = null;

        if (null != id_list && id_list.length > 0) {
            list = repository.query(AudiostoreProgramacaoBean.class).in("id", id_list).findAll();
        } else {
            AudiostoreProgramacaoJSON json = beanList(1, 999999999, null, idcliente, descricao);
            if (null != json.getRows() && !json.getRows().isEmpty()) {
                id_list = new Integer[json.getRows().size()];
                int i = 0;
                for (AudiostoreProgramacaoDTO dto : json.getRows()) {
                    id_list[i] = dto.getId();
                    i++;
                }
            }
            list = repository.query(AudiostoreProgramacaoBean.class).in("id", id_list).findAll();
        }


        boolean ajaxResultBool = true;
        String ajaxResultStr = "";

        if (null != list && list.size() > 0) {
            // verifica se todos são do mesmo cliente
            Integer idclienteAux = list.get(0).getCliente().getIdcliente();
            for (AudiostoreProgramacaoBean bean : list) {
                if (!bean.getCliente().getIdcliente().equals(idclienteAux)) {
                    ajaxResultBool = false;
                    ajaxResultStr = "Você selecionou programações de clientes diferentes!";
                    break;
                }
            }
            if (ajaxResultBool) {
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", list.get(0).getCliente().getIdcliente()).findOne();
                if (null == dados || null == dados.getLocalDestinoExp() || dados.getLocalDestinoExp().trim().isEmpty()) {
                    ajaxResultBool = false;
                    ajaxResultStr = "O cliente não possui um local de destino para os arquivos de exportação!";
                }
            }


            try {
                upload(id_list);
            } catch (Exception e) {
                ajaxResultBool = false;
                ajaxResultStr = e.getMessage();
            }

            result.use(Results.json()).withoutRoot().from(new AjaxResult(ajaxResultBool, ajaxResultStr)).recursive().serialize();
        } else {
            ajaxResultBool = false;
            ajaxResultStr = "Não foi possive gerar arquivos!";
        }
    }

    public List<AudiostoreComercialBean> comercialList(AudiostoreProgramacaoBean prog) {
        List<AudiostoreComercialBean> list = new ArrayList<AudiostoreComercialBean>();
        if (null != prog) {
            list = repository.query(AudiostoreComercialBean.class)
                    .eq("cliente.idcliente", prog.getCliente().getIdcliente())
                    .lessEqual("periodoInicial", prog.getDataInicio())
                    .and()
                    .moreEqual("periodoFinal", prog.getDataFinal())
                    .and()
                    .moreEqual("dataVencimento", prog.getDataFinal())
                    .and()
                    .moreEqual("periodoFinal", prog.getDataFinal()).findAll();
        }

        return list;
    }

    public class AudiostoreProgramacaoComercialBeanExtended {

        private AudiostoreProgramacaoComercialBean beanLigacao;
        private AudiostoreComercialBean comercial;

        public AudiostoreProgramacaoComercialBean getBeanLigacao() {
            return beanLigacao;
        }

        public void setBeanLigacao(AudiostoreProgramacaoComercialBean beanLigacao) {
            this.beanLigacao = beanLigacao;
        }

        public AudiostoreComercialBean getComercial() {
            return comercial;
        }

        public void setComercial(AudiostoreComercialBean comercial) {
            this.comercial = comercial;
        }
    }

    public List<AudiostoreProgramacaoComercialBeanExtended> comercialVinculadosAProgramacao(AudiostoreProgramacaoBean prog) {
        List<AudiostoreProgramacaoComercialBeanExtended> ret = new ArrayList<AudiostoreProgramacaoComercialBeanExtended>();
        List<AudiostoreProgramacaoComercialBean> list = new ArrayList<AudiostoreProgramacaoComercialBean>();
        if (null != prog) {
            list = repository.query(AudiostoreProgramacaoComercialBean.class).eq("programacao", prog.getId()).findAll();
        }

        for (AudiostoreProgramacaoComercialBean item : list) {
            AudiostoreProgramacaoComercialBeanExtended bean = new AudiostoreProgramacaoComercialBeanExtended();
            bean.setBeanLigacao(item);
            bean.setComercial((AudiostoreComercialBean) repository.find(AudiostoreComercialBean.class, item.getComercial()));
            ret.add(bean);
        }
        return ret;
    }

    public void salvar2(AudiostoreProgramacaoBean audiostoreProgramacaoBean, String horaInicio, String horaFinal, Integer[] categorias, Integer[] diasSemana, String comercialHorarioA, String comercialHorarioB) {
        repository.setUsuario(sessionUsuario.getUsuarioBean());

        boolean acontinue = false;
        boolean bcontinue = false;
        String aerror = "";
        String berror = "";

        if (null != comercialHorarioA && !comercialHorarioA.isEmpty()) {
            acontinue = true;
        }

        if (null != comercialHorarioB && !comercialHorarioB.isEmpty()) {
            bcontinue = true;
        }

        if (acontinue && (comercialHorarioA.split(",").length > 0) && (comercialHorarioA.split(",").length % 2 == 0)) {
            acontinue = true;
        }

        if (bcontinue && (comercialHorarioB.split(",").length > 0) && (comercialHorarioB.split(",").length % 2 == 0)) {
            bcontinue = true;
        }

        List<String> comercialHorarioAList = new ArrayList<String>();
        List<String> comercialHorarioBList = new ArrayList<String>();

        if (acontinue) {
            comercialHorarioAList = Arrays.asList(comercialHorarioA.split(","));

            int i = 1;
            for (String val : comercialHorarioAList) {
                if (i % 2 != 0) {
                    try {
                        Integer.parseInt(val.trim());
                    } catch (NumberFormatException e) {
                        acontinue = false;
                        aerror = "O comercial relacionado ao inicio da programação está em um formato incorreto";
                    }
                } else {
                    try {
                        new SimpleDateFormat("HH:mm:ss").parse(val.trim());
                    } catch (ParseException e) {
                        acontinue = false;
                        aerror = "o horário( " + val.trim() + " ) relacionado ao inicio da programação está em um formato incorreto";
                    }
                }
                i++;
            }
        }

        if (bcontinue) {
            comercialHorarioBList = Arrays.asList(comercialHorarioB.split(","));

            int i = 1;
            for (String val : comercialHorarioBList) {
                if (i % 2 != 0) {
                    try {
                        Integer.parseInt(val.trim());
                    } catch (NumberFormatException e) {
                        bcontinue = false;
                        berror = "O comercial relacionado ao final da programação está em um formato incorreto";
                    }
                } else {
                    try {
                        new SimpleDateFormat("HH:mm:ss").parse(val.trim());
                    } catch (ParseException e) {
                        bcontinue = false;
                        berror = "o horário( " + val.trim() + " ) relacionado ao final da programação está em um formato incorreto";
                    }
                }
                i++;
            }
        }

        List<AudiostoreProgramacaoComercialBean> comercialList = new ArrayList<AudiostoreProgramacaoComercialBean>();

        if (!acontinue) {
            if ((null != comercialHorarioA && !comercialHorarioA.isEmpty()) || (null != comercialHorarioB && !comercialHorarioB.isEmpty())) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, aerror)).recursive().serialize();
                return;
            }
        }
        
        if (!bcontinue) {
            if ((null != comercialHorarioA && !comercialHorarioA.isEmpty()) || (null != comercialHorarioB && !comercialHorarioB.isEmpty())) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, berror)).recursive().serialize();
                return;
            }
        }

        try {
            if (null == audiostoreProgramacaoBean.getConteudo()) {
                audiostoreProgramacaoBean.setConteudo(" ");
            }

            if (null == categorias || categorias.length <= 0) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe pelomenos uma categoria!")).recursive().serialize();
                return;
            }

            if (null == diasSemana || diasSemana.length <= 0) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe pelomenos um dia da semana!")).recursive().serialize();
                return;
            }

            if (audiostoreProgramacaoBean.getDataInicio().after(audiostoreProgramacaoBean.getDataFinal())) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "A data inicial deve ser menor que a data final!")).recursive().serialize();
                return;
            }


            audiostoreProgramacaoBean.setHoraInicio(new SimpleDateFormat("HH:mm:ss").parse(horaInicio));
            audiostoreProgramacaoBean.setHoraFinal(new SimpleDateFormat("HH:mm:ss").parse(horaFinal));

            audiostoreProgramacaoBean.setSegundaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setTercaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setQuartaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setQuintaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setSextaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setSabado(Boolean.FALSE);
            audiostoreProgramacaoBean.setDomingo(Boolean.FALSE);

            for (Integer dia : diasSemana) {
                if (null != dia && dia == 1) {
                    audiostoreProgramacaoBean.setSegundaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 2) {
                    audiostoreProgramacaoBean.setTercaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 3) {
                    audiostoreProgramacaoBean.setQuartaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 4) {
                    audiostoreProgramacaoBean.setQuintaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 5) {
                    audiostoreProgramacaoBean.setSextaFeira(Boolean.TRUE);
                }

                if (null != dia && dia == 6) {
                    audiostoreProgramacaoBean.setSabado(Boolean.TRUE);
                }

                if (null != dia && dia == 7) {
                    audiostoreProgramacaoBean.setDomingo(Boolean.TRUE);
                }
            }


            String sql = "";

            if (null != audiostoreProgramacaoBean && null != audiostoreProgramacaoBean.getId() && audiostoreProgramacaoBean.getId() > 0) {
                sql = "DELETE FROM audiostore_programacao_categoria WHERE id = " + audiostoreProgramacaoBean.getId() + ";";
                repository.query(sql).executeSQLCommand2();

                sql = "DELETE FROM audiostore_programacao WHERE id = " + audiostoreProgramacaoBean.getId() + "; ";
                repository.query(sql).executeSQLCommand2();

                sql = "DELETE FROM audiostore_programacao_comercial WHERE programacao = " + audiostoreProgramacaoBean.getId() + ";";
                repository.query(sql).executeSQLCommand2();
            }

            sql = "INSERT INTO audiostore_programacao VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            sql = sql.replaceFirst("\\?", "null"); // id
            sql = sql.replaceFirst("\\?", "'" + audiostoreProgramacaoBean.getDescricao() + "'"); // descricao
            sql = sql.replaceFirst("\\?", audiostoreProgramacaoBean.getCliente().getIdcliente().toString()); // idcliente
            sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(audiostoreProgramacaoBean.getDataInicio()) + "'"); // data inicio
            sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(audiostoreProgramacaoBean.getDataFinal()) + "'"); // data final
            sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("HH:mm:ss").format(audiostoreProgramacaoBean.getHoraInicio()) + "'"); // hora inicio
            sql = sql.replaceFirst("\\?", "'" + new SimpleDateFormat("HH:mm:ss").format(audiostoreProgramacaoBean.getHoraFinal()) + "'"); // hora final
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getSegundaFeira() ? "1" : "0")); // segunda
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getTercaFeira() ? "1" : "0")); // terca
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getQuartaFeira() ? "1" : "0")); // quarta
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getQuintaFeira() ? "1" : "0")); // quinta
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getSextaFeira() ? "1" : "0")); // sexta
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getSabado() ? "1" : "0")); // sabado
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getDomingo() ? "1" : "0")); // domingo
            sql = sql.replaceFirst("\\?", (null != audiostoreProgramacaoBean.getConteudo() ? "'" + audiostoreProgramacaoBean.getConteudo() + "'" : "''")); // conteudo
            sql = sql.replaceFirst("\\?", (audiostoreProgramacaoBean.getLoopback() ? "1" : "0")); // loopback
            repository.query(sql).executeSQLCommand2();

            for (Integer codigo : categorias) {
                if (null != codigo && codigo > 0) {
                    sql = "INSERT INTO audiostore_programacao_categoria VALUES(?,?,?);";
                    sql = sql.replaceFirst("\\?", "null");
                    sql = sql.replaceFirst("\\?", "'" + codigo + "'");
                    sql = sql.replaceFirst("\\?", "(select id from audiostore_programacao order by id desc limit 1)");
                    repository.query(sql).executeSQLCommand2();
                }
            }

            sql = "select id as num, '' as param from audiostore_programacao order by id desc limit 1";
            final List<Integer> idprogList = new ArrayList<Integer>();
            repository.query(sql).executeSQL(new Each() {
                public Integer num;
                public String param;

                @Override
                public void each() {
                    idprogList.add(num);
                }
            });

            int idprog = idprogList.get(0);

            if (acontinue) {
                AudiostoreProgramacaoComercialBean bean = null;
                for (int i = 0; i < comercialHorarioAList.size(); i++) {
                    String val = comercialHorarioAList.get(i);
                    if ((i + 1) % 2 != 0) {
                        try {
                            int id = Integer.parseInt(val.trim());
                            bean = new AudiostoreProgramacaoComercialBean();
                            bean.setComercial(id);
                            bean.setInicialFinal(true);
                            bean.setProgramacao(idprog);
                        } catch (NumberFormatException e) {
                            bcontinue = false;
                            break;
                        }
                    } else {
                        bean.setIntervalo(val);
                        comercialList.add(bean);
                    }
                }
            }

            if (bcontinue) {
                AudiostoreProgramacaoComercialBean bean = null;
                for (int i = 0; i < comercialHorarioBList.size(); i++) {
                    String val = comercialHorarioBList.get(i);
                    if ((i + 1) % 2 != 0) {
                        try {
                            int id = Integer.parseInt(val.trim());
                            bean = new AudiostoreProgramacaoComercialBean();
                            bean.setComercial(id);
                            bean.setInicialFinal(false);
                            bean.setProgramacao(idprog);
                        } catch (NumberFormatException e) {
                            bcontinue = false;
                            break;
                        }
                    } else {
                        bean.setIntervalo(val);
                        comercialList.add(bean);
                    }
                }
            }

            if (acontinue || bcontinue) {
                for (AudiostoreProgramacaoComercialBean bean : comercialList) {
                    repository.save(bean);
                }
            }

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }
}
