var WebSockets = function () {
	var socket,
		stompClient;

	function __init() {
		socket = new SockJS('/ws');
		stompClient = Stomp.over(socket);
	}

	function subscribe(topic, _handler) {
		stompClient.subscribe(topic, _handler);
	}

	function connect(success, failure) {
		stompClient.connect({}, success, failure);
	}

	__init();

	return {
		connect: connect,
		subscribe: subscribe
	}
}();