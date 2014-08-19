<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/indice-reajuste" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
        <a href="${url}/indice-reajuste/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:body>

        <c:if test="${podeRemover}">
            Deseja remover a origem da ocorrencia ${indiceReajusteBean.descricao} ?
            <hr />
            <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/indice-reajuste">
                <input type="hidden" name="indiceReajusteBean.id" value="${indiceReajusteBean.id}" />
                <button type="submit" class="btn btn-default">
                    <i class="fa fa-thumbs-o-down"></i> Remover
                </button>
                <a href="${url}/indice-reajuste" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
            </form>
        </c:if>
            
        <c:if test="${not podeRemover}">
            <div class="alert alert-warning alert-white rounded">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <div class="icon"><i class="fa fa-warning"></i></div>
                <strong>Importante!</strong> &nbsp;&nbsp; Existem clientes que estão relacionados ao indice de reajuste.
            </div>
        </c:if>
    </jsp:body>
</instore:template>