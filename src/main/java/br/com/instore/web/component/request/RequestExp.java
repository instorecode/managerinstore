package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.instore.core.orm.RepositoryViewer;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import br.com.instore.core.orm.bean.ClienteBean;
import java.util.ArrayList;
import java.util.List;
import br.com.caelum.vraptor.ioc.RequestScoped;
import javax.inject.Inject;

@Component
@RequestScoped
public class RequestExp implements java.io.Serializable {

    private Result result;
    private RepositoryViewer rv;

    public void gerarExp(String tipo_exp, String orderBy, String idcliente) {
        if (null == idcliente || idcliente.isEmpty()) {
            result.include("clienteBeanList", rv.query(ClienteBean.class).eq("matriz", true).findAll());
        } else {
            if (null == tipo_exp || tipo_exp.isEmpty()) {
                tipo_exp = "gravadora";
            }
            result.include("tipo_exp", tipo_exp);

            if (tipo_exp.equals("gravadora")) {
                paginaGravadora(orderBy);
            }

            result.include("total_lista_gravadora", rv.query(AudiostoreGravadoraBean.class).count());
            result.include("total_lista_categoria", rv.query(AudiostoreCategoriaBean.class).count());
            result.include("total_lista_programacao", rv.query(AudiostoreProgramacaoBean.class).count());
            result.include("total_lista_comercial", rv.query(AudiostoreComercialBean.class).count());
            result.include("total_lista_musica", rv.query(AudiostoreMusicaBean.class).count());
            result.include("idcliente", idcliente);
        }

    }

    private void paginaGravadora(String orderBy) {
        result.include("lista", rv.query(AudiostoreGravadoraBean.class).findAll());
        result.include("total_lista", rv.query(AudiostoreGravadoraBean.class).count());

        // order by List
        List<String> orderByList = new ArrayList<String>();
        orderByList.add("Nome ascendente");
        orderByList.add("Nome descendente");
        result.include("orderByList", orderByList);

    }
}
