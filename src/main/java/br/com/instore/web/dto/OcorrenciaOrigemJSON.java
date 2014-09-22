package br.com.instore.web.dto;

import br.com.instore.web.dto.OcorrenciaOrigemDTO;
import java.util.ArrayList;
import java.util.List;


public class OcorrenciaOrigemJSON {
    private Integer page;
    private Integer size;
    private List<OcorrenciaOrigemDTO> rows = new ArrayList<OcorrenciaOrigemDTO>();

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<OcorrenciaOrigemDTO> getRows() {
        return rows;
    }

    public void setRows(List<OcorrenciaOrigemDTO> rows) {
        this.rows = rows;
    }
}
