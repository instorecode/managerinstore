package br.com.instore.web.dto;

import java.util.ArrayList;
import java.util.List;

public class AudiostoreCategoriaJSON {
    private Integer page;
    private Integer size;
    private List<AudiostoreCategoriaDTO> rows = new ArrayList<AudiostoreCategoriaDTO>();

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

    public List<AudiostoreCategoriaDTO> getRows() {
        return rows;
    }

    public void setRows(List<AudiostoreCategoriaDTO> rows) {
        this.rows = rows;
    }
}
