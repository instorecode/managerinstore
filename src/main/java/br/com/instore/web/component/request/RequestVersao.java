package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.VersaoJSON;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import br.com.instore.core.orm.bean.VersaoBean;
import br.com.instore.core.orm.Query;

@Component
@RequestScoped
public class RequestVersao implements java.io.Serializable {

    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;

    public RequestVersao(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public void beanList(Integer page, Integer rows, Integer id, Date dataCriacao, String nome, String descricao, Integer idProjeto, String linkSvn, Boolean download) {
        VersaoJSON json = new VersaoJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<VersaoBean> lista = new ArrayList<VersaoBean>();
        
        Query q1 = repository.query(VersaoBean.class);
        Query q2 = repository.query(VersaoBean.class);
        
        if(null != id && id > 0){
            q1.eq("idversao", id);
            q2.eq("idversao", id);
            json.setId(id.toString());
        }
        
        if(null != dataCriacao ){
            
        }
        
        if(null != nome && !nome.isEmpty()){
            q1.ilikeAnyWhere("nome", nome);
            q2.ilikeAnyWhere("nome", nome);
            json.setNome(nome);
        }
        
        if(null != descricao && !descricao.isEmpty()){
            q1.ilikeAnyWhere("descricao", descricao);
            q2.ilikeAnyWhere("descricao", descricao);
            json.setDescricao(descricao);            
        }

    }

}
