<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-categorias" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Audiostore Categorias </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-categorias">
            <input type="hidden" name="audiostoreCategoriaBean.codigo" value="${audiostoreCategoriaBean.codigo}" />


            <div class="row">
                <div class="col-md-9">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="audiostoreCategoriaBean.categoria" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreCategoriaBean.categoria}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Cliente</label>
                        <select  class="select2 select_cliente" name="audiostoreCategoriaBean.cliente.idcliente" data-rule-required="true" >
                            <option value>Selecione um cliente</option>
                            <c:forEach items="${clienteBeanList}" var="cliente">
                                <option value="${cliente.idcliente}" ${cliente.idcliente eq audiostoreCategoriaBean.cliente.idcliente ? 'selected="selected"' : ''}>${cliente.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de inicio</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreCategoriaBean.dataInicio" class="form-control datepicker" placeholder="Data de inicio"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" 
                                   data-mask="99/99/9999"
                                   value="${cf:dateFormat(audiostoreCategoriaBean.dataInicio , "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de termino</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreCategoriaBean.dataFinal" class="form-control datepicker" placeholder="Data de termino"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" 
                                   data-mask="99/99/9999"
                                   value="${cf:dateFormat(audiostoreCategoriaBean.dataFinal, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Tipo</label>
                        <select class="select2" name="audiostoreCategoriaBean.tipo" data-rule-required="true">
                            <option value>Selecione um tipo</option>
                            <option value="1" ${audiostoreCategoriaBean.tipo eq 1 ? 'selected="selected"' : ''}>Mùsica</option>
                            <option value="2" ${audiostoreCategoriaBean.tipo eq 2 ? 'selected="selected"' : ''}>Comercial</option>
                            <option value="3" ${audiostoreCategoriaBean.tipo eq 3 ? 'selected="selected"' : ''}>Video</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Tempo de duração</label>
                        <input type="text" name="tempo" class="form-control" placeholder="Tempo de duração"  
                               data-rule-required="true" 
                               data-rule-minlength="8"
                               data-rule-maxlength="8" 
                               data-mask="99:99:99"
                               value="${audiostoreCategoriaBean.tempo}">
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>