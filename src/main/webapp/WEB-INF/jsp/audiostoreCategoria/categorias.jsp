<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-categoria/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
        <a style="display: none" xhref="${url}/audiostore-categoria/download-exp" class="btn btn-default btnDownloadEXP"> <i class="fa fa-save"></i> Download do arquivo EXP </a>
        <a style="display: none" xhref="${url}/audiostore-categoria/upload-exp" class="btn btn-default btnUploadEXP"> <i class="fa fa-upload"></i> Enviar EXP para o repositório  </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'Código', name: 'codigo', index: true, filter: true, filterType: 'input'},
                {title: 'Nome da categoria', name: 'categoria', index: true, filter: true, filterType: 'input'},
                {title: 'Nome do cliente', name: 'clienteNome', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {
                jQuery('.btnDownloadEXP').show();
                jQuery('.btnUploadEXP').show();
                var xhref = jQuery('.btnDownloadEXP').attr('xhref') + '/' + data.codigo;
                var xhref2 = jQuery('.btnUploadEXP').attr('xhref') + '/' + data.codigo;
                jQuery('.btnDownloadEXP').attr('href', xhref);
                jQuery('.btnUploadEXP').attr('href', xhref2);
            }

            jQuery(document).ready(function() {
                jQuery('.btnUploadEXP').on('click', function() {
                    var self = jQuery(this);
                    jQuery.ajax({
                        type: 'GET',
                        url: self.attr('href'),
                        beforeSend: function(response) {
                            bootbox.hideAll();
                            bootbox.dialog({
                                message: "Aguarde...",
                                title: "Sistema processando informações",
                                buttons: {}
                            });
                        },
                        success: function(response) {
                            bootbox.hideAll();

                            if (response.success) {
                                bootbox.dialog({
                                    message: "Arquivo enviado para o repositório com sucesso!",
                                    title: "Sistema processando informações",
                                    buttons: {}
                                });
                            } else {
                                bootbox.dialog({
                                    message: "Lamento, não foi possivel enviar o arquivo para o repositório!",
                                    title: "Sistema processando informações",
                                    buttons: {}
                                });
                            }

                            setTimeout(function() {
                                bootbox.hideAll();
                            }, 2000);
                        },
                        error: function(response) {
                            bootbox.hideAll();
                            bootbox.dialog({
                                message: "Lamento, não foi possivel enviar o arquivo para o repositório!",
                                title: "Sistema processando informações",
                                buttons: {}
                            });

                            setTimeout(function() {
                                bootbox.hideAll();
                            }, 2000);
                        }
                    });
                    return false;
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body> 
        <div datagrid-view="true" style="display: none">
            <div class="row">
                <div class="col-md-4 prop"> 
                    Código
                </div>
                <div class="col-md-8 val"> 
                    <div data-codigo="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Nome da categoria
                </div>
                <div class="col-md-8 val"> 
                    <div data-categoria="true"></div> 
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
                    Tipo da categoria
                </div>
                <div class="col-md-8 val"> 
                    <div data-tipo="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Tempo Duração
                </div>
                <div class="col-md-8 val"> 
                    <div data-tempo="true"></div> 
                </div>
            </div>
        </div>

        <div datagrid="true" data-id="codigo"></div>
    </jsp:body>
</instore:template>