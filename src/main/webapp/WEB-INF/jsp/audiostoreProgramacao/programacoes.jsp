<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-programacao/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
<!--        <a style="display: none" xhref="${url}/audiostore-programacao/download-exp" class="btn btn-default btnDownloadEXP"> <i class="fa fa-save"></i> Download do arquivo EXP </a>
        <a style="display: none" xhref="${url}/audiostore-programacao/upload-exp" class="btn btn-default btnUploadEXP"> <i class="fa fa-upload"></i> Enviar EXP para o repositório  </a>-->
    </jsp:attribute>

    <jsp:attribute name="detailsButton">
<!--        <a data-toggle="tooltip" data-placement="bottom" data-original-title="Download do arquivo exp" style="display: none" xhref="${url}/audiostore-programacao/download-exp" class="btn btn-default btnDownloadEXP"> <i class="fa fa-download"></i></a>
        <a data-toggle="tooltip" data-placement="right" data-original-title="Upload para repositorio de arquivos exp" style="display: none" xhref="${url}/audiostore-programacao/upload-exp" class="btn btn-default btnUploadEXP"> <i class="fa fa-upload"></i></a>-->
        <a data-toggle="tooltip" data-placement="right" data-original-title="Clonar" style="display: none" xhref="${url}/audiostore-programacao/cadastrar?clonar=" class="btn btn-default btn_clonar"> <i class="fa fa-random"></i></a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'id', index: true, filter: true, filterType: 'input'},
                {title: 'Descrição', name: 'descricao', index: true, filter: true, filterType: 'input'},
                {title: 'Nome do cliente', name: 'clienteNome', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {
                jQuery('.btnDownloadEXP').show();
                jQuery('.btnUploadEXP').show();
                jQuery('.btn_clonar').show();
                
                var xhref = jQuery('.btnDownloadEXP').attr('xhref') + '/' + data.id;
                var xhref2 = jQuery('.btnUploadEXP').attr('xhref') + '/' + data.id;
                var xhref3 = jQuery('.btn_clonar').attr('xhref') + data.id;
                
                jQuery('.btnDownloadEXP').attr('href', xhref);
                jQuery('.btnUploadEXP').attr('href', xhref2);
                jQuery('.btn_clonar').attr('href', xhref3);
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
                            Nome do cliente
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-clienteNome="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de inicio
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataInicio="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de termino
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataFinal="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Horário de inicio
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-horaInicio="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Horário de termino
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-horaFinal="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Dias da semana
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-diasSemana="true"></div> 
                        </div>
                    </div>           
                </div>
                <div datagrid="true" data-id="id"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>