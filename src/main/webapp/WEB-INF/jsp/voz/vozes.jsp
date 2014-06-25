<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/voz/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'idvoz', index: true, filter: true, filterType: 'input'},
                {title: 'Nome', name: 'nome', index: true, filter: true, filterType: 'input'},
                {title: 'Nome do cliente', name: 'clienteNome', index: true, filter: true},
                {title: 'E-mail', name: 'email', index: true, filter: true, filterType: 'input'},
                {title: 'Tel.', name: 'tel', index: true, filter: true, filterType: 'input'},
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
                    <div data-idvoz="true"></div> 
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
                    Nome do cliente
                </div>
                <div class="col-md-8 val"> 
                    <div data-clienteNome="true"></div> 
                </div>
            </div>


            <div class="row">
                <div class="col-md-4 prop"> 
                    E-mail
                </div>
                <div class="col-md-8 val"> 
                    <div data-email="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Tel.
                </div>
                <div class="col-md-8 val"> 
                    <div data-tel="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Genero
                </div>
                <div class="col-md-8 val"> 
                    <div data-genero="true"></div> 
                </div>
            </div>
        </div>

        <div datagrid="true" data-id="idvoz"></div>
    </jsp:body>
</instore:template>