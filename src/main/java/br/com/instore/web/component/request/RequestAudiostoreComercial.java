package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreComercialShBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.property.AudiostoreComercialSh;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.controller.AudiostoreComercialController;
import br.com.instore.web.dto.CadastroMusicaDTO;
import br.com.instore.web.dto.AudiostoreComercialDTO;
import br.com.instore.web.dto.AudiostoreComercialJSON;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.File;
import java.math.BigDecimal;
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
import javax.servlet.http.HttpServletRequest;
import jcifs.smb.SmbException;
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
    private Validator validator;
    @Inject
    private SessionUsuario sessionUsuario;
    @Inject
    private HttpServletRequest httpServletRequest;

    public RequestAudiostoreComercial() {
    }

    public RequestAudiostoreComercial(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public AudiostoreComercialJSON beanList(Integer page, Integer rows, Integer id, String titulo, String arquivo, Integer codigo, Integer idcliente) {
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

        if (null != idcliente && idcliente > 0) {
            q1.eq("cliente.idcliente", idcliente);
            q2.eq("cliente.idcliente", idcliente);
            json.setIdcliente(idcliente);
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

        if (null != codigo && codigo > 0) {
            q1.eq("audiostoreCategoria.codigo", codigo);
            q2.eq("audiostoreCategoria.codigo", codigo);
            json.setCodigo(codigo);
        }


        json.setCount(q1.count().intValue());
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
            dto.setCategoriaNome(bean.getAudiostoreCategoria().getCategoria());
            dto.setClienteNome(bean.getCliente().getNome());

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
        return json;
    }

    public AudiostoreComercialDTO bean2(Integer pk) {
        AudiostoreComercialBean bean = repository.find(AudiostoreComercialBean.class, pk);
        AudiostoreComercialDTO dto = new AudiostoreComercialDTO();
        if (null != bean) {


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
            dto.setClienteNome(bean.getCliente().getNome());

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
        }
        return dto;
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
        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).eq("tipo", new Short("2")).findAll();
        return audiostoreCategoriaBeanList;
    }

    public List<AudiostoreCategoriaBean> categoriaBeanListByCliente(Integer idcliente) {
        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).eq("tipo", new Short("2")).eq("cliente.idcliente", idcliente).findAll();
        return audiostoreCategoriaBeanList;
    }

    public List<ClienteBean> clienteBeanList2() {
        List<ClienteBean> beanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        return beanList;
    }

    public List<AudiostoreCategoriaBean> categoriaBeanList(Integer id) {
        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", id).findAll();
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
        return repository.query(AudiostoreComercialShBean.class).eq(AudiostoreComercialSh.COMERCIAL_ID, id).orderAsc("horario").findAll();
    }

    public void salvar(AudiostoreComercialBean bean, String tempoTotalString, AudiostoreComercialShBean[] sh, UploadedFile arquivo) {

        if (null != arquivo && arquivo.getFileName().length() > 28) {
            result.redirectTo(AudiostoreComercialController.class).cadastrar();
        }

        DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", bean.getCliente().getIdcliente()).findOne();
        bean.setInterromperMusicaTocada(Boolean.FALSE);
        try {
            Date tempoTotal = new SimpleDateFormat("HH:mm").parse(tempoTotalString);
            bean.setTempoTotal(tempoTotal);

            if (null == bean.getQtde()) {
                bean.setQtde(0);
            }

            bean.setQtdePlayer(bean.getQtde());
            bean.setData(new Date());

            if (null == bean.getMsg()) {
                bean.setMsg("");
            }

            if (null == bean.getSemSom()) {
                bean.setSemSom(Boolean.FALSE);
            }

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
            result.redirectTo(AudiostoreComercialController.class).listar(null, null, null, null, null, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            result.redirectTo(AudiostoreComercialController.class).cadastrar();
        }
    }

    public void salvar2(AudiostoreComercialBean bean, String tempoTotalString, AudiostoreComercialShBean[] sh) {
        try {
            if (null != bean && bean.getArquivo().length() > 28) {
                result.redirectTo(AudiostoreComercialController.class).cadastrar();
            }

            if (null == bean.getMsg()) {
                bean.setMsg("");
            }

            if (null == bean.getSemSom()) {
                bean.setSemSom(Boolean.FALSE);
            }

            String query = "UPDATE audiostore_comercial SET categoria = ? , arquivo = ? , titulo = ? , tipo_interprete = ? , periodo_inicial = ? , periodo_final = ? , tipo_horario = ? , dias_semana = ? , dias_alternados = ? , data = ? , ultima_execucao = ?  , tempo_total = ? , random = ? , qtde_player = ? , qtde = ? , data_vencimento = ? , dependencia1 = ? , dependencia2 = ? , dependencia3 = ? , frame_inicio = ? , frame_final = ? , msg = ? , sem_som = ? , cliente = ? , texto = ? where id = " + bean.getId();
            query = query.replaceFirst("\\?", bean.getAudiostoreCategoria().getCodigo().toString()); // categoria
            query = query.replaceFirst("\\?", "'" + bean.getArquivo() + "'"); // arquivo
            query = query.replaceFirst("\\?", "'" + bean.getTitulo() + "'"); // titulo
            query = query.replaceFirst("\\?", "" + bean.getTipoInterprete()); // tipo_interprete
            query = query.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(bean.getPeriodoInicial()) + "'"); // periodo_inicial
            query = query.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(bean.getPeriodoFinal()) + "'"); // periodo_final
            query = query.replaceFirst("\\?", "" + bean.getTipoHorario()); // tipo_horario
            query = query.replaceFirst("\\?", "" + bean.getDiasSemana()); // dias_semana
            query = query.replaceFirst("\\?", bean.getDiasAlternados().toString()); // dias_alternados
            query = query.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "'"); // data
            query = query.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bean.getUltimaExecucao()) + "'"); // ultima_execucao
            query = query.replaceFirst("\\?", "'" + tempoTotalString + "'"); // tempo_total
            query = query.replaceFirst("\\?", "0"); // random
            query = query.replaceFirst("\\?", bean.getQtde().toString()); // qtde_player
            query = query.replaceFirst("\\?", bean.getQtde().toString()); // qtde
            query = query.replaceFirst("\\?", "'" + new SimpleDateFormat("yyyy-MM-dd").format(bean.getDataVencimento()) + "'"); // data_vencimento
            query = query.replaceFirst("\\?", "'" + bean.getDependencia1() + "'"); // dependencia1
            query = query.replaceFirst("\\?", "'" + bean.getDependencia2() + "'"); // dependencia2
            query = query.replaceFirst("\\?", "'" + bean.getDependencia3() + "'"); // dependencia3
            query = query.replaceFirst("\\?", "'0'"); // frame_inicio
            query = query.replaceFirst("\\?", "'0'"); // frame_final
            query = query.replaceFirst("\\?", "'" + bean.getMsg() + "'"); // msg
            query = query.replaceFirst("\\?", "'" + (bean.getSemSom() ? 1 : 0) + "'"); // sem_som
            query = query.replaceFirst("\\?", "'" + bean.getCliente().getIdcliente().toString() + "'"); // cliente
            query = query.replaceFirst("\\?", "'" + bean.getTexto() + "'"); // texto
            System.out.println("-------------------------------------------------------");
            System.out.println(query);
            System.out.println("-------------------------------------------------------");
            repository.query(query).executeSQLCommand2();

            query = "DELETE FROM audiostore_comercial_sh WHERE comercial = " + bean.getId();
            repository.query(query).executeSQLCommand2();


            for (AudiostoreComercialShBean bbean : sh) {
                if (null != bbean) {
                    query = "INSERT INTO audiostore_comercial_sh VALUES(null, ?,?,?,?)";
                    query = query.replaceFirst("\\?", bean.getId().toString()); // comercial
                    query = query.replaceFirst("\\?", "'" + bbean.getSemana() + "'"); // semana
                    query = query.replaceFirst("\\?", "'" + new SimpleDateFormat("HH:mm:ss").format(bbean.getHorario()) + "'"); // horario
                    query = query.replaceFirst("\\?", bbean.getInterromperMusicaTocada() ? "1" : "0"); // interromper_musica_tocada
                    repository.query(query).executeSQLCommand2();
                }
            }


            repository.finalize();
            result.redirectTo(AudiostoreComercialController.class).listar(null, null, null, null, null, null, null, null, null, null, null, null);
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

    public void upload(Integer[] id_list, Boolean expArquivoAudio) {
        try {
            List<AudiostoreComercialBean> list = repository.query(AudiostoreComercialBean.class).in("id", id_list).findAll();
            String conteudo = "";
            String breakLine = "";
            System.out.println("TAMANHO DA LISTA " + id_list.length);
            for (AudiostoreComercialBean bean : list) {
                if (bean != null) {
                    String queryHoraSemana = "";
                    queryHoraSemana = "select ceil(count(*) / 24) as count , '' as param from (\n"
                            + "	select \n"
                            + "		date_format(horario, '%H:%i') as hora,\n"
                            + "		group_concat(semana separator '') as dia,\n"
                            + "		group_concat(distinct interromper_musica_tocada separator '') as n_ou_x\n"
                            + "	from\n"
                            + "		audiostore_comercial_sh\n"
                            + "	where comercial = " + bean.getId() + "\n"
                            + "	\n"
                            + "	group by hora\n"
                            + "	order by semana != 'segunda' , semana != 'terca' , semana != 'quarta' , semana != 'quinta' , semana != 'sexta' , semana != 'sabado' , semana != 'domingo'\n"
                            + ") as t ";

                    final List<BigDecimal> counts = new ArrayList<BigDecimal>();
                    repository.query(queryHoraSemana).executeSQL(new Each() {
                        public BigDecimal count;
                        public String param;

                        @Override
                        public void each() {
                            counts.add(count);
                        }
                    });

                    Integer multiplo_24 = 0;

                    if (!counts.isEmpty()) {
                        multiplo_24 = counts.get(0).intValue();
                    }

                    if (multiplo_24 < 1) {
                        multiplo_24 = 1;
                    }

                    Integer multiplo_24_max = multiplo_24;
                    multiplo_24 = 1;

                    queryHoraSemana = "select hora, concat(if(dia like '%segunda%' , if(n_ou_x = 0 , 'N','X') , ' '),\n"
                            + "					if(dia like '%terca%' , if(n_ou_x = 0 , 'N','X') , ' '),\n"
                            + "					if(dia like '%quarta%' , if(n_ou_x = 0 , 'N','X') , ' '),\n"
                            + "					if(dia like '%quinta%' , if(n_ou_x = 0 , 'N','X') , ' '),\n"
                            + "					if(dia like '%sexta%' , if(n_ou_x = 0 , 'N','X') , ' '),\n"
                            + "					if(dia like '%sabado%' , if(n_ou_x = 0 , 'N','X') , ' '),\n"
                            + "					if(dia like '%domingo%' , if(n_ou_x = 0 , 'N','X') , ' ')) as dia from (\n"
                            + "	select \n"
                            + "		date_format(horario, '%H:%i') as hora,\n"
                            + "		group_concat(semana separator '') as dia,\n"
                            + "		group_concat(distinct interromper_musica_tocada separator '') as n_ou_x\n"
                            + "	from\n"
                            + "		audiostore_comercial_sh\n"
                            + "	where comercial = " + bean.getId() + "\n"
                            + "	\n"
                            + "	group by hora\n"
                            + "	order by semana != 'segunda' , semana != 'terca' , semana != 'quarta' , semana != 'quinta' , semana != 'sexta' , semana != 'sabado' , semana != 'domingo'\n"
                            + ") as t";

                    final List<String> horasDiasList = new ArrayList<String>();

                    repository.query(queryHoraSemana).executeSQL(new Each() {
                        public String hora;
                        public String dia;

                        @Override
                        public void each() {
                            horasDiasList.add(hora + dia);
                        }
                    });

                    while (multiplo_24 <= multiplo_24_max) {
                        conteudo += breakLine;
                        breakLine = Utilities.quebrarLinhaComHexa();

                        String arquivo = bean.getArquivo();

                        String arquivoNome = arquivo.substring(0, arquivo.length() - 4);
                        String arquivoExtensao = arquivo.substring(arquivo.length() - 4, arquivo.length());

                        if (multiplo_24 > 1) {
                            arquivoNome = arquivoNome + "-" + (multiplo_24 - 1);
                        }

                        arquivoNome = arquivoNome + arquivoExtensao;
                        String smbArquivoNome = arquivoNome;
                        arquivo = arquivoNome;

                        if (arquivo.length() < 30) {
                            arquivo = StringUtils.leftPad(arquivo, 30, " ");
                        } else {
                            if (arquivo.length() > 30) {
                                arquivo = arquivo.substring(arquivo.length() - 30, 30);
                                smbArquivoNome = smbArquivoNome.substring(smbArquivoNome.length() - 30, 30);
                            }
                        }
                        arquivo = Utilities.formatarHexExp(arquivo);

                        String cliente = bean.getCliente().getNome();
                        if (cliente.length() < 30) {
                            cliente = StringUtils.leftPad(cliente, 30, " ");
                        } else {
                            if (cliente.length() > 30) {
                                cliente = cliente.substring(0, 30);
                            }
                        }
                        cliente = Utilities.formatarHexExp(cliente);

                        String titulo = bean.getTitulo();
                        if (titulo.length() < 30) {
                            titulo = StringUtils.leftPad(titulo, 30, " ");
                        } else {
                            if (titulo.length() > 30) {
                                titulo = titulo.substring(0, 30);
                            }
                        }
                        titulo = Utilities.formatarHexExp(titulo);

                        String dep1 = bean.getDependencia1();
                        if (dep1.length() < 30) {
                            dep1 = StringUtils.leftPad(dep1, 30, " ");
                        } else {
                            if (dep1.length() > 30) {
                                dep1 = dep1.substring(0, 30);
                            }
                        }
                        dep1 = Utilities.formatarHexExp(dep1);

                        String dep2 = bean.getDependencia2();
                        if (dep2.length() < 30) {
                            dep2 = StringUtils.leftPad(dep2, 30, " ");
                        } else {
                            if (dep2.length() > 30) {
                                dep2 = dep2.substring(0, 30);
                            }
                        }
                        dep2 = Utilities.formatarHexExp(dep2);

                        String dep3 = bean.getDependencia3();
                        if (dep3.length() < 30) {
                            dep3 = StringUtils.leftPad(dep3, 30, " ");
                        } else {
                            if (dep3.length() > 30) {
                                dep3 = dep3.substring(0, 30);
                            }
                        }
                        dep3 = Utilities.formatarHexExp(dep3);

                        String msg = bean.getMsg();
                        if (msg.length() < 40) {
                            msg = StringUtils.leftPad(msg, 40, " ");
                        } else {
                            if (msg.length() > 40) {
                                msg = msg.substring(0, 40);
                            }
                        }
                        msg = Utilities.formatarHexExp(msg);


                        conteudo += arquivo;
                        conteudo += cliente;
                        conteudo += titulo;
                        conteudo += bean.getTipoInterprete();
                        conteudo += StringUtils.leftPad(bean.getAudiostoreCategoria().getCodInterno().toString(), 3, "0");
                        conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getPeriodoInicial());
                        conteudo += new SimpleDateFormat("dd/MM/yy").format(bean.getPeriodoFinal());
                        int index = 1;

                        for (int i = ((multiplo_24 * 24) - 24); i < ((multiplo_24 * 24)); i++) {

                            System.out.println("tamanho da lista dentro do foreach" + horasDiasList.size());
                            System.out.println("posicao do index" + i);
                            System.out.println("eh true" + (i < horasDiasList.size()));

                            if (i == 0) {
                                try {
                                    conteudo += horasDiasList.get(i);
                                } catch (IndexOutOfBoundsException e) {
                                    conteudo += "00:00";
                                    conteudo += "       ";
                                }
                            } else if (i < horasDiasList.size()) {
                                try {
                                    conteudo += horasDiasList.get(i);
                                } catch (IndexOutOfBoundsException e) {
                                    conteudo += "00:00";
                                    conteudo += "       ";
                                }
                            } else {
                                if (!(i + 1 > (multiplo_24 * 24))) {
                                    conteudo += "00:00";
                                    conteudo += "       ";
                                }
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

                        if (expArquivoAudio) {
                            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", list.get(0).getCliente().getIdcliente()).findOne();
                            String origem = dados.getLocalOrigemSpot();
                            String destino = dados.getLocalDestinoExp();

                            SmbFile smbOrigem = new SmbFile(origem, Utilities.getAuthSmbDefault());
                            SmbFile smb2Origem = new SmbFile(origem + bean.getArquivo() + "/", Utilities.getAuthSmbDefault());
                            SmbFile smbDestino = new SmbFile(destino + smbArquivoNome.trim(), Utilities.getAuthSmbDefault());

                            if (!smbOrigem.exists()) {
                                smbOrigem.mkdirs();
                            }

                            if (smb2Origem.exists()) {
                                Utilities.createLogComercial(dados.getLocalDestinoExp(), true, smbOrigem.getName());
                                SmbFileInputStream sfis = new SmbFileInputStream(smb2Origem);
                                SmbFileOutputStream sfous = new SmbFileOutputStream(smbDestino, true);
                                IOUtils.copy(sfis, sfous);
                            } else {
                                Utilities.createLogComercial(dados.getLocalDestinoExp(), false, smbOrigem.getName());
                            }
                        }
                        multiplo_24++;
                    }
                }
            }

            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", list.get(0).getCliente().getIdcliente()).findOne();
            String destino = dados.getLocalDestinoExp();
            SmbFile smb = new SmbFile(destino, Utilities.getAuthSmbDefault());
            SmbFile smb2 = new SmbFile(destino + "comercial.exp", Utilities.getAuthSmbDefault());

            if (!smb.exists()) {
                smb.mkdirs();
            }

            if (smb2.exists()) {
                conteudo = Utilities.quebrarLinhaComHexa() + conteudo;
            }

            SmbFileOutputStream sfous = new SmbFileOutputStream(smb2);
            sfous.write(conteudo.getBytes());
            sfous.close();

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

    public void validarComercial(Integer[] id_list, Boolean expArquivoAudio, Integer idcliente, String titulo, String arquivo, Integer codigo) {
        List<AudiostoreComercialBean> list = null;

        if (null != id_list && id_list.length > 0) {
            list = repository.query(AudiostoreComercialBean.class).in("id", id_list).findAll();
        } else {
            AudiostoreComercialJSON json = beanList(1, 999999999, null, titulo, arquivo, codigo, idcliente);
            if (null != json.getRows() && !json.getRows().isEmpty()) {
                id_list = new Integer[json.getRows().size()];
                int i = 0;
                for (AudiostoreComercialDTO dto : json.getRows()) {
                    id_list[i] = Integer.parseInt(dto.getId().trim());
                    i++;
                }
            }
            list = repository.query(AudiostoreComercialBean.class).in("id", id_list).findAll();
        }

        boolean ajaxResultBool = true;
        String ajaxResultStr = "";
        if (null != id_list && id_list.length > 0) {
            // verifica se todos são do mesmo cliente
            Integer idclienteAux = list.get(0).getCliente().getIdcliente();
            for (AudiostoreComercialBean bean : list) {
                if (!bean.getCliente().getIdcliente().equals(idclienteAux)) {
                    ajaxResultBool = false;
                    ajaxResultStr = "Você selecionou programações de clientes diferentes!";
                    break;
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
                if (null == dados || null == dados.getLocalDestinoExp() || dados.getLocalDestinoExp().trim().isEmpty()) {
                    ajaxResultBool = false;
                    ajaxResultStr = "O cliente não possui um local de destino para os arquivos de exportação!";
                }
            }

            try {
                Utilities.createTaskLock(dados.getLocalDestinoExp(), sessionUsuario.getUsuarioBean().getNome(), httpServletRequest, Utilities.TaskLock.COMERCIAL);
                upload(id_list, expArquivoAudio);
                Utilities.removerLogComercial(dados.getLocalDestinoExp());
            } catch (Exception e) {
                ajaxResultBool = false;
                ajaxResultStr = e.getMessage();
            } finally {
                Utilities.deleteTaskLock(dados.getLocalDestinoExp(), Utilities.TaskLock.COMERCIAL);
            }
            result.use(Results.json()).withoutRoot().from(new AjaxResult(ajaxResultBool, ajaxResultStr)).recursive().serialize();
        } else {
            ajaxResultBool = false;
            ajaxResultStr = "Não foi possivel gerar arquivo.";
        }


    }

    public void validarCateg(Integer idcliente) {
        result.use(Results.json()).withoutRoot().from(repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", idcliente).eq("tipo", new Short("2")).findAll()).recursive().serialize();
    }
}