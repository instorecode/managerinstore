<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/ocorrencia-solucao" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
        <a href="${url}/ocorrencia-solucao/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Deseja remover a solução da ocorrencia ${ocorrenciaSolucaoBean.descricao} ?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/ocorrencia-solucao">
            <input type="hidden" name="ocorrenciaSolucaoBean.id" value="${ocorrenciaSolucaoBean.id}" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/ocorrencia-solucao" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>