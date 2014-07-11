<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/lancamento/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
        <a href="${url}/lancamento" class="btn btn-default btnEfetuarLB" style="display: none;"> <i class="fa fa-money"></i> Efetuar lançamento </a>
        <span class="jaFoiEfetuado" style="display: none;">&nbsp;&nbsp;Lançamento já foi efetuado </span>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'id', index: true, filter: true, filterType: 'input'},
                {title: 'Descrição', name: 'descricao', index: true, filter: true, filterType: 'input'},
                {title: 'Usuário', name: 'usuarioNome', index: true, filter: true, filterType: 'input'},
                {title: 'Valor', name: 'valor', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {
                jQuery.ajax({
                    type: 'GET',
                    data: {
                        efetuar: true,
                        id: data.id
                    },
                    success: function(response) {
                        if (response) {
                            jQuery('.btnEfetuarLB').show();
                            jQuery('.btnEfetuarLB').attr('xid', data.id);
                            jQuery('.jaFoiEfetuado').hide();

                        } else {
                            jQuery('.btnEfetuarLB').hide();
                            jQuery('.jaFoiEfetuado').show();
                        }
                    },
                    error: function(response) {
                        jQuery('.jaFoiEfetuado').hide();
                        jQuery('.btnEfetuarLB').hide();
                        bootbox.hideAll();
                        bootbox.dialog({
                            message: "Lamento, ouve um erro interno. Não foi possiveç efetuar o lançamento",
                            title: "Sistema processando informações",
                            buttons: {}
                        });

                        setTimeout(function() {
                            bootbox.hideAll();
                        }, 2000);
                    }
                });
            }

            jQuery(document).ready(function() {
                jQuery('.btnEfetuarLB').on('click', function() {
                    var xid = jQuery(this).attr('xid');
                    jQuery.ajax({
                        async: true,
                        type: 'GET',
                        data: {
                            id:xid,
                            efetuarJa: true
                        },
                        dataType: 'json',
                        beforeSend: function() {
                            bootbox.hideAll();
                            bootbox.dialog({
                                message: "Aguarde...",
                                title: "Sistema processando informações",
                                buttons: {}
                            });
                        },
                        success: function(data) {
                            dialogAjax(data.response);
                            window.location.reload();
                        },
                        error: function(data) {
                            dialogAjax(data.response);
                            window.location.reload();
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
                    È débito?
                </div>
                <div class="col-md-8 val"> 
                    <div data-debito="true"></div> 
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 prop"> 
                    È crédito?
                </div>
                <div class="col-md-8 val"> 
                    <div data-credito="true"></div> 
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

        </div>

        <div datagrid="true" data-id="id"></div>
    </jsp:body>
</instore:template>