package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoCategoriaBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.property.AudiostoreProgramacaoCategoria;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreProgramacaoDTO;
import br.com.instore.web.dto.AudiostoreProgramacaoJSON;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void beanList(Integer page, Integer rows, Integer id, Integer idcliente, String descricao) {
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

    public void salvar(AudiostoreProgramacaoBean audiostoreProgramacaoBean, String horaInicio, String horaFinal, Integer[] categorias, Integer[] diasSemana) {
        try {
            audiostoreProgramacaoBean.setConteudo(" ");
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
//            ValidatorFactory factory =
//                    Validation.buildDefaultValidatorFactory();
//            Validator validator = factory.getValidator();
//            Set<ConstraintViolation<MyBean>> constraintViolations =
//                    validator.validate(bean);

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

            if (null != audiostoreProgramacaoBean && null != audiostoreProgramacaoBean.getId() && audiostoreProgramacaoBean.getId() > 0) {
                repository.save(repository.marge(audiostoreProgramacaoBean));
            } else {
                repository.save(audiostoreProgramacaoBean);
            }

            List<AudiostoreProgramacaoCategoriaBean> lista = repository.query(AudiostoreProgramacaoCategoriaBean.class).eq(AudiostoreProgramacaoCategoria.ID, audiostoreProgramacaoBean.getId()).findAll();
            for (AudiostoreProgramacaoCategoriaBean bean : lista) {
                repository.delete(bean);
            }

            for (Integer codigo : categorias) {
                if (null != codigo && codigo > 0) {
                    AudiostoreProgramacaoCategoriaBean bean = new AudiostoreProgramacaoCategoriaBean();
                    bean.setAudiostoreCategoria(new AudiostoreCategoriaBean(codigo));
                    bean.setAudiostoreProgramacao(audiostoreProgramacaoBean);
                    repository.save(bean);
                }
            }


            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
            repository.finalize();
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
                            conteudo += (audiostoreProgramacaoBean.getSegundaFeira() ? "x" : " ");
                            conteudo += (audiostoreProgramacaoBean.getTercaFeira() ? "x" : " ");
                            conteudo += (audiostoreProgramacaoBean.getQuartaFeira() ? "x" : " ");
                            conteudo += (audiostoreProgramacaoBean.getQuintaFeira() ? "x" : " ");
                            conteudo += (audiostoreProgramacaoBean.getSextaFeira() ? "x" : " ");
                            conteudo += (audiostoreProgramacaoBean.getSabado() ? "x" : " ");
                            conteudo += (audiostoreProgramacaoBean.getDomingo() ? "x" : " ");
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
                    conteudo += (audiostoreProgramacaoBean.getSegundaFeira() ? "x" : " ");
                    conteudo += (audiostoreProgramacaoBean.getTercaFeira() ? "x" : " ");
                    conteudo += (audiostoreProgramacaoBean.getQuartaFeira() ? "x" : " ");
                    conteudo += (audiostoreProgramacaoBean.getQuintaFeira() ? "x" : " ");
                    conteudo += (audiostoreProgramacaoBean.getSextaFeira() ? "x" : " ");
                    conteudo += (audiostoreProgramacaoBean.getSabado() ? "x" : " ");
                    conteudo += (audiostoreProgramacaoBean.getDomingo() ? "x" : " ");
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
        ConfigAppBean config = repository.find(ConfigAppBean.class, 1);
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
                                conteudo += (audiostoreProgramacaoBean.getSegundaFeira() ? "x" : " ");
                                conteudo += (audiostoreProgramacaoBean.getTercaFeira() ? "x" : " ");
                                conteudo += (audiostoreProgramacaoBean.getQuartaFeira() ? "x" : " ");
                                conteudo += (audiostoreProgramacaoBean.getQuintaFeira() ? "x" : " ");
                                conteudo += (audiostoreProgramacaoBean.getSextaFeira() ? "x" : " ");
                                conteudo += (audiostoreProgramacaoBean.getSabado() ? "x" : " ");
                                conteudo += (audiostoreProgramacaoBean.getDomingo() ? "x" : " ");
                                conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraInicio());
                                conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraFinal());
                                conteudo += StringUtils.leftPad(pcBean.getAudiostoreCategoria().getCodigo().toString(), 3, "0");
                                conteudo += conteudoo;
                                conteudo += audiostoreProgramacaoBean.getLoopback() ? 1 : 0;
                                lineBreak = "\r\n";
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
                        conteudo += (audiostoreProgramacaoBean.getSegundaFeira() ? "x" : " ");
                        conteudo += (audiostoreProgramacaoBean.getTercaFeira() ? "x" : " ");
                        conteudo += (audiostoreProgramacaoBean.getQuartaFeira() ? "x" : " ");
                        conteudo += (audiostoreProgramacaoBean.getQuintaFeira() ? "x" : " ");
                        conteudo += (audiostoreProgramacaoBean.getSextaFeira() ? "x" : " ");
                        conteudo += (audiostoreProgramacaoBean.getSabado() ? "x" : " ");
                        conteudo += (audiostoreProgramacaoBean.getDomingo() ? "x" : " ");
                        conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraInicio());
                        conteudo += (new SimpleDateFormat("HH:mm")).format(audiostoreProgramacaoBean.getHoraFinal());

                        for (AudiostoreProgramacaoCategoriaBean pcBean : audiostoreProgramacaoCategoriaBeanList) {
                            conteudo += StringUtils.leftPad(pcBean.getAudiostoreCategoria().getCodigo().toString(), 3, "0");
                        }

                        conteudo += conteudoo;
                        conteudo += audiostoreProgramacaoBean.getLoopback() ? 1 : 0;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AudiostoreCategoriaBean> categorias(Integer id) {
        return repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", id).findAll();
    }

    public void validarProgramacao(Integer[] id_list) {
        List<AudiostoreProgramacaoBean> list = repository.query(AudiostoreProgramacaoBean.class).in("id", id_list).findAll();

        boolean ajaxResultBool = true;
        String ajaxResultStr = "";
        // verifica se todos são do mesmo cliente
        Integer idcliente = list.get(0).getCliente().getIdcliente();
        for (AudiostoreProgramacaoBean bean : list) {
            if (!bean.getCliente().getIdcliente().equals(idcliente)) {
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
    }
}
