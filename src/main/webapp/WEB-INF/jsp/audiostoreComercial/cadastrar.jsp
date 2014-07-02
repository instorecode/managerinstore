<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-musica" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Musicas </a>
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
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-musica">
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
                        <select data-selectradio="true"  class="form-control" name="audiostoreComercialBean.categoria1.codigo" data-rule-required="true" >
                            <c:forEach items="${categoriaBeanList}" var="cat">
                                <option value="${cat.codigo}" ${audiostoreComercialBean.categoria1.codigo eq cat.codigo ? 'selected="selected"':''}>${cat.categoria}</option> 
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
                        <label>Prim�ria</label>
                        <br />
                        <select name="audiostoreComercialBean.dependencia1" data-selectradio="true" data-drop-right="false" class="form-control"  data-rule-required="true" style="margin-left: -30px;">
                            <option>Nenhuma</option>
                            <c:forEach items="${arquivoMusicaList}" var="musica">
                                <option value="${musica.caminho}" ${musica.caminho eq audiostoreComercialBean.dependencia1 ? 'selected="selected"' : ''}>${musica.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Secund�ria</label>
                        <br />
                        <select name="audiostoreComercialBean.dependencia2" data-selectradio="true" data-drop-right="true" class="form-control"  data-rule-required="true" style="margin-left: -30px;">
                            <option>Nenhuma</option>
                            <c:forEach items="${arquivoMusicaList}" var="musica">
                                <option value="${musica.caminho}" ${musica.caminho eq audiostoreComercialBean.dependencia2 ? 'selected="selected"' : ''}>${musica.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Terci�ria</label>
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

                        <input type="text" name="audiostoreComercialBean.qtde" class="form-control span2" placeholder="Quantidade m�xima de execu��es no dia)"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreComercialBean.qtde}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Ultima  execu��o</label>
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
                            <option value="${false}">N�o</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-12">
                    <br />
                    <b> Hor�rios pr� definidos </b>
                    <hr />
                    <div class="row">
                        <div class="col-md-6">
                            <div class="table-responsive">
                                <table class="tbl table table-striped table-bordered table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <td>Hor�rio</td>
                                            <td>Semana</td>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div style="float: left;">
                                <input type="checkbox" name="segunda" value="1" />&nbsp;Segunda
                                <br />
                                <input type="checkbox" name="terca" value="2"/>&nbsp;Ter�a
                                <br />
                                <input type="checkbox" name="quarta" value="3"/>&nbsp;Quarta
                                <br />
                                <input type="checkbox" name="quinta" value="4"/>&nbsp;Quinta
                                <br />
                                <input type="checkbox" name="sexta" value="5"/>&nbsp;Sexta
                                <br />
                                <input type="checkbox" name="sabado" value="6"/>&nbsp;S�bado
                                <br />
                                <input type="checkbox" name="domingo" value="7"/>&nbsp;Domingo&nbsp;&nbsp;
                                <input type="checkbox" name="todos" value="0"/>&nbsp;Todos
                            </div>
                            <div class="col-xs-3">
                                <input type="text" name="hora" class="form-control span5" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="8"
                                       data-rule-maxlength="8"
                                       data-mask="99:99:99"
                                       value="${audiostoreComercialBean.arquivo}">
                            </div>

                            <button type="button" class="btn btn-default add_sh" data-tooltip="true" title="Para adicionar � necess�rio seleionar um ou mais dias da semana e informa um hor�rio!">
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

                jQuery(document).on('click', '.tbl tr td', function(event) {
                    if (!event.ctrlKey) {
                        jQuery('.tbl tr td').removeAttr('style');
                        jQuery('.tbl tr').removeAttr('data-selected');
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

                    var diasLabel = '';
                    var connector = '';
                    var inputsHiddens = '';


                    if (segunda.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel += connector + 'Segunda';
                        connector = ', ';
                        inputsHiddens += '<input type="hidden" name="sh.semana" value="Segunda" />';
                        inputsHiddens += '<input type="hidden" name="sh.hora" value="' + hora.val() + '" />';
                    }

                    if (terca.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel += connector + 'Ter�a';
                        connector = ', ';
                        inputsHiddens += '<input type="hidden" name="sh.semana" value="Ter�a" />';
                        inputsHiddens += '<input type="hidden" name="sh.hora" value="' + hora.val() + '" />';
                    }

                    if (quarta.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel += connector + 'Quarta';
                        connector = ', ';
                        inputsHiddens += '<input type="hidden" name="sh.semana" value="Quarta" />';
                        inputsHiddens += '<input type="hidden" name="sh.hora" value="' + hora.val() + '" />';
                    }

                    if (quinta.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel += connector + 'Quinta';
                        connector = ', ';
                        inputsHiddens += '<input type="hidden" name="sh.semana" value="Quinta" />';
                        inputsHiddens += '<input type="hidden" name="sh.hora" value="' + hora.val() + '" />';
                    }

                    if (sexta.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel += connector + 'Sexta';
                        connector = ', ';
                        inputsHiddens += '<input type="hidden" name="sh.semana" value="Sexta" />';
                        inputsHiddens += '<input type="hidden" name="sh.hora" value="' + hora.val() + '" />';
                    }

                    if (sabado.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel += connector + 'S�bado';
                        connector = ', ';
                        inputsHiddens += '<input type="hidden" name="sh.semana" value="Sabado" />';
                        inputsHiddens += '<input type="hidden" name="sh.hora" value="' + hora.val() + '" />';
                    }

                    if (domingo.is(':checked')) {
                        temDiasSelecionados = true;
                        diasLabel += connector + 'Domingo';
                        connector = ', ';
                        inputsHiddens += '<input type="hidden" name="sh.semana" value="Domingo" />';
                        inputsHiddens += '<input type="hidden" name="sh.hora" value="' + hora.val() + '" />';
                    }

                    if (temDiasSelecionados && temHora) {
                        var tbl = jQuery('.tbl tbody');
                        tbl.append('<tr> <td>' + hora.val() + '</td> <td>' + diasLabel + '</td> </tr>' + inputsHiddens);

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
                    return false;
                });
                jQuery('.rm_sh').on('click', function() {
                    jQuery('[data-selected="true"]').remove();
                });
            });
        </script>

    </jsp:body>
</instore:template>