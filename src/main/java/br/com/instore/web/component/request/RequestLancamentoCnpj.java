package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.LancamentoBean;
import br.com.instore.core.orm.bean.LancamentoCnpjBean;
import br.com.instore.core.orm.bean.property.Lancamento;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.LancamentoCnpjDTO;
import br.com.instore.web.dto.LancamentoRelatorioDTO;
import br.com.instore.web.dto.Relatorio1DTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

            String moneyString = bean.getSaldoDisponivel().toString();
            if (null != bean.getSaldoDisponivel()) {
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                moneyString = formatter.format(bean.getSaldoDisponivel());
            }

            dto.setSaldoDisponivel(moneyString);
            dto.setCnpj(bean.getCnpj());
            lista2.add(dto);
        }
        return lista2;
    }

    public LancamentoCnpjBean bean(Integer id) {
        return repository.find(LancamentoCnpjBean.class, id);
    }

    public void salvar(LancamentoCnpjBean bean) {

        if (null == bean) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Preencha todos os campos")).recursive().serialize();
            return;
        }
        if (null == bean.getNome() || bean.getNome().isEmpty()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe o nome!")).recursive().serialize();
            return;
        }
        if (null == bean.getCnpj() || bean.getCnpj().isEmpty()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe o CNPJ!")).recursive().serialize();
            return;
        }
        if (null == bean.getSaldoDisponivel()) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Informe o saldo disponivel!")).recursive().serialize();
            return;
        }

        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (bean != null && bean.getId() != null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                if (repository.query(LancamentoCnpjBean.class).eq("nome", bean.getNome()).count() > 0) {
                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não é possivel finalizar a ação , já existe uma Entidade financeiro com esse nome!")).recursive().serialize();
                    return;
                }

                if (repository.query(LancamentoCnpjBean.class).eq("cnpj", bean.getCnpj()).count() > 0) {
                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não é possivel finalizar a ação , já existe uma Entidade financeiro com esse cnpj!")).recursive().serialize();
                    return;
                }
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

            if (repository.query(LancamentoBean.class).eq(Lancamento.LANCAMENTO_CNPJ, id).count() > 0) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Ops! Existem lançamentos ligados á empresa, por favor remova os lançamentos primeiro.")).recursive().serialize();
            } else {
                LancamentoCnpjBean bean = repository.marge((LancamentoCnpjBean) repository.find(LancamentoCnpjBean.class, id));
                repository.delete(bean);

                repository.finalize();
                result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Entidade removida com sucesso!")).recursive().serialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a entidade!")).recursive().serialize();
        }
    }

    public List<Relatorio1DTO> relatorio1() {
        String q = "select \n"
                + "    valor , descricao\n"
                + "from\n"
                + "    lancamento";
        List<Relatorio1DTO> lista = repository.query(q).executeSQL(Relatorio1DTO.class);
        return lista;
    }

    private List<LancamentoRelatorioDTO> relatorio(Integer id, Date d1, Date d2, int tipo) {

        String sqlRule = " \n";
        String sqlTipo = "\n\n";

        if (null != id) {
            sqlRule += " \n and lancamento_cnpj.id = " + id;
        }

        if (null != d1) {
            sqlRule += " \n and lancamento.mes >= date('" + new SimpleDateFormat("yyyy-MM-dd").format(d1) + "')";
        }

        if (null != d2) {
            sqlRule += " \n and lancamento.mes <= date('" + new SimpleDateFormat("yyyy-MM-dd").format(d2) + "')";
        }

        String queries = "";
        queries = "select \n"
                + "    mes as data,\n"
                + "	descricao as historico,\n"
                + "	valor as credito,\n"
                + "	0     as debito,\n"
                + "	saldo_disponivel as saldo ,\n"
                + "	positivo, data_fechamento as fechamento\n"
                + "from\n"
                + "    lancamento\n"
                + "inner join lancamento_cnpj on lancamento_cnpj.id = lancamento.lancamento_cnpj\n"
                + "where \n"
                + "	credito = 1\n"
                + sqlTipo
                + sqlRule
                + "\nunion\n"
                + "\n"
                + "select \n"
                + "    mes as data,\n"
                + "	descricao as historico,\n"
                + "    0     as credito,\n"
                + "	valor as debito,\n"
                + "	saldo_disponivel as saldo ,\n"
                + "	positivo, data_fechamento as fechamento\n"
                + "from\n"
                + "    lancamento\n"
                + "inner join lancamento_cnpj on lancamento_cnpj.id = lancamento.lancamento_cnpj\n"
                + "where \n"
                + "	debito = 1\n"
                + sqlTipo
                + sqlRule
                + " order by data \n";

        List<LancamentoRelatorioDTO> lista1 = repository.query(queries).executeSQL(LancamentoRelatorioDTO.class);
        return lista1;
    }

    public void relatorios(Integer id, Date d1, Date d2) {
        List<LancamentoRelatorioDTO> lista1 = relatorio(id, d1, d2, 0);
        List<LancamentoRelatorioDTO> lista2 = new ArrayList<LancamentoRelatorioDTO>();

        List<LancamentoRelatorioDTO> listaAux = new ArrayList<LancamentoRelatorioDTO>();
        List<LancamentoRelatorioDTO> listaAux2 = new ArrayList<LancamentoRelatorioDTO>();

        for (LancamentoRelatorioDTO item : lista1) {
            listaAux.add(item);
        }

        for (LancamentoRelatorioDTO item : lista2) {
            listaAux.add(item);
        }

        BigDecimal valurAnt = new BigDecimal("0");
        int indice = 0;
        for (LancamentoRelatorioDTO item : listaAux) {
            if (indice == 0) {
                valurAnt = item.getSaldo();
            }

            item.setSaldo(valurAnt);

            if (item.getPositivo() > 0) {

                item.setSaldoCalculado((valurAnt.add(item.getDebito())).add(item.getCredito()));
            } else {
                item.setSaldoCalculado((valurAnt.subtract(item.getDebito())).subtract(item.getCredito()));
            }
            valurAnt = item.getSaldoCalculado();
            lista2.add(item);

            indice++;
        }



        BigDecimal bd1 = new BigDecimal("0");
        BigDecimal bd2 = new BigDecimal("0");


        for (LancamentoRelatorioDTO item : lista1) {
            bd1 = bd1.add(item.getDebito());
            bd2 = bd2.add(item.getCredito());
        }

        result.include("lancamentoRelatorioList", lista2);
        result.include("totalDebito", bd1);
        result.include("totalCredito", bd2);
    }
}
