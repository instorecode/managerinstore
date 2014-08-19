<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/indice-reajuste" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/indice-reajuste">
            <input type="hidden" name="indiceReajusteBean.id" value="${indiceReajusteBean.id}" />

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Tipo</label>
                        <input type="text" name="indiceReajusteBean.tipo" class="form-control" placeholder="Tipo"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="10" value="${indiceReajusteBean.tipo}">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Descrição</label>
                        <input type="text" name="indiceReajusteBean.descricao" class="form-control" placeholder="Descrição" value="${indiceReajusteBean.descricao}">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Percentual</label>
                        <input type="text" name="indiceReajusteBean.percentual" class="form-control" placeholder="Percentual"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="10" 
                               data-mask="00.00"
                               value="${indiceReajusteBean.percentual}">
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>