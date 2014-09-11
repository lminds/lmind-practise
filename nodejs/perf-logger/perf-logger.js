var express = require('express');
var bodyParser = require("body-parser")
var app = express();


app.enable('trust proxy');
app.use(bodyParser.urlencoded({
	extended: false
}));

app.post('/collect', function(req, res){
	var data = req.param("data")
	var obj = JSON.parse(data)
	obj.client = req.ip
	
	console.log(JSON.stringify(obj));
	
	res.header("Access-Control-Allow-Origin", "*")
  res.send('ok');
});


var server = app.listen(8888, function() {
  console.log('Listening on port %d', server.address().port);
});