package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.RepositoryViewer;
import br.com.instore.core.orm.bean.LancamentoBean;
import br.com.instore.core.orm.bean.LancamentoCnpjBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.LancamentoDTO;
import br.com.instore.web.dto.LancamentoRelatorioDTO;
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
        
        lista = repository.query(LancamentoBean.class).orderAsc("mes").findAll();
        
        for (LancamentoBean bean : lista) {
            LancamentoDTO dto = new LancamentoDTO();
            String moneyString = bean.getValor().toString();
            if (null != bean.getValor()) {
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                moneyString = formatter.format(bean.getValor());
            }


            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setCredito(bean.getCredito() ? "Sim" : "Não");
            dto.setDebito(bean.getDebito() ? "Sim" : "Não");
            dto.setTipo(bean.getCredito() ? "CRÉDITO" : "DÉBITO");
            dto.setDescricao(bean.getDescricao());
            dto.setMes(new SimpleDateFormat("dd/MM/yyyy").format(bean.getMes()));
            dto.setValor(moneyString);
            dto.setUsuarioNome(bean.getUsuario().getNome());
            if (null != bean.getDatFechamento()) {
                dto.setDataFechamento("Finalizado na data " + new SimpleDateFormat("dd/MM/yyyy").format(bean.getDatFechamento()) + ".");
            } else {
                dto.setDataFechamento("Não foi finalizado");
            }

            dto.setPositivo(bean.getPositivo() ? "Sim" : "Não");

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

    public void salvar(LancamentoBean bean, Date d1, String d2s) {

        if (null != d1 && !(null != d2s && !d2s.isEmpty())) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe a data de inicio e a data de termino!")).recursive().serialize();
            return;
        }
        if ((null != d2s && !d2s.isEmpty()) && null == d1) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe a data de inicio e a data de termino!")).recursive().serialize();
            return;
        }
        
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

            if (null != d1 && null != d2s && !d2s.isEmpty()) {

                Date d2 = null;
                try {
                    d2 = new SimpleDateFormat("MMMMM yyyy").parse(d2s);
                } catch (Exception e) {
                    e.printStackTrace();
                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Intervalo de datas de repetiçoes invalido!")).recursive().serialize();
                    return;
                }

                DateTime dt1 = new DateTime(d1);
                DateTime dt2 = new DateTime(d2);

                if (dt2.getYear() <= dt1.getYear() && (!d2.after(d1) || dt2.getMonthOfYear() <= dt1.getMonthOfYear())) {
                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O invervalo de meses é muito pequeno!")).recursive().serialize();
                    return;
                }

                int meses = Months.monthsBetween(dt1, dt2).getMonths();

                for (int i = 0; i <= meses; i++) {

                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(d1.getTime());
                    c.add(Calendar.MONTH, i);
                    Date d = c.getTime();
                    LancamentoBean lb = new LancamentoBean();
                    lb.setCredito(bean.getCredito());
                    lb.setDatFechamento(null);
                    lb.setDebito(bean.getCredito());
                    lb.setDescricao(bean.getDescricao() + " " + (i + 1) + "ª parcela em " + new SimpleDateFormat("MMMMM").format(d) + " de " + new SimpleDateFormat("yyyy").format(d) + ".");
                    lb.setLancamentoCnpj(bean.getLancamentoCnpj());
                    lb.setMes(d);
                    lb.setUsuario(bean.getUsuario());
                    lb.setValor(bean.getValor() / (meses + 1));
                    lb.setPositivo(bean.getPositivo());

                    if (!validate(lb)) {
                        return;
                    }


                    repository.save(lb);
                }
            } else {

                if (!validate(bean)) {
                    return;
                }

                if (bean != null && bean.getId() != null && bean.getId() > 0) {
                    if (null != bean.getDatFechamento()) {
                        removeSaldo(bean.getLancamentoCnpj().getId(), bean.getValor());
                    }
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

    public boolean validate(LancamentoBean lb) {
        if (null == lb.getCredito()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Defina se é crédito ou débito!")).recursive().serialize();
            return false;
        }

        if (null == lb.getDebito()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Defina se é crédito ou débito!")).recursive().serialize();
            return false;
        }

        if (null == lb.getDescricao() || lb.getDescricao().isEmpty()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe a descrição do lançamento!")).recursive().serialize();
            return false;
        }

        if (null == lb.getLancamentoCnpj() || null == lb.getLancamentoCnpj().getId() || lb.getLancamentoCnpj().getId() <= 0) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Selecione uma entidade!")).recursive().serialize();
            return false;
        }

        if (null == lb.getMes()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe a data!")).recursive().serialize();
            return false;
        }

        if (null == lb.getUsuario()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe o usuários!")).recursive().serialize();
            return false;
        }

        if (null == lb.getValor()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe o valor!")).recursive().serialize();
            return false;
        }
        if (null == lb.getPositivo()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe se o lançamento é positivo ou negativo!")).recursive().serialize();
            return false;
        }
        return true;
    }

    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            LancamentoBean bean = repository.marge((LancamentoBean) repository.find(LancamentoBean.class, id));

            if (null == bean.getDatFechamento()) {
                addSaldo(bean.getLancamentoCnpj().getId(), bean.getValor());
            }

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

    public void removeSaldo(Integer id, Double val) {
//        LancamentoCnpjBean bean = repository.find(LancamentoCnpjBean.class, id);
//        bean.setSaldoDisponivel(bean.getSaldoDisponivel() - val);
//        repository.save(repository.marge(bean));
    }

    public void addSaldo(Integer id, Double val) {
//        LancamentoCnpjBean bean = repository.find(LancamentoCnpjBean.class, id);
//        bean.setSaldoDisponivel(bean.getSaldoDisponivel() + val);
//        repository.save(repository.marge(bean));
    }
}
