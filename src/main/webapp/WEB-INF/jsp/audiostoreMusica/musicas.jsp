<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-musica/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
        <a style="display: none" xhref="${url}/audiostore-musica/download-exp" class="btn btn-default btnDownloadEXP"> <i class="fa fa-save"></i> Download do arquivo EXP </a>
        <a style="display: none" xhref="${url}/audiostore-musica/upload-exp" class="btn btn-default btnUploadEXP"> <i class="fa fa-upload"></i> Enviar EXP para o repositório  </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'id', index: true, filter: true, filterType: 'input'},
                {title: 'Titulo', name: 'titulo', index: true, filter: true, filterType: 'input'},
                {title: 'Arquivo', name: 'arquivo', index: true, filter: true, filterType: 'input'},
                {title: 'Gravadora', name: 'gravadora', index: true, filter: true},
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
                            Título
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-titulo="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Arquivo
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-arquivo="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Gravadora
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-gravadora="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Categoria Primária
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-categoria1="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Categoria Secundária
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-categoria2="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Categoria Terciária
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-categoria3="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Interprete
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-interprete="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Tipo Interprete
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-tipoInterprete="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Cut
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-cut="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Crossover
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-crossover="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de vencimento do crossover
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataVencimentoCrossover="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            dias execucao ( referente a categoria primária )
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-diasExecucao1="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            dias execucao ( referente a categoria secundária )
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-diasExecucao2="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Afinidade primário
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-afinidade1="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Afinidade secundário
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-afinidade2="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Afinidade terciário
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-afinidade3="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Afinidade quaternário
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-afinidade4="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Ano de gravação
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-anoGravacao="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Velocidade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-velocidade="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de cadastro da musica no sistema
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-data="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Ùltima execução
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-ultimaExecucao="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Tempo total
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-tempoTotal="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Random
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-random="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Quantidade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-qtdePlayer="true"></div> 
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de vencimento
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataVencimento="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Frame inicial
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-frameInicio="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Frame final
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-frameFinal="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Mensagem
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-msg="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Sem som?
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-semSom="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Super crossover
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-superCrossover="true"></div> 
                        </div>
                    </div>
                </div>

                <div datagrid="true" data-id="id"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>