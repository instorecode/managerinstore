<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<instore:template  menucolapse="false">
    <jsp:attribute name="submenu">
        <a href="${url}/musica" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista</a>
    </jsp:attribute>
    <jsp:body>   
        <script src="${url_cz}js/fuelux/loader.js" type="text/javascript"></script>
        <form method="POST" data-form="true" data-success-url="${url}/musica" onkeypress="return event.keyCode != 13;"> 
            <link rel="stylesheet" type="text/css" href="${url_css}bootstrap-tagsinput.css" />
            <script src="${url_js}bootstrap-tagsinput.js" type="text/javascript"></script>

            <label>Categoria</label>
            <input type="text" name="categorias" value="${categoriasDaMusica}" data-role="tagsinput" />

            <script>
            var json = [ <c:forEach items="${categorias}" var="item" varStatus="vs"> '${item.nome}', </c:forEach> ];
                    jQuery('input').tagsinput({
                typeahead: {
                    source: json
                }
            });

            $('input').on('itemAdded', function(event) {
                
            });
                </script>


                <input type="hidden" name="musicaGeralBean.id" value="${musicaGeralBean.id}" />
            <input type="hidden" name="musicaGeralBean.categoriaGeral" value="0" />
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Arquivo</label>
                        <input type="text" class="form-control arq" name="musicaGeralBean.arquivo" value="${musicaGeralBean.arquivo}" placeholder="clique no botão ao lado para selecionar um arquivo" data-rule-required="true">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Gravadora</label>
                        <select class="select2" dara-rule-required="true" name="musicaGeralBean.gravadora">
                            <option value="0">Desconhecida</option>
                            <c:forEach items="${gravadoras}" var="grav">
                                <option value="${grav.id}" ${musicaGeralBean.gravadora eq grav.id ? 'selected="selected"':''}>${grav.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Título</label>
                        <input type="text" name="musicaGeralBean.titulo" class="form-control" placeholder="Título" value="${musicaGeralBean.titulo}" data-rule-required="true">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Interprete</label>
                        <input type="text" name="musicaGeralBean.interprete" class="form-control" placeholder="Interprete" value="${musicaGeralBean.interprete}" data-rule-required="true">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Tipo de interprete</label>
                        <select name="musicaGeralBean.tipoInterprete" class="select2" dara-rule-required="true">
                            <option value="1" ${musicaGeralBean.tipoInterprete eq 1 ? 'selected="selected"':''}>Masculino</option> 
                            <option value="2" ${musicaGeralBean.tipoInterprete eq 2 ? 'selected="selected"':''}>Feminino</option> 
                            <option value="3" ${musicaGeralBean.tipoInterprete eq 3 ? 'selected="selected"':''}>Grupo</option> 
                            <option value="4" ${musicaGeralBean.tipoInterprete eq 4 ? 'selected="selected"':''}>Instrumental</option> 
                            <option value="5" ${musicaGeralBean.tipoInterprete eq 5 ? 'selected="selected"':''}>Jingle</option>
                        </select>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label>Letra</label>
                        <textarea name="musicaGeralBean.letra" class="form-control" style="height: 150px;">${musicaGeralBean.letra}</textarea>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>BPM</label>
                        <input type="text" name="musicaGeralBean.bpm" class="form-control" placeholder="BPM" value="${musicaGeralBean.bpm}" data-rule-required="true" data-rule-number="true">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Tempo total</label>
                        <input type="text" name="musicaGeralBean.tempoTotal" class="form-control" placeholder="Tempo total" value="${musicaGeralBean.tempoTotal}" data-rule-required="true" data-mask="00:00">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Ano de gravação</label>
                        <input type="text" name="musicaGeralBean.anoGravacao" class="form-control" placeholder="Ano de gravação" value="${musicaGeralBean.anoGravacao}" data-rule-required="true" data-rule-number="true">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>1ª Afinidade</label>
                        <input type="text" name="musicaGeralBean.afinidade1" class="form-control" placeholder="1ª Afinidade" value="${musicaGeralBean.afinidade1}" >
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>2ª Afinidade</label>
                        <input type="text" name="musicaGeralBean.afinidade2" class="form-control" placeholder="2ª Afinidade" value="${musicaGeralBean.afinidade2}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>3ª Afinidade</label>
                        <input type="text" name="musicaGeralBean.afinidade3" class="form-control" placeholder="3ª Afinidade" value="${musicaGeralBean.afinidade3}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>4ª Afinidade</label>
                        <input type="text" name="musicaGeralBean.afinidade4" class="form-control" placeholder="4ª Afinidade" value="${musicaGeralBean.afinidade4}">
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-success btn_enviar">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>


        <style type="text/css">
            .auto_complete_words { list-style: none; }
            .auto_complete_words li { margin-left: -30px; }
            .acw_item label {color: red;}
            .highlight { background-color: #60C060; color: #FFF; }
            .acw {
                max-height: 300px;
            }
        </style>

        <script type="text/javascript">
            var json = [];
            <c:forEach items="${categorias}" var="item" varStatus="vs">
            json[${vs.index}] = {nome: "${item.nome}"};
            </c:forEach>

        </script>

        <script type="text/javascript">
            jQuery(document).ready(function() {
                jQuery('.btn_upload').on('click', function() {
//                    jQuery('[type="file"]').click();
                    var path = jQuery('.arq').val();
                    jQuery.ajax({
                        url: '${url}/musica/carrega-metadata?url=' + path,
                        type: 'GET',
                        success: function() {

                        },
                        error: function() {

                        }
                    });
                });

                jQuery('[type="file"]').on('change', function() {
                    var file = this.files[0];
                    console.log(this.files);
                    console.log(file.name);
                    console.log(file.type);
                    console.log(file.size);
                    console.log(file.lastModifiedDate);
                    console.log(file.lastModifiedDate.toLocaleDateString());
                    console.log(jQuery(this).val());


//                     jQuery.ajax({
//                         url : '${url}/musica/carrega-metadata?url='+path,
//                         type: 'GET',
//                         success: function(){
//                     
//                         },
//                         error: function(){
//                     
//                         }
//                     });
                });

                var html = '';
                for (i in json) {
                    var item = json[i];
                    html += '<li class="acw_item" data-id="' + item.id + '" data-text="' + item.nome + '">' + item.nome + '</li>';
                }
                jQuery('.auto_complete_words').html(html);
                jQuery('.cat').on('keyup', function(e) {
                    if (e.keyCode == 13) {
                        if (jQuery('.highlight').size() > 0) {
                            jQuery(this).val(jQuery('.highlight').parent('.acw_item').data('text'));
                            jQuery('[name="musicaGeralBean.categoriaGeral"]').val(jQuery('.highlight').parent('.acw_item').data('id'));
                            jQuery('[name="musicaGeralBean.categoriaGeral"]').attr('disabled', false);
                        } else {
                            jQuery('[name="musicaGeralBean.categoriaGeral"]').val('');
                            jQuery('[name="musicaGeralBean.categoriaGeral"]').attr('disabled', true);
                        }

                        jQuery(this).next().hide();
                        $('.acw_item').removeHighlight();
                        return false;
                    } else {
                        var val = jQuery(this).val();
                        $('.acw_item').removeHighlight();
                        $('.acw_item').highlight(val);
                    }
                });

                jQuery('.cat').on('blur', function(e) {
                    if (jQuery('.highlight').size() > 0) {
                        jQuery(this).val(jQuery('.highlight').parent('.acw_item').data('text'));
                        jQuery('[name="musicaGeralBean.categoriaGeral"]').val(jQuery('.highlight').parent('.acw_item').data('id'));
                        jQuery('[name="musicaGeralBean.categoriaGeral"]').attr('disabled', false);
                    } else {
                        jQuery('[name="musicaGeralBean.categoriaGeral"]').val('');
                        jQuery('[name="musicaGeralBean.categoriaGeral"]').attr('disabled', true);
                    }
                    jQuery('.cat').next().hide();
                });

                jQuery.fn.highlight = function(pat) {
                    function innerHighlight(node, pat) {

                        var skip = 0;
                        if (node.nodeType == 3) {
                            var pos = node.data.toUpperCase().indexOf(pat);
                            if (pos >= 0) {

                                jQuery('.cat').next().show();
                                var spannode = document.createElement('span');
                                spannode.className = 'highlight';
                                var middlebit = node.splitText(pos);
                                var endbit = middlebit.splitText(pat.length);
                                var middleclone = middlebit.cloneNode(true);
                                spannode.appendChild(middleclone);
                                middlebit.parentNode.replaceChild(spannode, middlebit);
                                skip = 1;
                                jQuery('.acw_item').hide();
                                jQuery('.highlight').parent('.acw_item').show();
                            }
                        }
                        else if (node.nodeType == 1 && node.childNodes && !/(script|style)/i.test(node.tagName)) {
                            for (var i = 0; i < node.childNodes.length; ++i) {
                                i += innerHighlight(node.childNodes[i], pat);
                            }
                        }
                        return skip;
                    }
                    return this.length && pat && pat.length ? this.each(function() {
                        innerHighlight(this, pat.toUpperCase());
                    }) : jQuery('.cat').next().hide();
                    this;
                };

                jQuery.fn.removeHighlight = function() {
                    return this.find("span.highlight").each(function() {
                        this.parentNode.firstChild.nodeName;
                        with (this.parentNode) {
                            replaceChild(this.firstChild, this);
                            normalize();
                        }
                    }).end();
                };
            });
        </script>
    </jsp:body>
</instore:template>
