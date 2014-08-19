<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<instore:template  menucolapse="false">
    <jsp:body>                             
        <div class="row show-case">
            <div class="col-sm-4 text-center">
                <img src="${url_cz}images/doc1.png" />       
                <h3>Menu dobrável</h3>
                <p>Nós adicionamos um recurso fixo-rolagem-dobrável, quando entrar em colapso, o sub-menu aparecerá ao lado da barra lateral.</p>
            </div>
            <div class="col-sm-4 text-center">
                <img src="${url_cz}images/doc2.png" />     
                <h3>Menu em destaque</h3>
                <p>Basta cliencar na seta ao fim do menu.</p>          
            </div>
            <div class="col-sm-4 text-center">
                <img src="${url_cz}images/doc3.png" />        
                <h3>Ajuste Automático</h3>
                <p>O menu e tambem toda a página se ajustam automaticamente quando a janela é redimensionada, e também cria um pergaminho, se não houver espaço vertical suficiente.</p>          
            </div>
        </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="block-flat">
                    <div class="header">
                        <div class="pull-right actions">
                        </div>							
                        <h3>Recomendações</h3>
                    </div>
                    <div class="content">
                        <p>Para uma melhor performance do sistema use navegadores com suporte á <code>HTML5</code> e <code>CSS3</code> . <code>Chrome</code>, <code>Firefox</code>, <code>Safare</code>, <code>Opera</code>.</p>
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
                        <p>Páginas com uma descrição contendo código <code>404</code> significa que a URL especificada pelo usuário não existe.
                            E em caso do código ser <code>500</code> este é um erro da aplicação e se faz necessária a urgente chamada do administrador para que o problema seja rápidamente solucionado.</p>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</instore:template>