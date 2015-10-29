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
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.OrdemServicoFileBean;
import br.com.instore.core.orm.bean.OrdemServicoHashBean;
import br.com.instore.core.orm.bean.OrdemServicoObsBean;
import br.com.instore.core.orm.bean.OrdemServicoParte1Bean;
import br.com.instore.core.orm.bean.OrdemServicoParte2Bean;
import br.com.instore.core.orm.bean.OrdemServicoParte3Bean;
import br.com.instore.core.orm.bean.OrdemServicoParte4Bean;
import br.com.instore.core.orm.bean.OrdemServicoValidacaoInternaBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAjuda;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.OSDetalhes;
import br.com.instore.web.dto.OSFila;
import br.com.instore.web.dto.OrdenServicoObsBeanExtended;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.JavaMail;
import br.com.instore.web.tools.Utilities;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

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
    @Restrict
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
    @Restrict
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
    public void salvarParte1(Integer cliente, String nome, String quemSolicitou, String quemSolicitouEmail, String quandoSolicitou, String dataMaxDistr, Integer tipo) {
        OrdemServicoParte1Bean ospb = new OrdemServicoParte1Bean();
        ospb.setCliente(cliente);
        ospb.setNome(nome);
        ospb.setQuemSolicitou(quemSolicitou);
        ospb.setQuandoSolicitou(quandoSolicitou);
        ospb.setDataMaxDistribuicao(dataMaxDistr);
        ospb.setTipo(tipo);
        ospb.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
        ospb.setData(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        ospb.setQuemSolicitouEmail(quemSolicitouEmail);

        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int) ospb.getId()).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/salvar-parte2")
    public void salvarParte2(Integer fk, Integer locutor, String texto, String prazo, Integer email) {

        OrdemServicoParte2Bean ospb = new OrdemServicoParte2Bean();
        ospb.setFk(fk);
        ospb.setLocutor(locutor);
        ospb.setTexto(texto);
        ospb.setPrazoLocucao(prazo);

        if (email == 1) {
            VozBean voz = repository.find(VozBean.class, locutor);
            if (null != voz) {
                JavaMail.send("Instore", "Solicitação de locução <br /><br /> <b>texto:</b> <br />".concat(texto), voz.getEmail());
            }
        }

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
    public void atualizarParte1(Integer pk, Integer cliente, String nome, String quemSolicitou, String quemSolicitouEmail, String quandoSolicitou, String dataMaxDistr, Integer tipo) {

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
        ospb.setQuemSolicitouEmail(quemSolicitouEmail);

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
    public void vep(Integer idcliente) {
        List<AudiostoreProgramacaoCategoriaBean> audiostoreProgramacaoCategoriaBeanList = new ArrayList<AudiostoreProgramacaoCategoriaBean>();
        List<AudiostoreProgramacaoBean> audiostoreProgramacaoBeanList = repository.query(AudiostoreProgramacaoBean.class).eq("cliente.idcliente", idcliente).orderAsc("descricao").findAll();

        for (AudiostoreProgramacaoBean item : audiostoreProgramacaoBeanList) {
            List<AudiostoreProgramacaoCategoriaBean> ls = repository.query(AudiostoreProgramacaoCategoriaBean.class).eq("audiostoreProgramacao.id", item.getId()).findAll();
            for (AudiostoreProgramacaoCategoriaBean l : ls) {
                audiostoreProgramacaoCategoriaBeanList.add(l);
            }
        }

        result.include("audiostoreProgramacaoBeanList", audiostoreProgramacaoBeanList);
        result.include("audiostoreProgramacaoCategoriaBeanList", audiostoreProgramacaoCategoriaBeanList);
    }

    @Get
    @Restrict
    @Path("/ordem-servico/lista")
    public void lista() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("parente", 0).eq("matriz", true).orderAsc("nome").findAll();
        result.include("clienteBeanList", clienteBeanList);
    }

    @Get
    @Path("/ordem-servico/listajson")
    public void listajson() {
        String sql = "SELECT \n"
                + "    LPAD(ordem_servico_parte1.id, 5, '0') AS cod,\n"
                + "    nome,\n"
                + "    data_max_distribuicao AS dataDist,\n"
                + "    CONCAT('',\n"
                + "            IF(STR_TO_DATE(data_max_distribuicao, '%d/%m/%Y') > CURDATE(),\n"
                + "                0,\n"
                + "                1),\n"
                + "            '') AS explodiu,\n"
                + "    CONCAT('', prioridade, '') AS prioridade,\n"
                + "    CONCAT('', situacao, '') AS situacao,\n"
                + "    CONCAT('', ordem_servico_parte1.cliente, '') AS cliente,\n"
                + "    CONCAT('',\n"
                + "            IF(situacao = 5,\n"
                + "                IF((SELECT \n"
                + "                            COUNT(id)\n"
                + "                        FROM\n"
                + "                            ordem_servico_hash\n"
                + "                        WHERE\n"
                + "                            fk = ordem_servico_parte1.id) = 0,\n"
                + "                    1,\n"
                + "                    IF((SELECT \n"
                + "                                COUNT(id)\n"
                + "                            FROM\n"
                + "                                ordem_servico_hash\n"
                + "                            WHERE\n"
                + "                                fk = ordem_servico_parte1.id) = (SELECT \n"
                + "                                COUNT(id)\n"
                + "                            FROM\n"
                + "                                ordem_servico_hash\n"
                + "                            WHERE\n"
                + "                                fk = ordem_servico_parte1.id\n"
                + "                                    AND finalizado = 2),\n"
                + "                        1,\n"
                + "                        0)),\n"
                + "                0),\n"
                + "            '') AS enviar_email,\n"
                + "    CONCAT('',\n"
                + "            IF((SELECT \n"
                + "                        ordem_servico_hash.id AS data\n"
                + "                    FROM\n"
                + "                        ordem_servico_hash\n"
                + "                    WHERE\n"
                + "                        fk = ordem_servico_parte1.id\n"
                + "                            AND finalizado = 1\n"
                + "                    ORDER BY STR_TO_DATE(data, '%d/%m/%Y %H:%i:%s') DESC\n"
                + "                    LIMIT 1) IS NULL,\n"
                + "                0,\n"
                + "                (SELECT \n"
                + "                        ordem_servico_hash.id AS data\n"
                + "                    FROM\n"
                + "                        ordem_servico_hash\n"
                + "                    WHERE\n"
                + "                        fk = ordem_servico_parte1.id\n"
                + "                            AND finalizado < 2\n"
                + "                    ORDER BY STR_TO_DATE(data, '%d/%m/%Y %H:%i:%s') DESC\n"
                + "                    LIMIT 1)),\n"
                + "            '') AS avaliacao_id,\n"
                + "    CONCAT('',\n"
                + "            IF((SELECT \n"
                + "                        COUNT(ordem_servico_hash.id) AS data\n"
                + "                    FROM\n"
                + "                        ordem_servico_hash\n"
                + "                    WHERE\n"
                + "                        fk = ordem_servico_parte1.id\n"
                + "                            AND finalizado = 1\n"
                + "                    ORDER BY STR_TO_DATE(data, '%d/%m/%Y %H:%i:%s') DESC\n"
                + "                    LIMIT 1) > 0,\n"
                + "                (SELECT \n"
                + "                        COUNT(ordem_servico_hash.id) AS data\n"
                + "                    FROM\n"
                + "                        ordem_servico_hash\n"
                + "                    WHERE\n"
                + "                        fk = ordem_servico_parte1.id\n"
                + "                            AND finalizado = 1\n"
                + "                    ORDER BY STR_TO_DATE(data, '%d/%m/%Y %H:%i:%s') DESC\n"
                + "                    LIMIT 1),\n"
                + "                0),\n"
                + "            '') avaliacao_count,\n"
                + "    (SELECT \n"
                + "            ordem_servico_hash.data AS data\n"
                + "        FROM\n"
                + "            ordem_servico_hash\n"
                + "        WHERE\n"
                + "            fk = ordem_servico_parte1.id\n"
                + "                AND finalizado = 1\n"
                + "        ORDER BY STR_TO_DATE(data, '%d/%m/%Y %H:%i:%s') DESC\n"
                + "        LIMIT 1) AS avaliacao_data,\n"
                + "    (SELECT \n"
                + "            ordem_servico_hash.email AS data\n"
                + "        FROM\n"
                + "            ordem_servico_hash\n"
                + "        WHERE\n"
                + "            fk = ordem_servico_parte1.id\n"
                + "                AND finalizado = 1\n"
                + "        ORDER BY STR_TO_DATE(data, '%d/%m/%Y %H:%i:%s') DESC\n"
                + "        LIMIT 1) AS avaliacao_email,\n"
                + "    CONCAT('',\n"
                + "            (SELECT \n"
                + "                    ordem_servico_hash.situacao AS data\n"
                + "                FROM\n"
                + "                    ordem_servico_hash\n"
                + "                WHERE\n"
                + "                    fk = ordem_servico_parte1.id\n"
                + "                        AND finalizado = 1\n"
                + "                ORDER BY STR_TO_DATE(data, '%d/%m/%Y %H:%i:%s') DESC\n"
                + "                LIMIT 1),\n"
                + "            '') AS avaliacao_situacao,\n"
                + "    (SELECT \n"
                + "            ordem_servico_hash.obs AS obs\n"
                + "        FROM\n"
                + "            ordem_servico_hash\n"
                + "        WHERE\n"
                + "            fk = ordem_servico_parte1.id\n"
                + "                AND finalizado = 1\n"
                + "        ORDER BY STR_TO_DATE(data, '%d/%m/%Y %H:%i:%s') DESC\n"
                + "        LIMIT 1) AS avaliacao_obs,\n"
                + "    CONCAT('',\n"
                + "            (SELECT \n"
                + "                    id\n"
                + "                FROM\n"
                + "                    ordem_servico_validacao_interna\n"
                + "                WHERE\n"
                + "                    fk = ordem_servico_parte1.id\n"
                + "                ORDER BY id ASC\n"
                + "                LIMIT 1),\n"
                + "            '') AS avaliacao_prim_id,\n"
                + "    CONCAT('',\n"
                + "            (SELECT \n"
                + "                    data\n"
                + "                FROM\n"
                + "                    ordem_servico_validacao_interna\n"
                + "                WHERE\n"
                + "                    fk = ordem_servico_parte1.id\n"
                + "                ORDER BY id ASC\n"
                + "                LIMIT 1),\n"
                + "            '') AS avaliacao_prim_data,\n"
                + "    CONCAT('',\n"
                + "            (SELECT \n"
                + "                    usuario.nome\n"
                + "                FROM\n"
                + "                    ordem_servico_validacao_interna,\n"
                + "                    usuario\n"
                + "                WHERE\n"
                + "                    fk = ordem_servico_parte1.id\n"
                + "                        AND usuario.idusuario = ordem_servico_validacao_interna.usuario\n"
                + "                ORDER BY id ASC\n"
                + "                LIMIT 1),\n"
                + "            '') AS avaliacao_prim_us,\n"
                + "    CONCAT('',\n"
                + "            (SELECT \n"
                + "                    id\n"
                + "                FROM\n"
                + "                    ordem_servico_validacao_interna\n"
                + "                WHERE\n"
                + "                    fk = ordem_servico_parte1.id\n"
                + "                ORDER BY id DESC\n"
                + "                LIMIT 1),\n"
                + "            '') AS avaliacao_sec_id,\n"
                + "    CONCAT('',\n"
                + "            (SELECT \n"
                + "                    data\n"
                + "                FROM\n"
                + "                    ordem_servico_validacao_interna\n"
                + "                WHERE\n"
                + "                    fk = ordem_servico_parte1.id\n"
                + "                ORDER BY id DESC\n"
                + "                LIMIT 1),\n"
                + "            '') AS avaliacao_sec_data,\n"
                + "    CONCAT('',\n"
                + "            (SELECT \n"
                + "                    usuario.nome\n"
                + "                FROM\n"
                + "                    ordem_servico_validacao_interna,\n"
                + "                    usuario\n"
                + "                WHERE\n"
                + "                    fk = ordem_servico_parte1.id\n"
                + "                        AND usuario.idusuario = ordem_servico_validacao_interna.usuario\n"
                + "                ORDER BY id DESC\n"
                + "                LIMIT 1),\n"
                + "            '') AS avaliacao_sec_us\n"
                + "FROM\n"
                + "    ordem_servico_fila,\n"
                + "    ordem_servico_parte1\n"
                + "WHERE\n"
                + "    ordem_servico_parte1.id = ordem_servico_fila.fk\n"
                + "ORDER BY prioridade ASC";

        final List<OSFila> osfilaList = new ArrayList<OSFila>();
        repository.query(sql).executeSQL(new Each() {
            public String cod;
            public String nome;
            public String dataDist;
            public String explodiu;
            public String prioridade;
            public String situacao;
            public String cliente;
            public String enviar_email;
            public String avaliacao_id;
            public String avaliacao_count;
            public String avaliacao_data;
            public String avaliacao_email;
            public String avaliacao_situacao;
            public String avaliacao_obs;

            public String avaliacao_prim_id;
            public String avaliacao_prim_data;
            public String avaliacao_prim_us;

            public String avaliacao_sec_id;
            public String avaliacao_sec_data;
            public String avaliacao_sec_us;

            @Override
            public void each() {
                OSFila osf = new OSFila();
                osf.setCod(cod);
                osf.setDataDist(dataDist);
                osf.setExplodiu(null != explodiu && !explodiu.isEmpty() ? Integer.parseInt(explodiu) : 0);
                osf.setNome(nome);
                osf.setPrioridade(null != prioridade && !prioridade.isEmpty() ? Integer.parseInt(prioridade) : 0);
                osf.setCliente(null != cliente && !cliente.isEmpty() ? Integer.parseInt(cliente) : 0);
                osf.setSituacao(null != situacao && !situacao.isEmpty() ? Integer.parseInt(situacao) : 0);
                osf.setEnviarEmail(null != enviar_email && !enviar_email.isEmpty() ? Integer.parseInt(enviar_email) : 0);
                osf.setAvaliacaoCount(null != avaliacao_count && !avaliacao_count.isEmpty() ? Integer.parseInt(avaliacao_count) : 0);
                osf.setAvaliacaoData(avaliacao_data);
                osf.setAvaliacaoEmail(avaliacao_email);
                osf.setAvaliacaoSituacao(null != avaliacao_situacao && !avaliacao_situacao.isEmpty() ? Integer.parseInt(avaliacao_situacao) : 0);
                osf.setAvaliacaoObs(avaliacao_obs);
                osf.setAvaliacaoId(null != avaliacao_id && !avaliacao_id.isEmpty() ? Integer.parseInt(avaliacao_id) : 0);

                osf.setAvaliacaoPrimId(avaliacao_prim_id);
                osf.setAvaliacaoPrimData(avaliacao_prim_data);
                osf.setAvaliacaoPrimUs(avaliacao_prim_us);

                osf.setAvaliacaoSecId(avaliacao_sec_id);
                osf.setAvaliacaoSecData(avaliacao_sec_data);
                osf.setAvaliacaoSecUs(avaliacao_sec_us);

                osfilaList.add(osf);
            }
        });
//        result.use(CharsetJSONSerialization.class).withoutRoot().from(osfilaList).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/postjson")
    public void postjson(String valores) {
        for (String val : valores.split(",")) {
            String sql = "UPDATE ordem_servico_fila SET data = DATE_FORMAT(now() , '%d/%m/%Y %H:%i:%s') , situacao = " + val.split("-")[2] + " , prioridade = " + val.split("-")[1] + " WHERE id > 0 and fk = " + val.split("-")[0] + ";";
            Integer id = Integer.parseInt(val.split("-")[0].trim().replaceAll("\\s", ""));;
            Integer situacaoInt = Integer.parseInt(val.split("-")[2].trim().replaceAll("\\s", ""));

            List<OrdemServicoHashBean> ordemServicoHashBeanList = repository.query(OrdemServicoHashBean.class).eq("fk", id).findAll();
//            for (OrdemServicoHashBean item : ordemServicoHashBeanList) {
//                if (item.getFinalizado() == 0) {
//                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Um e-mail já foi enviado para o cliente , mais ele ainda nao avaliou a OS.")).recursive().serialize();
//                    return;
//                }
//            }

//            List<OrdemServicoHashBean> ordemServicoHashBeanList2 = repository.query(OrdemServicoHashBean.class).eq("fk", id).moreEqual("finalizado", 1).orderDesc("id").limit(1).findAll();
//            OrdemServicoHashBean ordemServicoHashBean = null;
//            if (!ordemServicoHashBeanList2.isEmpty()) {
//                ordemServicoHashBean = ordemServicoHashBeanList2.get(0);
//            }
            OrdemServicoFileBean osfb = repository.query(OrdemServicoFileBean.class).eq("fk", id).findOne();

//            if (situacaoInt < 4 && osfb.getSituacao() > 4) {
//                if (null != ordemServicoHashBean && 1 == ordemServicoHashBean.getSituacao()) {
//                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O cliente já avaliou , e o conteudo da OS não pode mais ser alterado.")).recursive().serialize();
//                    return;
//                }
//
//                if (null != ordemServicoHashBean && 0 == ordemServicoHashBean.getSituacao() && 1 == ordemServicoHashBean.getFinalizado()) {
//                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Antes de corrigir a OS verifique as sugestões do cliente.")).recursive().serialize();
//                    return;
//                }
//            }
//            if (situacaoInt > 4) {
//                if (null != ordemServicoHashBean && !ordemServicoHashBean.getSituacao().equals(1)) {
//                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O cliente avaliou a OS e sugerio algumas correções.")).recursive().serialize();
//                    return;
//                }
//
//                if (null != ordemServicoHashBean && ordemServicoHashBean.getSituacao().equals(1) && ordemServicoHashBean.getFinalizado().equals(1)) {
//                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Antes você deve ver as sugestões do cliente.")).recursive().serialize();
//                    return;
//                }
//            }
//            if (osfb.getSituacao() > 4 & situacaoInt < 5) {
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O cliente já avaliou , e o conteudo da OS não pode mais ser alterado.")).recursive().serialize();
//                return;
//            }
//
//            if (6 == osfb.getSituacao() && situacaoInt != 6) {
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "A OS já foi distribuida e não pode mais ser invalidada.")).recursive().serialize();
//                return;
//            }
//
//            if (7 == osfb.getSituacao() && situacaoInt != 6) {
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Você não pode editar a OS, ela já foi invalidada.")).recursive().serialize();
//                return;
//            }
//
//            if (repository.query(OrdemServicoValidacaoInternaBean.class).eq("fk", id).count().intValue() < 2 && situacaoInt == 6) {
//                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Duas pessoas precisam avaliar internamente a OS para manter a qualidade de teste.")).recursive().serialize();
//                return;
//            }
            try {
                repository.query(sql).executeSQLCommand2();
            } catch (DataValidatorException e) {
                e.printStackTrace();
            }

            if (situacaoInt == 6) {
                ditribuir(id);
            }

        }
        repository.finalize();
        result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "")).recursive().serialize();
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

    @Get
    @Path("/ordem-servico/detalhes")
    public void detalhes(Integer fk) {
        String sql = "SELECT \n"
                + "    LPAD(ordem_servico_parte1.id , 5, '0') as osn ,\n"
                + "    (SELECT nome FROM cliente WHERE idcliente = cliente) as cliente,\n"
                + "    ordem_servico_parte1.data as data,\n"
                + "    ordem_servico_parte1.quando_solicitou as quando_solicitou,\n"
                + "    if((SELECT data FROM ordem_servico_fila WHERE fk = ordem_servico_parte1.id and situacao = 5) is null , ''  , (SELECT data FROM ordem_servico_fila WHERE fk = ordem_servico_parte1.id and situacao = 5)) as aprovado_em ,\n"
                + "    ordem_servico_parte4.unidades as praca ,\n"
                + "    ordem_servico_parte4.frequencia as frequencia ,\n"
                + "    if( ordem_servico_parte1.tipo =  1 , 'Radio Interna' , if(ordem_servico_parte1.tipo = 2  , 'Radio Externa' , if(ordem_servico_parte1.tipo = 3 , 'URA' , '') )) as radio ,\n"
                + "    ordem_servico_parte1.data_max_distribuicao as data_distr,\n"
                + "    if(ordem_servico_parte4.tipo = 1 , concat('Categoria: ' , (select categoria from audiostore_categoria where codigo = ordem_servico_parte4.categoria)) , 'Determinado') as det,\n"
                + "    ordem_servico_parte2.texto as texto\n"
                + "    \n"
                + "FROM\n"
                + "    ordem_servico_parte1 , \n"
                + "    ordem_servico_parte2 ,\n"
                + "    ordem_servico_parte3 ,\n"
                + "    ordem_servico_parte4 \n"
                + "WHERE \n"
                + "	ordem_servico_parte1.id = " + fk + "\n"
                + "    AND ordem_servico_parte2.fk = " + fk + "\n"
                + "    AND ordem_servico_parte3.fk = " + fk + "\n"
                + "    AND ordem_servico_parte4.fk = " + fk + "";

        final OSDetalhes detalhes = new OSDetalhes();
        repository.query(sql).executeSQL(new Each() {
            private String osn;
            private String cliente;
            private String data;
            private String quando_solicitou;
            private String aprovado_em;
            private String praca;
            private String frequencia;
            private String radio;
            private String data_distr;
            private String det;
            private String texto;

            @Override
            public void each() {
                detalhes.setOsn(osn);
                detalhes.setCliente(cliente);
                detalhes.setData(data);
                detalhes.setQuandoSolicitou(quando_solicitou);
                detalhes.setAprovadoEm(aprovado_em);
                detalhes.setPraca(praca);
                detalhes.setFrequencia(frequencia);
                detalhes.setRadio(radio);
                detalhes.setDataDistr(data_distr);
                detalhes.setDet(det);
                detalhes.setTexto(texto);
            }
        });

        System.out.println(detalhes);

        sql = "SELECT group_concat(codigo_interno) as codigo_interno , 'param' as param  FROM cliente WHERE idcliente in(" + detalhes.getPraca().replace(".", ",") + ")";

        repository.query(sql).executeSQL(new Each() {
            private String codigo_interno;
            private String param;

            @Override
            public void each() {
                detalhes.setClientesCod(codigo_interno);
            }
        });
        List<OrdemServicoObsBean> lista = repository.query(OrdemServicoObsBean.class).eq("tipo", 2).eq("fk", fk).findAll();

        for (OrdemServicoObsBean item : lista) {
            OrdenServicoObsBeanExtended o = new OrdenServicoObsBeanExtended();
            o.setData(item.getData());
            o.setFk(item.getFk());
            o.setHtml(item.getHtml());
            o.setId(item.getId());
            o.setTipo(item.getTipo());
            o.setUsuario(item.getUsuario());
            o.setUsuarioBean((UsuarioBean) repository.find(UsuarioBean.class, item.getUsuario()));
            detalhes.getObsList().add(o);
        }

        System.out.println(detalhes);

        result.use(Results.json()).withoutRoot().from(detalhes).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/enviar-email-cliente")
    public void enviarEmailCliente(String cods, String email, String msg) {
        try {
            String hash = "".concat(cods).concat(new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()));
            hash = Utilities.md5(hash).toUpperCase();

            String codStrArr[] = cods.split("-");
            List<Integer> idList1 = new ArrayList<Integer>();
            List<Integer> idList2 = new ArrayList<Integer>();

            for (String codStr : codStrArr) {
                idList1.add(Integer.parseInt(codStr.replaceAll("\\s", "")));
            }

            List<OrdemServicoHashBean> ordemServicoHashBeanList = repository.query(OrdemServicoHashBean.class).in("fk", idList1.toArray(new Integer[idList1.size()])).findAll();
            for (OrdemServicoHashBean item : ordemServicoHashBeanList) {
                if (item.getFinalizado().equals(0)) {
                    idList1.remove(item.getId());
                    idList2.add(item.getId());
                }
            }

            DadosClienteBean dados = null;
            OrdemServicoParte1Bean ospb = null;
            for (Integer fk : idList1) {
                OrdemServicoHashBean oshb = new OrdemServicoHashBean();

                oshb.setData("");
                oshb.setEmail("");
                oshb.setFinalizado(0);
                oshb.setFk(fk);
                oshb.setHash(hash);
                oshb.setMetadata("");
                oshb.setObs("");
                oshb.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
                oshb.setSituacao(1);

                if (null == dados) {
                    ospb = repository.find(OrdemServicoParte1Bean.class, fk);
                    dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", ospb.getCliente()).findOne();
                }

                String local = dados.getLocalDestinoSpot();
                if (local.endsWith("/")) {
                    local = local.substring(0, local.length() - 1);
                }

                NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();

                if (local.contains("192.168.1.249")) {
                    auth = Utilities.getAuthSmbDefault1921681249();
                }

                SmbFile fileLocal = new SmbFile(local.concat("/").concat(fk.toString()).concat(".wav"), auth);
                SmbFileInputStream sfis = new SmbFileInputStream(fileLocal);
                postarArquivo(sfis, fileLocal.getName(), hash);
                repository.save(oshb);
            }

            JavaMail.send("Instore - solicitação de avaliação de qualidade de serviço", "Solicitamos ao senhor(a) ".concat(ospb.getQuemSolicitou()).concat(" que avalie a qualidade da OS. <br /> <a href=\"www.instore.com.br/intranet/?h=" + hash + "\">Clique aqui</a> <br /> <hr /> " + msg), email);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Um e-mail foi enviado para o cliente avaliar a OS.")).recursive().serialize();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel enviar um e-mail para o cliente.")).recursive().serialize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel enviar um e-mail para o cliente.")).recursive().serialize();
        } catch (SmbException e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel enviar um e-mail para o cliente.")).recursive().serialize();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel enviar um e-mail para o cliente.")).recursive().serialize();
        } catch (IOException e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel enviar um e-mail para o cliente.")).recursive().serialize();
        }
    }

    @Post
    @Path("/ordem-servico/finalizar2")
    public void finalizar2(Integer id) {
        OrdemServicoHashBean oshb = repository.find(OrdemServicoHashBean.class, id);
        oshb.setFinalizado(2);
        repository.save(oshb);
        repository.finalize();
        result.use(Results.json()).withoutRoot().from((int) 1).recursive().serialize();
    }

    @Post
    @Path("/ordem-servico/avaliacao-interna")
    public void avaliacaoInterna(Integer id) {
        int count = repository.query(OrdemServicoValidacaoInternaBean.class).eq("fk", id).count().intValue();
        if (count > 1) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Dois usuários já avaliaram esta OS")).recursive().serialize();
            return;
        }

        if (count > 0) {
            OrdemServicoValidacaoInternaBean bb = repository.query(OrdemServicoValidacaoInternaBean.class).eq("fk", id).findOne();
            if (bb.getUsuario().equals(sessionUsuario.getUsuarioBean().getIdusuario())) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Um mesmo usuário não pode realizar as duas avaliações")).recursive().serialize();
                return;
            }
        }

        OrdemServicoFileBean ordemServicoFileBean = repository.query(OrdemServicoFileBean.class).eq("fk", id).findOne();
        if (ordemServicoFileBean.getSituacao() < 5) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "A OS não pode ser avaliada, apenas OS na situação 'Aprovado' podem ser avaliadas.")).recursive().serialize();
            return;
        }

        OrdemServicoValidacaoInternaBean bean = new OrdemServicoValidacaoInternaBean();
        bean.setData(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        bean.setFk(id);
        bean.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());

        repository.save(bean);
        repository.finalize();
        result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Avaliado com sucesso.")).recursive().serialize();
    }

    public void postarArquivo(InputStream is, String nome, String hash) throws IOException {
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

//        HttpPost httppost = new HttpPost("http://192.168.1.89/intranet/recebe_arquivo.php");
        HttpPost httppost = new HttpPost("http://localhost/test/recebe_arquivo.php");

        HttpEntity httpEntity = MultipartEntityBuilder.create().addBinaryBody("arquivo", is, ContentType.create("audio/wav"), nome.concat("-").concat(hash)).build();

        httppost.setEntity(httpEntity);
        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            System.out.println(EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {
            resEntity.consumeContent();
        }

        httpclient.getConnectionManager().shutdown();
        is.close();
    }

    public void ditribuir(final Integer id) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                OrdemServicoParte1Bean bean1 = repository.query(OrdemServicoParte1Bean.class).eq("id", id).findOne();
                OrdemServicoParte4Bean bean4 = repository.query(OrdemServicoParte4Bean.class).eq("fk", id).findOne();
                if (null != bean1 && null != bean4) {
                    List<String> idstrList = Arrays.asList(bean4.getUnidades().split("."));
                    ClienteBean cliente = repository.find(ClienteBean.class, bean1.getCliente());

                    if (null != cliente) {
                        try {
                            String lines = "";

                            // Arquivo                          
                            lines = lines.concat(Utilities.absoluteLeftPad(bean1.getCliente().toString().concat(".wav"), 30, " "));

                            // Cliente                          
                            lines = lines.concat(Utilities.absoluteLeftPad(cliente.getNome(), 30, " "));

                            // Titulo                            
                            lines = lines.concat(Utilities.absoluteLeftPad(bean1.getNome(), 30, " "));

                            // tipo Interprete  
                            lines = lines.concat("1");

                            // Categoria                          
                            lines = lines.concat(Utilities.absoluteLeftPad(bean4.getCategoria().toString(), 3, "0"));

                            // Período Inicial (dd/mm/aa)                          
                            lines = lines.concat(bean4.getDinicial().substring(0, 6).concat(bean4.getDinicial().substring(8, 10)));

                            // Período final (dd/mm/aa)                         
                            lines = lines.concat(bean4.getDinicial().substring(0, 6).concat(bean4.getDfinal().substring(8, 10)));

                            // Horario & Semana (1-24)                                  
                            // X -> INTERROMPE
                            // N -> NAO INTERROMPE
                            if (bean4.getTipo().equals(1)) {
                                for (int i = 1; i < 24; i++) {
                                    lines = lines.concat("00:00").concat("       ");
                                }
                            } else {
                                String aux = bean4.getHorarios();
                                int count = 0;
                                for (int i = 0; i < aux.length(); i += 12) {
                                    String group = aux.substring(i, i + 12);
                                    String xoun = Integer.parseInt(group.substring(4, 5)) == 1 ? "X" : "N";

                                    //horario
                                    lines = lines.concat(group.substring(0, 2)).concat(":").concat(group.substring(0, 2));

                                    // segunda
                                    lines = lines.concat(Integer.parseInt(group.substring(5, 6)) == 1 ? xoun : " ");
                                    // terca
                                    lines = lines.concat(Integer.parseInt(group.substring(6, 7)) == 1 ? xoun : " ");
                                    // quarta
                                    lines = lines.concat(Integer.parseInt(group.substring(7, 8)) == 1 ? xoun : " ");
                                    // quinta
                                    lines = lines.concat(Integer.parseInt(group.substring(8, 9)) == 1 ? xoun : " ");
                                    // sexta
                                    lines = lines.concat(Integer.parseInt(group.substring(9, 10)) == 1 ? xoun : " ");
                                    // sabado
                                    lines = lines.concat(Integer.parseInt(group.substring(10, 11)) == 1 ? xoun : " ");
                                    // domingo
                                    lines = lines.concat(Integer.parseInt(group.substring(11, 12)) == 1 ? xoun : " ");

                                    count++;
                                }

                                for (int i = count; i <= 24; i++) {
                                    lines = lines.concat("00:00").concat("       ");
                                }
                            }

                            // Dias alternados                            
                            lines = lines.concat("nao");

                            // Data (dd/mm/aa)                            
                            lines = lines.concat(bean1.getData().substring(0, 6).concat(bean1.getData().substring(8, 10)));

                            // data da ultima execução                     
                            lines = lines.concat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

                            // tempo total da música                      
                            lines = lines.concat("00:00:00");

                            // qtde de player total                     
                            lines = lines.concat("0");

                            // data vencimento (dd/mm/aa)                 
                            lines = lines.concat(bean4.getDvencimento().substring(0, 6).concat(bean4.getDvencimento().substring(8, 10)));

                            // Arquivo dependencia 1                 
                            lines = lines.concat(Utilities.absoluteLeftPad("", 30, ""));

                            // Arquivo dependencia 2                 
                            lines = lines.concat(Utilities.absoluteLeftPad("", 30, ""));

                            // Arquivo dependencia 3                 
                            lines = lines.concat(Utilities.absoluteLeftPad("", 30, ""));

                            // frame inicial          
                            lines = lines.concat(Utilities.absoluteLeftPad("0", 8, ""));

                            // frame final                                
                            lines = lines.concat(Utilities.absoluteLeftPad("0", 8, ""));

                            // msg                                
                            lines = lines.concat(Utilities.absoluteLeftPad("", 40, ""));

                            // sem som                                
                            lines = lines.concat("0");

                            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", bean1.getCliente()).findOne();
                            String path = dados.getLocalDestinoExp();

                            if (path.endsWith("/")) {
                                path = path.substring(0, path.length() - 1);
                            }
                            path = path.concat("/ENVIO/");

                            String lojas = "";
                            String lojasbr = "";

                            if (!bean4.getUnidades().trim().replaceAll("\\s", "").isEmpty()) {
                                String[] idstrUnidadeList = bean4.getUnidades().trim().replaceAll("\\s", "").replace(".", "-").split("-");
                                for (String idstrUnidade : idstrUnidadeList) {
                                    ClienteBean unidade = repository.find(ClienteBean.class, Integer.parseInt(idstrUnidade));
                                    if (null != unidade && !unidade.getCodigoInterno().trim().replaceAll("\\s", "").isEmpty()) {
                                        lojas = lojas.concat(lojasbr).concat(unidade.getCodigoInterno());
                                        lojasbr = "\r\n";
                                    }
                                }
                            }

                            NtlmPasswordAuthentication auth = Utilities.getAuthSmbDefault();
                            if (path.contains("192.168.1.249")) {
                                auth = Utilities.getAuthSmbDefault1921681249();
                            }

                            SmbFile dir = new SmbFile(path, auth);

                            if (dir.exists()) {
                                SmbFile file = new SmbFile(path.concat("comercial.exp"), auth);

                                if (file.exists()) {
                                    lines = "\r".concat(lines);
                                } else {
                                    file.createNewFile();
                                }

                                SmbFileOutputStream sfos = new SmbFileOutputStream(file, true);
                                IOUtils.write(lines, sfos);
                                sfos.close();

                                file = new SmbFile(path.concat("lojas.txt"), auth);
                                sfos = new SmbFileOutputStream(file);
                                IOUtils.write(lojas, sfos);
                                sfos.close();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (SmbException e) {
                            e.printStackTrace();
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Get
//    @Restrict
    @Path("/ordem-servico/categorias/{idcliente}")
    public void categorias(Integer idcliente) {
        List<AudiostoreCategoriaBean> list = repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", idcliente).eq("tipo", new Short("2")).orderAsc("categoria").findAll();
        result.use(Results.json()).from(list).recursive().serialize();
    }

    @Post
//    @Restrict
    @Path("/ordem-servico/categorias/{idcliente}")
    public void categorias(String dados) {

        try {
            String catStrArr[] = dados.split("@");
            for (String catStr : catStrArr) {
                Integer codigo = Integer.parseInt(catStr.split("-")[0]);
                Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(catStr.split("-")[1]);
                Date d2 = new SimpleDateFormat("dd/MM/yyyy").parse(catStr.split("-")[2]);

                AudiostoreCategoriaBean bean = repository.find(AudiostoreCategoriaBean.class, codigo);
                bean.setDataInicio(d1);
                bean.setDataFinal(d2);
                repository.save(bean);
            }
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Atualizado com sucesso.")).recursive().serialize();
        } catch (ParseException e) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Lamento, não foi possivel atualizar.")).recursive().serialize();
        }
    }
}
