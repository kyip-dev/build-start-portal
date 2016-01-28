define(function(require) {
	var $ = require('jquery');
	
	var URL = "ws://localhost:13001/messages/tail";
	var websocket;

	$(document).ready(function() {
		connect();
	});
 
	function connect() {
		websocket = new WebSocket(URL);
		websocket.onopen = function(evnt) {
			onOpen(evnt)
		};
		websocket.onmessage = function(evnt) {
			onMessage(evnt)
		};
		websocket.onerror = function(evnt) {
			onError(evnt)
		};
	}

	function onOpen() {
		updateStatus("connected")
	}
	function onMessage(evnt) {
		if (typeof evnt.data == "string") {
			$("#logContent").append(evnt.data).append('<br/>');
		}
		// scroll to bottom
		document.getElementById('logContent').scrollTop = document.getElementById('logContent').scrollHeight;
	}
	function onError(evnt) {
		alert('ERROR: ' + evnt.data);
	}
	function updateStatus(status) {
		if (status == "connected") {
			$("#status").removeClass(function(index, css) {
				return (css.match(/\blabel-\S+/g) || []).join(' ')
			});
			$("#status").text(status).addClass("label-success");
		}
	}


});