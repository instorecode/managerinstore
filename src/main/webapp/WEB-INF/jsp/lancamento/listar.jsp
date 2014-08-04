<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/lancamento/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'Descrição', name: 'descricao', index: true, filter: true, filterType: 'input'},
                {title: 'Valor', name: 'valor', index: true, filter: true, filterType: 'input'},
                {title: 'Date de lançamento', name: 'mes', index: true, filter: true, filterType: 'input'},
                {title: 'Pagar / Receber', name: 'tipo', index: true, filter: true},
                {title: 'Instituição Financeira', name: 'instituicao', index: true, filter: true},
                {title: 'Finalizado', name: 'finalizado', index: false, filter: true},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {

            }

            function onReady() {
                pagar = 0;
                receber = 0;
                jQuery('.bbGrid-row').each(function() {
                    var tr = jQuery(this);
                    var td = tr.children('td:eq(1)');
                    var td_tipo = tr.children('td:eq(3)');
                    var td_finalizado = jQuery.trim(tr.children('td:eq(5)').text());
                    console.log(td_finalizado);
                    if (td_finalizado != "Sim") {
                        var v1 = td.text();
                        v1 = v1.replace('R', '');
                        v1 = v1.replace('$', '');

                        if (v1.indexOf('.') != -1 && v1.indexOf(',') != -1) {
                            v1 = v1.replace('.', '');
                            v1 = v1.replace(',', '.');
                        }

                        if (v1.indexOf('.') == -1 && v1.indexOf(',') != -1) {
                            v1 = v1.replace(',', '.');
                        }

                        v1 = jQuery.trim(v1);

                        if (jQuery.trim(td_tipo.text()) == 'Pagar') {
                            pagar += parseFloat(v1);
                        }

                        if (jQuery.trim(td_tipo.text()) == 'Receber') {
                            receber += parseFloat(v1);
                        }
                    }
                });

                var p = " " + pagar.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
                var r = " " + receber.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
                pagar = p;
                receber = r;
                jQuery('.total_debito').text("R$ -" + pagar);
                jQuery('.total_credito').text("R$ +" + receber);
            }
            
            jQuery(document).ready(function(){
//                jQuery('select.finalizado').val('Não');
//                jQuery('select.finalizado').change();
            });
        </script>
    </jsp:attribute>
    <jsp:body> 
        <div class="block">
            <div class="header">
                <h2><i class="fa  fa-money"></i>Totais</h2>
            </div>
            <div class="content no-padding">
                <div class="fact-data text-center">
                    <h3>Débito</h3>
                    <h2 class="total_debito" style="color: red;"></h2>
                </div>
                <div class="fact-data text-center">
                    <h3>Crédito</h3>							
                    <h2 class="total_credito" style="color: green">13</h2>
                </div>
            </div>
        </div>

        <div class="block-flat">
            <div class="content">
                <div datagrid-view="true" style="display: none">

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Instituição Financeira
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-instituicao="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Finalizado
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-finalizado="true"></div> 
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
                            Data de lançamento
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-mes="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Valor
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-valor="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Tipo
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-tipo="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Quem criou o lançamento?
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-usuarioNome="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de finalização do lançamento
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataFechamento="true"></div> 
                        </div>
                    </div>
                </div>



                <div datagrid="true" data-id="id"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>