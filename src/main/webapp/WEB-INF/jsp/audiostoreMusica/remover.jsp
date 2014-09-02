<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-musica" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Audiostore Categorias </a>
        <a href="${url}/audiostore-musica/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover o contato ${musicaGeralBean.titulo} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/musica/programacao-audiostore/${idmusicaGeral}">
            <input type="hidden" name="audiostoreGravadoraBean.id" value="${audiostoreGravadoraBean.id}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/musica/programacao-audiostore/${idmusicaGeral}" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>