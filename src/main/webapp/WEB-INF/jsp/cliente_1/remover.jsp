<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template>
    <jsp:attribute name="submenu">
        <a href="${url}/contatos" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Contatos </a>
        <a href="${url}/contato/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
     
    <jsp:body>
        Lamento, o cliente náo pode ser removido.
        <br > 
        Deseja desativá-la o contato ${contatoClienteBean.nome} do setor ${contatoClienteBean.setor} da empresa ${contatoClienteBean.dadosCliente.nomeFantasia}?
        <hr />
        <form id="rm_cliente" method="POST" data-form="true" data-success-url="${url}/contatos">
            <input type="hidden" name="cliente.idcliente" />
            <button type="submit" class="btn btn-default">
                <i class="fa fa-thumbs-o-down"></i> Remover
            </button>
            <a href="${url}/contatos" class="btn btn-danger"> <i class="fa fa-times"></i> Não </a>
        </form>
        
    </jsp:body>
</instore:template>