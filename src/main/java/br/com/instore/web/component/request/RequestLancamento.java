package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.LancamentoBean;
import br.com.instore.core.orm.bean.LancamentoCnpjBean;
import br.com.instore.core.orm.bean.property.LancamentoFinalizado;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.LancamentoDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.Months;

@RequestScoped
public class RequestLancamento implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestLancamento() {
    }

    public RequestLancamento(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public List<LancamentoDTO> beanList() {
        List<LancamentoBean> lista = new ArrayList<LancamentoBean>();
        List<LancamentoDTO> lista2 = new ArrayList<LancamentoDTO>();
        lista = repository.query(LancamentoBean.class).findAll();
        for (LancamentoBean bean : lista) {
            LancamentoDTO dto = new LancamentoDTO();

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String moneyString = formatter.format(bean.getValor());

            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setCredito(bean.getCredito() ? "Sim" : "Não");
            dto.setDebito(bean.getDebito() ? "Sim" : "Não");
            dto.setDescricao(bean.getDescricao());
            dto.setMes(new SimpleDateFormat("dd/MM/yyyy").format(bean.getMes()));
            dto.setValor(moneyString);
            dto.setUsuarioNome(bean.getUsuario().getNome());
            dto.setDataFechamento(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDatFechamento()));


            lista2.add(dto);
        }
        return lista2;
    }

    public LancamentoBean bean(Integer id) {
        return repository.find(LancamentoBean.class, id);
    }

    public List<LancamentoCnpjBean> lancamentoCnpjList() {
        return (List<LancamentoCnpjBean>) repository.query(LancamentoCnpjBean.class).findAll();
    }

    public void salvar(LancamentoBean bean, Date d1, Date d2) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (null != bean.getDebito() && bean.getDebito()) {
                bean.setCredito(Boolean.FALSE);
            }

            if (null != bean.getDebito() && !bean.getDebito()) {
                bean.setCredito(Boolean.TRUE);
            }

            if (null == bean.getCredito()) {
                bean.setCredito(Boolean.FALSE);
            }

            if (null == bean.getDebito()) {
                bean.setDebito(Boolean.FALSE);
            }

            bean.setUsuario(sessionUsuario.getUsuarioBean());

            if (null != d1 && null != d2) {
                DateTime dt1 = new DateTime(d1);
                DateTime dt2 = new DateTime(d2);

                int meses = Months.monthsBetween(dt1, dt2).getMonths();
                for (int i = 0; i <= meses; i++) {
                        
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(bean.getMes().getTime());
                    c.add(Calendar.MONTH, i);
                    
                    LancamentoBean lb = new LancamentoBean();
                    lb.setCredito(bean.getCredito());
                    lb.setDatFechamento(null);
                    lb.setDebito(bean.getCredito());
                    lb.setDescricao(bean.getDescricao() +i+"ª parcela.");
                    lb.setLancamentoCnpj(bean.getLancamentoCnpj());
                    lb.setMes(c.getTime());
                    lb.setUsuario(bean.getUsuario());
                    lb.setValor(bean.getValor());
                    
                    repository.save(lb);
                }
            } else {
                if (bean != null && bean.getId() != null && bean.getId() > 0) {
                    repository.save(repository.marge(bean));
                } else {
                    repository.save(bean);
                }
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

            LancamentoBean bean = repository.marge((LancamentoBean) repository.find(LancamentoBean.class, id));
            repository.delete(bean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Entidade removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a entidade!")).recursive().serialize();
        }
    }

    public Boolean efetuarLancamento(Integer id) {
        LancamentoBean lb = bean(id);
        if (null == lb.getDatFechamento()) {
            return true;
        }
        return false;
    }
}
