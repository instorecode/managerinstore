<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">

        <c:if test="${cliente.idcliente ne null and cliente.idcliente > 0}">
            <a href="${url}/filial/${cliente.parente}" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Filiais </a>
        </c:if>
        <c:if test="${cliente.idcliente eq null or cliente.idcliente < 1}">
            <a href="${url}/filial/${id}" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Filiais </a>
        </c:if>
    </jsp:attribute>
    <jsp:body>
        <c:if test="${cliente.idcliente ne null and cliente.idcliente > 0}">
            <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/filial/${cliente.parente}">
            </c:if>
            <c:if test="${cliente.idcliente eq null or cliente.idcliente < 1}">
                <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/filial/${id}">
                </c:if>

                <input type="hidden" name="cliente.idcliente" value="${cliente.idcliente}" />
                <input type="hidden" name="dadosCliente.iddadosCliente" value="${dadosCliente.iddadosCliente}" />
                <input type="hidden" name="cliente.endereco.idendereco" value="${cliente.endereco.idendereco}" />
                <input type="hidden" name="cliente.endereco.cep.idcep" value="${cliente.endereco.cep.idcep}" />
                <input type="hidden" name="cliente.endereco.cep.bairro.idbairro" value="${cliente.endereco.cep.bairro.idbairro}" />
                <input type="hidden" name="cliente.endereco.cep.bairro.cidade.idcidade" value="${cliente.endereco.cep.bairro.cidade.idcidade}" />
                <c:if test="${cliente.idcliente ne null and cliente.idcliente > 0}">
                    <input type="hidden" name="cliente.parente" value="${cliente.parente}" />
                </c:if>
                <c:if test="${cliente.idcliente eq null or cliente.idcliente < 1}">
                    <input type="hidden" name="cliente.parente" value="${id}" />
                </c:if>

                <input type="hidden" name="cliente.matriz" value="${false}" />

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

                    <c:if test="${cliente.idcliente ne null and cliente.idcliente > 0}">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Matriz</label>
                                <select class="select2" data-right="true" disabled="disabled">
                                    <c:forEach items="${clienteBeanList}" var="clienteBean">
                                        <option value="${clienteBean.idcliente}" ${cliente.parente eq clienteBean.idcliente ? 'selected="selected"' : ''}>Cliente: ${clienteBean.nome}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${cliente.idcliente eq null or cliente.idcliente < 1}">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Matriz</label>
                                <select class="select2" name="cliente.parente" data-right="true" disabled="disabled">
                                    <c:forEach items="${clienteBeanList}" var="clienteBean">
                                        <option value="${clienteBean.idcliente}" ${id eq clienteBean.idcliente ? 'selected="selected"' : ''}>Cliente: ${clienteBean.nome}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </c:if>


                </div>
                <div class="row">

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