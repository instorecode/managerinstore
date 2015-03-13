package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.core.orm.bean.FuncionalidadeBean;
import br.com.instore.core.orm.bean.PerfilBean;
import br.com.instore.core.orm.bean.PerfilUsuarioBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.web.annotation.NotRestrict;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestUsuario;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.tools.AjaxResult;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController implements java.io.Serializable {
    
    @Inject
    private SessionRepository repository;
    @Inject
    private SessionUsuario sessionUsuario;
    @Inject
    private RequestUsuario requestUsuario;
    @Inject
    private Result result;
    @Inject
    private HttpServletRequest request;
    
    @Inject
    private HttpServletRequest httpServletRequest;
    
    public HomeController() {
    }

    public HomeController(SessionRepository repository, SessionUsuario sessionUsuario, RequestUsuario requestUsuario, Result result , HttpServletRequest request) {
        this.repository = repository;
        this.sessionUsuario = sessionUsuario;
        this.requestUsuario = requestUsuario;
        this.result = result;
        this.request = request;
    }    
    
    @Get
    @Path("/test")
    public void test() {
    }

    @Get
    @Path("/menu")
    public void menu() {
        List<PerfilUsuarioBean> perfilUsuarioBeanList = repository.query(PerfilUsuarioBean.class).eq("usuario.idusuario", sessionUsuario.getUsuarioBean().getIdusuario()).findAll();
        List<PerfilBean> perfis = new ArrayList<PerfilBean>();
        
        for (PerfilUsuarioBean pub : perfilUsuarioBeanList) {
            pub.getPerfil().setFuncionalidadeBeanList(constructMenu(pub.getPerfil().getIdperfil()));
            perfis.add(pub.getPerfil());
        }
        result.include("perfis", perfis);
    }
    
    @Get
    @Path("/404")
    public void page404() {
    }
    
    @Get
    @Path("/500")
    public void page500() {   
    }
    
    @Get
    @Path("/")
    @NotRestrict
    public void index() {
        boolean temEmail = false; 
    }
    
    @Post
    @Path("/")
    @NotRestrict
    public void index(String email, String senha) {
        requestUsuario.logIn(email, senha);
    }
    
    @Get
    @Path("/dashboard")
    @Restrict
    public void dashboard() {
        
    }
    
    @Post
    @Path("/dashboard")
    @Restrict
    public void dashboard(Integer idcliente) {
         ClienteBean c = (ClienteBean) repository.find(ClienteBean.class, idcliente);
         if(null != c) {
             sessionUsuario.setCliente(c);
             result.use(Results.json()).withoutRoot().from(1).recursive().serialize();
         } else {
             result.use(Results.json()).withoutRoot().from(0).recursive().serialize();
         }   
    }
    
    @Get
    @Path("/sair")
    @Restrict
    public void sair() {
        requestUsuario.logOut();
    }
    
    @Get
    @Path("/configuracao-interna")
    @Restrict
    public void configuracaoInterna() {
        result.include("configAppBean", repository.find(ConfigAppBean.class, 1));
    }
    
    @Post
    @Path("/configuracao-interna")
    @Restrict
    public void configuracaoInterna(ConfigAppBean configAppBean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(configAppBean != null && configAppBean.getId()!= null && configAppBean.getId() > 0) {
                repository.save(repository.marge(configAppBean));
            } else {
                repository.save(configAppBean);
            }
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar as configurações!")).recursive().serialize();
        }
    }
    
    @Get
    @Path("/meus-dados")
    @Restrict
    public void meusDados() {
        result.include("estadoBeanList", requestUsuario.estadoBeanList());
        result.include("usuarioBean", sessionUsuario.getUsuarioBean());
    }
   
    @Post
    @Path("/meus-dados")
    @Restrict
    public void meusDados(UsuarioBean usuarioBean) {
        requestUsuario.meusDados(usuarioBean);
    }
                        
    @Get
    @Path("/minha-senha")
    @Restrict
    public void minhaSenha() {
        
    }
   
    @Post
    @Path("/minha-senha")
    @Restrict
    public void minhaSenha(String senha_atual , String nova_senha, String conf_senha) {
        requestUsuario.minhaSenha(senha_atual , nova_senha , conf_senha);
    }
   
    @Get
    @Path("/plantao")
    public void plantao() {
        
    }
    
    
    public List<FuncionalidadeBean> constructMenu(int idperfil) {
        String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String html = "";
        Integer parente = 0;

        String queries = "";

        queries = "select \n"
                + "    funcionalidade.idfuncionalidade as idfuncionalidade,\n"
                + "    mapping_id as mappingId,\n"
                + "    nome,\n"
                + "    icone,\n"
                + "    parente,\n"
                + "    visivel as visivel\n"
                + "from funcionalidade\n"
                + "left join perfil_funcionalidade using(idfuncionalidade) \n"
                + "left join perfil_usuario using(idperfil) \n"
                + "where\n"
                + "    parente = " + parente + "\n"
                + "	and idusuario = " + sessionUsuario.getUsuarioBean().getIdusuario() + "\n"
                + "     and visivel = 1"
                + "     and perfil_funcionalidade.idperfil = "+idperfil+"\n"
                + "     and perfil_usuario.idperfil = "+idperfil+"\n"
                + "group by funcionalidade.idfuncionalidade order by funcionalidade.nome asc";

        // pega funcionalidades ROOT
        List<FuncionalidadeBean> funcionalidadeBeanList = repository.query(queries).executeSQL(FuncionalidadeBean.class);
        return funcionalidadeBeanList;
    }
    
    
    public List<FuncionalidadeBean> constructMenuChild(FuncionalidadeBean fb) {
        if (null == fb) {
            return new ArrayList<FuncionalidadeBean>();
        }

        final List<Integer> id_s = new ArrayList<Integer>();
        String query = "select \n"
                + "    funcionalidade.idfuncionalidade as id , '' as param\n"
                + "from\n"
                + "    funcionalidade\n"
                + "inner join perfil_funcionalidade using(idfuncionalidade)\n"
                + "inner join perfil_usuario using(idperfil)\n"
                + "\n"
                + "where 	funcionalidade.parente = " + fb.getIdfuncionalidade() + "\n"
                + "		and perfil_usuario.idusuario = " + sessionUsuario.getUsuarioBean().getIdusuario();

        repository.query(query).executeSQL(new br.com.instore.core.orm.Each() {
            Integer id;
            String param;

            @Override
            public void each() {
                id_s.add(id);
            }
        });

        if (!id_s.isEmpty()) {
            return repository.query(FuncionalidadeBean.class).in("idfuncionalidade", id_s.toArray(new Integer[id_s.size()])).findAll();
        } else {
            return new ArrayList<FuncionalidadeBean>();
        }
    }
}
