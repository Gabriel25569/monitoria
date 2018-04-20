var express = require('express');
var app = express();

var Monitor = require('./models/monitor.js');
var Horario = require('./models/horario.js');

var connection = require('./scripts/connection.js');

Monitor.setConnection(connection);
Horario.setConnection(connection);

app.get('/', function(req, res) {
    res.send('Utilize /api para entrar na api!');
});

app.get('/api', function(req, res) {
    res.send('/monitores/{RA}/horarios/{DIA}');
});

app.get('/api/monitores/', function(req, res) {
    Monitor.getMonitores(function (monitores) {
        if (monitores) {
            console.log('GET: monitores');
            res.send(monitores);
        } else {
            res.sendStatus(404);
        }
    });
});

app.get('/api/monitores/:_ra', function(req, res) {
    let ra = req.params._ra;

    Monitor.getMonitor(ra, function(monitor) {
        if (monitor) {
            console.log('GET: monitor ' + ra);
            res.send(monitor);
        } else {
            res.sendStatus(404);
        }
    });
});

app.get('/api/monitores/:_ra/horarios', function(req, res) {
    let ra = req.params._ra;

    Horario.getHorarios(ra, 0, function(horarios) {
        if (horarios) {
            console.log('GET: horários ' + ra);
            res.send(horarios);
        } else {
            res.sendStatus(404);
        }
    })
});


app.get('/api/monitores/:_ra/horarios/:_dia', function(req, res) {
    let ra = req.params._ra;
    let dia = req.params._dia || 0;

    Horario.getHorarios(ra, dia, function(horarios) {
        if (horarios) {
            console.log('GET: horários ' + ra + ', dia ' + dia);
            res.send(horarios);
        } else {
            res.sendStatus(404);
        }
    })
});

app.listen(8080);
console.log('Rodando na porta 8080');
