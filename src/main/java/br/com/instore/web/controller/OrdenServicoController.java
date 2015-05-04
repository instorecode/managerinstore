package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.web.component.request.RequestAjuda;
import br.com.instore.web.component.session.SessionRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Resource
public class OrdenServicoController implements java.io.Serializable {
    private Result result;
    private RequestAjuda requestAjuda;
    private SessionRepository repository;

    public OrdenServicoController(Result result, RequestAjuda requestAjuda, SessionRepository repository) {
        this.result = result;
        this.requestAjuda = requestAjuda;
        this.repository = repository;
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

}
