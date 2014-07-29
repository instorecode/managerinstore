<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/ocorrencia-prioridade" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/ocorrencia-prioridade">
            <input type="hidden" name="ocorrenciaPrioridadeBean.id" value="${ocorrenciaPrioridadeBean.id}" />

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Descrição</label>
                        <input type="text" name="ocorrenciaPrioridadeBean.descricao" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${ocorrenciaPrioridadeBean.descricao}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Nivel</label>
                        <br />
                        <input type="text" class="bslider form-control" name="ocorrenciaPrioridadeBean.nivel" value="" data-slider-step="1" data-slider-max="100" data-slider-min="1" data-slider-value="${ocorrenciaPrioridadeBean.nivel}" />
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>