<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/musica/programacao-audiostore/${idmusicaGeral}" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Musicas </a>
    </jsp:attribute>

    <jsp:body>
        <script type="text/javascript">
            jQuery(document).ready(function() {
                if (null != jQuery('.select_cliente').children('option:selected').val() && undefined != jQuery('.select_cliente').children('option:selected').val() && '' != jQuery('.select_cliente').children('option:selected').val() && jQuery('.select_cliente').children('option:selected').val() > 0) {
                    var clienteid = jQuery('.select_cliente').children('option:selected').val();

                    jQuery('.sel_cat1, .sel_cat2').find('option').remove().end();

                    jQuery.ajax({
                        url: '${url}/musica/programacao-audiostore/categorias-by-cliente/',
                        type: 'POST',
                        data: {clienteid: clienteid},
                        success: function(response) {
                            for (i in response) {
                                var item = response[i];
                                jQuery('.sel_cat1, .sel_cat2').append('<option value="' + item.codigo + '">' + item.categoria + '</option>');
                            }
                        },
                        error: function(response) {
                            console.log(response);
                        }
                    });
                }

                jQuery('.select_cliente').on('change', function() {
                    var clienteid = jQuery(this).val();
                    jQuery('.sel_cat1, .sel_cat2').find('option').remove().end();

                    jQuery.ajax({
                        url: '${url}/musica/programacao-audiostore/categorias-by-cliente/',
                        type: 'POST',
                        data: {clienteid: clienteid},
                        success: function(response) {
                            for (i in response) {
                                var item = response[i];
                                jQuery('.sel_cat1, .sel_cat2').append('<option value="' + item.codigo + '">' + item.categoria + '</option>');
                            }
                        },
                        error: function(response) {
                            console.log(response);
                        }
                    });
                });
            });
        </script>


        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/musica/programacao-audiostore/${idmusicaGeral}">
            <input type="hidden" name="audiostoreMusicaBean.id" value="${audiostoreMusicaBean.id}"  />
            <input type="hidden" name="audiostoreMusicaBean.musicaGeral" value="${idmusicaGeral}"  />

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Cliente</label>
                        <br />
                        <select  class="select2 sel_cliente select_cliente"  data-rule-required="true" name="audiostoreMusicaBean.cliente.idcliente">
                            <option value="" >Selecione um cliente</option>
                            <c:forEach items="${clienteBeanList}" var="cli">
                                <option value="${cli.idcliente}" ${audiostoreMusicaBean.cliente.idcliente eq cli.idcliente ? 'selected="selected"':''}>${cli.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

            </div>


            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label>Categoria Primária</label> 
                        <br />
                        <select  class="select2 sel_cat1" name="audiostoreMusicaBean.categoria1.codigo" data-rule-required="true" >
                            <c:if test="${not cadastrar}">
                                <c:forEach items="${categoriasByCliente2}" var="item">
                                    <option value="${item.codigo}"  ${audiostoreMusicaBean.categoria1.codigo  eq item.codigo ? 'selected="selected"' : ''}>${item.categoria}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Dias execução <i>(Primária)</i></label>
                        <input type="text" name="audiostoreMusicaBean.diasExecucao1" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.diasExecucao1}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Categoria Secundário</label> 
                        <br />
                        <select class="select2 sel_cat2" name="audiostoreMusicaBean.categoria2.codigo" data-rule-required="true" >
                            <c:if test="${not cadastrar}">
                                <c:forEach items="${categoriasByCliente2}" var="item">
                                    <option value="${item.codigo}"  ${audiostoreMusicaBean.categoria2.codigo  eq item.codigo ? 'selected="selected"' : ''}>${item.categoria}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Dias execução <i>(Secundário)</i></label>
                        <input type="text" name="audiostoreMusicaBean.diasExecucao2" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.diasExecucao2}">
                    </div>
                </div>

            </div>

            <hr />

            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">                        
                        <label></label>
                        <br />
                        Usar crossover<label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.crossover" id="optionsRadios1" value="${true}" ${audiostoreMusicaBean.crossover ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.crossover" id="optionsRadios1" value="${false}"  ${not audiostoreMusicaBean.crossover ? 'checked="checked"' : ''}>&nbsp;Não </label>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data vencimendo crossover</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.dataVencimentoCrossover" class="form-control datepicker" placeholder="Nome"  

                                   value="${cf:dateFormat(audiostoreMusicaBean.dataVencimentoCrossover, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Quantidade </label>

                        <input type="text" name="audiostoreMusicaBean.qtdePlayer" class="form-control span2" placeholder="Quantidade máxima de execuções no dia)"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.qtdePlayer}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Ultima  execução</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.ultimaExecucao" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true"
                                   data-mask="99/99/9999"
                                   data-rule-maxlength="30" value="${cf:dateFormat(audiostoreMusicaBean.ultimaExecucao, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de vencimento</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.dataVencimento" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999" value="${cf:dateFormat(audiostoreMusicaBean.dataVencimento, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">




                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de inclusao</label>

                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.data" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999"value="${cf:dateFormat(audiostoreMusicaBean.data, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label></label>
                        <br />
                        Tipo <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.cut" id="optionsRadios1" value="${true}" ${audiostoreMusicaBean.cut ? 'checked="checked"' : ''} >&nbsp;Cut </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.cut" id="optionsRadios1" value="${false}"  ${not audiostoreMusicaBean.cut ? 'checked="checked"' : ''}>&nbsp;FadeOut </label>
                    </div>
                </div>

            </div>


            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>

    </jsp:body>
</instore:template>