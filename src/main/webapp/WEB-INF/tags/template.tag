<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor" %>
<%@tag description="Template instore" pageEncoding="UTF-8"%>
<%@attribute fragment="true" name="gridColumn" %>
<%@attribute fragment="false" name="isGrid" %>
<%@attribute fragment="true" name="submenu" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 

<c:set scope="session" var="url" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"></c:set>
<c:set scope="session" var="url_resources" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/"></c:set>
<c:set scope="session" var="url_css" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/css/"></c:set>
<c:set scope="session" var="url_js" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/js/"></c:set>
<c:set scope="session" var="url_img" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/img/"></c:set>

<compress:html enabled="true" removeComments="true" compressJavaScript="true" jsCompressor="closure" closureOptLevel="simple">
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Intore</title>

            <link rel="stylesheet" type="text/css" media="all" href="${url_css}jquery.ui.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}bootstrap.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}font.awesome.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}todc-bootstrap.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}bbGrid.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}clockface.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}bootstrap.multiselect.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}prettify.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}main.css"/>

            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.min.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.ui.min.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}watch.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}underscore.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}backbone.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}bootstrap.js"></script> 
            <script type="text/javascript" charset="utf-8" src="${url_js}bootstrap.multiselect.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}bbGrid.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}holder.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.validate.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}additional.methods.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.form.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.mask.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}maskmoney.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}bootbox.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}clockface.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}typeahead.bundle.js"></script>
            
            <script type="text/javascript" charset="utf-8" src="${url_js}prettify.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}main.js"></script>

            <c:if test="${null ne isGrid and isGrid eq true}">
                <jsp:invoke fragment="gridColumn"></jsp:invoke>
                <script type="text/javascript" charset="utf-8">
                    jQuery(document).ready(function() {
                        
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
                        // filters example
                        App.FiltersExampleGrid = new bbGrid.View({
                            container: $('#bbGrid-filters'),
                            rows: 5,
                            rowList: [5, 25, 50, 100],
                            collection: App.exampleCollection,
                            colModel: [{title: 'ID', name: 'id', index: true, sorttype: 'number'},
                                {title: 'Full Name', index: true, name: 'name', filter: true, filterType: 'input'},
                                {title: 'Company', index: true, name: 'company', filter: true},
                                {title: 'Email', index: true, name: 'email'}
                            ]
                        });

                        App.SearchExampleGrid = new bbGrid.View({
                            container: $('#bbGrid-search'),
                            rows: 5,
                            rowList: [5, 25, 50, 100],
                            collection: App.exampleCollection,
                            colModel: [{title: 'ID', name: 'id', index: true, sorttype: 'number'},
                                {title: 'Full Name', index: true, name: 'name'},
                                {title: 'Company', index: true, name: 'company'},
                                {title: 'Email', index: true, name: 'email'}],
                            enableSearch: true,
                            onReady: function() {
                                $('a', this.$el).removeAttr('href');
                            }
                        });

                        App.exampleCollection.fetch({wait: true,
                            success: function(collection) {
                                App.ClearExampleGrid = new bbGrid.View({
                                    container: $('#bbGrid-clear'),
                                    collection: App.clearGridCollection,
                                    colModel: [{title: 'ID', name: 'id'},
                                        {title: 'Full Name', name: 'name'},
                                        {title: 'Company', name: 'company'},
                                        {title: 'Email', name: 'email'}]
                                });
                                App.ButtonsExampleGrid = new bbGrid.View({
                                    container: $('#bbGrid-buttons'),
                                    collection: App.clearGridCollection,
                                    colModel: [{title: 'ID', name: 'id'},
                                        {title: 'Full Name', name: 'name'},
                                        {title: 'Company', name: 'company'},
                                        {title: 'Email', name: 'email'}],
                                    buttons: [{
                                            title: 'Show selected',
                                            onClick: function() {
                                                var models = this.view.getSelectedModels();
                                                if (!_.isEmpty(models))
                                                    alert(_.first(models).get('name'));
                                                else
                                                    alert('Nothing');

                                            }
                                        }]
                                });
                                App.clearGridCollection.reset(collection.models.slice(0, 10000));

                                App.SubgridExapmleGrid = new bbGrid.View({
                                    container: $('#bbGrid-subgrid'),
                                    rows: 5,
                                    rowList: [25, 50, 100],
                                    collection: App.companies,
                                    subgrid: true,
                                    subgridAccordion: true,
                                    colModel: [{title: 'Company', index: true, name: 'company'}],
                                    onRowExpanded: function($el, rowid) {
                                        var subgridCollection = new Backbone.Collection();
                                        var subgrid = new bbGrid.View({
                                            container: $el,
                                            rows: 10,
                                            //                        multiselect: true,
                                            collection: subgridCollection,
                                            colModel: [{title: 'Full Name', index: true, name: 'name'},
                                                {title: 'Age', name: 'age', index: true, sorttype: 'number'},
                                                {title: 'Address', index: true, name: 'address'},
                                                {title: 'Email', index: true, name: 'email'}
                                            ]
                                        });
                                        subgridCollection.reset(collection.where({'company': App.companies.at(rowid).get('company')}));
                                    }
                                });
                                App.companies.reset(_.map(_.uniq(collection.pluck('company')), function(val, index) {
                                    return {
                                        'id': index,
                                        'company': val
                                    };
                                }));
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
                            enableSearch: true,
                            rows: 25,
                            rowList: [5, 25, 50, 100, 250, 500],
                            colModel: gridColumn,
                            onRowClick: function(data) {
                                if (null != onRowClick && undefined != onRowClick) {
                                    onRowClick(data.attributes);
                                }
                                
                            },
                            onRowDblClick: function(data) {
                                var _url = url;
                               
                                if (null != _url.match(/\/$/)) {
                                    var _url = url.substring(0, url.match(/\/$/).index);
                                }
                                if (null != _url.match(/s$/)) {
                                    var _url = url.substring(0, url.match(/s$/).index);
                                }
                                
                                _url_atualizar =  _url + '/atualizar/' + data.attributes[jQuery('[datagrid="true"]').data('id')];
                                _url_remover =  _url + '/remover/' + data.attributes[jQuery('[datagrid="true"]').data('id')];
                                
                                for (var p in data.attributes) {
                                    var txt = ''; 
                                    var sep = '';
                                    var txt = jQuery('[data-' + p + '="true"]').text() + ' ';
                                    jQuery('[data-' + p + '="true"]').text(data.attributes[p]); 
                                }

                                if (null != onRowDblClick && undefined != onRowDblClick) {
                                    onRowDblClick(data.attributes);
                                }
                                
                                bootbox.dialog({
                                    message: jQuery('[datagrid-view="true"]').html(),
                                    title: "Informações detalhadas",
                                    buttons: {
                                        atualizar: {
                                            label: "<i class=\"fa fa-refresh\">&nbsp;&nbsp;Atualizar</i>",
                                            className: "btn-success",
                                            callback: function() {
                                                window.location.href = _url_atualizar;
                                            }
                                        },
                                        remover: {
                                            label: "<i class=\"fa fa-trash-o\">&nbsp;&nbsp;Remover</i>",
                                            className: "btn-danger",
                                            callback: function() {
                                                window.location.href = _url_remover;
                                            }
                                        },
                                        fechar: {
                                            label: "<i class=\"fa fa-times\">&nbsp;&nbsp;Fechar</i>",
                                            className: "btn-default",
                                            callback: function() {
                                            }
                                        },
                                    }
                                });

                            },
                        });
                    });
                </script>
            </c:if>
                 
            </head>
            <body>
                <instore:noscript></instore:noscript>
                <instore:nocookie></instore:nocookie>
                <div class="yesscript">
                    <div class="browserRec" style="display: none;"> Opa!! Você está usando o Internet Explorer, recomendamos que use o CHROME para uma melhor performance do sistema!!!</div>
                    <div class="menuleft">
                            <ul class="list-group">
                                <li class="list-group-header"> <i class="fa fa-bars"></i>&nbsp;&nbsp;&nbsp;Menu</li>
                                <li class="divider"></li>
                                ${menu} 
                            <li class="divider"></li> 
                            <li class="list-group-item"><a class="btn_sair" href="${url}/sair"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;&nbsp;Sair</a></li>
                        </ul>
                    </div>

                    <div class="content">
                        <h4><i class="fa ${currentFuncionalidadeBean.icone}"></i> Instore <small> ${currentFuncionalidadeBean.nome}</small></h4>
                        <hr style="margin-top: -1px;" />
                        <div class="row submenu">
                            <div style="margin-left: 15px;"> 
                                <jsp:invoke fragment="submenu"></jsp:invoke>
                            </div>
                        </div>

                        <div class="over">
                            <br />
                            <div style="margin-right: 10px;">
                            <br />    
                            <jsp:doBody />
                                <br />
                                <br />
                                <br />
                            </div>
                            <div style="position: absolute; bottom: 15px; right: 20px;"><i> 2014 © www.instore.com.br </i></div>
                        </div>
                    </div>
                </div>
                
        </body>
    </html>
</compress:html>