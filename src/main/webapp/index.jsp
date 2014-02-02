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
				}, 3000);
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
				}, 3000);
	})();
	
	function pushLeft() {
		$.ajax({
		    url: '/garage/webapi/door/left/push',
		    type: 'GET',
		    success: function() { 
		    	alert('Completed'); 
		    }
		});
	}
	function pushRight() {
		$.ajax({
		    url: '/garage/webapi/door/right/push',
		    type: 'GET',
		    success: function() { 
		    	alert('Completed'); 
		    }
		});
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
			<h2>Right Door:</h2>
		</div>
		<div id="rightDoorPosition"></div>
		<div>
			<button onclick="pushRight()">Push Right</button>
		</div>
	</div>


</body>
</html>
