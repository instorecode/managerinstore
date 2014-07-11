package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.LancamentoBean;
import br.com.instore.core.orm.bean.LancamentoCnpjBean;
import br.com.instore.core.orm.bean.property.Lancamento;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.LancamentoCnpjDTO;
import br.com.instore.web.dto.Relatorio1DTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RequestLancamentoCnpj implements java.io.Serializable {
 @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestLancamentoCnpj() {
    }

    public RequestLancamentoCnpj(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public List<LancamentoCnpjDTO> beanList() {
        List<LancamentoCnpjBean> lista = new ArrayList<LancamentoCnpjBean>();
        List<LancamentoCnpjDTO> lista2 = new ArrayList<LancamentoCnpjDTO>();
        lista = repository.query(LancamentoCnpjBean.class).findAll();
        for (LancamentoCnpjBean bean : lista) {
            LancamentoCnpjDTO dto = new LancamentoCnpjDTO(Utilities.leftPad(bean.getId()), bean.getNome());
            lista2.add(dto);
        }
        return lista2;
    }

    public LancamentoCnpjBean bean(Integer id) {
        return repository.find(LancamentoCnpjBean.class, id);
    }

    public void salvar(LancamentoCnpjBean bean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (bean != null && bean.getId() != null && bean.getId() > 0) {
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

            List<LancamentoBean> lista = repository.query(LancamentoBean.class).eq(Lancamento.LANCAMENTO_CNPJ, id).findAll();
            if(null != lista && !lista.isEmpty()) {
                for (LancamentoBean item : lista) {
                    repository.delete(item);
                }
            }
            
            LancamentoCnpjBean bean = repository.marge((LancamentoCnpjBean) repository.find(LancamentoCnpjBean.class, id));
            repository.delete(bean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Entidade removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a entidade!")).recursive().serialize();
        }
    }
    
    public List<Relatorio1DTO> relatorio1() {
        String  q = "select \n" +
                    "    valor , descricao\n" +
                    "from\n" +
                    "    lancamento";
        List<Relatorio1DTO> lista  = repository.query(q).executeSQL(Relatorio1DTO.class);
        return lista;
    }
}
