package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreCategoriaDTO;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

@RequestScoped
public class RequestAudiostoreCategoria implements java.io.Serializable {

    @Inject
    private SessionRepository repository;
    @Inject
    private Result result;
    @Inject
    private SessionUsuario sessionUsuario;

    public RequestAudiostoreCategoria() {
    }

    public RequestAudiostoreCategoria(SessionRepository repository, Result result, SessionUsuario sessionUsuario) {
        this.repository = repository;
        this.result = result;
        this.sessionUsuario = sessionUsuario;
    }

    public List<AudiostoreCategoriaDTO> categoriaDTOList() {
        List<AudiostoreCategoriaDTO> lista = new ArrayList<AudiostoreCategoriaDTO>();
        List<AudiostoreCategoriaBean> lista2 = repository.query(AudiostoreCategoriaBean.class).findAll();

        for (AudiostoreCategoriaBean categoria : lista2) {
            AudiostoreCategoriaDTO dto = new AudiostoreCategoriaDTO();

            dto.setCategoria(categoria.getCategoria());
            dto.setClienteNome(categoria.getCliente().getNome());
            dto.setCodigo(categoria.getCodigo());
            dto.setDataFinal(new SimpleDateFormat("dd/MM/yyyy").format(categoria.getDataFinal()));
            dto.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").format(categoria.getDataInicio()));
            dto.setTempo(new SimpleDateFormat("hh:mm:ss").format(categoria.getTempo()));
            switch (categoria.getTipo()) {
                case 1:
                    dto.setTipo("Música");
                    break;
                case 2:
                    dto.setTipo("Comercial");
                    break;
                case 3:
                    dto.setTipo("Video");
                    break;
            }

            lista.add(dto);
        }
        return lista;
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        return clienteBeanList;
    }

    public AudiostoreCategoriaBean audiostoreCategoriaBean(Integer id) {
        return repository.find(AudiostoreCategoriaBean.class, id);
    }

    public void salvar(AudiostoreCategoriaBean audiostoreCategoriaBean, String tempo) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            audiostoreCategoriaBean.setTempo(new SimpleDateFormat("HH:mm:ss").parse(tempo));

            if (null != audiostoreCategoriaBean && null != audiostoreCategoriaBean.getIdaudiostoreCategoria() && audiostoreCategoriaBean.getIdaudiostoreCategoria() > 0) {
                repository.save(repository.marge(audiostoreCategoriaBean));
            } else {
                repository.save(audiostoreCategoriaBean);
            }

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Dados salvos com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel salvar os dados!")).recursive().serialize();
            repository.finalize();
        }
    }

    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            AudiostoreCategoriaBean audiostoreCategoriaBean = repository.marge((AudiostoreCategoriaBean) repository.find(AudiostoreCategoriaBean.class, id));
            repository.delete(audiostoreCategoriaBean);

            repository.finalize();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "Audiostore categoria removida com sucesso!")).recursive().serialize();
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Não foi possivel remover a audiostore categoria!")).recursive().serialize();
            repository.finalize();
        }
    }

    public InputStreamDownload download(Integer id) {
        InputStreamDownload inputStreamDownload = null;
        try {
            AudiostoreCategoriaBean audiostoreCategoriaBean = audiostoreCategoriaBean(id);
            if (audiostoreCategoriaBean != null) {

                String conteudo = "";

                conteudo += StringUtils.leftPad(audiostoreCategoriaBean.getCodigo().toString(), 5, " ");
                conteudo += StringUtils.leftPad(audiostoreCategoriaBean.getCategoria(), 30, " ");
                conteudo += StringUtils.leftPad(new SimpleDateFormat("dd/MM/yy").format(audiostoreCategoriaBean.getDataInicio()), 8, " ");
                conteudo += StringUtils.leftPad(new SimpleDateFormat("dd/MM/yy").format(audiostoreCategoriaBean.getDataFinal()), 8, " ");
                conteudo += audiostoreCategoriaBean.getTipo();
                conteudo += StringUtils.leftPad(new SimpleDateFormat("HH:mm:ss").format(audiostoreCategoriaBean.getTempo()), 8, " ");

                inputStreamDownload = new InputStreamDownload(new ByteArrayInputStream(conteudo.getBytes()), "application/exp", audiostoreCategoriaBean.getCategoria() + ".exp");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStreamDownload;
    }

    public void upload(Integer id) {
        try {
            AudiostoreCategoriaBean audiostoreCategoriaBean = audiostoreCategoriaBean(id);
            if (audiostoreCategoriaBean != null) {

                String conteudo = "";

                conteudo += StringUtils.leftPad(audiostoreCategoriaBean.getCodigo().toString(), 5, " ");
                conteudo += StringUtils.leftPad(audiostoreCategoriaBean.getCategoria(), 30, " ");
                conteudo += StringUtils.leftPad(new SimpleDateFormat("dd/MM/yy").format(audiostoreCategoriaBean.getDataInicio()), 8, " ");
                conteudo += StringUtils.leftPad(new SimpleDateFormat("dd/MM/yy").format(audiostoreCategoriaBean.getDataFinal()), 8, " ");
                conteudo += audiostoreCategoriaBean.getTipo();

                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", audiostoreCategoriaBean.getCliente().getIdcliente()).findOne();

                SmbFile smb = new SmbFile("smb://"+dados.getLocalDestinoExp(), Utilities.getAuthSmb());
                SmbFile smb2 = new SmbFile("smb://"+dados.getLocalDestinoExp()+"/"+StringUtils.leftPad(audiostoreCategoriaBean.getCodigo().toString(), 5, " ")+".exp", Utilities.getAuthSmb());
                
                if (!smb.exists()) {
                    smb.mkdirs();
                }
                
                SmbFileOutputStream sfous = new SmbFileOutputStream(smb2);
                sfous.write(conteudo.getBytes());
                sfous.close();
                
                result.use(Results.json()).withoutRoot().from(new AjaxResult(true, "")).recursive().serialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "")).recursive().serialize();
        }
    }
}
