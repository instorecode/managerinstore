<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:body>
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
                            <h2 class="page-title">Upload exp</h2>
                            <p class="description">Gerar arquivos de exportação</p>
                        </div>        
                        <div class="mail-nav collapse">
                            <ul class="nav nav-pills nav-stacked ">
                                <li ${tipo_exp eq 'gravadora' ? 'class="active"' :''}>
                                    <a href="${url}/gerarexp?tipo_exp=gravadora">
                                        <span class="label label-primary pull-right">${total_lista_gravadora}</span>
                                        <i class="fa fa-youtube-play"></i> 
                                        Gravadora
                                    </a>
                                </li>
                                
                                <li ${tipo_exp eq 'categoria' ? 'class="active"' :''}>
                                    <a href="${url}/gerarexp?tipo_exp=categoria">
                                        <span class="label label-primary pull-right">${total_lista_categoria}</span>
                                        <i class="fa fa-file-code-o"></i> 
                                        Categoria
                                    </a>
                                </li>
                                
                                <li ${tipo_exp eq 'programacao' ? 'class="active"' :''}>
                                    <a href="${url}/gerarexp?tipo_exp=programacao">
                                        <span class="label label-primary pull-right">${total_lista_programacao}</span>
                                        <i class="fa fa-file-archive-o"></i> 
                                        Programação
                                    </a>
                                </li>
                                
                                <li ${tipo_exp eq 'comercial' ? 'class="active"' :''}>
                                    <a href="${url}/gerarexp?tipo_exp=comercial">
                                        <span class="label label-primary pull-right">${total_lista_comercial}</span>
                                        <i class="fa fa-tag"></i> 
                                        Comercial
                                    </a>
                                </li>
                                
                                <li ${tipo_exp eq 'musica' ? 'class="active"' :''}>
                                    <a href="${url}/gerarexp?tipo_exp=musica">
                                        <span class="label label-primary pull-right">${total_lista_musica}</span>
                                        <i class="fa fa-music"></i> 
                                        Música
                                    </a>
                                </li>
                            </ul>

                            <p class="title">Labels</p>
                            <ul class="nav nav-pills nav-stacked ">
                                <li><a href="#"><span class="label label1 pull-right">0</span> Inbox</a></li>
                                <li><a href="#"><span class="label label2 pull-right">8</span>Sent Mail</a></li>
                                <li><a href="#"><span class="label label3 pull-right">4</span>Important</a></li>
                            </ul>
                            <div class="compose">
                                <a class="btn btn-flat btn-primary">Compose Email</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>		
            <div class="container-fluid" id="pcont">
                <div class="mail-inbox">
                    <div class="head">
                        <h3>Inbox <span>(13 new)</span></h3>
                        <input type="text" class="form-control"  placeholder="Search mail..." />
                    </div>
                    <div class="filters">
                        <input id="check-all" type="checkbox" name="checkall" />
                        <span>Select All</span>
                        <div class="btn-group pull-right">
                            <button class="btn btn-sm btn-flat btn-default" type="button"><i class="fa fa-angle-left"></i></button> 
                            <button class="btn btn-sm btn-flat btn-default" type="button"><i class="fa fa-angle-right"></i></button> 
                        </div>        
                        <div class="btn-group pull-right">
                            <button data-toggle="dropdown" class="btn btn-sm btn-flat btn-default dropdown-toggle" type="button">
                                Order by <span class="caret"></span>
                            </button>
                            <ul role="menu" class="dropdown-menu">
                                <li><a href="#">Date</a></li>
                                <li><a href="#">From</a></li>
                                <li><a href="#">Subject</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Size</a></li>
                            </ul>
                        </div>

                    </div>
                    <div class="mails">
                        <div class="item">
                            <div><input type="checkbox" name="c[]" /> </div>
                            <div>
                                <span class="date pull-right"><i class="fa fa-paperclip"></i> 20 Nov</span>
                                <h4 class="from">Jeff Hanneman</h4>
                                <p class="msg">Urgent - You forgot your keys in the class room, please come imediatly!</p>
                            </div>
                        </div>
                        <div class="item">
                            <div><input type="checkbox" name="c[]" /> </div>
                            <div>
                                <span class="date pull-right"><i class="fa fa-paperclip"></i> 20 Nov</span>
                                <h4 class="from">John Doe</h4>
                                <p class="msg">Urgent - You forgot your keys in the class room, please come imediatly!</p>
                            </div>
                        </div>
                        <div class="item">
                            <div><input type="checkbox" name="c[]" /> </div>
                            <div>
                                <span class="date pull-right"><i class="fa fa-paperclip"></i> 20 Nov</span>
                                <h4 class="from">John Doe</h4>
                                <p class="msg">Urgent - You forgot your keys in the class room, please come imediatly!</p>
                            </div>
                        </div>
                        <div class="item">
                            <div><input type="checkbox" name="c[]" /> </div>
                            <div>
                                <span class="date pull-right"><i class="fa fa-paperclip"></i> 20 Nov</span>
                                <h4 class="from">John Doe</h4>
                                <p class="msg">Urgent - You forgot your keys in the class room, please come imediatly!</p>
                            </div>
                        </div>
                        <div class="item">
                            <div><input type="checkbox" name="c[]" /> </div>
                            <div>
                                <span class="date pull-right"><i class="fa fa-paperclip"></i> 20 Nov</span>
                                <h4 class="from">John Doe</h4>
                                <p class="msg">Urgent - You forgot your keys in the class room, please come imediatly!</p>
                            </div>
                        </div>
                        <div class="item">
                            <div><input type="checkbox" name="c[]" /> </div>
                            <div>
                                <span class="date pull-right"><i class="fa fa-paperclip"></i> 20 Nov</span>
                                <h4 class="from">John Doe</h4>
                                <p class="msg">Urgent - You forgot your keys in the class room, please come imediatly!</p>
                            </div>
                        </div>

                    </div>
                </div>
            </div> 
        </div>
    </jsp:body>
</instore:template>