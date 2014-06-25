<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-categorias" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Audiostore Categorias </a>
        <a href="${url}/audiostore-categoria/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover o contato ${contatoClienteBean.nome} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/contatos">
            <input type="hidden" name="contatoClienteBean.idcontatoCliente" value="${contatoClienteBean.idcontatoCliente}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/audiostore-categorias" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>