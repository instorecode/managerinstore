<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template  menucolapse="false">
    <jsp:body>                
        <form  method="POST" data-form="true">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Senha atual</label>
                        <input type="password" name="senha_atual" class="form-control" placeholder="Senha atual"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${usuarioBean.nome}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Nova senha</label>
                        <input type="password" name="nova_senha" class="form-control" placeholder="Nova senha"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${usuarioBean.nome}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Confirmar senha</label>
                        <input type="password" name="conf_senha" class="form-control" placeholder="Confirmar senha"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${usuarioBean.nome}">
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-default" style="margin-left: 0px;">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>