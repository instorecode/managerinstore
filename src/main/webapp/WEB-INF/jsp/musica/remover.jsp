<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/musica" class="btn btn-voltar btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
        <a href="${url}/musica/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:body>


        Deseja remover a música ${musicaGeralBean.titulo} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/musica">
            <input type="hidden" name="musicaGeralBean.id" value="${musicaGeralBean.id}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/musica" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>

    </jsp:body>
</instore:template>