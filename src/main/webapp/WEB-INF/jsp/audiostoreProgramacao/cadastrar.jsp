<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-programacao" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Audiostore Programações </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-programacao">
            <input type="hidden" name="audiostoreProgramacaoBean.id" value="${audiostoreProgramacaoBean.id}" />


            <div class="row">
                <div class="col-md-9">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="audiostoreProgramacaoBean.descricao" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="20" value="${audiostoreProgramacaoBean.descricao}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Cliente</label>
                        <select class="form-control" name="audiostoreProgramacaoBean.cliente.idcliente" data-rule-required="true" >
                            <option value>Selecione um cliente</option>
                            <c:forEach items="${clienteBeanList}" var="cliente">
                                <option value="${cliente.idcliente}" ${cliente.idcliente eq audiostoreProgramacaoBean.cliente.idcliente ? 'selected="selected"' : ''}>Cliente: ${cliente.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Horário inicial</label>
                        <input type="text" name="horaInicio" class="form-control" placeholder="Tempo de duração"  
                               data-rule-required="true" 
                               data-rule-minlength="5"
                               data-rule-maxlength="5" 
                               data-clock="true"
                               data-clock-format="HH:mm"
                               data-mask="99:99"
                               value="${audiostoreProgramacaoBean.horaInicio}">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>Horário final</label>
                        <input type="text" name="horaFinal" class="form-control" placeholder="Tempo de duração"  
                               data-rule-required="true" 
                               data-rule-minlength="5"
                               data-rule-maxlength="5" 
                               data-clock="true"
                               data-clock-format="HH:mm"
                               data-mask="99:99"
                               value="${audiostoreProgramacaoBean.horaFinal}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de inicio</label>
                        <input type="text" name="audiostoreProgramacaoBean.dataInicio" class="form-control datepicker" placeholder="Data de inicio"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" 
                               data-mask="99/99/9999"
                               value="${cf:dateFormat(audiostoreProgramacaoBean.dataInicio , "dd/MM/yyyy")}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de termino</label>
                        <input type="text" name="audiostoreProgramacaoBean.dataFinal" class="form-control datepicker" placeholder="Data de termino"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" 
                               data-mask="99/99/9999"
                               value="${cf:dateFormat(audiostoreProgramacaoBean.dataFinal, "dd/MM/yyyy")}">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>Categorias</label>
                        <select  multiple="multiple" class="form-control" name="categorias" data-rule-required="true" >
                            <c:forEach items="${categoriaBeanList}" var="categoria">
                                <c:set var="selected" value="${false}"></c:set>
                                <c:forEach items="${programacaoCategoriaBeanList}" var="pcb">
                                    <c:if test="${pcb.audiostoreCategoria.codigo eq categoria.codigo}">
                                        <c:set var="selected" value="${true}"></c:set>
                                    </c:if>
                                </c:forEach>
                                <option value="${categoria.codigo}" ${selected  ? 'selected="selected"': '' }>${categoria.categoria}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Dias da semana</label>
                        <select  multiple="multiple" class="form-control" name="diasSemana" data-rule-required="true" >
                            <option value="1" ${audiostoreProgramacaoBean.segundaFeira ? 'selected="selected"': ''}>Segunda-Feira</option>
                            <option value="2" ${audiostoreProgramacaoBean.tercaFeira ? 'selected="selected"': ''}>Terça-Feira</option>
                            <option value="3" ${audiostoreProgramacaoBean.quartaFeira ? 'selected="selected"': ''}>Quarta-Feira</option>
                            <option value="4" ${audiostoreProgramacaoBean.quintaFeira ? 'selected="selected"': ''}>Quinta-Feira</option>
                            <option value="5" ${audiostoreProgramacaoBean.sextaFeira ? 'selected="selected"': ''}>Sexta-Feira</option>
                            <option value="6" ${audiostoreProgramacaoBean.sabado ? 'selected="selected"': ''}>Sábado</option>
                            <option value="7" ${audiostoreProgramacaoBean.domingo ? 'selected="selected"': ''}>Domíngo</option>
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