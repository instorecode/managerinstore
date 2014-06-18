<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/clientes" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Clientes </a>
    </jsp:attribute>
    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/dashboard">
            <div class="row">
                <div class="col-md-9">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="cliente.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-msg-required="O nome do cliente é obrigatório"
                               
                               data-rule-minlength="3" 
                               data-msg-minlength="Informe no minimo 3 caracteres para o nome do cliente." 
                               
                               data-rule-maxlength="255" 
                               data-msg-maxlength="Informe no maximo 255 caracteres para o nome do cliente." >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Matriz</label>
                        <select class="form-control">

                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Nome Fantasia</label>
                        <input type="text" name="dadosCliente.nomeFantasia" class="form-control" placeholder="Nome Fantasia" 
                               data-rule-required="true" 
                               data-msg-required="O nome fantasia do cliente é obrigatório"
                               
                               data-rule-minlength="3" 
                               data-msg-minlength="Informe no minimo 3 caracteres para o nome fantasia do cliente." 
                               
                               data-rule-maxlength="255" 
                               data-msg-maxlength="Informe no maximo 255 caracteres para o nome fantasia do cliente.">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>CNPJ</label>
                        <input type="text" data-mask="99.999.999/9999-99" name="dadosCliente.cnpj" class="form-control" placeholder="CNPJ"  data-rule-required="true" >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de inicio de contrato</label>
                        <input type="text" name="dadosCliente.dataInicioContrato" class="form-control datepicker" placeholder="Data de inicio do contrato"  data-rule-required="true" >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de termino de contrato</label>
                        <input type="text" name="dadosCliente.dataTerminoContrato" class="form-control datepicker" placeholder="Data de termino do contrato"  data-rule-required="true" >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Indice de reajuste do contrato</label>
                        <input type="text" name="dadosCliente.indiceReajusteContrato" class="form-control" placeholder="Indice de reajuste do contrato"  data-rule-required="true" >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Renovação automatica do contrato</label>
                        <input type="radio" name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="option1" checked>&nbsp;&nbsp;&nbsp;Sim &nbsp;
                        <br />
                        <input type="radio" name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="option1" checked>&nbsp;&nbsp;&nbsp;Não &nbsp;
                    </div>
                </div>

                <div class="col-md-6"></div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Razão social</label> 
                        <textarea name="dadosCliente.razaoSocial" class="form-control" rows="3"  placeholder="Razão social"></textarea>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
        
        <br />
        <div id="messageBox"></div> 

    </jsp:body>
</instore:template>