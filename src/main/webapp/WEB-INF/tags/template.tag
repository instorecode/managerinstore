<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor" %>
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

<compress:html enabled="true" removeComments="true" compressJavaScript="true" jsCompressor="closure" closureOptLevel="simple">
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="utf-8">
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
            <link href="${url_cz}css/style.css?v=${machine_id}" rel="stylesheet" />	
            <link href="${url_css}main.css?v=${machine_id}" rel="stylesheet" />	
            <link rel="stylesheet" type="text/css" href="${url_cz}js/jquery.niftymodals/css/component.css" />
            <!--<link rel="stylesheet" type="text/css" href="${url_cz}js/bootstrap.slider/css/slider.css" />-->

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
            <script type="text/javascript" src="${url_cz}js/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js?v=${machine_id}"></script>
            <script type="text/javascript" src="${url_cz}js/jquery.icheck/icheck.min.js?v=${machine_id}"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}imask.js?v=${machine_id}"></script>
            <script type="text/javascript" src="${url_cz}js/bootstrap.summernote/dist/summernote.min.js?v=${machine_id}"></script>
            <script type="text/javascript" src="${url_cz}js/jquery.gritter/js/jquery.gritter.js?v=${machine_id}"></script>

            <script type="text/javascript" charset="utf-8" src="${url_js}bloodhound.js?v=${machine_id}"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}typeahead.bundle.js?v=${machine_id}"></script>
            <script type="text/javascript" src="${url_cz}js/jquery.niftymodals/js/jquery.modalEffects.js"></script>
            <script src="${url_cz}js/bootstrap.slider/js/bootstrap-slider.js" type="text/javascript"></script>
            <script src="${url_cz}js/fuelux/loader.js" type="text/javascript"></script>

            <script type="text/javascript">
                $(document).ready(function() {
                    App.init();
                    App.wizard();

                    $('.md-trigger').modalEffects();
                    jQuery.storageAdd = function(name, value) {
                        if (typeof(Storage) !== "undefined") {
                            localStorage.setItem(name, value);
                        }
                    };

                    jQuery.storage = function(name) {
                        if (typeof(Storage) !== "undefined") {
                            return localStorage.getItem(name);
                        }
                    };

                    jQuery.storageClear = function() {
                        if (typeof(Storage) !== "undefined") {
                            window.localStorage.clear();
                        }
                    };

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

                    jQuery('.btn_up_cache').on('click', function() {
                        bootbox.confirm("A página será atualizada, tem certeza de que deseja atualizar o cache? ", function(result) {
                            if (result) {
                                jQuery.storageClear();
                                setTimeout(function() {
                                    window.location.reload();
                                }, 1000);
                                return false;
                            }
                        });
                    });
                });
            </script>
            <script type="text/javascript" charset="utf-8" src="${url_js}ws_cache.js?v=${machine_id}"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}main.js?v=${machine_id}"></script>

            <c:if test="${null ne isGrid and isGrid eq true}">
                <style>
                    .bbGrid-grid-nav {
                        margin-top: -1px !important;
                        height: 50px !important;
                    }
                </style>
                <jsp:invoke fragment="gridColumn"></jsp:invoke>
                    <script type="text/javascript" charset="utf-8">
                        jQuery(document).ready(function() {
                            var App = {};

                            var url = document.URL;

                            if (null != url.match(/#$/)) {
                                url = url.substring(0, url.match(/#$/).index);
                            }

                            var urlJSON = url + '?datajson=true';

//                            var array_dados = new Array();
//                            jQuery.ajax({
//                                async: false,
//                                type: 'GET',
//                                url: urlJSON,
//                                success: function(json) {
//                                    jQuery.each(json, function(key, value) {
//                                        array_dados[key] = value;
//                                    });
//                                }
//                            });


                            App._exampleCollection = Backbone.Collection.extend({
                                url: urlJSON
                            });


                            App.companies = new Backbone.Collection;
                            App.clearGridCollection = new Backbone.Collection;
                            App.exampleCollection = new App._exampleCollection();


                            App.exampleCollection.fetch({
                                wait: true,
                                success: function(collection) {
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

                            var gridview = new bbGrid.View({
                                container: jQuery('[datagrid="true"]'),
                                collection: App.clearGridCollection,
                                enableSearch: false,
                                rows: 25,
                                rowList: [5, 25, 50, 100, 250, 500],
                                colModel: gridColumn,
                                onRowClick: function(data) {
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


                                    jQuery('.upd').on('click', function() {
                                        window.location.href = _url_atualizar;
                                    });
                                    jQuery('.trash').on('click', function() {
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
                                        jQuery('[data-' + p + '="true"]').text(data.attributes[p]);
                                    }


                                    jQuery('.xdet').html(jQuery('[datagrid-view="true"]').html() + "<br /><br /><br /><br />");

                                    if (jQuery('.sld_view:hidden')) {
                                        jQuery('.sld_view').show('slow');
                                    }
                                    jQuery('.xclose').on('click', function() {
                                        jQuery('.sld_view').hide('slow');
                                    });

                                },
                                onRowDblClick: function(data) {
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
        </head>

        <body>
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
                                <li class="active"><a href="${url}">Dashboard</a></li>
                                <li><a href="#ajuda">Ajuda</a></li>
                                <li><a href="#contato">Contato</a></li>
                                <li><a class="btn_up_cache" href="#"> <i class="fa fa-refresh"></i>&nbsp;&nbsp;Atualizar Cache da aplicação</a></li>
                                <li><a class="btn_sair" href="${url}/sair">Sair</a></li>
                            </ul>
                        </div>
                    </div><!--/.nav-collapse -->
                </div>
            </div>


            <div id="cl-wrapper" class="fixed-menu  ${menucolapse ? "sb-collapsed" : ""}">
                <div class="cl-sidebar">
                    <div class="cl-toggle"><i class="fa fa-bars"></i></div>
                    <div class="cl-navblock">
                        <div class="menu-space">
                            <div class="content">
                                <div class="side-user">
                                    <div class="avatar"><img src="${url_cz}/images/logo.png" alt="Avatar" style="max-width: 50px;" /></div>
                                    <div class="info">
                                        <center>
                                            <a href="#">${sessionUsuario.usuarioBean.nome}<img src="${url_cz}images/state_online.png" alt="Status" /> <span>Online</span> </a>
                                        </center>
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
                </div>
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

                <div class="container-fluid" id="pcont">
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
                    <div class="cl-mcont">

                        <jsp:doBody />
                    </div>
                </div> 

            </div>
        </body>
    </html>
</compress:html>