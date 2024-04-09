const mysql = require('mysql2');

const dbConnection = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'shvetsov_login',
    port: "3307"
});

module.exports = dbConnection.promise();