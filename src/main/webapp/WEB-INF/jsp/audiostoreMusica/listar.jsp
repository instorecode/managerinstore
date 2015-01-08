<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<instore:template isGrid="false">
    <jsp:body> 
        <c:set scope="session" var="form_access" value="${false}"></c:set>
        <c:set scope="session" var="update_access" value="${true}"></c:set>
        <c:set scope="session" var="delete_access" value="${true}"></c:set>

        <c:forEach items="${funcionalidadeBeanList}" var="func">
            <c:if test="${func.mappingId eq '/musica/programacao-audiostore/atualizar/{id}'}">
                <c:set scope="session" var="update_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/musica/programacao-audiostore/remover/{id}'}">
                <c:set scope="session" var="delete_access" value="${true}"></c:set>
            </c:if>
        </c:forEach>

        <!--FORM-->
        <div class="form">
            <div class="block">
                <div class="content">
                    <c:if test="${form_access}">

                    </c:if>

                    <c:if test="${not form_access}">
                        <br />
                        <h2>Acesso não permitdo a essa funcionaliade</h2>
                    </c:if>
                </div>
            </div>
        </div>

        <!--VIEW-->
        <div class="view"> 
            <br />
            <h2>Detalhes</h2>
            <hr />
            <ol class="view_itens" type="i">
                <li>
                    <strong>Identificador</strong>
                    <br />
                    <small field="id"></small>
                </li>
                <li>
                    <strong>Nome</strong>
                    <br />
                    <small field="nome"></small>
                </li>
                <li>
                    <strong>Cliente</strong>
                    <br />
                    <small field="nomeCliente"></small>
                </li>
                <li>
                    <strong>1º Categoria</strong>
                    <br />
                    <small field="categoria1"></small>
                </li>
                <li>
                    <strong>2º Categoria</strong>
                    <br />
                    <small field="categoria2"></small>
                </li>
                <li>
                    <strong>Letra</strong>
                    <br />
                    <small field="letra"></small>
                </li>
            </ol>
        </div>


        <!--ATUALIZAR-->
        <div class="edit">
            <c:if test="${update_access}">

            </c:if>

            <c:if test="${not update_access}">
                <br />
                <h2>Acesso não permitdo a essa funcionaliade</h2>
            </c:if>

        </div>

        <!--DELETE-->

        <div class="delete">
            <c:if test="${delete_access}">
                <br />
                <h2>Deseja remover  ?</h2>
                <hr />

                <form name="FORM_DELETAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/musica/programacao-audiostore/remover/[[__PK__]]">
                    <input type="hidden" name="ocorrenciaOrigemBean.id" value="[[__PK__]]" />
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

            <button type="button" class="btn btn-default btn-flat btn_cadastro" style="margin-left: 0px;"><i class="fa fa-save"></i> Cadastrar</button>

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
                <button type="button" class="btn btn-default btn-flat " id="botaoCsv" ><i class="fa fa-file-excel-o"></i></button>

                <button type="button" class="btn btn-default btn-flat btn_export btn_export1"><i class="fa fa-upload"></i> Exportar arquivo </button>
                <button type="button" class="btn btn-default btn-flat btn_export btn_export2"><i class="fa fa-upload"></i> Exportar arquivo com audio</button>
                <!--<button type="button" class="btn btn-default btn-flat btn_export btn_export2"><i class="fa fa-download"></i> Logs</button>-->
            </div>

            <div class="addon" style="display: none;">
                <a href="${url}/musica/programacao-audiostore/cadastrar/nulo?clonar=[[__PK__]]" class="btn btn-default btn-flat btn-xs" id="clonar" ><i class="fa fa-plus"></i></a>
            </div>

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
                        url="${url}/musica/programacao-audiostore"
                        page="1" 
                        size="0"
                        rows="10" 
                        pk="id"
                        btn-edit-onclick="javascript:window.location.href='${url}/musica/programacao-audiostore/atualizar/[[__PK__]]'"> 
                    <thead>
                        <tr>
                            <th options="true" class="options" data-html="">#</th>
                            <!--<th field="id" options="false">ID</th>-->
                            <th field="arquivo" options="false">Arquivo</th>
                            <th field="nome" options="false">Nome</th>
                            <th field="nomeCliente" isfk="true" fk="idcliente" fklabel="nome" fklabelselect="Todos" fkurl="${url}/musica/programacao-audiostore?clientes=true"  options="false"> Cliente </th>
                            <th field="categoria1" isfk="true" fk="codigo" fklabel="categoria" fklabelselect="Todos"  fkurl="${url}/musica/programacao-audiostore?categorias=true"  options="false"> Categoria </th>
                            <th field="ultimaImportacao" isfk="true" fk="bool" fklabel="label" fklabelselect="Todos"  fkurl="${url}/musica/programacao-audiostore?ultimp=true"  options="false"> Ultima importação </th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>

        

        <script type="text/javascript">
            jQuery(document).ready(function() {
                jQuery.ajaxSetup({
                    timeout: 1000 * 60 * 60 * 24
                });

                jQuery('.btn_export1').on("click", function() {
                    fnn(false);
                });

                jQuery('.btn_export2').on("click", function() {
                    fnn(true);
                });

                function fnn(exp_arquivo_audio) {

                    var cliente_selecionado = jQuery('[name="idcliente"]').val();

                    if (null == cliente_selecionado || undefined == cliente_selecionado || '' == cliente_selecionado) {
                        bootbox.alert("Selecione um cliente.", function() {
                        });
                    } else {
                        var arr = rowsSelected();
                        var id_list = new Array();
                        for (i in arr) {
                            var item = arr[i];
                            id_list[i] = item.id;
                        }

                        if (id_list.length <= 0) {
                            bootbox.confirm("Você não selecionou nenhuma música, deseja exportar todos os registros?", function(res) {
                                if (res) {
                                    jQuery.ajax({
                                        async: true,
                                        timeout: ((1000 * 60) * 10),
                                        type: 'POST',
                                        url: '${url}/musica/vld-msc',
                                        dataType: "json",
                                        beforeSend: function() {
                                            msg_fadeIn();
                                        },
                                        data: {
                                            id_list: id_list,
                                            idcliente: cliente_selecionado,
                                            arquivo: jQuery('[name="arquivo"]').val(),
                                            nome: jQuery('[name="nome"]').val(),
                                            codigo: jQuery('[name="codigo"]').val(),
                                            exp_arquivo_audio: exp_arquivo_audio
                                        },
                                        success: function(json) {
                                            console.log("JSON");
                                            console.log(json);
                                            if (json.success) {
                                                qtdeRequests = json.object;
                                            } else {
                                                bootbox.alert(json.response, function() {
                                                });
                                            }
                                        },
                                        error: function(resp) {
                                            bootbox.alert('Lamento, ocorreu um erro.', function() {
                                            });
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
                                async: true,
                                timeout: ((1000 * 60) * 10),
                                type: 'POST',
                                url: '${url}/musica/vld-msc',
                                dataType: "json",
                                beforeSend: function() {
                                    msg_fadeIn();
                                },
                                data: {
                                    id_list: id_list,
                                    idcliente: cliente_selecionado,
                                    arquivo: jQuery('[name="arquivo"]').val(),
                                    nome: jQuery('[name="nome"]').val(),
                                    codigo: jQuery('[name="codigo"]').val(),
                                    exp_arquivo_audio: exp_arquivo_audio
                                },
                                success: function(json) {
                                    console.log("JSON");
                                    console.log(json);
                                    if (json.success) {
                                        qtdeRequests = json.object;
                                    } else {
                                        bootbox.alert(json.response, function() {
                                        });
                                    }
                                },
                                error: function(resp) {
                                    bootbox.alert('Lamento, ocorreu um erro.', function() {
                                    });
                                },
                                complete: function() {
                                    msg_fadeOut();
                                },
                            });
                        }
                    }

                }
            });
        </script>
    </jsp:body>
</instore:template>