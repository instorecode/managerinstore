<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/filial/cadastrar/${id}" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script>
            var gridColumn = [
                {title: 'ID', name: 'idcliente', index: true, filter: true, filterType: 'input'},
                {title: 'Nome', name: 'nome', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }
            
            function onRowClick(data) {
                jQuery('.btnCont').on('click', function(){
                    var self = jQuery(this);
                    self.attr('href',self.attr('xhref')+'/'+data.idcliente);
                });
            }
        </script>
    </jsp:attribute>
        
    <jsp:attribute name="detailsButton">
        <a xhref="${url}/contatos" data-toggle="tooltip" data-placement="bottom" data-original-title="Contatos" type="button" class="btn btn-default btnCont"><i class="fa fa-users"></i></a>
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
                            <div data-idcliente="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Nome
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-nome="true"></div> 
                        </div>
                    </div>
               

                    
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            CEP
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-cep="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Estado
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-estado="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Cidade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-cidade="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Bairro
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-bairro="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Logradouro
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-logradouro="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Número
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-numero="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Complemento
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-complemento="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Situação
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-situacao="true"></div> 
                        </div>
                    </div>
                </div>

                <div datagrid="true" data-id="idcliente" data-remove-param="1"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>