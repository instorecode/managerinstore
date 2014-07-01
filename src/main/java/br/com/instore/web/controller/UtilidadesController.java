package br.com.instore.web.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.RepositoryViewer;
import br.com.instore.core.orm.Test2;
import br.com.instore.core.orm.bean.CepBean;
import br.com.instore.core.orm.bean.property.Cep;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.dto.AbstractDTO;
import br.com.instore.web.tools.CepResult;
import br.com.instore.web.tools.CepService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@Controller
public class UtilidadesController implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;

    public UtilidadesController() {
    }

    public UtilidadesController(SessionRepository repository, Result result) {
        this.repository = repository;
        this.result = result;
    }

    @Get
    @Path("/utilidades/cepload")
    public void cepload(String cep) {
        CepResult res = new CepResult();
        if (repository.query(CepBean.class).eq(Cep.NUMERO, cep).count() > 0) {
            CepBean bean = repository.query(CepBean.class).eq(Cep.NUMERO, cep).findOne();
            res.setUf(bean.getBairro().getCidade().getEstado().getSigla());
            res.setCidade(bean.getBairro().getCidade().getNome());
            res.setBairro(bean.getBairro().getNome());
            res.setLogradouro(bean.getBairro().getRua());
        } else {
            res = CepService.get(cep);
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

                filho.idfuncionalidade = idfuncionalidade;
                filho.nome = nome;
                filho.parente = parente;
                filho.perfilTem = (perfilTem.longValue() > 0 ? true : false);

                if (temFilhos.longValue() > 0) {
                    loadfucionalidadeTree(filho, filho.idfuncionalidade, idperfil);
                }
                root.filhos.add(filho);
            }
        });
    }

    public class FuncionalidadeTreeDTO extends AbstractDTO {

        Integer idfuncionalidade;
        String nome;
        Boolean perfilTem;
        Integer parente;
        List<FuncionalidadeTreeDTO> filhos = new ArrayList<FuncionalidadeTreeDTO>();
    }
}
