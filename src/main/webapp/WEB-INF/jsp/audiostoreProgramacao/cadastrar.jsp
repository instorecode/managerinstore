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
            <c:if test="${isPageCadastro eq false}">
                <input type="hidden" name="audiostoreProgramacaoBean.id" value="${audiostoreProgramacaoBean.id}" />
            </c:if>
            

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
                        <label>Cliente </label>
                        <select class="select2 select_cliente" name="audiostoreProgramacaoBean.cliente.idcliente" data-rule-required="true" >
                            <option value>Selecione um cliente</option>
                            <c:forEach items="${clienteBeanList}" var="cliente">
                                <option value="${cliente.idcliente}" ${cliente.idcliente eq audiostoreProgramacaoBean.cliente.idcliente ? 'selected="selected"' : ''}>Cliente: ${cliente.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de inicio</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreProgramacaoBean.dataInicio" class="form-control datepicker" placeholder="Data de inicio"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" 
                                   data-mask="99/99/9999"
                                   value="${cf:dateFormat(audiostoreProgramacaoBean.dataInicio , "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>

                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de termino</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <c:if test="${isPageCadastro}">
                                <input type="text" name="audiostoreProgramacaoBean.dataFinal" class="form-control datepicker" placeholder="Data de termino"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" 
                                       data-mask="99/99/9999"
                                       value="31/12/2050">
                            </c:if>
                            <c:if test="${not isPageCadastro}">
                                <input type="text" name="audiostoreProgramacaoBean.dataFinal" class="form-control datepicker" placeholder="Data de termino"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" 
                                       data-mask="99/99/9999"
                                       value="${cf:dateFormat(audiostoreProgramacaoBean.dataFinal, "dd/MM/yyyy")}">
                            </c:if>

                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Horário inicial</label>
                        <input type="text" name="horaInicio" class="form-control" placeholder="Tempo de duração"  
                               data-rule-required="true" 
                               data-rule-minlength="8"
                               data-rule-maxlength="8" 
                               data-mask="99:99:99"
                               data-rule-proghora1="true"
                               data-rule-proghora2="true"
                               value="${audiostoreProgramacaoBean.horaInicio ne null ? audiostoreProgramacaoBean.horaInicio  : '00:00:00'}">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>Horário final</label>
                        <input type="text" name="horaFinal" class="form-control" placeholder="Tempo de duração"  
                               data-rule-required="true" 
                               data-rule-minlength="8"
                               data-rule-maxlength="8" 
                               data-mask="99:99:99"
                               data-rule-proghora1="true"
                               data-rule-proghora2="true"
                               value="${audiostoreProgramacaoBean.horaFinal ne null ? audiostoreProgramacaoBean.horaFinal : '23:59:59'}">
                    </div>
                </div> 

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Conteudo</label>
                        <input type="text" name="audiostoreProgramacaoBean.conteudo" class="form-control" placeholder="Conteudo"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="70" 
                               value="${audiostoreProgramacaoBean.conteudo}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Sensor/monitor</label>

                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreProgramacaoBean.loopback" id="optionsRadios1" value="${true}" ${audiostoreProgramacaoBean.loopback eq true ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreProgramacaoBean.loopback" id="optionsRadios1" value="${false}"  ${audiostoreProgramacaoBean.loopback eq false or audiostoreProgramacaoBean.loopback eq null ? 'checked="checked"' : ''}>&nbsp;Não </label>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Dias da semana</label>
                        <select class="select2" multiple="multiple" name="diasSemana" data-rule-required="true" >
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
                <div class="col-md-12">
                    <div class="form-group">
                        <label></label>
                        <button type="button" class="btn btn-default btn_sel_all_dia_semana"> 
                            Selecionar todos os dias da semana
                        </button>
                    </div>
                </div>




                <div class="col-md-12">
                    <style>
                        .lista_filial {
                            height: 300px;
                            overflow: auto;
                        }

                        .lista_filial::-webkit-scrollbar {
                            width: 5px;
                        }

                        .lista_filial::-webkit-scrollbar-track {
                            background-color: transparent;
                        }

                        .lista_filial::-webkit-scrollbar-thumb {
                            background-color: #909090;
                        }

                        .lista_filial li { cursor: pointer; }
                    </style>

                    <script type="text/javascript">
                        jQuery(document).ready(function() {
                            
                            if (null != jQuery('.select_cliente').children('option:selected').val()
                                    && undefined != jQuery('.select_cliente').children('option:selected').val()
                                    && '' != jQuery('.select_cliente').children('option:selected').val()) {

                                var novo_id = jQuery('.select_cliente').children('option:selected').val();
                                jQuery.ajax({
                                    url: '${url}/audiostore-programacao/categorias/' + novo_id,
                                    type: 'GET',
                                    success: function(json) {

                                        if (json.length == 0) {
                                            jQuery('.cat_container').hide();
                                            jQuery('.btn_salvar').hide();
                                        } else {
                                            var html = '';
                                            for (i in json) {
                                                var item = json[i];
                                                var tipo = '';

                                                if (item.tipo == 1) {
                                                    tipo = 'música';
                                                }

                                                if (item.tipo == 2) {
                                                    tipo = 'comercial';
                                                }

                                                if (item.tipo == 3) {
                                                    tipo = 'video';
                                                }
                                                html += '<li id="' + i + '" data-index="' + i + '"> <i class="fa fa-cubes"></i> <div class="text">' + item.categoria + '</div> <small> <b style="color: green;"> ' + item.cliente.nome + ' -  ' + tipo + '</b> </small> <input type="hidden" name="categorias" value="' + item.codigo + '" disabled="disabled"/> </li>';

                                            }
                                            jQuery('.lista_filial2 li').each(function() {
                                                if (jQuery(this).data('cliente') != novo_id) {
                                                    jQuery(this).remove();
                                                }
                                            });
                                            jQuery('.lista_filial1').html('');
                                            jQuery('.lista_filial1').html(html);
                                            jQuery('.cat_container').show();
                                            jQuery('.btn_salvar').show();
                                        }
                                    },
                                    error: function(err) {
                                        console.log(err);
                                    }
                                });

                            }
                            jQuery('.select_cliente').on('change', function() {

                                var novo_id = jQuery(this).val();
                                jQuery.ajax({
                                    url: '${url}/audiostore-programacao/categorias/' + jQuery(this).val(),
                                    type: 'GET',
                                    success: function(json) {

                                        if (json.length == 0) {
                                            jQuery('.cat_container').hide();
                                            jQuery('.btn_salvar').hide();
                                        } else {
                                            var html = '';
                                            for (i in json) {
                                                var item = json[i];
                                                var tipo = '';

                                                if (item.tipo == 1) {
                                                    tipo = 'música';
                                                }

                                                if (item.tipo == 2) {
                                                    tipo = 'comercial';
                                                }

                                                if (item.tipo == 3) {
                                                    tipo = 'video';
                                                }
                                                html += '<li id="' + i + '" data-index="' + i + '"> <i class="fa fa-cubes"></i> <div class="text">' + item.categoria + '</div> <small> <b style="color: green;"> ' + item.cliente.nome + ' -  ' + tipo + '</b> </small> <input type="hidden" name="categorias" value="' + item.codigo + '" disabled="disabled"/> </li>';

                                            }
                                            jQuery('.lista_filial2 li').each(function() {
                                                if (jQuery(this).data('cliente') != novo_id) {
                                                    jQuery(this).remove();
                                                }
                                            });
                                            jQuery('.lista_filial1').html('');
                                            jQuery('.lista_filial1').html(html);
                                            jQuery('.cat_container').show();
                                            jQuery('.btn_salvar').show();
                                        }
                                    },
                                    error: function(err) {
                                        console.log(err);
                                    }
                                });
                            });

                            jQuery('.btn_sel_all_dia_semana').on('click', function() {
                                jQuery('[name="diasSemana"]').children('option').each(function() {
                                    jQuery(this).attr('selected', true);
                                });
                                jQuery('[name="diasSemana"]').change();
                            });

                            $(".lista_filial2").sortable();
                            $(".lista_filial2").disableSelection();

                            var prim_index = null;
                            jQuery(document).on('click', '.lista_filial1 li', function(event) {
                                if (event.ctrlKey) {
                                    if (null != prim_index) {
                                        var clones = new Array();
                                        var i2 = 0;
                                        var fim_index = jQuery(this).data("index");

                                        if (prim_index > fim_index) {
                                            var aux = prim_index;
                                            prim_index = fim_index;
                                            fim_index = aux;
                                        }

                                        for (i = prim_index; i <= fim_index; i++) {
                                            var self = jQuery('.lista_filial1 li[data-index="' + i + '"]');
                                            self.css({
                                                'background-color': '#f4f4f4'
                                            });

                                            clones[i2] = self;
                                            i2++;
                                        }

//                                        jQuery('.contador_l1').text("Total " + (jQuery('.lista_filial1 li').size() - clones.length));
//                                        jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size() + clones.length));

                                        for (i = 0; i <= clones.length; i++) {
                                            var self = clones[i];
                                            addLista1(self);
                                        }

                                        prim_index = null;
                                    } else {
                                        prim_index = jQuery(this).data("index");
                                        jQuery(this).css({
                                            'background-color': '#f4f4f4'
                                        });
                                    }
                                } else {
                                    addLista1(jQuery(this));
//                                    jQuery('.contador_l1').text("Total " + (jQuery('.lista_filial1 li').size() - 1));
//                                    jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size() + 1));

                                }
                            });

                            jQuery(document).on('click', '.lista_filial2 li', function(event) {
                                if (event.ctrlKey) {
                                    if (null != prim_index) {
                                        var clones = new Array();
                                        var i2 = 0;

                                        var fim_index = jQuery(this).data("index");

                                        if (prim_index > fim_index) {
                                            var aux = prim_index;
                                            prim_index = fim_index;
                                            fim_index = aux;
                                        }

                                        for (i = prim_index; i <= fim_index; i++) {
                                            var self = jQuery('.lista_filial2 li[data-index="' + i + '"]');
                                            self.css({
                                                'background-color': '#f4f4f4'
                                            });

                                            clones[i2] = self;
                                            i2++;
                                        }

//                                        jQuery('.contador_l1').text("Total " + (jQuery('.lista_filial1 li').size() + clones.length));
//                                        jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size() - clones.length));

                                        for (i = 0; i <= clones.length; i++) {
                                            var self = clones[i];
                                            addLista2(self);
                                        }

                                        prim_index = null;
                                    } else {
                                        prim_index = jQuery(this).data("index");
                                        jQuery(this).css({
                                            'background-color': '#f4f4f4'
                                        });
                                    }
                                } else {
//                                    jQuery('.contador_l1').text("Total " + (jQuery('.lista_filial1 li').size() + 1));
//                                    jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size() - 1));
                                    addLista2(jQuery(this));
                                }
                            });

                            function addLista1(self) {
                                if (null != self && undefined != self) {
                                    var id = self.attr("id");
                                    var clone = self.clone();

                                    clone.children('input[type="hidden"]').attr("disabled", false);
                                    clone.css({
                                        'background-color': '#FFF'
                                    });

                                    jQuery('.lista_filial2').append(clone);
                                    clone.fadeIn('500');
                                    clone.on('mouseover', function() {
                                        jQuery(this).css({
                                            'background-color': '#f4f4f4'
                                        });
                                    }).on('mouseout', function() {
                                        jQuery(this).css({
                                            'background-color': '#fff'
                                        });
                                    });
                                }

                                jQuery('.lista_filial2 li').each(function() {
                                    jQuery(this).show();
                                });
                                jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size()));
                            }

                            function addLista2(self) {

                                if (null != self && undefined != self) {
                                    self.fadeOut('500', function() {
                                        var id = jQuery(this).attr("id");
                                        jQuery(this).remove();
                                    });
                                }
                                jQuery('.lista_filial1 li').each(function() {
                                    jQuery(this).show();
                                });
                                jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size() - 1));
                            }

                            jQuery('.lista1_pesq').on('keyup', function() {
                                var value = jQuery(this).val();
                                if (value != '') {
                                    jQuery(".lista_filial1 li").hide();
                                    jQuery(".lista_filial1 li div.text:like('%" + value + "%')").each(function() {
                                        jQuery(this).parent('li').show();
                                    });
//                                    jQuery('.contador_l1').text("Total " + jQuery('.lista_filial1 li:visible').size());
                                }
                            });

                            jQuery('.lista2_pesq').on('keyup', function() {
                                var value = jQuery(this).val();
                                jQuery(".lista_filial2 li").hide();
                                jQuery(".lista_filial2 li div.text:like('%" + value + "%')").each(function() {
                                    jQuery(this).parent('li').show();
                                });
//                                jQuery('.contador_l2').text("Total " + jQuery('.lista_filial2 li:visible').size());
                            });

//                            jQuery('.contador_l1').text("Total " + jQuery('.lista_filial1 li').size());
                            jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size()));

                            jQuery('.btn_all_l1').on('click', function() {
                                jQuery('.lista_filial1 li:visible').each(function() {
                                    addLista1(jQuery(this));
                                });
                            });

                            jQuery('.btn_all_l2').on('click', function() {
                                jQuery('.lista_filial2 li:visible').each(function() {
                                    addLista2(jQuery(this));
                                });

                                jQuery('.contador_l2').text("Total 0");
                            });
                        });
                    </script>

                    <c:if test="${not isPageCadastro}">
                        <script type="text/javascript">
                            jQuery(document).ready(function() {
                                jQuery('.select_cliente').change();
                            });
                        </script>
                    </c:if>

                    <div class="row cat_container" style="display: none">
                        <div class="col-sm-6">
                            <div class="block">
                                <div class="header">
                                    <h2> 
                                        <b>Categorías</b> 
                                        <span class="pull-right contador_l1"> </span>
                                    </h2>
                                    <input type="text" class="form-control lista1_pesq" placeholder="Filtro de Pesquisar">

                                </div>
                                <div class="content no-padding ">
                                    <ul class="items lista_filial lista_filial1">
                                        <c:forEach items="${categoriaBeanList}" var="categoria" varStatus="vs">
                                            <li id="${vs.index}" data-index="${vs.index}">
                                                <i class="fa fa-cubes"></i>
                                                <div class="text">${categoria.categoria}</div>
                                                <small>
                                                    <b style="color: green;">
                                                        ${categoria.cliente.nome} - 
                                                        <c:if test="${categoria.tipo eq 1}">
                                                            Música
                                                        </c:if>
                                                        <c:if test="${categoria.tipo eq 2}">
                                                            Comercial
                                                        </c:if>
                                                        <c:if test="${categoria.tipo eq 2}">
                                                            Video
                                                        </c:if>
                                                    </b>
                                                </small>

                                                <input type="hidden" name="categorias" value="${categoria.codigo}" disabled="disabled"/>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <div style="clear: both"></div>
                                    <button type="button" class="btn btn-default pull-right btn_all_l1" style="margin-top: 10px; margin-right: 10px;">
                                        Adicionar todos os itens
                                    </button>
                                    <div style="clear: both"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="block">
                                <div class="header">
                                    <h2> 
                                        <b>Categorías inclusas</b> 
                                        <span class="pull-right contador_l2"> </span>
                                    </h2>
                                    <input type="text" class="form-control lista2_pesq" placeholder="Filtro de Pesquisar">
                                </div>
                                <div class="content no-padding ">
                                    <ul class="items lista_filial lista_filial2">
                                        <c:forEach items="${programacaoCategoriaBeanList}" var="pcb" varStatus="vs">
                                            <li id="${vs.index}" data-index="${vs.index}" data-cliente="${pcb.audiostoreCategoria.cliente.idcliente}">
                                                <i class="fa fa-cubes"></i>
                                                <div class="text">${pcb.audiostoreCategoria.categoria}</div>
                                                <small>
                                                    <b style="color: green;">
                                                        ${pcb.audiostoreCategoria.cliente.nome} - 
                                                        <c:if test="${pcb.audiostoreCategoria.tipo eq 1}">
                                                            Música
                                                        </c:if>
                                                        <c:if test="${pcb.audiostoreCategoria.tipo eq 2}">
                                                            Comercial
                                                        </c:if>
                                                        <c:if test="${pcb.audiostoreCategoria.tipo eq 2}">
                                                            Video
                                                        </c:if>
                                                    </b>
                                                </small>

                                                <input type="hidden" name="categorias" value="${pcb.audiostoreCategoria.codigo}" />
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <div style="clear: both"></div>
                                    <button type="button" class="btn btn-default pull-right btn_all_l2" style="margin-top: 10px; margin-right: 10px;">
                                        Remover todos os itens
                                    </button>
                                    <div style="clear: both"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <button type="submit" class="btn btn-default btn_salvar" style="display: none;">
                <i class="fa fa-save"></i> Salvar
            </button>

            <script>
                jQuery(document).ready(function() {
                    $('#example37').multiselect({
                        numberDisplayed: 0,
                        nonSelectedText: 'Selecione uma opção',
                        nSelectedText: 'iten(s) selecionado(s)',
                        enableFiltering: true,
                        onChange: function(option, checked) {
                            // Get selected options.
                            var selectedOptions = $('#example37 option:selected');
                            if (selectedOptions.length >= 24) {
                                // Disable all other checkboxes.
                                var nonSelectedOptions = $('#example37 option').filter(function() {
                                    return !$(this).is(':selected');
                                });
                                var dropdown = $('#example37').siblings('.multiselect-container');
                                nonSelectedOptions.each(function() {
                                    var input = $('input[value="' + $(this).val() + '"]');
                                    input.prop('disabled', true);
                                    input.parent('li').addClass('disabled');
                                });
                            }
                            else {
                                // Enable all checkboxes.
                                var dropdown = $('#example37').siblings('.multiselect-container');
                                $('#example37 option').each(function() {
                                    var input = $('input[value="' + $(this).val() + '"]');
                                    input.prop('disabled', false);
                                    input.parent('li').addClass('disabled');
                                });
                            }
                        }
                    });


                });
            </script>
        </form>
    </jsp:body>
</instore:template>


