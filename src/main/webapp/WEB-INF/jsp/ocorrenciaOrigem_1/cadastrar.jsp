<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/ocorrencia-status" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/ocorrencia-status">
            <input type="hidden" name="ocorrenciaStatusBean.id" value="${ocorrenciaStatusBean.id}" />

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Descrição</label>
                        <input type="text" name="ocorrenciaStatusBean.descricao" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${ocorrenciaStatusBean.descricao}">
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Cor</label>
                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#4D90FD" ${ocorrenciaStatusBean.cor eq '#4D90FD' ? 'checked="checked"':''}  class="icheck"> Azul </label> 
                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#54A754" ${ocorrenciaStatusBean.cor eq '#54A754' ? 'checked="checked"':''} class="icheck"> Verder </label> 
                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#ffa800" ${ocorrenciaStatusBean.cor eq '#ffa800' ? 'checked="checked"':''} class="icheck"> Amarelo </label> 
                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#e64d35" ${ocorrenciaStatusBean.cor eq '#e64d35' ? 'checked="checked"':''} class="icheck"> Vermelho </label> 
                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#000000" ${ocorrenciaStatusBean.cor eq '#000000' ? 'checked="checked"':''} class="icheck"> Preto </label> 
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>