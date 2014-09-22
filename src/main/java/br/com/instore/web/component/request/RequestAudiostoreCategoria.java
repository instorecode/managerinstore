package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.Query;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreCategoriaDTO;
import br.com.instore.web.dto.AudiostoreCategoriaJSON;
import br.com.instore.web.tools.AjaxResult;
import br.com.instore.web.tools.Utilities;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
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

    public void beanList(Integer page, Integer rows, Integer id, Integer cliente, String nome, Integer tipo, String duracao, String dataInicio, String dataFinal) {
        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<AudiostoreCategoriaBean> lista = new ArrayList<AudiostoreCategoriaBean>();

        Query q1 = repository.query(AudiostoreCategoriaBean.class);
        Query q2 = repository.query(AudiostoreCategoriaBean.class);

        if (null != id && id > 0) {
            q2.eq("id", id);
        }

        if (null != nome && !nome.isEmpty()) {
            q2.ilikeAnyWhere("categoria", nome);
        }

        if (null != tipo && tipo > 0) {
            q2.eq("tipo", tipo);
        }

        if (null != duracao && !duracao.isEmpty()) {
            try {
                Date d = new SimpleDateFormat("HH:mm:ss").parse(duracao);
                q2.eq("tempo", d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();

        AudiostoreCategoriaJSON json = new AudiostoreCategoriaJSON();
        json.setPage(page);
        json.setSize(size);

        List<AudiostoreCategoriaDTO> rowsList = new ArrayList<AudiostoreCategoriaDTO>();
        for (AudiostoreCategoriaBean bean : lista) {

            AudiostoreCategoriaDTO dto = new AudiostoreCategoriaDTO();
            dto.setCodigo(bean.getCodigo());
            dto.setCategoria(bean.getCategoria());
            dto.setClienteNome(bean.getCliente().getNome());
            dto.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataInicio()));
            dto.setDataFinal(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataFinal()));
            dto.setTempo(new SimpleDateFormat("HH:mm:ss").format(bean.getTempo()));
            dto.setTipoNum(""+bean.getTipo());
            dto.setIdcliente(bean.getCliente().getIdcliente().toString());
            switch (bean.getTipo()) {
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

            rowsList.add(dto);
        }
        json.setRows(rowsList);
        result.use(Results.json()).withoutRoot().from(json).recursive().serialize();
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).eq("matriz", true).eq("parente", 0).findAll();
        return clienteBeanList;
    }

    public AudiostoreCategoriaBean bean(Integer id) {
        return repository.find(AudiostoreCategoriaBean.class, id);
    }
    
    public AudiostoreCategoriaDTO audiostoreCategoriaBean(Integer id) {

        AudiostoreCategoriaBean bean = repository.find(AudiostoreCategoriaBean.class, id);
        AudiostoreCategoriaDTO dto = new AudiostoreCategoriaDTO();
        dto.setCodigo(bean.getCodigo());
        dto.setCategoria(bean.getCategoria());
        dto.setClienteNome(bean.getCliente().getNome());
        dto.setIdcliente(bean.getCliente().getIdcliente().toString());
        dto.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataInicio()));
        dto.setDataFinal(new SimpleDateFormat("dd/MM/yyyy").format(bean.getDataFinal()));
        dto.setTempo(new SimpleDateFormat("HH:mm:ss").format(bean.getTempo()));
        dto.setTipoNum(""+bean.getTipo());
        dto.setIdcliente(bean.getCliente().getIdcliente().toString());
        
        switch (bean.getTipo()) {
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

        return dto;
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
        }
    }

    public void remover(Integer id) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());

            AudiostoreCategoriaBean audiostoreCategoriaBean = repository.marge((AudiostoreCategoriaBean) repository.find(AudiostoreCategoriaBean.class, id));

            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", audiostoreCategoriaBean.getCliente().getIdcliente()).findOne();
            if (dados != null) {
                String destino = dados.getLocalDestinoExp() + "exp/categoria/" + audiostoreCategoriaBean.getCodigo() + ".exp";
                SmbFile smb = new SmbFile(destino, Utilities.getAuthSmbDefault());

                if (smb.exists()) {
                    smb.delete();
                }
            }
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
            AudiostoreCategoriaBean audiostoreCategoriaBean = repository.find(AudiostoreCategoriaBean.class, id);
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

    public void upload(AudiostoreCategoriaBean audiostoreCategoriaBean) throws Exception {
        try {
            if (audiostoreCategoriaBean != null) {
                String conteudo = "";

                conteudo += StringUtils.leftPad(audiostoreCategoriaBean.getCodigo().toString(), 5, " ");
                conteudo += StringUtils.leftPad(audiostoreCategoriaBean.getCategoria(), 30, " ");
                conteudo += StringUtils.leftPad(new SimpleDateFormat("dd/MM/yy").format(audiostoreCategoriaBean.getDataInicio()), 8, " ");
                conteudo += StringUtils.leftPad(new SimpleDateFormat("dd/MM/yy").format(audiostoreCategoriaBean.getDataFinal()), 8, " ");
                conteudo += audiostoreCategoriaBean.getTipo();

                DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", audiostoreCategoriaBean.getCliente().getIdcliente()).findOne();
                String destino = dados.getLocalDestinoExp() + "exp/categoria/";
                SmbFile smb = new SmbFile(destino, Utilities.getAuthSmbDefault());
                SmbFile smb2 = new SmbFile(destino + StringUtils.leftPad(audiostoreCategoriaBean.getCodigo().toString(), 5, " ") + ".exp", Utilities.getAuthSmbDefault());

                if (!smb.exists()) {
                    smb.mkdirs();
                }

                SmbFileOutputStream sfous = new SmbFileOutputStream(smb2);
                sfous.write(conteudo.getBytes());
                sfous.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void upload(UploadedFile file) {
        try {
            if (!file.getFileName().endsWith(".exp")) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "O arquivo " + file.getFileName() + " não está num formato válido!")).recursive().serialize();
            } else {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getFile()));
                String readline;
                List<String> lines = new ArrayList<String>();
                while ((readline = bufferedReader.readLine()) != null) {
                    lines.add(readline);
                }

                for (String line : lines) {
                }
            }
        } catch (IOException ex) {
            result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Ocorreu um erro ao tentar sincronizar o arquivo " + file.getFileName())).recursive().serialize();
        }
    }
}
