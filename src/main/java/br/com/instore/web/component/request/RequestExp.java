package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.instore.core.orm.RepositoryViewer;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.AudiostoreComercialBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.AudiostoreMusicaBean;
import br.com.instore.core.orm.bean.AudiostoreProgramacaoBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestExp implements java.io.Serializable {
    @Inject
    private Result result;
    
    @Inject
    private RepositoryViewer rv;
    
    public void gerarExp(String tipo_exp) {
        if(null == tipo_exp || tipo_exp.isEmpty()) {
            tipo_exp = "gravadora";
        }
        result.include("tipo_exp", tipo_exp);
        
        if(tipo_exp.equals("gravadora")) {
            result.include("lista", rv.query(AudiostoreGravadoraBean.class).findAll());
            result.include("total_lista", rv.query(AudiostoreGravadoraBean.class).count());
        }
        
        result.include("total_lista_gravadora", rv.query(AudiostoreGravadoraBean.class).count());
        result.include("total_lista_categoria", rv.query(AudiostoreCategoriaBean.class).count());
        result.include("total_lista_programacao", rv.query(AudiostoreProgramacaoBean.class).count());
        result.include("total_lista_comercial", rv.query(AudiostoreComercialBean.class).count());
        result.include("total_lista_musica", rv.query(AudiostoreMusicaBean.class).count());
        
    }
}
