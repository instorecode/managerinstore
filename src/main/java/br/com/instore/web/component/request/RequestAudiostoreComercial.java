package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreComercialShBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.property.AudiostoreComercialSh;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.controller.AudiostoreComercialController;
import br.com.instore.web.dto.CadastroMusicaDTO;
import br.com.instore.web.dto.AudiostoreComercialDTO;
import br.com.instore.web.dto.AudiostoreComercialDTO;
import br.com.instore.web.dto.AudiostoreComercialJSON;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
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
    private Validator validator;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestAudiostoreComercial() {
    }

    public RequestAudiostoreComercial(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public void beanList(Integer page, Integer rows, Integer id, String titulo, String arquivo) {
        AudiostoreComercialJSON json = new AudiostoreComercialJSON();

        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<AudiostoreComercialBean> lista = new ArrayList<AudiostoreComercialBean>();

        Query q1 = repository.query(AudiostoreComercialBean.class);
        Query q2 = repository.query(AudiostoreComercialBean.class);

        if (null != id && id > 0) {
            q1.eq("id", id);
            q2.eq("id", id);
            json.setId(id.toString());
        }

        if (null != titulo && !titulo.isEmpty()) {
            q1.ilikeAnyWhere("titulo", titulo);
            q2.ilikeAnyWhere("titulo", titulo);
            json.setTitulo(titulo);
        }

        if (null != arquivo && !arquivo.isEmpty()) {
            q1.ilikeAnyWhere("arquivo", arquivo);
            q2.ilikeAnyWhere("arquivo", arquivo);
            json.setArquivo(arquivo);
        }


        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();


        json.setPage(page);
        json.setSize(size);

        List<AudiostoreComercialDTO> rowsList = new ArrayList<AudiostoreComercialDTO>();
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
                sh += connector + item.getHorario() + " : " + item.getSemana() + "<br />";
                connector = ",";
            }
            dto.setSemanaHora(sh);

            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public List<ClienteBean> clienteBeanList() {
        String script = "select \n"
                + "    cliente.idcliente , '' as param \n"
                + "from\n"
                + "    cliente\n"
                + "inner join dados_cliente using(idcliente)\n"
                + "where local_destino_spot is not null and parente = 0 and instore = 0 and matriz = 1";

        final List<Integer> idents = new ArrayList<Integer>();
        repository.query(script).executeSQL(new Each() {
            Integer idcliente;
            String param;

            @Override
            public void each() {
                idents.add(idcliente);
            }
        });

        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).in("idcliente", idents.toArray(new Integer[idents.size()])).findAll();
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
        DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", bean.getCliente().getIdcliente()).findOne();

        try {
            Date tempoTotal = new SimpleDateFormat("HH:mm").parse(tempoTotalString);
            bean.setTempoTotal(tempoTotal);

            if (null == bean.getQtde()) {
                bean.setQtde(0);
            }

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
                if (null != item && null != item.getHorario() && null != item.getSemana()) {
                    item.setComercial(bean);
                    repository.save(item);
                }
            }

            try {
                if (null != dados) {
                    SmbFile dir = new SmbFile(dados.getLocalDestinoSpot(), Utilities.getAuthSmbDefault());

                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    SmbFile file = new SmbFile(dados.getLocalDestinoSpot() + arquivo.getFileName() + "/", Utilities.getAuthSmbDefault());
                    SmbFileOutputStream smbFos = new SmbFileOutputStream(file);
                    byte[] bytes = IOUtils.toByteArray(arquivo.getFile());

                    smbFos.write(bytes);
                    smbFos.flush();
                    smbFos.close();
                }
            } catch (SmbException e) {
                e.printStackTrace();
            }

            repository.finalize();
            result.redirectTo(AudiostoreComercialController.class).listar(null, null, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
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
            result.redirectTo(AudiostoreComercialController.class).listar(null, null, null, null, null, null, null, null, null);
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

            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", bean.getCliente().getIdcliente()).findOne();

            try {
                if (null != dados) {
                    SmbFile file = new SmbFile(dados.getLocalDestinoSpot() + bean.getArquivo() + "/", Utilities.getAuthSmbDefault());
                    file.delete();
                }
            } catch (SmbException e) {
                e.printStackTrace();
            }

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

    public void upload(Integer[] id_list) {
        try {
            List<AudiostoreComercialBean> list = repository.query(AudiostoreComercialBean.class).in("id", id_list).findAll();
            String conteudo = "";
            String breakLine = "";

            for (AudiostoreComercialBean bean : list) {
                if (bean != null) {
                    conteudo += breakLine;
                    breakLine = "\r\n";

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
                    conteudo += StringUtils.leftPad(bean.getAudiostoreCategoria().getCodInterno().toString(), 3, "0");
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
                        if (bean.getDiasAlternados()) {
                            if ("segunda".equals(item.getSemana())) {
                                semana = "N      ";
                            }

                            if ("terca".equals(item.getSemana())) {
                                semana = " N     ";
                            }

                            if ("quarta".equals(item.getSemana())) {
                                semana = "  N    ";
                            }

                            if ("quinta".equals(item.getSemana())) {
                                semana = "   N   ";
                            }

                            if ("sexta".equals(item.getSemana())) {
                                semana = "    N  ";
                            }

                            if ("sabado".equals(item.getSemana())) {
                                semana = "     N ";
                            }

                            if ("domingo".equals(item.getSemana())) {
                                semana = "      N";
                            }
                        } else {
                            if ("segunda".equals(item.getSemana())) {
                                semana = "X      ";
                            }

                            if ("terca".equals(item.getSemana())) {
                                semana = " X     ";
                            }

                            if ("quarta".equals(item.getSemana())) {
                                semana = "  X    ";
                            }

                            if ("quinta".equals(item.getSemana())) {
                                semana = "   X   ";
                            }

                            if ("sexta".equals(item.getSemana())) {
                                semana = "    X  ";
                            }

                            if ("sabado".equals(item.getSemana())) {
                                semana = "     X ";
                            }

                            if ("domingo".equals(item.getSemana())) {
                                semana = "      X";
                            }
                        }


                        conteudo += hora;
                        conteudo += semana;

                        index++;
                    }

                    if (index < 24) {
                        for (int i = index; i <= 24; i++) {
                            conteudo += "00:00";
                            conteudo += "       ";
                        }
                    }
                    conteudo += "       ";
                    conteudo += bean.getDiasAlternados() ? "sim" : "nao";
                    conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getData());
                    conteudo += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bean.getUltimaExecucao());
                    conteudo += new SimpleDateFormat("HH:mm:ss").format(bean.getTempoTotal());
                    conteudo += StringUtils.leftPad(bean.getQtdePlayer().toString(), 3, "0");
                    conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getDataVencimento());

                    if ("".equals(dep1.trim())) {
                        dep1 = "                       Nenhuma";
                    }
                    
                    if ("".equals(dep2.trim())) {
                        dep2 = "                       Nenhuma";
                    }
                    
                    if ("".equals(dep3.trim())) {
                        dep3 = "                       Nenhuma";
                    }

                    conteudo += dep1;
                    conteudo += dep2;
                    conteudo += dep3;
                    conteudo += StringUtils.leftPad(bean.getFrameInicio().toString(), 8, "0");
                    conteudo += StringUtils.leftPad(bean.getFrameInicio().toString(), 8, "0");
                    conteudo += msg;
                    conteudo += bean.getSemSom() ? "sim" : "nao";

                }
            }
            
            conteudo = Utilities.removeLetrasEspeciais(conteudo);
            
            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", list.get(0).getCliente().getIdcliente()).findOne();
            String destino = dados.getLocalDestinoExp();
            SmbFile smb = new SmbFile(destino, Utilities.getAuthSmbDefault());
            SmbFile smb2 = new SmbFile(destino + "comercial.exp", Utilities.getAuthSmbDefault());

            if (!smb.exists()) {
                smb.mkdirs();
            }

            SmbFileOutputStream sfous = new SmbFileOutputStream(smb2);
            sfous.write(conteudo.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "")).recursive().serialize();
        }
    }

    public void validador(String titulo, String periodoInicial, String periodoFinal, String nomeArquivo, Integer idcliente) {
        Date dataPeriodoInicial = null;
        Date dataPeriodoFinal = null;
        try {
            dataPeriodoInicial = new SimpleDateFormat("dd/MM/yyyy").parse(periodoInicial);
            dataPeriodoFinal = new SimpleDateFormat("dd/MM/yyyy").parse(periodoFinal);
        } catch (ParseException ex) {
            Logger.getLogger(RequestAudiostoreComercial.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean bool = true;
        String mensagem = "";

        if (null != dataPeriodoInicial && null != dataPeriodoFinal) {
            if (dataPeriodoInicial.after(dataPeriodoFinal)) {
                mensagem = "O periodo inicial deve ser menor que o periodo final!";
                bool = false;
            }
        }

        if (repository.query(AudiostoreComercialBean.class).eq("cliente.idcliente", idcliente).eq("titulo", titulo).count() > 0) {
            mensagem = "Já existe um comercial com este título!";
            bool = false;
        }

        DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", idcliente).findOne();
        if (null != dados) {

            if (null == dados.getLocalDestinoSpot() || dados.getLocalDestinoSpot().trim().isEmpty()) {
                mensagem = "O cliente selecionado não possui um local de destino para os arquivos dos comerciais!";
                bool = false;
            }

            try {
                SmbFile fileValid = new SmbFile(dados.getLocalDestinoSpot() + nomeArquivo + "/", Utilities.getAuthSmbDefault());
                if (fileValid.exists()) {
                    mensagem = "Já existe um comercial com este arquivo!";
                    bool = false;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                mensagem = "Não foi possivel verificar se o arquivo já existe, erro: " + e.getMessage();
                bool = false;
            } catch (SmbException e) {
                e.printStackTrace();
                mensagem = "Não foi possivel verificar se o arquivo já existe, erro: " + e.getMessage();
                mensagem = e.getMessage();
                bool = false;
            }
        }
        result.use(Results.json()).withoutRoot().from(new AjaxResult(bool, mensagem)).recursive().serialize();
    }

    public void dep(Integer idcliente, Integer id) {
        result.use(Results.json()).withoutRoot().from(repository.query(AudiostoreComercialBean.class).eq("cliente.idcliente", idcliente).not().eq("id", id).findAll()).recursive().serialize();
    }

    public void validarComercial(Integer[] id_list) {
        List<AudiostoreComercialBean> list = repository.query(AudiostoreComercialBean.class).in("id", id_list).findAll();

        boolean ajaxResultBool = true;
        String ajaxResultStr = "";
        // verifica se todos são do mesmo cliente
        Integer idcliente = list.get(0).getCliente().getIdcliente();
        for (AudiostoreComercialBean bean : list) {
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
    
    public void validarCateg(Integer idcliente) {        
        result.use(Results.json()).withoutRoot().from(repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", idcliente).eq("tipo", new Short("2")).findAll()).recursive().serialize();
    }
}