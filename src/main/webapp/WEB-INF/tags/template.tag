<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Template instore" pageEncoding="UTF-8"%>
<%@attribute fragment="true" name="gridColumn" %>
<%@attribute fragment="false" name="isGrid" %>
<%@attribute fragment="true" name="submenu" %>
<%@attribute fragment="true" name="detailsButton" %>
<%@attribute fragment="false" name="menucolapse"  %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 

<c:set scope="session" var="url" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"></c:set>
<c:set scope="session" var="url_resources" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/"></c:set>
<c:set scope="session" var="url_css" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/css/"></c:set>
<c:set scope="session" var="url_js" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/js/"></c:set>
<c:set scope="session" var="url_img" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/img/"></c:set>
<c:set scope="session" var="url_cz" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/cz/"></c:set>

    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta name="description" content="">
            <meta name="author" content="">
            <link rel="shortcut icon" href="images/favicon.png">

            <title>Instore</title>
            <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,400italic,700,800' rel='stylesheet' type='text/css'>
            <link href='http://fonts.googleapis.com/css?family=Raleway:300,200,100' rel='stylesheet' type='text/css'>
            <link href="${url_cz}js/bootstrap/dist/css/bootstrap.css?v=${machine_id}" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${url_cz}js/jquery.gritter/css/jquery.gritter.css?v=${machine_id}" />
        <link rel="stylesheet" href="${url_cz}fonts/font-awesome-4/css/font-awesome.min.css?v=${machine_id}">
        <link rel="stylesheet" type="text/css" media="all" href="${url_css}bbGrid.css?v=${machine_id}"/>
        <link rel="stylesheet" type="text/css" media="all" href="${url_css}clockface.css?v=${machine_id}"/>
        <link rel="stylesheet" type="text/css" href="${url_cz}js/jquery.nanoscroller/nanoscroller.css?v=${machine_id}" />
        <link rel="stylesheet" type="text/css" href="${url_cz}js/jquery.easypiechart/jquery.easy-pie-chart.css?v=${machine_id}" />
        <link rel="stylesheet" type="text/css" href="${url_cz}js/bootstrap.switch/bootstrap-switch.css?v=${machine_id}" />
        <link rel="stylesheet" type="text/css" href="${url_cz}js/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css?v=${machine_id}" />
        <link rel="stylesheet" type="text/css" href="${url_cz}js/jquery.select2/select2.css?v=${machine_id}" />
        <link rel="stylesheet" type="text/css" href="${url_cz}js/bootstrap.slider/css/slider.css?v=${machine_id}" />
        <link rel="stylesheet" type="text/css" href="${url_cz}js/bootstrap.datetimepicker/css/bootstrap-datetimepicker.css?v=${machine_id}" />
        <link rel="stylesheet" type="text/css" href="${url_cz}js/bootstrap.summernote/dist/summernote.css?v=${machine_id}" />
        <link href="${url_cz}js/jquery.icheck/skins/square/blue.css?v=${machine_id}" rel="stylesheet">
        <link href="${url_cz}js/fuelux/css/fuelux.css" rel="stylesheet" />	
        <link href="${url_css}main.css" rel="stylesheet" />	
        <link rel="stylesheet" type="text/css" href="${url_cz}js/bootstrap.wysihtml5/src/bootstrap-wysihtml5.css?v=${machine_id}"></link>
        <link rel="stylesheet" type="text/css" href="${url_cz}js/jquery.niftymodals/css/component.css" />
        <link href="${url_cz}css/style.css?v=${machine_id}" rel="stylesheet" />	
        <link rel="stylesheet" type="text/css" href="${url_cz}js/bootstrap.slider/css/slider.css" />


        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_js}jquery.ui.min.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.nanoscroller/jquery.nanoscroller.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.sparkline/jquery.sparkline.min.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.easypiechart/jquery.easy-pie-chart.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/behaviour/general.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.ui/jquery-ui.js?v=${machine_id}" ></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.nestable/jquery.nestable.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/bootstrap.switch/bootstrap-switch.min.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.select2/select2.min.js?v=${machine_id}" ></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/bootstrap.slider/js/bootstrap-slider.js?v=${machine_id}" ></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.gritter/js/jquery.gritter.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/behaviour/voice-commands.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/bootstrap/dist/js/bootstrap.min.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.flot/jquery.flot.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.flot/jquery.flot.pie.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.flot/jquery.flot.resize.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8"  src="${url_cz}js/jquery.flot/jquery.flot.labels.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}watch.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}underscore.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}backbone.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}bbGrid.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}holder.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}bootbox.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}jquery.validate.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}additional.methods.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}base64.js?v=${machine_id}"></script>
        <script type="text/javascript" src="${url_cz}js/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js?v=${machine_id}"></script>
        <script type="text/javascript" src="${url_cz}js/jquery.icheck/icheck.min.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}imask.js?v=${machine_id}"></script>
        <script type="text/javascript" src="${url_cz}js/bootstrap.summernote/dist/summernote.min.js?v=${machine_id}"></script>
        <script type="text/javascript" src="${url_cz}js/jquery.gritter/js/jquery.gritter.js?v=${machine_id}"></script>

        <script type="text/javascript" charset="utf-8" src="${url_js}bloodhound.js?v=${machine_id}"></script>
        <script type="text/javascript" charset="utf-8" src="${url_js}typeahead.bundle.js?v=${machine_id}"></script>
        <script type="text/javascript" src="${url_cz}js/jquery.niftymodals/js/jquery.modalEffects.js"></script>
        <script src="${url_cz}js/bootstrap.slider/js/bootstrap-slider.js" type="text/javascript"></script>
        <script src="${url_js}bootstrap.multiselect.js" type="text/javascript"></script>
        <script src="${url_js}prettify.js" type="text/javascript"></script>
        <script type="text/javascript" src="${url_cz}js/bootstrap.wysihtml5/lib/js/wysihtml5-0.3.0.js"></script>
        <script type="text/javascript" src="${url_cz}js/bootstrap.wysihtml5/src/bootstrap-wysihtml5.js"></script>


        <link type="text/css" href="${url}/resources/jplayer/skin/blue_monday/jplayer.blue.monday.css" rel="stylesheet" />
        <script type="text/javascript" src="${url}/resources/jplayer/jquery.jplayer.min.js"></script>


        <script src="${url_cz}js/ckeditor/ckeditor.js"></script>
        <script src="${url_cz}js/ckeditor/adapters/jquery.js"></script>
        <script src="${url_js}gerarCsv.js" ></script>
        <script src="${url_js}shortcut.js" ></script>

        <style type="text/css">
            input{
                text-transform: uppercase;
            } 

            .table > thead > tr > td.warning, .table > tbody > tr > td.warning, .table > tfoot > tr > td.warning, .table > thead > tr > th.warning, .table > tbody > tr > th.warning, .table > tfoot > tr > th.warning, .table > thead > tr.warning > td, .table > tbody > tr.warning > td, .table > tfoot > tr.warning > td, .table > thead > tr.warning > th, .table > tbody > tr.warning > th, .table > tfoot > tr.warning > th {
                background-color: #2494f2 !important;
                color: #FFF;
            }
           
        </style>

        <script type="text/javascript">
            jQuery(document).ready(function () {
                
                
                App.init();
                App.textEditor();
                $('#some-textarea').wysihtml5();

                $('.md-trigger').modalEffects();

                jQuery.storageAdd = function (name, value) {
                    if (typeof (Storage) !== "undefined") {
                        localStorage.setItem(name, value);
                    }
                };

                jQuery.storage = function (name) {
                    if (typeof (Storage) !== "undefined") {
                        return localStorage.getItem(name);
                    }
                };

                jQuery.storageClear = function () {
                    if (typeof (Storage) !== "undefined") {
                        window.localStorage.clear();
                    }
                };

                if (null != jQuery.storage("matriz_selecionada_nome")
                        && undefined != jQuery.storage("matriz_selecionada_nome")) {
                    jQuery('.cli_nom_red').text(jQuery.storage("matriz_selecionada_nome"));
                }

                jQuery('.cliente_selecionado').on('click', function () {
                    self = jQuery(this);
                    jQuery.storageAdd("matriz_selecionada", self.data('id'));
                    jQuery.storageAdd("matriz_selecionada_nome", self.data('clienteNome'));
                    jQuery('.cli_nom_red').text(self.data('clienteNome'));
                    if (self.val() == '0') {
                        jQuery.storageClear();
                    } else {
                        if (null != self.data('id') && '' != self.data('id') && self.data('id') != '0') {
                            jQuery('.select_cliente').val(self.data('id'));
                            jQuery('.select_cliente').trigger('change');
                        }
                    }
                });

                if (null != jQuery.storage("matriz_selecionada") && undefined != jQuery.storage("matriz_selecionada")) {
                    jQuery('.cliente_selecionado[data-id="' + jQuery.storage("matriz_selecionada") + '"]').click();
                }


                $('#summernote').summernote();

                // ws_url
                var ws_url = jQuery.storage('ws_url');
                if (ws_url == null || ws_url == undefined) {
                    jQuery.storageAdd('ws_url', "ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}");
                }
                // machine id
                var machine_id = jQuery.storage('machine_id');
                if (machine_id == null || machine_id == undefined) {
                    jQuery.storageAdd('machine_id', "MACHINEID${machine_id}");
                }

                jQuery('.btn_up_cache').on('click', function () {
                    bootbox.confirm("A página será atualizada, tem certeza de que deseja atualizar o cache? ", function (result) {
                        if (result) {
                            jQuery.storageClear();
                            setTimeout(function () {
                                window.location.reload();
                            }, 1000);
                            return false;
                        }
                    });
                });
            });
        </script>
        <script>
            jQuery(document).ready(function () {
                var input = $('input[data-rule-required]');
                var div = input.closest('div.form-group');
                div.children("label").append("*");
                
            });
            
        </script>

        <style>
            .info .select2-chosen {
                color: #3380FF !important;
            }
        </style>
        <c:if test="${null ne isGrid and isGrid eq true}">
            <style>
                .bbGrid-grid-nav {
                    margin-top: -1px !important;
                    height: 50px !important;
                }
                .info .select2-chosen {
                    color: #3380FF !important;
                }
            </style>
            <jsp:invoke fragment="gridColumn"></jsp:invoke>
                <script type="text/javascript" charset="utf-8">
                    jQuery(document).ready(function () {
                        var App = {};

                        var url = document.URL;

                        if (null != url.match(/#$/)) {
                            url = url.substring(0, url.match(/#$/).index);
                        }

                        var urlJSON = url + '?datajson=true';


                        App._exampleCollection = Backbone.Collection.extend({
                            url: urlJSON
                        });


                        App.companies = new Backbone.Collection;
                        App.clearGridCollection = new Backbone.Collection;
                        App.exampleCollection = new App._exampleCollection();


                        App.exampleCollection.fetch({
                            wait: true,
                            success: function (collection) {
                                App.clearGridCollection.reset(collection.models.slice(0, 10000));
                            }
                        });

                        bbGrid.Dict.ptbr = {
                            loading: 'Aguarde, carregando...',
                            noData: 'Nenhum registro encontrado',
                            search: 'Filtrar',
                            rowsOnPage: 'Linhas por página',
                            page: 'Página',
                            all: 'Todos',
                            prep: 'de'
                        };

                        bbGrid.setDict('ptbr');
                        var valor = 0;
                        var gridview = new bbGrid.View({
                            container: jQuery('[datagrid="true"]'),
                            collection: App.clearGridCollection,
                            enableSearch: false,
                            rows: 25,
                            rowList: [5, 25, 50, 100, 250, 500],
                            colModel: gridColumn,
                            multiselect: false,
                            onBeforeRender: function () {
                                valor = 0;
                            },
                            onReady: function () {
                                if (typeof (onReady) != "undefined") {
                                    onReady(this._collection.models);
                                }
                            },
                            onRowClick: function (data, evt) {
                                if (!evt.ctrlKey) {
                                    var _url = url;

                                    if (null != _url.match(/\/$/)) {
                                        var _url = url.substring(0, url.match(/\/$/).index);
                                    }

                                    if (jQuery('[datagrid="true"]').data('withs') != null && jQuery('[datagrid="true"]').data('withs') != undefined && jQuery('[datagrid="true"]').data('withs') != "") {
                                        if (jQuery('[datagrid="true"]').data('withs') == 0) {
                                            if (null != _url.match(/s$/)) {
                                                var _url = url.substring(0, url.match(/s$/).index);
                                            }
                                        }
                                    } else {
                                        if (null != url.match(/s$/)) {
                                            var _url = url.substring(0, url.match(/s$/).index);
                                        }
                                    }


                                    if (jQuery('[datagrid="true"]').data('removeParam') != null && jQuery('[datagrid="true"]').data('removeParam') != undefined && jQuery('[datagrid="true"]').data('removeParam') != "") {
                                        var arrayUrlx = _url.split("/");
                                        var x = arrayUrlx.length;
                                        var y = 0;
                                        var __URL__ = '';
                                        var connector = '';
                                        for (i in arrayUrlx) {
                                            if (i < (x - 1)) {
                                                var item = arrayUrlx[i];
                                                __URL__ += connector + item;
                                                connector = '/';
                                            }
                                        }

                                        if (jQuery('[datagrid="true"]').data('withs') != null && jQuery('[datagrid="true"]').data('withs') != undefined && jQuery('[datagrid="true"]').data('withs') != "") {
                                            if (jQuery('[datagrid="true"]').data('withs') == 1) {
                                                __URL__ = __URL__.substr(0, __URL__.length - 1);
                                            }
                                        }
                                        _url_atualizar = __URL__ + '/atualizar/' + data.attributes[jQuery('[datagrid="true"]').data('id')];
                                        _url_remover = __URL__ + '/remover/' + data.attributes[jQuery('[datagrid="true"]').data('id')];
                                    } else {
                                        _url_atualizar = _url + '/atualizar/' + data.attributes[jQuery('[datagrid="true"]').data('id')];
                                        _url_remover = _url + '/remover/' + data.attributes[jQuery('[datagrid="true"]').data('id')];
                                    }


                                    jQuery('.upd').on('click', function () {
                                        window.location.href = _url_atualizar;
                                    });
                                    jQuery('.trash').on('click', function () {
                                        window.location.href = _url_remover;
                                    });

                                    for (var p in data.attributes) {
                                        var txt = '';
                                        var sep = '';
                                        var txt = jQuery('[data-' + p + '="true"]').text() + ' ';
                                        jQuery('[data-' + p + '="true"]').text(data.attributes[p]);
                                    }

                                    if (null != onRowClick && undefined != onRowClick) {
                                        onRowClick(data.attributes);
                                    }

                                    for (var p in data.attributes) {
                                        var txt = '';
                                        var sep = '';
                                        var txt = jQuery('[data-' + p + '="true"]').text() + ' ';
                                        jQuery('[data-' + p + '="true"]').html(data.attributes[p]);
                                    }

                                    jQuery('.xdet [datagrid-view="true"]').remove();
                                    var clone_view = jQuery('[datagrid-view="true"]').clone();
                                    clone_view.append('<br /><br /><br /><br /><br /><br />');
                                    jQuery('.xdet').append(clone_view);
                                    clone_view.show();
                                    jQuery("#player_musicas_gerais").jPlayer({
                                        ready: function () {
                                            $(this).jPlayer("setMedia", {
                                                title: "Musica",
                                                mp3: "${url}/musica/stream/" + data.attributes.id,
                                            });
                                        },
                                        swfPath: "${url}/resources/jplayer/",
                                        supplied: "mp3"
                                    });


                                    if (jQuery('.sld_view:hidden')) {
                                        jQuery('.sld_view').show('slow');
                                    }
                                    jQuery('.xclose').on('click', function () {
                                        jQuery('.sld_view').hide('slow');
                                    });
                                }
                            },
                            onRowDblClick: function (data) {
                                var _url = url;

                                if (null != _url.match(/\/$/)) {
                                    var _url = url.substring(0, url.match(/\/$/).index);
                                }
                                if (null != _url.match(/s$/)) {
                                    var _url = url.substring(0, url.match(/s$/).index);
                                }

                                _url_atualizar = _url + '/atualizar/' + data.attributes[jQuery('[datagrid="true"]').data('id')];
                                _url_remover = _url + '/remover/' + data.attributes[jQuery('[datagrid="true"]').data('id')];

                                for (var p in data.attributes) {
                                    var txt = '';
                                    var sep = '';
                                    var txt = jQuery('[data-' + p + '="true"]').text() + ' ';
                                    jQuery('[data-' + p + '="true"]').text(data.attributes[p]);
                                }

                                if (null != onRowDblClick && undefined != onRowDblClick) {
                                    onRowDblClick(data.attributes);
                                }

//                                    bootbox.dialog({
//                                        message: jQuery('[datagrid-view="true"]').html(),
//                                        title: "Informações detalhadas",
//                                        buttons: {
//                                            atualizar: {
//                                                label: "<i class=\"fa fa-refresh\">&nbsp;&nbsp;Atualizar</i>",
//                                                className: "btn-success",
//                                                callback: function() {
//                                                    window.location.href = _url_atualizar;
//                                                }
//                                            },
//                                            remover: {
//                                                label: "<i class=\"fa fa-trash-o\">&nbsp;&nbsp;Remover</i>",
//                                                className: "btn-danger",
//                                                callback: function() {
//                                                    window.location.href = _url_remover;
//                                                }
//                                            },
//                                            fechar: {
//                                                label: "<i class=\"fa fa-times\">&nbsp;&nbsp;Fechar</i>",
//                                                className: "btn-default",
//                                                callback: function() {
//                                                }
//                                            },
//                                        }
//                                    });

                            },
                        });
                    });
            </script>
        </c:if>

        <link rel="stylesheet" type="text/css" href="${url_css}table.css" />
        <script type="text/javascript" src="${url_js}table.js"></script>
        <script type="text/javascript" src="${url_js}ws_cache.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${url_js}main.js"></script>
    </head>

    <body>
        <div class="enfoc">
            <!-- Fixed navbar -->
            <div id="head-nav" class="navbar navbar-default navbar-fixed-top">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="fa fa-gear"></span>
                        </button>
                        <a class="navbar-brand" href="#"><span>ManagerInstore</span></a>
                    </div>
                    <div class="navbar-collapse collapse">
                        <div class="navbar-collapse collapse">
                            <ul class="nav navbar-nav">
                                <li><a href="#menu" class="link_menu">Menu</a></li>
                                <li class="active"><a href="${url}">Dashboard</a></li>
                                <li><a href="#ajuda">Ajuda</a></li>
                                <li><a href="${url}/meus-dados">Meus Dados</a></li>
                                <li><a href="${url}/minha-senha">Minha senha</a></li>
                                <li><a class="btn_up_cache" href="#"> <i class="fa fa-refresh"></i>&nbsp;&nbsp;Atualizar Cache da aplicação</a></li>
                                <li><a class="btn_sair" href="${url}/sair">Sair</a></li>
                                <li>
                                    <a href="${url}">
                                        <div style="background-color: #ef3e3e; font-weight: bold; float: left; padding-left: 20px; padding-right: 20px; border-radius: 4px; margin-top: -9px;">
                                            <h4 style="color: #fff; font-weight: bold;">Cliente: ${sessionUsuario.cliente.nome}</h4>
                                        </div>
                                    </a>
                                </li>
                               <!--
                                <li class="button dropdown">
                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                                        <i class="fa fa-building"></i> 
                                        <span class="bubble cli_nom_red"> Clientes</span></a> 
                                    <ul class="dropdown-menu">
                                        <li>
                                            <div class="nano nscroller">
                                                <div class="content">
                                                    <ul>
                                                        <c:forEach items="${atalhoClienteList}" var="item">
                                                            <li> 
                                                                <a data-cliente-nome="${item.nome}" class="cliente_selecionado" href="#cliente_selecionado=${item.idcliente}" data-id="${item.idcliente}"><i class="fa fa-building info"></i><b>Cliente</b> ${item.nome} <span class="date">${item.matriz ? 'Matriz' : 'Filial'}</span></a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                            <ul class="foot"><li><a href="${url}/clientes">Ver todos</a></li></ul>           
                                        </li>
                                    </ul>
                                </li>
                               -->
                            </ul>
                        </div>
                    </div><!--/.nav-collapse -->
                </div>
            </div>


            <div id="cl-wrapper" class="fixed-menu  ${menucolapse ? "sb-collapsed" : ""}">
                <!--                    <div class="cl-sidebar">
                                        <div class="cl-toggle"><i class="fa fa-bars"></i></div>
                                        <div class="cl-navblock">
                                            <div class="menu-space">
                                                <div class="content">
                                                    <div class="side-user">
                                                        <div class="avatar"><img src="${url_cz}/images/logo.png" alt="Avatar" style="max-width: 170px;" /></div>
                                                        <hr style="border: 0px; border-top: 1px solid rgba(255,255,255,0.1)" />
                                                        <div class="info">
                                                            <a href="#">${sessionUsuario.usuarioBean.nome}</a>
                                                            <br /> 
                                                            <select class="select2" name="matriz_selecionada" style="color: #3380FF;">
                                                                <option value="0">Selecione uma matriz</option>
                <c:forEach items="${atalhoClienteList}" var="item">
                    <option value="${item.idcliente}">${item.nome}</option>
                </c:forEach>
            </select>

        </div>
    </div>
    <ul class="cl-vnavigation">
                ${menu}
            </ul>
        </div>
    </div>
    <div class="text-right collapse-button" style="padding:7px 9px;">
        <button id="sidebar-collapse" class="btn btn-default" style=""><i style="color:#fff;" class="fa fa-angle-left"></i></button>
    </div>
</div>
</div>-->
                <div class="page-aside sld_view" style="display: none;">
                    <div class="fixed fixed_aside1" style="overflow-y: auto;">
                        <div class="content"> 
                            <h2>Detalhes 
                                <br />
                                <button data-toggle="tooltip" data-placement="bottom" data-original-title="Fechar area" type="button" class="btn btn-default xclose"><i class="fa fa-long-arrow-left"></i></button>
                                <button data-toggle="tooltip" data-placement="bottom" data-original-title="Atualizar dados" type="button" class="btn btn-default upd"><i class="fa fa-pencil"></i></button>
                                <button data-toggle="tooltip" data-placement="bottom" data-original-title="Remover dados" type="button" class="btn btn-default trash"><i class="fa fa-trash-o"></i></button>
                                    <jsp:invoke fragment="detailsButton" /> 
                            </h2>
                            <hr />
                            <div class="xdet"></div>
                        </div>
                    </div>
                </div>

                <div class="container-fluid" id="pcont" style="background-color: #c3c3c3">
                    <div class="page-head">
                        <h2><i class="fa ${currentFuncionalidadeBean.icone}"></i> Instore ${currentFuncionalidadeBean.nome}</h2>

                        <div style="float: right; margin-top: -40px; margin-right: 7px;">
                            <jsp:invoke fragment="submenu" /> 
                        </div>
                        
                    </div>		
                        
                    <style type="text/css">
                        .show-case{margin-bottom:50px;}
                        .show-case img{max-width:362px;width:100%;}
                    </style>
                    <div class="cl-mcont" style="background-color: #c3c3c3">
                        <jsp:doBody />
                    </div>
                </div> 

            </div>
        </div>

       <script type="text/javascript" charset="utf-8"  src="${url_js}angular.min.js"></script>
        <style>
            .sidebar {
                display: block;
                width: 400px; 
                margin-top: -15px;
                margin-left: -30px;
                background-color: white;
                float: left;
            }

            .sidebarContent {
                display: block;
                width: 400px; 
                margin-top: -15px;
                margin-left: 0px;
                overflow-y: auto;
                overflow-x: hidden;
                float: left;
                background-color: #ebebeb;
                padding-top: 20px;
                padding-left: 20px;
                padding-right: 20px;
            }

            .sidebar h1 {
                display: block;
                width: 400px;
                font-size: 14px;
                line-height: 40px;
                text-indent: 20px;
                border-bottom: 1px solid #ebebeb;
            }

            .sidebar ul { list-style: none; margin-left: -40px; }
            .sidebar ul li { 
                margin-left: 20px; 
                padding: 4px; 
            }

            .sidebar ul li a { 
                color: #2494F2;
                font-weight: bold; 
            }

            .txtPF {
                display: block;
                display: block;
                width: 180px;
                height: 50px;
                padding: 20px;
                font-size: 12px;
                text-align: center;
                margin-top: 0px;
                margin-left: -20px;
                color: #73B843;
                font-weight: bold;
                padding-top: 0px;
                padding-bottom: 0px;
            }

            .well2 {
                display: block;
                width: 180px;
                height: 100px;

                padding: 20px;

                background-color: #FFF;
                margin-top: 40px;
            }

            .well2 i {
                display: block;
                width: 180px;
                height: 50px; 
                padding: 20px;
                font-size: 24px;
                text-align: center;
                margin-left: -20px;
                margin-top: -20px;
                color: #ef3e3e;
            }
            .SidebarApp {
                display: none;
                margin-top: 150px;
                margin-left: 30px;
                position: absolute;
                background-color: #ebebeb;
                z-index: 9999999999999999999999999;
            }
        </style>
        <div class="SidebarApp" ng-app="SidebarApp" ng-controller="SidebarController">
            <div class="sidebar">
                <h1>Meus perfis</h1>
                <ul>
                    <li ng-repeat="p in perfilList" ng-click="selecionarPerfil($index);">
                        <a href="#"><i class="fa fa-files-o"></i>&nbsp;&nbsp;{{p.nome}}</a>
                    </li>
                </ul>
            </div>

            <div class="sidebarContent">
                <div class="row">
                    <div class="col-md-2" ng-repeat="f in funcionalidades">
                        <a href="${url}{{f.mid}}">
                            <div class="well2">
                                <i class="fa {{f.icone}}"></i> <span class="txtPF">{{f.nome}}</span>
                            </div>
                        </a>
                    </div>
                </div>

                <div ng-if="null == funcionalidades" style="color: #000;"> Selecione um perfil </div>
                <div ng-if="funcionalidades.length <= 0" style="color: #000;"> Nenhuma permissão atribuida a este perfil </div>
            </div>
        </div>


        <script type="text/javascript">
            var angular = angular.module('SidebarApp', []);
            angular.controller('SidebarController', function ($scope) {
                $scope.perfilList = new Array();
                <c:forEach items="${perfis}" var="p">
                    $scope.perfilList.push({
                        nome: '${p.nome}',
                        funcionalidades: [
                            <c:forEach items="${p.funcionalidadeBeanList}" var="f">
                                    {mid: '${f.mappingId}', nome: '${f.nome}', icone: '${f.icone}'},
                            </c:forEach>
                        ]
                    });
                </c:forEach> 
                $scope.funcionalidades = null;
                $scope.selecionarPerfil = function (index) {
                    $scope.funcionalidades = new Array();
                    $scope.funcionalidades = $scope.perfilList[index].funcionalidades;
                }
            });
        </script>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                function autosize() {
                    var h = jQuery(window).height();
                    var w = jQuery(window).width();
                    jQuery('.sidebar').css({
                        'height': (h - 130) + 'px'
                    });

                    jQuery('.sidebarContent').css({
                        'width': (w - 400) + 'px',
                        'height': (h - 130) + 'px'
                    });
                }

                autosize();

                jQuery(window).resize(function () {
                    autosize();
                });
            });
        </script>
                        
        
        
        <script type="text/javascript">
            jQuery(document).ready(function () {
                jQuery(window).resize(function () {
                    jQuery('.navv').css('height', (jQuery(window).height() - 100) + 'px');
                });
                jQuery('.navv').css('height', (jQuery(window).height() - 100) + 'px');
                
                if(window.location.href.indexOf('#menu_show')!=-1) {
                    jQuery('body').css({
                            'overflow-x':'hidden',
                            'overflow-y':'hidden'
                        });
                    jQuery('.SidebarApp').show();
                    jQuery('.sidebar ul li:first a').click();   
                    
                }
                
                jQuery('.link_menu').on('click', function () {
//                        jQuery('.enfoc').addClass('desfoc');
                    jQuery('.SidebarApp').toggle();
                    if(jQuery('.SidebarApp').is(':visible')){
                        jQuery('body').css({
                            'overflow-x':'hidden',
                            'overflow-y':'hidden'
                        });
                        $(window).scrollTop(0);
                        jQuery('.sidebar ul li:first a').click();
                    } else {
                        jQuery('body').css({
                            'overflow-x':'auto',
                            'overflow-y':'auto'
                        });
                        $(window).scrollTop(0);
                    }
                    return false;
                });

                jQuery(document).on('keyup', function (e) {
                    if (e.keyCode == 27) {
//                            jQuery('.enfoc').removeClass('desfoc');
                        jQuery('.SidebarApp').hide();
                        jQuery('body').css('overflow', 'auto');
                    }
                });
                jQuery('.mask__menu ._close').on('click', function (e) {
                    jQuery('.enfoc').removeClass('desfoc');
                    jQuery('.mask__menu').hide();
                    jQuery('body').css('overflow', 'auto');
                });
            });
        </script>
    </body>
</html>


