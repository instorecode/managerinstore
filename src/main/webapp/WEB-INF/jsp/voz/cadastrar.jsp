<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/voz" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Vozes </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/voz">
            <input type="hidden" name="vozBean.idvoz" value="${vozBean.idvoz}" />
            <input type="hidden" name="vozBean.tipo" value="0" />


            <div class="row">
                <div class="col-md-9">
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="vozBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${vozBean.nome}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Cliente</label>
                        <br />
                        <select  class="select2 select_cliente" name="vozBean.cliente.idcliente" data-rule-required="true" >
                            <c:forEach items="${clienteBeanList}" var="cliente">
                                <option value="${cliente.idcliente}" ${cliente.idcliente eq vozBean.cliente.idcliente ? 'selected="selected"' : ''}>${cliente.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>E-mail</label>
                        <input type="text" name="vozBean.email" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-email="true"
                               data-rule-maxlength="255" 
                               value="${vozBean.email}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Tel.</label>
                        <input type="text" name="vozBean.tel" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="20" value="${vozBean.tel}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Genero</label>
                        <br />
                        <select data-selectradio="true" class="form-control" name="vozBean.genero" data-rule-required="true" >
                            <option value="${true}"  ${vozBean.genero ? 'selected="selected"' :''} >Masculino</option>
                            <option value="${false}" ${not vozBean.genero ? 'selected="selected"' :''} >Feminino</option>
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