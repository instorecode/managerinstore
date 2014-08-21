<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/lancamento" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lançamentos  </a>
    </jsp:attribute>

    <jsp:body>

        <c:if test="${not (cadastrar or lancamentoBean.datFechamento eq null)}">
            <div class="alert alert-warning alert-white rounded msg_err0">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <div class="icon"><i class="fa fa-warning"></i></div>
                <strong>Importante!</strong> &nbsp;&nbsp;O lançamento já foi finalizado e não pode ser atualizado
            </div>
        </c:if>
        
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/lancamento">
            <input type="hidden" name="lancamentoBean.id" value="${lancamentoBean.id}" />
            <input type="hidden" name="lancamentoBean.positivo" value="${true}" />

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Descrição</label>
                        <div id="the-basics">
                            <input type="text" name="lancamentoBean.descricao" class="form-control typeahead" placeholder="Descrição"    value="${lancamentoBean.descricao}">
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Entidade Financeiro - CNPJ</label>
                        <select name="lancamentoBean.lancamentoCnpj.id" class="form-control"  data-rule-required="true">
                            <c:forEach items="${lancamentoCnpjList}" var="ent">
                                <option value="${ent.id}" ${ent.id eq lancamentoBean.lancamentoCnpj.id ? 'selected="selected"' : ''}>${ent.nome} - ${ent.cnpj}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Pagar ou Receber</label>
                        <br />

                        <select name="lancamentoBean.debito" data-selectradio="true" class="form-control arquivosDeMusica"  data-rule-required="true">
                            <option value="${true}" ${lancamentoBean.debito ? 'selected="selected"' : ''} ${cadastrar eq true ? 'selected="selected"' : ''}>Pagar</option>
                            <option value="${false}" ${lancamentoBean.credito ? 'selected="selected"' : ''}>Receber</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Valor</label>
                        <input type="text" name="lancamentoBean.valor" class="form-control" placeholder="Valor"  
                               data-rule-required="true" 
                               data-maskmoney="true"
                               value='<fmt:formatNumber value="${lancamentoBean.valor}" minFractionDigits="2" />'>
                    </div>
                </div>

                <div class="col-md-4" style="${cadastrar eq false ? 'display:none;' : ''}">
                    <div class="form-group">
                        <label>Parcelado</label>
                        <select name="lancamentoBean.loop" data-selectradio="true" class="form-control"  data-rule-required="true">
                            <option value="${true}" >Sim</option>
                            <option value="${false}" selected="selected">Não</option>
                        </select>
                    </div>
                </div>

                <div class="intervalo1"> 
                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Data do lançamento</label>
                            <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                <input  type="text" name="lancamentoBean.mes" class="form-control" placeholder="Data do lançamento"  
                                        data-mask="99/99/9999"
                                        value="${cf:dateFormat(lancamentoBean.mes, "dd/MM/yyyy")}" readonly />
                                <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                            </div>	

                        </div>
                    </div>

                    <div class="col-md-12" style="${cadastrar eq false ? '' : 'display:none;'}">
                        <div class="form-group" style="margin-left: -20px">
                            <c:if test="${lancamentoBean.datFechamento ne null}">
                                <div class="checkbox">
                                    <label><input type="checkbox" class="icheck" name="lancamentoBean.datFechamento" value="${cf:dateFormat(lancamentoBean.datFechamento, "dd/MM/yyyy")}" checked="checked" /></label>
                                    &nbsp;&nbsp;Marcar lançamento como não finalizado ${cf:dateCurrent("dd/MM/yyyy")}
                                </div>
                            </c:if>
                            <c:if test="${lancamentoBean.datFechamento eq null}">
                                <div class="checkbox">
                                    <label><input type="checkbox" class="icheck" name="lancamentoBean.datFechamento" value="${cf:dateCurrent("dd/MM/yyyy")}" /></label>
                                    &nbsp;&nbsp;Marcar lançamento como finalizado ${cf:dateCurrent("dd/MM/yyyy")}
                                </div>
                            </c:if>


                        </div>
                    </div>
                </div>

            </div>



            <div class="row intervalo2" style="display:none;">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de inicio</label>

                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="d1" class="form-control datepicker" placeholder=""  
                                   data-mask="99/99/9999"
                                   value="" readonly/>
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>

                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de termino</label>
                        <input type="text" name="d2" class="form-control" placeholder="" value="" data-mask="99/9999">
                    </div>
                </div>
            </div>
            <c:if test="${cadastrar or lancamentoBean.datFechamento eq null}">
                <button type="submit" class="btn btn-default">
                    <i class="fa fa-save"></i> Salvar
                </button>
            </c:if>


            <script>
                jQuery(document).ready(function() {

//                    $(".monthPicker").datepicker({
//                        dateFormat: 'MM yy',
//                        changeMonth: true,
//                        changeYear: true,
//                        showButtonPanel: true,
//                        onClose: function(dateText, inst) {
//                            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
//                            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
//                            $(this).val($.datepicker.formatDate('MM yy', new Date(year, month, 1)));
//                        }
//                    });
//
//                    $(".monthPicker").focus(function() {
//                        $(".ui-datepicker-calendar").hide();
//                        $("#ui-datepicker-div").position({
//                            my: "center top",
//                            at: "center bottom",
//                            of: $(this)
//                        });
//                    });

                    jQuery('[name="lancamentoBean.loop"]').on('change', function() {
                        console.log(jQuery(this));
                        if (jQuery(this).val() != 'true') {
                            jQuery('.intervalo2').hide();
                            jQuery('.intervalo1').show();
                        } else {
                            jQuery('.intervalo2').show();
                            jQuery('.intervalo1').hide();
                        }
                    });

                    var states = [];

                    jQuery.getJSON('${url}/lancamento?datajson=true', function(data) {
                        for (i in data) {
                            var item = data[i];
                            states[states.length] = item.descricao;
                        }
                    });

                    var substringMatcher = function(strs) {
                        return function findMatches(q, cb) {
                            var matches, substrRegex;

                            // an array that will be populated with substring matches
                            matches = [];

                            // regex used to determine if a string contains the substring `q`
                            substrRegex = new RegExp(q, 'i');

                            // iterate through the pool of strings and for any string that
                            // contains the substring `q`, add it to the `matches` array
                            $.each(strs, function(i, str) {
                                if (substrRegex.test(str)) {
                                    // the typeahead jQuery plugin expects suggestions to a
                                    // JavaScript object, refer to typeahead docs for more info
                                    matches.push({value: str});
                                }
                            });

                            cb(matches);
                        };
                    };




                    $('#the-basics .typeahead').typeahead({
                        hint: true,
                        highlight: true,
                        minLength: 1
                    },
                    {
                        name: 'states',
                        displayKey: 'value',
                        source: substringMatcher(states)
                    });
                });
            </script>

            <style type="text/css">
                .tt-dropdown-menu {
                    background-color: white;
                    padding: 10px;
                }
            </style>
        </form>
    </jsp:body>
</instore:template>