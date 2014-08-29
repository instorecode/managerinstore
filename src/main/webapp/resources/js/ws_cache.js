//jQuery(document).ready(function() {
//    var machine_id = jQuery.storage("machine_id");
//    var ws_url = jQuery.storage("ws_url");
//
//    var wsUri = ws_url + "/ws/limpar-cache";
//    var websocket = new WebSocket(wsUri);
//
//    websocket.onopen = function(evt) {
//        onOpen(evt);
//    };
//    websocket.onmessage = function(evt) {
//        onMessage(evt);
//    };
//    websocket.onerror = function(evt) {
//        onError(evt);
//    };
//
//    jQuery.avisar = function() {
//        websocket.send(machine_id);
//    }
//});
//
//
//
//function onOpen() {
//    console.log(jQuery.storage("machine_id") + " abril uma conexão direta com " + jQuery.storage("ws_url") + "/ws/limpar-cache");
//}
//
//function onMessage(evt) {
//    if (evt.data != jQuery.storage("machine_id")) {
//        $.gritter.add({
//            title: "Importante!",
//            text: "Já existe novas informações registradas, o cache da aplicação será atualizado!",
//            position: 'bottom-right',
//            class_name: 'danger'
//        });
//    }
//
//    var machine_id = jQuery.storage("machine_id");
//    var ws_url = jQuery.storage("ws_url");
//    var matriz_selecionada = jQuery.storage("matriz_selecionada");
//
//    jQuery.storageClear();
//    jQuery.storageAdd("machine_id", machine_id);
//    jQuery.storageAdd("ws_url", ws_url);
//    jQuery.storageAdd("matriz_selecionada", matriz_selecionada);
//}