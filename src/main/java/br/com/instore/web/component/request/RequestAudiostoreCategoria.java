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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void beanList(Integer page, Integer rows, Integer id, Integer idcliente, String nome, Integer tipo, String duracao, String dataInicio, String dataFinal, String codInterno) {
        AudiostoreCategoriaJSON json = new AudiostoreCategoriaJSON();

        page = (null == page || 0 == page ? 1 : page);
        rows = (null == rows || 0 == rows ? 10 : rows);

        Integer offset = (page - 1) * rows;
        List<AudiostoreCategoriaBean> lista = new ArrayList<AudiostoreCategoriaBean>();

        Query q1 = repository.query(AudiostoreCategoriaBean.class);
        Query q2 = repository.query(AudiostoreCategoriaBean.class);

        if (null != id && id > 0) {
            q1.eq("id", id);
            q2.eq("id", id);
            json.setId(id);
        }

        if (null != idcliente && idcliente > 0) {
            q1.eq("cliente.idcliente", idcliente);
            q2.eq("cliente.idcliente", idcliente);
            json.setIdcliente(idcliente);
        }

        if (null != nome && !nome.isEmpty()) {
            q1.ilikeAnyWhere("categoria", nome);
            q2.ilikeAnyWhere("categoria", nome);
            json.setCategoria(nome);
        }

        if (null != tipo && tipo > 0) {
            q1.eq("tipo", tipo);
            q2.eq("tipo", tipo);
            json.setTipo(tipo);
        }

        if (null != codInterno && !codInterno.isEmpty()) {
            q1.eq("codInterno", codInterno);
            q2.eq("codInterno", codInterno);
            json.setCodInterno(codInterno);
        }

        if (null != duracao && !duracao.isEmpty()) {
            try {
                Date d = new SimpleDateFormat("HH:mm:ss").parse(duracao);
                q1.eq("tempo", d);
                q2.eq("tempo", d);
                json.setTempo(duracao);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (null != dataInicio && !dataInicio.isEmpty()) {
            try {
                Date d = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicio);
                q1.eq("dataInicio", d);
                q2.eq("dataInicio", d);
                json.setDataInicio(dataInicio);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (null != dataFinal && !dataFinal.isEmpty()) {
            try {
                Date d = new SimpleDateFormat("dd/MM/yyyy").parse(dataFinal);
                q1.eq("dataFinal", d);
                q2.eq("dataFinal", d);
                json.setDataFinal(dataFinal);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int size = q1.count().intValue() / rows + ((q1.count().intValue() % rows == 0) ? 0 : 1);
        lista = q2.limit(offset, rows).findAll();


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
            dto.setTipoNum("" + bean.getTipo());
            dto.setIdcliente(bean.getCliente().getIdcliente().toString());
            dto.setCodInterno(bean.getCodInterno());
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
        dto.setTipoNum("" + bean.getTipo());
        dto.setIdcliente(bean.getCliente().getIdcliente().toString());
        dto.setCodInterno(bean.getCodInterno());

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

            if (audiostoreCategoriaBean.getDataInicio().after(audiostoreCategoriaBean.getDataFinal())) {
                result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "A data de inicio deve ser menor que a data de termino da categoria!")).recursive().serialize();
                return;
            }

            if (!(null != audiostoreCategoriaBean && null != audiostoreCategoriaBean.getIdaudiostoreCategoria() && audiostoreCategoriaBean.getIdaudiostoreCategoria() > 0)) {
                if (repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", audiostoreCategoriaBean.getCliente().getIdcliente()).eq("categoria", audiostoreCategoriaBean.getCategoria()).count() > 0) {
                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Já existe uma categoria com este nome!")).recursive().serialize();
                    return;
                }

                if (repository.query(AudiostoreCategoriaBean.class).eq("cliente.idcliente", audiostoreCategoriaBean.getCliente().getIdcliente()).eq("codInterno", audiostoreCategoriaBean.getCodInterno()).count() > 0) {
                    result.use(Results.json()).withoutRoot().from(new AjaxResult(false, "Já existe uma categoria com este código!")).recursive().serialize();
                    return;
                }
            }

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

    public void upload(Integer [] id_list) throws Exception {
        try {
            String conteudo = "";
            List<AudiostoreCategoriaBean> list = repository.query(AudiostoreCategoriaBean.class).in("codigo", id_list).findAll();
            for (AudiostoreCategoriaBean audiostoreCategoriaBean : list) {
                if (audiostoreCategoriaBean != null) {
                    conteudo += StringUtils.leftPad(audiostoreCategoriaBean.getCodigo().toString(), 5, " ");
                    conteudo += StringUtils.leftPad(audiostoreCategoriaBean.getCategoria(), 30, " ");
                    conteudo += StringUtils.leftPad(new SimpleDateFormat("dd/MM/yy").format(audiostoreCategoriaBean.getDataInicio()), 8, " ");
                    conteudo += StringUtils.leftPad(new SimpleDateFormat("dd/MM/yy").format(audiostoreCategoriaBean.getDataFinal()), 8, " ");
                    conteudo += audiostoreCategoriaBean.getTipo();
                    conteudo = "\n";
                }
            }

            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", list.get(0).getCliente().getIdcliente()).findOne();
            String destino = dados.getLocalDestinoExp();
            SmbFile smb = new SmbFile(destino, Utilities.getAuthSmbDefault());
            SmbFile smb2 = new SmbFile(destino + StringUtils.leftPad(list.get(0).getCodInterno().toString(), 5, " ") + ".exp", Utilities.getAuthSmbDefault());

            if (!smb.exists()) {
                smb.mkdirs();
            }

            SmbFileOutputStream sfous = new SmbFileOutputStream(smb2);
            sfous.write(conteudo.getBytes());
            sfous.close();

        } catch (Exception e) {
            e.printStackTrace();
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

    public void validarCategorias(Integer[] id_list) {
        List<AudiostoreCategoriaBean> list = repository.query(AudiostoreCategoriaBean.class).in("codigo", id_list).findAll();

        boolean ajaxResultBool = true;
        String ajaxResultStr = "";

        // verifica se todos são do mesmo cliente
        Integer idcliente = list.get(0).getCliente().getIdcliente();
        for (AudiostoreCategoriaBean bean : list) {
            if (!bean.getCliente().getIdcliente().equals(idcliente)) {
                ajaxResultBool = false;
                ajaxResultStr = "Você selecionou categorias de clientes diferentes!";
                break;
            }
        }

        if (ajaxResultBool) {
            DadosClienteBean dados = repository.query(DadosClienteBean.class).eq("cliente.idcliente", list.get(0).getCliente().getIdcliente()).findOne();
            if (null == dados || null == dados.getLocalDestinoExp() || dados.getLocalDestinoExp().trim().isEmpty()) {
                ajaxResultBool = false;
                ajaxResultStr = "O cliente não possui um local de destino para os arquivos de exportação!";
            }
        }

        try {
            upload(id_list);
        } catch (Exception e) {
            ajaxResultBool = false;
            ajaxResultStr = e.getMessage();
        }

        result.use(Results.json()).withoutRoot().from(new AjaxResult(ajaxResultBool, ajaxResultStr)).recursive().serialize();
    }
}
