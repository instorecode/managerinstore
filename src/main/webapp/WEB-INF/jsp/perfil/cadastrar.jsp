<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/perfil" class="btn btn-voltar btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/perfil">
            <input type="hidden" name="perfilBean.idperfil" value="${perfilBean.idperfil}" />

            <div class="row">
                <div class="col-md-9">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="perfilBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${perfilBean.nome}">
                    </div>
                </div>

                <div class="col-md-9">
                    <div class="form-group">
                        <label>Funcionalidades</label>
                        <ul class="funcionalidades">
                            <c:forEach items="${perfis.filhos}" var="item">
                                <li>
                                    <input type="checkbox" name="funcionalidadeID" value="${item.idfuncionalidade}" ${item.perfilTem ? 'checked="checked"' : ''} style="  width: 25px; height: 25px;" /> 
                                    <span style="  display: block; margin-top: -29px; margin-left: 35px;">${item.nome}</span>
                                    <c:if test="${null ne item.filhos and not empty item.filhos}">
                                        <c:forEach items="${item.filhos}" var="filho">
                                            <input type="checkbox" value="${filho.idfuncionalidade}" name="funcionalidadeID" ${filho.perfilTem ? 'checked="checked"' : ''} style="width: 25px; height: 25px;" /> 
                                            <span style="  display: block; margin-top: -29px; margin-left: 35px;">
                                                &nbsp;-&nbsp;-&nbsp;-&nbsp;-
                                                ${filho.nome}
                                            </span>
                                        </c:forEach>
                                    </c:if>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
        <style type="text/css">
            .funcionalidades , .funcionalidades * {list-style: none;}
            .funcionalidades { margin-left: -30px; }
        </style>

        <script type="text/javascript">
            
            jQuery(document).ready(function() {
                jQuery(document).on('change', '.chkTree', function() {
                    if (jQuery(this).is(':checked')) {
                        var parente = jQuery(this).parent('li').data('parente');
                        if (jQuery('#' + parente).length > 0) {
                            var chk = jQuery('#' + parente).children('.chkTree');
                            if (!chk.is(':checked')) {
                                jQuery('#' + parente).children('.chkTree').trigger('click');
                            }
                        }
                    } else {
                        if (jQuery(this).parent('li').children('ul').children('li').length > 0) {
                            var remarcar = false;
                            jQuery(this).parent('li').children('ul').children('li').each(function() {
                                var chk = jQuery(this).children('.chkTree');
                                if (chk.is(':checked')) {
                                    remarcar = true;
                                }
                            });

                            if (remarcar) {
                                jQuery(this).trigger('click');
                            }
                        }
                    }
                });
                /**
                var idperfil = '${perfilBean.idperfil}';
                jQuery.getJSON('${url}/utilidades/funcionalidadetree?idperfil=' + idperfil, function(data) {
                    recursiveNode(data, null);
                });

                function recursiveNode(jsonItem, el) {

                    var elm = null;
                    if (el == null) {
                        elm = jQuery('ul.funcionalidades');
                    } else {
                        var elm = jQuery('<ul />', {
                            id: el.attr('id'),
                        }).appendTo(el);
                    }

                    jQuery.each(jsonItem.filhos, function(key, item) {

                        var li = jQuery('<li />', {
                            id: +item.idfuncionalidade,
                        }).appendTo(elm);


                        var label = jQuery('<label />', {
                            id: "label" + item.idfuncionalidade,
                        }).appendTo(li);

                        var input = jQuery('<input />', {
                            id: 'input-funcionalidade-' + item.idfuncionalidade,
                            type: 'checkbox',
                            name: 'funcionalidadeID',
                            'class': 'chkTree icheck3',
                            checked: (item.perfilTem ? true : false),
                            value: item.idfuncionalidade
                        }).appendTo(label);

                        var input = jQuery('<span />', {
                            html: '&nbsp;&nbsp;&nbsp;&nbsp;' + item.nome
                        }).appendTo(li);

                        li.attr('data-parente', item.parente);
                        if (item.filhos.length > 0) {
                            recursiveNode(item, li);
                        }
                    });

                    jQuery('.icheck3').iCheck({
                        checkboxClass: 'icheckbox_square-blue checkbox',
                        radioClass: 'iradio_square-blue'
                    });
                }
            });
            **/
        </script>                    
    </jsp:body>
</instore:template>