<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">
    
    <jsp:attribute name="submenu">
        <a href="${url}/cliente/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>
        
    <jsp:attribute name="gridColumn">
        <script>
            var gridColumn = [
                {title: 'ID', name: 'idcliente', index: true, filter: true, filterType:'input'},
                {title: 'Matriz', name: 'parente', index: true, filter: true, filterType:'input'},
                {title: 'Nome', name: 'nome', index: true, filter: true, filterType:'input'},
                {title: 'È matriz', name: 'matriz', index: true , filter: true},
                {title: 'È instore', name: 'instore', index: true, filter: true}
            ];

            function onRowDblClick(data) {

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
                    <div data-idcliente="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Parente
                </div>
                <div class="col-md-8 val"> 
                    <div data-parente="true"></div> 
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 prop"> 
                    Nome
                </div>
                <div class="col-md-8 val"> 
                    <div data-nome="true"></div> 
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 prop"> 
                    Matriz
                </div>
                <div class="col-md-8 val"> 
                    <div data-matriz="true"></div> 
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 prop"> 
                    Instore
                </div>
                <div class="col-md-8 val"> 
                    <div data-instore="true"></div> 
                </div>
            </div>
        </div>

        <div datagrid="true" data-id="idcliente"></div>
    </jsp:body>
</instore:template>