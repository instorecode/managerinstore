package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestDumpParadox;

@Resource
public class DumpParadoxController implements java.io.Serializable {

    private Result result;
    private RequestDumpParadox requestDumpParadox;

    public DumpParadoxController(Result result, RequestDumpParadox requestDumpParadox) {
        this.result = result;
        this.requestDumpParadox = requestDumpParadox;        
    }

    @Get
    @Restrict
    @Path("/dump-paradox")
    public void gerarDump() {

    }

    @Post
    @Restrict
    @Path("/dump-paradox")
    public void gerarDump(String path) {
        requestDumpParadox.gerarDump(path);
    }

}
