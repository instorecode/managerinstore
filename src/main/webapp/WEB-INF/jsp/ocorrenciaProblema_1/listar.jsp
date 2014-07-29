<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <div class="btn-group">
        <a href="${url}/ocorrencia-solucao/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'id', index: true, filter: true, filterType: 'input'},
                {title: 'Descrição', name: 'descricao', index: true, filter: true, filterType: 'input'},
                {title: 'Prazo', name: 'prazoResolucao', index: true, filter: true, filterType: 'input'},
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
                            ID
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-id="true"></div> 
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
                            Prazo
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-prazoResolucao="true"></div> 
                        </div>
                    </div>

                </div>

                <div datagrid="true" data-id="id"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>