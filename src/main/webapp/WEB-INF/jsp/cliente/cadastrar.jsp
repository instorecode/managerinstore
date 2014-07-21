<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/clientes" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Clientes </a>
    </jsp:attribute>
    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/clientes">
            <input type="hidden" name="cliente.idcliente" value="${cliente.idcliente}" />
            <input type="hidden" name="dadosCliente.iddadosCliente" value="${dadosCliente.iddadosCliente}" />
            <input type="hidden" name="cliente.endereco.idendereco" value="${cliente.endereco.idendereco}" />
            <input type="hidden" name="cliente.endereco.cep.idcep" value="${cliente.endereco.cep.idcep}" />
            <input type="hidden" name="cliente.endereco.cep.bairro.idbairro" value="${cliente.endereco.cep.bairro.idbairro}" />
            <input type="hidden" name="cliente.endereco.cep.bairro.cidade.idcidade" value="${cliente.endereco.cep.bairro.cidade.idcidade}" />
            <input type="hidden" name="cliente.parente" value="0" />
            <input type="hidden" name="cliente.matriz" value="${true}" />

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="cliente.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${cliente.nome}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Nome Fantasia</label>
                        <input type="text" name="dadosCliente.nomeFantasia" class="form-control" placeholder="Nome Fantasia" 
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" 
                               value="${dadosCliente.nomeFantasia}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>CNPJ</label>
                        <input type="text" data-mask="99.999.999/9999-99" name="dadosCliente.cnpj" class="form-control" placeholder="CNPJ"  
                               data-rule-required="true" 
                               data-rule-minlength="18"
                               data-rule-maxlength="18"
                               value="${dadosCliente.cnpj}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de inicio de contrato</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="dadosCliente.dataInicioContrato" class="form-control datepicker" placeholder="Data de inicio do contrato" 
                                   data-rule-required="true" 
                                   data-rule-minlength="10"
                                   data-rule-maxlength="10" value="${cf:dateFormat(dadosCliente.dataInicioContrato , "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de termino de contrato</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="dadosCliente.dataTerminoContrato" class="form-control datepicker" placeholder="Data de termino do contrato" 
                                   data-rule-required="true" 
                                   data-rule-minlength="10"
                                   data-rule-maxlength="10" value="${cf:dateFormat(dadosCliente.dataTerminoContrato, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Indice de reajuste do contrato</label>
                        <input type="text" name="dadosCliente.indiceReajusteContrato" class="form-control" placeholder="Indice de reajuste do contrato" 
                               data-rule-required="true" data-mask="00.00" value="${dadosCliente.indiceReajusteContrato}">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Renovação automatica do contrato</label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="${true}" ${dadosCliente.renovacaoAutomatica ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="${false}"  ${not dadosCliente.renovacaoAutomatica ? 'checked="checked"' : ''}>&nbsp;Não </label>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label></label> 

                        <div class="block-flat">
                            <div class="header">							
                                Razão social
                            </div>
                            <div class="content">
                                <textarea id="summernote1" name="dadosCliente.razaoSocial" class="form-control" rows="3"  placeholder="Razão social" 
                                          data-rule-required="true" 
                                          data-rule-minlength="3"
                                          data-rule-maxlength="1000" >${dadosCliente.razaoSocial}</textarea>

                            </div>
                        </div>	

                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group"> 
                        <label>CEP</label>
                        <input type="text" name="cliente.endereco.cep.numero" class="form-control cepload" placeholder="CEP" 
                               data-mask="99.999-999"
                               data-url="${url}/utilidades/cepload" value="${cliente.endereco.cep.numero}"
                               data-rule-required="true" 
                               data-rule-minlength="10"
                               data-rule-maxlength="10">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Estado</label>
                        <select class="select2" name="cliente.endereco.cep.bairro.cidade.estado.idestado" class="form-control est">
                            <option value>Selecione um estado</option>
                            <c:forEach items="${estadoBeanList}" var="est">
                                <option value="${est.idestado}" data-uf="${est.sigla}" ${cliente.endereco.cep.bairro.cidade.estado.idestado eq est.idestado ? 'selected="selected"' : ''}>${est.sigla} - ${est.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group"> 
                        <label>Cidade</label>
                        <input type="text" name="cliente.endereco.cep.bairro.cidade.nome" class="form-control cid" placeholder="Cidade" 
                               value="${cliente.endereco.cep.bairro.cidade.nome}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group"> 
                        <label>Bairro</label>
                        <input type="text" name="cliente.endereco.cep.bairro.nome" class="form-control bai" placeholder="Bairro" 
                               value="${cliente.endereco.cep.bairro.nome}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group"> 
                        <label>Logradouro</label>
                        <input type="text" name="cliente.endereco.cep.bairro.rua" class="form-control log" placeholder="Logradouro" 
                               value="${cliente.endereco.cep.bairro.rua}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">

                    </div>
                </div>

                <div class="col-md-1">
                    <div class="form-group"> 
                        <label>Nº</label>
                        <input type="text" name="cliente.endereco.numero" class="form-control num" placeholder="Número" 
                               value="${cliente.endereco.numero}"
                               data-rule-required="true" 
                               data-rule-minlength="1"
                               data-rule-maxlength="255">
                    </div>
                </div>

                <div class="col-md-5">
                    <div class="form-group"> 
                        <label>Complemento</label>
                        <input type="text" name="cliente.endereco.complemento" class="form-control comp" placeholder="Complemento" 
                               value="${cliente.endereco.complemento}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Situação &nbsp;&nbsp;</label>
                        <label class="radio-inline"> <input class="icheck" type="radio" name="cliente.situacao" id="optionsRadios1" value="${true}" ${cliente.situacao ? 'checked="checked"' : ''} >&nbsp;Ativo </label>
                        <label class="radio-inline"> <input class="icheck" type="radio" name="cliente.situacao" id="optionsRadios1" value="${false}"  ${not cliente.situacao ? 'checked="checked"' : ''}>&nbsp;Inativo </label>



                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>