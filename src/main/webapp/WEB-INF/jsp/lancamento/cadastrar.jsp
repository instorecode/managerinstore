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
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/lancamento">
            <input type="hidden" name="lancamentoBean.id" value="${lancamentoBean.id}" />

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Descrição</label>
                        <div id="the-basics">
                            <input type="text" name="lancamentoBean.descricao" class="form-control typeahead" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" value="${lancamentoBean.descricao}">
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Entidade / CNPJ</label>
                        <br />
                        <select name="lancamentoBean.lancamentoCnpj.id" data-selectradio="true" class="form-control arquivosDeMusica"  data-rule-required="true" style="margin-left: -30px;">
                            <c:forEach items="${lancamentoCnpjList}" var="ent">
                                <option value="${ent.id}" ${ent.id eq lancamentoBean.lancamentoCnpj.id ? 'selected="selected"' : ''}>${ent.nome} - ${ent.cnpj}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Debito ou Credito</label>
                        <br />

                        <select name="lancamentoBean.debito" data-selectradio="true" class="form-control arquivosDeMusica"  data-rule-required="true" style="margin-left: -30px;">
                            <option value="${true}" ${lancamentoBean.debito ? 'selected="selected"' : ''} ${cadastrar eq true ? 'selected="selected"' : ''}>Débito</option>
                            <option value="${false}" ${lancamentoBean.credito ? 'selected="selected"' : ''}>Crédito</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Saldo positivo ? </label>
                        <br />

                        <select name="lancamentoBean.positivo" data-selectradio="true" class="form-control arquivosDeMusica"  data-rule-required="true" style="margin-left: -30px;">
                            <option value="${true}" ${lancamentoBean.positivo ? 'selected="selected"' : ''} ${cadastrar eq true ? 'selected="selected"' : ''}>Sim</option>
                            <option value="${false}" ${not lancamentoBean.positivo ? 'selected="selected"' : ''}>Não</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label>Valor</label>
                        <input type="text" name="lancamentoBean.valor" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-maskmoney="true"
                               value='<fmt:formatNumber value="${lancamentoBean.valor}" minFractionDigits="2" />'>
                    </div>
                </div>


            </div>

            <div class="row" style="${cadastrar eq false ? 'display:none;' : ''}">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Vai se repetir ?</label>
                        <br />
                        <select name="lancamentoBean.loop" data-selectradio="true" class="form-control arquivosDeMusica"  data-rule-required="true" style="margin-left: -30px;">
                            <option value="${true}" >Sim</option>
                            <option value="${false}" selected="selected">Não</option>
                        </select>
                    </div>
                </div>
            </div>

            <br /> 

            <div class="row intervalo1"> 
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data do lançamento</label>
                        <input type="text" name="lancamentoBean.mes" class="form-control datepicker" placeholder="Data do lançamento"  
                               data-mask="99/99/9999"
                               value="${cf:dateFormat(lancamentoBean.mes, "dd/MM/yyyy")}">
                    </div>
                </div>

                <div class="col-md-4" style="${cadastrar eq false ? '' : 'display:none;'}">
                    <br />
                    <div class="form-group">
                        <c:if test="${lancamentoBean.datFechamento ne null}">
                            <input type="checkbox" name="lancamentoBean.datFechamento" value="${cf:dateFormat(lancamentoBean.datFechamento, "dd/MM/yyyy")}" checked="checked" />
                        </c:if>
                        <c:if test="${lancamentoBean.datFechamento eq null}">
                            <input type="checkbox" name="lancamentoBean.datFechamento" value="${cf:dateCurrent("dd/MM/yyyy")}" />
                        </c:if>

                        <label>Marcar lançamento como finalizado</label>
                    </div>
                </div>
            </div>

            <div class="row intervalo2" style="display:none;">
                <div class="col-md-4">
                    <b>Vai se repetir em qual intervalo de data?</b>
                </div>
                <hr />
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Data de inicio</label>
                        <input type="text" name="d1" class="form-control datepicker" placeholder=""  
                               data-mask="99/99/9999"
                               value="">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Data de termino</label>
                        <input type="text" name="d2" class="form-control monthPicker" placeholder=""  
                               value="">
                    </div>
                </div>
            </div>


            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
            <script>
                jQuery(document).ready(function() {

                    $(".monthPicker").datepicker({
                        dateFormat: 'MM yy',
                        changeMonth: true,
                        changeYear: true,
                        showButtonPanel: true,
                        onClose: function(dateText, inst) {
                            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                            $(this).val($.datepicker.formatDate('MM yy', new Date(year, month, 1)));
                        }
                    });

                    $(".monthPicker").focus(function() {
                        $(".ui-datepicker-calendar").hide();
                        $("#ui-datepicker-div").position({
                            my: "center top",
                            at: "center bottom",
                            of: $(this)
                        });
                    });

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
        </form>
    </jsp:body>
</instore:template>