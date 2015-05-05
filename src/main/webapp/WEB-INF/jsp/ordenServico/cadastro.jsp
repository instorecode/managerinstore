<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-categorias" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Audiostore Categorias </a>
    </jsp:attribute>

    <jsp:body>

        <div class="panel panel-primary">
            <div class="panel-heading">
                <b>Informações da ordem de serviço </b>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Clientes</label> 
                            <select class="select2" name="cliente_matriz" class="form-control" data-rule-required="true">
                                <option value="">Selecione um cliente</option>
                                <c:forEach items="${clienteBeanList}" var="cliente" varStatus="vs">
                                    <c:if test="${cliente.matriz and cliente.parente eq 0}">
                                        <option value="${cliente.idcliente}">${cliente.nome}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Nome / Campanha</label>
                            <input type="text" name="campanha" class="form-control" placeholder="Nome / Campanha"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" value="${usuarioBean.nome}">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Quem solicitou</label>
                            <input type="text" name="quem_solicitou" class="form-control" placeholder="Quem solicitou"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" value="${usuarioBean.nome}">
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Quando solicitou</label>
                            <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                <input type="text" name="quando_solicitou" class="form-control datepicker" placeholder="Quando solicitou" 
                                       data-rule-required="true" 
                                       data-rule-minlength="10"
                                       data-rule-maxlength="10" value="${cf:dateFormat(dadosCliente.dataInicioContrato , "dd/MM/yyyy")}">

                            </div>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Data máxima para distribuição</label>
                            <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                <input type="text" name="data_max_distribuicao" class="form-control datepicker" placeholder="Data máxima para distribuição" 
                                       data-rule-required="true" 
                                       data-rule-minlength="10"
                                       data-rule-maxlength="10" value="${cf:dateFormat(dadosCliente.dataInicioContrato , "dd/MM/yyyy")}">

                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Tipo</label> 
                            <select class="select2" name="tipo" class="form-control" data-rule-required="true">
                                <option value="1">Radio interna</option>
                                <option value="2">Radio externa</option>
                                <option value="3">URA</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <input type="text" name="usuarioBean.nome" class="form-control" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" disabled="disabled" value="Solicitação processada pelo usuário ${sessionUsuario.usuarioBean.nome} na data ${dataAtualStr}. ">
                        </div>
                    </div>
                </div>

                <div class="row obs1_container">
                    <div class="col-md-1 obs1_clonar modelo_obs" style="display: none;">
                        <div class="header">
                            <div style="  margin-top: 90px; float: left;">
                                &nbsp;&nbsp;&nbsp;&nbsp;<b class="bold">Usuário:</b> Alex Valentim Gonçalves
                                <br />
                                &nbsp;&nbsp;&nbsp;&nbsp;<b class="bold">Data:</b> 01/02/2015 00:25:37
                                <br />
                            </div>
                        </div>
                        <div class="body">
                            <hr style="border: 0px; border-top: 1px dashed blue; opacity: 0.2;" />
                            <div class="content"></div>

                            <div class="clearfix"></div>

                            <br />

                            <div class="pull-left">
                                <i class="fa fa-pencil obs1-btn-edt"></i>
                            </div>

                            <div class="pull-right">

                                <i class="fa fa-trash-o obs1-btn-rm"></i>
                            </div>

                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
                <br />  
                <button data-toggle="modal" data-target="#obs-modal"  type="button" class="btn btn-default" style="margin-left: 0px;"><i class="fa fa-plus"></i> Adicionar Observações</button>
            </div>
        </div>


        <div class="panel panel-primary">
            <div class="panel-heading">
                <b>Informações do locutor </b>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Locutores</label>
                            <select class="select2" name="locutores_lista" class="form-control" data-rule-required="true">
                                <option value="" >Selecione um locutor</option>
                                <c:set var="tem_locutor_no_cliente" value="0" scope="session"></c:set>

                                <c:forEach items="${clienteBeanList}" var="cliente">
                                    <c:forEach items="${vozBeanList}" var="voz">
                                        <c:if test="${cliente.idcliente eq voz.idcliente}">
                                            <c:set var="tem_locutor_no_cliente" value="${tem_locutor_no_cliente + 1}" scope="session"></c:set>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>

                                <c:if test="${tem_locutor_no_cliente ne 0}">
                                    <c:forEach items="${clienteBeanList}" var="cliente">
                                        <optgroup label="${cliente.nome}"> 
                                            <c:forEach items="${vozBeanList}" var="voz">
                                                <c:if test="${cliente.idcliente eq voz.idcliente}">
                                                    <option class="red" value="${voz.idvoz}" >${voz.nome}</option>
                                                </c:if>
                                            </c:forEach>
                                        </optgroup>
                                    </c:forEach>

                                    <optgroup label="Outros">
                                        <c:forEach items="${vozBeanList}" var="voz">
                                            <c:set var="locutor_no_cliente_eq" value="${false}" scope="session"></c:set>
                                            <c:forEach items="${clienteBeanList}" var="cliente">
                                                <c:if test="${cliente.idcliente eq voz.idcliente and locutor_no_cliente_eq eq false}">
                                                    <c:set var="locutor_no_cliente_eq" value="${true}" scope="session"></c:set>
                                                </c:if>
                                            </c:forEach>

                                            <c:if test="${not locutor_no_cliente_eq}">
                                                <option class="red" value="${voz.idvoz}" >${voz.nome}</option>
                                            </c:if>
                                        </c:forEach>
                                    </optgroup>
                                </c:if>

                                <c:if test="${tem_locutor_no_cliente eq 0}">
                                    <c:forEach items="${vozBeanList}" var="voz">
                                        <option class="red" value="${voz.idvoz}" >${voz.nome}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </div>

                    <c:forEach items="${vozBeanList}" var="voz">
                        <div class="data-voz" data-voz="${voz.idvoz}" style="display: none;">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>E-mail do locutor</label>
                                    <input type="text" name="usuarioBean.nome" class="form-control" placeholder="Nome"  
                                           data-rule-required="true" 
                                           data-rule-minlength="3"
                                           data-rule-maxlength="255" disabled="disabled" value="${voz.nome}" style="text-transform: inherit;">
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Telefone do locutor</label>
                                    <input type="text" name="usuarioBean.nome" class="form-control" placeholder="Nome"  
                                           data-rule-required="true" 
                                           data-rule-minlength="3"
                                           data-rule-maxlength="255" disabled="disabled" value="${voz.email}" style="text-transform: inherit;">
                                </div>
                            </div>
                        </div>
                    </c:forEach>


                    <div class="col-md-12">
                        <label>Texto do comercial</label>
                        <div class="panel panel-default">
                            <div id="locutor_toolbar" class="panel-heading">
                                <div class="btn-group" style="float: left;">
                                    <button class="ql-bold btn btn-primary btn-xs"><i class="fa fa-bold"></i></button>
                                    <button class="ql-italic btn btn-primary btn-xs"><i class="fa fa-italic"></i></button>
                                    <button class="ql-underline btn btn-primary btn-xs"><i class="fa fa-underline"></i></button>
                                    <button class="ql-list btn btn-primary btn-xs"><i class="fa fa-list-ol"></i></button>
                                </div>

                                <span class="ql-format-group">
                                    <span title="Gras" class="ql-format-button ql-bold"></span>
                                    <span class="ql-format-separator"></span>
                                    <span title="Italique" class="ql-format-button ql-italic"></span>
                                    <span class="ql-format-separator"></span>
                                    <span title="Souligner" class="ql-format-button ql-underline"></span>

                                    <div style="display: inline-block; margin-left: 5px;">
                                        alinhar <select title="Text Alignment" class="ql-align form-control input-sm ql-select-style">
                                            <option value="left">esquerda</option>
                                            <option value="center">centro</option>
                                            <option value="right">direita</option>
                                            <option value="justify">justificado</option>
                                        </select>
                                    </div>

                                    <div style="display: inline-block; margin-left: 5px;">
                                        tamanho<select title="Size" class="ql-size ql-select-style form-control input-sm" style="margin-left: 10px;">
                                            <option value="10px">pequeno</option>
                                            <option value="13px" selected>normal</option>
                                            <option value="18px">medio</option>
                                            <option value="32px">Grande</option>
                                        </select>    
                                    </div>


                                    <div style="display: inline-block; margin-left: 5px;">
                                        color<select title="Text Color" class="ql-color ql-select-style form-control input-sm" style="margin-left: 10px;">
                                            <option value="black" selected>preto</option>
                                            <option value="white" selected>branco</option>
                                            <option value="red" >vermelho</option>
                                            <option value="green" >verde</option>
                                            <option value="blue" >azul</option>
                                            <option value="pink" >rosa</option>
                                        </select>    
                                    </div>

                                    <div style="display: inline-block; margin-left: 5px;">
                                        cor de fundo<select title="Text Color" class="ql-background ql-select-style form-control input-sm" style="margin-left: 10px;">
                                            <option value="white" selected>branco</option>
                                            <option value="black" selected>preto</option>
                                            <option value="red" >vermelho</option>
                                            <option value="green" >verde</option>
                                            <option value="blue" >azul</option>
                                            <option value="pink" >rosa</option>
                                        </select>    
                                    </div>
                            </div>
                            <div id="locutor_texto" class="panel-body" >

                            </div>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Prazo máximo de locução</label>
                            <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                <input type="text" name="prazo_locucao" class="form-control datepicker" placeholder="Prazo máximo de locução" 
                                       data-rule-required="true" 
                                       data-rule-minlength="10"
                                       data-rule-maxlength="10" value="${cf:dateFormat(dadosCliente.dataInicioContrato , "dd/MM/yyyy")}">

                            </div>
                        </div>
                    </div> 
                    <div class="clearfix"></div>
                    <div class="row obs2_container" style="padding: 10px;">
                        <div class="col-md-1 obs2_clonar modelo_obs" style="display: none;">
                            <div class="header">
                                <div style="  margin-top: 90px; float: left;">
                                    &nbsp;&nbsp;&nbsp;&nbsp;<b class="bold">Usuário:</b> Alex Valentim Gonçalves
                                    <br />
                                    &nbsp;&nbsp;&nbsp;&nbsp;<b class="bold">Data:</b> 01/02/2015 00:25:37
                                    <br />
                                </div>
                            </div>
                            <div class="body">
                                <hr style="border: 0px; border-top: 1px dashed blue; opacity: 0.2;" />
                                <div class="content"></div>

                                <div class="clearfix"></div>

                                <br />

                                <div class="pull-left">
                                    <i class="fa fa-pencil obs2-btn-edt"></i>
                                </div>

                                <div class="pull-right">

                                    <i class="fa fa-trash-o obs2-btn-rm"></i>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="clearfix"></div>
                <br />  
                <button data-toggle="modal" data-target="#obs-modal2"  type="button" class="btn btn-default" style="margin-left: 0px;"><i class="fa fa-plus"></i> Adicionar Observações</button>
            </div>
        </div>


        <div class="panel panel-primary">
            <div class="panel-heading">
                <b>Informações do estudio </b>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Prazo máximo de processamento de audio</label>
                            <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                <input type="text" name="prazo_max_estudio" class="form-control datepicker" placeholder="Prazo máximo de processamento de audio" 
                                       data-rule-required="true" 
                                       data-rule-minlength="10"
                                       data-rule-maxlength="10" value="${cf:dateFormat(dadosCliente.dataInicioContrato , "dd/MM/yyyy")}">

                            </div>
                        </div>
                    </div> 
                </div>
            </div>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading">
                <b>Informações da ficha do audiostore </b>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label>Tipo do comercial</label>
                            <label class="radio-inline "> <input type="radio" class="icheck tipo_comerciala"  name="tipo_comercial"  value="${true}" ${dadosCliente.renovacaoAutomatica eq true or dadosCliente.renovacaoAutomatica eq null ? 'checked="checked"' : ''} >&nbsp;Normal </label>
                            <label class="radio-inline "> <input type="radio" class="icheck tipo_comercialb"  name="tipo_comercial"  value="${false}"  ${dadosCliente.renovacaoAutomatica eq false ? 'checked="checked"' : ''}>&nbsp;Determinado </label>
                        </div>
                    </div> 

                    <c:forEach items="${clienteBeanList}" var="cliente" varStatus="vs">
                        <c:if test="${cliente.matriz and cliente.parente eq 0}">
                            <div class="col-md-3 data-cat" data-cat="${cliente.idcliente}" style="display: none;">
                                <div class="form-group">
                                    <label>Categoria <a href="#" class="link_ver_prog">Visualizar a estrutura das programações</a> </label>
                                    <select class="select2" name="dadosCliente.indiceReajuste.id" class="form-control" data-rule-required="true">
                                        <c:forEach items="${audiostoreCategoriaBeanList}" var="categoria">
                                            <c:if test="${categoria.cliente.idcliente eq cliente.idcliente}">
                                                <option value="${categoria.codigo}">${categoria.categoria}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>


                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Frequencia de reprodução</label>
                            <input type="text" name="cliente_frequencia" class="form-control" placeholder="Frequencia de reprodução"  
                                   data-rule-required="true" 
                                   data-rule-minlength="3"
                                   data-rule-maxlength="255" value="${usuarioBean.nome}">
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Data inicial</label>
                            <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                <input type="text" name="cliente_datai" class="form-control datepicker" placeholder="Data inicial" 
                                       data-rule-required="true" 
                                       data-rule-minlength="10"
                                       data-rule-maxlength="10" value="${cf:dateFormat(null , "dd/MM/yyyy")}">

                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Data final</label>
                            <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                <input type="text" name="cliente_dataf" class="form-control datepicker" placeholder="Data final" 
                                       data-rule-required="true" 
                                       data-rule-minlength="10"
                                       data-rule-maxlength="10" value="30/12/2050">

                            </div>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label>Data de vencimento</label>
                            <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                                <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                                <input type="text" name="cliente_datav" class="form-control datepicker" placeholder="Data final" 
                                       data-rule-required="true" 
                                       data-rule-minlength="10"
                                       data-rule-maxlength="10" value="31/12/2050">

                            </div>
                        </div>
                    </div>


                    <div class="col-md-12" style="background-color: #FFF; padding-top: 10px; border-top: 1px solid #ebebeb; border-bottom: 1px solid #ebebeb;">
                        <label>Determinar horários:</label>
                        <br />
                        <button class="btn btn-flat btn-xs btn-info clonar_horario_modelo1">Adicionar horário</button> 
                        <button class="btn btn-flat btn-xs btn-info clonar_horario_modelo2">Adicionar horário com intervalo</button> 

                        <br />

                        <div style="background-color: #f1f1f1;padding: 10px;">
                            &nbsp;&nbsp;&nbsp;&nbsp;Horário inicial 
                            <input class="form-control input-sm horario_modelo_interval1a" maxlength="2"  style="display: inline; width: 33px; margin-left: 10px; margin-right: 5px;" />
                            :
                            <input class="form-control input-sm horario_modelo_interval1b" maxlength="2" style="display: inline; width: 33px; margin-left: 5px; margin-right: 10px;" />



                            &nbsp;&nbsp;&nbsp;&nbsp;Horário final 
                            <input class="form-control input-sm horario_modelo_interval2a" maxlength="2" style="display: inline; width: 33px; margin-left: 10px; margin-right: 5px;" />
                            :
                            <input class="form-control input-sm horario_modelo_interval2b" maxlength="2" style="display: inline; width: 33px; margin-left: 5px;" />



                            &nbsp;&nbsp;&nbsp;&nbsp;Intervalo 
                            <input class="form-control input-sm horario_modelo_interval3a" maxlength="2" style="display: inline; width: 33px; margin-left: 10px; margin-right: 5px;" />
                            :
                            <input class="form-control input-sm horario_modelo_interval3b" maxlength="2" style="display: inline; width: 33px; margin-left: 5px;" />


                            &nbsp;&nbsp;&nbsp;&nbsp;

                            <button class="btn btn-flat btn-xs btn-success btn-checkbox btn-filtro-checked0" data-checked="1" data-label-true="Interromper" data-label-false="Não Interromper">
                                <i class="fa fa-check"></i>
                                <span>Interromper</span>
                            </button>

                            &nbsp;&nbsp;&nbsp;&nbsp;

                            <button class="btn btn-flat btn-xs btn-success btn-checkbox btn-filtro-checked1" data-checked="1" data-label-true="Segunda" data-label-false="Segunda">
                                <i class="fa fa-check"></i>
                                <span>Segunda</span>
                            </button>

                            <button class="btn btn-flat btn-xs btn-success btn-checkbox btn-filtro-checked2" data-checked="1" data-label-true="Terça" data-label-false="Terça">
                                <i class="fa fa-check"></i>
                                <span>Terça</span>
                            </button>

                            <button class="btn btn-flat btn-xs btn-success btn-checkbox btn-filtro-checked3" data-checked="1" data-label-true="Quarta" data-label-false="Quarta">
                                <i class="fa fa-check"></i>
                                <span>Quarta</span>
                            </button>

                            <button class="btn btn-flat btn-xs btn-success btn-checkbox btn-filtro-checked4" data-checked="1" data-label-true="Quinta" data-label-false="Quinta">
                                <i class="fa fa-check"></i>
                                <span>Quinta</span>
                            </button>

                            <button class="btn btn-flat btn-xs btn-success btn-checkbox btn-filtro-checked5" data-checked="1" data-label-true="Sexta" data-label-false="Sexta">
                                <i class="fa fa-check"></i>
                                <span>Sexta</span>
                            </button>

                            <button class="btn btn-flat btn-xs btn-success btn-checkbox btn-filtro-checked6" data-checked="1" data-label-true="Sabado" data-label-false="Sabado">
                                <i class="fa fa-check"></i>
                                <span>Sabado</span>
                            </button>

                            <button class="btn btn-flat btn-xs btn-success btn-checkbox btn-filtro-checked7" data-checked="1" data-label-true="Domingo" data-label-false="Domingo">
                                <i class="fa fa-check"></i>
                                <span>Domingo</span>
                            </button>
                        </div>

                        <div class="form-group">
                            <table class="horario_tbl" style="display: none;">
                                <thead>
                                    <tr>
                                        <th style="width: 60px; font-weight: bold; color: red;"></th>
                                        <th style="width: 100px; font-weight: bold; color: red;">Interomper</th>
                                        <th style="width: 140px; font-weight: bold; color: red;">Horário</th>
                                        <th style="font-weight: bold; color: red;">Dias da semana</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="horario_modelo" style="display: none;">
                                        <td> <button class="btn btn-flat btn-xs btn-danger remover_horario_modelo"><i class="fa fa-trash-o"></i></button> </td>
                                        <td> 
                                            <button class="btn btn-flat btn-xs btn-inverse btn-checkbox" data-checked="0" data-label-true="Sim" data-label-false="Não">
                                                <i class="fa fa-times"></i>
                                                <span>Não</span>
                                            </button>
                                        </td>
                                        <td> 
                                            <input class="form-control" value="" style="display: inline; width: 33px; margin-right: 5px;" /> 
                                            :
                                            <input class="form-control" value="" style="display: inline; width: 33px; margin-left: 5px;" /> 
                                        </td>
                                        <td> 

                                            <button class="btn btn-flat btn-xs btn-success btn-checkbox" data-checked="1" data-label-true="Segunda" data-label-false="Segunda">
                                                <i class="fa fa-check"></i>
                                                <span>Segunda</span>
                                            </button>

                                            <button class="btn btn-flat btn-xs btn-success btn-checkbox" data-checked="1" data-label-true="Terça" data-label-false="Terça">
                                                <i class="fa fa-check"></i>
                                                <span>Terça</span>
                                            </button>

                                            <button class="btn btn-flat btn-xs btn-success btn-checkbox" data-checked="1" data-label-true="Quarta" data-label-false="Quarta">
                                                <i class="fa fa-check"></i>
                                                <span>Quarta</span>
                                            </button>

                                            <button class="btn btn-flat btn-xs btn-success btn-checkbox" data-checked="1" data-label-true="Quinta" data-label-false="Quinta">
                                                <i class="fa fa-check"></i>
                                                <span>Quinta</span>
                                            </button>

                                            <button class="btn btn-flat btn-xs btn-success btn-checkbox" data-checked="1" data-label-true="Sexta" data-label-false="Sexta">
                                                <i class="fa fa-check"></i>
                                                <span>Sexta</span>
                                            </button>

                                            <button class="btn btn-flat btn-xs btn-success btn-checkbox" data-checked="1" data-label-true="Sabado" data-label-false="Sabado">
                                                <i class="fa fa-check"></i>
                                                <span>Sabado</span>
                                            </button>

                                            <button class="btn btn-flat btn-xs btn-success btn-checkbox" data-checked="1" data-label-true="Domingo" data-label-false="Domingo">
                                                <i class="fa fa-check"></i>
                                                <span>Domingo</span>
                                            </button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div style="height: 300px;"></div>

                    <div class="col-md-12" style="background-color: #f1f1f1; padding-top: 10px; border-top: 1px solid #ebebeb; border-bottom: 1px solid #ebebeb;">
                        <label>Unidades:</label>
                        <div class="form-group">
                            <c:forEach items="${clienteBeanList}" var="mcliente">
                                <c:if test="${mcliente.matriz and mcliente.parente eq 0}">
                                    <table class="pkcm" data-pkcm="${mcliente.idcliente}" style="display: none;">
                                        <thead>
                                            <tr>
                                                <th style="width: 80px;">
                                                    <label class="radio-inline"> <input data-chk-pkcm="${mcliente.idcliente}" type="checkbox" class="icheck"  name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="${true}"></label>
                                                </th>
                                                <th style="width: 200px; font-weight: bold; color: red;">Nome</th>
                                                <th style="width: 120px; font-weight: bold; color: red;">Cod. Interno</th>
                                                <th style="width: 120px; font-weight: bold; color: red;">Cod. Externo</th>
                                                <th style="font-weight: bold; color: red;">Endereço</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${clienteBeanListOrderByCod}" var="cliente">
                                                <c:if test="${not cliente.matriz and cliente.parente ne 0 and mcliente.idcliente eq cliente.parente}">
                                                    <tr>
                                                        <td>
                                                            <label class="radio-inline"> <input data-chk-unid-pkcm="${mcliente.idcliente}" type="checkbox" class="icheck"  name="dadosCliente.renovacaoAutomatica" id="optionsRadios1" value="${true}"></label>
                                                        </td>

                                                        <td>${cliente.nome}</td>
                                                        <td>${cliente.codigoInterno}</td>
                                                        <td>${cliente.codigoExterno}</td>
                                                        <td>
                                                            <span class="label label-default">${cliente.endereco.cep.bairro.rua} - ${cliente.endereco.cep.bairro.nome}</span>  
                                                            <span class="label label-primary">${cliente.endereco.cep.bairro.cidade.nome}</span>  
                                                            <span class="label label-danger">${cliente.endereco.cep.bairro.cidade.estado.nome} / ${cliente.endereco.cep.bairro.cidade.estado.regiao.nome}</span>        
                                                        </td> 
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>



                    <div class="clearfix"></div>
                    <div class="row obs3_container" style="padding: 10px;">
                        <div class="col-md-1 obs3_clonar modelo_obs" style="display: none;">
                            <div class="header">
                                <div style="  margin-top: 90px; float: left;">
                                    &nbsp;&nbsp;&nbsp;&nbsp;<b class="bold">Usuário:</b> Alex Valentim Gonçalves
                                    <br />
                                    &nbsp;&nbsp;&nbsp;&nbsp;<b class="bold">Data:</b> 01/02/2015 00:25:37
                                    <br />
                                </div>
                            </div>
                            <div class="body">
                                <hr style="border: 0px; border-top: 1px dashed blue; opacity: 0.2;" />
                                <div class="content"></div>

                                <div class="clearfix"></div>

                                <br />

                                <div class="pull-left">
                                    <i class="fa fa-pencil obs3-btn-edt"></i>
                                </div>

                                <div class="pull-right">

                                    <i class="fa fa-trash-o obs3-btn-rm"></i>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="clearfix"></div>
                <br />  
                <button data-toggle="modal" data-target="#obs-modal3"  type="button" class="btn btn-default" style="margin-left: 0px;"><i class="fa fa-plus"></i> Adicionar Observações</button>              
            </div>


        </div>
    </div>


    <div data-backdrop="static" data-keyboard="false" class="modal-admin modal fade modal-wide" id="obs-modal" tabindex="-1" data-class-edt="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header" style="background-color: #f1f1f1; border: 0px;">
                    <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                    <!--<h4 class="modal-title" id="myModalLabel">Observação da Ordem de Serviço</h4>-->
                    <div id="toolbar">
                        <div class="btn-group" style="float: left;">
                            <button class="ql-bold btn btn-primary btn-xs"><i class="fa fa-bold"></i></button>
                            <button class="ql-italic btn btn-primary btn-xs"><i class="fa fa-italic"></i></button>
                            <button class="ql-underline btn btn-primary btn-xs"><i class="fa fa-underline"></i></button>
                            <button class="ql-list btn btn-primary btn-xs"><i class="fa fa-list-ol"></i></button>
                        </div>

                        <span class="ql-format-group">
                            <span title="Gras" class="ql-format-button ql-bold"></span>
                            <span class="ql-format-separator"></span>
                            <span title="Italique" class="ql-format-button ql-italic"></span>
                            <span class="ql-format-separator"></span>
                            <span title="Souligner" class="ql-format-button ql-underline"></span>

                            <div style="display: inline-block; margin-left: 5px;">
                                alinhar <select title="Text Alignment" class="ql-align form-control input-sm ql-select-style">
                                    <option value="left">esquerda</option>
                                    <option value="center">centro</option>
                                    <option value="right">direita</option>
                                    <option value="justify">justificado</option>
                                </select>
                            </div>

                            <div style="display: inline-block; margin-left: 5px;">
                                tamanho<select title="Size" class="ql-size ql-select-style form-control input-sm" style="margin-left: 10px;">
                                    <option value="10px">pequeno</option>
                                    <option value="13px" selected>normal</option>
                                    <option value="18px">medio</option>
                                    <option value="32px">Grande</option>
                                </select>    
                            </div>


                            <div style="display: inline-block; margin-left: 5px;">
                                color<select title="Text Color" class="ql-color ql-select-style form-control input-sm" style="margin-left: 10px;">
                                    <option value="black" selected>preto</option>
                                    <option value="white" selected>branco</option>
                                    <option value="red" >vermelho</option>
                                    <option value="green" >verde</option>
                                    <option value="blue" >azul</option>
                                    <option value="pink" >rosa</option>
                                </select>    
                            </div>

                            <div style="display: inline-block; margin-left: 5px;">
                                cor de fundo<select title="Text Color" class="ql-background ql-select-style form-control input-sm" style="margin-left: 10px;">
                                    <option value="white" selected>branco</option>
                                    <option value="black" selected>preto</option>
                                    <option value="red" >vermelho</option>
                                    <option value="green" >verde</option>
                                    <option value="blue" >azul</option>
                                    <option value="pink" >rosa</option>
                                </select>    
                            </div>

                    </div>
                </div>
                <div class="modal-body" style="padding: 0px !important">
                    <!--<textarea id="ckedt" class="ckedt form-control" name="audiostoreComercialBean.texto" rows="10" data-rule-required="true"></textarea>-->
                    <div id="editor">

                    </div>
                </div>

                <div class="modal-footer" style="background-color: #f1f1f1; border: 0px; background-color: #f1f1f1; border: 0px; padding: 0px; padding-right: 20px; padding-top: 5px; padding-bottom: 0px; padding-left: 20px; margin-top: 3px;">
                    <button type="button" class="btn btn-default btn-xs btn-close-obs1" data-dismiss="modal">Fechar</button>
                    <button type="button" class="btn btn-primary btn-xs btn-add-obs1">Salvar</button>
                </div>
            </div>
        </div>
    </div>


    <div data-backdrop="static" data-keyboard="false" class="modal-admin modal fade modal-wide" id="obs-modal2" tabindex="-1" data-class-edt="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header" style="background-color: #f1f1f1; border: 0px;">
                    <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                    <!--<h4 class="modal-title" id="myModalLabel">Observação da Ordem de Serviço</h4>-->
                    <div id="toolbar2">
                        <div class="btn-group" style="float: left;">
                            <button class="ql-bold btn btn-primary btn-xs"><i class="fa fa-bold"></i></button>
                            <button class="ql-italic btn btn-primary btn-xs"><i class="fa fa-italic"></i></button>
                            <button class="ql-underline btn btn-primary btn-xs"><i class="fa fa-underline"></i></button>
                            <button class="ql-list btn btn-primary btn-xs"><i class="fa fa-list-ol"></i></button>
                        </div>

                        <span class="ql-format-group">
                            <span title="Gras" class="ql-format-button ql-bold"></span>
                            <span class="ql-format-separator"></span>
                            <span title="Italique" class="ql-format-button ql-italic"></span>
                            <span class="ql-format-separator"></span>
                            <span title="Souligner" class="ql-format-button ql-underline"></span>

                            <div style="display: inline-block; margin-left: 5px;">
                                alinhar <select title="Text Alignment" class="ql-align form-control input-sm ql-select-style">
                                    <option value="left">esquerda</option>
                                    <option value="center">centro</option>
                                    <option value="right">direita</option>
                                    <option value="justify">justificado</option>
                                </select>
                            </div>

                            <div style="display: inline-block; margin-left: 5px;">
                                tamanho<select title="Size" class="ql-size ql-select-style form-control input-sm" style="margin-left: 10px;">
                                    <option value="10px">pequeno</option>
                                    <option value="13px" selected>normal</option>
                                    <option value="18px">medio</option>
                                    <option value="32px">Grande</option>
                                </select>    
                            </div>


                            <div style="display: inline-block; margin-left: 5px;">
                                color<select title="Text Color" class="ql-color ql-select-style form-control input-sm" style="margin-left: 10px;">
                                    <option value="black" selected>preto</option>
                                    <option value="white" selected>branco</option>
                                    <option value="red" >vermelho</option>
                                    <option value="green" >verde</option>
                                    <option value="blue" >azul</option>
                                    <option value="pink" >rosa</option>
                                </select>    
                            </div>

                            <div style="display: inline-block; margin-left: 5px;">
                                cor de fundo<select title="Text Color" class="ql-background ql-select-style form-control input-sm" style="margin-left: 10px;">
                                    <option value="white" selected>branco</option>
                                    <option value="black" selected>preto</option>
                                    <option value="red" >vermelho</option>
                                    <option value="green" >verde</option>
                                    <option value="blue" >azul</option>
                                    <option value="pink" >rosa</option>
                                </select>    
                            </div>

                    </div>
                </div>
                <div class="modal-body" style="padding: 0px !important">
                    <!--<textarea id="ckedt" class="ckedt form-control" name="audiostoreComercialBean.texto" rows="10" data-rule-required="true"></textarea>-->
                    <div id="editor2">

                    </div>
                </div>

                <div class="modal-footer" style="background-color: #f1f1f1; border: 0px; background-color: #f1f1f1; border: 0px; padding: 0px; padding-right: 20px; padding-top: 5px; padding-bottom: 0px; padding-left: 20px; margin-top: 3px;">
                    <button type="button" class="btn btn-default btn-xs btn-close-obs2" data-dismiss="modal">Fechar</button>
                    <button type="button" class="btn btn-primary btn-xs btn-add-obs2">Salvar</button>
                </div>
            </div>
        </div>
    </div>



    <div data-backdrop="static" data-keyboard="false" class="modal-admin modal fade modal-wide" id="obs-modal3" tabindex="-1" data-class-edt="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header" style="background-color: #f1f1f1; border: 0px;">
                    <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                    <!--<h4 class="modal-title" id="myModalLabel">Observação da Ordem de Serviço</h4>-->
                    <div id="toolbar3">
                        <div class="btn-group" style="float: left;">
                            <button class="ql-bold btn btn-primary btn-xs"><i class="fa fa-bold"></i></button>
                            <button class="ql-italic btn btn-primary btn-xs"><i class="fa fa-italic"></i></button>
                            <button class="ql-underline btn btn-primary btn-xs"><i class="fa fa-underline"></i></button>
                            <button class="ql-list btn btn-primary btn-xs"><i class="fa fa-list-ol"></i></button>
                        </div>

                        <span class="ql-format-group">
                            <span title="Gras" class="ql-format-button ql-bold"></span>
                            <span class="ql-format-separator"></span>
                            <span title="Italique" class="ql-format-button ql-italic"></span>
                            <span class="ql-format-separator"></span>
                            <span title="Souligner" class="ql-format-button ql-underline"></span>

                            <div style="display: inline-block; margin-left: 5px;">
                                alinhar <select title="Text Alignment" class="ql-align form-control input-sm ql-select-style">
                                    <option value="left">esquerda</option>
                                    <option value="center">centro</option>
                                    <option value="right">direita</option>
                                    <option value="justify">justificado</option>
                                </select>
                            </div>

                            <div style="display: inline-block; margin-left: 5px;">
                                tamanho<select title="Size" class="ql-size ql-select-style form-control input-sm" style="margin-left: 10px;">
                                    <option value="10px">pequeno</option>
                                    <option value="13px" selected>normal</option>
                                    <option value="18px">medio</option>
                                    <option value="32px">Grande</option>
                                </select>    
                            </div>


                            <div style="display: inline-block; margin-left: 5px;">
                                color<select title="Text Color" class="ql-color ql-select-style form-control input-sm" style="margin-left: 10px;">
                                    <option value="black" selected>preto</option>
                                    <option value="white" selected>branco</option>
                                    <option value="red" >vermelho</option>
                                    <option value="green" >verde</option>
                                    <option value="blue" >azul</option>
                                    <option value="pink" >rosa</option>
                                </select>    
                            </div>

                            <div style="display: inline-block; margin-left: 5px;">
                                cor de fundo<select title="Text Color" class="ql-background ql-select-style form-control input-sm" style="margin-left: 10px;">
                                    <option value="white" selected>branco</option>
                                    <option value="black" selected>preto</option>
                                    <option value="red" >vermelho</option>
                                    <option value="green" >verde</option>
                                    <option value="blue" >azul</option>
                                    <option value="pink" >rosa</option>
                                </select>    
                            </div>

                    </div>
                </div>
                <div class="modal-body" style="padding: 0px !important">
                    <!--<textarea id="ckedt" class="ckedt form-control" name="audiostoreComercialBean.texto" rows="10" data-rule-required="true"></textarea>-->
                    <div id="editor3">

                    </div>
                </div>

                <div class="modal-footer" style="background-color: #f1f1f1; border: 0px; background-color: #f1f1f1; border: 0px; padding: 0px; padding-right: 20px; padding-top: 5px; padding-bottom: 0px; padding-left: 20px; margin-top: 3px;">
                    <button type="button" class="btn btn-default btn-xs btn-close-obs3" data-dismiss="modal">Fechar</button>
                    <button type="button" class="btn btn-primary btn-xs btn-add-obs3">Salvar</button>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-primary btn-salvar-tudo" style="margin-left: 30px; margin-top: -50px;">Salvar</button>
    <button type="button" class="btn btn-primary btn-salvar-tudo-email" style="margin-left: 10px; margin-top: -50px;">Salvar e enviar e-mail</button>

    <div class="mask_progress">
        <div class="body">
            <div class="pg">
                <span>25% Aguarde, estamos salvando informações da orden de serviço</span>
                <div class="color"></div>
            </div>
        </div>
    </div>

    <style type="text/css">
        .mask_progress {
            display: none;
            width: 100%;
            height: 100%;

            position: fixed;
            top:0;
            left: 0;

            background-color: rgba(0,0,0,0.8);
        }

        .mask_progress .body {
            display: block;
            width: 480px;
            height: 40px;

            position: fixed;
            top:50%;
            left: 50%;
            margin-left: -250px;

            background-color: #FFF;
            border: 1px solid #c3c3c3;

            padding: 10px;
        }

        .mask_progress .body .pg {
            display: block;
            width: 100%;
            height: 10px;
            background-color: #0d3556;
            padding: 10px;
        }

        .mask_progress .body .pg span {
            display: block;
            margin-top: -8px;
            z-index: 9999;
            position: absolute;
            color: white;
        }

        .mask_progress .body .pg .color {
            display: block;
            width: 30%;
            height: 20px;
            position: absolute;
            background-color: #2494f2;
            margin-top: -10px;
            margin-left: -10px;
        }
    </style>

    <!-- Include the Quill library -->
    <script src="${url_resources}quill/quill.js"></script>

    <script>
        jQuery(document).ready(function () {
            var json_post = {
                "enviar_email": 0,
                "cliente": 1,
                "nome": "xxx",
                "quem_solicitou": "smjsjsj",
                "quando_solicitou": "01/01/2050",
                "data_distribuicao": "01/01/2050",
                "usuario": 1,
                "data": "01/01/2050",
                "locutor": 1,
                "texto_comercial": "asadaasas",
                "praxo_locucao": "01/01/2050",
                "praxo_estudio": "01/01/2050",
                "tipo_comercial": 1,
                "categoria": 1,
                "frequencia": 1,
                "dinicial": "01/01/2050",
                "dfinal": "01/01/2050",
                "dvencimento": "01/01/2050",
                "horarios": [
                    {"interromper": 1, "horario": "00:01:00", "dias": [1, 1, 1, 1, 1, 1, 1]},
                    {"interromper": 1, "horario": "00:01:00", "dias": [1, 1, 1, 1, 1, 1, 1]},
                    {"interromper": 1, "horario": "00:01:00", "dias": [1, 1, 1, 1, 1, 1, 1]}
                ],
                "obs": [
                    {"usuario": "1", "data": "01/01/2050", "texto": "aaa", "tipo": 0},
                    {"usuario": "1", "data": "01/01/2050", "texto": "bbb", "tipo": 0},
                    {"usuario": "1", "data": "01/01/2050", "texto": "ccc", "tipo": 0}
                ]
            };


            //-> VALIDAR DADOS
            jQuery('.btn-salvar-tudo').on('click', function () {
                var pkcm = jQuery('[name="cliente_matriz"]').val();
//                if ('' == pkcm || null == pkcm || undefined == pkcm) {
//                    bootbox.alert('Selecione um cliente', function () {
//                    });
//                    jQuery('[name="cliente_matriz"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="campanha"]').val() || null == jQuery('[name="campanha"]').val() || undefined == jQuery('[name="campanha"]').val()) {
//                    bootbox.alert('Informe o nome / campanha ', function () {
//                    });
//                    jQuery('[name="campanha"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="quem_solicitou"]').val() || null == jQuery('[name="quem_solicitou"]').val() || undefined == jQuery('[name="quem_solicitou"]').val()) {
//                    bootbox.alert('Informe o quem solicitou ', function () {
//                    });
//                    jQuery('[name="quem_solicitou"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="quando_solicitou"]').val() || null == jQuery('[name="quando_solicitou"]').val() || undefined == jQuery('[name="quando_solicitou"]').val()) {
//                    bootbox.alert('Informe o quando solicitou ', function () {
//                    });
//                    jQuery('[name="quando_solicitou"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="data_max_distribuicao"]').val() || null == jQuery('[name="data_max_distribuicao"]').val() || undefined == jQuery('[name="data_max_distribuicao"]').val()) {
//                    bootbox.alert('Informe a data máxima para distribuição ', function () {
//                    });
//                    jQuery('[name="quando_solicitou"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="locutores_lista"]').val() || null == jQuery('[name="locutores_lista"]').val() || undefined == jQuery('[name="locutores_lista"]').val()) {
//                    bootbox.alert('Selecione um locutor  ', function () {
//                    });
//                    jQuery('[name="locutores_lista"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="prazo_locucao"]').val() || null == jQuery('[name="prazo_locucao"]').val() || undefined == jQuery('[name="prazo_locucao"]').val()) {
//                    bootbox.alert('Informe a data máxima para a finalização da locução ', function () {
//                    });
//                    jQuery('[name="prazo_locucao"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="prazo_max_estudio"]').val() || null == jQuery('[name="prazo_max_estudio"]').val() || undefined == jQuery('[name="prazo_max_estudio"]').val()) {
//                    bootbox.alert('Informe a data máxima para a finalização de processamento de audio ', function () {
//                    });
//                    jQuery('[name="prazo_max_estudio"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="cliente_frequencia"]').val() || null == jQuery('[name="cliente_frequencia"]').val() || undefined == jQuery('[name="cliente_frequencia"]').val()) {
//                    bootbox.alert('Informe a frequencia de reprodução do audio ', function () {
//                    });
//                    jQuery('[name="cliente_frequencia"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="cliente_datai"]').val() || null == jQuery('[name="cliente_datai"]').val() || undefined == jQuery('[name="cliente_datai"]').val()) {
//                    bootbox.alert('Informe a data de inicio do comercial ', function () {
//                    });
//                    jQuery('[name="cliente_datai"]').focus();
//                    return;
//                }
//
//                if ('' == jQuery('[name="cliente_dataf"]').val() || null == jQuery('[name="cliente_dataf"]').val() || undefined == jQuery('[name="cliente_dataf"]').val()) {
//                    bootbox.alert('Informe a data final do comercial ', function () {
//                    });
//                    jQuery('[name="cliente_dataif"]').focus();
//                    return;
//                }
//
//                // validação mais complexa
//                if (jQuery('input:radio.tipo_comerciala').is(':checked')) {
//                    if ('' == jQuery('[data-cat="' + pkcm + '"]').val() || null == jQuery('[data-cat="' + pkcm + '"]').val() || undefined == jQuery('[data-cat="' + pkcm + '"]').val()) {
//                        bootbox.alert('Selecione uma categoria ', function () {
//                        });
//                        jQuery('[name="cliente_dataif"]').focus();
//                        return;
//                    }
//                } else {
//                    if (jQuery('.hdet').size() == 0) {
//                        bootbox.alert('Adicione um horário determinado', function () {
//                        });
//                        return;
//                    }
//                }
//                var alguma_unidade_marcada = false;
//                jQuery('[data-pkcm="' + pkcm + '"] tbody tr').each(function () {
//                    var input = jQuery(this).children('td:nth-child(1)').children('label').children('div').children('input');
//                    if (input.is(':checked')) {
//                        alguma_unidade_marcada = true;
//                        return false;
//                    }
//                });
//
//                if (!alguma_unidade_marcada) {
//                    bootbox.alert('Selecione uma unidade', function () {
//                    });
//                    return;
//                }
//
                json_post.cliente = pkcm;
                json_post.nome = jQuery('[name="campanha"]').val();
                json_post.quem_solicitou = jQuery('[name="quem_solicitou"]').val();
                json_post.quando_solicitou = jQuery('[name="quando_solicitou"]').val();
                json_post.data_distribuicao = jQuery('[name="data_max_distribuicao"]').val();
                json_post.usuario = "${sessionUsuario.usuarioBean.idusuario}";
                json_post.data = "${dataAtualStr}";
                json_post.locutor = jQuery('[name="locutores_lista"]').val();
                json_post.texto_comercial = jQuery('#locutor_texto').val();
                json_post.prazo_locucao = jQuery('[name="prazo_locucao"]').val();
                json_post.prazo_estudio = jQuery('[name="prazo_max_estudio"]').val();
                json_post.tipo_comercial = jQuery('[name="tipo"]').val();
                json_post.categoria = jQuery('[data-cat="' + pkcm + '"]').val();
                json_post.frequencia = jQuery('[name="cliente_frequencia"]').val();
                json_post.dinicial = jQuery('[name="cliente_datai"]').val();
                json_post.dfinal = jQuery('[name="cliente_dataf"]').val();
                json_post.dvencimento = jQuery('[name="cliente_datav"]').val();


                jQuery('.obs1_container').children('.modelo_obs:not(.obs1_clonar)').each(function () {
                    json_post.obs.push({
                        "usuario": "${sessionUsuario.usuarioBean.idusuario}",
                        "data": "${dataAtualStr}",
                        "texto": jQuery(this).children('.body').children('.content').html(),
                        "tipo": 1
                    });
                });

                jQuery('.mask_progress .body .pg span').text('25% Aguarde, estamos salvando informações da orden de serviço');
                jQuery('.mask_progress').show();

                jQuery.ajax({
                    type: 'POST',
                    url: '${url}/orden-servico/salvar-parte1',
                    data: {
                        cliente: pkcm,
                        nome: jQuery('[name="campanha"]').val(),
                        quemSolicitou: jQuery('[name="quem_solicitou"]').val(),
                        quandoSolicitou: jQuery('[name="quando_solicitou"]').val(),
                        dataMaxDistr: jQuery('[name="data_max_distribuicao"]').val(),
                        tipo: jQuery('[name="tipo"]').val(),
                    },
                    success: function (res1) {
                        if (res1.int > 0) {
                            jQuery('.mask_progress .body .pg span').text('35% Aguarde, estamos salvando as observações');
                            jQuery('.mask_progress').show();

                            jQuery('.obs1_container').children('.modelo_obs:not(.obs1_clonar)').each(function () {
                                
                                jQuery.ajax({
                                    async : false,
                                    type: 'POST',
                                    url: '${url}/orden-servico/salvar-obs',
                                    data: {
                                        "fk": res1.int,
                                        "data": "${dataAtualStr}",
                                        "html": jQuery(this).children('.body').children('.content').html(),
                                        "tipo": 1
                                    },
                                    success: function (res2) {
                                        console.log(res2);
                                    },
                                    error: function (res2) {
                                        console.log(res2);
                                    }
                                });
                            });
                            
                            jQuery.ajax({
                                async : false,
                                type: 'POST',
                                url: '${url}/orden-servico/salvar-parte2',
                                data: {
                                    "fk": res1.int,
                                    "locutor": parseInt(jQuery('[name="locutores_lista"]').val()),
                                    "texto": jQuery('#locutor_texto').html(),
                                    "prazo":  jQuery('[name="prazo_locucao"]').val()
                                },
                                success: function (res3) {
                                    console.log(res3);
                                },
                                error: function (res3) {
                                    console.log(res3);
                                }
                            });
                        }
                    },
                    error: function (res1) {
                        console.log(res1);
                    }
                });
            });
            //-> FIMVALIDAR DADOS

            jQuery(document).on('click', '.remover_horario_modelo', function () {
                jQuery(this).parent().parent().remove();
                if (jQuery('.hdet').size() == 0) {
                    jQuery('.horario_tbl').hide();
                }
            });

            jQuery(document).on('click', '.btn-checkbox', function () {
                var checked = jQuery(this).data('checked');
                var labelTrue = jQuery(this).data('labelTrue');
                var labelFalse = jQuery(this).data('labelFalse');

                if (checked == 0) {
                    jQuery(this).children('span').text(labelTrue);
                    jQuery(this).removeClass('btn-inverse').addClass('btn-success');
                    jQuery(this).children('i').removeClass('fa-times').addClass('fa-check');
                    jQuery(this).data('checked', 1);
                } else {
                    jQuery(this).children('span').text(labelFalse);
                    jQuery(this).removeClass('btn-success').addClass('btn-inverse');
                    jQuery(this).children('i').removeClass('fa-check').addClass('fa-times');
                    jQuery(this).data('checked', 0);
                }
            });

            jQuery('.clonar_horario_modelo1').on('click', function () {
                var tbody = jQuery('.horario_modelo').parent('tbody');
                var clone = jQuery('.horario_modelo').clone();

                var d4 = new Date();

                clone.removeClass('horario_modelo').addClass('hdet').show();
                clone.children('td:nth-child(3)').children('input').val((String(d4.getHours()).length < 2 ? "0" + String(d4.getHours()) : String(d4.getHours())));
                clone.children('td:nth-child(3)').children('input').next().val((String(d4.getMinutes()).length < 2 ? "0" + String(d4.getMinutes()) : String(d4.getMinutes())));
                tbody.prepend(clone);
                jQuery('.horario_tbl').show();
            });

            jQuery('.clonar_horario_modelo2').on('click', function () {
                var tbody = jQuery('.horario_modelo').parent('tbody');


                var horario_modelo_interval1a = jQuery('.horario_modelo_interval1a').val();
                var horario_modelo_interval1b = jQuery('.horario_modelo_interval1b').val();

                var horario_modelo_interval2a = jQuery('.horario_modelo_interval2a').val();
                var horario_modelo_interval2b = jQuery('.horario_modelo_interval2b').val();

                var horario_modelo_interval3a = jQuery('.horario_modelo_interval3a').val();
                var horario_modelo_interval3b = jQuery('.horario_modelo_interval3b').val();

                if (null != horario_modelo_interval1a && undefined != horario_modelo_interval1a && '' != horario_modelo_interval1a &&
                        null != horario_modelo_interval1b && undefined != horario_modelo_interval1b && '' != horario_modelo_interval1b &&
                        null != horario_modelo_interval2a && undefined != horario_modelo_interval2a && '' != horario_modelo_interval2a &&
                        null != horario_modelo_interval2b && undefined != horario_modelo_interval2b && '' != horario_modelo_interval2b &&
                        null != horario_modelo_interval3a && undefined != horario_modelo_interval3a && '' != horario_modelo_interval3a &&
                        null != horario_modelo_interval3b && undefined != horario_modelo_interval3b && '' != horario_modelo_interval3b) {

                    if (!horario_modelo_interval1a.match(/^\d+$/)) {
                        bootbox.alert('O horário inicial deve conter um horário válido com apenas NUMERO', function () {
                        });
                        return;
                    }

                    if (!horario_modelo_interval1b.match(/^\d+$/)) {
                        bootbox.alert('O horário inicial deve conter um horário válido com apenas NUMERO', function () {
                        });
                        return;
                    }

                    if (!horario_modelo_interval2a.match(/^\d+$/)) {
                        bootbox.alert('O horário final deve conter um horário válido com apenas NUMERO', function () {
                        });
                        return;
                    }

                    if (!horario_modelo_interval2b.match(/^\d+$/)) {
                        bootbox.alert('O horário final deve conter um horário válido com apenas NUMERO', function () {
                        });
                        return;
                    }

                    if (!horario_modelo_interval3a.match(/^\d+$/)) {
                        bootbox.alert('O intervalo deve conter um horário válido com apenas NUMERO', function () {
                        });
                        return;
                    }

                    if (!horario_modelo_interval3b.match(/^\d+$/)) {
                        bootbox.alert('O intervalo deve conter um horário válido com apenas NUMERO', function () {
                        });
                        return;
                    }

                    var d1 = new Date(2000, 0, 1, parseInt(horario_modelo_interval1a), parseInt(horario_modelo_interval1b), 0);
                    var d2 = new Date(2000, 0, 1, parseInt(horario_modelo_interval2a), parseInt(horario_modelo_interval2b), 0);
                    var d3 = new Date(2000, 0, 1, parseInt(horario_modelo_interval3a), parseInt(horario_modelo_interval3b), 0);

                    var d4 = d1;


                    while (d4 <= d2) {
                        var val = (String(d4.getHours()).length < 2 ? "0" + String(d4.getHours()) : String(d4.getHours())) + ":" + (String(d4.getMinutes()).length < 2 ? "0" + String(d4.getMinutes()) : String(d4.getMinutes()));

                        var clone = jQuery('.horario_modelo').clone();
                        clone.removeClass('horario_modelo').show();
                        clone.children('td:nth-child(3)').children('input').val((String(d4.getHours()).length < 2 ? "0" + String(d4.getHours()) : String(d4.getHours())));
                        clone.children('td:nth-child(3)').children('input').next().val((String(d4.getMinutes()).length < 2 ? "0" + String(d4.getMinutes()) : String(d4.getMinutes())));

                        clone.children('td:nth-child(2)').children('button.btn-checkbox:nth-child(1)').attr('data-checked', jQuery('.btn-filtro-checked0').data('checked'));
                        clone.children('td:nth-child(2)').children('button.btn-checkbox:nth-child(1)').removeClass('btn-inverse').removeClass('btn-success').addClass(jQuery('.btn-filtro-checked0').data('checked') == 1 ? 'btn-success' : 'btn-inverse');
                        clone.children('td:nth-child(2)').children('button.btn-checkbox:nth-child(1)').children('i').removeClass('fa-check').removeClass('fa-times').addClass(jQuery('.btn-filtro-checked0').data('checked') == 1 ? 'fa-check' : 'fa-times');
                        clone.children('td:nth-child(2)').children('button.btn-checkbox:nth-child(1)').children('span').text(jQuery('.btn-filtro-checked0').data('checked') == 1 ? 'Sim' : 'Não');

                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(1)').attr('data-checked', jQuery('.btn-filtro-checked1').data('checked'));
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(1)').removeClass('btn-inverse').removeClass('btn-success').addClass(jQuery('.btn-filtro-checked1').data('checked') == 1 ? 'btn-success' : 'btn-inverse');
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(1)').children('i').removeClass('fa-check').removeClass('fa-times').addClass(jQuery('.btn-filtro-checked1').data('checked') == 1 ? 'fa-check' : 'fa-times');

                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(2)').attr('data-checked', jQuery('.btn-filtro-checked2').data('checked'));
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(2)').removeClass('btn-inverse').removeClass('btn-success').addClass(jQuery('.btn-filtro-checked2').data('checked') == 1 ? 'btn-success' : 'btn-inverse');
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(2)').children('i').removeClass('fa-check').removeClass('fa-times').addClass(jQuery('.btn-filtro-checked2').data('checked') == 1 ? 'fa-check' : 'fa-times');

                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(3)').attr('data-checked', jQuery('.btn-filtro-checked3').data('checked'));
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(3)').removeClass('btn-inverse').removeClass('btn-success').addClass(jQuery('.btn-filtro-checked3').data('checked') == 1 ? 'btn-success' : 'btn-inverse');
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(3)').children('i').removeClass('fa-check').removeClass('fa-times').addClass(jQuery('.btn-filtro-checked3').data('checked') == 1 ? 'fa-check' : 'fa-times');

                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(4)').attr('data-checked', jQuery('.btn-filtro-checked4').data('checked'));
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(4)').removeClass('btn-inverse').removeClass('btn-success').addClass(jQuery('.btn-filtro-checked4').data('checked') == 1 ? 'btn-success' : 'btn-inverse');
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(4)').children('i').removeClass('fa-check').removeClass('fa-times').addClass(jQuery('.btn-filtro-checked4').data('checked') == 1 ? 'fa-check' : 'fa-times');

                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(5)').attr('data-checked', jQuery('.btn-filtro-checked5').data('checked'));
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(5)').removeClass('btn-inverse').removeClass('btn-success').addClass(jQuery('.btn-filtro-checked5').data('checked') == 1 ? 'btn-success' : 'btn-inverse');
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(5)').children('i').removeClass('fa-check').removeClass('fa-times').addClass(jQuery('.btn-filtro-checked5').data('checked') == 1 ? 'fa-check' : 'fa-times');

                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(6)').attr('data-checked', jQuery('.btn-filtro-checked6').data('checked'));
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(6)').removeClass('btn-inverse').removeClass('btn-success').addClass(jQuery('.btn-filtro-checked6').data('checked') == 1 ? 'btn-success' : 'btn-inverse');
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(6)').children('i').removeClass('fa-check').removeClass('fa-times').addClass(jQuery('.btn-filtro-checked6').data('checked') == 1 ? 'fa-check' : 'fa-times');

                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(7)').attr('data-checked', jQuery('.btn-filtro-checked7').data('checked'));
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(7)').removeClass('btn-inverse').removeClass('btn-success').addClass(jQuery('.btn-filtro-checked7').data('checked') == 1 ? 'btn-success' : 'btn-inverse');
                        clone.children('td:nth-child(4)').children('button.btn-checkbox:nth-child(7)').children('i').removeClass('fa-check').removeClass('fa-times').addClass(jQuery('.btn-filtro-checked7').data('checked') == 1 ? 'fa-check' : 'fa-times');

                        tbody.append(clone);

                        d4 = new Date(2000, 0, 1, (d4.getHours() + d3.getHours()), (d4.getMinutes() + d3.getMinutes()), 0);
                    }
                } else {
                    bootbox.alert('Para poder adicionar um horário determinado com intervalos você deve informar um horário inicial , um horário final e um intervalo', function () {
                    });
                }
                jQuery('.horario_tbl').show();
            });

            jQuery('[name="locutores_lista"]').on('change', function () {
                jQuery('.data-voz').hide();
                jQuery('[data-voz="' + jQuery('[name="locutores_lista"]').val() + '"]').show();
            });

            jQuery('[name="cliente_matriz"]').on('change', function () {
                var pkcm = jQuery(this).val();
                jQuery('.pkcm').hide();
                jQuery('[data-pkcm="' + pkcm + '"]').show();

                jQuery('[data-chk-pkcm="' + pkcm + '"]').on('ifChecked', function () {
                    jQuery('[data-chk-unid-pkcm="' + pkcm + '"]').iCheck('check');
                });

                jQuery('[data-chk-pkcm="' + pkcm + '"]').on('ifUnchecked', function () {
                    jQuery('[data-chk-unid-pkcm="' + pkcm + '"]').iCheck('uncheck');
                });

                jQuery('.data-cat').hide();
                jQuery('[data-cat="' + pkcm + '"]').show();
            });

            jQuery('.link_ver_prog').on('click', function () {
                var winOpen = window.open("${url}/orden-servico/visualizar-estrutura-das-programacoes", "", "width=600, height=480");
                return false;
            });

            var letra_quill = new Quill('#locutor_texto');
            letra_quill.addModule('toolbar', {container: '#locutor_toolbar'});


            var quill = new Quill('#editor');
            quill.addModule('toolbar', {container: '#toolbar'});

            var quill2 = new Quill('#editor2');
            quill2.addModule('toolbar', {container: '#toolbar2'});

            var quill3 = new Quill('#editor3');
            quill3.addModule('toolbar', {container: '#toolbar3'});

            jQuery('.ckedt').ckeditor({
                toolbar: [
                    {name: 'clipboard', items: ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo']},
                    {name: 'editing', items: ['Find', 'Replace', '-', 'SelectAll', '-', 'SpellChecker', 'Scayt']},
                    {name: 'basicstyles', items: ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat']},
                    {name: 'paragraph', items: ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl']},
                    {name: 'insert', items: ['Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe']},
                    {name: 'Fonts', items: ['Styles', 'Format', 'Font', 'FontSize']},
                ]
            });

            jQuery(document).on('click', '.obs1-btn-rm', function () {
                jQuery(this).parent().parent().parent().remove();
            });

            jQuery(document).on('click', '.obs1-btn-edt', function () {
                console.log(quill);
                quill.setHTML(jQuery(this).parent().parent().children('.content').html());
                var classe = '.' + jQuery(this).parent().parent().children('.content').attr('class').split(' ').join('.');
                jQuery('[data-target="#obs-modal"]').attr('data-class-edt', classe).click();
            });

            jQuery('.btn-close-obs1').on('click', function () {
                quill.setHTML('');
                jQuery('[data-target="#obs-modal"]').removeAttr('data-class-edt');
            });

            jQuery('.btn-add-obs1').on('click', function () {
                var dataClassEdt = jQuery('[data-target="#obs-modal"]').attr('data-class-edt');

                if (null != dataClassEdt && undefined != dataClassEdt && '' != dataClassEdt) {
                    jQuery(dataClassEdt).html('');
                    jQuery(dataClassEdt).html(jQuery('#editor').html());
                } else {
                    var clone = jQuery('.obs1_clonar').clone();
                    var x = Math.floor((Math.random() * 10) + 1);
                    clone.removeClass('obs1_clonar').show();
                    clone.children('.body').children('.content').addClass('obs1_content_' + x);
                    clone.children('.body').children('.content').append(jQuery('#editor').html());
                    jQuery('.obs1_container').append(clone);
                }

                jQuery('.btn-close-obs1').click();
                quill.setHTML('');
                jQuery('[data-target="#obs-modal"]').removeAttr('data-class-edt');
            });


            //-- 

            jQuery(document).on('click', '.obs2-btn-rm', function () {
                jQuery(this).parent().parent().parent().remove();
            });

            jQuery(document).on('click', '.obs2-btn-edt', function () {
                console.log(quill);
                quill2.setHTML(jQuery(this).parent().parent().children('.content').html());
                var classe = '.' + jQuery(this).parent().parent().children('.content').attr('class').split(' ').join('.');
                jQuery('[data-target="#obs-modal2"]').attr('data-class-edt', classe).click();
            });

            jQuery('.btn-close-obs2').on('click', function () {
                quill2.setHTML('');
                jQuery('[data-target="#obs-modal2"]').removeAttr('data-class-edt');
            });

            jQuery('.btn-add-obs2').on('click', function () {
                var dataClassEdt = jQuery('[data-target="#obs-modal2"]').attr('data-class-edt');

                if (null != dataClassEdt && undefined != dataClassEdt && '' != dataClassEdt) {
                    jQuery(dataClassEdt).html('');
                    jQuery(dataClassEdt).html(jQuery('#editor2').html());
                } else {
                    var clone = jQuery('.obs2_clonar').clone();
                    var x = Math.floor((Math.random() * 10) + 1);
                    clone.removeClass('obs2_clonar').show();
                    clone.children('.body').children('.content').addClass('ob2_content_' + x);
                    clone.children('.body').children('.content').append(jQuery('#editor2').html());
                    jQuery('.obs2_container').append(clone);
                }

                jQuery('.btn-close-obs2').click();
                quill.setHTML('');
                jQuery('[data-target="#obs-modal2"]').removeAttr('data-class-edt');
            });


            //-- 

            jQuery(document).on('click', '.obs3-btn-rm', function () {
                jQuery(this).parent().parent().parent().remove();
            });

            jQuery(document).on('click', '.obs3-btn-edt', function () {
                console.log(quill);
                quill3.setHTML(jQuery(this).parent().parent().children('.content').html());
                var classe = '.' + jQuery(this).parent().parent().children('.content').attr('class').split(' ').join('.');
                jQuery('[data-target="#obs-modal3"]').attr('data-class-edt', classe).click();
            });

            jQuery('.btn-close-obs3').on('click', function () {
                quill3.setHTML('');
                jQuery('[data-target="#obs-modal3"]').removeAttr('data-class-edt');
            });

            jQuery('.btn-add-obs3').on('click', function () {
                var dataClassEdt = jQuery('[data-target="#obs-modal3"]').attr('data-class-edt');

                if (null != dataClassEdt && undefined != dataClassEdt && '' != dataClassEdt) {
                    jQuery(dataClassEdt).html('');
                    jQuery(dataClassEdt).html(jQuery('#editor3').html());
                } else {
                    var clone = jQuery('.obs3_clonar').clone();
                    var x = Math.floor((Math.random() * 10) + 1);
                    clone.removeClass('obs3_clonar').show();
                    clone.children('.body').children('.content').addClass('ob3_content_' + x);
                    clone.children('.body').children('.content').append(jQuery('#editor3').html());
                    jQuery('.obs3_container').append(clone);
                }

                jQuery('.btn-close-obs3').click();
                quill.setHTML('');
                jQuery('[data-target="#obs-modal3"]').removeAttr('data-class-edt');
            });
        });
    </script>

    <style>
        @font-face {
            font-family: 'pencil';
            src: url('${url_resources}fonts/ZakirahsHand.ttf?v=4.1.0') format('truetype');
            font-weight: normal;
            font-style: normal;
        }
        @font-face {
            font-family: 'pencilB';
            src: url('${url_resources}fonts/ZakirahsHandB.ttf?v=4.1.0') format('truetype');
            font-weight: normal;
            font-style: normal;
        }
        .cke_chrome {
            border: 0px;
            margin-left: -20px;
            margin-right: -20px;
            margin-bottom: -35px;
            margin-top: -20px;
        }

        .cke_top {
            border: 0px;
            background-color: #FFF;
            background-image: none;
            border-bottom: 1px solid #ebebeb;
        }

        .cke_bottom {
            border: 0px;
            background-color: #FFF;
            background-image: none;
        }


        .modelo_obs .body .content .cke_editable
        {
            font-size: 13px;
            line-height: 1.6em;
        }

        .modelo_obs .body .content  blockquote
        {
            font-style: italic;
            font-family: Georgia, Times, "Times New Roman", serif;
            padding: 2px 0;
            border-style: solid;
            border-color: #ccc;
            border-width: 0;
        }

        .modelo_obs .body .content  .cke_contents_ltr blockquote
        {
            padding-left: 20px;
            padding-right: 8px;
            border-left-width: 5px;
        }

        .modelo_obs .body .content  .cke_contents_rtl blockquote
        {
            padding-left: 8px;
            padding-right: 20px;
            border-right-width: 5px;
        }

        .modelo_obs .body .content  a
        {
            color: #0782C1;
        }

        .modelo_obs .body .content  ol,ul,dl
        {
            /* IE7: reset rtl list margin. (#7334) */
            *margin-right: 0px;
            /* preserved spaces for list items with text direction other than the list. (#6249,#8049)*/
            padding: 0 40px;
        }

        .modelo_obs .body .content  h1,h2,h3,h4,h5,h6
        {
            font-weight: normal;
            line-height: 1.2em;
        }

        .modelo_obs .body .content  hr
        {
            border: 0px;
            border-top: 1px solid #ebebeb;
        }

        .modelo_obs .body .content img.right
        {
            border: 1px solid #ccc;
            float: right;
            margin-left: 15px;
            padding: 5px;
        }

        .modelo_obs .body .content  img.left
        {
            border: 1px solid #ccc;
            float: left;
            margin-right: 15px;
            padding: 5px;
        }

        .modelo_obs .body .content  pre
        {
            white-space: pre-wrap; /* CSS 2.1 */
            word-wrap: break-word; /* IE7 */
            background-color: transparent;
            color: blue;
            border: 0px;
        }

        .modelo_obs .body .content  .marker
        {
            background-color: red;
            color: #FFF;
            padding: 4px 10px 0px 6px;
            border-radius: 24px;
        }

        .modelo_obs .body .content  span[lang]
        {
            font-style: italic;
        }

        .modelo_obs {
            display: block;
            width: 373px; 
            background-size: 100%;
        }

        .modelo_obs .body .content {
            display: block;
            width: 337px;
            word-break: break-all;
        }

        .modelo_obs .body { 
            display: block;
            width: 359px;
            background-color: #fdf976;
            padding: 10px;
            padding-top: 0px;
            max-width: 373px;
            margin-left: 6px;
            margin-top: -29px;
            font-family: pencil;
            color: blue;
            padding-bottom: 4px;
        }

        .modelo_obs .header {
            width: 373px;
            height: 145px;
            background-image: url(${url_img}paper4.png); 
            font-family: pencil;
            font-size: 16px;
            color: blue;
        }

        .bold {
            font-weight: bold;
        }

        .ql-select-style  {
            display: inline;
            width: 65px;
            height: 22px !important;
            border-radius: 0px;
            padding: 0px 1px !important;
            /* box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.12), 1px 1px 0 rgba(255, 255, 255, 0.2) inset; */
            margin-top: 0px;
        }

        .modal.modal-wide .modal-dialog {
            width: 60%;
        }

        /*            .ql-editor > * {
                        font-family: pencil;
                    }*/

        .ql-container {
            box-sizing: border-box;
            cursor: text;
            font-family: Helvetica,Arial,sans-serif;
            font-size: 13px;
            height: 100%;
            line-height: 1.42;
            margin: 0;
            overflow-x: hidden;
            overflow-y: auto;
            padding: 5px;
            padding-bottom: 0px;
            position: relative;
        }

        #locutor_texto .ql-editor {
            min-height: 100px !important;
            font-family: Arial !important;
        }

        .modal-body .ql-container .ql-editor {
            /*min-height: 400px;*/
        }
    </style>

    <div style="height: 600px;"></div>
</jsp:body>
</instore:template>