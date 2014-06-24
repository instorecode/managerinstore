package br.com.instore.web.component.request;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.instore.core.orm.bean.AudiostoreCategoriaBean;
import br.com.instore.core.orm.bean.BairroBean;
import br.com.instore.core.orm.bean.CepBean;
import br.com.instore.core.orm.bean.CidadeBean;
import br.com.instore.web.component.session.SessionRepository;
import br.com.instore.core.orm.bean.ClienteBean;
import br.com.instore.core.orm.bean.DadosClienteBean;
import br.com.instore.core.orm.bean.EnderecoBean;
import br.com.instore.core.orm.bean.EstadoBean;
import br.com.instore.core.orm.bean.property.DadosCliente;
import br.com.instore.core.orm.bean.property.Estado;
import br.com.instore.web.component.session.SessionUsuario;
import br.com.instore.web.dto.AudiostoreCategoriaDTO;
import br.com.instore.web.dto.ClienteDTO;
import br.com.instore.web.tools.AjaxResult;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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
        List<AudiostoreCategoriaBean> lista2  = repository.query(AudiostoreCategoriaBean.class).findAll();
        
        for (AudiostoreCategoriaBean categoria : lista2) {
            AudiostoreCategoriaDTO dto = new AudiostoreCategoriaDTO();
            
            dto.setCategoria(categoria.getCategoria());
            dto.setClienteNome(categoria.getCliente().getNome());
            dto.setCodigo(categoria.getCodigo());
            dto.setDataFinal( new SimpleDateFormat("dd/MM/yyyy").format(categoria.getDataFinal()));
            dto.setDataInicio( new SimpleDateFormat("dd/MM/yyyy").format(categoria.getDataInicio()));
            dto.setTempo(new SimpleDateFormat("hh:mm:ss").format(categoria.getTempo()));
            switch(categoria.getTipo()){
                case 1 : dto.setTipo("Música"); break;
                case 2 : dto.setTipo("Comercial"); break;
                case 3 : dto.setTipo("Video"); break;
            }
            
            lista.add(dto);
        }
        return lista;
    }

    public List<ClienteBean> clienteBeanList() {
        List<ClienteBean> clienteBeanList = repository.query(ClienteBean.class).findAll();
        return clienteBeanList;
    }
    
    public AudiostoreCategoriaBean audiostoreCategoriaBean(Integer id) {
        return repository.find(AudiostoreCategoriaBean.class, id);
    }


    public void salvar(AudiostoreCategoriaBean audiostoreCategoriaBean, String tempo) {
        try {
            repository.setUsuario(sessionUsuario.getUsuarioBean());
            audiostoreCategoriaBean.setTempo( new SimpleDateFormat("HH:mm:ss").parse(tempo += ":00") );
            
            if(null != audiostoreCategoriaBean && null != audiostoreCategoriaBean.getIdaudiostoreCategoria() && audiostoreCategoriaBean.getIdaudiostoreCategoria() > 0) {
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
}
