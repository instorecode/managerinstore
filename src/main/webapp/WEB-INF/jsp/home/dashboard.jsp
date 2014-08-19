<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<instore:template  menucolapse="false">
    <jsp:body>                             
        <div class="row show-case">
            <div class="col-sm-4 text-center">
                <img src="${url_cz}images/doc1.png" />       
                <h3>Menu dobr�vel</h3>
                <p>N�s adicionamos um recurso fixo-rolagem-dobr�vel, quando entrar em colapso, o sub-menu aparecer� ao lado da barra lateral.</p>
            </div>
            <div class="col-sm-4 text-center">
                <img src="${url_cz}images/doc2.png" />     
                <h3>Menu em destaque</h3>
                <p>Basta cliencar na seta ao fim do menu.</p>          
            </div>
            <div class="col-sm-4 text-center">
                <img src="${url_cz}images/doc3.png" />        
                <h3>Ajuste Autom�tico</h3>
                <p>O menu e tambem toda a p�gina se ajustam automaticamente quando a janela � redimensionada, e tamb�m cria um pergaminho, se n�o houver espa�o vertical suficiente.</p>          
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="block-flat">
                    <div class="header">
                        <div class="pull-right actions">
                        </div>							
                        <h3>Recomenda��es</h3>
                    </div>
                    <div class="content">
                        <p>Para uma melhor performance do sistema use navegadores com suporte � <code>HTML5</code> e <code>CSS3</code> . <code>Chrome</code>, <code>Firefox</code>, <code>Safare</code>, <code>Opera</code>.</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="block-flat">
                    <div class="header">
                        <div class="pull-right actions">
                        </div>							
                        <h3>Em caso de problema</h3>
                    </div>
                    <div class="content">
                        <p>P�ginas com uma descri��o contendo c�digo <code>404</code> significa que a URL especificada pelo usu�rio n�o existe.
                            E em caso do c�digo ser <code>500</code> este � um erro da aplica��o e se faz necess�ria a urgente chamada do administrador para que o problema seja r�pidamente solucionado.</p>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</instore:template>