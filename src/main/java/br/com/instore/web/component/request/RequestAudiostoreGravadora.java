package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.DataValidator;
import br.com.instore.core.orm.DataValidatorException;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreGravadoraDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

@RequestScoped
public class RequestAudiostoreGravadora implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestAudiostoreGravadora() {
    }

    public RequestAudiostoreGravadora(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public List<AudiostoreGravadoraDTO> beanList() {
        List<AudiostoreGravadoraBean> lista = new ArrayList<AudiostoreGravadoraBean>();
        List<AudiostoreGravadoraDTO> lista2 = new ArrayList<AudiostoreGravadoraDTO>();
        lista = repository.query(AudiostoreGravadoraBean.class).findAll();
        for (AudiostoreGravadoraBean bean : lista) {
            AudiostoreGravadoraDTO dto = new AudiostoreGravadoraDTO(Utilities.leftPad(bean.getId()), bean.getNome());
            lista2.add(dto);
        }
        return lista2;
    }

    public AudiostoreGravadoraBean bean(Integer id) {
        return repository.find(AudiostoreGravadoraBean.class, id);
    }

    public void salvar(AudiostoreGravadoraBean bean) {
        try {
            DataValidator.beanValidator(bean);
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            if (bean != null && bean.getId() != null && bean.getId() > 0) {
                repository.save(repository.marge(bean));
            } else {
                repository.save(bean);
            }

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (DataValidatorException e) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, e.getMessage())).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
        }
    }

    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            AudiostoreGravadoraBean bean = repository.marge((AudiostoreGravadoraBean) repository.find(AudiostoreGravadoraBean.class, id));
            repository.delete(bean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Voz removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a voz!")).recursive().serialize();
        }
    }

    public InputStreamDownload download(Integer id) {
        InputStreamDownload inputStreamDownload = null;
        try {
            AudiostoreGravadoraBean bean = bean(id);
            if (bean != null) {

                String conteudo = "";
                String nome = bean.getNome();

                if (nome.length() < 30) {
                    nome = StringUtils.leftPad(nome, 30, " ");
                } else {
                    if (nome.length() > 30) {
                        nome = nome.substring(0, 30);
                    }
                }

                conteudo += StringUtils.leftPad(bean.getId().toString(), 5, " ");
                conteudo += nome;

                inputStreamDownload = new InputStreamDownload(new ByteArrayInputStream(conteudo.getBytes()), "application/exp", bean.getId()+ ".exp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStreamDownload;
    }

    public void upload(Integer id) {
        try {
            ConfigAppBean config = repository.find(ConfigAppBean.class, 1);
            AudiostoreGravadoraBean bean = bean(id);
            if (bean != null) {

                String conteudo = "";
                String nome = bean.getNome();

                if (nome.length() < 30) {
                    nome = StringUtils.leftPad(nome, 30, " ");
                } else {
                    if (nome.length() > 30) {
                        nome = nome.substring(0, 30);
                    }
                }
                conteudo += StringUtils.leftPad(bean.getId().toString(), 5, " ");
                conteudo += nome;
                
                File dir = new File(config.getDataPath()+"\\gravacao-exp\\");
                if(!dir.exists()) {
                    dir.mkdirs();
                }
                
                InputStream is = new ByteArrayInputStream(conteudo.getBytes());
                FileOutputStream fos = new FileOutputStream(new File(config.getDataPath() + "\\gravacao-exp\\" + StringUtils.leftPad(bean.getId().toString(), 3, "0") + ".exp"));

                IOUtils.copy(is, fos);
                result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "")).recursive().serialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "")).recursive().serialize();
        }
    }
}
