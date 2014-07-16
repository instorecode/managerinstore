<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/lancamento/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'id', index: true, filter: true, filterType: 'input'},
                {title: 'Descrição', name: 'descricao', index: true, filter: true, filterType: 'input'},
                {title: 'Usuário', name: 'usuarioNome', index: true, filter: true, filterType: 'input'},
                {title: 'Valor', name: 'valor', index: true, filter: true, filterType: 'input'},
                {title: 'Date de lançamento', name: 'mes', index: true, filter: true, filterType: 'input'},
                {title: 'Pagar / Receber', name: 'tipo', index: true, filter: true},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {
                
            }

        </script>
    </jsp:attribute>
    <jsp:body> 
        <div datagrid-view="true" style="display: none">
            <div class="row">
                <div class="col-md-4 prop"> 
                    ID
                </div>
                <div class="col-md-8 val"> 
                    <div data-id="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Descrição
                </div>
                <div class="col-md-8 val"> 
                    <div data-descricao="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Data de lançamento
                </div>
                <div class="col-md-8 val"> 
                    <div data-mes="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Valor
                </div>
                <div class="col-md-8 val"> 
                    <div data-valor="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Tipo
                </div>
                <div class="col-md-8 val"> 
                    <div data-tipo="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Quem criou o lançamento?
                </div>
                <div class="col-md-8 val"> 
                    <div data-usuarioNome="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Data de finalização do lançamento
                </div>
                <div class="col-md-8 val"> 
                    <div data-dataFechamento="true"></div> 
                </div>
            </div>
        </div>

        <div datagrid="true" data-id="id"></div>
    </jsp:body>
</instore:template>