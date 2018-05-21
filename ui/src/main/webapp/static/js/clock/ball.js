var ball = {
	x: 512,
	y: 100,
	r: 20,
	g: 2,
	vx: -4,
	vy: -10,
	color: '#005588'
};

var canvas = document.getElementById("canvas");
canvas.width = 1024;
canvas.height = 768;

var context = canvas.getContext("2d");

setInterval(function() {

	render(context);
	update();

}, 50)

function render(context) {
	context.clearRect(0, 0, context.canvas.width, context.canvas.height);
	context.fillStyle = ball.color;
	context.beginPath();
	context.arc(ball.x, ball.y, ball.r, 0, 2 * Math.PI);
	context.closePath();
	context.fill();
}

function update() {
	ball.x += ball.vx;
	ball.y += ball.vy;
	ball.vy += ball.g;
	
	if(ball.y>=canvas.height-ball.r){
		ball.y=canvas.height-ball.r;
		ball.vy=-ball.vy*0.5;
	}
	
}