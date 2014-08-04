<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/ocorrencia" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Lista </a>
    </jsp:attribute>

    <jsp:body> 
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/ocorrencia">
            <input type="hidden" name="ocorrenciaBean.id" value="${ocorrenciaBean.id}" />
            <input type="hidden" name="ocorrenciaBean.dataCadastro" value="${cf:dateFormat(ocorrenciaBean.dataCadastro, "dd/MM/yyyy")}" />
            <input type="hidden" name="ocorrenciaBean.usuarioCriacao.idusuario" value="${sessionUsuario.usuarioBean.idusuario}" />

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>Descrição</label>
                        <textarea class="form-control" name="ocorrenciaBean.descricao" rows="10" data-rule-required="true">${ocorrenciaBean.descricao}</textarea>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Data de resolução</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="ocorrenciaBean.dataResolucao" class="form-control" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" value="${cf:dateFormat(ocorrenciaBean.dataResolucao,"dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Cliente</label>
                        <select class="select2" name="ocorrenciaBean.cliente.idcliente"  data-rule-required="true">
                            <c:forEach items="${clienteList}" var="item">
                                <option value="${item.idcliente}" ${ocorrenciaBean.cliente.idcliente eq item.idcliente ? 'selected="selected"' :''}>${item.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Origem</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaOrigem.id"  data-rule-required="true">
                            <c:forEach items="${ocorrenciaOrigemList}" var="item">
                                <option value="${item.id}" ${ocorrenciaBean.ocorrenciaOrigem.id eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Prioridade</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaPrioridade.id"  data-rule-required="true">
                            <c:forEach items="${ocorrenciaPrioridadeList}" var="item">
                                <option value="${item.id}" ${ocorrenciaBean.ocorrenciaPrioridade.id eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Possivel Problema</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaProblema">
                            <option>&nbsp;</option>
                            <c:forEach items="${ocorrenciaProblemaList}" var="item">
                                <option value="${item.id}" ${ocorrenciaBean.ocorrenciaProblema eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Possivel Solução</label>
                        <select class="select2" name="ocorrenciaBean.ocorrenciaSolucao">
                            <option>&nbsp;</option>
                            <c:forEach items="${ocorrenciaSolucaoList}" var="item">
                                <option value="${item.id}" ${ocorrenciaBean.ocorrenciaSolucao eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-12">
                    <h4>Relacionar usuários</h4>
                    <table class="no-border">
                        <thead class="no-border">
                            <tr>
                                <th width="70"></th>
                                <th width="200">Situação</th>
                                <th>Usuários</th>
                            </tr>
                        </thead>
                        <tbody class="no-border-y">
                            <c:forEach items="${usuarioList}" var="usuario" varStatus="vs">
                                <tr>
                                    <td data-id="${vs.index}">
                                        <div class="switch switch-small">
                                            <c:set var="selected" value="${false}"></c:set>
                                            <c:set var="__ob__" value="${null}"></c:set>
                                            <c:forEach items="${ocorrenciaUsuarioList}" var="ocorr">
                                                <c:if test="${usuario.idusuario eq ocorr.usuario.idusuario and ocorr.ocorrenciaBean.id eq ocorrenciaBean.id}">
                                                    <c:set var="selected" value="${true}"></c:set>
                                                    <c:set var="__ob__" value="${ocorr}"></c:set>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${selected}">
                                                <input  data-id="${vs.index}" class="xradio" name="op1" type="checkbox" checked="checked" />
                                            </c:if>
                                            <c:if test="${not selected}">
                                                <input data-id="${vs.index}" class="xradio" name="op1" type="checkbox" />
                                            </c:if>

                                            <input data-usuario-index="${vs.index}" type="hidden" ${__ob__ eq null ? 'disabled="disabled"' : ''} name="ocorrenciaUsuarioBeanList[${vs.index}].usuario.idusuario" value="${usuario.idusuario}" />
                                            <input data-usuario-index="${vs.index}" type="hidden" ${__ob__ eq null ? 'disabled="disabled"' : ''} name="ocorrenciaUsuarioBeanList[${vs.index}].id" value="${__ob__.id}" />   
                                        </div>
                                    </td>

                                    <c:if test="${__ob__ ne null}">
                                        <td data-id="${vs.index}" style="background-color: ${__ob__.ocorrenciaStatus.cor}"> 
                                    </c:if>
                                    <c:if test="${__ob__ eq null}">
                                        <td> 
                                    </c:if>

                                        <select data-sit-index="${vs.index}" class="select2 sel_situ" name="ocorrenciaUsuarioBeanList[${vs.index}].ocorrenciaStatus.id"  data-rule-required="true" ${__ob__ eq null ? 'disabled="disabled"' : ''}>
                                            <option data-cor="transparent">&nbsp;</option>
                                            <c:forEach items="${ocorrenciaStatusList}" var="item">
                                                <option data-cor="${item.cor}" data-id="${vs.index}" value="${item.id}" ${__ob__.ocorrenciaStatus.id eq item.id ? 'selected="selected"' :''}>${item.descricao}</option>
                                            </c:forEach>
                                        </select>
                                    </td>

                                    <td>
                                        <strong>Usuário: </strong>
                                        <br /> ${usuario.nome}
                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>			
                </div>
            </div>

            <br />
            <br />

            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
        <script type="text/javascript">
            jQuery(document).ready(function() {
                jQuery('.xradio').on('change', function() {
                    var data_id = jQuery(this).data('id');
                    console.log("ID--"+data_id);
                    
                    if (!jQuery(this).is(':checked')) {
                        jQuery('td[data-id="' + data_id + '"]').css({
                            'background-color': 'transparent',
                        });
                        jQuery('[data-usuario-index="'+data_id+'"]').attr("disabled",true);
                        jQuery('[data-sit-index="'+data_id+'"]').attr("disabled",true);
                        jQuery('[data-sit-index="'+data_id+'"]').parent('td').css({
                            'background-color': 'transparent',
                        });
                    } else {
                        jQuery('[data-usuario-index="'+data_id+'"]').attr("disabled",false);
                        jQuery('[data-sit-index="'+data_id+'"]').attr("disabled",false);
                        
                        var _this = jQuery('[data-sit-index="'+data_id+'"]');
                        var data_cor = jQuery('option:selected', _this).data('cor');
                        jQuery('[data-sit-index="'+data_id+'"]').parent('td').css({
                            'background-color': data_cor,
                        });
                    }
                });
                jQuery('.sel_situ').on('change', function() {
                    var data_cor = jQuery('option:selected', this).data('cor');
                    var data_id = jQuery('option:selected', this).data('id');

                    jQuery(this).parent('td').css({
                        'background-color': data_cor,
                    });
                });


            });
        </script>
    </jsp:body>
</instore:template> 