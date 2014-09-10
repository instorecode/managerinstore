package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestExp;
import javax.inject.Inject;


@Controller
public class ExpController implements java.io.Serializable {
    @Inject
    private RequestExp requestExp;
    
    @Get
    @Restrict
    @Path("/gerarexp")
    public void gerarExp(String tipo_exp , String orderBy, String idcliente) {
        requestExp.gerarExp(tipo_exp , orderBy , idcliente);
    }
}
