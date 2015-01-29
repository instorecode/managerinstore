/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery(document).ready(function(){
    
    jQuery(document).on("keypress",function(e){
        if(2 == e.keyCode){
           var btnVoltar = jQuery('a.btn-voltar');
           var link = btnVoltar.attr('href');
           if(jQuery('a.btn-voltar').size() > 0){
                window.location.href = link;
           }
       }
    });
    
    
});