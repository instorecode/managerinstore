package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AjudaBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestAjuda;
import javax.inject.Inject;

@Controller
public class AjudaController implements java.io.Serializable{
    
    @Inject            
    private Result result;
    
    @Inject
    private RequestAjuda requestAjuda;
    
    public AjudaController(){
        
    }

    public AjudaController(Result result, RequestAjuda requestAjuda) {
        this.result = result;
        this.requestAjuda = requestAjuda;
    }
    
    @Get
    @Restrict
    @Path("/ajuda")
    public void listar (Boolean datajson, Boolean view ,Boolean funcionalidades, Integer page, Integer rows, Integer id, String titulo , String idfuncionalidade, Integer pk){
     
      if(null != datajson && datajson){
          requestAjuda.beanList(page, rows, id, titulo,idfuncionalidade);
      }
      
      if(null != view && view){
         result.use(Results.json()).withoutRoot().from(requestAjuda.ajudaDTO(pk)).recursive().serialize();
      }    
    
      if(null != funcionalidades && funcionalidades){
         result.use(Results.json()).withoutRoot().from(requestAjuda.ajudaBeanList()).recursive().serialize();
      }
    }
    
    @Get
    @Restrict
    @Path("/ajuda/cadastrar")
    public void cadastrar(){
        result.include("isPageCadastro", true);
        result.include("ajudaBeanList", requestAjuda.listaFuncionalidades());
    }
    
    @Post
    @Restrict
    @Path("/ajuda/cadastrar")
    public void cadastar (AjudaBean ajudaBean ){
        requestAjuda.salvar(ajudaBean);
    }
    
    @Get
    @Restrict
    @Path("/ajuda/remover/{id}")
    public void remover (Integer id ){
      result.include("ajudaBean", requestAjuda.bean(id));
    }

    @Post
    @Restrict
    @Path("/ajuda/remover/{id}")
    public void remover (Integer id, String param ){
        requestAjuda.bean(id);
    }
    
    @Get
    @Restrict
    @Path("/ajuda/atualizar/{id}")
    public void cadastar (Integer id ){
      result.include("isPageCadastro", false);  
      result.include("ajudaBean", requestAjuda.bean(id));
      result.include("ajudaBeanList", requestAjuda.listaFuncionalidades());      
    }

    @Post
    @Restrict
    @Path("/ajuda/atualizar/{id}")
    public void cadastar (Integer id, AjudaBean ajudaBean ){
        requestAjuda.salvar(ajudaBean);
    }
    
    
    
    
}
