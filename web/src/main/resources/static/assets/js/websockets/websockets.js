var WebSockets = function () {
	var socket,
		stompClient;

	function __init() {
		socket = new SockJS('/ws');
		stompClient = Stomp.over(socket);
	}

	function connect(topic, _handler) {
		stompClient.connect({}, function () {
			stompClient.subscribe(topic, _handler);
		});
	}

	__init();

	return {
		connect: connect
	}
}();