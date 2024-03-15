const mysql = require("mysql2");
const db_connection = mysql.createConnection({
        host: "localhost", // имя хоста
        user: "root", // имя пользователя
        database: "pe-11b_shvetsov", // имя базы данных
        password: "", // пароль от базы данных
        port: "3307" 
    })
    .on("error", (err) => {
        console.log("Failed to connect to Database - ", err);
    });
module.exports = db_connection;