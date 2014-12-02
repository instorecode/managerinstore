package br.com.instore.web.interceptor;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.FuncionalidadeBean;
import br.com.instore.core.orm.bean.PerfilUsuarioBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.core.orm.bean.property.Funcionalidade;
import br.com.instore.core.orm.bean.property.Usuario;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.controller.HomeController;
import br.com.instore.web.tools.Utilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Intercepts(after = HibernateSession.class)
@RequestScoped
@AcceptsWithAnnotations(Restrict.class)
public class RestrictAccessValidator {

    @Inject
    private HttpServletRequest request;
    @Inject
    private ControllerMethod controllerMethod;
    @Inject
    private SessionUsuario sessionUsuario;
    @Inject
    private Result result;
    @Inject
    private SessionRepository requestRepository;
    @Inject
    private HttpServletRequest httpServletRequest;

    @AroundCall
    public void intercept(SimpleInterceptorStack stack) {
        System.out.println("A SESSAO COMP EH NULA::"+(null == requestRepository));
        System.out.println("A SESSAO EH NULA::"+(null == requestRepository.getSession()));
        Path path = controllerMethod.getMethod().getAnnotation(Path.class);
        if (null != path && sessionUsuario.isLogado()) {

            if (sessionUsuario.getUsuarioBean().getSenha().equals("202cb962ac59075b964b07152d234b70") && !"/minha-senha".equals(path.value()[0]) )  {
                result.redirectTo(HomeController.class).minhaSenha();
            }

            result.include("machine_id", request.getRemoteAddr().replace(".", "").replace(":", "") + new SimpleDateFormat("ddMMyyyy").format(new Date()));
            FuncionalidadeBean f = current(path.value()[0]);

            if ("/dashboard".equals(path.value()[0]) || "/meus-dados".equals(path.value()[0]) || "/minha-senha".equals(path.value()[0])) {
                result.include("currentFuncionalidadeBean", f);
                result.include("menu", constructMenu(null, path.value()[0]));
                result.include("funcionalidadeBeanList", constructMenuChild(f));
                loadClienteMatriz();
                perfilUsuarios();
                stack.next();
            } else if ("/sair".equals(path.value()[0])) {
                stack.next();
            } else {
                if (null != f) {
                    result.include("currentFuncionalidadeBean", f);
                    result.include("menu", constructMenu(null, path.value()[0]));
                    result.include("funcionalidadeBeanList", constructMenuChild(f));
                    loadClienteMatriz();
                    perfilUsuarios();
                    stack.next();
                } else {
                    result.redirectTo(HomeController.class).index();
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

    public FuncionalidadeBean current(String currentMappinId) {

        String q = "";
        q = "select \n"
                + "    count(*) as size\n"
                + "from\n"
                + "    perfil_funcionalidade\n"
                + "left join perfil_usuario using(idperfil)\n"
                + "inner join funcionalidade using(idfuncionalidade)\n"
                + "where mapping_id = '" + currentMappinId + "' and idusuario = " + sessionUsuario.getUsuarioBean().getIdusuario() + " \n group by idfuncionalidade";

        if (requestRepository.query(q).executeSQLCount() > 0) {
            FuncionalidadeBean fb = requestRepository.query(FuncionalidadeBean.class).eq(Funcionalidade.MAPPING_ID, currentMappinId).findOne();
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

        requestRepository.query(query).executeSQL(new br.com.instore.core.orm.Each() {
            Integer id;
            String param;

            @Override
            public void each() {
                id_s.add(id);
            }
        });

        if (!id_s.isEmpty()) {
            return requestRepository.query(FuncionalidadeBean.class).in("idfuncionalidade", id_s.toArray(new Integer[id_s.size()])).findAll();
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
        List<FuncionalidadeBean> funcionalidadeBeanList = requestRepository.query(queries).executeSQL(FuncionalidadeBean.class);
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
    }

    public void clientesMatriz() {
        List<ClienteBean> lista = requestRepository.query(ClienteBean.class).eq("parente", 0).eq("matriz", true).findAll();
        result.include("listaClientesMatriz", lista);
    }

    public void loadClienteMatriz() {
        result.include("atalhoClienteList", requestRepository.query(ClienteBean.class).eq("parente", 0).eq("matriz", true).findAll());
    }
}
