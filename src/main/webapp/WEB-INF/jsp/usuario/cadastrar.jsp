<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/usuario" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Usuarios </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/usuario">
            <input type="hidden" name="usuarioBean.idusuario" value="${usuarioBean.idusuario}" />
            <input type="hidden" name="usuarioBean.dataCadastro" value="${cf:dateFormat(usuarioBean.dataCadastro, 'dd/MM/yyyy HH:mm:ss')}" />

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="usuarioBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${usuarioBean.nome}">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label>CPF</label>
                        <input type="text" name="usuarioBean.cpf" class="form-control" placeholder="CPF"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="14" 
                               data-mask="999.999.999-99"
                               value="${usuarioBean.cpf}">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>E-mail</label>
                        <input type="text" name="usuarioBean.email" class="form-control" placeholder="E-mail"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-email="true"
                               data-rule-maxlength="255" value="${usuarioBean.email}">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Senha</label>
                        <input type="password" name="usuarioBean.senha" class="form-control" placeholder="Senha"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group"> 
                        <label>CEP</label>
                        <input type="text" name="usuarioBean.endereco.cep.numero" class="form-control cepload" placeholder="CEP" 
                               data-mask="99.999-999"
                               data-url="${url}/utilidades/cepload" value="${usuarioBean.endereco.cep.numero}"
                               data-rule-required="true" 
                               data-rule-minlength="10"
                               data-rule-maxlength="10">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Estado</label>
                        <select name="usuarioBean.endereco.cep.bairro.cidade.estado.idestado" class="form-control est" data-rule-required="true">
                            <option value>Selecione um estado</option>
                            <c:forEach items="${estadoBeanList}" var="est">
                                <option value="${est.idestado}" data-uf="${est.sigla}" ${usuarioBean.endereco.cep.bairro.cidade.estado.idestado eq est.idestado ? 'selected="selected"' : ''}>${est.sigla} - ${est.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group"> 
                        <label>Cidade</label>
                        <input type="text" name="usuarioBean.endereco.cep.bairro.cidade.nome" class="form-control cid" placeholder="Cidade" 
                               value="${usuarioBean.endereco.cep.bairro.cidade.nome}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group"> 
                        <label>Bairro</label>
                        <input type="text" name="usuarioBean.endereco.cep.bairro.nome" class="form-control bai" placeholder="Bairro" 
                               value="${usuarioBean.endereco.cep.bairro.nome}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group"> 
                        <label>Tipo Log.</label>
                        <input type="text" name="usuarioBean.endereco.cep.bairro.tipo" class="form-control tipo_log" placeholder="Tipo do Logradouro" 
                               value="${usuarioBean.endereco.cep.bairro.tipo}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">

                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group"> 
                        <label>Logradouro</label>
                        <input type="text" name="usuarioBean.endereco.cep.bairro.rua" class="form-control log" placeholder="Logradouro" 
                               value="${usuarioBean.endereco.cep.bairro.rua}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">

                    </div>
                </div>

                <div class="col-md-1">
                    <div class="form-group"> 
                        <label>Nº</label>
                        <input type="text" name="usuarioBean.endereco.numero" class="form-control num" placeholder="Número" 
                               value="${usuarioBean.endereco.numero}"
                               data-rule-required="true" 
                               data-rule-minlength="1"
                               data-rule-maxlength="255">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Complemento</label>
                        <input type="text" name="usuarioBean.endereco.complemento" class="form-control comp" placeholder="Complemento" 
                               value="${usuarioBean.endereco.complemento}"
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group"> 
                        <label>Permissões</label>
                        <br />
                        <select class="select2"  name="perfilListID"  multiple>
                            <c:forEach items="${perfilBeanList}" var="perfil">
                                <c:set var="selected" value="${false}"></c:set>
                                <c:forEach items="${usuarioBean.perfilBeanList}" var="perfil2">
                                    <c:if test="${perfil2.idperfil eq perfil.idperfil}">
                                        <c:set var="selected" value="${true}"></c:set>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${selected}">
                                    <option selected="selected" value="${perfil.idperfil}">${perfil.nome}</option>
                                </c:if>
                                <c:if test="${not selected}">
                                    <option value="${perfil.idperfil}">${perfil.nome}</option>
                                </c:if>

                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>


            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>