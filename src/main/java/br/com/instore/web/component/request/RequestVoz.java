package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.core.orm.bean.VozBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.VozDTO;
import br.com.instore.web.dto.VozJSON;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestVoz implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestVoz() {
    }

    public RequestVoz(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> lista = new ArrayList<ClienteBean>();
        lista = repository.query(ClienteBean.class).findAll();
        return lista;
    }
    
    public void beanList(Integer page, Integer rows, Integer idvoz, String clienteNome, String genero, String tipo, String nome, String email, String tel) {
        VozJSON json = new VozJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 :rows);
        
        Integer offset = (page - 1) * rows;
        List<VozBean> lista = new ArrayList<VozBean>();
        
        Query q1 = repository.query(VozBean.class);
        Query q2 = repository.query(VozBean.class);
        
        if(null != idvoz && idvoz > 0){
            q1.eq("id", idvoz);
            q2.eq("id", idvoz);
            json.setIdvoz(idvoz);
        }
        
        if(null != clienteNome && !clienteNome.isEmpty()){
            q1.ilikeAnyWhere("cliente", clienteNome);
            q2.ilikeAnyWhere("cliente", clienteNome);
            json.setClienteNome(clienteNome);
        }
        
        if(null != genero && !genero.isEmpty()){
            q1.ilikeAnyWhere("genero", genero);
            q2.ilikeAnyWhere("genero", genero);
            json.setGenero(genero);            
        }
        
        if(null != tipo && !tipo.isEmpty()){
            q1.ilikeAnyWhere("tipo", tipo);
            q2.ilikeAnyWhere("tipo", tipo);
            json.setTipo(tipo);
        }
        
        if(null != nome && !nome.isEmpty()){
            q1.ilikeAnyWhere("nome", nome);
            q2.ilikeAnyWhere("nome", nome);
            json.setNome(nome);
        }
                
        if(null != email && !email.isEmpty()){
            q1.ilikeAnyWhere("email", email);
            q2.ilikeAnyWhere("email", email);
            json.setEmail(email);
        }        
        
        if(null != tel && !tel.isEmpty()){
            q1.ilikeAnyWhere("telefone", tel);
            q2.ilikeAnyWhere("telefone", tel);
            json.setTel(tel);
        }
        
        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();
        
        json.setPage(page);
        json.setSize(size);
        
        List<VozDTO> rowsList = new ArrayList<VozDTO>();
        for (VozBean bean : lista){
            
            VozDTO dto = new VozDTO();
            dto.setIdvoz(bean.getIdvoz());
            dto.setClienteNome(bean.getCliente().getNome());
            dto.setGenero((bean.isGenero() == true)? "Masculino" : "Feminino");
            dto.setTipo("A");
            dto.setNome(bean.getNome());
            dto.setEmail(bean.getEmail());
            dto.setTel(bean.getTel());
            
            rowsList.add(dto);           
        }
        
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public VozBean bean(Integer id) {
        return repository.find(VozBean.class, id);
    }

    public void salvar(VozBean bean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            if(bean != null && bean.getIdvoz()!= null && bean.getIdvoz() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }
    
    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            VozBean bean = repository.marge((VozBean) repository.find(VozBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Voz removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a voz!")).recursive().serialize();
        }
    }
}
