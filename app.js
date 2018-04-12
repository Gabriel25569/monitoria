var express = require('express');
var app = express();
var bodyParser = require('body-parser');

// Importar classes do entediante
var Connection = require('tedious').Connection;
var Request = require('tedious').Request;

var config = {
    userName: 'BD16195',
    password: 'BD14002',
    server: 'Regulus'
};

var connection = new Connection(config);


app.get('/', function(req, res) {
    res.send("Utilize /api para entrar na api!");
});

app.get('/api/alunos', function(req, res) {
    
});

app.listen(8080);
console.log('Rodando na porta 8080');
