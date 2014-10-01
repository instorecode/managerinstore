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
            <c:if test="${func.mappingId eq '/audiostore-categoria/cadastrar'}">
                <c:set scope="session" var="form_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/audiostore-categoria/atualizar/{id}'}">
                <c:set scope="session" var="update_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/audiostore-categoria/remover/{id}'}">
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
                        <form name="FORM_CADASTRO" method="POST" data-formtable="true" action="${url}/audiostore-categoria/cadastrar">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <label>Codigo</label>
                                        <input type="text" name="audiostoreCategoriaBean.codInterno" class="form-control" placeholder="Código"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               data-rule-maxlength="3"
                                               data-rule-number="true"
                                               data-mask="000" field="codInterno" value="">
                                    </div>
                                </div>

                                <div class="col-md-7">
                                    <div class="form-group"> 
                                        <label>Nome</label>
                                        <input type="text" name="audiostoreCategoriaBean.categoria" class="form-control" placeholder="Nome"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3"
                                               data-rule-maxlength="30" field="categoria" value="">
                                    </div>
                                </div>

                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Cliente</label>
                                        <select field="idcliente"  class="select_cliente select2" name="audiostoreCategoriaBean.cliente.idcliente" data-rule-required="true" >
                                            <c:forEach items="${clienteBeanList}" var="cliente">
                                                <option value="${cliente.idcliente}" ${cliente.idcliente eq audiostoreCategoriaBean.cliente.idcliente ? 'selected="selected"' : ''}>${cliente.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Data de inicio</label>
                                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                            <input type="text" name="audiostoreCategoriaBean.dataInicio" class="form-control datepicker" placeholder="Data de inicio"  
                                                   data-rule-required="true" 
                                                   data-rule-minlength="3"
                                                   data-rule-maxlength="255" 
                                                   data-mask="99/99/9999"
                                                   field="dataInicio"
                                                   value="${cf:dateCurrent("dd/MM/yyyy")}">
                                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Data de termino</label>
                                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                            <input type="text" name="audiostoreCategoriaBean.dataFinal" class="form-control datepicker" placeholder="Data de termino"  
                                                   data-rule-required="true" 
                                                   data-rule-minlength="3"
                                                   data-rule-maxlength="255" 
                                                   data-mask="99/99/9999"
                                                   field="dataFinal"
                                                   value="31/12/2050">

                                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Tipo</label>
                                        <select field="tipoNum" class="select2" name="audiostoreCategoriaBean.tipo" data-rule-required="true">
                                            <option value="1" ${audiostoreCategoriaBean.tipo eq 1 ? 'selected="selected"' : ''}>Mùsica</option>
                                            <option value="2" ${audiostoreCategoriaBean.tipo eq 2 ? 'selected="selected"' : ''}>Comercial</option>
                                            <option value="3" ${audiostoreCategoriaBean.tipo eq 3 ? 'selected="selected"' : ''}>Video</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Tempo de duração</label>
                                        <input type="text" name="tempo" class="form-control" placeholder="Tempo de duração"  
                                               data-rule-required="true" 
                                               data-rule-minlength="8"
                                               data-rule-maxlength="8" 
                                               data-mask="99:99:99"
                                               field="tempo"
                                               value="00:00:00"> 

                                    </div>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-success btn-flat" style="margin-left: 0px;"><i class="fa fa-save"></i>Salvar</button>
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
                    <small field="categoria"></small>
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
                    <strong>Duração</strong>
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
            <c:if test="${update_access}">
                <br />
                <h2>Atualizar dados</h2>
                <hr />

                <form name="FORM_ALTERAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/audiostore-categoria/cadastrar">
                    <input type="hidden" name="audiostoreCategoriaBean.codigo" value="[[__PK__]]" />

                    <div class="row">
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>Codigo</label>
                                <input type="text" name="audiostoreCategoriaBean.codInterno" class="form-control" placeholder="Código"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="3"
                                       data-rule-number="true"
                                       data-mask="000" field="codInterno" value="">
                            </div>
                        </div>
                        <div class="col-md-7">
                            <div class="form-group">
                                <label>Nome</label>
                                <input type="text" name="audiostoreCategoriaBean.categoria" class="form-control" placeholder="Nome"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3"
                                       data-rule-maxlength="30" field="categoria" value="">
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Cliente</label>
                                <select field="idcliente"  class="select_cliente" name="audiostoreCategoriaBean.cliente.idcliente" data-rule-required="true" >
                                    <option value>Selecione um cliente</option>
                                    <c:forEach items="${clienteBeanList}" var="cliente">
                                        <option value="${cliente.idcliente}" ${cliente.idcliente eq audiostoreCategoriaBean.cliente.idcliente ? 'selected="selected"' : ''}>${cliente.nome}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Data de inicio</label>
                                <div class="input-group date inner_datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                    <input type="text" name="audiostoreCategoriaBean.dataInicio" class="form-control datepicker" placeholder="Data de inicio"  
                                           data-rule-required="true" 
                                           data-rule-minlength="3"
                                           data-rule-maxlength="255" 
                                           data-mask="99/99/9999"
                                           field="dataInicio"
                                           value="">
                                    <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Data de termino</label>
                                <div class="input-group date inner_datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                    <input type="text" name="audiostoreCategoriaBean.dataFinal" class="form-control datepicker" placeholder="Data de termino"  
                                           data-rule-required="true" 
                                           data-rule-minlength="3"
                                           data-rule-maxlength="255" 
                                           data-mask="99/99/9999"
                                           field="dataFinal"
                                           value="">

                                    <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Tipo</label>
                                <select field="tipoNum" name="audiostoreCategoriaBean.tipo" data-rule-required="true">
                                    <option value>Selecione um tipo</option>
                                    <option value="1" ${audiostoreCategoriaBean.tipo eq 1 ? 'selected="selected"' : ''}>Mùsica</option>
                                    <option value="2" ${audiostoreCategoriaBean.tipo eq 2 ? 'selected="selected"' : ''}>Comercial</option>
                                    <option value="3" ${audiostoreCategoriaBean.tipo eq 3 ? 'selected="selected"' : ''}>Video</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Tempo de duração</label>
                                <input type="text" name="tempo" class="form-control" placeholder="Tempo de duração"  
                                       data-rule-required="true" 
                                       data-rule-minlength="8"
                                       data-rule-maxlength="8" 
                                       data-mask="99:99:99"
                                       field="tempo"
                                       value="">

                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-success btn-flat" style="margin-left: 0px;"><i class="fa fa-save"></i>Salvar</button>
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

                <form name="FORM_DELETAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/audiostore-categoria/remover/[[__PK__]]">
                    <input type="hidden" name="audiostoreCategoriaBean.id" value="[[__PK__]]" />
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
            </div>
            <button type="button" class="btn btn-default btn-flat btn_export" style="display: none;"><i class="fa fa-upload"></i> Exportar arquivo </button>
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
                        url="${url}/audiostore-categorias"
                        page="1"
                        size="0"
                        rows="10"
                        pk="codigo"> 
                    <thead>
                        <tr> 
                            <th options="true" class="options">#</th>
                            <th field="codInterno" options="false">Código</th>
                            <th field="categoria" options="false">Categoria</th>
                            <th field="clienteNome" isfk="true" fk="idcliente" fklabel="nome" fklabelselect="Todos" fkurl="${url}/audiostore-categorias?clientes=true"  options="false">Cliente</th>
                            <th field="dataInicio" options="false">Data inicial</th>
                            <th field="dataFinal" options="false">Data final</th>
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
                jQuery(document).on("selected", ".row_data", function(evt, item) {
                    jQuery('.btn_export').show();
                }).on("unselected", ".row_data", function(evt, item) {
                    if (countRowsSelected() == 0) {
                        jQuery('.btn_export').hide();
                    }
                });

                jQuery('.btn_export').on("click", function() {
                    if (countRowsSelected() <= 0) {
                        bootbox.alert("Selecione no minimo um registro na tabela.", function() {
                        });
                    } else {
                        var arr = rowsSelected();
                        for (i in arr) {
                            var item = arr[i];
                        }
                    }
                });
            });
        </script>
    </jsp:body>
</instore:template>