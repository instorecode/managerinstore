<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-categorias" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Audiostore Categorias </a>
        <a href="${url}/audiostore-categoria/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover a audiostore categoria ${audiostoreCategoriaBean.categoria} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-categorias">
            <input type="hidden" name="id" value="${audiostoreCategoriaBean.idaudiostoreCategoria}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/audiostore-categorias" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>