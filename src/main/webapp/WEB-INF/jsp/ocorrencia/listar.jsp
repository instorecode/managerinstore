<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<instore:template isGrid="false">
    <jsp:body> 
       
        <c:set scope="session" var="form_access" value="${false}"></c:set>
        <c:set scope="session" var="update_access" value="${false}"></c:set>
        <c:set scope="session" var="delete_access" value="${false}"></c:set>

        <c:forEach items="${funcionalidadeBeanList}" var="func">
            <c:if test="${func.mappingId eq '/ocorrencia/cadastrar'}">
                <c:set scope="session" var="form_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/ocorrencia/atualizar/{id}'}">
                <c:set scope="session" var="update_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/ocorrencia/remover/{id}'}">
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
                        <form name="FORM_CADASTRO" method="POST" data-formtable="true" action="${url}/ocorrencia-origem/cadastrar">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Descri��o</label>
                                        <input type="text" name="ocorrenciaOrigemBean.descricao" class="form-control" placeholder="Descri��o"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3" 
                                               data-rule-maxlength="255" 
                                               value="${ocorrenciaOrigemBean.descricao}">
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
                        <h2>Acesso n�o permitdo a essa funcionaliade</h2>
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
                    <strong>Descri��o</strong>
                    <br />
                    <small field="descricao"></small>
                </li>
                <li>
                    <strong>Usu�rio que relatou o caso</strong>
                    <br />
                    <small field="usuario"></small>
                </li>
                <li>
                    <strong>Prioridade</strong>
                    <br />
                    <small field="ocorrenciaPrioridade"></small>
                </li>
                <li>
                    <strong>Situa��o</strong>
                    <br />
                    <small field="statusNome"></small>
                </li>
                <li>
                    <strong>Data de cadastro</strong>
                    <br />
                    <small field="dataCadastro"></small>
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
                <h2>Deseja remover  ?</h2>
                <hr />

                <form name="FORM_DELETAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/ocorrencia/remover/[[__PK__]]">
                    <input type="hidden" name="ocorrenciaOrigemBean.id" value="[[__PK__]]" />
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
           
            <a href="${url}/ocorrencia/cadastrar" class="btn btn-default btn-flat " style="margin-left: 0px;"><i class="fa fa-save"></i> Cadastrar</a>

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
                <span class="pag_info">P�gina 0 de 0</span>
            </div>
            <div class="content">
                <table  id="table" 
                        class="xtable" 
                        id="datatable" 
                        url="${url}/ocorrencia"
                        page="1"
                        size="0"
                        rows="10"
                        pk="id"
                        btn-edit-onclick="javascript:window.location.href='${url}/ocorrencia/atualizar/[[__PK__]]'"> 
                    <thead>
                        <tr>
                            <th options="true" class="options">#</th>
                            <th field="descricao" options="false">Descri��o</th>
                            <th field="usuario" options="false" isfk="true" fk="idusuario" fklabel="nome" fklabelselect="Todos" fkurl="${url}/ocorrencia?usuario=true">Usu�rio</th>
                            <th field="cliente" options="false" isfk="true" fk="idcliente" fklabel="nome" fklabelselect="Todos" fkurl="${url}/ocorrencia?clientes=true">Cliente</th>
                            <th field="statusNome" options="false" isfk="true" fk="idstatus" fklabel="descricao" fklabelselect="Todos" fkurl="${url}/ocorrencia?status=true">Situa��o</th>
                            <th field="prioridade" options="false" isfk="true" fk="idprioridade" fklabel="descricao" fklabelselect="Todos" fkurl="${url}/ocorrencia?prioridade=true">Prioridade</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </jsp:body>
</instore:template>