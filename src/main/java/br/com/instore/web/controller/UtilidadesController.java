package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.RepositoryViewer;
import br.com.instore.core.orm.bean.CepBean;
import br.com.instore.core.orm.property.Cep;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.dto.FuncionalidadeTreeDTO;
import br.com.instore.web.tools.CepResult;
import br.com.instore.web.tools.CepService;
import java.math.BigInteger;

@Resource
public class UtilidadesController implements java.io.Serializable {

    private SessionRepository repository;
    private Result result;

    public UtilidadesController(SessionRepository repository, Result result) {
        this.repository = repository;
        this.result = result;
    }

    @Get
    @Path("/utilidades/cepload")
    public void cepload(String cep1 , String cep2) {
        CepResult res = new CepResult();
        if (repository.query(CepBean.class).eq(Cep.NUMERO, cep1).count() > 0) {
            CepBean bean = repository.query(CepBean.class).eq(Cep.NUMERO, cep1).findOne();
            res.setUf(bean.getBairro().getCidade().getEstado().getSigla());
            res.setCidade(bean.getBairro().getCidade().getNome());
            res.setBairro(bean.getBairro().getNome());
            res.setLogradouro(bean.getBairro().getRua());
            res.setTipo_logradouro(bean.getBairro().getTipo());
        } else {
            res = CepService.get(cep2);
        }
        result.use(Results.json()).withoutRoot().from(res).recursive().serialize();

    }
    
    @Get
    @Path("/utilidades/funcionalidadetree")
    public void loadfucionalidadeTree(Integer idperfil) {
        FuncionalidadeTreeDTO root = new  FuncionalidadeTreeDTO();
        loadfucionalidadeTree(root, 0, idperfil);
        result.use(Results.json()).withoutRoot().from(root).recursive().serialize();
    }

    public void loadfucionalidadeTree(final FuncionalidadeTreeDTO root, Integer parente, final Integer idperfil) {
        if (parente == null) {
            parente = 0;
        }

        RepositoryViewer rv = repository;
        String query = "select \n"
                + "    idfuncionalidade , nome , ( select if(count(*) > 0 , 1 , 0) from perfil_funcionalidade where idperfil = " + idperfil + " and idfuncionalidade = funcionalidade.idfuncionalidade) as perfil_tem , \n"
                + "   parente, "
                + "   (select count(*) as filhos from funcionalidade as f where f.parente = funcionalidade.idfuncionalidade) as tem_filhos "
                + "from\n"
                + "    funcionalidade\n"
                + "where parente = " + parente;

        rv.query(query).executeSQL(new Each() {
            private Integer idfuncionalidade;
            private String nome;
            private BigInteger perfilTem;
            private Integer parente;
            private BigInteger temFilhos;

            @Override
            public void each() {
                FuncionalidadeTreeDTO filho = new FuncionalidadeTreeDTO();

                filho.setIdfuncionalidade(idfuncionalidade);
                filho.setNome(nome);
                filho.setParente(parente);
                filho.setPerfilTem((perfilTem.longValue() > 0 ? true : false));

                if (temFilhos.longValue() > 0) {
                    loadfucionalidadeTree(filho, filho.getIdfuncionalidade(), idperfil);
                }
                root.getFilhos().add(filho);
            }
        });
    }
}
