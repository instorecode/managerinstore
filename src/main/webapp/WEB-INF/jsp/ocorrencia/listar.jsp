<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <div class="btn-group">
            <a href="${url}/ocorrencia/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'ID', name: 'id', index: true, filter: true, filterType: 'input'},
                {title: 'Descrição', name: 'descricao', index: true, filter: true, filterType: 'input'},
                {title: 'Relacionado ao usuário', name: 'statusUsuarioNome', index: true, filter: true},
                {title: 'Situação', name: 'statusNome', index: true, filter: true},
                {title: 'Prioridade', name: 'prioridade', index: true, filter: true},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {

            }

            function onReady(data) {
                jQuery('.bbGrid-row').each(function() {
                    var tr = jQuery(this);
                    var td = tr.children('td:eq(4)');
                    var ob = data[jQuery(this).index()].attributes;

                    tr.children('td').css({
                        'background-color': ob.statusCor + ' !important',
                        'color': '#FFF !important'
                    }).on('mouseover', function() {
                        tr.children('td').css({
                            'background-color': ob.statusCor + ' !important',
                            'color': '#FFF !important'
                        });
                    });
                });
            }

            jQuery(document).ready(function() {

            });
        </script>
    </jsp:attribute>
    <jsp:body> 


        <div class="block-flat">
            <div class="content">
                <h3>Total por situação</h3>
                
                <table class="no-border">
                    <thead class="no-border">
                        <tr>
                            <th>Situação</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody class="no-border-x no-border-y">
                        <c:forEach items="${ocorrenciaRelatorioRapidoList}" var="item">
                            <tr>
                                <td>
                                    <label style="background-color: ${item.ocorrenciaStatus.cor}; color: #fff; padding:0px 5px 0px 5px;">
                                        ${item.ocorrenciaStatus.descricao}
                                    </label>
                                </td>
                                <td>${item.total}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>


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
                            Cliente
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-cliente="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Prioridade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-ocorrenciaPrioridade="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Relator da ocorrencia
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-usuarioCriacao="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de criação
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataCadastro="true"></div> 
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
                            Data de resolução
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataResolucao="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Origem
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-ocorrenciaOrigem="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Situação
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-statusNome="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Relacionado ao usuário
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-statusUsuarioNome="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Prioridade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-prioridade="true"></div> 
                        </div>
                    </div>


                </div>

                <div datagrid="true" data-id="id"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>