<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        <a href="${url}/cliente/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script>
            var gridColumn = [
                {title: 'ID', name: 'idcliente', index: true, filter: true, filterType: 'input'},
                {title: 'Nome', name: 'nome', index: true, filter: true, filterType: 'input'},
                {title: 'Nome Fantasia', name: 'nomeFantasia', index: true, filter: true, filterType: 'input'},
                {title: 'CNPJ', name: 'cnpj', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }
            
            function onRowClick(data) {
                jQuery('.btnFil').on('click', function(){
                    var self = jQuery(this);
                    self.attr('href',self.attr('xhref')+'/'+data.idcliente);
                });
                
                jQuery('.btnCont').on('click', function(){
                    var self = jQuery(this);
                    self.attr('href',self.attr('xhref')+'/'+data.idcliente);
                });
            }
        </script>
    </jsp:attribute>
        
    <jsp:attribute name="detailsButton">
        <a xhref="${url}/filial"  data-toggle="tooltip" data-placement="bottom" data-original-title="Filiais" type="button" class="btn btn-default btnFil"><i class="fa fa-cubes"></i></a>
        <a xhref="${url}/contatos" data-toggle="tooltip" data-placement="bottom" data-original-title="Contatos" type="button" class="btn btn-default btnCont"><i class="fa fa-users"></i></a>
        <a xhref="${url}/cliente-configuracao" data-toggle="tooltip" data-placement="bottom" data-original-title="Configurações" type="button" class="btn btn-default btnCont"><i class="fa fa-gear"></i></a>
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
                            Empresa Matriz
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-parente="true"></div> 
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
                            È matriz
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-matriz="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            È instore
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-instore="true"></div> 
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Nome fantasia
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-nomeFantasia="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            CNPJ
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-cnpj="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de inicio do contrato
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataInicioContrato="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Data de termino do contrato
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-dataTerminoContrato="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Indice de reajuste do contrato
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-indiceReajusteContrato="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Renovação automatica
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-renovacaoAutomatica="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Razão social
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-razaoSocial="true"></div> 
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
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Local dos arquivos de musica
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-local1="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Destino dos arquivos de musica
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-local2="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Local dos arquivos de spot
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-local3="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Destino dos arquivos de spot
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-local4="true"></div> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Destino dos arquivos Exp
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-local5="true"></div> 
                        </div>
                    </div>
                </div>

                <div datagrid="true" data-id="idcliente"></div>
            </div>
        </div>

    </jsp:body>
</instore:template>