package br.com.instore.web.interceptor;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.bean.FuncionalidadeBean;
import br.com.instore.core.orm.bean.property.Funcionalidade;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.controller.HomeController;
import br.com.instore.web.tools.Utilities;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

    @AroundCall
    public void intercept(SimpleInterceptorStack stack) {
        Path path = controllerMethod.getMethod().getAnnotation(Path.class);
        if (null != path && sessionUsuario.isLogado()) {
            result.include("machine_id", request.getRemoteAddr().replace(".", "").replace(":", "")+new SimpleDateFormat("ddMMyyyy").format(new Date()));
            FuncionalidadeBean f = current(path.value()[0]);

            if ("/dashboard".equals(path.value()[0])) {
                result.include("currentFuncionalidadeBean", f);
                result.include("menu", constructMenu(null, path.value()[0]));
                result.include("funcionalidadeBeanList", constructMenuChild(f));

                stack.next();
            } else if ("/sair".equals(path.value()[0])) {
                stack.next();
            } else {
                if (null != f) {
                    result.include("currentFuncionalidadeBean", f);
                    result.include("menu", constructMenu(null, path.value()[0]));
                    result.include("funcionalidadeBeanList", constructMenuChild(f));

                    stack.next();
                } else {
                    result.redirectTo(HomeController.class).index();
                }
            }
        } else {
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
        List<FuncionalidadeBean> funcionalidadeBeanList = requestRepository.query(FuncionalidadeBean.class).eq(Funcionalidade.PARENTE, fb.getIdfuncionalidade()).findAll();
        return funcionalidadeBeanList;
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
}
