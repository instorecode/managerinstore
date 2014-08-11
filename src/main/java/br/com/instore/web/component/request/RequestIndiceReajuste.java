package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.IndiceReajusteBean;
import br.com.instore.core.orm.bean.IndiceReajusteHistoricoBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.IndiceReajusteDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestIndiceReajuste implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    
    @Inject
    private Result result;
    
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestIndiceReajuste() {
    }

    public RequestIndiceReajuste(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }
    
    public List<IndiceReajusteDTO> beanList() {
        List<IndiceReajusteBean> lista = new ArrayList<IndiceReajusteBean>();
        List<IndiceReajusteDTO> lista2 = new ArrayList<IndiceReajusteDTO>();
        lista = repository.query(IndiceReajusteBean.class).findAll();
        for (IndiceReajusteBean bean : lista) {
            IndiceReajusteDTO dto = new IndiceReajusteDTO();
            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setDescricao(bean.getDescricao());
            dto.setTipo(bean.getTipo());
            
            dto.setPercentual(bean.getPercentual());
            lista2.add(dto);
        }
        return lista2;
    }

    public IndiceReajusteBean bean(Integer id) {
        return repository.find(IndiceReajusteBean.class, id);
    }

    public void salvar(IndiceReajusteBean bean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            IndiceReajusteHistoricoBean historico = new IndiceReajusteHistoricoBean();
            historico.setData(new Date());
            historico.setUsuario(sessionUsuario.getUsuarioBean());
            String texto = "";
            
            if(null == bean.getDescricao() && bean.getDescricao().isEmpty()) {
                bean.setDescricao("");
            }
            
            if(bean != null && bean.getId()!= null && bean.getId() > 0) {
                historico.setIndiceReajuste(bean);
                IndiceReajusteBean beanAux = repository.find(IndiceReajusteBean.class, bean.getId());
                texto = "Indice alterado de " + beanAux.getPercentual() + " para " + bean.getPercentual();
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
                historico.setIndiceReajuste(bean);
                
                texto = "Inclusão de indice de reajuste.";
            }
            
            historico.setTexto(texto);
            repository.save(historico);
            
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
            
            repository.query("delete from indice_reajuste_historico where indice_reajuste = " + id).executeSQLCommand();
            
            IndiceReajusteBean bean = repository.marge((IndiceReajusteBean) repository.find(IndiceReajusteBean.class, id));
            repository.delete(bean);
            
            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Origem da ocorrencia removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a origem da ocorrencia!")).recursive().serialize();
        }
    }
    
    public boolean podeRemover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            if(repository.query(DadosClienteBean.class).eq("indiceReajuste.id", id).count() <= 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
