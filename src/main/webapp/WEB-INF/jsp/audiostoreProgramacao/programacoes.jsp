<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-programacao/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
        <a style="display: none" xhref="${url}/audiostore-programacao/download-exp" class="btn btn-default btnDownloadEXP"> <i class="fa fa-save"></i> Download do arquivo EXP </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
            {title: 'ID', name: 'id', index: true, filter: true, filterType:'input'},
            {title: 'Descrição', name: 'descricao', index: true, filter: true, filterType:'input'},
            {title: 'Nome do cliente', name: 'clienteNome', index: true, filter: true, filterType:'input'},
            ];

            function onRowDblClick(data) {

            }
            
            function onRowClick(data) {
                jQuery('.btnDownloadEXP').show();
                var xhref = jQuery('.btnDownloadEXP').attr('xhref')+'/'+data.id;
                jQuery('.btnDownloadEXP').attr('href', xhref);
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
                    Nome do cliente
                </div>
                <div class="col-md-8 val"> 
                    <div data-clienteNome="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Data de inicio
                </div>
                <div class="col-md-8 val"> 
                    <div data-dataInicio="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Data de termino
                </div>
                <div class="col-md-8 val"> 
                    <div data-dataFinal="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Horário de inicio
                </div>
                <div class="col-md-8 val"> 
                    <div data-horaInicio="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Horário de termino
                </div>
                <div class="col-md-8 val"> 
                    <div data-horaFinal="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Dias da semana
                </div>
                <div class="col-md-8 val"> 
                    <div data-diasSemana="true"></div> 
                </div>
            </div>           
        </div>
        <div datagrid="true" data-id="id"></div>
    </jsp:body>
</instore:template>