/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery(document).ready(function() {

    jQuery.criarCsv = function() {
//        msg_fadeIn();
        var info = jQuery(".pag_info").text();
        info = info.split(" ");
        var last = info.length - 1;
        info = info[last];
        var url = document.URL;
        url = url.replace('#', '');
        var pagina = url + "?datajson=true&rows=" + info;

        var texto = "";
        var tabela = jQuery("table.xtable");
        var pontoVirgula = "";
        var th = tabela.children("thead").children("tr").children("th").each(function() {
//            if (jQuery(this).attr('options') == "false") {
            texto += pontoVirgula + jQuery(this).text();
            pontoVirgula = ";";
//            }
        });

        texto += "\n";
        pontoVirgula = "";



        $.ajax({
            dataType: "json",
            type: "GET",
            url: pagina,
            success: function(dados) {
                var rows = dados.rows;
                $.each(dados.rows, function(key1, value) {
                    $.each(value, function(key, value) {
                        texto += pontoVirgula + value;
                        pontoVirgula = ";";
                    });
                    texto += "\n";
                    pontoVirgula = "";

                });
                var titulo = jQuery("div.page-head").text();

                var a_hidden = document.createElement("a");
                a_hidden.download = jQuery.trim(titulo) + ".csv";
                a_hidden.href = 'data:text/csv;charset=utf-8,%EF%BB%BF' + encodeURIComponent(texto);
                a_hidden.style = 'display: none; visibility: hidden;';
                a_hidden.click();
                jQuery(a_hidden).remove();

            }
        });
    };

    jQuery('#botaoCsv').on("click", function() {
        jQuery.criarCsv();
    });
});