<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/ocorrencia" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
        <a href="${url}/ocorrencia/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover a origem da ocorrencia ${ocorrenciaBean.descricao} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/ocorrencia">
            <input type="hidden" name="ocorrenciaBean.id" value="${ocorrenciaBean.id}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/ocorrencia" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>