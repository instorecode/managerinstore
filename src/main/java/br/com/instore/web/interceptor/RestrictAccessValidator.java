package br.com.instore.web.interceptor;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.FuncionalidadeBean;
import br.com.instore.core.orm.bean.PerfilBean;
import br.com.instore.core.orm.bean.PerfilUsuarioBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.core.orm.bean.property.Funcionalidade;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.controller.HomeController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.com.caelum.vraptor.ioc.RequestScoped;
import javax.servlet.http.HttpServletRequest;

@Intercepts
@RequestScoped
public class RestrictAccessValidator implements Interceptor {

    private HttpServletRequest request;
    private SessionUsuario sessionUsuario;
    private Result result;
    private SessionRepository repository;
    private HttpServletRequest httpServletRequest;

    public RestrictAccessValidator(HttpServletRequest request, SessionUsuario sessionUsuario, Result result, SessionRepository repository, HttpServletRequest httpServletRequest) {
        this.request = request;
        this.sessionUsuario = sessionUsuario;
        this.result = result;
        this.repository = repository;
        this.httpServletRequest = httpServletRequest;
    }
    
    public boolean accepts(ResourceMethod method) {
        return method.containsAnnotation(Restrict.class);
    }

    public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
        Path path = method.getMethod().getAnnotation(Path.class);
        if (null != path && sessionUsuario.isLogado()) {

            if (sessionUsuario.getUsuarioBean().getSenha().equals("202cb962ac59075b964b07152d234b70") && !"/minha-senha".equals(path.value()[0])) {
                result.redirectTo(HomeController.class).minhaSenha();
            }

            result.include("machine_id", request.getRemoteAddr().replace(".", "").replace(":", "") + new SimpleDateFormat("ddMMyyyy").format(new Date()));
            FuncionalidadeBean f = current(path.value()[0]);

            if ("/dashboard".equals(path.value()[0]) || "/meus-dados".equals(path.value()[0]) || "/minha-senha".equals(path.value()[0])) {
                result.include("currentFuncionalidadeBean", f);
                result.include("menu", constructMenu(null, path.value()[0]));
                result.include("funcionalidadeBeanList", constructMenuChild(f));
                loadClienteMatriz();
                carregarPerfil();
                result.include("funcionalidadeBeanAtual", f);
                stack.next(method, resourceInstance);

            } else if ("/sair".equals(path.value()[0])) {
                stack.next(method, resourceInstance);
            } else {
                if (null != f) {
                    result.include("currentFuncionalidadeBean", f);
                    result.include("menu", constructMenu(null, path.value()[0]));
                    result.include("funcionalidadeBeanList", constructMenuChild(f));
                    if (null != sessionUsuario.getCliente() || urlLivres(path) ) {

                        loadClienteMatriz();
                        carregarPerfil();
                        result.include("funcionalidadeBeanAtual", f);
                        stack.next(method, resourceInstance);
                    } else {
                        result.redirectTo(HomeController.class).dashboard(path.value());
                    }
                } else {
                    result.redirectTo(HomeController.class).page404();
                }
            }
        } else {
//            for (Cookie cookie : httpServletRequest.getCookies()) {
//                if (cookie.getName().equals("managerinstore_machine_userck")) {
//                    Integer id = Integer.parseInt(cookie.getValue());
//
//                    if (requestRepository.query(UsuarioBean.class).eq(Usuario.IDUSUARIO, id).count() > 0) {
//                        UsuarioBean usuario = requestRepository.query(UsuarioBean.class).eq(Usuario.IDUSUARIO, id).findOne();
//                        usuario.getPerfilBeanList();
//                        sessionUsuario.setUsuarioBean(usuario);
//                        sessionUsuario.setLogado(true);
//                        requestRepository.setUsuario(sessionUsuario.getUsuarioBean());
//                    } else {
//                        result.redirectTo(HomeController.class).index();
//                    }
//                }
//            }
            result.redirectTo(HomeController.class).index();
        }
    }
    
    public void carregarPerfil() {
        List<PerfilUsuarioBean> perfilUsuarioBeanList = repository.query(PerfilUsuarioBean.class).eq("usuario.idusuario", sessionUsuario.getUsuarioBean().getIdusuario()).findAll();
        
        List<Integer> idList = new ArrayList<Integer>();
        
        for (PerfilUsuarioBean b : perfilUsuarioBeanList) {
            idList.add(b.getPerfil().getIdperfil());
        }
        
        List<PerfilBean> perfilBeanList = repository.query(PerfilBean.class).in("idperfil", idList.toArray(new Integer[idList.size()])).findAll();
        
        result.include("perfilBeanList", perfilBeanList);
    }
    
    public void carregarFuncionalidadeAtual(String map) {
        FuncionalidadeBean funcionalidadeBeanAtual = repository.query(FuncionalidadeBean.class).eq("mappingId", map).findOne();
        result.include("funcionalidadeBeanAtual", funcionalidadeBeanAtual);
    }
    
    public boolean urlLivres(Path path) {
        if("/clientes".equals(path.value()[0])) {
            return true;
        }
        
        if("/cliente/cadastrar".equals(path.value()[0])) {
            return true;
        }
        
        if(path.value()[0].contains("/cliente/atualizar/")) {
            return true;
        }
        
        return false;
    }

    public FuncionalidadeBean current(String currentMappinId) {

        String q = "";
        q = "select \n"
                + "    count(*) as size\n"
                + "from\n"
                + "    perfil_funcionalidade\n"
                + "left join perfil_usuario using(idperfil)\n"
                + "inner join funcionalidade using(idfuncionalidade)\n"
                + "where mapping_id = '" + currentMappinId + "' and idusuario = " + sessionUsuario.getUsuarioBean().getIdusuario() + " \n group by idfuncionalidade";

        if (repository.query(q).executeSQLCount() > 0) {
            FuncionalidadeBean fb = repository.query(FuncionalidadeBean.class).eq(Funcionalidade.MAPPING_ID, currentMappinId).findOne();
            return fb;
        } else {
            return null;
        }
    }

    class Each {

        public Object size;
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

    public String constructMenu(Integer parente, String currentMappinId) {
        String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String html = "";

        if (parente == null) {
            parente = 0;
        }

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
                + "     and visivel = 1\n"
                + "group by funcionalidade.idfuncionalidade order by funcionalidade.nome asc";

        // pega funcionalidades ROOT
        List<FuncionalidadeBean> funcionalidadeBeanList = repository.query(queries).executeSQL(FuncionalidadeBean.class);
        if (funcionalidadeBeanList != null) {
            for (FuncionalidadeBean f : funcionalidadeBeanList) {
                if (null != f.getMappingId() && !f.getMappingId().isEmpty()) {
                    if (currentMappinId.equals(f.getMappingId())) {
                        html += "<li class=\"active\"><a href=\"" + url + f.getMappingId() + "\"><i class=\"fa  " + f.getIcone() + "\"></i><span>" + f.getNome() + "</span></a></li>";
                    } else {
                        html += "<li><a href=\"" + url + f.getMappingId() + "\"><i class=\"fa  " + f.getIcone() + "\"></i><span>" + f.getNome() + "</span></a></li>";
                    }
                } else {

                    html += "<li><a href=\"" + url + f.getMappingId() + "\"><i class=\"fa  " + f.getIcone() + "\"></i><span>" + f.getNome() + "</span></a></li>";
                }
            }
        }

        return html;
    }

    public void perfilUsuarios() {
//        List<PerfilUsuarioBean> listaDePerfil = requestRepository.query(PerfilUsuarioBean.class).eq("usuario.idusuario", sessionUsuario.getUsuarioBean().getIdusuario()).findAll();
//        result.include("listaDePerfil", listaDePerfil);
        constructMenu();
    }

    public void clientesMatriz() {
        List<ClienteBean> lista = repository.query(ClienteBean.class).eq("parente", 0).eq("matriz", true).findAll();
        result.include("listaClientesMatriz", lista);
    }

    public void loadClienteMatriz() {
        result.include("atalhoClienteList", repository.query(ClienteBean.class).eq("parente", 0).eq("matriz", true).findAll());
    }

    public void constructMenu() {
        final List<PerfilUsuarioBean> perfilUsuarioBeanListAux = new ArrayList<PerfilUsuarioBean>();
        final List<PerfilUsuarioBean> perfilUsuarioBeanList = new ArrayList<PerfilUsuarioBean>();
        repository.query("select idperfil_usuario as 'idperfilUsuario' , idperfil as idperfil , idusuario as idusuario from perfil_usuario where idusuario = " + sessionUsuario.getUsuarioBean().getIdusuario()).executeSQL(new br.com.instore.core.orm.Each() {
            private Integer idperfilUsuario;
            private Integer idperfil;
            private Integer idusuario;

            @Override
            public void each() {
                PerfilUsuarioBean pub = new PerfilUsuarioBean();
                pub.setIdperfilUsuario(idperfilUsuario);
                pub.setPerfil(new PerfilBean(idperfil));
                pub.setUsuario(new UsuarioBean(idusuario));
                perfilUsuarioBeanListAux.add(pub);
            }
        });

        for (final PerfilUsuarioBean pubbbb : perfilUsuarioBeanListAux) {
            repository.query("select idperfil , nome from perfil where idperfil = " + pubbbb.getPerfil().getIdperfil()).executeSQL(new br.com.instore.core.orm.Each() {
                private Integer idperfil;
                private String nome;

                @Override
                public void each() {
                    PerfilUsuarioBean pub = new PerfilUsuarioBean();
                    pub.setIdperfilUsuario(pubbbb.getIdperfilUsuario());
                    pub.setUsuario(new UsuarioBean(pubbbb.getUsuario().getIdusuario()));
                    PerfilBean p = new PerfilBean(idperfil);
                    p.setNome(nome);
                    pub.setPerfil(p);
                    perfilUsuarioBeanList.add(pub);
                }
            });
        }

        final List<PerfilBean> perfis = new ArrayList<PerfilBean>();

        for (PerfilUsuarioBean pub : perfilUsuarioBeanList) {
            pub.getPerfil().setFuncionalidadeBeanList(constructMenu(pub.getPerfil().getIdperfil()));
            perfis.add(pub.getPerfil());
        }
        
        result.include("perfis", perfis);
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
                + "     and perfil_funcionalidade.idperfil = " + idperfil + "\n"
                + "     and perfil_usuario.idperfil = " + idperfil + "\n"
                + "group by funcionalidade.idfuncionalidade order by funcionalidade.nome asc";

        // pega funcionalidades ROOT
        List<FuncionalidadeBean> funcionalidadeBeanList = repository.query(queries).executeSQL(FuncionalidadeBean.class);
        return funcionalidadeBeanList;
    }
}
