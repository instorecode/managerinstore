package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoCategoriaBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.property.AudiostoreProgramacaoCategoria;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreProgramacaoDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

    public List<AudiostoreProgramacaoDTO> programacaoDTOList() {
        List<AudiostoreProgramacaoDTO> lista = new ArrayList<AudiostoreProgramacaoDTO>();
        List<AudiostoreProgramacaoBean> lista2 = repository.query(AudiostoreProgramacaoBean.class).findAll();

        for (AudiostoreProgramacaoBean prog : lista2) {
            AudiostoreProgramacaoDTO dto = new AudiostoreProgramacaoDTO();

            dto.setClienteNome(prog.getCliente().getNome());
            dto.setDataFinal(new SimpleDateFormat("dd/MM/yyyy").format(prog.getDataFinal()));
            dto.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").format(prog.getDataInicio()));
            dto.setDescricao(prog.getDescricao());

            String diasSemana = "";
            String connector = " ";

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
            dto.setId( Utilities.leftPad(prog.getId()) );

            lista.add(dto);
        }
        return lista;
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).findAll();
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

    public AudiostoreProgramacaoBean audiostoreProgramacaoBean(Integer id) {
        return repository.find(AudiostoreProgramacaoBean.class, id);
    }

    public void salvar(AudiostoreProgramacaoBean audiostoreProgramacaoBean, String horaInicio, String horaFinal, Integer[] categorias, Integer[] diasSemana) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            audiostoreProgramacaoBean.setHoraInicio(new SimpleDateFormat("HH:mm:ss").parse(horaInicio += ":00"));
            audiostoreProgramacaoBean.setHoraFinal(new SimpleDateFormat("HH:mm:ss").parse(horaFinal += ":00"));

            audiostoreProgramacaoBean.setSegundaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setTercaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setQuartaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setQuintaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setSextaFeira(Boolean.FALSE);
            audiostoreProgramacaoBean.setSabado(Boolean.FALSE);
            audiostoreProgramacaoBean.setDomingo(Boolean.FALSE);

            for (Integer dia : diasSemana) {
                if (dia == 1) {
                    audiostoreProgramacaoBean.setSegundaFeira(Boolean.TRUE);
                }

                if (dia == 2) {
                    audiostoreProgramacaoBean.setTercaFeira(Boolean.TRUE);
                }

                if (dia == 3) {
                    audiostoreProgramacaoBean.setQuartaFeira(Boolean.TRUE);
                }

                if (dia == 4) {
                    audiostoreProgramacaoBean.setQuintaFeira(Boolean.TRUE);
                }

                if (dia == 5) {
                    audiostoreProgramacaoBean.setSextaFeira(Boolean.TRUE);
                }

                if (dia == 6) {
                    audiostoreProgramacaoBean.setSabado(Boolean.TRUE);
                }

                if (dia == 7) {
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
                AudiostoreProgramacaoCategoriaBean bean = new AudiostoreProgramacaoCategoriaBean();
                bean.setAudiostoreCategoria(new AudiostoreCategoriaBean(codigo));
                bean.setAudiostoreProgramacao(audiostoreProgramacaoBean);
                repository.save(bean);
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
            AudiostoreProgramacaoBean audiostoreProgramacaoBean = audiostoreProgramacaoBean(id);
            if (audiostoreProgramacaoBean != null) {
                String alphab = "ABCDEFGHIJKLMNOPQRSTUVXYWZ";
                List<String> alphabList = Arrays.asList(alphab.split(""));
                String conteudo = "";
                String lineBreak = "";
                List<AudiostoreProgramacaoCategoriaBean> audiostoreProgramacaoCategoriaBeanList = repository.query(AudiostoreProgramacaoCategoriaBean.class).eq(AudiostoreProgramacaoCategoria.ID, id).findAll();
                if (audiostoreProgramacaoCategoriaBeanList.size() > 24) {
                    int ia = 1;
                    for (int i = 0; i < audiostoreProgramacaoCategoriaBeanList.size(); i++) {
                        AudiostoreProgramacaoCategoriaBean pcBean = audiostoreProgramacaoCategoriaBeanList.get(i);
                        if ((i % 24) == 0) {
                            conteudo += lineBreak;
                            conteudo += StringUtils.leftPad(audiostoreProgramacaoBean.getDescricao() + "-" + alphabList.get(ia), 21, " ");
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
                            lineBreak = "\n";
                            ia++;
                        } else {
                            conteudo += StringUtils.leftPad(pcBean.getAudiostoreCategoria().getCodigo().toString(), 3, "0");
                        }
                    }
                } else {
                    conteudo += StringUtils.leftPad(audiostoreProgramacaoBean.getDescricao(), 21, " ");
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
                }
                inputStreamDownload = new InputStreamDownload(new ByteArrayInputStream(conteudo.getBytes()), "application/exp", audiostoreProgramacaoBean.getDescricao() + ".exp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputStreamDownload;
    }
}
