package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreComercialShBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.property.AudiostoreComercialSh;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.controller.AudiostoreComercialController;
import br.com.instore.web.dto.CadastroMusicaDTO;
import br.com.instore.web.dto.AudiostoreComercialDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;

@RequestScoped
public class RequestAudiostoreComercial implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestAudiostoreComercial() {
    }

    public RequestAudiostoreComercial(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public List<AudiostoreComercialDTO> beanList() {
        List<AudiostoreComercialBean> lista = new ArrayList<AudiostoreComercialBean>();
        List<AudiostoreComercialDTO> lista2 = new ArrayList<AudiostoreComercialDTO>();
        lista = repository.query(AudiostoreComercialBean.class).findAll();
        for (AudiostoreComercialBean bean : lista) {
            AudiostoreComercialDTO dto = new AudiostoreComercialDTO();

            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setArquivo(bean.getArquivo());
            if (null != bean.getAudiostoreCategoria()) {
                dto.setCategoriaNome(bean.getAudiostoreCategoria().getCategoria());
            }
            dto.setData(new SimpleDateFormat("dd/MM/yyyy").format(bean.getData()));
            dto.setDataVencimento(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataVencimento()));
            dto.setDiasSemana("" + bean.getDiasSemana());
            dto.setDiasAlternados(bean.getDiasAlternados() ? "Sim" : "Não");
            dto.setFrameFinal(bean.getFrameFinal().toString());
            dto.setFrameInicial(bean.getFrameInicio().toString());
            dto.setMsg(bean.getMsg());
            dto.setQtd(bean.getQtde().toString());
            dto.setRandom(bean.getRandom().toString());
            dto.setSemSom(bean.getSemSom() ? "Sim" : "Não");
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
            dto.setDependencia1(bean.getDependencia1());
            dto.setDependencia2(bean.getDependencia2());
            dto.setDependencia3(bean.getDependencia3());
            dto.setPeriodoInicial(new SimpleDateFormat("dd/MM/yyyy").format(bean.getPeriodoInicial()));
            dto.setPeriodoFinal(new SimpleDateFormat("dd/MM/yyyy").format(bean.getPeriodoFinal()));

            String sh = "";
            String connector = "";
            for (AudiostoreComercialShBean item : shs(bean.getId())) {
                sh += connector + item.getHorario() + " : " + item.getSemana();
                connector = ",";
            }
            dto.setSemanaHora(sh);
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

    public List<AudiostoreComercialShBean> audiostoreComercialShBeanList() {
        List<AudiostoreComercialShBean> audiostoreComercialShBeanList = repository.query(AudiostoreComercialShBean.class).findAll();
        return audiostoreComercialShBeanList;
    }

    public AudiostoreComercialBean bean(Integer id) {
        return repository.find(AudiostoreComercialBean.class, id);
    }

    public List<AudiostoreComercialShBean> shs(Integer id) {
        return repository.query(AudiostoreComercialShBean.class).eq(AudiostoreComercialSh.COMERCIAL_ID, id).findAll();
    }

    public void salvar(AudiostoreComercialBean bean, String tempoTotalString, AudiostoreComercialShBean[] sh, UploadedFile arquivo) {
        try {
            Date tempoTotal = new SimpleDateFormat("HH:mm:ss").parse(tempoTotalString);
            bean.setTempoTotal(tempoTotal);
            bean.setQtdePlayer(bean.getQtde());
            bean.setData(new Date());
            bean.setMsg("");
            bean.setSemSom(Boolean.FALSE);
            bean.setRandom(10);

            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            bean.setFrameInicio(0);
            bean.setFrameFinal(0);
            bean.setArquivo(arquivo.getFileName());
            
            if (bean != null && bean.getId() != null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }

            String query = "delete from audiostore_comercial_sh where comercial = " + bean.getId();
            repository.query(query).executeSQLCommand();

            for (AudiostoreComercialShBean item : sh) {
                if (item != null) {
                    item.setComercial(bean);
                    repository.save(item);
                }
            }

            try {
                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", bean.getCliente().getIdcliente()).findOne();

                String destino = "smb://"+dados.getLocalDestinoSpot()+ "/";
                
                SmbFile dir = new SmbFile(destino, Utilities.getAuthSmbDefault());

                if (!dir.exists()) {
                    dir.mkdirs();
                }
                
                SmbFile file = new SmbFile(destino + arquivo.getFileName() + "/" , Utilities.getAuthSmbDefault());
                SmbFileOutputStream smbFos = new SmbFileOutputStream(file);
                byte[] bytes = IOUtils.toByteArray(arquivo.getFile());
                
                smbFos.write(bytes);
                smbFos.flush();
                smbFos.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
           
            repository.finalize();
            result.redirectTo(AudiostoreComercialController.class).listar(false);
        } catch (Exception e) {
            e.printStackTrace();
            repository.finalize();
            result.redirectTo(AudiostoreComercialController.class).cadastrar();
        }
    }
    
    public void salvar2(AudiostoreComercialBean bean, String tempoTotalString, AudiostoreComercialShBean[] sh) {
        try {
            Date tempoTotal = new SimpleDateFormat("HH:mm:ss").parse(tempoTotalString);
            bean.setTempoTotal(tempoTotal);
            bean.setQtdePlayer(bean.getQtde());
            bean.setData(new Date());
            bean.setMsg("");
            bean.setSemSom(Boolean.FALSE);
            bean.setRandom(10);

            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            bean.setFrameInicio(0);
            bean.setFrameFinal(0);
            
            if (bean != null && bean.getId() != null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }

            String query = "delete from audiostore_comercial_sh where comercial = " + bean.getId();
            repository.query(query).executeSQLCommand();

            for (AudiostoreComercialShBean item : sh) {
                if (item != null) {
                    item.setComercial(bean);
                    repository.save(item);
                }
            }
           
            repository.finalize();
            result.redirectTo(AudiostoreComercialController.class).listar(false);
        } catch (Exception e) {
            e.printStackTrace();
            result.redirectTo(AudiostoreComercialController.class).cadastrar();
        }
    }

    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            String query = "delete from audiostore_comercial_sh where comercial = " + id;
            repository.query(query).executeSQLCommand();

            AudiostoreComercialBean bean = repository.marge((AudiostoreComercialBean) repository.find(AudiostoreComercialBean.class, id));
            repository.delete(bean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Comercial removido com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a comercial!")).recursive().serialize();
        }
    }

    public List<CadastroMusicaDTO> arquivoMusicaList(Integer id) {
        DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", id).findOne();

        File dir = new File(dados.getLocalOrigemSpot());
        List<CadastroMusicaDTO> lista = new ArrayList<CadastroMusicaDTO>();
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                if (file.getName().endsWith(".mp3")) {
//                    CadastroMusicaDTO dto = new CadastroMusicaDTO(file.getName().replaceAll("([.]{1})([.,a-z,A-Z,0-9,_,-]+)", ""), file.getName());
//                    lista.add(dto);
                }
            }
        }
        return lista;
    }

//    public InputStreamDownload download(Integer id) {
//        InputStreamDownload inputStreamDownload = null;
//        try {
//            AudiostoreGravadoraBean bean = bean(id);
//            if (bean != null) {
//
//                String conteudo = "";
//                String nome = bean.getNome();
//
//                if (nome.length() < 30) {
//                    nome = StringUtils.leftPad(nome, 30, " ");
//                } else {
//                    if (nome.length() > 30) {
//                        nome = nome.substring(0, 30);
//                    }
//                }
//
//                conteudo += StringUtils.leftPad(bean.getId().toString(), 5, " ");
//                conteudo += nome;
//
//                inputStreamDownload = new InputStreamDownload(new ByteArrayInputStream(conteudo.getBytes()), "application/exp", bean.getId()+ ".exp");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return inputStreamDownload;
//    }
    public void upload(Integer id) {
        try {
            ConfigAppBean config = repository.find(ConfigAppBean.class, 1);
            AudiostoreComercialBean bean = bean(id);
            if (bean != null) {

                String conteudo = "";

                String arquivo = bean.getArquivo();
                if (arquivo.length() < 30) {
                    arquivo = StringUtils.leftPad(arquivo, 30, " ");
                } else {
                    if (arquivo.length() > 30) {
                        arquivo = arquivo.substring(0, 30);
                    }
                }

                String cliente = bean.getAudiostoreCategoria().getCliente().getNome();
                if (cliente.length() < 30) {
                    cliente = StringUtils.leftPad(cliente, 30, " ");
                } else {
                    if (cliente.length() > 30) {
                        cliente = cliente.substring(0, 30);
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

                String dep1 = bean.getDependencia1();
                if (dep1.length() < 30) {
                    dep1 = StringUtils.leftPad(dep1, 30, " ");
                } else {
                    if (dep1.length() > 30) {
                        dep1 = dep1.substring(0, 30);
                    }
                }

                String dep2 = bean.getDependencia2();
                if (dep2.length() < 30) {
                    dep2 = StringUtils.leftPad(dep2, 30, " ");
                } else {
                    if (dep2.length() > 30) {
                        dep2 = dep2.substring(0, 30);
                    }
                }

                String dep3 = bean.getDependencia3();
                if (dep3.length() < 30) {
                    dep3 = StringUtils.leftPad(dep3, 30, " ");
                } else {
                    if (dep3.length() > 30) {
                        dep3 = dep3.substring(0, 30);
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


                conteudo += arquivo;
                conteudo += cliente;
                conteudo += titulo;
                conteudo += bean.getTipoInterprete();
                conteudo += StringUtils.leftPad(bean.getAudiostoreCategoria().getCodigo().toString(), 3, "0");
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getPeriodoInicial());
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getPeriodoFinal());
                int index = 1;
                for (AudiostoreComercialShBean item : shs(bean.getId())) {
                    String semana = item.getSemana();
                    String hora = new SimpleDateFormat("HH:mm").format(item.getHorario());
                    if (semana.length() < 7) {
                        semana = StringUtils.leftPad(semana, 7, " ");
                    } else {
                        if (semana.length() > 7) {
                            semana = semana.substring(0, 7);
                        }
                    }

                    conteudo += semana;
                    conteudo += hora;
                    index++;
                }

                if (index < 24) {
                    for (int i = index; i <= 24; i++) {
                        conteudo += "       ";
                        conteudo += "     ";
                    }
                }
                conteudo += bean.getDiasAlternados() ? "sim" : "nao";
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getData());
                conteudo += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucao());
                conteudo += new SimpleDateFormat("HH:mm:ss").format(bean.getTempoTotal());
                conteudo += StringUtils.leftPad(bean.getQtdePlayer().toString(), 3, "0");
                conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimento());
                conteudo += dep1;
                conteudo += dep2;
                conteudo += dep3;
                conteudo += StringUtils.leftPad(bean.getFrameInicio().toString(), 8, "0");
                conteudo += StringUtils.leftPad(bean.getFrameInicio().toString(), 8, "0");
                conteudo += msg;
                conteudo += bean.getSemSom() ? "sim" : "nao";

                File dir = new File(config.getDataPath() + "\\comercial-exp\\");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                InputStream is = new ByteArrayInputStream(conteudo.getBytes());
                FileOutputStream fos = new FileOutputStream(new File(config.getDataPath() + "\\comercial-exp\\" + StringUtils.leftPad(bean.getId().toString(), 3, "0") + ".exp"));

                org.apache.commons.io.IOUtils.copy(is, fos);
                result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "")).recursive().serialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "")).recursive().serialize();
        }
    }
}