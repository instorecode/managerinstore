<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-programacao" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Audiostore Programações </a>
        <a href="${url}/audiostore-programacao/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover a audiostore categoria ${audiostoreProgramacaoBean.descricao} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-programacao">
            <input type="hidden" name="id" value="${audiostoreProgramacaoBean.id}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/audiostore-categorias" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>