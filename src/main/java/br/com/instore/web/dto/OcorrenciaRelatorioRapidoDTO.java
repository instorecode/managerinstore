package br.com.instore.web.dto;

import br.com.instore.core.orm.bean.OcorrenciaStatusBean;


public class OcorrenciaRelatorioRapidoDTO extends AbstractDTO {
    private Long total;
    private OcorrenciaStatusBean ocorrenciaStatus;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public OcorrenciaStatusBean getOcorrenciaStatus() {
        return ocorrenciaStatus;
    }

    public void setOcorrenciaStatus(OcorrenciaStatusBean ocorrenciaStatus) {
        this.ocorrenciaStatus = ocorrenciaStatus;
    }
}
