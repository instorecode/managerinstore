<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/lancamento" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lançamentos </a>
        <a href="${url}/lancamento/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover a entidade  ${lancamentoBean.descricao} de lançamento?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/lancamento">
            <input type="hidden" name="lancamentoBean.id" value="${lancamentoBean.id}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/lancamento-entidade" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>