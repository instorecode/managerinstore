<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/lancamento-entidade" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Entidades </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/lancamento-entidade">
            <input type="hidden" name="lancamentoCnpjBean.id" value="${lancamentoCnpjBean.id}" />

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="lancamentoCnpjBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${lancamentoCnpjBean.nome}">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label>CNPJ</label>
                        <input type="text" name="lancamentoCnpjBean.cnpj" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="18"
                               data-rule-maxlength="18"
                               data-rule-cnpj="true"
                               data-mask="99.999.999/9999-99" value="${lancamentoCnpjBean.cnpj}">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Saldo disponivel</label>
                        <input type="text" name="lancamentoCnpjBean.saldoDisponivel" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-maskmoney="true" value='<fmt:formatNumber value="${lancamentoCnpjBean.saldoDisponivel}" minFractionDigits="2" />'>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>