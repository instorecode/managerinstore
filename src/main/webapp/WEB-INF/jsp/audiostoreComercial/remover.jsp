<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-comercial" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Comerciais </a>
        <a href="${url}/audiostore-comercial/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover o contato ${audiostoreComercialBean.titulo} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-comercial">
            <input type="hidden" name="audiostoreComercialBean.id" value="${audiostoreComercialBean.id}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/audiostore-comercial" class="btn btn-danger"> <i class="fa fa-times"></i> N�o </a>
        </form>
        
    </jsp:body>
</instore:template>