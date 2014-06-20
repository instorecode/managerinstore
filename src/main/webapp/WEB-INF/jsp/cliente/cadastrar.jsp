<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                               data-rule-minlength="3"
                               data-rule-maxlength="255" >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Matriz</label>
                        <select class="form-control">
                            <option value>Não possui Matriz</option>
                            <c:forEach items="${clienteBeanList}" var="clienteBean">
                                <option value="${clienteBean.idcliente}">Cliente: ${clienteBean.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Nome Fantasia</label>
                        <input type="text" name="dadosCliente.nomeFantasia" class="form-control" placeholder="Nome Fantasia" 
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>CNPJ</label>
                        <input type="text" data-mask="99.999.999/9999-99" name="dadosCliente.cnpj" class="form-control" placeholder="CNPJ"  
                               data-rule-required="true" 
                               data-rule-minlength="18"
                               data-rule-maxlength="18"
                               >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de inicio de contrato</label>
                        <input type="text" name="dadosCliente.dataInicioContrato" class="form-control datepicker" placeholder="Data de inicio do contrato" 
                               data-rule-required="true" 
                               data-rule-minlength="10"
                               data-rule-maxlength="10">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de termino de contrato</label>
                        <input type="text" name="dadosCliente.dataTerminoContrato" class="form-control datepicker" placeholder="Data de termino do contrato" 
                               data-rule-required="true" 
                               data-rule-minlength="10"
                               data-rule-maxlength="10">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Indice de reajuste do contrato</label>
                        <input type="text" name="dadosCliente.indiceReajusteContrato" class="form-control" placeholder="Indice de reajuste do contrato" 
                               data-rule-required="true" data-mask="99?.99">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Renovação automatica do contrato</label>
                        <input type="radio" name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="${true}" checked>&nbsp;&nbsp;&nbsp;Sim &nbsp;
                        <br />
                        <input type="radio" name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="${false}" checked>&nbsp;&nbsp;&nbsp;Não &nbsp;
                    </div>
                </div>

                <div class="col-md-6"></div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Razão social</label> 
                        <textarea name="dadosCliente.razaoSocial" class="form-control" rows="3"  placeholder="Razão social" 
                                  data-rule-required="true" 
                                  data-rule-minlength="3"
                                  data-rule-maxlength="1000"></textarea>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group"> 
                        <label>CEP</label>
                        <input type="text" name="cliente.endereco.cep.numero" class="form-control cepload" placeholder="CEP" 
                               data-mask="99.999-999"
                               data-url="${url}/utilidades/cepload">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Estado</label>
                        <select name="cliente.endereco.cep.bairro.cidade.estado.idestado" class="form-control est">
                            <option value>Selecione um estado</option>
                            <c:forEach items="${estadoBeanList}" var="est">
                                <option value="${est.idestado}" data-uf="${est.sigla}">${est.sigla} - ${est.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group"> 
                        <label>Cidade</label>
                        <input type="text" name="cliente.endereco.cep.bairro.cidade.nome" class="form-control cid" placeholder="Cidade" 
                               >
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group"> 
                        <label>Bairro</label>
                        <input type="text" name="cliente.endereco.cep.bairro.nome" class="form-control bai" placeholder="Bairro" 
                               >
                    </div>
                </div>

                <div class="col-md-5">
                    <div class="form-group"> 
                        <label>Logradouro</label>
                        <input type="text" name="cliente.endereco.cep.bairro.rua" class="form-control log" placeholder="Logradouro" 
                               >
                    </div>
                </div>

                <div class="col-md-1">
                    <div class="form-group"> 
                        <label>Nº</label>
                        <input type="text" name="cliente.endereco.numero" class="form-control num" placeholder="Número" 
                               >
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Complemento</label>
                        <input type="text" name="cliente.endereco.complemento" class="form-control comp" placeholder="Complemento" >
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-default btnAddContato" title="Adicionar contato" data-tooltip="true" data-placement="bottom">
                <i class="fa fa-plus"></i>
            </button>
            &nbsp;
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>

        </form>

        <br />
        <div id="messageBox"></div> 

     
    </jsp:body>
</instore:template>