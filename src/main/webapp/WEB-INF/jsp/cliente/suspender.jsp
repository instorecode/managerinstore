<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="true">

    <jsp:attribute name="submenu">
        
    </jsp:attribute>

    <jsp:attribute name="gridColumn">

    </jsp:attribute>

    <jsp:attribute name="detailsButton">

    </jsp:attribute>


    <jsp:body> 
        <h2>${cliente.nome}</h2>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/cliente-ou-filial/suspender/${cliente.idcliente}">
            <input type="hidden" name="clienteSuspenso.cliente.idcliente" value="${cliente.idcliente}" />
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Motivo</label>
                        <input type="text" name="clienteSuspenso.motivo" class="form-control" placeholder="Motivo"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="60000" value="">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de inicio</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="clienteSuspenso.dataInicio" class="form-control datepicker" placeholder="Data de inicio"  value="">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data final</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="clienteSuspenso.dataFim" class="form-control datepicker" placeholder="Data final"  value="">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Suspenso</label>
                        <br />
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="clienteSuspenso.suspenso" id="optionsRadios1" value="${true}" ${clienteSuspenso.suspenso eq true ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="clienteSuspenso.suspenso" id="optionsRadios1" value="${false}"  ${clienteSuspenso.suspenso eq false ? 'checked="checked"' : ''}>&nbsp;Não </label>
                    </div>
                </div>

            </div>

            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>

        <c:if test="${not empty suspenderList}">
            <hr />

            <h2>Histórico</h2>
            
            <ul class="timeline">
                <c:forEach items="${suspenderList}" var="item">
                    <li>
                        <i class="fa fa-calendar ${item.suspenso ? 'red' : ''}"></i>
                        <div class="content">
                            <p><strong>Usuário</strong> ${item.usuario.nome}</p>
                            <small>
                                Em ${cf:dateFormat(item.data,"dd/MM/yyyy")}
                                o cliente ${item.cliente.nome} foi atualizado para 
                                ${item.suspenso ? 'suspenso' : 'liberado'}
                                <c:if test="${item.suspenso}">
                                    de ${cf:dateFormat(item.dataInicio,"dd/MM/yyyy")} á ${cf:dateFormat(item.dataFim,"dd/MM/yyyy")}
                                </c:if> pelo seguinte motivo,
                            </small>
                            <blockquote>
                                <small>${item.motivo}</small>
                            </blockquote>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

    </jsp:body>
</instore:template>