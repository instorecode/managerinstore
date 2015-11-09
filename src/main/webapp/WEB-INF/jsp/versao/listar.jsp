<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="CustomFunctions" %> 

<instore:template isGrid="false">
    <jsp:body>
        <c:set scope="session" var="form_access" value="${false}"></c:set>
        <c:set scope="session" var="update_access" value="${false}"></c:set>
        <c:set scope="session" var="delete_access" value="${false}"></c:set>
        <c:set scope="session" var="msg_not_permission" value="Acesso n�o permitdo a essa funcionaliade"></c:set>

        <c:forEach items="${funcionalidadeBeanList}" var="func">
            <c:if test="${func.mappingId eq '/versao/cadastrar'}">
                <c:set scope="session" var="form_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/versao/atualizar/{id}'}">
                <c:set scope="session" var="update_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/versao/remover/{id}'}">
                <c:set scope="session" var="delete_access" value="${true}"></c:set>                
            </c:if>
        </c:forEach>

        <!--FORM-->
        <c:if test="${countProjetos eq 0}">
            <c:set scope="session" var="form_access" value="${false}"></c:set>
            <c:set scope="session" var="msg_not_permission" value="N�o � poss�vel cadastrar uma vers�o sem existir algum projeto"></c:set>
        </c:if>

        <div class="form">
            <div class="block">
                <div class="content">
                    <c:if test="${form_access}">
                        <br />
                        <h2>Cadastro</h2>
                        <hr />
                        <form name="FORM_CADASTRO" method="POST" data-formtable="true" action="${url}/versao/cadastrar">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Projeto</label>
                                        <select class="select2" name="versaoBean.idProjeto" class="form-control" data-rule-required="true">
                                            <c:forEach items="${projetos}" var="projeto">
                                                <option value="${projeto.id}">${projeto.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Nome</label>
                                        <input type="text" name="versaoBean.nome" class="form-control" placeholder="Nome"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               data-rule-maxlength="255" value="${versaoBean.nome}">
                                    </div>
                                </div>   

                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Descri��o</label>
                                        <input type="text" name="versaoBean.descricao" class="form-control" placeholder="Descri��o"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               data-rule-maxlength="255" value="${versaoBean.descricao}">
                                    </div>
                                </div>              

                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Link Documenta��o</label>
                                        <input type="text" name="versaoBean.linkSvn" class="form-control" placeholder="Link SVN"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               data-rule-maxlength="255" value="${versaoBean.linkSvn}">
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
                        <h2><c:out value="${msg_not_permission}"></c:out></h2>
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
                    <strong>Descri��o</strong>
                    <br />
                    <small field="descricao"></small>
                </li>
                <li>
                    <strong>SVN</strong>
                    <br />
                    <small field="linkSvn"></small>
                </li>
            </ol>
        </div>

        <!--ATUALIZAR-->
        <div class="edit"> 
            <c:if test="${update_access}">
                <br />
                <h2>Atualizar dados</h2>
                <hr />
                <form name="FORM_ALTERAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/versao/atualizar/[[__PK__]]"  reload="true">
                    <input type="hidden" name="versaoBean.id" field="id" />

                    <div class="row">                                      
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Nome</label>
                                <input type="text" name="versaoBean.nome" class="form-control" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" field="nome">
                            </div>
                        </div>   
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Descri��o</label>
                                <input type="text" name="versaoBean.descricao" class="form-control" placeholder="Descri��o"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" field="descricao">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Link SVN</label>
                                <input type="text" name="versaoBean.linkDocumentacao" class="form-control" placeholder="Link SVN"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" field="linkSvn">
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
                <h2>Acesso n�o permitdo a essa funcionaliade</h2>
            </c:if>
        </div>

        <!--DELETE-->
        <div class="delete">
            <c:if test="${delete_access}">
                <br />
                <h2>Deseja remover ?</h2>
                <hr />

                <form name="FORM_DELETAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/versao/remover/[[__PK__]]">
                    <input type="hidden" name="versaoBean.id" value="[[__PK__]]" />
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
                <span class="pag_info">P�gina 0 de 0</span>
            </div>
            <div class="content">
                <table  id="table" 
                        class="xtable"  
                        url="${url}/versoes"
                        page="1"
                        size="0"
                        rows="10"
                        pk="id"> 
                    <thead>
                        <tr>
                            <th options="true" class="options">#</th>
                            <th field="nome" options="false">Nome</th>
                            <th field="descricao" options="false">Descri��o</th>
                            <th field="linkSvn" options="false">SVN</th>                            
                        </tr>
                    </thead>
                </table>
            </div>
        </div>

    </jsp:body>    
</instore:template>>    