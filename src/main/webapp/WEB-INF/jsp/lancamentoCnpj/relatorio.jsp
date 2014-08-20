<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">

    <jsp:attribute name="submenu">
        <a href="${url}/lancamento-entidade" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Entidades </a>
        <a href="${url}/lancamento-entidade/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">

    </jsp:attribute>
    <jsp:body> 
        <form d="cad_cliente" method="GET" data-form="false" >

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Filtro Data inicial</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <c:if test="${d1 ne null}">

                                <input type="text" name="d1s" class="form-control datepicker" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" value="${cf:dateFormat(d1, "dd/MM/yyyy")}">

                            </c:if>
                            <c:if test="${d1 eq null}">
                                <input type="text" name="d1s" class="form-control datepicker" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" >
                            </c:if>
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Filtro Data final</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <c:if test="${d2 ne null}">
                                <input type="text" name="d2s" class="form-control datepicker" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" value="${cf:dateFormat(d2, "dd/MM/yyyy")}">
                            </c:if>
                            <c:if test="${d2 eq null}">
                                <input type="text" name="d2s" class="form-control datepicker" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" >
                            </c:if>
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Situação</label>
                        <br />
                        <label class=""> <input type="radio" class="icheck"  name="sit" id="optionsRadios1" value="1" ${sit eq 1 or sit ? 'checked="checked"' : ''} >&nbsp;Pendente </label> &nbsp;&nbsp;&nbsp;
                        <label class=""> <input type="radio" class="icheck"  name="sit" id="optionsRadios1" value="0"  ${sit eq 0 ? 'checked="checked"' : ''}>&nbsp;Finalizados </label>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Entidade Financeiro</label>
                        <select name="entid" class="select2">
                            <c:forEach items="${beanList}" var="ent">
                                <option value="${ent.id}" ${ent.id eq entid ? 'selected="selected"' : ''}>${ent.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                Filtrar
            </button>
            <a class="btn btn-default" href="${url}/lancamento-entidade/relatorio">Limpar filtro</a>
        </form>

        <br /><br />

        <table class=" table table-striped">
            <tbody>
                <tr>
                    <td>Data</td>
                    <td>Situação</td>
                    <td>Hitórico</td>
                    <td class="alignRight">Débito</td>
                    <td class="alignRight">Crédito</td>
                    <td class="alignRight">Saldo</td>
                    <td class="alignRight">Saldo Calculado</td>

                </tr>

                <c:if test="${empty lancamentoRelatorioList}">
                    <tr>
                        <td colspan="7">Nenhum lançamento finalizado.</td>
                    </tr>
                </c:if>

                <c:if test="${not empty lancamentoRelatorioList}">
                    <c:forEach items="${lancamentoRelatorioList}" var="item">
                        <tr>
                            <td>${cf:dateFormat(item.data, "dd/MM/yyyy")}</td>
                            <td>
                                <c:if test="${item.fechamento ne null}">
                                    Finalizado
                                </c:if>
                                <c:if test="${item.fechamento eq null}">
                                    Pendente
                                </c:if>
                            </td>

                            <td>${item.historico}</td>
                            <td class="alignRight"><fmt:formatNumber value="${item.debito}" type="currency" /></td>
                            <td class="alignRight"><fmt:formatNumber value="${item.credito}" type="currency" /></td>
                            <td class="alignRight"><fmt:formatNumber value="${item.saldo}" type="currency" /></td>
                            <td class="alignRight"><fmt:formatNumber value="${item.saldoCalculado}" type="currency" /></td>


                        </tr>
                    </c:forEach>

                </c:if>

                <tr  class="total2">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="alignRight"><fmt:formatNumber value="${totalDebito}" type="currency" /></td>
                    <td class="alignRight"><fmt:formatNumber value="${totalCredito}" type="currency" /></td>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>

        <style>
            .alignRight {text-align: right;}
            .total2 td { background-color: #b8c8e0 !important }
        </style>
    </jsp:body> 
</instore:template>