<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<instore:template  menucolapse="false" isGrid="false">
    <jsp:attribute name="submenu">
        <div class="btn-group">
            <a href="${url}/musica/cadastrar" class="btn btn-default btn-flat"> <i class="fa fa-save"></i> Cadastrar </a>
            <!--<a href="#" class="btn btn-default btn-flat" data-toggle="modal" data-target="#modal_sincronizacao" > <i class="fa fa-download"></i></a>-->
        </div>
    </jsp:attribute>

    <jsp:attribute name="gridColumn">
        <script type="text/javascript">
            var gridColumn = [
                {title: 'Categorias', name: 'categoriaGeral', index: true, filter: true, filterType: 'input'},
                {title: 'Título', name: 'titulo', index: true, filter: true, filterType: 'input'},
                {title: 'Interprete', name: 'interprete', index: true, filter: true, filterType: 'input'},
                {title: 'Velocidade em BPM', name: 'bpm', index: true, filter: true, filterType: 'input'},
                {title: 'Ano de gravação', name: 'anoGravacao', index: true, filter: true},
                {title: 'Letra', name: 'letra', index: true, filter: true, filterType: 'input'},
            ];

            function onRowDblClick(data) {

            }

            function onRowClick(data) {

            }

            jQuery(document).ready(function() {
                jQuery('[name="url"]').on('keyup', function() {
                    jQuery('.erro2').hide();

                    if ('' != jQuery.trim(jQuery(this).val())) {
                        jQuery('.btn_sinc').attr('disabled', false);
                        jQuery('.erro1').hide();
                    } else {
                        jQuery('.btn_sinc').attr('disabled', true);
                        jQuery('.erro1').show();
                    }
                });

                jQuery('.btn_sinc').on('click', function() {
                    var url = jQuery.trim(jQuery('[name="url"]').val());

                    if ('' == url) {
                        jQuery('.erro1').show();
                    } else {
                        jQuery('.erro1').hide();
                        console.log("enviando");
                        jQuery.ajax({
                            type: 'POST',
                            url: '${url}/musica/sinc',
                            data: {dir: url},
                            beforeSend: function() {
                                jQuery('.icone1').hide();
                                jQuery('.icone2').show();
                                jQuery('.btn_sinc_fechar').attr('disabled', true);
                                jQuery('.btn_sinc_fechar').text('Aguarde...');

                                jQuery('.btn_sinc').hide();
                                jQuery('.btn_sinc').attr('disabled', true);
                            },
                            success: function(response) {
                                jQuery('.icone1').show();
                                jQuery('.icone2').hide();
                                console.log(response);
                                if (!response.success) {
                                    jQuery('.texto').text(response.response);
                                    jQuery('.erro2').show();

                                    jQuery('.btn_sinc').show();
                                    jQuery('.btn_sinc').attr('disabled', false);
                                } else {
                                    jQuery('.erro2').hide();
                                    jQuery('.texto').text(response.response);
                                    jQuery('.erro2').show();

                                    jQuery('.btn_sinc').hide();
                                    jQuery('.btn_sinc_fechar').attr('disabled', false);
                                    jQuery('.btn_sinc_fechar').text('Fechar e atualizar a página');
                                }
                            },
                            error: function(repsonse) {
                                jQuery('.icone1').show();
                                jQuery('.icone2').hide();
                                console.log(repsonse);
                            }
                        });
                    }
                });

                jQuery('.btn_sinc_fechar').on('click', function() {
                    jQuery.storageClear();
                    window.location.reload();
                });
            });
        </script>
    </jsp:attribute>

    <jsp:body>  

        <script type="text/javascript">
//            var gridColumn = [
//                {title: 'Categorias', name: 'categoriaGeral', index: true, filter: true, filterType: 'input'},
//                {title: 'Título', name: 'titulo', index: true, filter: true, filterType: 'input'},
//                {title: 'Interprete', name: 'interprete', index: true, filter: true, filterType: 'input'},
//                {title: 'Velocidade em BPM', name: 'bpm', index: true, filter: true, filterType: 'input'},
//                {title: 'Ano de gravação', name: 'anoGravacao', index: true, filter: true},
//                {title: 'Letra', name: 'letra', index: true, filter: true, filterType: 'input'},
//            ];
//
//            function onRowDblClick(data) {
//
//            }
//
//            function onRowClick(data) {
//
//            }

            jQuery(document).ready(function() {
                jQuery('[name="url"]').on('keyup', function() {
                    jQuery('.erro2').hide();

                    if ('' != jQuery.trim(jQuery(this).val())) {
                        jQuery('.btn_sinc').attr('disabled', false);
                        jQuery('.erro1').hide();
                    } else {
                        jQuery('.btn_sinc').attr('disabled', true);
                        jQuery('.erro1').show();
                    }
                });

                jQuery('.btn_sinc').on('click', function() {
                    var url = jQuery.trim(jQuery('[name="url"]').val());

                    if ('' == url) {
                        jQuery('.erro1').show();
                    } else {
                        jQuery('.erro1').hide();
                        console.log("enviando");
                        jQuery.ajax({
                            type: 'POST',
                            url: '${url}/musica/sinc',
                            data: {dir: url},
                            beforeSend: function() {
                                jQuery('.icone1').hide();
                                jQuery('.icone2').show();
                                jQuery('.btn_sinc_fechar').attr('disabled', true);
                                jQuery('.btn_sinc_fechar').text('Aguarde...');

                                jQuery('.btn_sinc').hide();
                                jQuery('.btn_sinc').attr('disabled', true);
                            },
                            success: function(response) {
                                jQuery('.icone1').show();
                                jQuery('.icone2').hide();
                                console.log(response);
                                if (!response.success) {
                                    jQuery('.texto').text(response.response);
                                    jQuery('.erro2').show();

                                    jQuery('.btn_sinc').show();
                                    jQuery('.btn_sinc').attr('disabled', false);
                                } else {
                                    jQuery('.erro2').hide();
                                    jQuery('.texto').text(response.response);
                                    jQuery('.erro2').show();

                                    jQuery('.btn_sinc').hide();
                                    jQuery('.btn_sinc_fechar').attr('disabled', false);
                                    jQuery('.btn_sinc_fechar').text('Fechar e atualizar a página');
                                }
                            },
                            error: function(repsonse) {
                                jQuery('.icone1').show();
                                jQuery('.icone2').hide();
                                console.log(repsonse);
                            }
                        });
                    }
                });

                jQuery('.btn_sinc_fechar').on('click', function() {
                    jQuery.storageClear();
                    window.location.reload();
                });
            });
        </script>

        <link rel="stylesheet" type="text/css" href="${url_css}bootstrap-tagsinput.css" />
        <script src="${url_js}bootstrap-tagsinput.js" type="text/javascript"></script>
        <!--<script src="${url_cz}js/fuelux/loader.js" type="text/javascript"></script>-->

        <div class="modal fade" id="modal_sincronizacao" tabindex="-1" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <!--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                    </div>
                    <div class="modal-body">
                        <div class="text-center">
                            <div class="i-circle success icone1"><i class="fa fa-download"></i></div>
                            <div class="i-circle success icone2" style="display: none;"> <img src="${url_img}25.GIF" /> </div>
                            <h4>Sincronização</h4>
                            <p>Escolha um diretório para ser importado para a base de dados automaticamente!</p>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Usuário</label>
                                    <div class="input-group">
                                        <span class="input-group-addon"> <i class="fa fa-user"></i> </span>
                                        <input type="text" name="usuario" class="form-control" placeholder="">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Senha</label>
                                    <div class="input-group"> 
                                        <span class="input-group-addon"> <i class="fa fa-key"></i> </span>
                                        <input type="password" name="senha" class="form-control" placeholder="">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Caminho do diretório</label>
                                    <div class="input-group">
                                        <span class="input-group-addon">smb://</span>
                                        <input type="text" name="url" class="form-control" placeholder="Exemplo: \\ftp\Audio\Musicas\Mp3">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="alert alert-success erro1" style="display: none;">
                            <i class="fa fa-warning sign"></i><strong>Importante:</strong> Informe o caminho do diretório.
                        </div>

                        <div class="alert alert-success erro2" style="display: none;">
                            <i class="fa fa-warning sign"></i><strong>Importante:</strong> <div class="texto"></div>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default btn_sinc_fechar" data-dismiss="modal"> <i class="fa fa-times"></i> Fechar Janela</button>
                        <button type="button" class="btn btn-success btn_sinc" disabled="disabled"> <i class="fa fa-download"></i> Sincronizar</button>
                    </div>
                </div>
            </div>
        </div>


        <c:set scope="session" var="paginacao" value=""></c:set>
        <c:set scope="session" var="paginacao_concat" value="?"></c:set>
        <c:forEach var="item" items="${paramValues}">
            <c:if test="${not fn:startsWith(item.key, 'pagina')}"> 
                <c:set scope="session" var="paginacao" value="${paginacao}${paginacao_concat}${item.key}=${item.value[0]}"></c:set>
                <c:set scope="session" var="paginacao_concat" value="&"></c:set>
            </c:if>
        </c:forEach>

        <c:set scope="session" var="qtd" value=""></c:set>
        <c:set scope="session" var="qtd_concat" value="?"></c:set>
        <c:forEach var="item" items="${paramValues}">
            <c:if test="${not fn:startsWith(item.key, 'qtd') and not fn:startsWith(item.key, 'pagina')}"> 
                <c:set scope="session" var="qtd" value="${qtd}${qtd_concat}${item.key}=${item.value[0]}"></c:set>
                <c:set scope="session" var="qtd_concat" value="&"></c:set>
            </c:if>
        </c:forEach>

        <c:set scope="session" var="__order" value=""></c:set>
        <c:set scope="session" var="__order_concat" value="?"></c:set>
        <c:forEach var="item" items="${paramValues}">
            <c:if test="${not fn:startsWith(item.key, 'order')}"> 
                <c:set scope="session" var="__order" value="${__order}${__order_concat}${item.key}=${item.value[0]}"></c:set>
                <c:set scope="session" var="__order_concat" value="&"></c:set>
            </c:if>
        </c:forEach>

        <form>
            <!--<input type="hidden" name="qtd" value="${qtd}" />-->

            <div class="block-flat">
                <style>
                    .play, .pause, .stop, .mute, .unmute {
                        display: inline-block;
                        padding: 10px;
                        width: 30px;
                        height: 30px;

                        background-color: #54A754;
                        border-radius: 2px;
                    }

                    .play i, .pause i, .stop i, .mute i, .unmute i {
                        font-size: 10px;
                        font-size: 15px;
                        text-align: center;
                        margin-left: 6px;
                        margin-top: 3px;
                        color: #FFF;
                    }
                </style>
                <c:forEach items="${lista2}" var="item" varStatus="vs">
                    <div id="jquery_jplayer_${vs.index}" class="jp-jplayer"></div>
                    <script type="text/javascript">
            $(document).ready(function() {
                jQuery('#play${vs.index}').on('click', function() {
                    //".btn_letra"    
                    jQuery(this).next().next().next().next().next().next().click();

                    $("#jquery_jplayer_${vs.index}").jPlayer({
                        ready: function() {
                            $(this).jPlayer("setMedia", {
                                mp3: "${url}/musica/stream/${item.id}",
                            }).jPlayer("play");
                            ;
                        },
                        swfPath: "/js",
                        supplied: "mp3",
                        cssSelectorAncestor: "",
                        cssSelector: {
                            play: "#play${vs.index}",
                            pause: "#pause${vs.index}",
                            stop: "#stop${vs.index}",
                            mute: "#mute${vs.index}",
                            unmute: "#unmute${vs.index}",
                            currentTime: "#currentTime${vs.index}",
                            duration: "#duration${vs.index}"
                        },
                        size: {
                            width: "0px",
                            height: "0px"
                        }
                    }).jPlayer('play');

                    return false;
                });
            });
                    </script>
                </c:forEach>

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Titulo</label>
                            <input type="text" class="form-control arq" name="titulo" value="${titulo}" placeholder="Título" data-rule-required="true">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Interprete</label>
                            <input type="text" class="form-control arq" name="interprete" value="${interprete}" placeholder="Interprete" data-rule-required="true">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Velocidade</label>
                            <input type="text" class="form-control arq" name="velocidade" value="${velocidade}" placeholder="Velocidade" data-rule-required="true">
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Ano de gravação</label>
                            <input type="text" class="form-control arq" name="anoGravacao" value="${anoGravacao}" placeholder="Ano de gravação" data-rule-required="true">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Letra</label>
                            <input type="text" class="form-control arq" name="letra" value="${letra}" placeholder="Letra" data-rule-required="true">
                        </div>
                    </div>

                    <div class="col-md-9">
                        <div class="form-group">
                            <label>Categoria</label>
                            <input type="text" class="form-control arq" name="categoria" value="${categoria}" placeholder="Categoras" data-rule-required="true" data-role="tagsinput"> 

                            <script type="text/javascript">
                                var json = [ <c:forEach items="${categorias}" var="item" varStatus="vs"> '${item.nome}', </c:forEach> ];
                                        jQuery('[data-role="tagsinput"]').tagsinput({
                                    typeahead: {
                                        source: json
                                    }
                                });

                                $('input').on('itemAdded', function(event) {

                                });
                                </script>
                            </div>
                        </div>
                    </div>

                    <hr />

                    <div class="clearfix"></div>

                    <div style="float: right">
                        <div class="btn-group">
                            <a href="${url}/musica${paginacao}${paginacao_concat}pagina=${paginaAtual-1}" class="btn btn-default btn-flat" ${(paginaAtual-1) < 1 ? 'disabled="disabled"':''}> 
                            <i class="fa fa-arrow-left"></i>
                        </a>
                        <a href="javascript:void(0)" class="btn btn-default btn-flat"> 
                            Página ${paginaAtual} de ${totalPaginas}
                        </a>
                        <a href="${url}/musica${paginacao}${paginacao_concat}pagina=${paginaAtual+1}" class="btn btn-default btn-flat" ${(paginaAtual+1) > totalPaginas ? 'disabled="disabled"':''}>
                            <i class="fa fa-arrow-right"></i>
                        </a>
                    </div>
                </div>	

                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle btn-flat" data-toggle="dropdown">
                        ${totalRegistrosPorPagina} registros <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${url}/musica${qtd}${qtd_concat}qtd=5">5 registros</a></li>
                        <li><a href="${url}/musica${qtd}${qtd_concat}qtd=10">10 registros</a></li>
                        <li><a href="${url}/musica${qtd}${qtd_concat}qtd=25">25 registros</a></li>
                        <li><a href="${url}/musica${qtd}${qtd_concat}qtd=50">50 registros</a></li>
                        <li><a href="${url}/musica${qtd}${qtd_concat}qtd=100">100 registros</a></li>
                        <li><a href="${url}/musica${qtd}${qtd_concat}qtd=250">250registros</a></li>
                    </ul>
                </div>

                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle btn-flat" data-toggle="dropdown">
                        Ordenação <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
<!--                        <li><a href="${url}/musica${__order}${__order_concat}order=1">por categoria ascendente</a></li>
                        <li><a href="${url}/musica${__order}${__order_concat}order=2">por categoria descendente</a></li>-->
                        <li><a href="${url}/musica${__order}${__order_concat}order=3">por título ascendente</a></li>
                        <li><a href="${url}/musica${__order}${__order_concat}order=4">por título descendente</a></li>
                        <li><a href="${url}/musica${__order}${__order_concat}order=5">por interprete ascendente</a></li>
                        <li><a href="${url}/musica${__order}${__order_concat}order=6">por interprete descendente</a></li>
                        <li><a href="${url}/musica${__order}${__order_concat}order=7">por velocidade ascendente</a></li>
                        <li><a href="${url}/musica${__order}${__order_concat}order=8">por velocidade descendente</a></li>
<!--                        <li><a href="${url}/musica${__order}${__order_concat}order=9">por ano de gravação ascendente</a></li>
                        <li><a href="${url}/musica${__order}${__order_concat}order=10">por ano de gravação descendente</a></li>-->
                        <li><a href="${url}/musica${__order}${__order_concat}order=11">por letra ascendente</a></li>
                        <li><a href="${url}/musica${__order}${__order_concat}order=12">por letra descendente</a></li>
                    </ul>
                </div>

                <button type="submit" class="btn btn-default btn-flat"> Filtrar </button>
                <a href="${url}/musica" class="btn btn-default btn-flat"> Limpar Filtro </a>
                <a href="${url}/musica/cadastrar" class="btn btn-default btn-flat btn_exp_arquivo" style="display: none;">Criar musicas audiostore</a>

                <div class="table-responsive">
                    <table class="no-border __tabela">
                        <thead class="no-border">
                            <tr>
                                <th></th>
                                <th><strong>Arquivo</strong></th>
                                <th><strong>Categorias</strong></th>
                                <th ><strong>Título</strong></th>
                                <th><strong>Interprete</strong></th>
                                <th><strong>Velocidade em BPM</strong></th>
                                <th><strong>Ano de gravação</strong></th>
                            </tr>
                        </thead>
                        <tbody class="no-border-y">
                            <c:forEach items="${lista2}" var="item" varStatus="vs">
                                <tr data-item-id="${item.id}">
                                    <td width="300">

                                        <a class="label label-default"  href="#" data-toggle="modal" data-target="#modal_ver_${item.id}"><i class="fa fa-eye"></i></a>
                                        <a class="label label-warning" href="${url}/musica/atualizar/${item.id}"><i class="fa fa-pencil"></i></a>
                                        <a class="label label-danger" href="${url}/musica/remover/${item.id}"><i class="fa fa-trash-o"></i></a>
                                        <a class="label label-success" href="${url}/musica/programacao-audiostore/${item.id}"><i class="fa fa-file-audio-o"></i></a>

                                        <a id="play${vs.index}" class="label label-info" href="#">
                                            <i class="fa fa-play"></i>
                                        </a>
                                        <a style="display: none" id="pause${vs.index}" class="label label-info" href="#">
                                            <i class="fa fa-pause"></i>
                                        </a>
                                        <a id="stop${vs.index}" class="label label-info" href="#">
                                            <i class="fa fa-stop"></i>
                                        </a>

                                        <a  id="mute${vs.index}" class="label label-info" href="#">
                                            <i class="fa fa-volume-off"></i>
                                        </a>

                                        <a style="display: none" id="unmute${vs.index}" class="label label-info" href="#">
                                            <i class="fa fa-volume-up"></i>
                                        </a>

                                        <a id="time{vs.index}" class="label label-info" href="javascript:;">
                                            <span id="currentTime${vs.index}">00:00</span> 
                                            <!--/  <span id="duration${vs.index}"></span>-->
                                        </a>

                                        <a class="label label-info btn_letra" href="javascript:;" data-popover="popover" data-html="true" data-content="${item.letra}" data-placement="bottom">
                                            <i class="fa fa-file-text"></i>
                                        </a>


                                        <div class="modal fade" id="modal_ver_${item.id}" tabindex="-1" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <table class="no-border">
                                                            <thead>
                                                                <tr>
                                                                    <td width="150"></td>
                                                                    <td></td>
                                                                </tr>
                                                            </thead>
                                                            <tbody class="no-border-x">
                                                                <tr>
                                                                    <td> <strong>Arquivo</strong></td>
                                                                    <td style="word-break: break-all;">${item.arquivo}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Categoria</strong></td>
                                                                    <td>${item.categoriaGeral}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Título</strong></td>
                                                                    <td>${item.titulo}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Interprete</strong></td>
                                                                    <td>${item.interprete}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Tipo de interprete</strong></td>
                                                                    <td>${item.tipoInterprete}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Velocidade em BPM</strong></td>
                                                                    <td>${item.bpm}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Tempo total da música</strong></td>
                                                                    <td>${item.tempoTotal}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Gravadora</strong></td>
                                                                    <td>${item.gravadora}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Ano de gravação</strong></td>
                                                                    <td>${item.anoGravacao}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>1ª Afinidade</strong></td>
                                                                    <td>${item.afinidade1}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>2ª Afinidade</strong></td>
                                                                    <td>${item.afinidade2}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>3ª Afinidade</strong></td>
                                                                    <td>${item.afinidade3}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>4ª Afinidade</strong></td>
                                                                    <td>${item.afinidade4}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Usuário que cadastrou</strong></td>
                                                                    <td>${item.usuario}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td><strong>Letra da música</strong></td>
                                                                    <td>${item.letra}</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>

                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i>Fechar Janela</button>
                                                        <button type="button" class="btn btn-warning"><i class="fa fa-pencil"></i>Atualizar</button>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        </div>
                                    </td>
                                    <td>${item.nomeArquivoFormatado}</td>
                                    <td>${item.categoriaGeral}</td>
                                    <td>${item.titulo}</td>
                                    <td>${item.interprete}</td>
                                    <td>${item.bpm}</td>
                                    <td>${item.anoGravacao}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>		
                    <b><strong>Total de músicas : ${totalRegistros}</strong></b>
                </div>    
            </div>
        </form>


        <script type="text/javascript">
            jQuery(document).ready(function() {
                var tabela = jQuery('.__tabela');
                tabela.children('tbody').children('tr').on('click', function() {
                    var tr = jQuery(this);

                    var klass = tr.attr("class");

                    if (null == klass || undefined == klass) {
                        klass = "tr__unselected";
                    }

                    if (klass.indexOf("tr__selected") != -1) {
                        tr.removeClass("tr__selected");
                        tr.addClass("tr__unselected");
                    } else if (klass.indexOf("tr__unselected") != -1) {
                        tr.removeClass("tr__unselected");
                        tr.addClass("tr__selected");
                    } else {
                        tr.removeClass("tr__selected");
                        tr.addClass("tr__unselected");
                    }

                    if (tabela.children('tbody').children('tr.tr__selected').size() > 0) {
                        jQuery('.btn_exp_arquivo').show();
                    } else {
                        jQuery('.btn_exp_arquivo').hide();
                    }
                });

                jQuery('.btn_exp_arquivo').on("click", function() {
                    var selected_line = tabela.children('tbody').children('tr.tr__selected').size();
                    var selected_cliente = jQuery.storage('matriz_selecionada');
                    if ( !(null != selected_line && undefined != selected_line && selected_line > 0) ) {
                        bootbox.alert("Selecione uma  ou mais músicas!", function(){});
                    }

                    if ( !(null != selected_cliente && undefined != selected_cliente && selected_cliente > 0) ) {
                        bootbox.alert("Selecione um cliente!", function(){});
                    }
                    
                    
                    var id_list = '';
                    var comma = '';

                    tabela.children('tbody').children('tr.tr__selected').each(function() {
                        var tr = jQuery(this);
                        id_list += comma + tr.data('itemId');
                        comma = ',';
                    });
                    
                    id_list += comma + selected_cliente;
                    
                    if ((null != selected_line && undefined != selected_line && selected_line > 0) && (null != selected_cliente && undefined != selected_cliente && selected_cliente > 0)) {
                        var ____url = '${url}/musica/programacao-audiostore/cadastrar/';
                        ____url += id_list;
                        window.location.href = ____url;
                    }
                    return false;
                });
            });
        </script>

        <style>
            .tr__selected {
                background-color: #fffbd3 !important;
            }
        </style>
    </jsp:body>
</instore:template>