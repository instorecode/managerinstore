<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/clientes" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Clientes </a>
    </jsp:attribute>
    <jsp:body>

        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/clientes">
            <input type="hidden" name="id" value="${dadosCliente.iddadosCliente}" />

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Local dos arquivos de musica</label>
                        <input type="text" name="p1" class="form-control cid" placeholder="Local dos arquivos de musica" 
                               value="${dadosCliente.localOrigemMusica}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Destino dos arquivos de musica</label>
                        <input type="text" name="p2" class="form-control cid" placeholder="Destino dos arquivos de musica" 
                               value="${dadosCliente.localDestinoMusica}">
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Local dos arquivos de spot</label>
                        <input type="text" name="p3" class="form-control cid" placeholder="Local dos arquivos de spot" 
                               value="${dadosCliente.localOrigemSpot}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Destino dos arquivos de spot</label>
                        <input type="text" name="p4" class="form-control cid" placeholder="Destino dos arquivos de spot" 
                               value="${dadosCliente.localDestinoSpot}">
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="form-group"> 
                        <label>Destino dos arquivos Exp</label>
                        <input type="text" name="p5" class="form-control cid" placeholder="Destino dos arquivos Exp" 
                               value="${dadosCliente.localDestinoExp}">
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>

    </jsp:body>
</instore:template>