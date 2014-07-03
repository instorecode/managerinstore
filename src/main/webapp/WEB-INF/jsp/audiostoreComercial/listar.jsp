<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-comercial/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
        <a style="display: none" xhref="${url}/audiostore-comercial/download-exp" class="btn btn-default btnDownloadEXP"> <i class="fa fa-save"></i> Download do arquivo EXP </a>
        <a style="display: none" xhref="${url}/audiostore-comercial/upload-exp" class="btn btn-default btnUploadEXP"> <i class="fa fa-upload"></i> Enviar EXP para o repositório  </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'id', index: true, filter: true, filterType: 'input'},
                {title: 'Titulo', name: 'titulo', index: true, filter: true, filterType: 'input'},
                {title: 'Arquivo', name: 'arquivo', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {
                jQuery('.btnDownloadEXP').show();
                jQuery('.btnUploadEXP').show();
                var xhref = jQuery('.btnDownloadEXP').attr('xhref') + '/' + data.id;
                var xhref2 = jQuery('.btnUploadEXP').attr('xhref') + '/' + data.id;
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
                    ID
                </div>
                <div class="col-md-8 val"> 
                    <div data-id="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Título
                </div>
                <div class="col-md-8 val"> 
                    <div data-titulo="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Arquivo
                </div>
                <div class="col-md-8 val"> 
                    <div data-arquivo="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Categoria
                </div>
                <div class="col-md-8 val"> 
                    <div data-categoriaNome="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Semana(s) e Horário(s)
                </div>
                <div class="col-md-8 val"> 
                    <div data-semanaHora="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Tipo de interprete
                </div>
                <div class="col-md-8 val"> 
                    <div data-tipoInterprete="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Periodo inicial
                </div>
                <div class="col-md-8 val"> 
                    <div data-periodoInicial="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Periodo final
                </div>
                <div class="col-md-8 val"> 
                    <div data-periodoFinal="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    Dias alternados
                </div>
                <div class="col-md-8 val"> 
                    <div data-diasAlternados="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Tempo total
                </div>
                <div class="col-md-8 val"> 
                    <div data-tempoTotal="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Dependencia primária
                </div>
                <div class="col-md-8 val"> 
                    <div data-dependencia1="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Dependencia secundária
                </div>
                <div class="col-md-8 val"> 
                    <div data-dependencia2="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Dependencia terciária
                </div>
                <div class="col-md-8 val"> 
                    <div data-dependencia2="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Frame inicial
                </div>
                <div class="col-md-8 val"> 
                    <div data-frameInicial="true"></div> 
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4 prop"> 
                    Frame final
                </div>
                <div class="col-md-8 val"> 
                    <div data-frameFinal="true"></div> 
                </div>
            </div>
        </div>

        <div datagrid="true" data-id="id"></div>
    </jsp:body>
</instore:template>