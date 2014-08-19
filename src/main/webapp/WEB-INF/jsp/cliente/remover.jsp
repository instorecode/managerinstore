<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/clientes" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Clientes </a>
        <a href="${url}/cliente/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Lamento, o cliente náo pode ser removido.
        <br >
        Deseja desativá-la o cliente ${cliente.nome} - ${dadosCliente.nomeFantasia}?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/clientes">
            <input type="hidden" name="cliente.idcliente" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Desabilitar
            </button>
            <a href="${url}/clientes" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>