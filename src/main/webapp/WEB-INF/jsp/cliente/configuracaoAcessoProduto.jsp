<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">

    </jsp:attribute>

    <jsp:body>
        <c:if test="${null eq unidadeList or empty unidadeList}">
            O cliente selecionado não possui unidades!
        </c:if>
            
        <c:if test="${null ne unidadeList and not empty unidadeList}">
            <form id="cad_cliente" method="POST" data-form="true" data-success-url="${url}/cliente/configuracao/acesso/produto/${cliente}">
                <input type="hidden" name="cliente" value="${cliente}" />
                <div class="block-flat">
                    <div class="content">
                        <h2>Relaionar produtos ao cliente</h2>
                        <table class="tbl_produto">
                            <thead>
                                <tr>
                                    <th style="width:50%;">Cliente</th>
                                    <th class="text-left">Produto</th>
                                    <th class="text-left" style="width: 60px;"></th> 
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="clonar1" style="display: none;">
                                    <td>
                                        <select  class="select2 sel1">
                                            <c:forEach items="${unidadeList}" var="unidItem">
                                                <option value="${unidItem.cliente.idcliente}" ${unidItem.cliente.idcliente eq prodCli.cliente.idcliente ? 'selected="selected"' : ''} >${unidItem.cliente.codigoInterno} / Nome: ${unidItem.cliente.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select  class="select2 sel2" >
                                            <c:forEach items="${produtoList}" var="prodItem">
                                                <option value="${prodItem.id}" ${prodItem.id eq prodCli.produto.id ? 'selected="selected"' : ''} >${prodItem.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <button class="btn btn-danger btn-flat btn-xs rm_produto">
                                            <i class="fa fa-trash-o"></i>
                                        </button>
                                    </td>
                                </tr>

                                <c:forEach items="${unidadeList}" var="unid" varStatus="vsa">
                                    <c:forEach items="${unid.produtoClienteList}" var="prodCli" varStatus="vsb">
                                        <tr>
                                            <td>
                                                <select  class="select2 sel1" name="produtoClienteBeanList[${vsa.index + vsb.index}].cliente.idcliente">
                                                    <c:forEach items="${unidadeList}" var="unidItem">
                                                        <option value="${unidItem.cliente.idcliente}" ${unidItem.cliente.idcliente eq prodCli.cliente.idcliente ? 'selected="selected"' : ''} >${unidItem.cliente.codigoInterno} / Nome: ${unidItem.cliente.nome}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select  class="select2 sel2" name="produtoClienteBeanList[${vsa.index + vsb.index}].produto.id">
                                                    <c:forEach items="${produtoList}" var="prodItem">
                                                        <option value="${prodItem.id}" ${prodItem.id eq prodCli.produto.id ? 'selected="selected"' : ''} >${prodItem.nome}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <button class="btn btn-danger btn-flat btn-xs rm_produto">
                                                    <i class="fa fa-trash-o"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:forEach>
                            </tbody>
                        </table>

                        <div class="clearfix"></div>
                        <br />

                        <div style="float: right">
                            <button class="btn btn-primary btn-flat btn-xs add_produto">adicionar um produto ao cliente</button>
                        </div>

                        <div class="clearfix"></div>
                    </div>
                </div>

                <div class="block-flat">
                    <div class="content">
                        <h2>Configurar acesso remoto</h2>
                        <table class="tbl_acesso">
                            <thead>
                                <tr>
                                    <th>Cliente</th>
                                    <th>Tipo de acesso</th>
                                    <th>Servidor</th>
                                    <th>Usuario</th>
                                    <th>Senha</th>
                                    <th>Porta</th>
                                    <th style="width: 60px;"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="clonar2" style="display: none;">
                                    <td>
                                        <select  class="select2 sel3" >
                                            <c:forEach items="${unidadeList}" var="unidItem">
                                                <option value="${unidItem.cliente.idcliente}" ${unidItem.cliente.idcliente eq acesso_remoto.cliente.idcliente ? 'selected="selected"' : ''} >${unidItem.cliente.codigoInterno} / Nome: ${unidItem.cliente.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select  class="select2 sel4">
                                            <c:forEach items="${tipoAcessoRemotoList}" var="tarItem">
                                                <option value="${tarItem.id}" ${tarItem.id eq acesso_remoto.tipoAcessoRemoto.id ? 'selected="selected"' : ''} >${tarItem.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="text" class="form-control in1" value="${acesso_remoto.servidor}" />
                                    </td>
                                    <td>
                                        <input type="text" class="form-control in2"  value="${acesso_remoto.usuario}" />
                                    </td>
                                    <td>
                                        <input type="text" class="form-control in3"  value="${acesso_remoto.senha}" />
                                    </td>
                                    <td>
                                        <input type="text" class="form-control in4"  value="${acesso_remoto.porta}" />
                                    </td>
                                    <td>
                                        <button class="btn btn-danger btn-flat btn-xs rm_acesso">
                                            <i class="fa fa-trash-o"></i>
                                        </button>
                                    </td>
                                </tr>

                                <c:set scope="session" var="last_index" value="0" />
                                <c:forEach items="${unidadeList}" var="unid" varStatus="vsa">
                                    <c:forEach items="${unid.acessoRemotoList}" var="acesso_remoto" varStatus="vsb">
                                        <c:set scope="session" var="last_index" value="${vsa.index + vsb.index}" />
                                        <tr>
                                            <td>
                                                <select  class="select2 sel3" name="acessoRemotoBeanList[${vsa.index + vsb.index}].cliente.idcliente">
                                                    <c:forEach items="${unidadeList}" var="unidItem">
                                                        <option value="${unidItem.cliente.idcliente}" ${unidItem.cliente.idcliente eq acesso_remoto.cliente.idcliente ? 'selected="selected"' : ''} >${unidItem.cliente.codigoInterno} / Nome: ${unidItem.cliente.nome}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <select  class="select2 sel4" name="acessoRemotoBeanList[${vsa.index + vsb.index}].tipoAcessoRemoto.id">
                                                    <c:forEach items="${tipoAcessoRemotoList}" var="tarItem">
                                                        <option value="${tarItem.id}" ${tarItem.id eq acesso_remoto.tipoAcessoRemoto.id ? 'selected="selected"' : ''} >${tarItem.nome}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="text" class="form-control in1" name="acessoRemotoBeanList[${vsa.index + vsb.index}].servidor" value="${acesso_remoto.servidor}" />
                                            </td>
                                            <td>
                                                <input type="text" class="form-control in2" name="acessoRemotoBeanList[${vsa.index + vsb.index}].usuario" value="${acesso_remoto.usuario}" />
                                            </td>
                                            <td>
                                                <input type="text" class="form-control in3" name="acessoRemotoBeanList[${vsa.index + vsb.index}].senha" value="${acesso_remoto.senha}" />
                                            </td>
                                            <td>
                                                <input type="text" class="form-control in4" name="acessoRemotoBeanList[${vsa.index + vsb.index}].porta" value="${acesso_remoto.porta}" />
                                            </td>
                                            <td>
                                                <button class="btn btn-danger btn-flat btn-xs rm_acesso">
                                                    <i class="fa fa-trash-o"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:forEach>
                            </tbody>
                        </table>

                        <div class="clearfix"></div>

                        <br />

                        <div style="float: right">
                            <button class="btn btn-primary btn-flat btn-xs add_acesso">adicionar um acesso ao cliente</button>
                        </div>

                        <div class="clearfix"></div>
                    </div>
                </div>


                <button class="btn btn-primary btn-flat">Salvar</button>


                <script>
                    jQuery(document).ready(function() {
                        jQuery('.add_produto').on('click', function() {
                            last_tr = jQuery('.tbl_produto tbody tr.clonar1');

                            var clone = last_tr.clone();
                            var index = (0 == last_tr.index() ? 14778 : last_tr.index()) * 7;

                            clone.children('td').children('.select2-container').remove();
                            clone.children('td').children('select.sel1').select2('destroy');
                            clone.children('td').children('select.sel2').select2('destroy');

                            clone.children('td').children('select.sel1').attr('class', 'sel1');
                            clone.children('td').children('select.sel2').attr('class', 'sel2');

                            clone.children('td').children('select.sel1').addClass('sel1___' + index);
                            clone.children('td').children('select.sel2').addClass('sel2___' + index);

                            var newName1 = 'produtoClienteBeanList[' + (last_tr.index() + 1) + '].cliente.idcliente';
                            var newName2 = 'produtoClienteBeanList[' + (last_tr.index() + 1) + '].produto.id';


                            clone.children('td').children('select.sel1').attr('name', newName1);
                            clone.children('td').children('select.sel2').attr('name', newName2);

                            clone.show();
                            jQuery('.tbl_produto tbody').append(clone);

                            jQuery('.sel1___' + index).select2('destroy');
                            jQuery('.sel2___' + index).select2('destroy');
                            jQuery('.sel1___' + index).select2({width: '100%'});
                            jQuery('.sel2___' + index).select2({width: '100%'});
                            return false;
                        });

                        jQuery(document).on('click', '.rm_produto', function() {
                            jQuery(this).parent('td').parent('tr').remove();
                        });

                        jQuery('.add_acesso').on('click', function() {
                            last_tr = jQuery('.tbl_acesso tbody tr.clonar2');

                            var clone = last_tr.clone();
                            var index = (0 == last_tr.index() ? 14778 : last_tr.index()) * 7;

                            clone.children('td').children('.select2-container').remove();
                            clone.children('td').children('select.sel3').select2('destroy');
                            clone.children('td').children('select.sel4').select2('destroy');

                            clone.children('td').children('select.sel3').attr('class', 'sel3');
                            clone.children('td').children('select.sel4').attr('class', 'sel4');

                            clone.children('td').children('select.sel3').addClass('sel3___' + index);
                            clone.children('td').children('select.sel4').addClass('sel4___' + index);

                            var newName1 = 'acessoRemotoBeanList[' + (last_tr.index() + 1) + '].cliente.idcliente';
                            var newName2 = 'acessoRemotoBeanList[' + (last_tr.index() + 1) + '].tipoAcessoRemoto.id';
                            var newNameIn1 = 'acessoRemotoBeanList[' + (last_tr.index() + 1) + '].servidor';
                            var newNameIn2 = 'acessoRemotoBeanList[' + (last_tr.index() + 1) + '].usuario';
                            var newNameIn3 = 'acessoRemotoBeanList[' + (last_tr.index() + 1) + '].senha';
                            var newNameIn4 = 'acessoRemotoBeanList[' + (last_tr.index() + 1) + '].porta';



                            clone.children('td').children('select.sel3').attr('name', newName1);
                            clone.children('td').children('select.sel4').attr('name', newName2);
                            clone.children('td').children('input.in1').attr('name', newNameIn1);
                            clone.children('td').children('input.in2').attr('name', newNameIn2);
                            clone.children('td').children('input.in3').attr('name', newNameIn3);
                            clone.children('td').children('input.in4').attr('name', newNameIn4);

                            clone.show();
                            jQuery('.tbl_acesso tbody').append(clone);

                            jQuery('.sel3___' + index).select2('destroy');
                            jQuery('.sel4___' + index).select2('destroy');
                            jQuery('.sel3___' + index).select2({width: '100%'});
                            jQuery('.sel4___' + index).select2({width: '100%'});
                            return false;

                        });

                        jQuery(document).on('click', '.rm_acesso', function() {
                            jQuery(this).parent('td').parent('tr').remove();
                        });
                    });
                </script>
            </form>
        </c:if>

    </jsp:body>
</instore:template>