<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-musica" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Musicas </a>
    </jsp:attribute>

    <jsp:body>



        <script type="text/javascript">
            jQuery(document).ready(function() {
                jQuery(':input').each(function() {
                    jQuery(this).attr("disabled", true);
                });
                jQuery('.sel_cliente').attr("disabled", false);

                jQuery('.sel_cliente').on('change', function() {


                    // var value = ${not cadastrar ? audiostoreMusicaBean.cliente.idcliente :'jQuery(this).val();'}
                    var value = jQuery(this).val();

                    var json_string = jQuery.storage("cliente_" + value);
                    if (null != json_string && undefined != json_string && "" != json_string) {

                        jQuery('.msg_err0').hide();
                        jQuery('.msg_err1').hide();
                        jQuery('.msg_err2').hide();
                        jQuery('.msg_err3').hide();
                        jQuery('.msg_err4').hide();

                        jQuery(':input').each(function() {
                            jQuery(this).attr("disabled", false);
                        });

                        var json = jQuery.parseJSON(json_string);

                        // sel_importe
                        var html = '';
                        for (i in json.musicaList) {
                            var item = json.musicaList[i];
                            html += '<option value="' + item.nomeArquivo + '" data-titulo="' + item.Titulo + '" data-nome-arquivo="' + item.nomeArquivo + '" data-caminho-arquivo="' + item.caminhoCaminho + '">' + item.Titulo + '</option>';
                        }
                        jQuery('.sel_importe option').remove();
                        jQuery('.sel_importe').append(html);

                        var html2 = '';
                        var id;
                        for (i in json.categoriaList) {
                            var item = json.categoriaList[i];
                            html2 += '<option value="' + item.codigo + '">' + item.categoria + '</option>';
                            id = item.codigo;
                        }
                        jQuery('.sel_cat1 option').remove();
                        jQuery('.sel_cat1').append(html2);
                        jQuery('.sel_cat1').val(id);
                        jQuery('.sel_cat1').change();

                        jQuery('.sel_cat2 option').remove();
                        jQuery('.sel_cat2').append(html2);
                        jQuery('.sel_cat2').val(id);
                        jQuery('.sel_cat2').change();

                    } else {

                        console.log("aki2 " + value);
                        jQuery.ajax({
                            type: 'GET',
                            url: '${url}/audiostore-musica/informacao',
                            data: {
                                idcliente: value
                            },
                            beforeSend: function() {
                                bootbox.dialog({
                                    message: "Aguarde...",
                                    title: "Sistema processando informações",
                                    buttons: {}
                                });
                            },
                            success: function(json) {
                                bootbox.hideAll();
                                if (!json.possuiCategoria) {
                                    jQuery('.msg_err0').show();
                                    jQuery('.msg_err1').show();
                                    jQuery('.msg_err2').hide();
                                    jQuery('.msg_err3').hide();
                                    jQuery('.msg_err4').hide();
                                    console.log("aqui");
                                    jQuery(':input').each(function() {
                                        jQuery(this).attr("disabled", true);
                                    });
                                    jQuery('.sel_cliente').attr("disabled", false);
                                } else {
                                    if (!json.existeDiretorio) {
                                        jQuery('.msg_err0').show();
                                        jQuery('.msg_err1').hide();
                                        jQuery('.msg_err2').show();
                                        jQuery('.msg_err3').hide();
                                        jQuery('.msg_err4').hide();

                                        jQuery(':input').each(function() {
                                            jQuery(this).attr("disabled", true);
                                        });
                                        jQuery('.sel_cliente').attr("disabled", false);
                                    } else {
                                        if (!json.existeArquivo) {
                                            jQuery('.msg_err0').show();
                                            jQuery('.msg_err1').hide();
                                            jQuery('.msg_err2').hide();
                                            jQuery('.msg_err3').show();
                                            jQuery('.msg_err4').hide();

                                            jQuery(':input').each(function() {
                                                jQuery(this).attr("disabled", true);
                                            });
                                            jQuery('.sel_cliente').attr("disabled", false);
                                        } else {
                                            jQuery('.msg_err0').hide();
                                            jQuery('.msg_err1').hide();
                                            jQuery('.msg_err2').hide();
                                            jQuery('.msg_err3').hide();
                                            jQuery('.msg_err4').hide();

                                            jQuery(':input').each(function() {
                                                jQuery(this).attr("disabled", false);
                                            });

                                            jQuery.storageAdd("cliente_" + value, JSON.stringify(json));
                                            // sel_importe
                                            var html = '';
                                            for (i in json.musicaList) {
                                                var item = json.musicaList[i];
                                                html += '<option value="' + item.nomeArquivo + '" data-titulo="' + item.Titulo + '" data-nome-arquivo="' + item.nomeArquivo + '" data-caminho-arquivo="' + item.caminhoCaminho + '" data-caminho-full="' + item.caminhoFull + '">' + item.Titulo + '</option>';
                                            }
                                            jQuery('.sel_importe option').remove();
                                            jQuery('.sel_importe').append(html);

                                            var html2 = '';
                                            var id;
                                            for (i in json.categoriaList) {
                                                var item = json.categoriaList[i];
                                                html2 += '<option value="' + item.codigo + '">' + item.categoria + '</option>';
                                                id = item.codigo;
                                            }
                                            jQuery('.sel_cat1 option').remove();
                                            jQuery('.sel_cat1').append(html2);
                                            jQuery('.sel_cat1').val(id);
                                            jQuery('.sel_cat1').change();

                                            jQuery('.sel_cat2 option').remove();
                                            jQuery('.sel_cat2').append(html2);
                                            jQuery('.sel_cat2').val(id);
                                            jQuery('.sel_cat2').change();
                                        }

                                    }
                                }
                            },
                            error: function(json) {
                                bootbox.hideAll();
                                jQuery('.msg_err0').show();
                                jQuery('.msg_err1').hide();
                                jQuery('.msg_err2').hide();
                                jQuery('.msg_err3').hide();
                                jQuery('.msg_err4').show();

                                jQuery(':input').each(function() {
                                    jQuery(this).attr("disabled", true);
                                });
                                jQuery('.sel_cliente').attr("disabled", false);
                            }
                        });
                    }
                });

                jQuery('.sel_importe').on('change', function() {
                    var op = jQuery('.sel_importe option:selected');
                    var titulo = op.data('titulo');
                    var nome_arquivo = op.data('nomeArquivo');
                    var caminho_arquivo = op.data('caminhoArquivo');
                    var caminho_arquivo = op.data('caminhoFull');

                    jQuery('[name="audiostoreMusicaBean.titulo"]').val(titulo);
                    jQuery('[name="audiostoreMusicaBean.arquivo"]').val(nome_arquivo);
                });
            });
        </script>

        <c:if test="${not cadastrar}">
            <script type="text/javascript">
                jQuery(document).ready(function() {
                    jQuery('.sel_cliente').val("${audiostoreMusicaBean.cliente.idcliente}");
                    jQuery('.sel_cliente').change();
                    
                    jQuery(".sel_cat1").val("${audiostoreMusicaBean.categoria1.codigo}");
                    jQuery(".sel_cat1").change();

                    jQuery(".sel_cat2").val("${audiostoreMusicaBean.categoria2.codigo}");
                    jQuery(".sel_cat2").change();

                    jQuery(".sel_importe").val("${audiostoreMusicaBean.arquivo}");
                    jQuery(".sel_importe").change();

                });
            </script>
        </c:if>


        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-musica">
            <input type="hidden" name="audiostoreMusicaBean.id" value="${audiostoreMusicaBean.id}"  />

            <div class="alert alert-info alert-white rounded msg_err0" style="${cadastrar ? '':'display:none;'}">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <div class="icon"><i class="fa fa-info"></i></div>
                <strong>Importante!</strong> &nbsp;&nbsp;Os campo apenas serão liberados quando você selecionar um cliente válido.
            </div>

            <div class="alert alert-danger alert-white rounded msg_err1" style="display: none">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <div class="icon"><i class="fa fa-warning"></i></div>
                <strong>Importante!</strong> &nbsp;&nbsp;Você não pode processeguir como cadastro, o cliente selecionado nao possui categoria.
            </div>

            <div class="alert alert-warning alert-white rounded msg_err2" style="display: none">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <div class="icon"><i class="fa fa-warning"></i></div>
                <strong>Importante!</strong> &nbsp;&nbsp;Você não pode processeguir como cadastro, o cliente selecionado não tem um diretorio configurado para os arquivos de musica.
            </div>

            <div class="alert alert-warning alert-white rounded msg_err3" style="display: none">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <div class="icon"><i class="fa fa-warning"></i></div>
                <strong>Importante!</strong> &nbsp;&nbsp;Você não pode processeguir como cadastro, o cliente selecionado não tem arquivos no diretorio configurado.
            </div>

            <div class="alert alert-warning alert-white rounded msg_err4" style="display: none">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <div class="icon"><i class="fa fa-warning"></i></div>
                <strong>Importante!</strong> &nbsp;&nbsp;Você não pode processeguir como cadastro, ocorreu algum erro ao tentar carregar os registro do cliente selecionado.
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Cliente</label>
                        <br />
                        <select  class="select2 sel_cliente select_cliente"  data-rule-required="true" name="audiostoreMusicaBean.cliente.idcliente">
                            <option value="" >Selecione um cliente</option>
                            <c:forEach items="${clienteBeanList}" var="cli">
                                <option value="${cli.idcliente}" ${audiostoreMusicaBean.cliente.idcliente eq cli.idcliente ? 'selected="selected"':''}>${cli.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Importar</label>
                        <br />
                        <select class="select2 sel_importe"   data-rule-required="true">
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Titulo</label>
                        <input type="text" name="audiostoreMusicaBean.titulo" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.titulo}" >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Arquivo</label>
                        <input type="text" name="audiostoreMusicaBean.arquivo" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.arquivo}" readonly>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Gravadora</label>
                        <br />
                        <select class="select2" name="audiostoreMusicaBean.gravadora.id" data-rule-required="true" >
                            <c:forEach items="${gravadoraBeanList}" var="gravadora">
                                <option value="${gravadora.id}" ${gravadora.id eq audiostoreMusicaBean.gravadora.id ? 'selected="selected"' : ''}>${gravadora.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>


            <div class="row">


                <div class="col-md-3">
                    <div class="form-group">
                        <label>Categoria Primária</label> 
                        <br />
                        <select  class="select2 sel_cat1" name="audiostoreMusicaBean.categoria1.codigo" data-rule-required="true" >

                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Categoria Secundário</label> 
                        <br />
                        <select class="select2 sel_cat2" name="audiostoreMusicaBean.categoria2.codigo" data-rule-required="true" >
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Dias execução <i>(Primária)</i></label>
                        <input type="text" name="audiostoreMusicaBean.diasExecucao1" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.diasExecucao1}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Dias execução <i>(Secundário)</i></label>
                        <input type="text" name="audiostoreMusicaBean.diasExecucao2" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.diasExecucao2}">
                    </div>
                </div>

            </div>

            <hr />

            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">                        
                        <label>Usar crossover</label>
                        <br />
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.crossover" id="optionsRadios1" value="${true}" ${audiostoreMusicaBean.crossover ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.crossover" id="optionsRadios1" value="${false}"  ${not audiostoreMusicaBean.crossover ? 'checked="checked"' : ''}>&nbsp;Não </label>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data vencimendo crossover</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.dataVencimentoCrossover" class="form-control datepicker" placeholder="Nome"  
                                   value="${cf:dateFormat(audiostoreMusicaBean.dataVencimentoCrossover, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>
            </div>


            <br />

            <b>Intérprete</b>
            <hr />

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Nome </label>
                        <input type="text" name="audiostoreMusicaBean.interprete" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.interprete}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Tipo</label> 
                        <br />
                        <select class="select2" name="audiostoreMusicaBean.tipoInterprete" data-rule-required="true" >
                            <option value="1" ${audiostoreMusicaBean.tipoInterprete eq 1 ? 'selected="selected"' : ''} >Masculino</option> 
                            <option value="2" ${audiostoreMusicaBean.tipoInterprete eq 2 ? 'selected="selected"' : ''} >Feminino</option> 
                            <option value="3" ${audiostoreMusicaBean.tipoInterprete eq 3 ? 'selected="selected"' : ''} >Grupo</option> 
                            <option value="4" ${audiostoreMusicaBean.tipoInterprete eq 4 ? 'selected="selected"' : ''} >Instrumental</option> 
                            <option value="5" ${audiostoreMusicaBean.tipoInterprete eq 5 ? 'selected="selected"' : ''} >Jingle</option> 
                        </select>
                    </div>
                </div>

                <div class="col-md-4"></div>
                <div class="col-md-12">
                    <br />
                    <b> Afinidades/Informações para streaming </b>
                    <hr />
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade1" class="form-control" placeholder="primário"  
                               value="${audiostoreMusicaBean.afinidade1}">
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade2" class="form-control" placeholder="secundário"  
                               value="${audiostoreMusicaBean.afinidade2}">
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade3" class="form-control" placeholder="terciário"  
                               value="${audiostoreMusicaBean.afinidade3}">
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade4" class="form-control" placeholder="quaternário"  
                               value="${audiostoreMusicaBean.afinidade4}">
                    </div>
                </div>
            </div>

            <br />
            <hr />

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Tempo</label>
                        <input type="text" name="tempoTotal" class="form-control" placeholder="Nome"  
                               data-mask="99:99:99"  
                               data-rule-required="true" 
                               value="${cf:dateFormat(audiostoreMusicaBean.tempoTotal, "HH:mm:ss")}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Quantidade </label>

                        <input type="text" name="audiostoreMusicaBean.qtdePlayer" class="form-control span2" placeholder="Quantidade máxima de execuções no dia)"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.qtdePlayer}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Ultima  execução</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.ultimaExecucao" class="form-control datepicker" placeholder="Nome"  
                                   data-mask="99/99/9999"
                                   data-rule-maxlength="30" value="${cf:dateFormat(audiostoreMusicaBean.ultimaExecucao, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Frame inicial</label>
                        <input type="text" name="audiostoreMusicaBean.frameInicio" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.frameInicio}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Frame final</label>
                        <input type="text" name="audiostoreMusicaBean.frameFinal" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.frameFinal}">
                    </div>
                </div>
            </div>

            <br />
            <hr />

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de vencimento</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.dataVencimento" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999" value="${cf:dateFormat(audiostoreMusicaBean.dataVencimento, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de inclusao</label>

                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.data" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999"value="${cf:dateFormat(audiostoreMusicaBean.data, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>



                <div class="col-md-2">
                    <div class="form-group">
                        <label>Velocidade</label> 
                        <br />
                        <select class="select2" name="audiostoreMusicaBean.velocidade" data-rule-required="true" >
                            <option value="1" ${audiostoreMusicaBean.velocidade eq 1 ? 'selected="selected"':''} >Lento</option> 
                            <option value="2" ${audiostoreMusicaBean.velocidade eq 2 ? 'selected="selected"':''}>Normal</option> 
                            <option value="3" ${audiostoreMusicaBean.velocidade eq 3 ? 'selected="selected"':''}>Rápido</option> 
                        </select>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Ano de gravação</label> 
                        <br />
                        <select class="select2" name="audiostoreMusicaBean.anoGravacao" data-rule-required="true" >
                            <c:forEach begin="1980" end="2014" varStatus="vs">
                                <option value="${vs.index}" ${audiostoreMusicaBean.anoGravacao eq vs.index ? 'selected="selected"':''}>${vs.index}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Tipo</label> 
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.cut" id="optionsRadios1" value="${true}" ${audiostoreMusicaBean.cut ? 'checked="checked"' : ''} >&nbsp;Cut </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.cut" id="optionsRadios1" value="${false}"  ${not audiostoreMusicaBean.cut ? 'checked="checked"' : ''}>&nbsp;FadeOut </label>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>

    </jsp:body>
</instore:template>