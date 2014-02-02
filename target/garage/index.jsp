<html>
<head>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script>
	(function poll() {
		setTimeout(
				function() {
					$
							.ajax({
								type : 'GET',
								dataType : 'json',
								url : '/garage/webapi/door/left/position',
								success : function(data) {
									$("#leftDoorPosition").html("<h2>" + data.statusMessage + "</h2>");
								},
								complete : poll
							});
				}, 5000);
	})();

	(function poll() {
		setTimeout(
				function() {
					$
							.ajax({
								type : 'GET',
								dataType : 'json',
								url : '/garage/webapi/door/right/position',
								success : function(data) {
									$("#rightDoorPosition").html("<h2>" + data.statusMessage + "</h2>");
								},
								complete : poll
							});
				}, 5000);
	})();
	
	function pushLeft() {
		alert("left");
	}
	function pushLeft() {
		alert("Right");
	}
</script>
</head>

<body>

	<div>
		<div>
			<h2>Left Door:</h2>
		</div>
		<div id="leftDoorPosition"></div>
		<div>
			<button onclick="pushLeft()">Push Left</button>
		</div>
	</div>

	<div>
		<div>
			<h2>Roght Door:</h2>
		</div>
		<div id="rightDoorPosition"></div>
		<div>
			<button onclick="pushRight()">Push Left</button>
		</div>
	</div>


</body>
</html>
