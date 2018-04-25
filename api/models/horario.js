var Request = require('tedious').Request;
var TYPES = require('tedious').TYPES;

var Horario = {
    setConnection: function(connection) {
        Horario._connection = connection;
    },

    getHorarios: function(ra, dia, callback) {
        if (ra < 10000 || ra > 99999) {
            callback(null);
            return;
        }

        if (dia < 0 || dia > 7) {
            callback(null);
            return;
        }

        let query;
        if (dia == 0) {
            query = 'select inicio, fim, dia from HorarioMonitor where ra = @ra';
        } else {
            query = 'select inicio, fim, dia from HorarioMonitor where ra = @ra and dia = @dia';
        }

        let horarios = new Array();
        
        request = new Request(query, function(err, rowCount) {
            if (err) {
                callback(null);
                return;
            } else {
                callback(horarios);
                return;
            }
        });

        request.on('row', function (columns) { 
            columns[0].value.setTime(columns[0].value.getTime() + 7200000);
            columns[1].value.setTime(columns[1].value.getTime() + 7200000);

            let inicio = String(columns[0].value).split(" ")[4].substr(0, 5);
            let fim = String(columns[1].value).split(" ")[4].substr(0, 5);

            let dia = columns[2].value;
            
            horarios.push({inicio: inicio, fim: fim, dia: dia});
        });

        request.addParameter("ra", TYPES.Int, ra);
        
        if (dia != 0) {
            request.addParameter("dia", TYPES.Int, dia);
        }


        Horario._connection.execSql(request);
    }
}

module.exports = Horario;