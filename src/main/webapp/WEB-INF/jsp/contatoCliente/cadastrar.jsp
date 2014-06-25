<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/contatos" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Contatos </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/contatos">
            <input type="hidden" name="contatoClienteBean.idcontatoCliente" value="${contatoClienteBean.idcontatoCliente}" />


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
                        <br />
                        <select data-selectradio="true" class="form-control" name="contatoClienteBean.dadosCliente.iddadosCliente" data-rule-required="true" >
                            <c:forEach items="${dadosClienteBeanList}" var="dados">
                                <option value="${dados.iddadosCliente}" ${dados.iddadosCliente eq contatoClienteBean.dadosCliente.iddadosCliente ? 'selected="selected"' : ''}>${dados.cliente.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

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

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Principal</label>
                        <br />
                        <select data-selectradio="true" class="form-control" name="contatoClienteBean.principal" data-rule-required="true" >
                            <option value="${true}"  ${contatoClienteBean.principal ? 'selected="selected"' :''} >Sim</option>
                            <option value="${false}" ${not contatoClienteBean.principal ? 'selected="selected"' :''} >Não</option>
                        </select>
                    </div>
                </div>

            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>