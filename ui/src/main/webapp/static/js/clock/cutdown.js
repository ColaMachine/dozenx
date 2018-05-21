(function() {
	var WIDTH = 1024;
	var HEIGHT = 400;
	var RADIUS = 1;
	var MARGIN_LEFT =300;
	var MARGIN_TOP = 0;

//  var endTime=new Date();
//  endTime.setTime(endTime.getTime()+3600*1000*24)
	var curShowTimeSeconds = getCurrentShowTimeSeconds();

	var balls = [];
	var colors = ['#33B5E5', '#0099CC', '#AA66CC', '#9933CC', '#99CC00', '#669900'];

	var canvas = document.getElementById("canvas");
	canvas.width = WIDTH;
	canvas.height = HEIGHT;
	var context = canvas.getContext("2d");

	setInterval(function() {
		render(context);
		update();
	}, 50);

	function update() {
		var nextShowTimeSeconds = getCurrentShowTimeSeconds();

		var nextHours = parseInt(nextShowTimeSeconds / 3600);
		var nextMinutes = parseInt((nextShowTimeSeconds - nextHours * 3600) / 60);
		var nextSeconds = nextShowTimeSeconds % 60;

		var curHours = parseInt(curShowTimeSeconds / 3600);
		var curMinutes = parseInt((curShowTimeSeconds - curHours * 3600) / 60);
		var curSeconds = curShowTimeSeconds % 60;

		if (nextSeconds != curSeconds) {

			if (parseInt(curHours / 10) != parseInt(nextHours / 10)) {
				addBalls(MARGIN_LEFT + 0, MARGIN_TOP, parseInt(curHours / 10));
			}
			if (parseInt(curHours % 10) != parseInt(nextHours % 10)) {
				addBalls(MARGIN_LEFT + 15 * (RADIUS + 1), MARGIN_TOP, parseInt(curHours % 10));
			}
			if (parseInt(curMinutes / 10) != parseInt(nextMinutes / 10)) {
				addBalls(MARGIN_LEFT + 39 * (RADIUS + 1), MARGIN_TOP, parseInt(curMinutes / 10));
			}
			if (parseInt(curMinutes % 10) != parseInt(nextMinutes % 10)) {
				addBalls(MARGIN_LEFT + 54 * (RADIUS + 1), MARGIN_TOP, parseInt(curMinutes % 10));
			}
			if (parseInt(curSeconds / 10) != parseInt(nextSeconds / 10)) {
				addBalls(MARGIN_LEFT + 78 * (RADIUS + 1), MARGIN_TOP, parseInt(curSeconds / 10));
			}
			if (parseInt(curSeconds % 10) != parseInt(nextSeconds % 10)) {
				addBalls(MARGIN_LEFT + 93 * (RADIUS + 1), MARGIN_TOP, parseInt(nextSeconds % 10));
			}
			curShowTimeSeconds = nextShowTimeSeconds;
		}

		updataBalls();
	};

	function updataBalls() {
		for (var i = 0; i < balls.length; i++) {
			balls[i].x += balls[i].vx;
			balls[i].y += balls[i].vy;
			balls[i].vy += balls[i].g;

			if (balls[i].y >= HEIGHT - RADIUS) {
				balls[i].y = HEIGHT - RADIUS;
				balls[i].vy = -balls[i].vy * 0.45;
			}
		}
		var cnt = 0;
		for (var i = 0; i < balls.length; i++) 
			if ((balls[i].x + RADIUS) > 0 && (balls[i].x - RADIUS) < WIDTH) 
				balls[cnt++] = balls[i];
				
				console.log(balls)
//			while (balls.length > cnt) {
//				balls.pop();
//			}
	};

	function addBalls(x, y, num) {
		for (var i = 0; i < digit[num].length; i++) {
			for (var j = 0; j < digit[num][i].length; j++) {
				if (digit[num][i][j] == 1) {
					var aball = {
						x: x + j * 2 * (RADIUS + 1) + (RADIUS + 1),
						y: y + i * 2 * (RADIUS + 1) + (RADIUS + 1),
						g: 1.5+Math.random(),
						vx: Math.pow(-1, Math.ceil(Math.random() * 1000))*4,
						vy: -5,
						color: colors[Math.floor(Math.random() * colors.length)]
					};
					balls.push(aball);
				}

			}
		}
		console.log(balls.length);
	}


	function render(context) {
		//		清除画布避免图像重叠
		context.clearRect(0, 0, WIDTH, HEIGHT);

		var hours = Math.floor(curShowTimeSeconds / 3600);
		var minutes = Math.floor((curShowTimeSeconds - hours * 3600) / 60);
		var seconds = Math.floor(curShowTimeSeconds % 60);

		renderDigit(MARGIN_LEFT, MARGIN_TOP, parseInt(hours / 10), context);
		renderDigit(MARGIN_LEFT + 15 * (RADIUS + 1), MARGIN_TOP, parseInt(hours % 10), context);
		renderDigit(MARGIN_LEFT + 30 * (RADIUS + 1), MARGIN_TOP, 10, context);
		renderDigit(MARGIN_LEFT + 39 * (RADIUS + 1), MARGIN_TOP, parseInt(minutes / 10), context);
		renderDigit(MARGIN_LEFT + 54 * (RADIUS + 1), MARGIN_TOP, parseInt(minutes % 10), context);
		renderDigit(MARGIN_LEFT + 69 * (RADIUS + 1), MARGIN_TOP, 10, context);
		renderDigit(MARGIN_LEFT + 78 * (RADIUS + 1), MARGIN_TOP, parseInt(seconds / 10), context);
		renderDigit(MARGIN_LEFT + 93 * (RADIUS + 1), MARGIN_TOP, parseInt(seconds % 10), context);
        	for (var i = 0; i < balls.length; i++) {
			context.fillStyle = balls[i].color;
			context.beginPath();
			context.arc(balls[i].x, balls[i].y, RADIUS, 0, 2 * Math.PI, true);
			context.closePath();
			context.fill();
		}
		
	};

	function renderDigit(x, y, num, context) {
		context.fillStyle = "#005588";

		for (var i = 0; i < digit[num].length; i++) {
			for (var j = 0; j < digit[num][i].length; j++) {
				if (digit[num][i][j] == 1) {
					var CenterX = x + j * 2 * (RADIUS + 1) + (RADIUS + 1);
					var CenterY = y + i * 2 * (RADIUS + 1) + (RADIUS + 1);
					context.beginPath();
					context.arc(CenterX, CenterY, RADIUS, 0, 2 * Math.PI);
					context.closePath();
					context.fill();
				}

			}
		}
	};

	function getCurrentShowTimeSeconds() {
		var curTime = new Date();
//		var ret = endTime.getTime() - curTime.getTime();
//		ret = Math.round(ret / 1000);
        var ret=curTime.getHours()*3600+curTime.getMinutes()*60+curTime.getSeconds();
		return ret >= 0 ? ret : 0;
	};




})();