<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/lancamento/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [ 
                {title: 'Descrição', name: 'descricao', index: true, filter: true, filterType: 'input'},
                {title: 'Valor', name: 'valor', index: true, filter: true, filterType: 'input'},
                {title: 'Date de lançamento', name: 'mes', index: true, filter: true, filterType: 'input'},
                {title: 'Pagar / Receber', name: 'tipo', index: true, filter: true},
                {title: 'Instituição Financeira', name: 'instituicao', index: true, filter: true},
                {title: 'Finalizado', name: 'finalizado', index: true, filter: true},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {

            }
        </script>
    </jsp:attribute>
    <jsp:body> 

        <div class="block-flat">
            <div class="content">
                <div datagrid-view="true" style="display: none">
                    
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Instituição Financeira
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-instituicao="true"></div> 
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Finalizado
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-finalizado="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Descrição
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-descricao="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de lançamento
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-mes="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Valor
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-valor="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Tipo
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-tipo="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Quem criou o lançamento?
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-usuarioNome="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de finalização do lançamento
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataFechamento="true"></div> 
                        </div>
                    </div>
                </div>

                <div datagrid="true" data-id="id"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>