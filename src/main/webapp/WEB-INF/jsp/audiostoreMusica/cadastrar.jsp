<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/musica/programacao-audiostore" class="btn btn-voltar btn-default"> <i class="fa fa-hand-o-left"></i> Musicas </a>
    </jsp:attribute>

    <jsp:body>
        <c:if test="${musicaGeralList ne null and not empty musicaGeralList}">
            <style type="text/css">
                .lista_mg {
                    list-style: none;
                }
                .lista_mg li {
                    margin-left: -30px;
                    margin-top: 5px;
                }
                .lista_mg_item {
                    display: block;
                    padding: 5px;

                    background-color: #FFF;
                }
                .lista_mg_item_titulo {
                    color: green;
                }
                .tbl_mg thead tr {
                    background-color: #2494F2;
                    color: #FFF;
                }
                .tbl_mg tbody tr.first {
                    background-color: #ebebeb;
                }
            </style>

            <script type="text/javascript">
                jQuery(document).ready(function() {
                    jQuery('.filter_categoria1').on('change', function() {
                        var valor = jQuery('.filter_categoria1').select2('val');
                        jQuery('.field_categoria1').select2('val', valor);
                    });

                    jQuery('.filter_categoria2').on('change', function() {
                        var valor = jQuery('.filter_categoria2').select2('val');
                        if(null == valor || undefined == valor || '' == valor) {
                            jQuery('.field_categoria2').select2('val', '');
                        } else {
                            jQuery('.field_categoria2').select2('val', valor);
                        }
                    });

                    jQuery('.filter_categoria3').on('change', function() {
                        var valor = jQuery('.filter_categoria3').select2('val');
                        if(null == valor || undefined == valor || '' == valor) {
                            jQuery('.field_categoria3').select2('val', '');
                        } else {
                            jQuery('.field_categoria3').select2('val', valor);
                        }
                    });

                    jQuery('.filter_tipo_a').on('ifChecked', function() {
                        jQuery('.field_tipo_a').iCheck('check');
                        jQuery('.filter_tipo_b').iCheck('uncheck');
                    });

                    jQuery('.filter_tipo_b').on('ifChecked', function() {
                        jQuery('.field_tipo_b').iCheck('check');
                        jQuery('.filter_tipo_a').iCheck('uncheck');
                    });
                });
            </script>

            <div class="block-flat">
                <form method="POST" data-form="true" data-success-url="${url}/musica/programacao-audiostore">
                    <table class="table tbl_mg">
                        <thead>
                            <tr>
                                <td>Música</td>
                                <td>Cliente</td>
                                <td>Categoria Primária </td>
                                <td>Categoria Secundário </td>
                                <td>Categoria Terciária </td>
                                <td>Tipo</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="first">
                                <td></td>
                                <td>
                                    <select class="select2 sel_cat2" data-rule-required="true" disabled="disabled">
                                        <option>${clienteBean.nome}</option>
                                    </select>
                                </td>
                                <td>
                                    <select class="select2 filter_categoria1" data-rule-required="true" >
                                        <c:forEach items="${categoriaList}" var="item">
                                            <option value="${item.codigo}">${item.categoria}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select class="select2 filter_categoria2" data-rule-required="true" >
                                        <option>Nenhuma</option>
                                        <c:forEach items="${categoriaList}" var="item">
                                            <option value="${item.codigo}">${item.categoria}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select class="select2 filter_categoria3" data-rule-required="true" >
                                        <option>Nenhuma</option>
                                        <c:forEach items="${categoriaList}" var="item">
                                            <option value="${item.codigo}">${item.categoria}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <label class="radio-inline"> <input type="radio" class="icheck filter_tipo_a" id="optionsRadios1" value="${true}" >&nbsp;Cut </label>
                                    <label class="radio-inline"> <input type="radio" class="icheck filter_tipo_b" id="optionsRadios1" value="${false}" checked="checked">&nbsp;FadeOut </label>
                                </td>
                            </tr>
                            <c:forEach items="${musicaGeralList}" var="mg" varStatus="vs">
                                <c:if test="${mg.existe}">
                                    <tr style="background-color: #C0311E; color: #FFF;">
                                        <td>
                                            ${mg.titulo}
                                        </td>

                                        <td colspan="5"> ${mg.msgErro} </td>
                                    </tr>
                                </c:if>

                                <c:if test="${not mg.existe}">
                                    <tr>
                                        <td>
                                            <input type="hidden" name="audiostoreMusicaBeanList[${vs.index+1}].ultimaImportacao"  value="1"/>
                                            <input type="hidden" name="audiostoreMusicaBeanList[${vs.index+1}].musicaGeral"  value="${mg.id}"/>
                                            <input type="hidden" name="audiostoreMusicaBeanList[${vs.index+1}].cliente.idcliente"  value="${clienteBean.idcliente}"/> 
                                            ${mg.titulo} - ${mg.id}
                                        </td>

                                        <td>
                                            
                                            <select name="audiostoreMusicaBeanList[${vs.index+1}].cliente.idcliente" class="select2 sel_cat2" data-rule-required="true" disabled="disabled">
                                                <option>${clienteBean.nome}</option>
                                            </select>
                                        </td>

                                        <td>
                                            <select class="select2 field_categoria1" name="audiostoreMusicaBeanList[${vs.index+1}].categoria1.codigo" data-rule-required="true" >
                                                <c:forEach items="${categoriaList}" var="item">
                                                    <option value="${item.codigo}">${item.categoria}</option>
                                                </c:forEach>
                                            </select>
                                        </td>

                                        <td>
                                            <select class="select2 field_categoria2" name="audiostoreMusicaBeanList[${vs.index+1}].categoria2.codigo" data-rule-required="true" >
                                                <option>Nenhuma</option>
                                                <c:forEach items="${categoriaList}" var="item">
                                                    <option value="${item.codigo}">${item.categoria}</option>
                                                </c:forEach>
                                            </select>
                                        </td>

                                        <td>
                                            <select class="select2 field_categoria3" name="audiostoreMusicaBeanList[${vs.index+1}].categoria3.codigo" data-rule-required="true" >
                                                <option>Nenhuma</option>
                                                <c:forEach items="${categoriaList}" var="item">
                                                    <option value="${item.codigo}">${item.categoria}</option>
                                                </c:forEach>
                                            </select>
                                        </td>

                                        <td>
                                            <label class="radio-inline"> <input type="radio" class="icheck field_tipo_a"  name="audiostoreMusicaBeanList[${vs.index+1}].cut" id="optionsRadios1" value="${true}" >&nbsp;Cut </label>
                                            <label class="radio-inline"> <input type="radio" class="icheck field_tipo_b"  name="audiostoreMusicaBeanList[${vs.index+1}].cut" id="optionsRadios1" value="${false}" checked="checked">&nbsp;FadeOut </label>
                                        </td>
                                    </tr>
                                </c:if>

                            </c:forEach>
                        </tbody>
                    </table>

                    <button type="submit" class="btn btn-default">
                        <i class="fa fa-save"></i> Salvar
                    </button>
                </form>
            </div>

        </c:if>

        <c:if test="${musicaGeralList eq null or empty musicaGeralList}">
            <script type="text/javascript">
                jQuery(document).ready(function() {
                    if (null != jQuery('.select_cliente').children('option:selected').val() && undefined != jQuery('.select_cliente').children('option:selected').val() && '' != jQuery('.select_cliente').children('option:selected').val() && jQuery('.select_cliente').children('option:selected').val() > 0) {
                        var clienteid = jQuery('.select_cliente').children('option:selected').val();

                        jQuery('.sel_cat1').find('option.rmv').remove().end();
                        jQuery('.sel_cat2').find('option.rmv').remove().end();
                        jQuery('.sel_cat3').find('option.rmv').remove().end();

                        jQuery.ajax({
                            url: '${url}/musica/programacao-audiostore/categorias-by-cliente/',
                            type: 'POST',
                            data: {clienteid: clienteid},
                            success: function(response) {
                                for (i in response) {
                                    var item = response[i];
                                    if ('${audiostoreMusicaBean.categoria1.codigo}' == item.codigo) {
                                        jQuery('.sel_cat1').append('<option selected="selected" class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                        jQuery('.sel_cat2').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                        jQuery('.sel_cat3').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                    } else if ('${audiostoreMusicaBean.categoria2.codigo}' == item.codigo) {
                                        jQuery('.sel_cat1').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                        jQuery('.sel_cat2').append('<option selected="selected" class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                        jQuery('.sel_cat3').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                    } else if ('${audiostoreMusicaBean.categoria3.codigo}' == item.codigo) {
                                        jQuery('.sel_cat1').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                        jQuery('.sel_cat2').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                        jQuery('.sel_cat3').append('<option selected="selected" class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                    } else {
                                        jQuery('.sel_cat1').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                        jQuery('.sel_cat2').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                        jQuery('.sel_cat3').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                    }
                                }
                            },
                            error: function(response) {
                                console.log(response);
                            }
                        });
                    }

                    jQuery('.select_cliente').on('change', function() {
                        var clienteid = jQuery(this).val();
                        jQuery('.sel_cat1').find('option.rmv').remove().end();
                        jQuery('.sel_cat2').find('option.rmv').remove().end();
                        jQuery('.sel_cat3').find('option.rmv').remove().end();

                        jQuery.ajax({
                            url: '${url}/musica/programacao-audiostore/categorias-by-cliente/',
                            type: 'POST',
                            data: {clienteid: clienteid},
                            success: function(response) {
                                for (i in response) {
                                    var item = response[i];
                                    jQuery('.sel_cat1').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                    jQuery('.sel_cat2').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                    jQuery('.sel_cat3').append('<option class="rmv" value="' + item.codigo + '">' + item.categoria + '</option>');
                                }
                            },
                            error: function(response) {
                                console.log(response);
                            }
                        });
                    });
                });
            </script>


            <form id="cad_cliente" method="POST" data-form="true" data-success-url="${url}/musica/programacao-audiostore">
                <c:if test="${cadastrar ne true}">
                    <input type="hidden" name="audiostoreMusicaBean.id" value="${audiostoreMusicaBean.id}"  />
                </c:if>

                <input type="hidden" name="audiostoreMusicaBean.musicaGeral" value="${audiostoreMusicaBean.musicaGeral}"  />

                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>Caminho do arquivo</label>
                            <br />
                            <input class="form-control" value="${musicaGeralBean.arquivo}" disabled="disabled"/>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>Titulo da musica</label>
                            <br />
                            <input class="form-control" value="${musicaGeralBean.titulo}" disabled="disabled"/>
                        </div>
                    </div>
                        
                    <input type="hidden" name="audiostoreMusicaBean.cliente.idcliente" value="${sessionUsuario.cliente.idcliente}" />
<!--                    <div class="col-md-12">
                        <div class="form-group">
                            <label>Cliente</label>
                            <br />
                            <select  class="form-control sel_cliente select_cliente"  data-rule-required="true" name="audiostoreMusicaBean.cliente.idcliente">
                                <option value="" >Selecione um cliente</option>
                                <c:forEach items="${clienteBeanList}" var="cli">
                                    <option value="${cli.idcliente}" ${audiostoreMusicaBean.cliente.idcliente eq cli.idcliente ? 'selected="selected"':''}>${cli.nome}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>-->

                </div>

                <div class="row">
                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Categoria Primária</label> 
                            <br />
                            <select  class="form-control sel_cat1" name="audiostoreMusicaBean.categoria1.codigo" data-rule-required="true" >
                                <c:if test="${not cadastrar}">
                                    <c:forEach items="${categoriasByCliente2}" var="item">
                                        <option class="rmv" value="${item.codigo}"  ${audiostoreMusicaBean.categoria1.codigo  eq item.codigo ? 'selected="selected"' : ''}>${item.categoria}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </div>

<!--                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Categoria Secundário</label> 
                            <br />
                            <select class="form-control sel_cat2" name="audiostoreMusicaBean.categoria2.codigo" data-rule-required="true" >
                                <c:if test="${not cadastrar}"> 
                                    <option>Nenhuma</option>
                                    <c:forEach items="${categoriasByCliente2}" var="item">
                                        <option class="rmv" value="${item.codigo}"  ${audiostoreMusicaBean.categoria2.codigo  eq item.codigo ? 'selected="selected"' : ''}>${item.categoria}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </div>-->
                    
                    <input type="hidden" name="audiostoreMusicaBean.categoria2.codigo" value="0" />
                    <input type="hidden" name="audiostoreMusicaBean.categoria3.codigo" value="0" />

<!--                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Categoria Terciária</label> 
                            <br />
                            <select class="form-control sel_cat3" name="audiostoreMusicaBean.categoria3.codigo" data-rule-required="true" >
                                <c:if test="${not cadastrar}">
                                    <option>Nenhuma</option>
                                    <c:forEach items="${categoriasByCliente2}" var="item">
                                        <option class="rmv" value="${item.codigo}"  ${audiostoreMusicaBean.categoria3.codigo  eq item.codigo ? 'selected="selected"' : ''}>${item.categoria}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </div>-->

<!--                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Dias execução <i>(Primária)</i></label>
                            <input type="text" name="audiostoreMusicaBean.diasExecucao1" class="form-control" placeholder="Dias execução (Primária)"  
                                   data-rule-required="true" 
                                   data-rule-number="true" value="${audiostoreMusicaBean.diasExecucao1}">
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Dias execução <i>(Secundário)</i></label>
                            <input type="text" name="audiostoreMusicaBean.diasExecucao2" class="form-control" placeholder="Dias execução (Secundário)"  
                                   data-rule-required="true" 
                                   data-rule-number="true" value="${audiostoreMusicaBean.diasExecucao2}">
                        </div>
                    </div>-->

                    <input type="hidden" name="audiostoreMusicaBean.diasExecucao1" value="0" />
                    <input type="hidden" name="audiostoreMusicaBean.diasExecucao2" value="0" />

                </div>

                <hr />

                <div class="row">
                    <div class="col-md-2">
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
                                   data-rule-number="true" value="${audiostoreMusicaBean.qtdePlayer eq null ? 999 : audiostoreMusicaBean.qtdePlayer}">
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
                            Faz parte da ultima importação 
                            <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.ultimaImportacao" id="optionsRadios1" value="${true}" ${audiostoreMusicaBean.ultimaImportacao ? 'checked="checked"' : ''} >&nbsp;Sim</label>
                            <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.ultimaImportacao" id="optionsRadios1" value="${false}"  ${not audiostoreMusicaBean.ultimaImportacao ? 'checked="checked"' : ''}>&nbsp;Não</label>
                        </div>
                    </div>



<!--                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Data de inclusao</label>

                            <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                <input type="text" name="audiostoreMusicaBean.data" class="form-control datepicker" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-mask="99/99/9999"value="${cf:dateFormat(audiostoreMusicaBean.data, "dd/MM/yyyy")}">
                                <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                            </div>
                        </div>
                    </div>-->

                    <input type="hidden" name="audiostoreMusicaBean.data" value="${cf:dateFormat(audiostoreMusicaBean.data, "dd/MM/yyyy")}" />
                    

<!--                    <div class="col-md-2">
                        <div class="form-group">
                            <label></label>
                            <br />
                            Tipo <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.cut" id="optionsRadios1" value="${true}" ${audiostoreMusicaBean.cut ? 'checked="checked"' : ''} >&nbsp;Cut </label>
                            <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.cut" id="optionsRadios1" value="${false}"  ${not audiostoreMusicaBean.cut ? 'checked="checked"' : ''}>&nbsp;FadeOut </label>
                        </div>
                    </div>-->
<!--
                    <div class="col-md-3">
                        <div class="form-group">
                            <label></label>
                            <br />
                            Sem Som? 
                            <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.semSom" id="optionsRadios1" value="${true}" ${audiostoreMusicaBean.semSom ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                            <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.semSom" id="optionsRadios1" value="${false}"  ${not audiostoreMusicaBean.semSom ? 'checked="checked"' : ''}>&nbsp;Não </label>
                        </div>
                    </div>-->
                    <input type="hidden" name="audiostoreMusicaBean.semSom" value="${false}" />
                    <input type="hidden" name="audiostoreMusicaBean.msg" value="" />
                    <input type="hidden" name="audiostoreMusicaBean.cut" value="${false}" />

<!--                    <div class="col-md-12">
                        <div class="form-group">
                            <div class="block-flat">
                                <div class="header">							
                                    <h3>Mensagem</h3>
                                </div>
                                <div class="content"> 
                                    <textarea class="form-control" name="audiostoreMusicaBean.msg" rows="10" data-rule-required="true">${audiostoreMusicaBean.msg}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>-->

                </div>


                <button type="submit" class="btn btn-default">
                    <i class="fa fa-save"></i> Salvar
                </button>
            </form>
        </c:if>
    </jsp:body>
</instore:template>