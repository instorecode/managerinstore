<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<instore:template isGrid="false">
    <jsp:body> 

        <c:set scope="session" var="form_access" value="${false}"></c:set>
        <c:set scope="session" var="update_access" value="${false}"></c:set>
        <c:set scope="session" var="delete_access" value="${false}"></c:set>

        <c:forEach items="${funcionalidadeBeanList}" var="func">
            <c:if test="${func.mappingId eq '/ocorrencia-status/cadastrar'}">
                <c:set scope="session" var="form_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/ocorrencia-status/atualizar/{id}'}">
                <c:set scope="session" var="update_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/ocorrencia-status/remover/{id}'}">
                <c:set scope="session" var="delete_access" value="${true}"></c:set>
            </c:if>
        </c:forEach>

        <!--FORM-->
        <div class="form">
            <div class="block">
                <div class="content">
                    <c:if test="${form_access}">
                        <br />
                        <h2>Cadastro</h2>
                        <hr />
                        <form name="FORM_CADASTRO" method="POST" data-formtable="true" action="${url}/ocorrencia-status/cadastrar">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Descrição</label>
                                        <input type="text" name="ocorrenciaStatusBean.descricao" class="form-control" placeholder="Nome"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               data-rule-maxlength="255" value="${ocorrenciaStatusBean.descricao}">
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Cor</label>
                                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#4D90FD" ${ocorrenciaStatusBean.cor eq '#4D90FD' ? 'checked="checked"':''}  class="icheck"> Azul </label> 
                                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#54A754" ${ocorrenciaStatusBean.cor eq '#54A754' ? 'checked="checked"':''} class="icheck"> Verde </label> 
                                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#ffa800" ${ocorrenciaStatusBean.cor eq '#ffa800' ? 'checked="checked"':''} class="icheck"> Amarelo </label> 
                                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#e64d35" ${ocorrenciaStatusBean.cor eq '#e64d35' ? 'checked="checked"':''} class="icheck"> Vermelho </label> 
                                        <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#000000" ${ocorrenciaStatusBean.cor eq '#000000' ? 'checked="checked"':''} class="icheck"> Preto </label> 
                                    </div>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-success btn-flat" style="margin-left: 0px;">
                                <i class="fa fa-save"></i> Salvar
                            </button>
                        </form>
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
                    <strong>Descrição</strong>
                    <br />
                    <small field="descricao"></small>
                </li>
            </ol>
        </div>


        <!--ATUALIZAR-->
        <div class="edit">
            <c:if test="${update_access}">
                <br />
                <h2>Atualizar dados</h2>
                <hr />

                <form name="FORM_ALTERAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/ocorrencia-status/cadastrar"  callback="callback" reload="true">
                    <input type="hidden" name="ocorrenciaStatusBean.id" value="${ocorrenciaStatusBean.id}" field="id" />

                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Descrição</label>
                                <input type="text" name="ocorrenciaStatusBean.descricao" class="form-control" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" field="descricao">
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Cor</label>
                                <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#4D90FD" ${ocorrenciaStatusBean.cor eq '#4D90FD' ? 'checked="checked"':''} class="icheck2"> Azul </label> 
                                <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#54A754" ${ocorrenciaStatusBean.cor eq '#54A754' ? 'checked="checked"':''} class="icheck2"> Verde </label> 
                                <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#ffa800" ${ocorrenciaStatusBean.cor eq '#ffa800' ? 'checked="checked"':''} class="icheck2"> Amarelo </label> 
                                <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#e64d35" ${ocorrenciaStatusBean.cor eq '#e64d35' ? 'checked="checked"':''} class="icheck2"> Vermelho </label> 
                                <label class="radio-inline"> <input type="radio" name="ocorrenciaStatusBean.cor" value="#000000" ${ocorrenciaStatusBean.cor eq '#000000' ? 'checked="checked"':''} class="icheck2"> Preto </label> 
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-success btn-flat" style="margin-left: 0px;">
                        <i class="fa fa-save"></i> Salvar
                    </button>
                </form>
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
                <h2>Deseja remover a origem ?</h2>
                <hr />

                <form name="FORM_DELETAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/ocorrencia-status/remover/[[__PK__]]">
                    <input type="hidden" name="ocorrenciaStatusBean.id" value="[[__PK__]]" />
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
            <div class="loader">
                <span class="txt">
                    <span class="fa fa-refresh"></span>&nbsp;&nbsp;Aguarde...
                </span>
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
                        url="${url}/ocorrencia-status"
                        page="1"
                        size="0"
                        rows="10"
                        pk="id"> 
                    <thead>
                        <tr>
                            <th options="true" class="options">#</th>
                            <!--<th field="id" options="false">ID</th>-->
                            <th field="descricao" options="false">Descrição</th>
                            <th field="cor" options="false">Cor</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>

        <script type="text/javascript">
            function callback(item) {
                console.log('[value="' + item.cor + '"]');
                jQuery('[value="' + item.cor + '"]').attr("checked", true);
                jQuery('[value="' + item.cor + '"]').iCheck('destroy');
                jQuery('[value="' + item.cor + '"]').iCheck({
                    checkboxClass: 'icheckbox_square-blue checkbox',
                    radioClass: 'iradio_square-blue'
                });
                jQuery('[value="' + item.cor + '"]').iCheck('check');
            }
        </script>
    </jsp:body>
</instore:template>