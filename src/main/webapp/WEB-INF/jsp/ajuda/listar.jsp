<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<instore:template isGrid="false">
    <jsp:body>
        <c:set scope="session" var="form_access" value="${false}"></c:set>
        <c:set scope="session" var="update_access" value="${false}"></c:set>
        <c:set scope="session" var="delete_access" value="${false}"></c:set>
        <c:forEach items="${funcionalidadeBeanList}" var="func">
            <c:if test="${func.mappingId eq '/voz/cadastrar'}">
                <c:set scope="session" var="form_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/voz/atualizar/{id}'}">
                <c:set scope="session" var="update_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/voz/remover/{id}'}">
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
                        <form name="FORM_CADASTRO" method="POST" data-formtable="true" action="${url}/voz/cadastrar">
                            <div class="row">                                                                    
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>Cliente</label>
                                        <select  class="select2 select_cliente" name="vozBean.cliente.idcliente" data-rule-required="true" >
                                            <c:forEach items="${atalhoClienteList}" var="cliente">
                                                <option value="${cliente.idcliente}" ${cliente.idcliente eq vozBean.cliente.idcliente ? 'selected="selected"' : ''}>${cliente.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>Nome</label>
                                        <input type="text" name="vozBean.nome" class="form-control" placeholder="Nome"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               data-rule-maxlength="255" value="${voz.nome}">
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>Género</label>
                                        <select data-selectradio="true" class="form-control" name="vozBean.genero" data-rule-required="true" >
                                            <option value="${true}"  ${vozBean.genero ? 'selected="selected"' :''} >Masculino</option>
                                            <option value="${false}" ${not vozBean.genero ? 'selected="selected"' :''} >Feminino</option>
                                        </select>
                                    </div>
                                </div>  
                            </div>                                        
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Email</label>
                                        <input type="text" name="vozBean.email" class="form-control" placeholder="Email"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               data-rule-maxlength="255" value="${voz.email}">
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Telefone</label>
                                        <input type="text" name="vozBean.tel" class="form-control" placeholder="Telefone"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               data-rule-maxlength="255" value="${voz.telefone}">
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
                    <strong>Titulo</strong>
                    <br />
                    <small field="titulo"></small>
                </li>
                
            </ol>
        </div>


        <!--ATUALIZAR-->
        <div class="edit">
            <c:if test="${update_access}">
                <br />
                <h2>Atualizar dados</h2>
                <hr />

                <form name="FORM_ALTERAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/voz/cadastrar" >
                    <input type="hidden" name="vozBean.idvoz" value="${vozBean.idvoz}" field="idvoz" />

                    <div class="row">                                                                    
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Cliente</label>
                                <select  class="select2 select_cliente" name="vozBean.cliente.idcliente" data-rule-required="true" >
                                    <c:forEach items="${clienteBeanList}" var="cliente">
                                        <option value="${cliente.idcliente}" ${cliente.idcliente eq vozBean.cliente.idcliente ? 'selected="selected"' : ''}>${cliente.nome}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Nome</label>
                                <input type="text" name="vozBean.nome" class="form-control" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" field="nome" value="${voz.nome}">
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Género</label>
                                <select data-selectradio="true" class="form-control" name="vozBean.genero" data-rule-required="true" >
                                    <option value="${true}"  ${vozBean.genero ? 'selected="selected"' :''} >Masculino</option>
                                    <option value="${false}" ${not vozBean.genero ? 'selected="selected"' :''} >Feminino</option>
                                </select>
                            </div>
                        </div>  
                    </div>                                        
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Email</label>
                                <input type="text" name="vozBean.email" class="form-control" placeholder="Email"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" field="email" value="${voz.email}">
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Telefone</label>
                                <input type="text" name="vozBean.tel" class="form-control" placeholder="Telefone"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="255" field="tel" value="${voz.telefone}">
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
                <h2>Deseja remover a voz ?</h2>
                <hr />

                <form name="FORM_DELETAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/voz/remover/[[__PK__]]">
                    <input type="hidden" name="vozBean.idvoz" value="[[__PK__]]" />
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
            <div class="mask_message" style="display:none;">
                <div class="text">
                    <img src="${url_img}486.GIF">
                    &nbsp; Aguarde, processando dados...
                </div>
            </div>
            <div class="loader">
                <span class="text">
                    <span class="fa fa-refresh"></span>&nbsp;&nbsp;Aguarde...
                </span>
            </div>

            <button type="button" class="btn btn-default btn-flat btn_cadastro" style="margin-left: 0px;" ><i class="fa fa-save"></i>Cadastrar</button>        

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
                <button type="button" class="btn btn-default btn-flat" id="botaoCsv" ><i class="fa fa-file-excel-o"></i></button>
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
                <table id="table" 
                       class="xtable" 
                       id="datatable" 
                       url="${url}/ajuda"
                       page="1"
                       size="0"
                       rows="10"
                       pk="id">
                    <thead>
                        <tr>
                            <th options="true" class="options">#</th>
                            <th field="titulo" options="false" >Titulo</th>
                            <th field="funcionalidade" isfk="true" fk="" ></th>
                        </tr>
                    </thead>                     
                </table>
            </div>  
        </div>
    </jsp:body>
</instore:template>        