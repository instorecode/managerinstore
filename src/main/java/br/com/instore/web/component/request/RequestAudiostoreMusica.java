package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreCategoriaDTO;
import br.com.instore.web.dto.CadastroMusicaDTO;
import br.com.instore.web.dto.AudiostoreMusicaDTO;
import br.com.instore.web.dto.Mdto;
import br.com.instore.web.dto.MusicaDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;

@RequestScoped
public class RequestAudiostoreMusica implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestAudiostoreMusica() {
    }

    public RequestAudiostoreMusica(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public List<AudiostoreMusicaDTO> beanList() {
        List<AudiostoreMusicaBean> lista = new ArrayList<AudiostoreMusicaBean>();
        List<AudiostoreMusicaDTO> lista2 = new ArrayList<AudiostoreMusicaDTO>();
        lista = repository.query(AudiostoreMusicaBean.class).findAll();
        for (AudiostoreMusicaBean bean : lista) {
            AudiostoreMusicaDTO dto = new AudiostoreMusicaDTO();

            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setGravadora(bean.getGravadora().getNome());
            dto.setAfinidade1(bean.getAfinidade1());
            dto.setAfinidade2(bean.getAfinidade2());
            dto.setAfinidade3(bean.getAfinidade3());
            dto.setAfinidade4(bean.getAfinidade4());
            dto.setAnoGravacao(bean.getAnoGravacao().toString());
            dto.setArquivo(bean.getArquivo());
            if (null != bean.getCategoria1()) {
                dto.setCategoria1(bean.getCategoria1().getCategoria());
            }
            if (null != bean.getCategoria2()) {
                dto.setCategoria2(bean.getCategoria2().getCategoria());
            }
            if (null != bean.getCategoria3()) {
                dto.setCategoria3(bean.getCategoria3().getCategoria());
            }
            dto.setCrossover(bean.getCrossover() ? "Sim" : "Não");
            dto.setCut(bean.getCut() ? "Cut" : "FadeOut");
            dto.setData(new SimpleDateFormat("dd/MM/yyyy").format(bean.getData()));
            dto.setDataVencimento(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataVencimento()));
            dto.setDataVencimentoCrossover(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataVencimentoCrossover()));
            dto.setDiasExecucao1(bean.getDiasExecucao1().toString());
            dto.setDiasExecucao2(bean.getDiasExecucao2().toString());
            dto.setFrameFinal(bean.getFrameFinal().toString());
            dto.setFrameInicio(bean.getFrameInicio().toString());
            dto.setInterprete(bean.getInterprete());
            dto.setMsg(bean.getMsg());
            dto.setQtde(bean.getQtde().toString());
            dto.setQtdePlayer(bean.getQtdePlayer().toString());
            dto.setRandom(bean.getRandom().toString());
            dto.setSemSom(bean.getSemSom() ? "Sim" : "Não");
            dto.setSuperCrossover(bean.getSuperCrossover() ? "sim" : "Não");
            dto.setTempoTotal(new SimpleDateFormat("HH:mm:ss").format(bean.getTempoTotal()));

            switch (bean.getTipoInterprete()) {
                case 1:
                    dto.setTipoInterprete("Masculino");
                    break;
                case 2:
                    dto.setTipoInterprete("Feminino");
                    break;
                case 3:
                    dto.setTipoInterprete("Grupo");
                    break;
                case 4:
                    dto.setTipoInterprete("Instrumental");
                    break;
                case 5:
                    dto.setTipoInterprete("Jingle");
                    break;
            }



            dto.setTitulo(bean.getTitulo());
            dto.setUltimaExecucao(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucao()));
            dto.setUltimaExecucaoData(new SimpleDateFormat("dd/MM/yyyy").format(bean.getUltimaExecucaoData()));
            dto.setVelocidade("" + bean.getVelocidade());

            lista2.add(dto);
        }
        return lista2;
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        return clienteBeanList;
    }

    public List<AudiostoreCategoriaBean> categoriaBeanList() {
        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).findAll();
        return audiostoreCategoriaBeanList;
    }

    public List<AudiostoreGravadoraBean> gravadoraBeanList() {
        List<AudiostoreGravadoraBean> audiostoreCategoriaBeanList = repository.query(AudiostoreGravadoraBean.class).findAll();
        return audiostoreCategoriaBeanList;
    }

    public AudiostoreMusicaBean bean(Integer id) {
        return repository.find(AudiostoreMusicaBean.class, id);
    }

    public void salvar(AudiostoreMusicaBean bean, String tempoTotalString) {
        try {
            if (bean.getCrossover()) {
                if (null == bean.getDataVencimentoCrossover()) {
                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Se estiver usando crossover, é obrigatório informar a data de vencimento!")).recursive().serialize();
                }
            } else {
                bean.setDataVencimentoCrossover(new Date());
            }

            Date tempoTotal = new SimpleDateFormat("HH:mm:ss").parse(tempoTotalString);
            bean.setTempoTotal(tempoTotal);

            bean.setSemSom(Boolean.FALSE);
            bean.setMsg("");
            bean.setUltimaExecucaoData(bean.getUltimaExecucao());
            bean.setQtde(bean.getQtdePlayer());
            bean.setSuperCrossover(Boolean.FALSE);
            bean.setRandom(new Random().nextInt());

            if (!(null != bean.getAfinidade1() && !bean.getAfinidade1().isEmpty())) {
                bean.setAfinidade1("");
            }

            if (!(null != bean.getAfinidade2() && !bean.getAfinidade2().isEmpty())) {
                bean.setAfinidade2("");
            }

            if (!(null != bean.getAfinidade3() && !bean.getAfinidade3().isEmpty())) {
                bean.setAfinidade3("");
            }

            if (!(null != bean.getAfinidade4() && !bean.getAfinidade4().isEmpty())) {
                bean.setAfinidade4("");
            }

            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (bean != null && bean.getId() != null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }
            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", bean.getCliente().getIdcliente()).findOne();

            String origem = dados.getLocalOrigemMusica() + "\\" + bean.getArquivo();
            String destino = dados.getLocalDestinoMusica() + "\\" + bean.getArquivo();

            File dir = new File(destino);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File f = new File(destino);
            if (!f.exists()) {
                FileInputStream fis = new FileInputStream(new File(origem));
                FileOutputStream fos = new FileOutputStream(new File(destino));
                IOUtils.copy(fis, fos);
            }

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }

    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            AudiostoreMusicaBean bean = repository.marge((AudiostoreMusicaBean) repository.find(AudiostoreMusicaBean.class, id));
            repository.delete(bean);

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
                String arquivo = bean.getArquivo();
                if (arquivo.length() < 30) {
                    arquivo = StringUtils.leftPad(arquivo, 30, " ");
                } else {
                    if (arquivo.length() > 30) {
                        arquivo = arquivo.substring(0, 30);
                    }
                }

                String interprete = bean.getInterprete();
                if (interprete.length() < 30) {
                    interprete = StringUtils.leftPad(interprete, 30, " ");
                } else {
                    if (interprete.length() > 30) {
                        interprete = interprete.substring(0, 30);
                    }
                }

                String titulo = bean.getTitulo();
                if (titulo.length() < 30) {
                    titulo = StringUtils.leftPad(titulo, 30, " ");
                } else {
                    if (titulo.length() > 30) {
                        titulo = titulo.substring(0, 30);
                    }
                }

                String afinidade1 = bean.getAfinidade1();
                if (afinidade1.length() < 30) {
                    afinidade1 = StringUtils.leftPad(afinidade1, 30, " ");
                } else {
                    if (afinidade1.length() > 30) {
                        afinidade1 = afinidade1.substring(0, 30);
                    }
                }

                String afinidade2 = bean.getAfinidade2();
                if (afinidade2.length() < 30) {
                    afinidade2 = StringUtils.leftPad(afinidade2, 30, " ");
                } else {
                    if (afinidade2.length() > 30) {
                        afinidade2 = afinidade2.substring(0, 30);
                    }
                }

                String afinidade3 = bean.getAfinidade3();
                if (afinidade3.length() < 30) {
                    afinidade3 = StringUtils.leftPad(afinidade3, 30, " ");
                } else {
                    if (afinidade3.length() > 30) {
                        afinidade3 = afinidade3.substring(0, 30);
                    }
                }

                String afinidade4 = bean.getAfinidade4();
                if (afinidade4.length() < 30) {
                    afinidade4 = StringUtils.leftPad(afinidade4, 30, " ");
                } else {
                    if (afinidade4.length() > 30) {
                        afinidade4 = afinidade4.substring(0, 30);
                    }
                }

                String msg = bean.getMsg();
                if (msg.length() < 40) {
                    msg = StringUtils.leftPad(msg, 40, " ");
                } else {
                    if (msg.length() > 40) {
                        msg = msg.substring(0, 40);
                    }
                }

                String conteudo = "";
                conteudo += "";
                conteudo += arquivo;
                conteudo += interprete;
                conteudo += bean.getTipoInterprete();
                conteudo += titulo;
                conteudo += bean.getCut() ? "nao" : "sim";
                conteudo += (null != bean.getCategoria1() ? StringUtils.leftPad(bean.getCategoria1().getCodigo().toString(), 3, " ") : "   ");
                conteudo += (null != bean.getCategoria2() ? StringUtils.leftPad(bean.getCategoria2().getCodigo().toString(), 3, " ") : "   ");
                conteudo += (null != bean.getCategoria3() ? StringUtils.leftPad(bean.getCategoria3().getCodigo().toString(), 3, " ") : "   ");
                conteudo += bean.getCrossover() ? "nao" : "sim";
                conteudo += StringUtils.leftPad(bean.getDiasExecucao1().toString(), 4, " ");
                conteudo += StringUtils.leftPad(bean.getDiasExecucao2().toString(), 4, " ");
                conteudo += afinidade1;
                conteudo += afinidade2;
                conteudo += afinidade3;
                conteudo += afinidade4;
                conteudo += StringUtils.leftPad(bean.getGravadora().getId().toString(), 3, " ");
                conteudo += bean.getAnoGravacao();
                conteudo += bean.getVelocidade();
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getData());
                conteudo += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucao());
                conteudo += new SimpleDateFormat("HH:mm:ss").format(bean.getTempoTotal());
                conteudo += StringUtils.leftPad(bean.getQtdePlayer().toString(), 3, " ");
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimento());
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimentoCrossover());
                conteudo += StringUtils.leftPad(bean.getFrameInicio().toString(), 8, " ");
                conteudo += StringUtils.leftPad(bean.getFrameFinal().toString(), 8, " ");
                conteudo += msg;
                conteudo += bean.getSemSom() ? 1 : 0;

                inputStreamDownload = new InputStreamDownload(new ByteArrayInputStream(conteudo.getBytes()), "application/exp", bean.getTitulo() + "+.exp");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStreamDownload;
    }

    public void upload(Integer id) {
        try {
            ConfigAppBean config = repository.find(ConfigAppBean.class, 1);
            AudiostoreMusicaBean bean = bean(id);
            if (bean != null) {

                String arquivo = bean.getArquivo();
                if (arquivo.length() < 30) {
                    arquivo = StringUtils.leftPad(arquivo, 30, " ");
                } else {
                    if (arquivo.length() > 30) {
                        arquivo = arquivo.substring(0, 30);
                    }
                }

                String interprete = bean.getInterprete();
                if (interprete.length() < 30) {
                    interprete = StringUtils.leftPad(interprete, 30, " ");
                } else {
                    if (interprete.length() > 30) {
                        interprete = interprete.substring(0, 30);
                    }
                }

                String titulo = bean.getTitulo();
                if (titulo.length() < 30) {
                    titulo = StringUtils.leftPad(titulo, 30, " ");
                } else {
                    if (titulo.length() > 30) {
                        titulo = titulo.substring(0, 30);
                    }
                }

                String afinidade1 = bean.getAfinidade1();
                if (afinidade1.length() < 30) {
                    afinidade1 = StringUtils.leftPad(afinidade1, 30, " ");
                } else {
                    if (afinidade1.length() > 30) {
                        afinidade1 = afinidade1.substring(0, 30);
                    }
                }

                String afinidade2 = bean.getAfinidade2();
                if (afinidade2.length() < 30) {
                    afinidade2 = StringUtils.leftPad(afinidade2, 30, " ");
                } else {
                    if (afinidade2.length() > 30) {
                        afinidade2 = afinidade2.substring(0, 30);
                    }
                }

                String afinidade3 = bean.getAfinidade3();
                if (afinidade3.length() < 30) {
                    afinidade3 = StringUtils.leftPad(afinidade3, 30, " ");
                } else {
                    if (afinidade3.length() > 30) {
                        afinidade3 = afinidade3.substring(0, 30);
                    }
                }

                String afinidade4 = bean.getAfinidade4();
                if (afinidade4.length() < 30) {
                    afinidade4 = StringUtils.leftPad(afinidade4, 30, " ");
                } else {
                    if (afinidade4.length() > 30) {
                        afinidade4 = afinidade4.substring(0, 30);
                    }
                }

                String msg = bean.getMsg();
                if (msg.length() < 40) {
                    msg = StringUtils.leftPad(msg, 40, " ");
                } else {
                    if (msg.length() > 40) {
                        msg = msg.substring(0, 40);
                    }
                }

                String conteudo = "";
                conteudo += "";
                conteudo += arquivo;
                conteudo += interprete;
                conteudo += bean.getTipoInterprete();
                conteudo += titulo;
                conteudo += bean.getCut() ? "nao" : "sim";
                conteudo += (null != bean.getCategoria1() ? StringUtils.leftPad(bean.getCategoria1().getCodigo().toString(), 3, " ") : "   ");
                conteudo += (null != bean.getCategoria2() ? StringUtils.leftPad(bean.getCategoria2().getCodigo().toString(), 3, " ") : "   ");
                conteudo += (null != bean.getCategoria3() ? StringUtils.leftPad(bean.getCategoria3().getCodigo().toString(), 3, " ") : "   ");
                conteudo += bean.getCrossover() ? "nao" : "sim";
                conteudo += StringUtils.leftPad(bean.getDiasExecucao1().toString(), 4, " ");
                conteudo += StringUtils.leftPad(bean.getDiasExecucao2().toString(), 4, " ");
                conteudo += afinidade1;
                conteudo += afinidade2;
                conteudo += afinidade3;
                conteudo += afinidade4;
                conteudo += StringUtils.leftPad(bean.getGravadora().getId().toString(), 3, " ");
                conteudo += bean.getAnoGravacao();
                conteudo += bean.getVelocidade();
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getData());
                conteudo += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucao());
                conteudo += new SimpleDateFormat("HH:mm:ss").format(bean.getTempoTotal());
                conteudo += StringUtils.leftPad(bean.getQtdePlayer().toString(), 3, " ");
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimento());
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimentoCrossover());
                conteudo += StringUtils.leftPad(bean.getFrameInicio().toString(), 8, " ");
                conteudo += StringUtils.leftPad(bean.getFrameFinal().toString(), 8, " ");
                conteudo += msg;
                conteudo += bean.getSemSom() ? 1 : 0;

                File dir = new File(config.getDataPath() + "\\musica-exp\\");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                InputStream is = new ByteArrayInputStream(conteudo.getBytes());
                FileOutputStream fos = new FileOutputStream(new File(config.getDataPath() + "\\musica-exp\\" + StringUtils.leftPad(bean.getId().toString(), 11, "0") + ".exp"));

                org.apache.commons.io.IOUtils.copy(is, fos);
                result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "")).recursive().serialize();
            }
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
    public void carregarInforWizard(Integer id) throws MalformedURLException, SmbException, IOException {
        List<AudiostoreCategoriaBean> categoriaLista = repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", id).findAll();

        if (null == categoriaLista || categoriaLista.isEmpty()) {
            CadastroMusicaDTO dto = new CadastroMusicaDTO();
            dto.setPossuiCategoria(false);

            result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
        } else {
            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", id).findOne();

            if (null == dados) {
                CadastroMusicaDTO dto = new CadastroMusicaDTO();
                dto.setPossuiCategoria(true);
                dto.setExisteDiretorio(false);
                result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
            } else {
                if (null == dados.getLocalOrigemMusica() || dados.getLocalOrigemMusica().isEmpty()) {
                    CadastroMusicaDTO dto = new CadastroMusicaDTO();
                    dto.setPossuiCategoria(true);
                    dto.setExisteDiretorio(false);
                    result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
                } else {

                    String user = "admin";
                    String pass = "q1a2s3";

                    String sharedFolder = "shared";
                    String path = "smb:" + dados.getLocalOrigemMusica();

                    if (!path.endsWith("/")) {
                        path += "/";
                    }
                    NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", user, pass);
                    SmbFile smbDir = new SmbFile(path, auth);
                    if (!smbDir.exists()) {
                        CadastroMusicaDTO dto = new CadastroMusicaDTO();
                        dto.setPossuiCategoria(true);
                        dto.setExisteDiretorio(false);
                        result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
                    } else {
                        if (null == smbDir.listFiles() || smbDir.listFiles().length == 0) {
                            CadastroMusicaDTO dto = new CadastroMusicaDTO();
                            dto.setPossuiCategoria(true);
                            dto.setExisteDiretorio(true);
                            dto.setExisteArquivo(false);
                            result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
                        } else {
                            CadastroMusicaDTO dto = new CadastroMusicaDTO();
                            dto.setPossuiCategoria(true);
                            dto.setExisteDiretorio(true);
                            dto.setExisteArquivo(true);

                            for (AudiostoreCategoriaBean item : categoriaLista) {
                                AudiostoreCategoriaDTO dtoItem = new AudiostoreCategoriaDTO();
                                dtoItem.setCategoria(item.getCategoria());
                                dtoItem.setCodigo(item.getCodigo());
                                dto.getCategoriaList().add(dtoItem);
                            }
                            HashMap<String, HashMap<String, InputStream>> hashMap = new HashMap<String, HashMap<String, InputStream>>();

                            List<Mdto> mdtoList = new ArrayList<Mdto>();

                            for (SmbFile item : smbDir.listFiles()) {
                                // mdtoList.add( new Mdto(item.getName(), item.getPath(), item.getInputStream()));
                                MusicaDTO mdt = new MusicaDTO();
                                mdt.setCaminhoCaminho(item.getPath());
                                mdt.setTitulo(item.getName());
                                mdt.setNomeArquivo(item.getName());
                                mdt.setCaminhoFull(path+"/"+item.getName());
                                dto.getMusicaList().add(mdt);
                            }

//                            for (Mdto mdto : mdtoList) {
//                                dto.getMusicaList().add(Utilities.getMetadataByIS(mdto.getIs(), mdto.getNome(), mdto.getPath()));
//                            }

                            result.use(Results.json()).withoutRoot().from(dto).recursive().serialize();
                        }


                    }
                }
            }
        }
    }

    public InputStreamDownload loadMusica(Integer id, String nome) throws MalformedURLException, SmbException, IOException {
        DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", id).findOne();
        
        String user = "admin";
        String pass = "q1a2s3";

        String sharedFolder = "shared";
        String path = "smb:"+dados.getLocalOrigemMusica()+"/"+nome;

        SmbFile smbFile = new SmbFile(path, Utilities.getAuthSmbDefault());
        
        return new InputStreamDownload(smbFile.getInputStream(), "audio/mpeg", smbFile.getName());
    }
}