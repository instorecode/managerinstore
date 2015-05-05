package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.OrdenServicoObsBean;
import br.com.instore.core.orm.bean.OrdenServicoParte1Bean;
import br.com.instore.core.orm.bean.OrdenServicoParte2Bean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.web.component.request.RequestAjuda;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Resource
public class OrdenServicoController implements java.io.Serializable {
    private Result result;
    private RequestAjuda requestAjuda;
    private SessionRepository repository;
    private SessionUsuario sessionUsuario;

    public OrdenServicoController(Result result, RequestAjuda requestAjuda, SessionRepository repository, SessionUsuario sessionUsuario) {
        this.result = result;
        this.requestAjuda = requestAjuda;
        this.repository = repository;
        this.sessionUsuario = sessionUsuario;
        repository.setUsuario(sessionUsuario.getUsuarioBean());
    }

    

    @Get
//    @Restrict
    @Path("/orden-servico/cadastro")
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
    
    @Post
    @Path("/orden-servico/salvar-parte1")
    public void salvarParte1(Integer cliente , String nome , String quemSolicitou , String quandoSolicitou , String dataMaxDistr , Integer tipo) {
        
        OrdenServicoParte1Bean ospb = new OrdenServicoParte1Bean();
        ospb.setCliente(cliente);
        ospb.setNome(nome);
        ospb.setQuemSolicitou(quemSolicitou);
        ospb.setQuandoSolicitou(quandoSolicitou);
        ospb.setDataMaxDistribuicao(dataMaxDistr);
        ospb.setTipo(tipo);
        ospb.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
        
        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int)ospb.getId()).recursive().serialize();
    }
    
    @Post
    @Path("/orden-servico/salvar-parte2")
    public void salvarParte2(Integer fk , Integer locutor , String texto , String prazo) {
        
        OrdenServicoParte2Bean ospb = new OrdenServicoParte2Bean();
        ospb.setFk(fk);
        ospb.setLocutor(locutor);
        ospb.setTexto(texto);
        ospb.setPrazoLocucao(prazo);

        repository.save(ospb);
        repository.finalize();
        result.use(Results.json()).from((int)ospb.getId()).recursive().serialize();
    }
    
    @Post
    @Path("/orden-servico/salvar-obs")
    public void salvarObs(Integer fk , String data, String html, Integer tipo) {
        
        OrdenServicoObsBean obs = new OrdenServicoObsBean();
        obs.setFk(fk);
        obs.setData(data);
        obs.setHtml(html);
        obs.setTipo(tipo);
        obs.setUsuario(sessionUsuario.getUsuarioBean().getIdusuario());
        
        
        repository.save(obs);
        repository.finalize();
        result.use(Results.json()).from((int)obs.getId()).recursive().serialize();
    }
    
    @Get
//    @Restrict
    @Path("/orden-servico/visualizar-estrutura-das-programacoes")
    public void vep() {
    }
    
    @Get
//    @Restrict
    @Path("/orden-servico/lista")
    public void lista() {
    }
    
    private void gerarHashCode(String nome) {
        
    }

}
