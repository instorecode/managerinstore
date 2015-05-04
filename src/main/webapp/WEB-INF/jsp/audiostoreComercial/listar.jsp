<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 

<instore:template isGrid="false">
    <jsp:body> 
        <c:set scope="session" var="form_access" value="${false}"></c:set>
        <c:set scope="session" var="update_access" value="${false}"></c:set>
        <c:set scope="session" var="delete_access" value="${false}"></c:set>

        <c:forEach items="${funcionalidadeBeanList}" var="func">
            <c:if test="${func.mappingId eq '/audiostore-comercial/cadastrar'}">
                <c:set scope="session" var="form_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/audiostore-comercial/atualizar/{id}'}">
                <c:set scope="session" var="update_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/audiostore-comercial/remover/{id}'}">
                <c:set scope="session" var="delete_access" value="${true}"></c:set>
            </c:if>
        </c:forEach>

        <!--FORM-->
        <div class="form"></div>

        <!--VIEW-->
        <div class="view"> 
            <br />
            <h2>Detalhes</h2>
            <hr />
            <ol class="view_itens" type="i">
                <li>
                    <strong>Cliente</strong>
                    <br />
                    <small field="clienteNome"></small>
                </li>
                <li>
                    <strong>Categoria</strong>
                    <br />
                    <small field="categoriaNome"></small>
                </li>
                <li>
                    <strong>1º Dependencia</strong>
                    <br />
                    <small field="dependencia1"></small>
                </li>
                <li>
                    <strong>2º Dependencia</strong>
                    <br />
                    <small field="dependencia3"></small>
                </li>
                <li>
                    <strong>3º Dependencia</strong>
                    <br />
                    <small field="dependencia3"></small>
                </li>
                <li>
                    <strong>Dias alternados</strong>
                    <br />
                    <small field="diasAlternados"></small>
                </li>
            </ol>
        </div>


        <!--ATUALIZAR-->
        <div class="edit">
            <form name="FORM_ATUALIZAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/audiostore-comercial/atualizar/[[__PK__]]">
            </form>
        </div>

        <!--DELETE-->

        <div class="delete">
            <c:if test="${delete_access}">
                <br />
                <h2>Deseja remover ?</h2>
                <hr />

                <form name="FORM_DELETAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/audiostore-comercial/remover/[[__PK__]]">
                    <input type="hidden" name="audiostoreProgramacaoBean.id" value="[[__PK__]]" />
                    <button type="submit" class="btn btn-danger">
                        <i class="fa fa-thumbs-o-down"></i> Remover
                    </button>
                </form>
            </c:if>

            <c:if test="${not delete_access}">
                <br />
                <h2>Acesso não permitdo a essa funcionaliade</h2>
            </c:if>
        </div>


        <!--TABLE-->
        <div class="block-xtable">
            <div class="mask_message" style="display: none;">
                <div class="text">
                    <img src="${url_img}486.GIF" />
                    &nbsp; Aguarde, processando dados...
                </div>
            </div>


            <a href="${url}/audiostore-comercial/cadastrar" class="btn btn-default btn-flat btn_cadastro" style="margin-left: 0px;"><i class="fa fa-save"></i> Cadastrar</a>

            <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle btn-flat" data-toggle="dropdown">
                    Qtd.<span class="caret"></span>
                </button>
                <ul class="dropdown-menu qtd" role="menu">
                    <li><a href="10">10</a></li>
                    <li><a href="20">20</a></li>
                    <li><a href="30">30</a></li>
                    <li><a href="50">50</a></li>
                    <li><a href="100">100</a></li>
                </ul>
            </div>
            <button type="button" class="btn btn-default btn-flat btn_export btn_export1" ><i class="fa fa-upload"></i> Exportar fichas </button>
            <button type="button" class="btn btn-default btn-flat btn_export btn_export2" ><i class="fa fa-upload"></i> Exportar fichas arquivos com audio</button>
            <!--            <div class="addon" style="display: none;">
                            <a href="${url}/audiostore-comercial/cadastrar?clonar=[[__PK__]]" class="btn btn-default btn-flat btn-xs" id="clonar" ><i class="fa fa-repeat"></i></a>
                        </div>-->

            &nbsp;
            &nbsp;

            <div class="btn-group">
                <button class="btn btn-default btn-flat _prev"> <i class="fa fa-angle-double-left"></i> </button>
                <button class="btn btn-default btn-flat prev"> <i class="fa fa-angle-left"></i> </button>
                <button class="btn btn-default btn-flat next"> <i class="fa fa-angle-right"></i> </button>
                <button class="btn btn-default btn-flat _next"> <i class="fa fa-angle-double-right"></i> </button>
                <button type="button" class="btn btn-default btn-flat btn_refresh"><i class="fa fa-refresh"></i></button>
                <span class="pag_info">Página 0 de 0</span>
            </div>

            <div class="content">
                <table  id="table" 
                        class="xtable" 
                        id="datatable" 
                        url="${url}/audiostore-comercial"
                        page="1"
                        size="0"
                        rows="10"
                        pk="id"                
                        btn-edit-onclick="javascript:window.location.href='${url}/audiostore-comercial/atualizar/[[__PK__]]'"> 
                    <thead>
                        <tr> 
                            <th options="true" class="options">#</th>
                            <th field="titulo" options="false">Título</th>
                            <th field="arquivo" options="false">Arquivo</th>
                            <th field="categoriaNome" isfk="true" fk="codigo" fklabel="categoria" fklabelselect="Todos"  fkurl="${url}/audiostore-comercial?categorias=true"  options="false"> Categoria </th>
                            <!--<th field="clienteNome" isfk="true" fk="idcliente" fklabel="nome" fklabelselect="Todos"  fkurl="${url}/audiostore-comercial?clientes=true"  options="false"> Cliente </th>-->
                            <!--<th field="tempo" options="false">Duração</th>-->
                        </tr>
                    </thead>
                </table>
            </div>
        </div>

        <div class="progress-mask"></div>

        <style type="text/css">
            .progress-mask {
                display: block;
                width: 100%;
                height: 100%;

                background-color: rgba(0,0,0,0.5);
            }
        </style>

        <script type="text/javascript">
            jQuery(document).ready(function() {

                jQuery('.btn_export1').on("click", function() {
                    fnn(false);
                });

                jQuery('.btn_export2').on("click", function() {
                    fnn(true);
                });

                function fnn(exp_arquivo_audio) {
                    var cliente_selecionado = 0;
                    msg_fadeIn();
                    var arr = rowsSelected();
                        var id_list = new Array();
                        for (i in arr) {
                            var item = arr[i];
                            id_list[i] = item.id;
                        }


                        if (id_list.length <= 0) {
                            bootbox.confirm("Você não selecionou nenhum comercial, deseja exportar todos os registros?", function(res) {
                                if (res) {
                                    jQuery.ajax({
                                        type: 'POST',
                                        url: '${url}/audiostore-comercial/vld-comm',
                                        beforeSend: function() {
                                            msg_fadeIn();
                                        },
                                        data: {
                                            id_list: id_list,
                                            idcliente: cliente_selecionado,
                                            titulo: jQuery('[name="titulo"]').val(),
                                            arquivo: jQuery('[name="arquivo"]').val(),
                                            codigo: jQuery('[name="codigo"]').val(),
                                            exp_arquivo_audio: exp_arquivo_audio
                                        },
                                        success: function(json) {
                                            console.log(json);
                                        },
                                        error: function(resp) {
                                            console.log(resp);
                                        },
                                        complete: function() {
                                            msg_fadeOut();
                                        },
                                    });
                                }
                                return true;
                            });
                        } else {
                            jQuery.ajax({
                                type: 'POST',
                                url: '${url}/audiostore-comercial/vld-comm',
                                beforeSend: function() {
                                    msg_fadeIn();
                                },
                                data: {
                                    id_list: id_list,
                                    idcliente: cliente_selecionado,
                                    titulo: jQuery('[name="titulo"]').val(),
                                    arquivo: jQuery('[name="arquivo"]').val(),
                                    codigo: jQuery('[name="codigo"]').val(),
                                    exp_arquivo_audio: exp_arquivo_audio
                                },
                                success: function(json) {
                                    console.log(json);
                                },
                                error: function(resp) {
                                    console.log(resp);
                                },
                                complete: function() {
                                    msg_fadeOut();
                                },
                            });
                        }

                    
                    msg_fadeOut();
                }
            });
        </script>
    </jsp:body>
</instore:template>