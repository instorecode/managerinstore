<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/configuracao-interna">
            <input type="hidden" name="configAppBean.id" value="1" />

            <div class="row">
                <div class="col-md-9">
                    <div class="form-group">
                        <label>Caminho dos arquivos do sistema</label>
                        <input type="text" name="configAppBean.dataPath" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${configAppBean.dataPath}">
                    </div>
                    
                    <div class="form-group">
                        <label>Caminho de origem das musicas</label>
                        <input type="text" name="configAppBean.audiostoreMusicaDirOrigem" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${configAppBean.audiostoreMusicaDirOrigem}">
                    </div>
                    
                    <div class="form-group">
                        <label>Caminho de destino das musicas</label>
                        <input type="text" name="configAppBean.audiostoreMusicaDirDestino" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="255" value="${configAppBean.audiostoreMusicaDirDestino}">
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>
    </jsp:body>
</instore:template>