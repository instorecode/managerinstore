<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor" %>
<%@tag description="Template instore" pageEncoding="UTF-8"%>

<c:set var="url" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"></c:set>
<c:set var="url_resources" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/"></c:set>
<c:set var="url_css" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/css/"></c:set>
<c:set var="url_js" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/js/"></c:set>
<c:set var="url_img" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/img/"></c:set>

<compress:html enabled="true" removeComments="true" compressJavaScript="true" yuiJsDisableOptimizations="true">
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Intore</title>

            <link rel="stylesheet" type="text/css" media="all" href="${url_css}jquery.ui.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}bootstrap.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}font.awesome.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}todc-bootstrap.css"/>
            <link rel="stylesheet" type="text/css" media="all" href="${url_css}main.css"/>

            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.min.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.ui.min.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}bootstrap.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}holder.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.validate.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}additional.methods.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.form.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}jquery.mask.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}bootbox.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}main.js"></script>
        </head>
        <body>
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
                    <c:if test="${funcionalidadeBeanList ne null and not empty funcionalidadeBeanList}">
                        <div class="col-md-2">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"> Opções <span class="caret"></span></button>
                                <ul class="dropdown-menu" role="menu">
                                    <c:forEach items="${funcionalidadeBeanList}" var="f">
                                        <li><a href="${f.mappingId}">${f.nome}</a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </div>

                <div class="over">
                    <jsp:doBody />
                    <div style="position: absolute; bottom: 15px; right: 20px;"><i> 2014 © www.instore.com.br </i></div>
                </div>
            </div>
        </body>
    </html>
</compress:html>