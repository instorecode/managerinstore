/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery(document).ready(function() {
    
    jQuery.criarCsv = function() {
        msg_fadeIn();
        var texto = "";
        var textoTh = "";
        var tabela = jQuery("table.xtable");
        var virgula = "";
        var th = tabela.children("thead").children("tr").children("th").each(function() {
            if (jQuery(this).attr('options') == "false") {
                texto += virgula + jQuery(this).text();
                virgula = ";";
            }

        });
        
        texto +="\n";
        
        var vir = "";
        var td = tabela.children("tbody").each(function() {
            if (jQuery(this).children("tr").size() > 0) {
                jQuery(this).children("tr").each(function() {
                    if (jQuery(this).attr('class') == "row_data") {
                        jQuery(this).children("td").each(function() {
                            if("" != jQuery.trim(jQuery(this).text())) {
                                texto += vir + jQuery(this).text();
                                vir = ";";
                            }
                        });
                        texto += "\n";
                        vir = "";
                    }
                });
            }
        });
        
        var titulo = jQuery("div.page-head").text();
        
        var a_hidden = document.createElement("a");
        a_hidden.download = titulo+".csv";
        a_hidden.href = 'data:text/csv,' + encodeURIComponent(texto);
        a_hidden.style = 'display: none; visibility: hidden;ISO-8859-1';
        a_hidden.click();
        jQuery(a_hidden).remove();
        msg_fadeOut();
    };

    jQuery('#botaoCsv').on("click", function() {
        jQuery.criarCsv();
    });
});