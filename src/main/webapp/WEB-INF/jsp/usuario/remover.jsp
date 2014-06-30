<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/usuario" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Audiostore Categorias </a>
        <a href="${url}/usuario/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover o contato ${usuarioBean.nome} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/usuario">
            <input type="hidden" name="usuarioBean.idusuario" value="${usuarioBean.idusuario}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/usuarios" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
    </jsp:body>
</instore:template>