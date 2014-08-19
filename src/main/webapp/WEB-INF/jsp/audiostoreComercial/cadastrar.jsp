<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-comercial" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Comerciais </a>
    </jsp:attribute>

    <jsp:body>
        <script type="text/javascript">
            jQuery(document).ready(function() {
                jQuery('.arquivosDeMusica').on('change', function() {
                    var valor = jQuery(this).val();
                    valor = valor.split('[|||SEPARADOR|||]');

                    jQuery('[name="audiostoreComercialBean.titulo"]').val(valor[0]);
                    jQuery('[name="audiostoreComercialBean.arquivo"]').val(valor[1]);
                });
            });
        </script>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-comercial">
            <input type="hidden" name="audiostoreComercialBean.id" value="${audiostoreComercialBean.id}" />

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Titulo</label>
                        <input type="text" name="audiostoreComercialBean.titulo" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreComercialBean.titulo}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Arquivo</label>
                        <input type="text" name="audiostoreComercialBean.arquivo" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreComercialBean.arquivo}">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Importar</label>
                        <br />
                        <select data-selectradio="true" data-drop-right="true" class="form-control arquivosDeMusica"  data-rule-required="true" style="margin-left: -30px;">
                            <c:forEach items="${arquivoMusicaList}" var="musica">
                                <option value="${musica.nome}[|||SEPARADOR|||]${musica.caminho}" >${musica.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Categoria</label> 
                        <br />
                        <select data-selectradio="true"  class="form-control" name="audiostoreComercialBean.audiostoreCategoria.codigo" data-rule-required="true" >
                            <c:forEach items="${categoriaBeanList}" var="cat">
                                <option value="${cat.codigo}" ${audiostoreComercialBean.audiostoreCategoria.codigo eq cat.codigo ? 'selected="selected"':''}>${cat.categoria}</option> 
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Tipo interprete</label> 
                        <br />
                        <select data-selectradio="true"  class="form-control" name="audiostoreComercialBean.tipoInterprete" data-rule-required="true" >
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
                <div class="col-md-4"></div>
                <div class="col-md-12">
                    <br />
                    <b> Dependencias </b>
                    <hr />
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Primária</label>
                        <br />
                        <select name="audiostoreComercialBean.dependencia1" data-selectradio="true" data-drop-right="false" class="form-control"  data-rule-required="true" style="margin-left: -30px;">
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
                        <select name="audiostoreComercialBean.dependencia2" data-selectradio="true" data-drop-right="true" class="form-control"  data-rule-required="true" style="margin-left: -30px;">
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
                        <select name="audiostoreComercialBean.dependencia3" data-selectradio="true" data-drop-right="true" class="form-control"  data-rule-required="true" style="margin-left: -30px;">
                            <option>Nenhuma</option>
                            <c:forEach items="${arquivoMusicaList}" var="musica">
                                <option value="${musica.caminho}" ${musica.caminho eq audiostoreComercialBean.dependencia3 ? 'selected="selected"' : ''}>${musica.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <hr />

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Tempo</label>
                        <input type="text" name="tempoTotal" class="form-control" placeholder="Nome"  
                               data-mask="99:99:99"  
                               data-rule-required="true" 
                               value="${audiostoreComercialBean.tempoTotal}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Quantidade </label>

                        <input type="text" name="audiostoreComercialBean.qtde" class="form-control span2" placeholder="Quantidade máxima de execuções no dia)"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreComercialBean.qtde}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Ultima  execução</label>
                        <input type="text" name="audiostoreComercialBean.ultimaExecucao" class="form-control datepicker" placeholder="Nome"  
                               data-mask="99/99/9999"
                               data-rule-maxlength="30" value="${audiostoreComercialBean.ultimaExecucao}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Frame inicial</label>
                        <input type="text" name="audiostoreComercialBean.frameInicio" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreComercialBean.frameInicio}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Frame final</label>
                        <input type="text" name="audiostoreComercialBean.frameFinal" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreComercialBean.frameFinal}">
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de vencimento</label>
                        <input type="text" name="audiostoreComercialBean.dataVencimento" class="form-control datepicker" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="99/99/9999" value="${cf:dateFormat(audiostoreComercialBean.dataVencimento, "dd/MM/yyyy")}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Periodo Inicial</label>
                        <input type="text" name="audiostoreComercialBean.periodoInicial" class="form-control datepicker" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="99/99/9999" value="${cf:dateFormat(audiostoreComercialBean.periodoInicial, "dd/MM/yyyy")}">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Periodo Final</label>
                        <input type="text" name="audiostoreComercialBean.periodoFinal" class="form-control datepicker" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="99/99/9999" value="${cf:dateFormat(audiostoreComercialBean.periodoFinal, "dd/MM/yyyy")}">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Tocar em dias alternados</label>
                        <br />
                        <select name="audiostoreComercialBean.diasAlternados" data-selectradio="true" data-drop-right="true" class="form-control"  data-rule-required="true" style="margin-left: -30px;">
                            <option value="${true}">Sim</option>
                            <option value="${false}">Não</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-12">
                    <br />
                    <b> Horários pré definidos </b>
                    <hr />
                    <div class="row">
                        <div class="col-md-6">
                            <div class="table-responsive">
                                <table class="tbl table table-striped table-bordered table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <td style="background-color: #606060; color: #FFF; font-weight: bold;">Horário</td>
                                            <td style="background-color: #606060; color: #FFF; font-weight: bold;">Semana</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${shs}" var="sh" varStatus="vs">
                                            <tr> 
                                                <td>${sh.horario}</td> 
                                                <td>
                                                    ${sh.semana}
                                                    <input type="hidden" name="sh[${vs.index}].semana" value="${sh.semana}" />
                                                    <input type="hidden" name="sh[${vs.index}].horario" value="${sh.horario}" />
                                                </td> 
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div style="float: left;">
                                <input type="checkbox" name="segunda" value="1" />&nbsp;Segunda
                                <br />
                                <input type="checkbox" name="terca" value="2"/>&nbsp;Terça
                                <br />
                                <input type="checkbox" name="quarta" value="3"/>&nbsp;Quarta
                                <br />
                                <input type="checkbox" name="quinta" value="4"/>&nbsp;Quinta
                                <br />
                                <input type="checkbox" name="sexta" value="5"/>&nbsp;Sexta
                                <br />
                                <input type="checkbox" name="sabado" value="6"/>&nbsp;Sábado
                                <br />
                                <input type="checkbox" name="domingo" value="7"/>&nbsp;Domingo&nbsp;&nbsp;
                                <input type="checkbox" name="todos" value="0"/>&nbsp;Todos
                            </div>
                            <div class="col-xs-3">
                                <input type="text" name="hora" class="form-control span5" placeholder="Nome"  
                                       data-mask="99:99:99"
                                       value="${audiostoreComercialBean.arquivo}">
                            </div>

                            <button type="button" class="btn btn-default add_sh" data-tooltip="true" title="Para adicionar é necessário seleionar um ou mais dias da semana e informa um horário!">
                                <i class="fa fa-plus"></i>
                            </button>
                            <button type="button" class="btn btn-default rm_sh" data-tooltip="true" title="Para remover um selecione um registro da tabela!">
                                <i class="fa fa-trash-o"></i>
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
            .tbl tr { cursor:  pointer; }
        </style>

        <script>
            jQuery(document).ready(function() {
                jQuery('[name="todos"]').on('click', function() {
                    var segunda = jQuery('[name="segunda"]');
                    var terca = jQuery('[name="terca"]');
                    var quarta = jQuery('[name="quarta"]');
                    var quinta = jQuery('[name="quinta"]');
                    var sexta = jQuery('[name="sexta"]');
                    var sabado = jQuery('[name="sabado"]');
                    var domingo = jQuery('[name="domingo"]');
                    var todos = jQuery('[name="todos"]');

                    if (jQuery(this).is(':checked')) {
                        if (!segunda.is(':checked')) {
                            segunda.trigger('click');
                        }

                        if (!terca.is(':checked')) {
                            terca.trigger('click');
                        }

                        if (!quarta.is(':checked')) {
                            quarta.trigger('click');
                        }

                        if (!quinta.is(':checked')) {
                            quinta.trigger('click');
                        }

                        if (!sexta.is(':checked')) {
                            sexta.trigger('click');
                        }

                        if (!sabado.is(':checked')) {
                            sabado.trigger('click');
                        }

                        if (!domingo.is(':checked')) {
                            domingo.trigger('click');
                        }

                        if (!todos.is(':checked')) {
                            todos.trigger('click');
                        }
                    } else {
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
                    }
                });

                jQuery(document).on('click', '.tbl tbody tr td', function(event) {
                    if (!event.ctrlKey) {
                        jQuery('.tbl tbody tr td').removeAttr('style');
                        jQuery('.tbl tbody tr').removeAttr('data-selected');
                    }

                    jQuery(this).parent('tr').attr('data-selected', true);
                    jQuery(this).parent('tr').children('td').each(function() {
                        jQuery(this).css({
                            'background-color': '#ffffcc'
                        });
                    });
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

                    var hora = jQuery('[name="hora"]');
                    if (hora.val() != "") {
                        temHora = true;
                    }
                    
                    var tbl = jQuery('.tbl tbody');
                    var diasLabel = '';
                    var connector = '';
                    var inputsHiddens = '';
                    var i = tbl.children('tr').length+1;


                    if (segunda.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel = 'Segunda';
                        connector = ', ';
                        inputsHiddens += '<tr> ';
                        inputsHiddens += ' <td>' + hora.val() + '</td>';
                        inputsHiddens += ' <td>';
                        inputsHiddens += diasLabel;
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].semana" value="' + diasLabel + '" />';
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + '" />';
                        inputsHiddens += '</td>';
                        inputsHiddens += '</tr>';
                        i++;
                    }

                    if (terca.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel = 'Terça';
                        connector = ', ';
                        inputsHiddens += '<tr> ';
                        inputsHiddens += ' <td>' + hora.val() + '</td>';
                        inputsHiddens += ' <td>';
                        inputsHiddens += diasLabel;
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].semana" value="' + diasLabel + '" />';
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + '" />';
                        inputsHiddens += '</td>';
                        inputsHiddens += '</tr>';
                        i++;
                    }

                    if (quarta.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel = 'Quarta';
                        connector = ', ';
                        inputsHiddens += '<tr> ';
                        inputsHiddens += ' <td>' + hora.val() + '</td>';
                        inputsHiddens += ' <td>';
                        inputsHiddens += diasLabel;
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].semana" value="' + diasLabel + '" />';
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + '" />';
                        inputsHiddens += '</td>';
                        inputsHiddens += '</tr>';
                        i++;
                    }

                    if (quinta.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel = 'Quinta';
                        connector = ', ';
                        inputsHiddens += '<tr> ';
                        inputsHiddens += ' <td>' + hora.val() + '</td>';
                        inputsHiddens += ' <td>';
                        inputsHiddens += diasLabel;
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].semana" value="' + diasLabel + '" />';
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + '" />';
                        inputsHiddens += '</td>';
                        inputsHiddens += '</tr>';
                        i++;
                    }

                    if (sexta.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel = 'Sexta';
                        connector = ', ';
                        inputsHiddens += '<tr> ';
                        inputsHiddens += ' <td>' + hora.val() + '</td>';
                        inputsHiddens += ' <td>';
                        inputsHiddens += diasLabel;
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].semana" value="' + diasLabel + '" />';
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + '" />';
                        inputsHiddens += '</td>';
                        inputsHiddens += '</tr>';
                        i++;
                    }

                    if (sabado.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel = 'Sábado';
                        connector = ', ';
                        inputsHiddens += '<tr> ';
                        inputsHiddens += ' <td>' + hora.val() + '</td>';
                        inputsHiddens += ' <td>';
                        inputsHiddens += diasLabel;
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].semana" value="' + diasLabel + '" />';
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + '" />';
                        inputsHiddens += '</td>';
                        inputsHiddens += '</tr>';
                        i++;
                    }

                    if (domingo.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel = 'Domingo';
                        connector = ', ';
                        inputsHiddens += '<tr> ';
                        inputsHiddens += ' <td>' + hora.val() + '</td>';
                        inputsHiddens += ' <td>';
                        inputsHiddens += diasLabel;
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].semana" value="' + diasLabel + '" />';
                        inputsHiddens += '<input type="hidden" name="sh[' + i + '].horario" value="${cf:dateCurrent("dd/MM/yyyy")} ' + hora.val() + '" />';
                        inputsHiddens += '</td>';
                        inputsHiddens += '</tr>';
                        i++;
                    }

                    if (temDiasSelecionados && temHora) {
                        

                        if (tbl.children('tr').length < 24) {
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
                        
                        if (tbl.children('tr').length >= 24) {
                            var i = 1;
                            tbl.children('tr').each(function(){
                                if(i > 24) {
                                    jQuery(this).remove();
                                }
                                i++;
                            });
                            
                            
                        }
                    }
                    return false;
                });
                jQuery('.rm_sh').on('click', function() {
                    jQuery('[data-selected="true"]').remove();
                });
            });
        </script>

    </jsp:body>
</instore:template>