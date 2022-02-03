//Examples for Express JS and Routing in NodeJS through Express

var express = require('express')
var app = express()
const port = 3001;
app.get('/', function(req, res) {
res.send('Welcome to Node JS with Express')
})

//Execute this code with http://localhost:3000?id=30
app.get("/Greet", function(req,res)
{
	const id = req.query.id
	res.send("Hey Vallidevi Krishnamurthy " + id)
}
)

//Execute this code with http://localhost:3000/20
app.get('/Greet/20',function(req,res)
{
	res.send('Welcome back Vallidevi with the reg number 20')
}
)

//Execute this code with http://localhost:3000/22(any reg number)
app.get('/Greet/:id', function(req,res)
{
	const id =req.params.id
	res.status(200).send("Hai Valli "+(id).toString());
}
)
	
app.listen(3001,()=>
{
console.log(`Example app listening on ${port}!`)
});	

/*var express = require('express'),
    app = express.createServer(),
	 port = 1337;
app.listen(port);
    app.get('/', function(req, res){
res.send('Welcome to Node Twitter');
});
;

console.log('start express server\n');
*/
/*
const express = require('express')
const app = express();
const port = 3000;

app.get('/', (req, res) => {
  res.send('Hello World!')
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}!`)
});*/

/*
const express = require('express')
const app = express()
 
app.get('/', function (req, res) {
  res.send('Hello World')
})
 
app.listen(3000)
*/