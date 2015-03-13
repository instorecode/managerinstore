<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 

<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-comercial" class="btn btn-voltar btn-default"> <i class="fa fa-hand-o-left"></i> Comerciais </a>
    </jsp:attribute>

    <jsp:body>
        <c:forEach var="error" items="${errors}">
            <div class="alert alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <i class="fa fa-times-circle sign"></i><strong>Error!</strong> ${error.message}
            </div>
        </c:forEach>


<!--<form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-comercial" enctype="multipart/form-data">-->
        <form data-form="false" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="audiostoreComercialBean.id" value="${audiostoreComercialBean.id}" />
            <input type="hidden" name="audiostoreComercialBean.ultimaExecucao" value="01/01/2000" />

            <c:if test="${isPageCadastro}">
                <input type="file" name="arquivo" accept="audio/*" style="display: none" value="${arquivo}" />
            </c:if>

            <c:if test="${isPageCadastro eq false}">
                <input type="hidden" name="audiostoreComercialBean.arquivo"  value="${audiostoreComercialBean.arquivo}" />
            </c:if>

            <script type="text/javascript">
                jQuery(document).ready(function() {
                    jQuery('[name="audiostoreComercialBean.arquivo"]').on('click', function() {
                        jQuery('[name="arquivo"]').click();
                    });

                    jQuery('[name="arquivo"]').on('change', function() {
                        jQuery('[name="audiostoreComercialBean.arquivo"]').val(jQuery(this).val());
                    });
                });
            </script>

            <div class="row">

                <!--<div class="col-md-3">--> 
                    <input type="hidden" name="audiostoreComercialBean.cliente.idcliente" value="${sessionUsuario.cliente.idcliente}" />
                    <!--
                    <div class="form-group">
                        <label>Cliente</label>
                        <select class="form-control ${isPageCadastro eq true ? 'select_cliente' : ''} tag_sel_cli" name="audiostoreComercialBean.cliente.idcliente" data-rule-required="true" >
                            <c:forEach items="${clienteBeanList}" var="item">
                                <option value="${item.idcliente}" ${audiostoreComercialBean.cliente.idcliente eq item.idcliente ? 'selected="selected"':''}>${item.nome}</option> 
                            </c:forEach>
                        </select>
                    </div>
                    -->
                <!--</div>-->

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Titulo</label>
                        <input type="text" name="audiostoreComercialBean.titulo" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreComercialBean.titulo}">
                    </div>
                </div>

                <c:if test="${isPageCadastro}">
                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Arquivo</label>
                            <div class="input-group">
                                <input type="text" name="audiostoreComercialBean.arquivo" class="form-control" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="30" value="${audiostoreComercialBean.arquivo}">
                                <span class="input-group-addon"> <i class="fa fa-file-audio-o"></i> </span>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${isPageCadastro eq false}">
                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Arquivo</label>
                            <div class="input-group">
                                <input type="text" class="form-control"  value="${audiostoreComercialBean.arquivo}" readonly="readonly">
                                <span class="input-group-addon"> <i class="fa fa-file-audio-o"></i> </span> 
                            </div>
                        </div>
                    </div>
                </c:if>


                <div class="col-md-2">
                    <div class="form-group">
                        <label>Categoria</label> 
                        <br />
                        <select class="form-control" name="audiostoreComercialBean.audiostoreCategoria.codigo" data-rule-required="true" >
                            <c:forEach items="${categoriaBeanList}" var="cat">
                                <option value="${cat.codigo}" ${audiostoreComercialBean.audiostoreCategoria.codigo eq cat.codigo ? 'selected="selected"':''}>${cat.categoria}</option> 
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Tipo interprete</label> 
                        <br />
                        <select class="form-control" name="audiostoreComercialBean.tipoInterprete" data-rule-required="true" >
                            <option value="1" ${audiostoreComercialBean.tipoInterprete eq 1 ? 'selected="selected"' : ''} >Masculino</option> 
                            <option value="2" ${audiostoreComercialBean.tipoInterprete eq 2 ? 'selected="selected"' : ''} >Feminino</option> 
                            <option value="3" ${audiostoreComercialBean.tipoInterprete eq 3 ? 'selected="selected"' : ''} >Grupo</option> 
                            <option value="4" ${audiostoreComercialBean.tipoInterprete eq 4 ? 'selected="selected"' : ''} >Instrumental</option> 
                            <option value="5" ${audiostoreComercialBean.tipoInterprete eq 5 ? 'selected="selected"' : ''} >Jingle</option> 
                        </select>
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col-md-12">
                    <b> Dependencias </b>
                    <hr />
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Primária</label>
                        <br />
                        <select name="audiostoreComercialBean.dependencia1" class="form-control"  data-rule-required="true">
                            <option value=" ">Nenhuma</option>
                            <c:forEach items="${arquivoMusicaList}" var="musica">
                                <option value="${musica.caminho}" ${musica.caminho eq audiostoreComercialBean.dependencia1 ? 'selected="selected"' : ''}>${musica.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Secundária</label>
                        <br />
                        <select name="audiostoreComercialBean.dependencia2" class="form-control"  data-rule-required="true">
                            <option value=" ">Nenhuma</option>
                            <c:forEach items="${arquivoMusicaList}" var="musica">
                                <option value="${musica.caminho}" ${musica.caminho eq audiostoreComercialBean.dependencia2 ? 'selected="selected"' : ''}>${musica.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label value=" ">Terciária</label>
                        <br />
                        <select name="audiostoreComercialBean.dependencia3" class="form-control"  data-rule-required="true">
                            <option value=" ">Nenhuma</option>
                            <c:forEach items="${arquivoMusicaList}" var="musica">
                                <option value="${musica.caminho}" ${musica.caminho eq audiostoreComercialBean.dependencia3 ? 'selected="selected"' : ''}>${musica.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <hr />

            <div class="row">
                <div class="col-md-1">
                    <div class="form-group">
                        <label>Tempo</label>
                        <input type="text" name="tempoTotal" class="form-control" placeholder="Nome"  
                               data-mask="99:99:99"  
                               data-rule-required="true" 
                               value="${audiostoreComercialBean.tempoTotal ne null ? cf:dateFormat(audiostoreComercialBean.tempoTotal, "HH:mm:ss") : '00:00:00'}">
                    </div>
                </div>

                <div class="col-md-1">
                    <div class="form-group">
                        <label>Quantidade </label>

                        <input type="text" name="audiostoreComercialBean.qtde" class="form-control span2" placeholder="Quantidade máxima de execuções no dia)"   data-rule-number="true" value="${audiostoreComercialBean.qtde}">
                    </div>
                </div>


                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de vencimento</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreComercialBean.dataVencimento" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999" value="${audiostoreComercialBean.dataVencimento ne null ? cf:dateFormat(audiostoreComercialBean.dataVencimento, "dd/MM/yyyy") : '31/12/2050'}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Periodo Inicial</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreComercialBean.periodoInicial" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999" value="${cf:dateFormat(audiostoreComercialBean.periodoInicial, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Periodo Final</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreComercialBean.periodoFinal" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999" value="${audiostoreComercialBean.periodoFinal ne null ? cf:dateFormat(audiostoreComercialBean.periodoFinal, "dd/MM/yyyy") : '31/12/2050'}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Tocar em dias alternados</label>
                        <br />
                        <select name="audiostoreComercialBean.diasAlternados" class="form-control"  data-rule-required="true">
                            <option value="${true}" ${audiostoreComercialBean.diasAlternados eq true ? 'selected="selected"' : ''}>Sim</option>
                            <option value="${false}" ${audiostoreComercialBean.diasAlternados eq null or audiostoreComercialBean.diasAlternados eq false ? 'selected="selected"' : ''}>Não</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Sem som?</label>
                        <br />
                        <select name="audiostoreComercialBean.semSom" class="form-control"  data-rule-required="true">
                            <option value="${true}" ${audiostoreComercialBean.semSom eq true ? 'selected="selected"' : ''}>Sim</option>
                            <option value="${false}" ${audiostoreComercialBean.semSom eq null or audiostoreComercialBean.semSom eq false ? 'selected="selected"' : ''}>Não</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <div class="block-flat">
                            <div class="header">							
                                <h3>Letra</h3>
                            </div>
                            <div class="content"> 
                                <textarea class="ckeditor form-control" name="audiostoreComercialBean.texto" rows="10" data-rule-required="true">${audiostoreComercialBean.texto}</textarea>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <div class="block-flat">
                            <div class="header">							
                                <h3>Mensagem</h3>
                            </div>
                            <div class="content"> 
                                <textarea class="ckeditor form-control" name="audiostoreComercialBean.msg" rows="10" data-rule-required="true">${audiostoreComercialBean.msg}</textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col-md-12">
                    <b> Horários pré definidos </b>
                    <hr />
                    <div class="row">
                        <div class="col-md-6">
                            <div class="table-responsive">
                                <table class="tbl table table-striped table-bordered table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <td style="background-color: #606060; color: #FFF; font-weight: bold;">Interromper</td>
                                            <td style="background-color: #606060; color: #FFF; font-weight: bold;">Horário</td>
                                            <td style="background-color: #606060; color: #FFF; font-weight: bold;">Semana</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set scope="session"  var="__HORA__" value="0"></c:set>
                                        <c:set scope="session"  var="indx" value="0"></c:set>
                                        <c:forEach items="${shs}" var="sh" varStatus="vs">
                                            <c:if test="${__HORA__ eq sh.horario}">
                                                ${sh.semana} ,
                                            <input class="hidden_input_sh" type="hidden" name="sh[${indx}].semana" value="${sh.semana}" />
                                            <input class="hidden_input_sh" type="hidden" name="sh[${indx}].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ${cf:dateFormat(sh.horario, "HH:mm")}:00" />
                                            <input class="hidden_input_sh" type="hidden" name="sh[${indx}].interromperMusicaTocada" value="${sh.interromperMusicaTocada}" />
                                        </c:if>

                                        <c:if test="${__HORA__ ne sh.horario}"> 
                                            </tr>
                                            </td>
                                            <tr>
                                                <td>${sh.interromperMusicaTocada ? "Sim" : "Não"}</td> 
                                                <td>${cf:dateFormat(sh.horario, "HH:mm")}</td> 
                                                <td>${sh.semana} ,
                                                    <input class="hidden_input_sh" type="hidden" name="sh[${indx}].semana" value="${sh.semana}" />
                                                    <input class="hidden_input_sh" type="hidden" name="sh[${indx}].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ${cf:dateFormat(sh.horario, "HH:mm")}:00" />
                                                    <input class="hidden_input_sh" type="hidden" name="sh[${indx}].interromperMusicaTocada" value="${sh.interromperMusicaTocada}" />
                                                    <c:set scope="session"  var="__HORA__" value="${sh.horario}"></c:set>
                                                </c:if>

                                                <c:set scope="session"  var="indx" value="${indx+1}"></c:set>
                                            </c:forEach>
                                            </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div style="float: left;">
                                <label> <input type="checkbox" name="segunda" value="1" class="icheck" />&nbsp;Segunda</label> 
                                <br />
                                <label> <input type="checkbox" name="terca" value="2" class="icheck"/>&nbsp;Terça</label> 
                                <br />
                                <label> <input type="checkbox" name="quarta" value="3" class="icheck"/>&nbsp;Quarta</label> 
                                <br />
                                <label> <input type="checkbox" name="quinta" value="4" class="icheck"/>&nbsp;Quinta</label> 
                                <br />
                                <label> <input type="checkbox" name="sexta" value="5" class="icheck"/>&nbsp;Sexta</label> 
                                <br />
                                <label> <input type="checkbox" name="sabado" value="6" class="icheck"/>&nbsp;Sábado</label> 
                                <br />
                                <label> <input type="checkbox" name="domingo" value="7" class="icheck"/>&nbsp;Domingo&nbsp;&nbsp;</label> 
                                <label> <input type="checkbox" name="todos" value="0" class="icheck"/>&nbsp;Marcar Todos</label> 
                                <br />
                                <label> <input type="checkbox" name="interromperMusicaTocada" value="0" class="icheck"/>&nbsp;Interromper música tocada </label> 
                            </div>
                            <div class="col-xs-3">
                                <div class="col-xs-12">
                                    Horário inicial
                                    <input type="text" name="hora" class="form-control span5" placeholder="Horário" data-mask="99:99" value="00:00"> 
                                </div>

                                <br />
                                <br />

                                <div class="col-xs-12"> 
                                    Horário final
                                    <input type="text" name="hora_final" class="form-control span5" placeholder="Horário final" data-mask="99:99" value=""> 
                                </div>

                                <br />
                                <br />

                                <div class="col-xs-12">
                                    Intervalo
                                    <input type="text" name="hora_intervalo" class="form-control span5" placeholder="Intervalo" data-mask="99:99" value=""> 
                                </div>




                                <div class="col-xs-12">
                                    <br />
                                    <button type="button" class="btn btn-default add_sh_intervalo" data-tooltip="true" title="Para remover um selecione um registro da tabela!" style="margin-left: 0px;">
                                        <i class="fa fa-plus"></i> Usar intervalo
                                    </button>
                                </div>

                            </div>

                            <button type="button" class="btn btn-default add_sh" data-tooltip="true" title="Para adicionar é necessário seleionar um ou mais dias da semana e informa um horário!">
                                <i class="fa fa-plus"></i>
                            </button>
                            <button type="button" class="btn btn-default rm_sh" data-tooltip="true" title="Para remover um selecione um registro da tabela!">
                                <i class="fa fa-trash-o"></i>
                            </button>

                            <button type="button" class="btn btn-default rm_all_sh" data-tooltip="true" title="Para remover um selecione um registro da tabela!">
                                <i class="fa fa-eraser"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>

        <style type="text/css">
            .tbl tr { cursor: pointer; }
        </style>

        <script type="text/javascript">
            jQuery(document).ready(function() {
                carrega_categorias();

                if (null != jQuery.storage("matriz_selecionada") && undefined != jQuery.storage("matriz_selecionada")) {
                    carrega_categorias();
                }

                function carrega_categorias() {
                    var idcliente = jQuery(".tag_sel_cli").val();
                    jQuery.ajax({
                        type: 'POST',
                        url: '${url}/audiostore-comercial/vld-categ',
                        data: {idcliente: idcliente},
                        success: function(json) {
                            jQuery('[name="audiostoreComercialBean.audiostoreCategoria.codigo"]').html('');
                            var option = '';
                            for (i in json) {
                                var item = json[i];
                                option += '<option value="' + item.codigo + '">' + item.categoria + '</option>';
                            }

                            jQuery('[name="audiostoreComercialBean.audiostoreCategoria.codigo"]').html(option);
                        }
                    });
                }

                jQuery('[name="audiostoreComercialBean.audiostoreCategoria.codigo"]').on("form-control-open", function() {
                    carrega_categorias()
                });

                jQuery('[name="audiostoreComercialBean.cliente.idcliente"]').on("change", function() {
                    carrega_categorias()
                });

                function load_dep() {

                    valor = jQuery(".tag_sel_cli").val();
                    if (null != valor && undefined != valor && "" != valor) {
                        jQuery.ajax({
                            url: "${url}/audiostore-comercial/dep/" + valor + "/${audiostoreComercialBean.id}",
                            dataType: "json",
                            success: function(json) {
                                jQuery('[name="audiostoreComercialBean.dependencia1"]').html('');
                                jQuery('[name="audiostoreComercialBean.dependencia2"]').html('');
                                jQuery('[name="audiostoreComercialBean.dependencia3"]').html('');
                                var option1 = '<option value=" " selected="selected">Nenhuma</option>';
                                for (i in json) {
                                    var item = json[i];
                                    var dp = '${audiostoreComercialBean.dependencia1}';

                                    if (dp === item.arquivo) {
                                        option1 += '<option value="' + item.arquivo + '" selected="selected">' + item.titulo + '</option>';
                                    } else {
                                        option1 += '<option value="' + item.arquivo + '">' + item.titulo + '</option>';
                                    }
                                }

                                var option2 = '<option value=" " selected="selected">Nenhuma</option>';
                                for (i in json) {
                                    var item = json[i];
                                    var dp = '${audiostoreComercialBean.dependencia2}';

                                    if (dp === item.arquivo) {
                                        option2 += '<option value="' + item.arquivo + '" selected="selected">' + item.titulo + '</option>';
                                    } else {
                                        option2 += '<option value="' + item.arquivo + '">' + item.titulo + '</option>';
                                    }
                                }

                                var option3 = '<option value=" " selected="selected">Nenhuma</option>';
                                for (i in json) {
                                    var item = json[i];
                                    var dp = '${audiostoreComercialBean.dependencia3}';

                                    if (dp === item.arquivo) {
                                        option3 += '<option value="' + item.arquivo + '" selected="selected">' + item.titulo + '</option>';
                                    } else {
                                        option3 += '<option value="' + item.arquivo + '">' + item.titulo + '</option>';
                                    }
                                }

                                jQuery('[name="audiostoreComercialBean.dependencia1"]').html(option1).change();
                                jQuery('[name="audiostoreComercialBean.dependencia2"]').html(option2).change();
                                jQuery('[name="audiostoreComercialBean.dependencia3"]').html(option3).change();
                            },
                            error: function(err) {
                                console.log("ERRO AO CONSULTAR AS DEPENDENCIAS DO CLIENTE");
                                console.log(err);
                            }
                        });
                    }
                }

                jQuery(".tag_sel_cli").on("change", function() {
                    load_dep();
                });
                jQuery('[name="audiostoreComercialBean.dependencia1"]').each(function() {
                    load_dep();
                });

                jQuery('[name="todos"]').on('ifChecked', function() {
                    var segunda = jQuery('[name="segunda"]');
                    var terca = jQuery('[name="terca"]');
                    var quarta = jQuery('[name="quarta"]');
                    var quinta = jQuery('[name="quinta"]');
                    var sexta = jQuery('[name="sexta"]');
                    var sabado = jQuery('[name="sabado"]');
                    var domingo = jQuery('[name="domingo"]');
                    var todos = jQuery('[name="todos"]');
                    segunda.iCheck('check');
                    terca.iCheck('check');
                    quarta.iCheck('check');
                    quinta.iCheck('check');
                    sexta.iCheck('check');
                    sabado.iCheck('check');
                    domingo.iCheck('check');

                });
                jQuery('[name="todos"]').on('ifUnchecked', function() {
                    var segunda = jQuery('[name="segunda"]');
                    var terca = jQuery('[name="terca"]');
                    var quarta = jQuery('[name="quarta"]');
                    var quinta = jQuery('[name="quinta"]');
                    var sexta = jQuery('[name="sexta"]');
                    var sabado = jQuery('[name="sabado"]');
                    var domingo = jQuery('[name="domingo"]');
                    var todos = jQuery('[name="todos"]');
                    segunda.iCheck('uncheck');
                    terca.iCheck('uncheck');
                    quarta.iCheck('uncheck');
                    quinta.iCheck('uncheck');
                    sexta.iCheck('uncheck');
                    sabado.iCheck('uncheck');
                    domingo.iCheck('uncheck');
                });

                jQuery(document).on('click', '.tbl tbody tr td', function(event) {
                    
                    if (null != jQuery(this).parent('tr').attr('data-selected') && undefined != jQuery(this).parent('tr').attr('data-selected') && '' != jQuery(this).parent('tr').attr('data-selected')) {
                        console.log('aqui'); 
                        jQuery(this).parent('tr').children('td').removeAttr('style');
                        jQuery(this).parent('tr').removeAttr('data-selected');
                    } else {
                        jQuery(this).parent('tr').attr('data-selected', true);
                        jQuery(this).parent('tr').children('td').each(function() {
                            jQuery(this).css({
                                'background-color': '#ffffcc'
                            });
                        });
                    }
                });

                jQuery('.add_sh').on('click', function() {
                    var temDiasSelecionados = false;
                    var temHora = false;

                    var segunda = jQuery('[name="segunda"]');
                    var terca = jQuery('[name="terca"]');
                    var quarta = jQuery('[name="quarta"]');
                    var quinta = jQuery('[name="quinta"]');
                    var sexta = jQuery('[name="sexta"]');
                    var sabado = jQuery('[name="sabado"]');
                    var domingo = jQuery('[name="domingo"]');
                    var todos = jQuery('[name="todos"]');
                    var interromperMusicaTocada = jQuery('[name="interromperMusicaTocada"]').is(':checked');

                    var hora = jQuery('[name="hora"]');

                    var date_inicial = new Date("2013-11-20T" + hora.val() + ":00.000Z");
                    if (date_inicial == "Invalid Date") {
                        bootbox.hideAll();
                        bootbox.dialog({
                            message: "Hora inválida",
                            title: "Importante!",
                            buttons: {}
                        });

                        setTimeout(function() {
                            bootbox.hideAll();
                        }, 2000);
                    } else {
                        if (hora.val() != "") {
                            temHora = true;
                        }

                        var tbl = jQuery('.tbl tbody');
                        var diasLabel = '';
                        var connector = '';
                        var inputsHiddens = '';
                        var i = 0;
                        tbl.children('tr').each(function() {
                            var ___tr = jQuery(this);
                            ___tr.children('td').each(function() {
                                var ___td = jQuery(this);
                                ___td.children('[type="hidden"]').each(function() {
                                    i++;
                                });
                            });
                        });

                        i = i / 2;
                        i = Math.floor(i.toFixed(1));
                        i = parseInt(jQuery('.hidden_input_sh').size()) / 3;


                        inputsHiddens += '<tr> ';
                        inputsHiddens += ' <td>' + (1 == interromperMusicaTocada || true == interromperMusicaTocada ? "Sim" : "Não") + '</td>';
                        inputsHiddens += ' <td>' + hora.val() + '</td>';
                        inputsHiddens += '<td> ';

                        if (segunda.is(':checked')) {
                            temDiasSelecionados = true;
                            inputsHiddens += connector + 'Segunda';
                            connector = ', ';

                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="segunda" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + ':00" />';
                            i++;
                        }



                        if (terca.is(':checked')) {
                            temDiasSelecionados = true;
                            inputsHiddens += connector + 'Terça';
                            connector = ', ';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="terca" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + ':00" />';
                            i++;
                        }

                        if (quarta.is(':checked')) {
                            temDiasSelecionados = true;
                            inputsHiddens += connector + 'Quarta';
                            connector = ', ';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="quarta" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + ':00" />';
                            i++;
                        }

                        if (quinta.is(':checked')) {
                            temDiasSelecionados = true;
                            inputsHiddens += connector + 'Quinta';
                            connector = ', ';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="quinta" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + ':00" />';
                            i++;
                        }

                        if (sexta.is(':checked')) {
                            temDiasSelecionados = true;
                            inputsHiddens += connector + 'Sexta';
                            connector = ', ';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="sexta" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + ':00" />';
                            i++;
                        }

                        if (sabado.is(':checked')) {
                            temDiasSelecionados = true;
                            inputsHiddens += connector + 'Sábado';
                            connector = ', ';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="sabado" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + ':00" />';
                            i++;
                        }

                        if (domingo.is(':checked')) {
                            temDiasSelecionados = true;
                            inputsHiddens += connector + 'Domingo';
                            connector = ', ';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="domingo" />';
                            inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + ':00" />';
                            i++;
                        }
                        inputsHiddens += '</td> ';
                        inputsHiddens += '</tr>';

                        if (temDiasSelecionados && temHora) {


                            if (tbl.children('tr').length < 10000000000000000) {
                                tbl.append(inputsHiddens);

                                if (segunda.is(':checked')) {
                                    segunda.trigger('click');
                                }

                                if (terca.is(':checked')) {
                                    terca.trigger('click');
                                }

                                if (quarta.is(':checked')) {
                                    quarta.trigger('click');
                                }

                                if (quinta.is(':checked')) {
                                    quinta.trigger('click');
                                }

                                if (sexta.is(':checked')) {
                                    sexta.trigger('click');
                                }

                                if (sabado.is(':checked')) {
                                    sabado.trigger('click');
                                }

                                if (domingo.is(':checked')) {
                                    domingo.trigger('click');
                                }

                                if (todos.is(':checked')) {
                                    todos.trigger('click');
                                }
                            } else {
                                bootbox.alert('Ops! Já está no limite de dias e horas!');
                            }

                            if (tbl.children('tr').length >= 1000000) {
                                var i = 1;
                                tbl.children('tr').each(function() {
                                    if (i > 24) {
                                        jQuery(this).remove();
                                    }
                                    i++;
                                });
                            }
                        }
                    }



                    return false;
                });

                // __intervalo

                jQuery('.add_sh_intervalo').on('click', function() {
                    var temDiasSelecionados = false;
                    var temHora = false;

                    var segunda = jQuery('[name="segunda"]');
                    var terca = jQuery('[name="terca"]');
                    var quarta = jQuery('[name="quarta"]');
                    var quinta = jQuery('[name="quinta"]');
                    var sexta = jQuery('[name="sexta"]');
                    var sabado = jQuery('[name="sabado"]');
                    var domingo = jQuery('[name="domingo"]');
                    var todos = jQuery('[name="todos"]');
                    var interromperMusicaTocada = jQuery('[name="interromperMusicaTocada"]').is(':checked');

                    var hora = jQuery('[name="hora"]');
                    var hora_final = jQuery('[name="hora_final"]');
                    var hora_intervalo = jQuery('[name="hora_intervalo"]');

                    var date_inicial = new Date("2013-11-20T" + hora.val() + ":00.000Z");
                    var date_final = new Date("2013-11-20T" + hora_final.val() + ":00.000Z");
                    var date_intervalo = new Date("2013-11-20T" + hora_intervalo.val() + ":00.000Z");

                    if (date_inicial == "Invalid Date" || date_final == "Invalid Date" || date_intervalo == "Invalid Date") {
                        bootbox.hideAll();
                        bootbox.dialog({
                            message: "Hora inválida",
                            title: "Importante!",
                            buttons: {}
                        });

                        setTimeout(function() {
                            bootbox.hideAll();
                        }, 2000);
                    } else {
                        var minutes = parseInt(date_intervalo.getUTCHours()) * 60;
                        minutes += parseInt(date_intervalo.getUTCMinutes());


                        var next = true;
                        while (next) {
                            var tdate_inicial = date_inicial.getTime();
                            var tdate_final = date_final.getTime();
                            date_inicial = new Date(tdate_inicial + minutes * 60000);

                            if (date_inicial.getTime() > tdate_final) {
                                next = false;
                                break;
                            } else {

                                var horas_strings = "";
                                if (date_inicial.getUTCHours().toString().length < 2) {
                                    horas_strings += "0" + date_inicial.getUTCHours().toString();
                                } else {
                                    horas_strings += date_inicial.getUTCHours().toString();
                                }

                                horas_strings += ":";

                                if (date_inicial.getUTCMinutes().toString().length < 2) {
                                    horas_strings += "0" + date_inicial.getUTCMinutes().toString();
                                } else {
                                    horas_strings += date_inicial.getUTCMinutes().toString();
                                }

                                horas_strings += "";

                                temHora = true;

                                var tbl = jQuery('.tbl tbody');
                                var diasLabel = '';
                                var connector = '';
                                var inputsHiddens = '';
                                var i = 0;
                                tbl.children('tr').each(function() {
                                    var ___tr = jQuery(this);
                                    ___tr.children('td').each(function() {
                                        var ___td = jQuery(this);
                                        ___td.children('[type="hidden"]').each(function() {
                                            i++;
                                        });
                                    });
                                });

                                i = i / 2;
                                i = parseInt(jQuery('.hidden_input_sh').size()) / 3;


                                inputsHiddens += '<tr> ';
                                inputsHiddens += ' <td>' + (1 == interromperMusicaTocada || true == interromperMusicaTocada ? "Sim" : "Não") + '</td>';
                                inputsHiddens += ' <td>' + horas_strings + '</td>';
                                inputsHiddens += '<td> ';

                                if (segunda.is(':checked')) {
                                    temDiasSelecionados = true;
                                    inputsHiddens += connector + 'Segunda';
                                    connector = ', ';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="segunda" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + horas_strings + ':00" />';
                                    i++;
                                }



                                if (terca.is(':checked')) {
                                    temDiasSelecionados = true;
                                    inputsHiddens += connector + 'Terça';
                                    connector = ', ';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="terca" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + horas_strings + ':00" />';
                                    i++;
                                }

                                if (quarta.is(':checked')) {
                                    temDiasSelecionados = true;
                                    inputsHiddens += connector + 'Quarta';
                                    connector = ', ';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="quarta" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + horas_strings + ':00" />';
                                    i++;
                                }

                                if (quinta.is(':checked')) {
                                    temDiasSelecionados = true;
                                    inputsHiddens += connector + 'Quinta';
                                    connector = ', ';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="quinta" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + horas_strings + ':00" />';
                                    i++;
                                }

                                if (sexta.is(':checked')) {
                                    temDiasSelecionados = true;
                                    inputsHiddens += connector + 'Sexta';
                                    connector = ', ';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="sexta" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + horas_strings + ':00" />';
                                    i++;
                                }

                                if (sabado.is(':checked')) {
                                    temDiasSelecionados = true;
                                    inputsHiddens += connector + 'Sábado';
                                    connector = ', ';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="sabado" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + horas_strings + ':00" />';
                                    i++;
                                }

                                if (domingo.is(':checked')) {
                                    temDiasSelecionados = true;
                                    inputsHiddens += connector + 'Domingo';
                                    connector = ', ';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].interromperMusicaTocada" value="' + interromperMusicaTocada + '" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].semana" value="domingo" />';
                                    inputsHiddens += '<input class="hidden_input_sh" type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + horas_strings + ':00" />';
                                    i++;
                                }
                                inputsHiddens += '</td> ';
                                inputsHiddens += '</tr>';

                                if (temDiasSelecionados && temHora) {


                                    if (tbl.children('tr').length < 10000000000000000) {
                                        tbl.append(inputsHiddens);

                                        if (segunda.is(':checked')) {
                                            segunda.trigger('click');
                                        }

                                        if (terca.is(':checked')) {
                                            terca.trigger('click');
                                        }

                                        if (quarta.is(':checked')) {
                                            quarta.trigger('click');
                                        }

                                        if (quinta.is(':checked')) {
                                            quinta.trigger('click');
                                        }

                                        if (sexta.is(':checked')) {
                                            sexta.trigger('click');
                                        }

                                        if (sabado.is(':checked')) {
                                            sabado.trigger('click');
                                        }

                                        if (domingo.is(':checked')) {
                                            domingo.trigger('click');
                                        }

                                        if (todos.is(':checked')) {
                                            todos.trigger('click');
                                        }
                                    } else {
                                        bootbox.alert('Ops! Já está no limite de dias e horas!');
                                    }

                                    if (tbl.children('tr').length >= 1000000) {
                                        var i = 1;
                                        tbl.children('tr').each(function() {
                                            if (i > 24) {
                                                jQuery(this).remove();
                                            }
                                            i++;
                                        });
                                    }
                                }
                            }
                        }
                    }

                    return false;
                });

                jQuery('.rm_sh').on('click', function() {
                    jQuery('[data-selected="true"]').remove();
                });

                jQuery('.rm_all_sh').on('click', function() {
                    jQuery('.tbl tbody tr').each(function() {
                        jQuery(this).attr("data-selected", "true");
                    });
                    jQuery('[data-selected="true"]').remove();
                });


                jQuery('[type="submit"]').on("click", function() {
                    bootbox.dialog({
                        message: "Aguarde...",
                        title: "Sistema processando informações",
                        buttons: {}
                    });

                    var $return = false;
                    var $preenchidos = true;

                    var idcliente = jQuery('[name="audiostoreComercialBean.cliente.idcliente"]').val();
                    var titulo = jQuery('[name="audiostoreComercialBean.titulo"]').val();
                    var periodoInicial = jQuery('[name="audiostoreComercialBean.periodoInicial"]').val();
                    var periodoFinal = jQuery('[name="audiostoreComercialBean.periodoFinal"]').val();
                    var nomeArquivo = jQuery('[name="arquivo"]');
                    // [0].files[0].name

                    if (null == idcliente || undefined == idcliente || '' == idcliente) {
                        bootbox.hideAll();
                        bootbox.dialog({
                            message: "Selecione um cliente!",
                            title: "",
                            buttons: {}
                        });

                        setTimeout(function() {
                            bootbox.hideAll();
                        }, 2000);
                        $preenchidos = false;
                    }

                    if (null == titulo || undefined == titulo || '' == titulo) {
                        bootbox.hideAll();
                        bootbox.dialog({
                            message: "Informe um título!",
                            title: "",
                            buttons: {}
                        });

                        setTimeout(function() {
                            bootbox.hideAll();
                        }, 2000);
                        $preenchidos = false;
                    }

                    if (null == nomeArquivo || undefined == nomeArquivo || '' == nomeArquivo) {
                        bootbox.hideAll();
                        bootbox.dialog({
                            message: "Informe um arquivo",
                            title: "",
                            buttons: {}
                        });

                        setTimeout(function() {
                            bootbox.hideAll();
                        }, 2000);
                        $preenchidos = false;
                    }


                    if (!(null == nomeArquivo || undefined == nomeArquivo || '' == nomeArquivo)) {
                        narq = nomeArquivo.val();
                        narq = narq.split("\\");
                        narq = narq[narq.length - 1];

                        if (narq.length > 28) {
                            bootbox.hideAll();
                            bootbox.dialog({
                                message: "O nome do arquivo não deve conter mais doque 28 caracteres!",
                                title: "",
                                buttons: {}
                            });

                            setTimeout(function() {
                                bootbox.hideAll();
                            }, 2000);
                            $preenchidos = false;
                        }
                    }

                    if (null == periodoInicial || undefined == periodoInicial || '' == periodoInicial) {
                        bootbox.hideAll();
                        bootbox.dialog({
                            message: "Informe um periodo inicial",
                            title: "",
                            buttons: {}
                        });

                        setTimeout(function() {
                            bootbox.hideAll();
                        }, 2000);
                        $preenchidos = false;
                    }

                    if (null == periodoFinal || undefined == periodoFinal || '' == periodoFinal) {
                        bootbox.hideAll();
                        bootbox.dialog({
                            message: "Informe um periodo final",
                            title: "",
                            buttons: {}
                        });

                        setTimeout(function() {
                            bootbox.hideAll();
                        }, 2000);
                        $preenchidos = false;
                    }

                    if ($preenchidos == true) {
                        jQuery.ajax({
                            async: false,
                            type: 'GET',
                            url: '${url}/audiostore-comercial/cadastrar/validador',
                            data: {
                                titulo: titulo,
                                periodoInicial: periodoInicial,
                                periodoFinal: periodoFinal,
                                nomeArquivo: nomeArquivo[0].files[0].name,
                                idcliente: idcliente
                            },
                            success: function(json) {
                                bootbox.hideAll();
                                if (!json.success) {
                                    bootbox.dialog({
                                        message: json.response,
                                        title: "Sistema processando informações",
                                        buttons: {}
                                    });

                                    setTimeout(function() {
                                        bootbox.hideAll();
                                    }, 2000);
                                } else {
                                    $return = true;
                                }
                            }
                        });
                    }


                    return $return;
                });
            });
        </script>

    </jsp:body>
</instore:template>