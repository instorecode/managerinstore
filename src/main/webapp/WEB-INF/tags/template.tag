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
            <meta name="description" content="">
            <meta name="author" content="">
            <link rel="shortcut icon" href="images/favicon.png">

            <title>Centro de Produtividade da Instore</title>
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
            #head-nav {
                min-width: 1120px;
            }
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
        <!-- Fixed navbar -->
        <div id="head-nav" class="navbar navbar-default navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="fa fa-gear"></span>
                    </button>
                    <a class="navbar-brand" href="#"><span>CPI</span></a>
                </div>
                <div class="navbar-collapse collapse">

                    <ul class="nav navbar-nav navbar-right user-nav">
                        <li><a href="${url}/dashboard" style="color:white;"> Cliente: ${sessionUsuario.cliente.nome} </a></li>
                        <li><a href="javascript:void(0);">Usuário: ${sessionUsuario.usuarioBean.nome}</a></li>
                        <li><a href="${url}/sair" class="btn_sair"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;Encerrar sessão</a></li>
                    </ul>			

                </div><!--/.nav-collapse -->
            </div>
        </div>

        <div id="cl-wrapper" class="fixed-menu">
            <div class="cl-sidebar">
                <div class="cl-toggle"><i class="fa fa-bars"></i></div>
                <div class="cl-navblock">
                    <div class="menu-space">
                        <div class="content">
                            <div class="side-user">
                                <div class="info">
                                    <a href="#">Centro de Processamento da Instore</a>
                                    <span>www.instore.com.br</span>
                                </div>
                            </div>
                            <ul class="cl-vnavigation">
                                <c:forEach items="${perfilBeanList}" var="perfil">
                                    <li>
                                        <a href="#"><i class="${perfil.icone}"></i><span>${perfil.nome}</span></a>
                                        <ul class="sub-menu">
                                            <c:forEach items="${perfil.funcionalidadeBeanList}" var="funcionalidade">
                                                <c:if test="${funcionalidade.visivel eq true }">
                                                    <li class="${funcionalidadeBeanAtual.idfuncionalidade eq funcionalidade.idfuncionalidade ? 'active' : ''}">
                                                        <a href="${url}${funcionalidade.mappingId}">
                                                            <!--<span class="label label-primary pull-right">New</span>--> 
                                                            ${funcionalidade.nome}
                                                        </a>
                                                    </li>
                                                </c:if>
                                            </c:forEach>


                                        </ul>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="text-right collapse-button" style="padding:7px 9px;">
                        <!--<input type="text" class="form-control search" placeholder="Search..." />-->
                        <button id="sidebar-collapse" class="btn btn-default" style=""><i style="color:#fff;" class="fa fa-angle-left"></i></button>
                    </div>
                </div>
            </div>

            <div class="container-fluid" id="pcont">

                <div class="cl-mcont">
                    <div class="row submenu">
                        <div style="margin-left: 15px;"> 
                            <jsp:invoke fragment="submenu"></jsp:invoke>
                        </div>
                    </div>
                    <jsp:doBody />
                </div>

            </div> 

        </div>
    </body>
</html>


