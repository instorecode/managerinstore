<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/ocorrencia" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
    </jsp:attribute>

    <jsp:body> 
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/ocorrencia">
            <input type="hidden" name="ocorrenciaBean.id" value="${ocorrenciaBean.id}" />
            <input type="hidden" name="ocorrenciaBean.dataCadastro" value="${cf:dateFormat(ocorrenciaBean.dataCadastro, "dd/MM/yyyy")}" />
            <input type="hidden" name="ocorrenciaBean.usuarioCriacao.idusuario" value="${sessionUsuario.usuarioBean.idusuario}" />

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Descrição</label>
                        <textarea class="form-control" name="ocorrenciaBean.descricao" rows="10">${ocorrenciaBean.descricao}</textarea>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Data de resolução</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="ocorrenciaBean.dataResolucao" class="form-control" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" value="${cf:dateFormat(ocorrenciaBean.dataResolucao,"dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Cliente</label>
                        <select class="select2" name="ocorrenciaBean.cliente.idcliente">
                            <c:forEach items="${clienteList}" var="item">
                                <option value="${item.idcliente}" ${ocorrenciaBean.cliente.idcliente eq item.idcliente ? 'selected="selected"' :''}>${item.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Origem</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaOrigem.id">
                            <c:forEach items="${ocorrenciaOrigemList}" var="item">
                                <option value="${item.id}" ${ocorrenciaBean.ocorrenciaOrigem.id eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Prioridade</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaPrioridade.id">
                            <c:forEach items="${ocorrenciaPrioridadeList}" var="item">
                                <option value="${item.id}" ${ocorrenciaBean.ocorrenciaPrioridade.id eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Possivel Problema</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaProblema">
                            <option>&nbsp;</option>
                            <c:forEach items="${ocorrenciaProblemaList}" var="item">
                                <option value="${item.id}" ${ocorrenciaBean.ocorrenciaProblema eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Possivel Solução</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaSolucao">
                            <option>&nbsp;</option>
                            <c:forEach items="${ocorrenciaSolucaoList}" var="item">
                                <option value="${item.id}" ${ocorrenciaBean.ocorrenciaSolucao eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Emcaminha para: </label>
                        <br />

                        <select class="select2"  name="usuarioList"  multiple>
                            <c:forEach items="${usuarioList}" var="usuario">
                                <c:set var="selected" value="${false}"></c:set>
                                <c:forEach items="${ocorrenciaUsuarioList}" var="ocorr">
                                    <c:if test="${usuario.idusuario eq ocorr.usuario.idusuario and ocorr.ocorrencia eq ocorrenciaBean.id}">
                                        <c:set var="selected" value="${true}"></c:set>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${selected}">
                                    <option selected="selected" value="${usuario.idusuario}">${usuario.nome}</option>
                                </c:if>
                                <c:if test="${not selected}">
                                    <option value="${usuario.idusuario}">${usuario.nome}</option>
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