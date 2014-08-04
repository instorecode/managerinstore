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
                {title: 'Descri��o', name: 'descricao', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {

            }
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
                            Data de cria��o
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataCadastro="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Descri��o
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-descricao="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de resolu��o
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


                </div>

                <div datagrid="true" data-id="id"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>