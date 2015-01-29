<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <c:if test="${contatoClienteBean.idcontatoCliente ne null and contatoClienteBean.idcontatoCliente > 0}">
            <a href="${url}/contatos/${contatoClienteBean.dadosCliente.cliente.idcliente}" class="btn btn-voltar btn-default"> <i class="fa fa-hand-o-left"></i> Contatos </a>
        </c:if>
        <c:if test="${contatoClienteBean.idcontatoCliente eq null or contatoClienteBean.idcontatoCliente < 1}">
            <a href="${url}/contatos/${id}" class="btn btn-voltar btn-default"> <i class="fa fa-hand-o-left"></i> Contatos </a>
        </c:if>
    </jsp:attribute>

    <jsp:body>
        <c:if test="${contatoClienteBean.idcontatoCliente ne null and contatoClienteBean.idcontatoCliente > 0}">
            <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/contatos/${contatoClienteBean.dadosCliente.cliente.idcliente}">
        </c:if>
        <c:if test="${contatoClienteBean.idcontatoCliente eq null or contatoClienteBean.idcontatoCliente < 1}">
            <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/contatos/${id}">
        </c:if>
            <input type="hidden" name="contatoClienteBean.idcontatoCliente" value="${contatoClienteBean.idcontatoCliente}" />
            <c:if test="${contatoClienteBean.idcontatoCliente ne null and contatoClienteBean.idcontatoCliente > 0}">
                    <input type="hidden" name="contatoClienteBean.dadosCliente.iddadosCliente" value="${contatoClienteBean.dadosCliente.iddadosCliente}" />
            </c:if>
            <c:if test="${contatoClienteBean.idcontatoCliente eq null or contatoClienteBean.idcontatoCliente < 1}">
                <input type="hidden" name="contatoClienteBean.dadosCliente.iddadosCliente" value="${id}" />
            </c:if>

            <div class="row">
                <div class="col-md-9">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="contatoClienteBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${contatoClienteBean.nome}">
                    </div>
                </div>
                    
                <c:if test="${contatoClienteBean.idcontatoCliente ne null and contatoClienteBean.idcontatoCliente > 0}">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Cliente</label>
                            <br />
                            <select data-selectradio="true" class="form-control" data-rule-required="true" disabled="disabled">
                                <c:forEach items="${dadosClienteBeanList}" var="dados">
                                    <option value="${dados.iddadosCliente}" ${dados.iddadosCliente eq contatoClienteBean.dadosCliente.iddadosCliente ? 'selected="selected"' : ''}>${dados.cliente.nome}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>
                <c:if test="${contatoClienteBean.idcontatoCliente eq null or contatoClienteBean.idcontatoCliente < 1}">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Cliente</label>
                            <br />
                            <select data-selectradio="true" class="form-control" data-rule-required="true" disabled="disabled">
                                <c:forEach items="${dadosClienteBeanList}" var="dados">
                                    <option value="${dados.iddadosCliente}" ${dados.iddadosCliente eq id ? 'selected="selected"' : ''}>${dados.cliente.nome}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>
                    
                

                <div class="col-md-3">
                    <div class="form-group">
                        <label>E-mail</label>
                        <input type="text" name="contatoClienteBean.email" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-email="true"
                               data-rule-maxlength="255" 
                               value="${contatoClienteBean.email}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Tel.</label>
                        <input type="text" name="contatoClienteBean.tel" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="20" value="${contatoClienteBean.tel}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Setor</label>
                        <input type="text" name="contatoClienteBean.setor" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${contatoClienteBean.setor}">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Principal contato desta loja &nbsp;&nbsp;</label>
                        <label class="radio-inline"> <input class="icheck" type="radio" name="contatoClienteBean.principal" id="optionsRadios1" value="${true}" ${contatoClienteBean.principal ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                        <label class="radio-inline"> <input class="icheck" type="radio" name="contatoClienteBean.principal" id="optionsRadios1" value="${false}"  ${not contatoClienteBean.principal ? 'checked="checked"' : ''}>&nbsp;Não </label>



                    </div>
                </div>

            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>