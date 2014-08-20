<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<instore:template  menucolapse="false" isGrid="true">
    <jsp:attribute name="submenu">
        <div class="btn-group">
            <a href="${url}/musica/cadastrar" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
            <a href="#" class="btn btn-default" data-toggle="modal" data-target="#modal_sincronizacao" > <i class="fa fa-download"></i> Sincronizar </a>
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
                    console.log(url);
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
                            },
                            success: function(response) {
                                jQuery('.icone1').show();
                                jQuery('.icone2').hide();
                                console.log(response);
                                if (!response.success) {
                                    jQuery('.texto').text(response.response);
                                    jQuery('.erro2').show();
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
                                    <label>Caminho do diretório</label>
                                    <input type="text" name="url" class="form-control" placeholder="Exemplo: \\ftp\Audio\Musicas\Mp3">
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



        <div class="block-flat">
            <div class="content">
                <div datagrid-view="true" style="display: none">
                    <div class="block">
                        <div class="content">
                            <div style="display: block; width: 195px; margin: 0 auto; margin-top: -30px;">
                                <div id="player_musicas_gerais" class="jp-jplayer"></div>
                                <div id="jp_container_1" class="jp-audio">
                                    <div class="jp-type-single">
                                        <div class="jp-gui jp-interface">
                                            <div class="jp-progress">
                                                <div class="jp-seek-bar">
                                                    <div class="jp-play-bar"></div>
                                                </div>
                                            </div>
                                            <div class="jp-time-holder">
                                                <div class="text-center">
                                                    <div class="i-circle success">
                                                        <i class="fa fa-volume-up"></i>
                                                        <div class="jp-current-time"></div>
                                                    </div>
                                                    <h4>Audio</h4>
                                                    <p>Música selecionada para reproduzir!</p>
                                                </div>

                                                <div class="jp-duration"></div>
                                            </div>
                                        </div>
                                        <div class="btn-group" style="margin-left: 26px;">
                                            <button type="button" class="btn btn-success jp-play"><i class="fa fa-play"></i></button>
                                            <button type="button" class="btn btn-success jp-pause"><i class="fa fa-pause"></i></button>
                                            <button type="button" class="btn btn-default jp-stop"><i class="fa fa-stop"></i></button>
                                            <button type="button" class="btn btn-default jp-mute"><i class="fa fa-volume-down"></i></button>
                                            <button type="button" class="btn btn-default jp-unmute"><i class="fa fa-volume-off"></i></button>
                                            <button type="button" class="btn btn-default jp-volume-max"><i class="fa fa-volume-up"></i></button>
<!--                                            <button type="button" class="btn btn-default jp-repeat"><i class="fa fa-repeat"></i></button>
                                            <button type="button" class="btn btn-default jp-repeat-off"><i class="fa fa-chain-broken"></i></button>-->
                                        </div>              
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 prop"> 
                            Arquivo
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-arquivo="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Categoria
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-categoriaGeral="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Título
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-titulo="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Interprete
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-interprete="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Tipo de interprete
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-tipoInterprete="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Velocidade em BPM
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-bpm="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Tempo total da música
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-tempoTotal="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Gravadora
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-gravadora="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Ano de gravação
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-anoGravacao="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            1ª Afinidade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-afinidade1="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            2ª Afinidade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-afinidade2="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            3ª Afinidade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-afinidade3="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            4ª Afinidade
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-afinidade4="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Usuário que cadastrou
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-usuario="true"></div> 
                        </div>

                        <div class="col-md-12 prop"> 
                            Letra da música
                        </div>
                        <div class="col-md-12 val"> 
                            <div data-letra="true"></div> 
                        </div>
                    </div>
                </div>

                <div datagrid="true" data-id="id"></div>
            </div>
        </div>
    </jsp:body>
</instore:template>