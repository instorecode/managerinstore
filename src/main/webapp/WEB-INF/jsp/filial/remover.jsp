<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/filial/${cliente.parente}" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Filiais </a>
        <a href="${url}/filial/cadastrar/${cliente.parente}" class="btn btn-voltar btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Lamento, a filial náo pode ser removido.
        <br >
        Deseja desativá-la a filial ${cliente.nome}?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/filial/${cliente.parente}">
            <input type="hidden" name="cliente.idcliente" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Desabilitar
            </button>
            <a href="${url}/filial/${cliente.parente}" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>