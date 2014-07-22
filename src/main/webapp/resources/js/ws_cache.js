jQuery(document).ready(function() {
    var machine_id = jQuery.storage("machine_id");
    var ws_url = jQuery.storage("ws_url");

    var wsUri = ws_url + "/ws/limpar-cache";
    var websocket = new WebSocket(wsUri);

    websocket.onopen = function(evt) {
        onOpen(evt);
    };
    websocket.onmessage = function(evt) {
        onMessage(evt);
    };
    websocket.onerror = function(evt) {
        onError(evt);
    };


    function avisar() {
        websocket.send(machine_id);
    }

    function onOpen() {
        avisar();
        writeToScreen(machine_id + " abril uma conexão direta com " + wsUri);
    }

    function onMessage(evt) {
        if (evt.data != machine_id) {
            $.gritter.add({
                title: "Importante!",
                text: "Já existe novas informações registradas, o cache da aplicação será atualizado!",
                class_name: 'clean',
                position: 'bottom-right',
                time: ''
            });
            
            jQuery.storageClear();
            jQuery.storageAdd("machine_id",machine_id);
            jQuery.storageAdd("ws_url",ws_url);
        }
    }

    function onError(evt) {
        console.log(evt.data);
    }

    function writeToScreen(message) {
        console.log(message);
    }
});
