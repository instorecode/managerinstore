package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.CepBean;
import br.com.instore.core.orm.bean.property.Cep;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.tools.CepResult;
import br.com.instore.web.tools.CepService;
import javax.inject.Inject;

@Controller
public class UtilidadesController implements java.io.Serializable {
    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;

    public UtilidadesController() {
    }

    public UtilidadesController(SessionRepository repository, Result result) {
        this.repository = repository;
        this.result = result;
    }
    
    @Get
    @Path("/utilidades/cepload")
    public void cepload(String cep) {
        CepResult res = new CepResult();
        if( repository.query(CepBean.class).eq(Cep.NUMERO, cep).count() > 0 ) {
            CepBean bean = repository.query(CepBean.class).eq(Cep.NUMERO, cep).findOne();
            res.setUf(bean.getBairro().getCidade().getEstado().getSigla());
            res.setCidade(bean.getBairro().getCidade().getNome());
            res.setBairro(bean.getBairro().getNome());
            res.setLogradouro(bean.getBairro().getRua());
        } else {
            res = CepService.get(cep);
        }
        result.use(Results.json()).withoutRoot().from(res).recursive().serialize();
        
    }
}
