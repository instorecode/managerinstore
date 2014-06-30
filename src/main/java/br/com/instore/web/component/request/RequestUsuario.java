package br.com.instore.web.component.request;

import br.com.instore.web.component.session.SessionRepository;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.BairroBean;
import br.com.instore.core.orm.bean.CepBean;
import br.com.instore.core.orm.bean.CidadeBean;
import br.com.instore.core.orm.bean.EnderecoBean;
import br.com.instore.core.orm.bean.EstadoBean;
import br.com.instore.core.orm.bean.FuncionalidadeBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.core.orm.bean.property.Estado;
import br.com.instore.core.orm.bean.property.Usuario;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreGravadoraDTO;
import br.com.instore.web.dto.UsuarioDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.swing.tree.TreeNode;

@RequestScoped
public class RequestUsuario implements java.io.Serializable {

    @Inject
    private Result result;
    @Inject
    private SessionRepository repository;
    @Inject
    private SessionUsuario sessionUsuario;
    @Inject
    private HttpSession httpSession;

    public RequestUsuario() {
    }

    public RequestUsuario(Result result, SessionRepository requestRepository, SessionUsuario sessionUsuario, HttpSession httpSession) {
        this.result = result;
        this.repository = requestRepository;
        this.sessionUsuario = sessionUsuario;
        this.httpSession = httpSession;
    }

    public void logIn(String email, String senha) {
        try {
            senha = Utilities.md5(senha);
            if (repository.query(UsuarioBean.class).eq(Usuario.EMAIL, email).and().eq(Usuario.SENHA, senha).count() > 0) {
                UsuarioBean usuario = repository.query(UsuarioBean.class).eq(Usuario.EMAIL, email).and().eq(Usuario.SENHA, senha).findOne();
                usuario.getPerfilBeanList();
                sessionUsuario.setUsuarioBean(usuario);
                sessionUsuario.setLogado(true);
                
                repository.setUsuario( sessionUsuario.getUsuarioBean() );
                Utilities.historicoUsuarioLogin(repository);

                result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Usuário logado com sucesso")).recursive().serialize();
            } else {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "E-mail / Senha inválidos.")).recursive().serialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "E-mail / Senha inválidos.")).recursive().serialize();
        }
    }

    public void logOut() {
        try {
            repository.setUsuario( sessionUsuario.getUsuarioBean() );
            Utilities.historicoUsuarioLogOut(repository);
            httpSession.invalidate();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "LogOut efetuado com sucesso.")).recursive().serialize();
        } catch (NoSuchAlgorithmException e) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Lamento, não foi possivel fazer o logout, tente limpar os dados do navegador.")).recursive().serialize();
        }
    }

    public List<EstadoBean> estadoBeanList() {
        List<EstadoBean> estadoBeanList = repository.query(EstadoBean.class).findAll();
        return estadoBeanList;
    }

    public List<UsuarioDTO> beanList() {
        List<UsuarioBean> lista = new ArrayList<UsuarioBean>();
        List<UsuarioDTO> lista2 = new ArrayList<UsuarioDTO>();
        lista = repository.query(UsuarioBean.class).findAll();
        for (UsuarioBean bean : lista) {
            UsuarioDTO dto = new UsuarioDTO();

            dto.setId(Utilities.leftPad(bean.getIdusuario()));
            dto.setNome(bean.getNome());
            dto.setCpf(bean.getCpf());
            dto.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataCadastro()));
            dto.setEmail(bean.getEmail());
            if (null != bean.getEndereco()) {
                String endereco = "";

                endereco += bean.getEndereco().getCep().getBairro().getNome();
                endereco += " , " + bean.getEndereco().getCep().getBairro().getRua();
                endereco += " nº" + bean.getEndereco().getNumero();
                endereco += " , " + bean.getEndereco().getCep().getBairro().getCidade().getNome();
                endereco += " - " + bean.getEndereco().getCep().getBairro().getCidade().getEstado().getNome();
                endereco += "/" + bean.getEndereco().getCep().getBairro().getCidade().getEstado().getRegiao().getNome();
                dto.setEndereco(endereco);
            }

            lista2.add(dto);
        }
        return lista2;
    }

    public UsuarioBean bean(Integer id) {
        return repository.find(UsuarioBean.class, id);
    }

    public void salvar(UsuarioBean bean) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            EnderecoBean end = bean.getEndereco();
            CepBean cep = bean.getEndereco().getCep();
            BairroBean bairro = bean.getEndereco().getCep().getBairro();
            CidadeBean cidade = bean.getEndereco().getCep().getBairro().getCidade();

            if (repository.query(EstadoBean.class).eq(Estado.IDESTADO, cidade.getEstado().getIdestado()).count() > 0) {
                EstadoBean est = repository.query(EstadoBean.class).eq(Estado.IDESTADO, cidade.getEstado().getIdestado()).findOne();
                cidade.setEstado(est);
            }

            if (cidade.getIdcidade() != null && cidade.getIdcidade() > 0) {
                cidade = repository.marge(cidade);
            }
            repository.save(cidade);


            if (bairro.getIdbairro() != null && bairro.getIdbairro() > 0) {
                bairro = repository.marge(bairro);
            }
            bairro.setCidade(cidade);
            repository.save(bairro);


            if (cep.getIdcep() != null && cep.getIdcep() > 0) {
                cep = repository.marge(cep);
            }
            cep.setBairro(bairro);
            repository.save(cep);


            if (end.getIdendereco() != null && end.getIdendereco() > 0) {
                end = repository.marge(end);
            }
            end.setCep(cep);
            repository.save(end);

            if (bean != null && bean.getIdusuario() != null && bean.getIdusuario() > 0) {
                repository.save(repository.marge(bean));
                repository.save(bean.getEndereco());
            } else {
                bean.setDataCadastro(new Date());
                bean.setEndereco(end);
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

            String query = "";

            // remove ligação com empresa
            query = "delete from usuario_empresa where idusuario = " + id;
            repository.query(query).executeSQLCommand();

            // remove ligação com perfil
            query = "delete from perfil_usuario where idusuario = " + id;
            repository.query(query).executeSQLCommand();

            // remove usuario
            query = "delete from usuario where idusuario = " + id;
            repository.query(query).executeSQLCommand();

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Usuário removido com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover o usuário!")).recursive().serialize();
        }
    }
}
