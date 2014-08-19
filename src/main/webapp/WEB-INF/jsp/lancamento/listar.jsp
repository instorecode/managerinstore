<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
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
//                jQuery('.total_debito').text("R$ -" + pagar);
//                jQuery('.total_credito').text("R$ +" + receber);
            }

            jQuery(document).ready(function() {
                var pagar = 0;
                var receber = 0;

                jQuery('tr.hoje').each(function() {
                    var tr = jQuery(this);
                    var td = tr.children('td:eq(1)');
                    var td_tipo = tr.children('td:eq(2)');
                    var td_finalizado = jQuery.trim(tr.children('td:eq(5)').text());


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

                });

                var p = " " + pagar.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
                var r = " " + receber.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
                pagar = p;
                receber = r;


//                jQuery('.total_hoje_debito').text("R$ -" + pagar);
//                jQuery('.total_hoje_credito').text("R$ +" + receber);
            });
        </script>
    </jsp:attribute>
    <jsp:body> 

        <div class="block">
            <div class="content">
                <h2>Total</h2>
                <table class="no-border">
                    <thead class="no-border">
                        <tr>
                            <th class="text-center"></th>
                            <th class="text-center" style="color: red; text-align: right">Débito</th>
                            <th class="text-center" style="color: green; text-align: right">Crédito</th>
                        </tr>
                    </thead>
                    <tbody class="no-border-y">
                            <tr>
                                <td>De hoje</td>
                                <td style="color: red; text-align: right"> ${atrasado_pagar} </td>
                                <td style="color: green; text-align: right"> ${atrasado_receber} </td>
                            </tr>
                            <tr>
                                <td>Do mês atual</td>
                                <td style="color: red; text-align: right">  ${pagar_mes}</td>
                                <td style="color: green; text-align: right">  ${receber_mes}</td>
                            </tr>
                            <tr>
                                <td>Geral</td>
                                <td style="color: red; text-align: right">  ${pagar}</td>
                                <td style="color: green; text-align: right"> ${receber} </td>
                            </tr>
                    </tbody>
                </table>
            </div>
        </div>


        <div class="block">
            <div class="header">
                <h2>Lançamentos de Hoje</h2>
            </div>
            <div class="content">
                <table class="no-border">
                    <thead class="no-border">
                        <tr>
                            <th style="width:40%;">Descrição</th>
                            <th class="text-center">Valor</th>
                            <th class="text-center">Pagar/Receber</th>
                            <th class="text-center">Instituição Financeira</th>
                            <th class="text-center">Finalizado</th>
                        </tr>
                    </thead>
                    <tbody class="no-border-y">
                        <c:forEach items="${listaDeHoje}" var="item">
                            <tr class="hoje">
                                <td>
                                    <a href="${url}/lancamento/atualizar/${item.id}"> ${item.descricao} </a>
                                </td>
                                <td class="text-center"> 
                                    ${item.valor}
                                </td>
                                <td class="text-center">${item.tipo}</td>
                                <td class="text-center">${item.instituicao}</td>
                                <td class="text-center">
                                    <span class="label label-${item.finalizado eq 'Sim' ? 'primary' : 'danger'}  pull-right"> ${item.finalizado eq 'Sim' ? '<i class="fa fa-arrow-up"></i>' : '<i class="fa fa-arrow-down"></i>'} ${item.finalizado}</span>
                                </td>
                            </tr>
                        </c:forEach>

                        <tr class="hoje">
                            <td colspan="5" style="text-align: right">
                                Totais: &nbsp;&nbsp;
                                <b class="total_hoje_debito" style="color: red;"> Débito ${atrasado_pagar}</b>
                                &nbsp;&nbsp; | &nbsp;&nbsp;
                                <b class="total_hoje_credito" style="color: green">Crédito ${atrasado_receber}</b>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="block">
            <div class="header">
                <h2>Lançamentos Pendentes</h2>
            </div>
            <div class="content">
                <table class="no-border">
                    <thead class="no-border">
                        <tr>
                            <th style="width:40%;">Descrição</th>
                            <th class="text-center">Valor</th>
                            <th class="text-center">Pagar/Receber</th>
                            <th class="text-center">Instituição Financeira</th>
                            <th class="text-center">Finalizado</th>
                        </tr>
                    </thead>
                    <tbody class="no-border-y">
                        <c:forEach items="${beanListByNaoFechado}" var="item">
                            <tr class="hoje">
                                <td>
                                    <a href="${url}/lancamento/atualizar/${item.id}"> ${item.descricao} </a>
                                </td>
                                <td class="text-center"> 
                                    ${item.valor}
                                </td>
                                <td class="text-center">${item.tipo}</td>
                                <td class="text-center">${item.instituicao}</td>
                                <td class="text-center">
                                    <span class="label label-${item.finalizado eq 'Sim' ? 'primary' : 'danger'}  pull-right"> ${item.finalizado eq 'Sim' ? '<i class="fa fa-arrow-up"></i>' : '<i class="fa fa-arrow-down"></i>'} ${item.finalizado}</span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="block-flat">
            <div class="header">
                <h2>Todos os lançamentos</h2>
            </div>
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