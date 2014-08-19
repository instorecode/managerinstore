<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/ocorrencia-solucao" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/ocorrencia-solucao">
            <input type="hidden" name="ocorrenciaSolucaoBean.id" value="${ocorrenciaSolucaoBean.id}" />

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Descrição</label>
                        <input type="text" name="ocorrenciaSolucaoBean.descricao" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${ocorrenciaSolucaoBean.descricao}">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Prazo</label>
                        <input type="text" name="prazo" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255"
                               data-mask="00:00:00" value="${cf:dateFormat(ocorrenciaSolucaoBean.prazoPesolucao, "HH:mm:ss")}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Problemas</label>
                        <br />
                        <select class="select2"  name="problemaList"  multiple>
                            <c:forEach items="${problemaList}" var="problema">
                                <c:set var="selected" value="${false}"></c:set>
                                <c:forEach items="${problemaSolucaoList}" var="ps">
                                    <c:if test="${ps.ocorrenciaSolucao.id eq ocorrenciaSolucaoBean.id and problema.id eq ps.ocorrenciaProblema.id}">
                                        <c:set var="selected" value="${true}"></c:set>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${selected}">
                                    <option selected="selected" value="${problema.id}">${problema.descricao}</option>
                                </c:if>
                                <c:if test="${not selected}">
                                    <option value="${problema.id}">${problema.descricao}</option>
                                </c:if>

                            </c:forEach>
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