
var wsUri = "ws://localhost:8080/managerinstore/ws/limpar-cache";
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
    websocket.send("alex!");
}

function onOpen() {
    writeToScreen("Connected to " + wsUri);
}

function onMessage(evt) {
    $.gritter.add({
        title: 'Just Text',
        text: 'This is a simple Gritter Notification.',
        class_name: 'clean',
        position: 'bottom-right',
        time: ''
    });
}

function onError(evt) {
    console.log(evt.data);
}

function writeToScreen(message) {
    console.log(message);
}