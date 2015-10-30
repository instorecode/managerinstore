<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 

<instore:template>
    <jsp:body>
        <c:set var="form_access" value="${false}"></c:set>

        <c:forEach items="${funcionalidadeBeanList}" var="func">
            <c:if test="${func.mappingId eq '/dump-paradox'}">         
                <c:set var="form_access" value="${true}"></c:set>
            </c:if>
        </c:forEach>

        <!--FORM-->
        <div class="">
            <div class="block">
                <div class="content">
                    <c:if test="${form_access}">
                        <br />
                        <h2>Dump Paradox</h2>
                        <hr />
                        <form name="FORM_DUMP_PARADOX" method="POST" data-formtable="true" action="${url}/dump-paradox">
                            <div class="row">

                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Caminho</label>
                                        <input type="text" name="path" class="form-control" placeholder="Caminho"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               value="${path}">
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-success btn-flat" style="margin-left: 0px;">
                                    <i class="fa fa-save"></i> Salvar
                                </button>
                        </form>
                    </c:if>

                    <c:if test="${not form_access}">
                        <br />
                        <h2>Acesso não permitido a essa funcionaliade</h2>
                    </c:if>

                </div>
            </div>
        </div>
    </jsp:body>
</instore:template>