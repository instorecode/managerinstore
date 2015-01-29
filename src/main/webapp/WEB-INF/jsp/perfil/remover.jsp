<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/perfil" class="btn btn-voltar btn-default"> <i class="fa fa-hand-o-left"></i> Perfis </a>
        <a href="${url}/perfil/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover o contato ${perfilBean.nome} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/perfil">
            <input type="hidden" name="perfilBean.idperfil" value="${perfilBean.idperfil}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/audiostore-categorias" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>