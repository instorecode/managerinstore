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
        <div class="form">
            
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
                    <small field="codigo"></small>
                </li>
                <li>
                    <strong>Cliente</strong>
                    <br />
                    <small field="clienteNome"></small>
                </li>
                <li>
                    <strong>Categoria</strong>
                    <br />
                    <small field="programacao"></small>
                </li>
                <li>
                    <strong>Data inicial</strong>
                    <br />
                    <small field="dataInicio"></small>
                </li>
                <li>
                    <strong>Data final</strong>
                    <br />
                    <small field="dataFinal"></small>
                </li>
                <li>
                    <strong>Dura��o</strong>
                    <br />
                    <small field="tempo"></small>
                </li>
                <li>
                    <strong>Tipo</strong>
                    <br />
                    <small field="tipo"></small>
                </li>
            </ol>
        </div>


        <!--ATUALIZAR-->
        <div class="edit">
           

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
                <h2>Acesso n�o permitdo a essa funcionaliade</h2>
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
            <button type="button" class="btn btn-default btn-flat btn_export" style="display: none;"><i class="fa fa-upload"></i> Exportar arquivo </button>
            <div class="addon" style="display: none;">
                <a href="${url}/audiostore-comercial/cadastrar?clonar=[[__PK__]]" class="btn btn-default btn-flat btn-xs" id="clonar" ><i class="fa fa-repeat"></i></a>
            </div>
            &nbsp;
            &nbsp;
            <div class="btn-group">
                <button class="btn btn-default btn-flat _prev"> <i class="fa fa-angle-double-left"></i> </button>
                <button class="btn btn-default btn-flat prev"> <i class="fa fa-angle-left"></i> </button>
                <button class="btn btn-default btn-flat next"> <i class="fa fa-angle-right"></i> </button>
                <button class="btn btn-default btn-flat _next"> <i class="fa fa-angle-double-right"></i> </button>
                <button type="button" class="btn btn-default btn-flat btn_refresh"><i class="fa fa-refresh"></i></button>
                <span class="pag_info">P�gina 0 de 0</span>
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
                            <th field="titulo" options="false">T�tulo</th>
                            <th field="arquivo" options="false">Arquivo</th>
                           
                            <!--<th field="tempo" options="false">Dura��o</th>-->
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
                
                jQuery(document).on("selected", ".row_data", function(evt, item) {
                    jQuery('.btn_export').show();
                }).on("unselected", ".row_data", function(evt, item) {
                    if (countRowsSelected() == 0) {
                        jQuery('.btn_export').hide();
                    }
                });

                jQuery('.btn_export').on("click", function() {
                    msg_fadeIn();
                    if (countRowsSelected() <= 0) {
                        bootbox.alert("Selecione no minimo um registro na tabela.", function() {
                        });
                    } else {
                        
                        var arr = rowsSelected();
                        var id_list = new Array();
                        for (i in arr) {
                            var item = arr[i];
                            id_list[i] = item.id;
                        }
                        jQuery.ajax({
                            type: 'POST',
                            url : '${url}/audiostore-comercial/vld-comm',
                            data:{ id_list : id_list },
                            success : function(json){
                                if(!json.success) {
                                    dialogAjax(json.response);
                                } else {
                                    
                                }
                            }
                        });
                    }
                    msg_fadeOut();
                });
                
            });
        </script>
    </jsp:body>
</instore:template>