<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cf" uri="/WEB-INF/custom-functions.tld" %> 
<instore:template isGrid="false">

    <jsp:attribute name="submenu">
        <a href="${url}/musica/programacao-audiostore/cadastrar/${idmusicaGeral}" class="btn btn-default"> <i class="fa fa-save"></i> Cadastrar </a>
        <a href="${url}/musica" class="btn btn-default"> <i class="fa fa-list"></i> Lista musicas </a>
    </jsp:attribute>

    <jsp:body> 

        <c:set scope="session" var="paginacao" value=""></c:set>
        <c:set scope="session" var="paginacao_concat" value="?"></c:set>
        <c:forEach var="item" items="${paramValues}">
            <c:if test="${not fn:startsWith(item.key, 'pagina')}"> 
                <c:set scope="session" var="paginacao" value="${paginacao}${paginacao_concat}${item.key}=${item.value[0]}"></c:set>
                <c:set scope="session" var="paginacao_concat" value="&"></c:set>
            </c:if>
        </c:forEach>

        <c:set scope="session" var="qtd" value=""></c:set>
        <c:set scope="session" var="qtd_concat" value="?"></c:set>
        <c:forEach var="item" items="${paramValues}">
            <c:if test="${not fn:startsWith(item.key, 'qtd') and not fn:startsWith(item.key, 'pagina')}"> 
                <c:set scope="session" var="qtd" value="${qtd}${qtd_concat}${item.key}=${item.value[0]}"></c:set>
                <c:set scope="session" var="qtd_concat" value="&"></c:set>
            </c:if>
        </c:forEach>

        <c:set scope="session" var="__order" value=""></c:set>
        <c:set scope="session" var="__order_concat" value="?"></c:set>
        <c:forEach var="item" items="${paramValues}">
            <c:if test="${not fn:startsWith(item.key, 'order')}"> 
                <c:set scope="session" var="__order" value="${__order}${__order_concat}${item.key}=${item.value[0]}"></c:set>
                <c:set scope="session" var="__order_concat" value="&"></c:set>
            </c:if>
        </c:forEach>

        <style>
            .play, .pause, .stop, .mute, .unmute {
                display: inline-block;
                padding: 10px;
                width: 30px;
                height: 30px;

                background-color: #54A754;
                border-radius: 2px;
            }

            .play i, .pause i, .stop i, .mute i, .unmute i {
                font-size: 10px;
                font-size: 15px;
                text-align: center;
                margin-left: 6px;
                margin-top: 3px;
                color: #FFF;
            }
        </style>

        <form>
            <div class="block-flat">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Cliente</label> 
                            <select class="select2" name="filtro.cliente.idcliente">
                                <c:forEach items="${clienteBeanList}" var="item">
                                    <option value="${item.idcliente}" ${filtro.cliente.idcliente eq item.idcliente ? 'selected="selected"' : ''}>${item.nome}</option>
                                </c:forEach> 
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Categoria</label>
                            <select class="select2" name="filtro.categoria1.codigo">
                                <c:forEach items="${categorias}" var="item">
                                    <option value="${item.codigo}" ${filtro.categoria1.codigo eq item.codigo ? 'selected="selected"' : ''}>${item.categoria}</option>
                                </c:forEach> 
                            </select>
                        </div>
                    </div>
                </div>

                <hr />

                <div class="clearfix"></div>

                <div style="float: right">
                    <div class="btn-group">
                        <a href="${url}/musica/programacao-audiostore/${idmusicaGeral}${paginacao}${paginacao_concat}pagina=${paginaAtual-1}" class="btn btn-default btn-flat" ${(paginaAtual-1) < 1 ? 'disabled="disabled"':''} > 
                            <i class="fa fa-arrow-left"></i>
                        </a>
                        <a  href="javascript:void(0)" class="btn btn-default btn-flat"> 
                            Página ${paginaAtual} de ${totalPaginas}
                        </a>
                        <a href="${url}/musica/programacao-audiostore/${idmusicaGeral}${paginacao}${paginacao_concat}pagina=${paginaAtual+1}" class="btn btn-default btn-flat" ${(paginaAtual+1) >= totalPaginas ? 'disabled="disabled"':''}>
                            <i class="fa fa-arrow-right"></i>
                        </a>
                    </div>
                </div>

                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle btn-flat" data-toggle="dropdown">
                        ${totalRegistrosPorPagina} registros <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${url}/musica/programacao-audiostore/${idmusicaGeral}${qtd}${qtd_concat}qtd=5">5 registros</a></li>
                        <li><a href="${url}/musica/programacao-audiostore/${idmusicaGeral}${qtd}${qtd_concat}qtd=10">10 registros</a></li>
                        <li><a href="${url}/musica/programacao-audiostore/${idmusicaGeral}${qtd}${qtd_concat}qtd=25">25 registros</a></li>
                        <li><a href="${url}/musica/programacao-audiostore/${idmusicaGeral}${qtd}${qtd_concat}qtd=50">50 registros</a></li>
                        <li><a href="${url}/musica/programacao-audiostore/${idmusicaGeral}${qtd}${qtd_concat}qtd=100">100 registros</a></li>
                        <li><a href="${url}/musica/programacao-audiostore/${idmusicaGeral}${qtd}${qtd_concat}qtd=250">250registros</a></li>
                    </ul>
                </div>

                <!--                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle btn-flat" data-toggle="dropdown">
                                        Ordenação <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="${url}/musica${__order}${__order_concat}order=3">por título ascendente</a></li>
                                        <li><a href="${url}/musica${__order}${__order_concat}order=4">por título descendente</a></li>
                                        <li><a href="${url}/musica${__order}${__order_concat}order=5">por interprete ascendente</a></li>
                                        <li><a href="${url}/musica${__order}${__order_concat}order=6">por interprete descendente</a></li>
                                        <li><a href="${url}/musica${__order}${__order_concat}order=7">por velocidade ascendente</a></li>
                                        <li><a href="${url}/musica${__order}${__order_concat}order=8">por velocidade descendente</a></li>
                                        <li><a href="${url}/musica${__order}${__order_concat}order=11">por letra ascendente</a></li>
                                        <li><a href="${url}/musica${__order}${__order_concat}order=12">por letra descendente</a></li>
                                    </ul>
                                </div>-->

                <button type="submit" class="btn btn-default btn-flat"> Filtrar </button>
                <a href="${url}/musica/programacao-audiostore/${idmusicaGeral}" class="btn btn-default btn-flat"> Limpar Filtro </a>

                <div class="table-responsive">
                    <table class="no-border">
                        <thead class="no-border">
                            <tr>
                                <th></th>
                                <th><strong>Cliente</strong></th>
                                <th ><strong>Categorias</strong></th>
                                <th><strong>Data de inclusão</strong></th>
                                <th><strong>Data de vencimento</strong></th>
                            </tr>
                        </thead>
                        <tbody class="no-border-y">
                            <c:forEach items="${audiostoreMusicaBeanList}" var="item" varStatus="vs">
                                <tr>
                                    <td width="120">
                                        <a class="label label-default"  href="#" data-toggle="modal" data-target="#modal_ver_${item.id}"><i class="fa fa-eye"></i></a>
                                        <a class="label label-warning" href="${url}/musica/programacao-audiostore/atualizar/${idmusicaGeral}/${item.id}"><i class="fa fa-pencil"></i></a>
                                        <a class="label label-danger" href="${url}/musica/programacao-audiostore/remover/${idmusicaGeral}/${item.id}"><i class="fa fa-trash-o"></i></a>

                                        <div class="modal fade" id="modal_ver_${item.id}" tabindex="-1" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <table class="no-border">
                                                            <thead>
                                                                <tr>
                                                                    <td width="150"></td>
                                                                    <td></td>
                                                                </tr>
                                                            </thead>
                                                            <tbody class="no-border-x">
                                                                <tr>
                                                                    <td> <strong>Cliente</strong></td>
                                                                    <td style="word-break: break-all;">${item.cliente.nome}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Categoria Primária</strong></td>
                                                                    <td style="word-break: break-all;">${item.categoria1.categoria}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Dias execução (Primária)</strong></td>
                                                                    <td style="word-break: break-all;">${item.diasExecucao1}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Categoria Secundário</strong></td>
                                                                    <td style="word-break: break-all;">${item.categoria2.categoria}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Dias execução (Secundário)</strong></td>
                                                                    <td style="word-break: break-all;">${item.diasExecucao2}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Usa crossover</strong></td>
                                                                    <td style="word-break: break-all;">${item.crossover ?  "Sim" : "Não"}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Data vencimendo crossover</strong></td>
                                                                    <td style="word-break: break-all;">${item.dataVencimentoCrossover}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Quantidade</strong></td>
                                                                    <td style="word-break: break-all;">${item.qtde}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Ultima execução</strong></td>
                                                                    <td style="word-break: break-all;">${cf:dateFormat(item.ultimaExecucao,"dd/MM/yyyy")}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Data de inclusão</strong></td>
                                                                    <td style="word-break: break-all;">${cf:dateFormat(item.data,"dd/MM/yyyy")}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td> <strong>Data de vencimento</strong></td>
                                                                    <td style="word-break: break-all;">${cf:dateFormat(item.dataVencimento,"dd/MM/yyyy")}</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>

                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i>Fechar Janela</button>
                                                        <button type="button" class="btn btn-warning"><i class="fa fa-pencil"></i>Atualizar</button>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        </div>
                                    </td>
                                    <td>${item.cliente.nome}</td> 
                                    <td>
                                        <c:if test="${null ne item.categoria1}">
                                            ${item.categoria1.categoria} / dias execução ${item.diasExecucao1}
                                        </c:if>

                                        |

                                        <c:if test="${null ne item.categoria2}">
                                            ${item.categoria2.categoria} / dias execução ${item.diasExecucao2}
                                        </c:if>
                                    </td>
                                    <td>${cf:dateFormat(item.data,"dd/MM/yyyy")}</td>
                                    <td>${cf:dateFormat(item.dataVencimento,"dd/MM/yyyy")}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>		
                    <b><strong>Total de músicas : ${totalRegistros}</strong></b>
                </div>  
            </div>
        </form>
    </jsp:body>
</instore:template>