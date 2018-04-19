var Request = require('tedious').Request;
var TYPES = require('tedious').TYPES;

var Monitor = {
    setConnection: function(connection) {
        Monitor._connection = connection;
    },

    getMonitores: function(callback) {
        let monitores = new Array();

        request = new Request("select * from Monitor", function(err, rowCount) {
            if (err) {
                callback(null);
            } else {
                callback(monitores);
            }
        });

        request.on('row', function (columns) { 
            let ra = columns[0].value;
            let nome = columns[1].value;
            let imagem = columns[2].value.toString('base64');
            
            monitores.push({ra: ra, nome: nome, imagem: imagem});
        });

        Monitor._connection.execSql(request);
    },

    getMonitor: function(ra, callback) {
        if (ra < 10000 || ra > 99999) {
            callback(null);
        }

        let monitor;

        request = new Request('select * from Monitor where ra = @ra', function(err, rowCount) {
            if (err) {
                callback(null);
            } else {
                callback(monitor);
            }
        });
        
        request.on('row', function (columns) { 
            let ra = columns[0].value;
            let nome = columns[1].value;
            let imagem = columns[2].value.toString('base64');
            
            monitor = {ra: ra, nome: nome, imagem: imagem};
        });

        request.addParameter("ra", TYPES.Int, ra);

        Monitor._connection.execSql(request);
    }
}

module.exports = Monitor;