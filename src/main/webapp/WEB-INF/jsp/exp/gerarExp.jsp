<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:body>
        <script type="text/javascript">
            jQuery(document).ready(function() {
                console.log('123');
                jQuery('.btn_up').on('click', function() {

                });
            });
        </script>
        <c:if test="${null eq idcliente}">
            <div class="row">
                <c:forEach items="${clienteBeanList}" var="item">
                    <div class="col-md-3">
                        <a href="?idcliente=${item.idcliente}">
                            <div class="block-flat">
                                <div class="content no-padding">
                                    <div class="overflow-hidden">
                                        <i class="fa fa-building fa-4x pull-left color-primary"></i> 
                                        <h3 class="no-margin">${item.nome}</h3>
                                        <p class="color-primary">Cod. Interno ${item.codigoInterno}</p>
                                    </div>
                                    <!--<h1 class="no-margin big-text">Cod.</h1>-->							
                                </div>
                            </div>
                        </a>	
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${null ne idcliente}">
            <div class="fixed-menu sb-collapsed" id="cl-wrapper" style="margin-top: -150px; margin-left: -30px;">
                <div class="cl-sidebar" style="width: 4px;">
                    <div class="cl-toggle"><i class="fa fa-bars"></i></div>
                    <div class="cl-navblock">
                        <div class="menu-space" style="width: 4px !important;">

                        </div>
                    </div>
                </div>
                <div class="page-aside email">
                    <div class="fixed nano nscroller" style="width: 296px;">
                        <div class="content">
                            <div class="header">
                                <button class="navbar-toggle" data-target=".mail-nav" data-toggle="collapse" type="button">
                                    <span class="fa fa-chevron-down"></span>
                                </button>          
                                <h2 class="page-title">Upload exp </h2>
                                <p class="description">Gerar arquivos de exportação</p>
                            </div>        
                            <div class="mail-nav collapse">
                                <ul class="nav nav-pills nav-stacked ">
                                    <li ${tipo_exp eq 'gravadora' ? 'class="active"' :''}>
                                        <a href="?idcliente=${idcliente}&tipo_exp=gravadora">
                                            <span class="label label-primary pull-right">${total_lista_gravadora}</span>
                                            <i class="fa fa-youtube-play"></i> 
                                            Gravadora
                                        </a>
                                    </li>

                                    <li ${tipo_exp eq 'categoria' ? 'class="active"' :''}>
                                        <a href="?idcliente=${idcliente}&tipo_exp=categoria">
                                            <span class="label label-primary pull-right">${total_lista_categoria}</span>
                                            <i class="fa fa-file-code-o"></i> 
                                            Categoria
                                        </a>
                                    </li>

                                    <li ${tipo_exp eq 'programacao' ? 'class="active"' :''}>
                                        <a href="?idcliente=${idcliente}&tipo_exp=programacao">
                                            <span class="label label-primary pull-right">${total_lista_programacao}</span>
                                            <i class="fa fa-file-archive-o"></i> 
                                            Programação
                                        </a>
                                    </li>

                                    <li ${tipo_exp eq 'comercial' ? 'class="active"' :''}>
                                        <a href="?idcliente=${idcliente}&tipo_exp=comercial">
                                            <span class="label label-primary pull-right">${total_lista_comercial}</span>
                                            <i class="fa fa-tag"></i> 
                                            Comercial
                                        </a>
                                    </li>

                                    <li ${tipo_exp eq 'musica' ? 'class="active"' :''}>
                                        <a href="?idcliente=${idcliente}&tipo_exp=musica">
                                            <span class="label label-primary pull-right">${total_lista_musica}</span>
                                            <i class="fa fa-music"></i> 
                                            Música
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>		
                <div class="container-fluid" id="pcont">
                    <div class="mail-inbox">
                        <div class="head">
                            <div class="input-group">
                                <input type="text" class="form-control" ${tipo_exp eq 'gravadora' ? 'disabled="disabled"' :''} >
                                <span class="input-group-btn">
                                    <button class="btn btn-primary" type="button">Filtrar</button>
                                </span>
                            </div>

                        </div>

                        <div class="filters">
                            <span>Descrição</span>
                            <div class="btn-group pull-right" ${tipo_exp eq 'gravadora' ? 'style="display: none;"' :''}> 
                                <button class="btn btn-sm btn-flat btn-default" type="button"><i class="fa fa-angle-left"></i></button> 
                                <button class="btn btn-sm btn-flat btn-default" type="button"><i class="fa fa-angle-right"></i></button> 
                            </div>        
                            <div class="btn-group pull-right" ${tipo_exp eq 'gravadora' ? 'style="display: none;"' :''}>
                                <button data-toggle="dropdown" class="btn btn-sm btn-flat btn-default dropdown-toggle" type="button">
                                    Ordenar por <span class="caret"></span>
                                </button>
                                <ul role="menu" class="dropdown-menu">
                                    <c:forEach items="${orderByList}" var="item_order">
                                        <li><a href="${item_order}">${item_order}</a></li>
                                        </c:forEach>
                                </ul>
                            </div>

                        </div>
                        <div class="mails">
                            <c:forEach items="${lista}" var="item_lista">
                                <div class="item">
                                    <div>
                                        <button class="btn btn-default btn-flat pull-right btn_up" data-tipo_exp="${tipo_exp}" data-id="${tipo_exp eq 'gravadora' ? item_lista.id :''}">
                                            <i class="fa fa-upload"></i>
                                        </button>

                                        <h4 class="from">
                                            <c:if test="${tipo_exp eq 'gravadora'}">
                                                Gravadora
                                            </c:if>
                                        </h4> 
                                        <p class="msg">
                                            <c:if test="${tipo_exp eq 'gravadora'}">
                                                ${item_lista.nome}
                                            </c:if>
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div> 
            </div>

        </c:if>


    </jsp:body>
</instore:template>