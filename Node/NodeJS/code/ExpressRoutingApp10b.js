const wiki = require('./ExpressRouting10a.js');
// ...
var express = require('express')
var app = express()
app.use('/wiki', wiki);