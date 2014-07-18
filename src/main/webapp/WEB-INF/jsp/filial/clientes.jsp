<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/cliente/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script>
            var gridColumn = [
                {title: 'ID', name: 'idcliente', index: true, filter: true, filterType: 'input'},
                {title: 'Matriz', name: 'parente', index: true, filter: true, filterType: 'input'},
                {title: 'Nome', name: 'nome', index: true, filter: true, filterType: 'input'},
                {title: '� matriz', name: 'matriz', index: true, filter: true},
                {title: '� instore', name: 'instore', index: true, filter: true}
            ];

            function onRowDblClick(data) {

            }
            
            function onRowClick(data) {
            }
        </script>
    </jsp:attribute>
        
    <jsp:attribute name="detailsButton">
        <a href="${url}/filial" data-toggle="tooltip" data-placement="bottom" data-original-title="Filiais" type="button" class="btn btn-default"><i class="fa fa-cubes"></i></a>
    </jsp:attribute>
        
        
    <jsp:body> 
        <div class="block-flat">
            <div class="content">
                <div datagrid-view="true" style="display: none">
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            ID
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-idcliente="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Empresa Matriz
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-parente="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Nome
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-nome="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            � matriz
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-matriz="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            � instore
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-instore="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Nome fantasia
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-nomeFantasia="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            CNPJ
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-cnpj="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de inicio do contrato
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataInicioContrato="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de termino do contrato
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataTerminoContrato="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Indice de reajuste do contrato
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-indiceReajusteContrato="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Renova��o automatica
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-renovacaoAutomatica="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Raz�o social
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-razaoSocial="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            CEP
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-cep="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Estado
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-estado="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Cidade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-cidade="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Bairro
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-bairro="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Logradouro
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-logradouro="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            N�mero
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-numero="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Complemento
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-complemento="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Situa��o
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-situacao="true"></div> 
                        </div>
                    </div>
                </div>

                <div datagrid="true" data-id="idcliente"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>