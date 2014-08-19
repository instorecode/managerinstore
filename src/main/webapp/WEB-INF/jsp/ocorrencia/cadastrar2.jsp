<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/minha-ocorrencia" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
    </jsp:attribute>

    <jsp:body> 
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/minha-ocorrencia">
            <input type="hidden" name="ocorrenciaBean.id" value="${ocorrenciaBean.id}" />
            <input type="hidden" name="ocorrenciaBean.dataCadastro" value="${cf:dateFormat(ocorrenciaBean.dataCadastro, "dd/MM/yyyy")}" />
            <input type="hidden" name="ocorrenciaBean.usuarioCriacao.idusuario" value="${sessionUsuario.usuarioBean.idusuario}" />


            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Descrição</label>
                        <textarea class="form-control" name="ocorrenciaBean.descricao" rows="10" data-rule-required="true">${ocorrenciaBean.descricao}</textarea>
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
                        <select class="select2 select_cliente" name="ocorrenciaBean.cliente.idcliente"  data-rule-required="true">
                            <c:forEach items="${clienteList}" var="item">
                                <option value="${item.idcliente}" ${ocorrenciaBean.cliente.idcliente eq item.idcliente ? 'selected="selected"' :''}>${item.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Origem</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaOrigem.id"  data-rule-required="true">
                            <c:forEach items="${ocorrenciaOrigemList}" var="item">
                                <option value="${item.id}" ${ocorrenciaBean.ocorrenciaOrigem.id eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Prioridade</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaPrioridade.id"  data-rule-required="true">
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
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Situação</label>
                        <select class="select2" name="idstatus">
                            <option>&nbsp;</option>
                            <c:forEach items="${ocorrenciaStatusList}" var="item">
                                <option value="${item.id}" ${ocorrenciaUsuario.ocorrenciaStatus.id eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <br />
            <br />

            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
        <script type="text/javascript">
            jQuery(document).ready(function() {
            });
        </script>
    </jsp:body>
</instore:template> 