<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-gravadora" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Vozes </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-gravadora">
            <input type="hidden" name="audiostoreGravadoraBean.id" value="${audiostoreGravadoraBean.id}" />

            <div class="row">
                <div class="col-md-9">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="audiostoreGravadoraBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="false" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${audiostoreGravadoraBean.nome}">
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>