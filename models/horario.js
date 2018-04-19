var Request = require('tedious').Request;
var TYPES = require('tedious').TYPES;

var Horario = {
    setConnection: function(connection) {
        Horario._connection = connection;
    },

    getHorarios: function(ra, callback) {
        if (ra < 10000 || ra > 99999) {
            callback(null);
        }

        let horarios = new Array();
        
        request = new Request('select inicio, fim, dia from HorarioMonitor where ra = @ra', function(err, rowCount) {
            if (err) {
                callback(null);
            } else {
                callback(horarios);
            }
        });

        request.on('row', function (columns) { 
            let inicio = String(columns[0].value).split(" ")[4].substr(0, 5);
            let fim = String(columns[1].value).split(" ")[4].substr(0, 5);
            let dia = columns[2].value;
            
            horarios.push({inicio: inicio, fim: fim, dia: dia});
        });

        request.addParameter("ra", TYPES.Int, ra);

        Horario._connection.execSql(request);
    }
}

module.exports = Horario;