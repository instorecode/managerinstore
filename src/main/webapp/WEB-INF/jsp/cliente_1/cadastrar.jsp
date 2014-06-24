<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/contatos" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Contatos </a>
    </jsp:attribute>
        
    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/contatos">
            <input type="hidden" name="contatoClienteBean.idcontatoCliente" value="${contatoClienteBean.idcontatoCliente}" />
            <input type="hidden" name="contatoClienteBean.dadosCliente.iddadosCliente" value="${contatoClienteBean.dadosCliente.iddadosCliente}" />


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

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Cliente</label>
                        <select class="form-control" name="contatoClienteBean.dadosCliente.iddadosCliente" data-rule-required="true" >
                            <option value>Selecione um cliente</option>
                            <c:forEach items="${dadosClienteBeanList}" var="dadosCliente">
                                <option value="${dadosCliente.iddadosCliente}" ${dadosCliente.iddadosCliente eq contatoClienteBean.dadosCliente.iddadosCliente ? 'selected="selected"' : ''}>Cliente: ${dadosCliente.nomeFantasia}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>E-mail</label>
                        <input type="text" name="contatoClienteBean.email" class="form-control" placeholder="E-mail" 
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" 
                               data-rule-email="false" 
                               value="${contatoClienteBean.email}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Tel.</label>
                        <input type="text" name="contatoClienteBean.tel" class="form-control" placeholder="Tel."  
                               data-rule-required="true" 
                               data-rule-minlength="7"
                               data-rule-maxlength="20"
                               value="${contatoClienteBean.tel}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Setor</label>
                        <input type="text" name="contatoClienteBean.setor" class="form-control" placeholder="Setor" 
                               data-rule-required="true" 
                               data-rule-minlength="2"
                               data-rule-maxlength="255" value="${contatoClienteBean.setor}">
                    </div>
                </div>

                <div class="col-md-3"></div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Contato Principal    &nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <input type="radio" name="contatoClienteBean.principal" id="optionsRadios1" value="${true}" ${contatoClienteBean.principal ? 'checked="checked"' : ''} >&nbsp;&nbsp;&nbsp;Sim &nbsp;
                        
                        <input type="radio" name="contatoClienteBean.principal" id="optionsRadios1" value="${false}"  ${not contatoClienteBean.principal ? 'checked="checked"' : ''}>&nbsp;&nbsp;&nbsp;Não &nbsp;
                    </div>
                </div>


            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>