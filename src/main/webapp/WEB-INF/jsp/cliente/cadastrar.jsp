<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/clientes" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Clientes </a>
    </jsp:attribute>
    <jsp:body>

        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/clientes">
            <input type="hidden" name="cliente.idcliente" value="${cliente.idcliente}" />
            <input type="hidden" name="dadosCliente.iddadosCliente" value="${dadosCliente.iddadosCliente}" />
            <input type="hidden" name="cliente.endereco.idendereco" value="${cliente.endereco.idendereco}" />
            <input type="hidden" name="cliente.endereco.cep.idcep" value="${cliente.endereco.cep.idcep}" />
            <input type="hidden" name="cliente.endereco.cep.bairro.idbairro" value="${cliente.endereco.cep.bairro.idbairro}" />
            <input type="hidden" name="cliente.endereco.cep.bairro.cidade.idcidade" value="${cliente.endereco.cep.bairro.cidade.idcidade}" />
            <input type="hidden" name="cliente.parente" value="0" />
            <input type="hidden" name="cliente.matriz" value="${true}" />
            <input type="hidden" name="dadosCliente.indiceReajusteContrato" value="0">
            
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Cód. Interno</label>
                        <input type="text" name="cliente.codigoInterno" class="form-control" placeholder="Cód. Interno"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${cliente.codigoInterno}">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Cód. Externo</label>
                        <input type="text" name="cliente.codigoExterno" class="form-control" placeholder="Cód. Externo" 
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" 
                               value="${cliente.codigoExterno}">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="cliente.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${cliente.nome}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Nome Fantasia</label>
                        <input type="text" name="dadosCliente.nomeFantasia" class="form-control" placeholder="Nome Fantasia" 
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" 
                               value="${dadosCliente.nomeFantasia}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>CNPJ</label>
                        <input type="text" data-mask="99.999.999/9999-99" name="dadosCliente.cnpj" class="form-control" placeholder="CNPJ"  
                               data-rule-required="true" 
                               data-rule-minlength="18"
                               data-rule-maxlength="18"
                               data-rule-cnpj="true"
                               value="${dadosCliente.cnpj}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de inicio de contrato</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="dadosCliente.dataInicioContrato" class="form-control datepicker" placeholder="Data de inicio do contrato" 
                                   data-rule-required="true" 
                                   data-rule-minlength="10"
                                   data-rule-maxlength="10" value="${cf:dateFormat(dadosCliente.dataInicioContrato , "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data de termino de contrato</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <c:if test="${not isPageCadastro}">
                                <input type="text" name="dadosCliente.dataTerminoContrato" class="form-control datepicker" placeholder="Data de termino do contrato" 
                                       data-rule-required="true" 
                                       data-rule-minlength="10"
                                       data-rule-maxlength="10" value="${cf:dateFormat(dadosCliente.dataTerminoContrato, "dd/MM/yyyy")}">
                            </c:if>
                            <c:if test="${isPageCadastro}">
                                <input type="text" name="dadosCliente.dataTerminoContrato" class="form-control datepicker" placeholder="Data de termino do contrato" 
                                       data-rule-required="true" 
                                       data-rule-minlength="10"
                                       data-rule-maxlength="10" value="${cf:dateAfterOneYear()}">
                            </c:if>

                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Valor do contrato</label> 
                        <input type="text" name="dadosCliente.valorContrato" class="form-control" placeholder="Indice de reajuste do contrato" value='<fmt:formatNumber value="${dadosCliente.valorContrato}" minFractionDigits="2" />' data-maskmoney="true" data-rule-required="true">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Índice de reajuste do contrato</label>
                        <select class="select2" name="dadosCliente.indiceReajuste.id" class="form-control" data-rule-required="true">
                            <c:forEach items="${indiceReajusteList}" var="indiceReajuste">
                                <option value="${indiceReajuste.id}" ${dadosCliente.indiceReajuste.id eq indiceReajuste.id ? 'selected="selected"' : ''}>${indiceReajuste.tipo}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Renovação automatica do contrato</label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="${true}" ${dadosCliente.renovacaoAutomatica ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="${false}"  ${not dadosCliente.renovacaoAutomatica ? 'checked="checked"' : ''}>&nbsp;Não </label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Faturamento por matriz</label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="cliente.faturamenoMatriz" id="optionsRadios1" value="${true}" ${cliente.faturamenoMatriz ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="cliente.faturamenoMatriz" id="optionsRadios1" value="${false}"  ${not cliente.faturamenoMatriz ? 'checked="checked"' : ''}>&nbsp;Não </label>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label></label> 

                        <div class="block-flat">
                            <div class="header">							
                                Razão social
                            </div>
                            <div class="content">
                                <textarea id="summernote1" name="dadosCliente.razaoSocial" class="form-control" rows="3"  placeholder="Razão social" 
                                          data-rule-required="true" 
                                          data-rule-minlength="3"
                                          data-rule-maxlength="1000" >${dadosCliente.razaoSocial}</textarea>
                            </div>
                        </div>	

                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Local dos arquivos de musica</label>
                        <input type="text" name="dadosCliente.localOrigemMusica" class="form-control" placeholder="Local dos arquivos de musica" 
                               value="${dadosCliente.localOrigemMusica}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Destino dos arquivos de musica</label>
                        <input type="text" name="dadosCliente.localDestinoMusica" class="form-control" placeholder="Destino dos arquivos de musica" 
                               value="${dadosCliente.localDestinoMusica}">
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Local dos arquivos de spot</label>
                        <input type="text" name="dadosCliente.localOrigemSpot" class="form-control" placeholder="Local dos arquivos de spot" 
                               value="${dadosCliente.localOrigemSpot}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Destino dos arquivos de spot</label>
                        <input type="text" name="dadosCliente.localDestinoSpot" class="form-control" placeholder="Destino dos arquivos de spot" 
                               value="${dadosCliente.localDestinoSpot}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Destino dos arquivos Exp</label>
                        <input type="text" name="dadosCliente.localDestinoExp" class="form-control" placeholder="Destino dos arquivos Exp" 
                               value="${dadosCliente.localDestinoExp}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group"> 
                        <label>CEP</label>
                        <input type="text" name="cliente.endereco.cep.numero" class="form-control cepload" placeholder="CEP" 
                               data-mask="99.999-999"
                               data-url="${url}/utilidades/cepload" value="${cliente.endereco.cep.numero}"
                               data-rule-required="true" 
                               data-rule-minlength="10"
                               data-rule-maxlength="10">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Estado</label>
                        <select class="select2" name="cliente.endereco.cep.bairro.cidade.estado.idestado" class="form-control est">
                            <option value>Selecione um estado</option>
                            <c:forEach items="${estadoBeanList}" var="est">
                                <option value="${est.idestado}" data-uf="${est.sigla}" ${cliente.endereco.cep.bairro.cidade.estado.idestado eq est.idestado ? 'selected="selected"' : ''}>${est.sigla} - ${est.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group"> 
                        <label>Cidade</label>
                        <input type="text" name="cliente.endereco.cep.bairro.cidade.nome" class="form-control cid" placeholder="Cidade" 
                               value="${cliente.endereco.cep.bairro.cidade.nome}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group"> 
                        <label>Bairro</label>
                        <input type="text" name="cliente.endereco.cep.bairro.nome" class="form-control bai" placeholder="Bairro" 
                               value="${cliente.endereco.cep.bairro.nome}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group"> 
                        <label>Tipo Log.</label>
                        <input type="text" name="cliente.endereco.cep.bairro.tipo" class="form-control tipo_log" placeholder="Tipo do Logradouro" 
                               value="${cliente.endereco.cep.bairro.tipo}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">

                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group"> 
                        <label>Logradouro</label>
                        <input type="text" name="cliente.endereco.cep.bairro.rua" class="form-control log" placeholder="Logradouro" 
                               value="${cliente.endereco.cep.bairro.rua}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">

                    </div>
                </div>

                <div class="col-md-1">
                    <div class="form-group"> 
                        <label>Nº</label>
                        <input type="text" name="cliente.endereco.numero" class="form-control num" placeholder="Número" 
                               value="${cliente.endereco.numero}"
                               data-rule-required="true" 
                               data-rule-minlength="1"
                               data-rule-maxlength="255">
                    </div>
                </div>

                <div class="col-md-5">
                    <div class="form-group"> 
                        <label>Complemento</label>
                        <input type="text" name="cliente.endereco.complemento" class="form-control comp" placeholder="Complemento" value="${cliente.endereco.complemento}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Situação &nbsp;&nbsp;</label>
                        <c:if test="${not isPageCadastro}">
                            <label class="radio-inline"> <input class="icheck" type="radio" name="cliente.situacao" id="optionsRadios1" value="${true}" ${cliente.situacao ? 'checked="checked"' : ''} >&nbsp;Ativo </label>
                            <label class="radio-inline"> <input class="icheck" type="radio" name="cliente.situacao" id="optionsRadios1" value="${false}"  ${not cliente.situacao ? 'checked="checked"' : ''}>&nbsp;Inativo </label>
                            </c:if>
                            <c:if test="${isPageCadastro}">
                            <label class="radio-inline"> <input class="icheck" type="radio" name="cliente.situacao" id="optionsRadios1" value="${true}"  checked="checked">&nbsp;Ativo </label>
                            <label class="radio-inline"> <input class="icheck" type="radio" name="cliente.situacao" id="optionsRadios1" value="${false}" >&nbsp;Inativo </label>
                            </c:if>
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

                                        jQuery('.contador_l1').text("Total " + (jQuery('.lista_filial1 li').size() - clones.length));
                                        jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size() + clones.length));

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
                                    jQuery('.contador_l1').text("Total " + (jQuery('.lista_filial1 li').size() - 1));
                                    jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size() + 1));

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

                                        jQuery('.contador_l1').text("Total " + (jQuery('.lista_filial1 li').size() + clones.length));
                                        jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size() - clones.length));

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
                                    jQuery('.contador_l1').text("Total " + (jQuery('.lista_filial1 li').size() + 1));
                                    jQuery('.contador_l2').text("Total " + (jQuery('.lista_filial2 li').size() - 1));
                                    addLista2(jQuery(this));
                                }
                            });

                            function addLista1(self) {
                                if (null != self && undefined != self) {
                                    self.fadeOut('500', function() {
                                        var id = jQuery(this).attr("id");
                                        var clone = jQuery(this).clone();
                                        jQuery(this).remove();
                                        clone.children('input[type="hidden"]').attr("disabled", false);
                                        clone.css({
                                            'background-color': '#FFF'
                                        });

                                        if (jQuery('.lista_filial2 li').size() > 0) {
                                            var data_index = clone.data("index");
                                            var data_index_less = 0;
                                            var data_index_more = 999999999999;

                                            jQuery('.lista_filial2 li').each(function() {
                                                var _this = jQuery(this);
                                                if (_this.data("index") < data_index && _this.data("index") > data_index_less) {
                                                    data_index_less = _this.data("index");
                                                }
                                                if (_this.data("index") > data_index && _this.data("index") < data_index_more) {
                                                    data_index_more = _this.data("index");
                                                }
                                            });

                                            var current = parseInt(data_index);
                                            var less = parseInt(data_index_less);
                                            var more = parseInt(data_index_more);


                                            // depois de x
                                            if ((current - less) < (more - current)) {
                                                jQuery('.lista_filial2 li[data-index="' + less + '"]').after(clone);
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

                                            // antes de x
                                            if ((current - less) > (more - current)) {
                                                jQuery('.lista_filial2 li[data-index="' + more + '"]').before(clone);
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

                                            // entre x e x
                                            if ((current - less) == (more - current)) {
                                                jQuery('.lista_filial2 li[data-index="' + more + '"]').before(clone);
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
                                        } else {
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
                                    });
                                }

                                jQuery('.lista_filial2 li').each(function() {
                                    jQuery(this).show();
                                });


                            }

                            function addLista2(self) {
                                if (null != self && undefined != self) {
                                    self.fadeOut('500', function() {
                                        var id = jQuery(this).attr("id");
                                        var clone = jQuery(this).clone();
                                        jQuery(this).remove();
                                        clone.children('input[type="hidden"]').attr("disabled", true);
                                        clone.css({
                                            'background-color': '#FFF'
                                        });

                                        if (jQuery('.lista_filial1 li').size() > 0) {
                                            var data_index = clone.data("index");
                                            var data_index_less = 0;
                                            var data_index_more = 999999999999;

                                            jQuery('.lista_filial1 li').each(function() {
                                                var _this = jQuery(this);
                                                if (_this.data("index") < data_index && _this.data("index") > data_index_less) {
                                                    data_index_less = _this.data("index");
                                                }
                                                if (_this.data("index") > data_index && _this.data("index") < data_index_more) {
                                                    data_index_more = _this.data("index");
                                                }
                                            });

                                            var current = parseInt(data_index);
                                            var less = parseInt(data_index_less);
                                            var more = parseInt(data_index_more);


                                            // depois de x
                                            if ((current - less) < (more - current)) {
                                                jQuery('.lista_filial1 li[data-index="' + less + '"]').after(clone);
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

                                            // antes de x
                                            if ((current - less) > (more - current)) {
                                                jQuery('.lista_filial1 li[data-index="' + more + '"]').before(clone);
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

                                            // entre x e x
                                            if ((current - less) == (more - current)) {
                                                jQuery('.lista_filial1 li[data-index="' + more + '"]').before(clone);
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
                                        } else {
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
                                    });
                                }
                                jQuery('.lista_filial1 li').each(function() {
                                    jQuery(this).show();
                                });
                            }

                            jQuery('.lista1_pesq').on('keyup', function() {
                                var value = jQuery(this).val();
                                if (value != '') {
                                    jQuery(".lista_filial1 li").hide();
                                    jQuery(".lista_filial1 li div.text:like('%" + value + "%')").each(function() {
                                        jQuery(this).parent('li').show();
                                    });
                                    jQuery('.contador_l1').text("Total " + jQuery('.lista_filial1 li:visible').size());
                                }
                            });

                            jQuery('.lista2_pesq').on('keyup', function() {
                                var value = jQuery(this).val();
                                jQuery(".lista_filial2 li").hide();
                                jQuery(".lista_filial2 li div.text:like('%" + value + "%')").each(function() {
                                    jQuery(this).parent('li').show();
                                });
                                jQuery('.contador_l2').text("Total " + jQuery('.lista_filial2 li:visible').size());
                            });

                            jQuery('.contador_l1').text("Total " + jQuery('.lista_filial1 li').size());
                            jQuery('.contador_l2').text("Total " + jQuery('.lista_filial2 li').size());

                            jQuery('.btn_all_l1').on('click', function() {
                                jQuery('.lista_filial1 li:visible').each(function() {
                                    addLista1(jQuery(this));
                                });
                            });

                            jQuery('.btn_all_l2').on('click', function() {
                                jQuery('.lista_filial2 li:visible').each(function() {
                                    addLista2(jQuery(this));
                                });
                            });
                        });
                    </script>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="block">
                                <div class="header">
                                    <h2> 
                                        <b>Filiais</b> 
                                        <span class="pull-right contador_l1">Total </span>
                                    </h2>
                                    <input type="text" class="form-control lista1_pesq" placeholder="Filtro de Pesquisar">

                                </div>
                                <div class="content no-padding ">
                                    <ul class="items lista_filial lista_filial1">
                                        <c:forEach items="${filialBeanList2}" var="filial" varStatus="vs">
                                            <li id="${vs.index}" data-index="${vs.index}">
                                                <i class="fa fa-cubes"></i>
                                                <div class="text">${filial.nome}</div>
                                                <small>
                                                    <c:if test="${filial.parente eq 0}">
                                                        <b style="color: green">Não possui matriz</b>
                                                    </c:if>
                                                    <c:if test="${filial.parente ne 0}">
                                                        <b style="color: red">${filial.nomeParente}</b>
                                                    </c:if>
                                                </small>

                                                <input type="hidden" name="filialList" value="${filial.idcliente}" disabled="disabled"/>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <div style="clear: both"></div>
                                    <button type="button" class="btn btn-default pull-right btn_all_l1" style="margin-top: 10px; margin-right: 10px;">
                                        Selecionar todos
                                    </button>
                                    <div style="clear: both"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="block">
                                <div class="header">
                                    <h2> 
                                        <b>Filiais inclusas</b> 
                                        <span class="pull-right contador_l2">Total </span>
                                    </h2>
                                    <input type="text" class="form-control lista2_pesq" placeholder="Filtro de Pesquisar">
                                </div>
                                <div class="content no-padding ">
                                    <ul class="items lista_filial lista_filial2">
                                        <c:forEach items="${filialBeanList1}" var="filial" varStatus="vs">
                                            <li id="${vs.index}" data-index="${vs.index}">
                                                <i class="fa fa-cubes"></i>
                                                <div class="text">${filial.nome}</div>
                                                <small>
                                                    <c:if test="${filial.parente eq 0}">
                                                        <b style="color: green">Não possui matriz</b>
                                                    </c:if>
                                                    <c:if test="${filial.parente ne 0}">
                                                        <b style="color: red">Possui matriz</b>
                                                    </c:if>
                                                </small>

                                                <input type="hidden" name="filialList" value="${filial.idcliente}" />
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <div style="clear: both"></div>
                                    <button type="button" class="btn btn-default pull-right btn_all_l2" style="margin-top: 10px; margin-right: 10px;">
                                        Selecionar todos
                                    </button>
                                    <div style="clear: both"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>

    </jsp:body>
</instore:template>