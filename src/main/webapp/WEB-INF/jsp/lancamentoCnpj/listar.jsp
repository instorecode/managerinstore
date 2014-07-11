<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/lancamento-entidade/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <link rel="stylesheet" type="text/css" media="all" href="${url_css}jquery.jqplot.css"/>
        <!--pizza-->
        <script type="text/javascript" charset="utf-8" src="${url_js}jquery.jqplot.js"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}jqplot.pieRenderer.js"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}jqplot.donutRenderer.js"></script>

        <!--barra-->
        <script type="text/javascript" charset="utf-8" src="${url_js}jqplot.barRenderer.js"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}jqplot.categoryAxisRenderer.js"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}jqplot.pointLabels.js"></script>



        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'id', index: true, filter: true, filterType: 'input'},
                {title: 'Nome', name: 'nome', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {

            }

            jQuery(document).ready(function() {

                // pizza
                var data = new Array();
                jQuery.ajax({
                    async: true,
                    type: 'GET',
                    url: '${url}/lancamento-entidade?datajson=false&rel=1',
                    success: function(resp) {
                        for (i in resp) {
                            var item = resp[i];
                            data[i] = [item.descricao, item.valor];
                        }

                        var plot1 = jQuery.jqplot('pizza', [data], {
                            height: 500,
                            seriesDefaults: {
                                // Make this a pie chart.
                                renderer: jQuery.jqplot.PieRenderer,
                                rendererOptions: {
                                    // Put data labels on the pie slices.
                                    // By default, labels show the percentage of the slice.
                                    showDataLabels: true
                                }
                            },
                            legend: {
                                show: true,
                                location: 's',
                                rendererOptions: {numberColumns: 4}
                            },
                            grid: {
                                background: 'transparent',
                                borderColor: 'transparent',
                                shadow: false
                            }

                        });
                    }
                });


                // barra

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
                    Nome
                </div>
                <div class="col-md-8 val"> 
                    <div data-nome="true"></div> 
                </div>
            </div>

        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">Gráfico de percentual do valor do lançamento</div>
                    <div class="panel-body">
                        <div id="pizza" ></div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">Relatório simples</div>
                    <div class="panel-body">
                        
                    </div>
                </div>
            </div>
        </div>

        <div datagrid="true" data-id="id"></div>


    </jsp:body>
</instore:template>