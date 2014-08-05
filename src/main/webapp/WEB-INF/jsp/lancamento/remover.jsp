<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 

<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/lancamento" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lan�amentos </a>
        <a href="${url}/lancamento/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:body>
        <c:if test="${lancamentoBean.datFechamento ne null}">
            <div class="alert alert-warning alert-white rounded msg_err0">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <div class="icon"><i class="fa fa-warning"></i></div>
                <strong>Importante!</strong> &nbsp;&nbsp;O lan�amento j� foi finalizado e n�o pode ser removido
            </div>
        </c:if>

        <c:if test="${lancamentoBean.datFechamento eq null}">
            Deseja remover a entidade  ${lancamentoBean.descricao} de lan�amento?
            <hr />
            <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/lancamento">
                <input type="hidden" name="lancamentoBean.id" value="${lancamentoBean.id}" />
                <button type="submit" class="btn btn-default">
                    <i class="fa fa-thumbs-o-down"></i> Remover
                </button>
                <a href="${url}/lancamento-entidade" class="btn btn-danger"> <i class="fa fa-times"></i> N�o </a>
            </form>
        </c:if>
    </jsp:body>
</instore:template>