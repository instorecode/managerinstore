package br.com.instore.web.component.request;

import br.com.instore.web.component.session.SessionRepository;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.BairroBean;
import br.com.instore.core.orm.bean.CepBean;
import br.com.instore.core.orm.bean.CidadeBean;
import br.com.instore.core.orm.bean.EnderecoBean;
import br.com.instore.core.orm.bean.EstadoBean;
import br.com.instore.core.orm.bean.FuncionalidadeBean;
import br.com.instore.core.orm.bean.PerfilBean;
import br.com.instore.core.orm.bean.PerfilUsuarioBean;
import br.com.instore.core.orm.bean.UsuarioBean;
import br.com.instore.core.orm.bean.property.Estado;
import br.com.instore.core.orm.bean.property.Perfil;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Inject
    private HttpServletResponse httpServletResponse;

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
            if (repository.query(UsuarioBean.class).eq(Usuario.EMAIL, email).and().eq(Usuario.SENHA, senha).count() >= 0) {
                UsuarioBean usuario = repository.query(UsuarioBean.class).eq(Usuario.EMAIL, email).and().eq(Usuario.SENHA, senha).findOne();
                usuario.getPerfilBeanList();
                sessionUsuario.setUsuarioBean(usuario);
                sessionUsuario.setLogado(true);

                repository.setUsuario(sessionUsuario.getUsuarioBean());
                Utilities.historicoUsuarioLogin(repository);

                Cookie cookie1 = new Cookie("managerinstore_machine_userck", usuario.getIdusuario().toString());

                httpServletResponse.addCookie(cookie1);

                result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Usuário logado com sucesso")).recursive().serialize();
            } else {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "E-mail / Senha inválidos.")).recursive().serialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "E-mail / Senha inválidos.")).recursive().serialize();
        }
    }

    public void autoLogIn(Integer id) {
        try {
            if (repository.query(UsuarioBean.class).eq(Usuario.IDUSUARIO, id).count() > 0) {
                UsuarioBean usuario = repository.query(UsuarioBean.class).eq(Usuario.IDUSUARIO, id).findOne();
                usuario.getPerfilBeanList();
                sessionUsuario.setUsuarioBean(usuario);
                sessionUsuario.setLogado(true);

                repository.setUsuario(sessionUsuario.getUsuarioBean());
                Utilities.historicoUsuarioLogin(repository);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut() {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
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

    public List<PerfilBean> perfilBeanList() {
        List<PerfilBean> perfilBeanList = repository.query(PerfilBean.class).findAll();
        return perfilBeanList;
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

    public void salvar(UsuarioBean bean, Integer[] perfilListID) {
        if (!(bean.getIdusuario() != null && bean.getIdusuario() > 0)) {
            if (repository.query(UsuarioBean.class).eq(Usuario.CPF, bean.getCpf()).count() > 0) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Já existe um usuário cadastrado com o este CPF!")).recursive().serialize();
                return;
            }
        }


        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            bean.setSenha(Utilities.md5(bean.getSenha()));

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
            } else {
                bean.setDataCadastro(new Date());
                bean.setEndereco(end);
                repository.save(bean);
            }

            // remove ligação com perfil
            String query = "delete from perfil_usuario where idusuario = " + bean.getIdusuario();
            repository.query(query).executeSQLCommand();

            for (Integer id : perfilListID) {
                PerfilUsuarioBean pub = new PerfilUsuarioBean();
                pub.setUsuario(bean);
                pub.setPerfil(new PerfilBean(id));
                repository.save(pub);
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
            query = "delete from usuario_cliente where idusuario = " + id;
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

    public void meusDados(UsuarioBean bean) {
        if (!(bean.getIdusuario() != null && bean.getIdusuario() > 0)) {
            if (repository.query(UsuarioBean.class).eq(Usuario.CPF, bean.getCpf()).count() > 0) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Já existe um usuário cadastrado com o este CPF!")).recursive().serialize();
                return;
            }
        }


        try {
            UsuarioBean b = repository.find(UsuarioBean.class, bean.getIdusuario());
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            bean.setSenha(b.getSenha());

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



            String updateUsuario = "UPDATE usuario SET nome = ? , cpf = ? , email = ? , senha = ? , idendereco = ? where idusuario = " + bean.getIdusuario();
            updateUsuario = updateUsuario.replaceFirst("\\?", "'"+bean.getNome()+"'");
            updateUsuario = updateUsuario.replaceFirst("\\?", "'"+bean.getCpf()+"'");
            updateUsuario = updateUsuario.replaceFirst("\\?", "'"+bean.getEmail()+"'");
            updateUsuario = updateUsuario.replaceFirst("\\?", "'"+bean.getSenha()+"'");
            updateUsuario = updateUsuario.replaceFirst("\\?", "'"+end.getIdendereco()+"'");
            

            repository.query(updateUsuario).executeSQLCommand();

            repository.finalize();
            sessionUsuario.setUsuarioBean(bean);
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }
    
    public void minhaSenha(String senha_atual , String nova_senha, String conf_senha) {
        try {
            
            boolean ajaxResultBool = true;
            String ajaxResultStr = "";
            
            if(repository.query(UsuarioBean.class).eq("senha", Utilities.md5(senha_atual)).count() <= 0) {
                ajaxResultBool = false;
                ajaxResultStr = "A senha atual está incorreta!";
            }
            
            if (ajaxResultBool) {
                if(null == nova_senha || nova_senha.trim().isEmpty() || null == conf_senha || conf_senha.trim().isEmpty()) {
                    ajaxResultBool = false;
                    ajaxResultStr = "Preencha todos os campos!";
                }
                
                if(!nova_senha.equals(conf_senha)) {
                    ajaxResultBool = false;
                    ajaxResultStr = "Você informu duas senhas diferentes!";
                }
            }
            
            if (ajaxResultBool) {
                String updateUsuario = "UPDATE usuario SET  senha = ?  where idusuario = " + sessionUsuario.getUsuarioBean().getIdusuario();
                updateUsuario = updateUsuario.replaceFirst("\\?", "'"+Utilities.md5(nova_senha)+"'");
                repository.query(updateUsuario).executeSQLCommand();
                repository.finalize();
                
                sessionUsuario.getUsuarioBean().setSenha(Utilities.md5(nova_senha));
                
                ajaxResultBool = false;
                ajaxResultStr = "Senha atualizada com sucesso!";
            }
            result.use(Results.json()).withoutRoot().from(new AjaxResult(ajaxResultBool, ajaxResultStr)).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }
}
