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
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
                Locale brLocale = new Locale("pt", "BR");
                NumberFormat formatter = NumberFormat.getCurrencyInstance(brLocale);
                moneyString = formatter.format(bean.getValor());
            }


            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setInstituicao(bean.getLancamentoCnpj().getNome());
            dto.setCredito(bean.getCredito() ? "Sim" : "Não");
            dto.setDebito(bean.getDebito() ? "Sim" : "Não");
            dto.setTipo(bean.getCredito() ? "Receber" : "Pagar");
            dto.setDescricao(bean.getDescricao());
            dto.setMes(new SimpleDateFormat("dd/MM/yyyy").format(bean.getMes()));
            dto.setValor(moneyString);
            dto.setUsuarioNome(bean.getUsuario().getNome());

            if (null != bean.getDatFechamento()) {
                dto.setFinalizado("Sim");
                dto.setDataFechamento("Finalizado na data " + new SimpleDateFormat("dd/MM/yyyy").format(bean.getDatFechamento()) + ".");
            } else {
                dto.setDataFechamento("Não foi finalizado");
                dto.setFinalizado("Não");
            }

            dto.setPositivo(bean.getPositivo() ? "Sim" : "Não");

            lista2.add(dto);
        }
        return lista2;
    }

    public List<LancamentoDTO> beanListByMesNow() {
        List<LancamentoBean> lista = new ArrayList<LancamentoBean>();
        List<LancamentoDTO> lista2 = new ArrayList<LancamentoDTO>();

        lista = repository.query(LancamentoBean.class).eq("mes", new Date()).findAll();
        for (LancamentoBean bean : lista) {


            LancamentoDTO dto = new LancamentoDTO();
            String moneyString = bean.getValor().toString();
            if (null != bean.getValor()) {
                Locale brLocale = new Locale("pt", "BR");
                NumberFormat formatter = NumberFormat.getCurrencyInstance(brLocale);
                moneyString = formatter.format(bean.getValor());
            }


            dto.setId(Utilities.leftPad(bean.getId()));
            dto.setInstituicao(bean.getLancamentoCnpj().getNome());
            dto.setCredito(bean.getCredito() ? "Sim" : "Não");
            dto.setDebito(bean.getDebito() ? "Sim" : "Não");
            dto.setTipo(bean.getCredito() ? "Receber" : "Pagar");
            dto.setDescricao(bean.getDescricao());
            dto.setMes(new SimpleDateFormat("dd/MM/yyyy").format(bean.getMes()));
            dto.setValor(moneyString);
            dto.setUsuarioNome(bean.getUsuario().getNome());

            if (null != bean.getDatFechamento()) {
                dto.setFinalizado("Sim");
                dto.setDataFechamento("Finalizado na data " + new SimpleDateFormat("dd/MM/yyyy").format(bean.getDatFechamento()) + ".");
            } else {
                dto.setDataFechamento("Não foi finalizado");
                dto.setFinalizado("Não");
            }

            dto.setPositivo(bean.getPositivo() ? "Sim" : "Não");

            lista2.add(dto);


        }
        return lista2;
    }

    public List<LancamentoDTO> beanListByNaoFechado() {
        List<LancamentoBean> lista = new ArrayList<LancamentoBean>();
        List<LancamentoDTO> lista2 = new ArrayList<LancamentoDTO>();

        lista = repository.query(LancamentoBean.class).orderAsc("mes").findAll();
        
        Integer i = 1;
        for (LancamentoBean bean : lista) {
            
            if (null == bean.getDatFechamento() && i<= 5) {
                LancamentoDTO dto = new LancamentoDTO();
                String moneyString = bean.getValor().toString();
                if (null != bean.getValor()) {
                    Locale brLocale = new Locale("pt", "BR");
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(brLocale);
                    moneyString = formatter.format(bean.getValor());
                }


                dto.setId(Utilities.leftPad(bean.getId()));
                dto.setInstituicao(bean.getLancamentoCnpj().getNome());
                dto.setCredito(bean.getCredito() ? "Sim" : "Não");
                dto.setDebito(bean.getDebito() ? "Sim" : "Não");
                dto.setTipo(bean.getCredito() ? "Receber" : "Pagar");
                dto.setDescricao(bean.getDescricao());
                dto.setMes(new SimpleDateFormat("dd/MM/yyyy").format(bean.getMes()));
                dto.setValor(moneyString);
                dto.setUsuarioNome(bean.getUsuario().getNome());

                if (null != bean.getDatFechamento()) {
                    dto.setFinalizado("Sim");
                    dto.setDataFechamento("Finalizado na data " + new SimpleDateFormat("dd/MM/yyyy").format(bean.getDatFechamento()) + ".");
                } else {
                    dto.setDataFechamento("Não foi finalizado");
                    dto.setFinalizado("Não");
                }

                dto.setPositivo(bean.getPositivo() ? "Sim" : "Não");

                lista2.add(dto);
                i++;
            }
        }
        return lista2;
    }

    public LancamentoBean bean(Integer id) {
        return repository.find(LancamentoBean.class, id);
    }

    public List<LancamentoCnpjBean> lancamentoCnpjList() {
        return (List<LancamentoCnpjBean>) repository.query(LancamentoCnpjBean.class).findAll();
    }

    public String mes(int i) {
        switch (i) {
            case 1:
                return "Janeiro";
            case 2:
                return "Fevereito";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Maio";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            case 12:
                return "Dezembro";
        }

        return " Janeiro";
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
        if (null == bean.getDescricao() || bean.getDescricao().isEmpty()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O campo descrição é obrigatório!")).recursive().serialize();
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
                    d2 = new SimpleDateFormat("MM/yyyy").parse(d2s);
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
                    lb.setDescricao(bean.getDescricao() + " " + (i + 1) + "ª parcela em " + mes(c.get(Calendar.MONTH) + 1) + " de " + new SimpleDateFormat("yyyy").format(d) + ".");
                    lb.setLancamentoCnpj(bean.getLancamentoCnpj());
                    lb.setMes(d);
                    lb.setUsuario(bean.getUsuario());
                    lb.setValor(bean.getValor() / (meses + 1));
                    lb.setPositivo(bean.getCredito() ? true : false);

                    if (!validate(lb)) {
                        return;
                    }

                    if ((null == lb.getDebito() && null == lb.getCredito()) || (!lb.getDebito() && !lb.getCredito())) {
                        lb.setDebito(true);
                    }

                    repository.save(lb);
                }
            } else {
                if ((null == bean.getDebito() && null == bean.getCredito()) || (!bean.getDebito() && !bean.getCredito())) {
                    bean.setDebito(true);
                }

                if (!validate(bean)) {
                    return;
                }
                bean.setPositivo(bean.getCredito() ? true : false);

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

            if (null != bean.getDatFechamento()) {
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
        LancamentoCnpjBean bean = repository.find(LancamentoCnpjBean.class, id);
        bean.setSaldoDisponivel(bean.getSaldoDisponivel() - val);
        repository.save(repository.marge(bean));
    }

    public void addSaldo(Integer id, Double val) {
        LancamentoCnpjBean bean = repository.find(LancamentoCnpjBean.class, id);
        bean.setSaldoDisponivel(bean.getSaldoDisponivel() + val);
        repository.save(repository.marge(bean));
    }

    public void incluirTotais() {
        List<LancamentoBean> lista = new ArrayList<LancamentoBean>();
        lista = repository.query(LancamentoBean.class).findAll();

        BigDecimal receber = new BigDecimal(0);
        BigDecimal pagar = new BigDecimal(0);
        BigDecimal atrasado_receber = new BigDecimal(0);
        BigDecimal atrasado_pagar = new BigDecimal(0);
        
        BigDecimal receber_mes = new BigDecimal(0);
        BigDecimal pagar_mes = new BigDecimal(0);

        for (LancamentoBean bean : lista) {
            DateTime dt1 = new DateTime(bean.getMes());
            DateTime dt2 = new DateTime(new Date());

            String dateMes = new SimpleDateFormat("ddMMyyyy").format(bean.getMes());
            String dateAtual = new SimpleDateFormat("ddMMyyyy").format(new Date());
            String dateMes_mes = new SimpleDateFormat("MM").format(bean.getMes());
            String dateAtual_mes = new SimpleDateFormat("MM").format(new Date());

            if (bean.getDatFechamento() == null) {
                if (bean.getCredito()) {
                    if (dateMes.equals(dateAtual)) {
                        atrasado_receber = atrasado_receber.add(new BigDecimal(bean.getValor()));
                    }
                    
                    if (dateMes_mes.equals(dateAtual_mes)) {
                        receber_mes = receber_mes.add(new BigDecimal(bean.getValor()));
                    }
                    
                    receber = receber.add(new BigDecimal(bean.getValor()));
                }
                if (bean.getDebito()) {
                    if (dateMes.equals(dateAtual)) {
                        atrasado_pagar = atrasado_pagar.add(new BigDecimal(bean.getValor()));
                    }
                    
                    if (dateMes_mes.equals(dateAtual_mes)) {
                        pagar_mes = pagar_mes.add(new BigDecimal(bean.getValor()));
                    }
                    
                    pagar = pagar.add(new BigDecimal(bean.getValor()));
                }
            }
        }

        Locale brLocale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(brLocale);

        result.include("receber", formatter.format(receber));
        result.include("pagar", formatter.format(pagar));
        
        result.include("receber_mes", formatter.format(receber_mes));
        result.include("pagar_mes", formatter.format(pagar_mes));
        
        result.include("atrasado_receber", formatter.format(atrasado_receber));
        result.include("atrasado_pagar", formatter.format(atrasado_pagar));
    }
    
    
}
