<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<instore:template isGrid="false">
    <jsp:body> 

        <c:set scope="session" var="form_access" value="${false}"></c:set>
        <c:set scope="session" var="update_access" value="${false}"></c:set>
        <c:set scope="session" var="delete_access" value="${false}"></c:set>

        <c:forEach items="${funcionalidadeBeanList}" var="func">
            <c:if test="${func.mappingId eq '/ocorrencia-solucao/cadastrar'}">
                <c:set scope="session" var="form_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/ocorrencia-solucao/atualizar/{id}'}">
                <c:set scope="session" var="update_access" value="${true}"></c:set>
            </c:if>

            <c:if test="${func.mappingId eq '/ocorrencia-solucao/remover/{id}'}">
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
                        <form name="FORM_CADASTRO" method="POST" data-formtable="true" action="${url}/ocorrencia-solucao/cadastrar">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Descrição</label>
                                        <input type="text" name="ocorrenciaSolucaoBean.descricao" class="form-control" placeholder="Descrição"  
                                               data-rule-required="true" 
                                               data-rule-minlength="3" 
                                               data-rule-maxlength="255" 
                                               value="${ocorrenciaSolucaoBean.descricao}">
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Prazo</label>
                                        <input type="text" name="prazo" class="form-control" placeholder="Nível"  
                                               data-rule-required="true" 
                                               data-mask="00:00:00" 
                                               field="prazo" 
                                               value="${ocorrenciaSolucaoBean.nivel}">
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Problemas</label>
                                        <br />
                                        <c:forEach items="${problemaList}" var="problema" varStatus="vs">
                                            <div class="col-md-2">
                                                <label> <input type="checkbox" class="icheck" name="problemaList[${vs.index}]" value="${problema.id}" /> </label> ${problema.descricao} 
                                            </div>
                                        </c:forEach>
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
                <li>
                    <strong>Nível</strong>
                    <br />
                    <small field="nivel"></small>
                </li>
            </ol>
        </div>


        <!--ATUALIZAR-->
        <div class="edit">
            <c:if test="${update_access}">
                <br />
                <h2>Atualizar dados</h2>
                <hr />

                <form name="FORM_ALTERAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/ocorrencia-solucao/cadastrar" callback="callback" reload="true"> 
                    <input type="hidden" name="ocorrenciaSolucaoBean.id" value="${ocorrenciaSolucaoBean.id}" field="id" />

                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Descrição</label>
                                <input type="text" name="ocorrenciaSolucaoBean.descricao" class="form-control" placeholder="Descrição"  
                                       data-rule-required="true" 
                                       data-rule-minlength="3" 
                                       data-rule-maxlength="255" 
                                       field="descricao" 
                                       value="${ocorrenciaSolucaoBean.descricao}">
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Prazo</label>
                                <input type="text" name="prazo" class="form-control" placeholder="Nível"  
                                       data-rule-required="true" 
                                       data-mask="00:00:00" 
                                       field="prazo" 
                                       value="${ocorrenciaSolucaoBean.nivel}">
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Problemas</label>
                                <br />
                                <c:forEach items="${problemaList}" var="problema" varStatus="vs">
                                    <div class="col-md-2">
                                        <label> <input type="checkbox" class="icheck2 chk_prob" data-id="${problema.id}|[[__PK__]]" name="problemaList" value="${problema.id}"  /> </label> ${problema.descricao} 
                                    </div>
                                </c:forEach>
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

                <form name="FORM_DELETAR_[[__PK__]]" method="POST" data-formtable="true" action="${url}/ocorrencia-solucao/remover/[[__PK__]]">
                    <input type="hidden" name="ocorrenciaSolucaoBean.id" value="[[__PK__]]" />
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
                        url="${url}/ocorrencia-solucao"
                        page="1"
                        size="0"
                        rows="10"
                        pk="id"> 
                    <thead>
                        <tr>
                            <th options="true" class="options">#</th>
                            <th field="descricao" options="false">Descrição</th>
                            <th field="prazo" options="false">Prazo</th>
                        </tr> 
                    </thead>
                </table>
            </div>
        </div>

        <script type="text/javascript">
            function callback(item) {
                jQuery.get("${url}/ocorrencia-solucao/sp/" + item.id, function(json) {
                    for (i in json) {
                        var ji = json[i];
                        jQuery('.chk_prob[data-id="'+ji.ocorrenciaProblema.id+'|'+item.id+'"]').attr('checked', true);
                        jQuery('.chk_prob[data-id="'+ji.ocorrenciaProblema.id+'|'+item.id+'"]').iCheck('destroy');
                        jQuery('.chk_prob[data-id="'+ji.ocorrenciaProblema.id+'|'+item.id+'"]').iCheck({
                            checkboxClass: 'icheckbox_square-blue checkbox',
                            radioClass: 'iradio_square-blue'
                        });
                        jQuery('.chk_prob[data-id="'+ji.ocorrenciaProblema.id+'|'+item.id+'"]').iCheck('check');
                    }
                });
            }
        </script>
    </jsp:body>
</instore:template>