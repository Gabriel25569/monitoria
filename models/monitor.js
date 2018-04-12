class Monitor {
    constructor (ra, nome) {
        this._ra = ra;
        this._nome = nome;
    }

    get ra () {
        
    }

    set ra (value) {
        if (value < 10000 || value > 99999)
    }

    get nome () {
        
    }
    
    set nome (value) {

    }

}


var Monitores = {
    getMonitores = function(callback) {
        request = new Request("select * from Monitor", function(err, rowCount) {
            if (err) {
                callback(err, null);
            } else {
                console.log(rowCount + ' rows');
            }
        });


        request.on('row', function (columns) { 

        });
    }
}