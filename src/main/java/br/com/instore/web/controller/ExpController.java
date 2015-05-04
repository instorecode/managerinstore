package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestExp;
import javax.inject.Inject;


@Resource
public class ExpController implements java.io.Serializable {
    private RequestExp requestExp;

    public ExpController(RequestExp requestExp) {
        this.requestExp = requestExp;
    }
    
    
    
    @Get
    @Restrict
    @Path("/gerarexp")
    public void gerarExp(String tipo_exp , String orderBy, String idcliente) {
        requestExp.gerarExp(tipo_exp , orderBy , idcliente);
    }
}
