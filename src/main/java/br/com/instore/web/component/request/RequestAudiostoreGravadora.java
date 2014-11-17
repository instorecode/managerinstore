package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.FileDownload;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.DataValidator;
import br.com.instore.core.orm.DataValidatorException;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.AudiostoreGravadoraBean;
import br.com.instore.core.orm.bean.ConfigAppBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreCategoriaJSON;
import br.com.instore.web.dto.AudiostoreGravadoraDTO;
import br.com.instore.web.dto.AudiostoreGravadoraJSON;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
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

    public void beanList(Boolean datajson, Boolean view, Integer page, Integer rows, Integer id, String nome) {
        AudiostoreGravadoraJSON json = new AudiostoreGravadoraJSON();
        List<AudiostoreGravadoraBean> lista = new ArrayList<AudiostoreGravadoraBean>();

        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);
        Integer offset = (page - 1) * rows;

        Query q1 = repository.query(AudiostoreGravadoraBean.class);
        Query q2 = repository.query(AudiostoreGravadoraBean.class);

        if (null != id && id > 0) {
            q1.eq("id", id);
            q2.eq("id", id);
            json.setId(id);
        }

        if (null != nome && !nome.isEmpty()) {
            q1.ilikeAnyWhere("nome", nome);
            q2.ilikeAnyWhere("nome", nome);
            json.setNome(nome);
        }
        
        json.setCount(q1.count().intValue());
        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();

        json.setPage(page);
        json.setSize(size);

        List<AudiostoreGravadoraDTO> rowsList = new ArrayList<AudiostoreGravadoraDTO>();

        for (AudiostoreGravadoraBean bean : lista) {
            AudiostoreGravadoraDTO dto = new AudiostoreGravadoraDTO();
            dto.setId(bean.getId());
            dto.setNome(bean.getNome());
            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> lista = new ArrayList<ClienteBean>();
        lista = repository.query(ClienteBean.class).findAll();
        return lista;
    }

    public List<ClienteBean> clienteMatrizBeanList() {
        List<ClienteBean> lista = new ArrayList<ClienteBean>();
        lista = repository.query(ClienteBean.class).eq("parente", 0).eq("matriz", true).findAll();
        return lista;
    }

    public AudiostoreGravadoraDTO beanDTO(Integer id) {
        AudiostoreGravadoraDTO dto = new AudiostoreGravadoraDTO();
        AudiostoreGravadoraBean bean = repository.find(AudiostoreGravadoraBean.class, id);

        dto.setId(bean.getId());
        dto.setNome(bean.getNome());

        return dto;
    }

    public void upload(Integer[] id_list, String caminho) {
        try {
            String conteudo = "";
            String quebraLinha = "";
            List<AudiostoreGravadoraBean> lista = repository.query(AudiostoreGravadoraBean.class).in("id", id_list).findAll();
            for (AudiostoreGravadoraBean audiostoreGravadoraBean : lista) {
                if (audiostoreGravadoraBean != null) {
                    conteudo += quebraLinha;
                    conteudo += StringUtils.leftPad(String.valueOf(audiostoreGravadoraBean.getId()), 5, " ");
                    conteudo += StringUtils.leftPad(String.valueOf(audiostoreGravadoraBean.getNome()), 30, " ");
                }
                quebraLinha = "\r\n";
            }
            SmbFile smb = new SmbFile(caminho, Utilities.getAuthSmbDefault());
            SmbFile smb2 = new SmbFile(caminho + "gravadora.exp", Utilities.getAuthSmbDefault());

            if (!smb.exists()) {
                smb.mkdirs();
            }

            SmbFileOutputStream sfous = new SmbFileOutputStream(smb2);
            sfous.write(conteudo.getBytes());
            sfous.close();

        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    public void validarGravadora(Integer[] id_list, Integer id_cliente) {
        DadosClienteBean resultado = repository.query(DadosClienteBean.class).eq("cliente.idcliente", id_cliente).findOne();

        boolean ajaxResultBool = true;
        String ajaxResultStr = "";

        if (ajaxResultBool) {
            if (resultado == null || resultado.getLocalDestinoExp().trim().isEmpty() || null == resultado.getLocalDestinoExp()) {
                ajaxResultBool = false;
                ajaxResultStr = "O cliente não possui um local de destino para os arquivos de exportação!";
            }
        }

        if (ajaxResultBool) {
            upload(id_list, resultado.getLocalDestinoExp());
        }

        result.use(Results.json()).withoutRoot().from(new AjaxResult(ajaxResultBool, ajaxResultStr)).recursive().serialize();

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

                inputStreamDownload = new InputStreamDownload(new ByteArrayInputStream(conteudo.getBytes()), "application/exp", bean.getId() + ".exp");
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
                nome = Utilities.formatarHexExp(nome);
                conteudo += nome;

                File dir = new File(config.getDataPath() + "\\gravacao-exp\\");
                if (!dir.exists()) {
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
