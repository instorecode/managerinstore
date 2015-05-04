<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-categorias" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Audiostore Categorias </a>
    </jsp:attribute>

    <jsp:body>
        <div class="coluna-titulo coluna-titulo1" style="color: #a94442">Enfileirados</div>
        <div class="coluna overflowy coluna1" data-status-color="panel-danger">
            <c:forEach begin="0" end="30" varStatus="vs">
                <div class="item panel panel-danger"> 
                    <div class="panel-heading">
                        Cod 000${vs.index}
                        <div class="pull-right">

                            <button type="button" class="btn btn-default btn-xs btn-flat"><i class="fa fa-upload"></i></button>
                            <button type="button" class="btn btn-default btn-xs btn-flat"><i class="fa fa-play"></i></button>
                            <button type="button" class="btn btn-default btn-xs btn-flat"><i class="fa fa-pause"></i></button>

                            <button class="btn btn-xs btn-default btn-flat btn-item-opt"><i class="fa fa-plus"></i></button>
                        </div>
                    </div>
                    <div class="panel-body">
                        Comercial da HAVAN 000${vs.index}
                    </div>
                    <div class="panel-footer">
                        01/05/2015
                        <span class="label label-danger pull-right">O prazo explodio</span>
                        <div class="clearfix"></div>
                    </div>                    
                </div>
            </c:forEach>
        </div>

        <div class="coluna-titulo coluna-titulo2" style="color: #8a6d3b">Em locução</div>
        <div class="coluna overflowy coluna2" data-status-color="panel-warning">

        </div>

        <div class="coluna-titulo coluna-titulo3" style="color: #31708f">Processamento de audio</div>
        <div class="coluna overflowy coluna3" data-status-color="panel-info">

        </div>

        <div class="coluna-titulo coluna-titulo4" style="color: #3c763d">Aguardando aprovação</div>
        <div class="coluna overflowy coluna4" data-status-color="panel-success">

        </div>


        <div class="coluna-titulo coluna-titulo5" style="color: #428bca">Aprovado</div>
        <div class="coluna overflowy coluna5" data-status-color="panel-primary">

        </div>

        <div class="coluna-titulo coluna-titulo6">Distribuido</div>
        <div class="coluna overflowy coluna6" data-status-color="panel-black">

        </div> 
        
        <div class="coluna-titulo coluna-titulo7">Inválido</div>
        <div class="coluna overflowy coluna7" data-status-color="panel-invalid">

        </div> 



        <div class="mask">
            <div role="tabpanel">
                <div class="w900px">
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Visão geral</a></li>
                        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Letra</a></li>
                        <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Arquivo</a></li>
                        <li><a href="#fechar-mask">Fechar [X]</a></li>
                    </ul>
                </div>

                <div class="tab-content" style="display: block; width: 100%; height: 100%; position: absolute; top: 30px; left: 0; border: 0px;">
                    <div role="tabpanel" class="tab-pane active" id="home">
                        <div class="w900px">
                            <div class="row">
                                <div class="col-md-2 visao-geral-gridcol h bbottom">
                                    <b>O.S Nº</b>
                                </div>

                                <div class="col-md-4 visao-geral-gridcol h">
                                    <b>CLIENTE</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol h">
                                    <b>RECEBIDO EM</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol h">
                                    <b>ENVIADO EM</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol h">
                                    <b>APROVADO EM</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol">
                                    <b>00001</b>
                                </div>

                                <div class="col-md-4 visao-geral-gridcol">
                                    <b>HAVAN</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol">
                                    <b>01/02/2015</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol">
                                    <b>03/03/2015</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol">
                                    <b>02/03/2015</b>
                                </div>


                                <div class="col-md-2 visao-geral-gridcol h">
                                    <b>Nº SPOT</b>
                                </div>

                                <div class="col-md-4 visao-geral-gridcol h">
                                    <b>VEICULAÇÃO</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol h">
                                    <b>FREQUENCIA</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol h">
                                    <b>PRAÇA</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol h">
                                    <b>RADIO</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol">
                                    <b>00001</b>
                                </div>

                                <div class="col-md-4 visao-geral-gridcol">
                                    <b> -- </b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol">
                                    <b>2 vezes por dia</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol">
                                    <b>H01 , H02 , H03</b>
                                </div>

                                <div class="col-md-2 visao-geral-gridcol">
                                    <b>INTERNA</b>
                                </div>
                            </div> 

                            <div style="height: 80px"></div>

                            <div class="row">
                                <div class="col-md-6 visao-geral-gridcol h">
                                    <b>DATA DE DISPONIBILIZAÇÃO</b>
                                </div>

                                <div class="col-md-6 visao-geral-gridcol h">
                                    <b>CATEGORIA/DET</b>
                                </div>

                                <div class="col-md-6 visao-geral-gridcol">
                                    <b>01/02/2050</b>
                                </div>

                                <div class="col-md-6 visao-geral-gridcol">
                                    <b>DETERMINADO</b>
                                </div>
                            </div>

                            <div style="height: 80px"></div>

                            TEXTO
                            <div class="well well-sm">
                                O que temos que ter sempre em mente é que a execução dos pontos do programa nos obriga à análise do fluxo de informações. Desta maneira, o acompanhamento das preferências de consumo não pode mais se dissociar dos conhecimentos estratégicos para atingir a excelência. Evidentemente, a necessidade de renovação processual assume importantes posições no estabelecimento do retorno esperado a longo prazo. A nível organizacional, o julgamento imparcial das eventualidades auxilia a preparação e a composição das regras de conduta normativas. Pensando mais a longo prazo, o início da atividade geral de formação de atitudes maximiza as possibilidades por conta das novas proposições. 
                                É importante questionar o quanto o desenvolvimento contínuo de distintas formas de atuação representa uma abertura para a melhoria das direções preferenciais no sentido do progresso. O cuidado em identificar pontos críticos na expansão dos mercados mundiais facilita a criação dos modos de operação convencionais. Podemos já vislumbrar o modo pelo qual a revolução dos costumes deve passar por modificações independentemente das condições inegavelmente apropriadas. Assim mesmo, a consulta aos diversos militantes afeta positivamente a correta previsão do sistema de formação de quadros que corresponde às necessidades. 
                                Por outro lado, a constante divulgação das informações promove a alavancagem dos relacionamentos verticais entre as hierarquias. Não obstante, o desafiador cenário globalizado oferece uma interessante oportunidade para verificação dos níveis de motivação departamental. Por conseguinte, a mobilidade dos capitais internacionais garante a contribuição de um grupo importante na determinação das condições financeiras e administrativas exigidas. 
                            </div>
                        </div>
                    </div>

                    <div role="tabpanel" class="tab-pane" id="profile">
                        <div class="w900px">
                            TEXTO

                            <div class="well well-sm">
                                O que temos que ter sempre em mente é que a execução dos pontos do programa nos obriga à análise do fluxo de informações. Desta maneira, o acompanhamento das preferências de consumo não pode mais se dissociar dos conhecimentos estratégicos para atingir a excelência. Evidentemente, a necessidade de renovação processual assume importantes posições no estabelecimento do retorno esperado a longo prazo. A nível organizacional, o julgamento imparcial das eventualidades auxilia a preparação e a composição das regras de conduta normativas. Pensando mais a longo prazo, o início da atividade geral de formação de atitudes maximiza as possibilidades por conta das novas proposições. 

                                É importante questionar o quanto o desenvolvimento contínuo de distintas formas de atuação representa uma abertura para a melhoria das direções preferenciais no sentido do progresso. O cuidado em identificar pontos críticos na expansão dos mercados mundiais facilita a criação dos modos de operação convencionais. Podemos já vislumbrar o modo pelo qual a revolução dos costumes deve passar por modificações independentemente das condições inegavelmente apropriadas. Assim mesmo, a consulta aos diversos militantes afeta positivamente a correta previsão do sistema de formação de quadros que corresponde às necessidades. 

                                Por outro lado, a constante divulgação das informações promove a alavancagem dos relacionamentos verticais entre as hierarquias. Não obstante, o desafiador cenário globalizado oferece uma interessante oportunidade para verificação dos níveis de motivação departamental. Por conseguinte, a mobilidade dos capitais internacionais garante a contribuição de um grupo importante na determinação das condições financeiras e administrativas exigidas. 
                            </div>


                            OBSERVAÇÕES
                            <div class="row obs1_container">
                                <c:forEach begin="0" end="10">
                                    <div class="col-md-1 modelo_obs">
                                        <div class="header">
                                            <div style="  margin-top: 90px; float: left;">
                                                &nbsp;&nbsp;&nbsp;&nbsp;<b class="bold">Usuário:</b> Alex Valentim Gonçalves
                                                <br />
                                                &nbsp;&nbsp;&nbsp;&nbsp;<b class="bold">Data:</b> 01/02/2015 00:25:37
                                                <br />
                                            </div>
                                        </div>
                                        <div class="body">
                                            <hr style="border: 0px; border-top: 1px dashed blue; opacity: 0.2;" />
                                            <div class="content">
                                                bla bla bla
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="messages">
                        <div class="w900px">
                            <div class="form-group">
                                <label for="exampleInputFile">Arquivo</label>
                                <input type="file" id="exampleInputFile">
                                <p class="help-block">Importar arquivo de audio</p>
                            </div>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="settings">
                        ccc
                    </div>
                </div>
            </div>
        </div>


        <style type="text/css">
            .mask {
                display: none;
                width: 100%;
                height: 100%;

                background-color: white;

                position: fixed;
                top:0;
                left: 0;

                z-index: 99999999;
                overflow-y: auto;

            }

            .visao-geral-gridcol {
                border: 1px solid #ebebeb;
            }

            .visao-geral-gridcol.h {
                height: 50px;
                line-height: 50px;

                border-bottom: 0px;
                color: #428bca;
            }

            .w900px {
                width: 900px;
                margin: 0 auto;
            }

            .coluna1 {
                display: inline-block;
                width: 200px;

                position: absolute;
                left: 0;
                top: 125px;
                border-right: 1px solid #2494f2; 
            }

            .coluna2 {
                display: inline-block;
                width: 200px;
                height: 100%;

                position: absolute;
                left: 200px;
                top: 125px;
                border-right: 1px solid #2494f2; 
            }

            .coluna3 {
                display: inline-block;
                width: 200px;
                height: 100%;

                position: absolute;
                left: 400px;
                top: 125px;
                border-right: 1px solid #2494f2; 
            }

            .coluna4 {
                display: inline-block;
                width: 200px;
                height: 100%;

                position: absolute;
                left: 600px;
                top: 125px;
                border-right: 1px solid #2494f2; 
            }

            .coluna5 {
                display: inline-block;
                width: 200px;
                height: 100%; 

                position: absolute;
                left: 800px;
                top: 125px;
                border-right: 1px solid #2494f2; 
            }

            .coluna6 {
                display: inline-block;
                width: 200px;
                height: 100%; 

                position: absolute;
                left: 1000px;
                top: 125px;
                border-right: 1px solid #2494f2; 
            }
            
            .coluna7 {
                display: inline-block;
                width: 200px;
                height: 100%; 

                position: absolute;
                left: 1000px;
                top: 125px;
                border-right: 1px solid #2494f2; 
            }

            .coluna-titulo1 {
                display: inline-block;
                width: 199px;
                height: 40px;

                background-color: #f9f9f9;
                line-height: 40px;
                font-weight: bold;
                text-indent: 20px;
                position: absolute;
                left: 0;
                top: 85px;
            }

            .coluna-titulo2 {
                display: inline-block;
                width: 199px;
                height: 40px;

                background-color: #f9f9f9;
                line-height: 40px;
                font-weight: bold;
                text-indent: 20px;
                position: absolute;
                left: 200px;
                top: 85px;
            }

            .coluna-titulo3 {
                display: inline-block;
                width: 199px;
                height: 40px;

                background-color: #f9f9f9;
                line-height: 40px;
                font-weight: bold;
                text-indent: 20px;
                position: absolute;
                left: 400px;
                top: 85px;
            }

            .coluna-titulo4 {
                display: inline-block;
                width: 199px;
                height: 40px;

                background-color: #f9f9f9;
                line-height: 40px;
                font-weight: bold;
                text-indent: 20px;
                position: absolute;
                left: 600px;
                top: 85px;
            }

            .coluna-titulo5 {
                display: inline-block;
                width: 199px;
                height: 40px;

                background-color: #f9f9f9;
                line-height: 40px;
                font-weight: bold;
                text-indent: 20px;
                position: absolute;
                left: 800px;
                top: 85px;
            }

            .coluna-titulo6 {
                display: inline-block;
                width: 199px;
                height: 40px;

                background-color: #f9f9f9;
                line-height: 40px;
                font-weight: bold;
                text-indent: 20px;
                position: absolute;
                left: 1000px;
                top: 85px;
            }

            .coluna-titulo7 {
                display: inline-block;
                width: 199px;
                height: 40px;

                background-color: #f9f9f9;
                line-height: 40px;
                font-weight: bold;
                text-indent: 20px;
                position: absolute;
                left: 1000px;
                top: 85px;
            }

            body {
                overflow-y: hidden;
            }

            .coluna::-webkit-scrollbar-track
            {
                background-color: #FFF;
            }

            .coluna::-webkit-scrollbar
            {
                width: 6px;
                background-color: #FFF;
            }

            .coluna::-webkit-scrollbar-thumb
            {
                background-color: #2494f2;
            }
            .item {
                z-index: 9999999;
                border-radius: 0px;
                /*margin-bottom: 4px;*/
            }

            .item .panel-heading {border-radius: 0px;}
            .item .panel-body {border-radius: 0px;}

            .overflowy {
                overflow-y: auto;
            }

            .panel-black > .panel-heading {
                color: #FFF;
                background-color: #000;
                border-color: #000;
            }

            .panel-black {
                border-color: #000;
            }
            
            .panel-invalid > .panel-heading {
                color: #000;
                background-color: #a3a3a3;
                border-color: #a3a3a3;
            }

            .panel-invalid {
                border-color: #a3a3a3;
            }
            .coluna-titulo {
                border-right: 1px solid #2494f2;
            }
        </style>


        <style>
            @font-face {
                font-family: 'pencil';
                src: url('${url_resources}fonts/ZakirahsHand.ttf?v=4.1.0') format('truetype');
                font-weight: normal;
                font-style: normal;
            }
            @font-face {
                font-family: 'pencilB';
                src: url('${url_resources}fonts/ZakirahsHandB.ttf?v=4.1.0') format('truetype');
                font-weight: normal;
                font-style: normal;
            }
            .cke_chrome {
                border: 0px;
                margin-left: -20px;
                margin-right: -20px;
                margin-bottom: -35px;
                margin-top: -20px;
            }

            .cke_top {
                border: 0px;
                background-color: #FFF;
                background-image: none;
                border-bottom: 1px solid #ebebeb;
            }

            .cke_bottom {
                border: 0px;
                background-color: #FFF;
                background-image: none;
            }


            .modelo_obs .body .content .cke_editable
            {
                font-size: 13px;
                line-height: 1.6em;
            }

            .modelo_obs .body .content  blockquote
            {
                font-style: italic;
                font-family: Georgia, Times, "Times New Roman", serif;
                padding: 2px 0;
                border-style: solid;
                border-color: #ccc;
                border-width: 0;
            }

            .modelo_obs .body .content  .cke_contents_ltr blockquote
            {
                padding-left: 20px;
                padding-right: 8px;
                border-left-width: 5px;
            }

            .modelo_obs .body .content  .cke_contents_rtl blockquote
            {
                padding-left: 8px;
                padding-right: 20px;
                border-right-width: 5px;
            }

            .modelo_obs .body .content  a
            {
                color: #0782C1;
            }

            .modelo_obs .body .content  ol,ul,dl
            {
                /* IE7: reset rtl list margin. (#7334) */
                *margin-right: 0px;
                /* preserved spaces for list items with text direction other than the list. (#6249,#8049)*/
                padding: 0 40px;
            }

            .modelo_obs .body .content  h1,h2,h3,h4,h5,h6
            {
                font-weight: normal;
                line-height: 1.2em;
            }

            .modelo_obs .body .content  hr
            {
                border: 0px;
                border-top: 1px solid #ebebeb;
            }

            .modelo_obs .body .content img.right
            {
                border: 1px solid #ccc;
                float: right;
                margin-left: 15px;
                padding: 5px;
            }

            .modelo_obs .body .content  img.left
            {
                border: 1px solid #ccc;
                float: left;
                margin-right: 15px;
                padding: 5px;
            }

            .modelo_obs .body .content  pre
            {
                white-space: pre-wrap; /* CSS 2.1 */
                word-wrap: break-word; /* IE7 */
                background-color: transparent;
                color: blue;
                border: 0px;
            }

            .modelo_obs .body .content  .marker
            {
                background-color: red;
                color: #FFF;
                padding: 4px 10px 0px 6px;
                border-radius: 24px;
            }

            .modelo_obs .body .content  span[lang]
            {
                font-style: italic;
            }

            .modelo_obs {
                display: block;
                width: 373px; 
                background-size: 100%;
            }

            .modelo_obs .body .content {
                display: block;
                width: 337px;
                word-break: break-all;
            }

            .modelo_obs .body { 
                display: block;
                width: 359px;
                background-color: #fdf976;
                padding: 10px;
                padding-top: 0px;
                max-width: 373px;
                margin-left: 6px;
                margin-top: -29px;
                font-family: pencil;
                color: blue;
                padding-bottom: 4px;
            }

            .modelo_obs .header {
                width: 373px;
                height: 145px;
                background-image: url(${url_img}paper4.png); 
                font-family: pencil;
                font-size: 16px;
                color: blue;
            }

            .bold {
                font-weight: bold;
            }

            .ql-select-style  {
                display: inline;
                width: 65px;
                height: 22px !important;
                border-radius: 0px;
                padding: 0px 1px !important;
                /* box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.12), 1px 1px 0 rgba(255, 255, 255, 0.2) inset; */
                margin-top: 0px;
            }

            .modal.modal-wide .modal-dialog {
                width: 60%;
            }

            /*            .ql-editor > * {
                            font-family: pencil;
                        }*/

            .ql-container {
                box-sizing: border-box;
                cursor: text;
                font-family: Helvetica,Arial,sans-serif;
                font-size: 13px;
                height: 100%;
                line-height: 1.42;
                margin: 0;
                overflow-x: hidden;
                overflow-y: auto;
                padding: 5px;
                padding-bottom: 0px;
                position: relative;
            }

            #locutor_texto .ql-editor {
                min-height: 100px !important;
                font-family: Arial !important;
            }

            .modal-body .ql-container .ql-editor {

            }
        </style>

        <script type="text/javascript">
            jQuery(document).ready(function () {
                function resize_coluna() {
                    var w = jQuery(window).width() / 7;
                    var h = jQuery(window).height();


                    jQuery('.coluna').css({
                        'width': (w) + 'px',
                        'height': (h - 175) + 'px'
                    });

                    jQuery('.coluna-titulo').css({
                        'width': (w) + 'px',
                    });

                    var index = 0;
                    jQuery('.coluna-titulo').each(function () {
                        jQuery(this).css({
                            'left': (index * w) + 'px',
                        });
                        index++;
                    });

                    index = 0;
                    jQuery('.coluna').each(function () {
                        jQuery(this).css({
                            'left': (index * w) + 'px',
                        });
                        index++;
                    });
                }


                resize_coluna();

                jQuery(window).resize(function () {
                    resize_coluna();
                });

                //                jQuery('.item').draggable({
                //                    start: function () {
                //                        jQuery('.coluna').removeClass('overflowy');
                //                        jQuery(this).css({
                //                            'opacity': '0.5'
//                        });
                //                    }
//                });

//                jQuery(".coluna").droppable({
//                    drop: function (event, ui) {
//                        var clone = jQuery(ui.draggable).clone();
//
//                        clone.removeClass('panel-primary');
//                        clone.removeClass('panel-success');
//                        clone.removeClass('panel-info');
//                        clone.removeClass('panel-warning');
//                        clone.removeClass('panel-warning');
//                        clone.removeClass('panel-danger');
//                        clone.removeClass('panel-black');
//                        clone.addClass(jQuery(this).data('statusColor'));
//                        clone.removeAttr('style');
//
//                        clone.css({
//                            'opacity': '1'
//                        });
//
//                        jQuery(ui.draggable).remove();
//                        jQuery(this).append(clone);
//                        jQuery('.coluna').addClass('overflowy');
//                    }
//                });
                jQuery(".coluna").sortable({
                    connectWith: ".coluna",
                    start: function (event, ui) {
                        jQuery('.coluna').removeClass('overflowy');
                        ui.item.css({
                            'opacity': '0.5'
                        });
                    },
                    stop: function (event, ui) {
                        jQuery('.coluna').addClass('overflowy');

                        ui.item.css({
                            'opacity': '1'
                        });
                    },
                    update: function (event, ui) {
                        ui.item.removeClass('panel-primary');
                        ui.item.removeClass('panel-success');
                        ui.item.removeClass('panel-info');
                        ui.item.removeClass('panel-warning');
                        ui.item.removeClass('panel-warning');
                        ui.item.removeClass('panel-danger');
                        ui.item.removeClass('panel-black');
                        ui.item.addClass(jQuery(this).data('statusColor'));
                        ui.item.removeAttr('style');
                    },
                });

                jQuery(document).on('click', '.btn-item-opt', function () {
                    jQuery('.mask').show();
                });

                jQuery(document).on('click', '[href="#fechar-mask"]', function () {
                    jQuery('.mask').hide();
                });
            });
        </script>
    </jsp:body>
</instore:template>