<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<c:set scope="session" var="url" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"></c:set>
<c:set scope="session" var="url_resources" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/"></c:set>
<c:set scope="session" var="url_css" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/css/"></c:set>
<c:set scope="session" var="url_js" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/js/"></c:set>
<c:set scope="session" var="url_img" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/img/"></c:set>
<c:set scope="session" var="url_cz" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/cz/"></c:set>

<compress:html enabled="true" removeComments="true" compressJavaScript="true" yuiJsDisableOptimizations="true">
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <title>ManagerInstore</title>

            <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,400italic,700,800' rel='stylesheet' type='text/css'>
            <link href='http://fonts.googleapis.com/css?family=Raleway:300,200,100' rel='stylesheet' type='text/css'>
            <link href="${url_cz}js/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
            <link rel="stylesheet" href="${url_cz}fonts/font-awesome-4/css/font-awesome.min.css">
            <link href="${url_cz}css/style.css" rel="stylesheet" />	
            <link rel="stylesheet" type="text/css" href="${url_cz}js/jquery.gritter/css/jquery.gritter.css" />

            <script src="${url_js}jquery.min.js"></script>
            <script src="${url_js}jquery.ui.min.js"></script>
            <script type="text/javascript" src="${url_cz}js/behaviour/general.js"></script>
            <script src="${url_cz}js/behaviour/voice-commands.js"></script>
            <script src="${url_cz}js/bootstrap/dist/js/bootstrap.min.js"></script>
            <script type="text/javascript" src="${url_cz}js/jquery.flot/jquery.flot.js"></script>
            <script type="text/javascript" src="${url_cz}js/jquery.flot/jquery.flot.pie.js"></script>
            <script type="text/javascript" src="${url_cz}js/jquery.flot/jquery.flot.resize.js"></script>
            <script type="text/javascript" src="${url_cz}js/jquery.flot/jquery.flot.labels.js"></script>

            <script type="text/javascript" src="${url_cz}js/jquery.gritter/js/jquery.gritter.js"></script>
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
            <script type="text/javascript" charset="utf-8" src="${url_js}imask.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}bootbox.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}clockface.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}typeahead.bundle.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}prettify.js"></script>
            <script type="text/javascript" charset="utf-8" src="${url_js}main.js"></script>

        </head>
        <body class="texture">

            <div id="cl-wrapper" class="error-container">
                <div class="page-error">
                <h1 class="number text-center">500</h1>
                <h2 class="description text-center">Não se preocupe, há um pouco de turbulência!</h2>
                <h3 class="text-center"> Estamos tentando corrigi-lo, por favor, tente novamente mais tarde.</h3>
            </div>
                <div class="text-center copy">&copy; 2014 <a href="http://www.instore.com.br">www.instore.com.br</a></div>
            </div>
        </body>
    </html>
</compress:html>