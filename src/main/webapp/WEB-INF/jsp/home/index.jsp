<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<c:set scope="session" var="url" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"></c:set>
<c:set scope="session" var="url_resources" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/"></c:set>
<c:set scope="session" var="url_css" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/css/"></c:set>
<c:set scope="session" var="url_js" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/js/"></c:set>
<c:set scope="session" var="url_img" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/img/"></c:set>

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
        <body class="login">
            <style type="text/css">
                body { 
                    background: url('${url_img}bg-login.jpg')  no-repeat center center fixed;
                    background-size: 100% 100%;
                }
            </style>
            <instore:noscript></instore:noscript>
            <instore:nocookie></instore:nocookie>
                <div class="yesscript">
                    <div class="browserRec" style="display: none;"> Opa!! Você está usando o Internet Explorer, recomendamos que use o CHROME para uma melhor performance do sistema!!!</div>
                    <div class="align">
                        <form id="login" method="POST" data-form="true" data-success-url="${url}/dashboard">
                        <div class="panel panel-default">
                            <div class="panel-body">

                                <center>
                                    <img src="${url_img}logo.png" style="max-width: 178px;" />
                                </center>

                                <br />
                                <br />


                                <div class="form-group">
                                    <input type="text" name="email" class="form-control" placeholder="Enter email"  data-rule-required="true" data-rule-email="true" value="">
                                </div>

                                <div class="form-group">
                                    <input type="password" name="senha" class="form-control" placeholder="Senha"  data-rule-required="true" value="">
                                </div>
                            </div>
                            <div class="panel-footer">
                                <button type="submit" class="btn btn-default">
                                    <i class="fa fa-sign-in"></i> LogIn
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </body>
    </html>
</compress:html>