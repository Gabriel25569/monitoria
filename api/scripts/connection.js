var Connection = require('tedious').Connection;

var config = {
    userName: 'BD16195',
    password: 'BD14002',
    server: 'Regulus'
};

var connection = new Connection(config);

module.exports = connection;