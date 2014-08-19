<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/lancamento-entidade/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
        <a href="${url}/lancamento-entidade/relatorio" class="btn btn-default"> <i class="fa fa-bar-chart-o"></i></a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">


        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'id', index: true, filter: true, filterType: 'input'},
                {title: 'Nome', name: 'nome', index: true, filter: true, filterType: 'input'},
                {title: 'CNPJ', name: 'cnpj', index: true, filter: true, filterType: 'input'},
                {title: 'Saldo disponivel', name: 'saldoDisponivel', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {

            }

        </script>
    </jsp:attribute>
        
    <jsp:attribute name="detailsButton">
        
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
                            <div data-id="true"></div> 
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
                            Saldo dosponivel
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-saldoDisponivel="true"></div> 
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
                </div>

                <div datagrid="true" data-id="id"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>