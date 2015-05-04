package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.DataValidator;
import br.com.instore.core.orm.DataValidatorException;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.AjudaBean;
import br.com.instore.core.orm.bean.FuncionalidadeBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AjudaDTO;
import br.com.instore.web.dto.AjudaJSON;
import br.com.instore.web.tools.AjaxResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@RequestScoped
public class RequestAjuda implements Serializable {

    private SessionRepository repository;
    private Result result;
    private SessionUsuario sessionUsuario;

    public RequestAjuda(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public void salvar(AjudaBean ajudaBean) {
        try {
            if (null == ajudaBean) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Todos os campos devem ser preenchidos!")).recursive().serialize();
                return;
            }

            if (ajudaBean.getTexto() == null || ajudaBean.getTexto().trim().isEmpty()) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe o texto!")).recursive().serialize();
                return;
            }

            if (ajudaBean.getTexto().length() <= 3) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe no mínimo 3 caracteres!")).recursive().serialize();
                return;
            }

            if (ajudaBean.getTitulo() == null || ajudaBean.getTitulo().trim().isEmpty()) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe o titulo!")).recursive().serialize();
                return;
            }

            if (ajudaBean.getTitulo().length() <= 3) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe no mínimo 3 caracteres!")).recursive().serialize();
                return;
            }

            if (ajudaBean.getFuncionalidade() == null) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe uma funcionalidade!")).recursive().serialize();
                return;
            }

            DataValidator.beanValidator(ajudaBean);

            if (null != ajudaBean && null != ajudaBean.getId() && ajudaBean.getId() > 0) {
                repository.save(repository.marge(ajudaBean));
            } else {
                repository.save(ajudaBean);
            }

            repository.setUsuario(sessionUsuario.getUsuarioBean());

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();

        } catch (DataValidatorException e) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, e.getMessage())).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }

    }

    public List<FuncionalidadeBean> funcionalidadeBeanList(){
        final List<FuncionalidadeBean> funcionalidadesBeanList = new ArrayList<FuncionalidadeBean>();
        repository.query("select idfuncionalidade , nome from funcionalidade ").executeSQL(new Each() {
            public Integer idfuncionalidade;
            public String nome;
            @Override
            public void each() {
                FuncionalidadeBean f = new FuncionalidadeBean();
                f.setIdfuncionalidade(idfuncionalidade);
                f.setNome(nome);
                
                funcionalidadesBeanList.add(f);
            }
        });
        
        return  funcionalidadesBeanList;           
    }
    
    public List<FuncionalidadeBean> listaFuncionalidades() {
        List<FuncionalidadeBean> listaFuncionalidade = listaFuncionalidade = repository.query(FuncionalidadeBean.class).findAll();
        return listaFuncionalidades();
    }
   
    public void beanList(Integer page, Integer rows, Integer id, String titulo, Integer idfuncionalidade) {
        AjudaJSON json = new AjudaJSON();
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<AjudaBean> lista = new ArrayList<AjudaBean>();

        Query q1 = repository.query(AjudaBean.class);
        Query q2 = repository.query(AjudaBean.class);

        if (null != id && id > 0) {
            q1.eq("codigo", id);
            q2.eq("codigo", id);
            json.setId(id);
        }

        if (null != titulo && !titulo.trim().isEmpty()) {
            q1.ilikeAnyWhere("titulo", titulo);
            q2.ilikeAnyWhere("titulo", titulo);
            json.setTitulo(titulo);
        }
        
        if (null != idfuncionalidade && idfuncionalidade > 0) {
            q1.eq("funcionalidade.idfuncionalidade", idfuncionalidade);
            q2.eq("funcionalidade.idfuncionalidade", idfuncionalidade);
            json.setIdfuncionalidade(idfuncionalidade.toString());
        }

        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();

        json.setPage(page);
        json.setSize(size);

        List<AjudaDTO> rowsList = new ArrayList<AjudaDTO>();        
        for(AjudaBean bean : lista){
            AjudaDTO dto = new AjudaDTO();            
            dto.setId(bean.getId());
            dto.setTitulo(bean.getTitulo());
            dto.setTexto(bean.getTexto());
            dto.setNome(bean.getFuncionalidade().getNome());
            dto.setIdfuncionalidade(bean.getFuncionalidade().getIdfuncionalidade());            
            rowsList.add(dto);
        }
        
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
     }
   
    public AjudaBean bean(Integer id){
        return repository.find(AjudaBean.class, id);
    }
    
    public AjudaDTO ajudaDTO(Integer id){        
        AjudaDTO dto = new AjudaDTO();
        AjudaBean bean = repository.find(AjudaBean.class, id);       
        if(null != bean) { 
            dto.setId(bean.getId());
            dto.setNome(bean.getFuncionalidade().getNome());
            dto.setIdfuncionalidade(bean.getFuncionalidade().getIdfuncionalidade());
            dto.setTexto(bean.getTexto());
            dto.setTitulo(bean.getTitulo());               
        }
        return dto;
    }
        
    
    public void remover (Integer id){
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            
            AjudaBean bean = repository.marge((AjudaBean) repository.find(AjudaBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Ajuda removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a ajuda!")).recursive().serialize();
        }
    }
}
