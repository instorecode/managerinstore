<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<instore:template  menucolapse="false">
    <jsp:body>           
        <c:if test="${null == sessionUsuario.cliente}">
            <h1>ANTES DE QUALQUER AÇÃO SELECIONE UM CLIENTE PRIMEIRO</h1>
            <hr />
        </c:if>
            
        <c:if test="${null != sessionUsuario.cliente}">
            <h1>CLIENTE ${sessionUsuario.cliente.nome} SELECIONADO</h1>
            <hr />
        </c:if>
        
        <div class="row">
            <c:forEach items="${atalhoClienteList}" var="item">
                <div class="col-md-2">
                    <div class="well2">
                        <a class="selecionarCliente" href="${item.idcliente}">
                            <span class="txtPF">${item.nome}</span>
                        </a>
                    </div>
                </div>
            </c:forEach>  
        </div>
        
        <script type="text/javascript">
            jQuery(document).ready(function(){
                
                jQuery('.selecionarCliente').on('click' , function(){
                    jQuery.ajax({
                        url : '',
                        type: 'POST',
                        data : {
                            idcliente : parseInt(jQuery(this).attr('href'))
                        } ,
                        success : function(response) {
                            if(response != 1) {
                                bootbox.alert('Não foi possivel selecionar cliente' , function(){});
                            } else {
                                window.location.reload();
                                if(window.location.href.indexOf('#menu_show')==-1) {
                                    window.location.href = '${url}${proxUrl}';
                                }
                            }
                        } , 
                        error : function(response) {
                            console.log('ERROR: ');
                            bootbox.alert(response , function(){});
                        }
                    });
                    return false;
                });
            });
        </script>
    </jsp:body>
</instore:template>