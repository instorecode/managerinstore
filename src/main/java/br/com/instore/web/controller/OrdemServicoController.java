package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.DataValidatorException;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.OrdemServicoFileBean;
import br.com.instore.core.orm.bean.OrdemServicoObsBean;
import br.com.instore.core.orm.bean.OrdemServicoParte1Bean;
import br.com.instore.core.orm.bean.OrdemServicoParte2Bean;
import br.com.instore.core.orm.bean.OrdemServicoParte3Bean;
import br.com.instore.core.orm.bean.OrdemServicoParte4Bean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.web.component.request.RequestAjuda;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OSFila;
import br.com.instore.web.dto.OrdenServicoObsBeanExtended;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.io.IOUtils;

@Resource
public class OrdemServicoController implements java.io.Serializable {

    private Result result;
    private RequestAjuda requestAjuda;
    private SessionRepository repository;
    private SessionUsuario sessionUsuario;

    public OrdemServicoController(Result result, RequestAjuda requestAjuda, SessionRepository repository, SessionUsuario sessionUsuario) {
        this.result = result;
        this.requestAjuda = requestAjuda;
        this.repository = repository;
        this.sessionUsuario = sessionUsuario;
        repository.setUsuario(sessionUsuario.getUsuarioBean());
    }

    @Get
//    @Restrict
    @Path("/ordem-servico/cadastro")
    public void cadastro() {
        List<VozBean> vozBeanList = repository.query(VozBean.class).orderAsc("nome").findAll();
        result.include("vozBeanList", vozBeanList);

        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).orderAsc("nome").findAll();
        result.include("clienteBeanList", clienteBeanList);

        List<ClienteBean> clienteBeanListOrderByCod = repository.query(ClienteBean.class).orderAsc("codigoInterno").findAll();
        result.include("clienteBeanListOrderByCod", clienteBeanListOrderByCod);

        result.include("dataAtualStr", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).orderAsc("categoria").findAll();
        result.include("audiostoreCategoriaBeanList", audiostoreCategoriaBeanList);
    }

    @Get
//    @Restrict
    @Path("/ordem-servico/atualizar/{id}")
    public void atualizar(Integer id) {
        OrdemServicoParte1Bean ordemServicoParte1Bean = repository.find(OrdemServicoParte1Bean.class, id);
        OrdemServicoParte2Bean ordemServicoParte2Bean = repository.query(OrdemServicoParte2Bean.class).eq("fk", id).findOne();
        OrdemServicoParte3Bean ordemServicoParte3Bean = repository.query(OrdemServicoParte3Bean.class).eq("fk", id).findOne();
        OrdemServicoParte4Bean ordemServicoParte4Bean = repository.query(OrdemServicoParte4Bean.class).eq("fk", id).findOne();
        VozBean locutor = repository.find(VozBean.class, ordemServicoParte2Bean.getLocutor());
        if (ordemServicoParte4Bean.getCategoria() > 0) {
            result.include("audiostoreCategoriaBean", repository.find(AudiostoreCategoriaBean.class, ordemServicoParte4Bean.getCategoria()));
        }
        List<OrdemServicoObsBean> ordemServicoObsBeanList = repository.query(OrdemServicoObsBean.class).eq("fk", id).findAll();
        List<OrdenServicoObsBeanExtended> ordemServicoObsBeanExtendedList = new ArrayList<OrdenServicoObsBeanExtended>();

        for (OrdemServicoObsBean ordemServicoObsBean : ordemServicoObsBeanList) {
            OrdenServicoObsBeanExtended o = new OrdenServicoObsBeanExtended();
            o.setData(ordemServicoObsBean.getData());
            o.setFk(ordemServicoObsBean.getFk());
            o.setHtml(ordemServicoObsBean.getHtml());
            o.setId(ordemServicoObsBean.getId());
            o.setTipo(ordemServicoObsBean.getTipo());
            o.setUsuario(ordemServicoObsBean.getUsuario());
            o.setUsuarioBean((UsuarioBean) repository.find(UsuarioBean.class, ordemServicoObsBean.getUsuario()));
            ordemServicoObsBeanExtendedList.add(o);
        }
        UsuarioBean usuarioBean = repository.find(UsuarioBean.class, ordemServicoParte1Bean.getUsuario());

        result.include("ordemServicoParte1Bean", ordemServicoParte1Bean);
        result.include("ordemServicoParte2Bean", ordemServicoParte2Bean);
        result.include("ordemServicoParte3Bean", ordemServicoParte3Bean);
        result.include("ordemServicoParte4Bean", ordemServicoParte4Bean);
        result.include("ordemServicoObsBeanList", ordemServicoObsBeanExtendedList);
        result.include("usuarioBean", usuarioBean);
        result.include("locutor", locutor);

        List<VozBean> vozBeanList = repository.query(VozBean.class).orderAsc("nome").findAll();
        result.include("vozBeanList", vozBeanList);

        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).orderAsc("nome").findAll();
        result.include("clienteBeanList", clienteBeanList);

        List<ClienteBean> clienteBeanListOrderByCod = repository.query(ClienteBean.class).orderAsc("codigoInterno").findAll();
        result.include("clienteBeanListOrderByCod", clienteBeanListOrderByCod);

        result.include("dataAtualStr", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        List<AudiostoreCategoriaBean> audiostoreCategoriaBeanList = repository.query(AudiostoreCategoriaBean.class).orderAsc("categoria").findAll();
        result.include("audiostoreCategoriaBeanList", audiostoreCategoriaBeanList);
    }

    @Post
    @Path("/ordem-servico/salvar-parte1")
    public void salvarParte1(Integer cliente, String nome, String quemSolicitou, String quandoSolicitou, String dataMaxDistr, Integer tipo) {
        OrdemServicoParte1Bean ospb = new OrdemServicoParte1Bean();
        ospb.setCliente(cliente);
        ospb.setNome(nome);
        ospb.setQuemSolicitou(quemSolicitou);
        ospb.setQuandoSolicitou(quandoSolicitou);
        ospb.setDataMaxDistribuicao(dataMaxDistr);
        ospb.setTipo(tipo);
        ospb.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
        ospb.setData(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int) ospb.getId()).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/salvar-parte2")
    public void salvarParte2(Integer fk, Integer locutor, String texto, String prazo) {

        OrdemServicoParte2Bean ospb = new OrdemServicoParte2Bean();
        ospb.setFk(fk);
        ospb.setLocutor(locutor);
        ospb.setTexto(texto);
        ospb.setPrazoLocucao(prazo);

        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int) ospb.getId()).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/salvar-parte3")
    public void salvarParte3(Integer fk, String prazo_estudio) {

        OrdemServicoParte3Bean ospb = new OrdemServicoParte3Bean();
        ospb.setFk(fk);
        ospb.setPrazoEstudio(prazo_estudio);

        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int) ospb.getId()).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/salvar-parte4")
    public void salvarParte4(Integer fk, Integer tipo, Integer categoria, String frequencia, String dinicial, String dfinal, String dvencimento, String unidades, String horarios) {

        OrdemServicoParte4Bean ospb = new OrdemServicoParte4Bean();

        if (null == categoria) {
            categoria = 0;
        }

        if (null == horarios) {
            horarios = "";
        }

        ospb.setFk(fk);
        ospb.setTipo(tipo);
        ospb.setCategoria(categoria);
        ospb.setFrequencia(frequencia);
        ospb.setDinicial(dinicial);
        ospb.setDfinal(dfinal);
        ospb.setDvencimento(dvencimento);
        ospb.setUnidades(unidades);
        ospb.setHorarios(horarios);

        OrdemServicoFileBean osfb = new OrdemServicoFileBean();
        osfb.setData(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        osfb.setFk(fk);
        osfb.setPrioridade(0);
        osfb.setSituacao(1);
        osfb.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());

        repository.save(ospb);
        repository.save(osfb);
        repository.finalize();
        result.use(Results.json()).from((int) ospb.getId()).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/salvar-obs")
    public void salvarObs(Integer fk, String data, String html, Integer tipo) {

        OrdemServicoObsBean obs = new OrdemServicoObsBean();
        obs.setFk(fk);
        obs.setData(data);
        obs.setHtml(html);
        obs.setTipo(tipo);
        obs.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());

        repository.save(obs);
        repository.finalize();
        result.use(Results.json()).from((int) obs.getId()).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/atualizar-parte1")
    public void atualizarParte1(Integer pk, Integer cliente, String nome, String quemSolicitou, String quandoSolicitou, String dataMaxDistr, Integer tipo) {

        try {
            repository.query("DELETE FROM ordem_servico_obs WHERE id > 0 and fk = " + pk).executeSQLCommand2();
        } catch (DataValidatorException e) {
            e.printStackTrace();
        }

        OrdemServicoParte1Bean ospb = new OrdemServicoParte1Bean();
        ospb.setId(pk);
        ospb.setCliente(cliente);
        ospb.setNome(nome);
        ospb.setQuemSolicitou(quemSolicitou);
        ospb.setQuandoSolicitou(quandoSolicitou);
        ospb.setDataMaxDistribuicao(dataMaxDistr);
        ospb.setTipo(tipo);
        ospb.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
        ospb.setData(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int) ospb.getId()).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/atualizar-parte2")
    public void atualizarParte2(Integer pk, Integer fk, Integer locutor, String texto, String prazo) {

        OrdemServicoParte2Bean ospb = new OrdemServicoParte2Bean();
        ospb.setId(pk);
        ospb.setFk(fk);
        ospb.setLocutor(locutor);
        ospb.setTexto(texto);
        ospb.setPrazoLocucao(prazo);

        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int) ospb.getId()).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/atualizar-parte3")
    public void atualizarParte3(Integer pk, Integer fk, String prazo_estudio) {

        OrdemServicoParte3Bean ospb = new OrdemServicoParte3Bean();
        ospb.setId(pk);
        ospb.setFk(fk);
        ospb.setPrazoEstudio(prazo_estudio);

        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int) ospb.getId()).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/atualizar-parte4")
    public void atualizarParte4(Integer pk, Integer fk, Integer tipo, Integer categoria, String frequencia, String dinicial, String dfinal, String dvencimento, String unidades, String horarios) {

        OrdemServicoParte4Bean ospb = new OrdemServicoParte4Bean();

        if (null == categoria) {
            categoria = 0;
        }

        if (null == horarios) {
            horarios = "";
        }

        ospb.setId(pk);
        ospb.setFk(fk);
        ospb.setTipo(tipo);
        ospb.setCategoria(categoria);
        ospb.setFrequencia(frequencia);
        ospb.setDinicial(dinicial);
        ospb.setDfinal(dfinal);
        ospb.setDvencimento(dvencimento);
        ospb.setUnidades(unidades);
        ospb.setHorarios(horarios);

        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int) ospb.getId()).recursive().serialize();
    }

    @Get
//    @Restrict
    @Path("/ordem-servico/visualizar-estrutura-das-programacoes")
    public void vep() {
    }

    @Get
//    @Restrict
    @Path("/ordem-servico/lista")
    public void lista() {
    }

    @Get
    @Path("/ordem-servico/listajson")
    public void listajson() {
        String sql = "SELECT \n"
                + "    LPAD(ordem_servico_parte1.id , 5 , '0') as cod ,\n"
                + "    nome , \n"
                + "    data_max_distribuicao as dataDist ,\n"
                + "    IF( STR_TO_DATE(data_max_distribuicao, '%d/%m/%Y') > CURDATE() , 0 , 1) AS explodiu , \n"
                + "    prioridade , situacao\n"
                + "FROM\n"
                + "    ordem_servico_fila ,\n"
                + "    ordem_servico_parte1\n"
                + "WHERE \n"
                + "	ordem_servico_parte1.id = ordem_servico_fila.fk\n"
                + "ORDER BY prioridade ASC";
        final List<OSFila> osfilaList = new ArrayList<OSFila>();
        repository.query(sql).executeSQL(new Each() {
            public String cod;
            public String nome;
            public String dataDist;
            public BigInteger explodiu;
            public Integer prioridade;
            public Integer situacao;

            @Override
            public void each() {
                OSFila osf = new OSFila();
                osf.setCod(cod);
                osf.setDataDist(dataDist);
                osf.setExplodiu(explodiu.intValue());
                osf.setNome(nome);
                osf.setPrioridade(prioridade);
                osf.setSituacao(situacao);
                osfilaList.add(osf);
            }
        });
        result.use(Results.json()).withoutRoot().from(osfilaList).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/postjson")
    public void postjson(String valores) {
        for (String val : valores.split(",")) {
            String sql = "UPDATE ordem_servico_fila SET data = DATE_FORMAT(now() , '%d/%m/%Y %H:%i:%s') , situacao = " + val.split("-")[2] + " , prioridade = " + val.split("-")[1] + " WHERE id > 0 and fk = " + val.split("-")[0] + ";";
            try {
                repository.query(sql).executeSQLCommand2();
            } catch (DataValidatorException e) {
                e.printStackTrace();
            }
        }
        repository.finalize();
        result.use(Results.json()).withoutRoot().from(1).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/postarquivo")
    public void postarquivo(UploadedFile arquivo, Integer fk) {
        if (null == arquivo) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Selecione um arquivo")).recursive().serialize();
            return;
        }

        if (!arquivo.getFileName().endsWith(".wav")) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O arquivo tem qe ser wav")).recursive().serialize();
            return;
        }

        if (!arquivo.getFileName().endsWith(".wav")) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O arquivo tem qe ser wav")).recursive().serialize();
            return;
        }

        OrdemServicoParte1Bean ordemServicoParte1Bean = repository.find(OrdemServicoParte1Bean.class, fk);
        ClienteBean clienteBean = repository.find(ClienteBean.class, ordemServicoParte1Bean.getCliente());
        DadosClienteBean dadosClienteBean = repository.query(DadosClienteBean.class).eq("cliente.idcliente", clienteBean.getIdcliente()).findOne();

        NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();
        if (dadosClienteBean.getLocalDestinoSpot().contains("192.168.1.249")) {
            auth = Utilities.getAuthSmbDefault1921681249();
        }

        if (dadosClienteBean.getLocalDestinoSpot().contains("://ftp")) {
            auth = Utilities.getAuthSmbDefault1921681249();
        }

        try {
            SmbFile sf = new SmbFile(dadosClienteBean.getLocalDestinoSpot(), auth);
            if (!sf.exists()) {
                sf.mkdirs();
            }

            sf = new SmbFile(dadosClienteBean.getLocalDestinoSpot().concat("/").concat(ordemServicoParte1Bean.getId().toString()).concat(".wav"), auth);

            if (sf.exists()) {
                sf.delete();
            }

            SmbFileOutputStream sfos = new SmbFileOutputStream(sf);
            IOUtils.copy(arquivo.getFile(), sfos);
            sfos.flush();
            sfos.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Arquivo enviado")).recursive().serialize();

    }

    @Get
    @Path("/ordem-servico/ouvir/{fk}")
    public InputStreamDownload postarquivo(Integer fk) {
        OrdemServicoParte1Bean ordemServicoParte1Bean = repository.find(OrdemServicoParte1Bean.class, fk);
        ClienteBean clienteBean = repository.find(ClienteBean.class, ordemServicoParte1Bean.getCliente());
        DadosClienteBean dadosClienteBean = repository.query(DadosClienteBean.class).eq("cliente.idcliente", clienteBean.getIdcliente()).findOne();

        NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();
        if (dadosClienteBean.getLocalDestinoSpot().contains("192.168.1.249")) {
            auth = Utilities.getAuthSmbDefault1921681249();
        }

        if (dadosClienteBean.getLocalDestinoSpot().contains("://ftp")) {
            auth = Utilities.getAuthSmbDefault1921681249();
        }

        SmbFile sf;
        try {
            sf = new SmbFile(dadosClienteBean.getLocalDestinoSpot().concat("/").concat(ordemServicoParte1Bean.getId().toString()).concat(".wav"), auth);

            if (sf.exists()) {
                SmbFileInputStream sfis = new SmbFileInputStream(sf);
                return new InputStreamDownload(sfis, "audio/wav", fk.toString().concat(".wav"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
