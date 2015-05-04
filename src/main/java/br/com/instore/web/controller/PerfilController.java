package br.com.instore.web.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.bean.PerfilBean;
import br.com.instore.web.annotation.Restrict;
import br.com.instore.web.component.request.RequestPerfil;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.dto.FuncionalidadeTreeDTO;
import java.math.BigInteger;


@Resource
public class PerfilController {
    
    private Result result;
    private RequestPerfil requestPerfil;
    private SessionRepository repository;

    public PerfilController(Result result, RequestPerfil requestPerfil, SessionRepository repository) {
        this.result = result;
        this.requestPerfil = requestPerfil;
        this.repository = repository;
    }

    @Get
    @Restrict
    @Path("/perfil")
    public void perfis(Boolean datajson) {
        if (null != datajson && datajson) {
            result.use(Results.json()).withoutRoot().from(requestPerfil.beanList()).recursive().serialize();
        }
    }

    @Get
    @Restrict
    @Path("/perfil/cadastrar")
    public void cadastrar() {
        FuncionalidadeTreeDTO root = new  FuncionalidadeTreeDTO();
        loadfucionalidadeTree(root, 0, 0);
        result.include("perfis", root);
    }

    @Post
    @Restrict
    @Path("/perfil/cadastrar")
    public void cadastrar(PerfilBean perfilBean , Integer [] funcionalidadeID) {
        requestPerfil.salvar(perfilBean , funcionalidadeID);
    }

    @Get
    @Restrict
    @Path("/perfil/atualizar/{id}")
    public void cadastrar(Integer id) {
        result.include("perfilBean", requestPerfil.bean(id));
        FuncionalidadeTreeDTO root = new  FuncionalidadeTreeDTO();
        loadfucionalidadeTree(root, 0, id);
        result.include("perfis", root);
    }

    @Post
    @Restrict
    @Path("/perfil/atualizar/{id}")
    public void cadastrar(Integer id , PerfilBean perfilBean ,Integer [] funcionalidadeID ) {
        requestPerfil.salvar(perfilBean , funcionalidadeID);
    }

    @Get
    @Restrict
    @Path("/perfil/remover/{id}")
    public void remover(Integer id) {
        result.include("perfilBean", requestPerfil.bean(id));
    }

    @Post
    @Restrict
    @Path("/perfil/remover/{id}")
    public void remover(Integer id, String param) {
        requestPerfil.remover(id);
    }
    
    public void loadfucionalidadeTree(final FuncionalidadeTreeDTO root, Integer parente, final Integer idperfil) {
        if (parente == null) {
            parente = 0;
        }

        String query = "select \n"
                + "    idfuncionalidade , nome , ( select if(count(*) > 0 , 1 , 0) from perfil_funcionalidade where idperfil = " + idperfil + " and idfuncionalidade = funcionalidade.idfuncionalidade) as perfil_tem , \n"
                + "   parente, "
                + "   (select count(*) as filhos from funcionalidade as f where f.parente = funcionalidade.idfuncionalidade) as tem_filhos "
                + "from\n"
                + "    funcionalidade\n"
                + "where parente = " + parente;

        repository.query(query).executeSQL(new Each() {
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
