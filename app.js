//server configuration
var AppBuild = "v0.1";
var AppPort = 8088;
var DBHost = 'localhost';
var DBUser = 'root';
var DBPassword = '';
var DBName = 'underlist';

//routing namespace
var endpoint = "api/" + AppBuild + "/";
endpoint = "";

var app = require('express')();
var http = require('http').Server(app);
var mysql = require('mysql');
var bodyParser = require("body-parser");

//DB configuration
var connection = mysql.createConnection({
    host : DBHost,
    user : DBUser,
    password : DBPassword,
    database : DBName,
});

//App configuration
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

//Login endpoint - POST
app.post(endpoint + '/login', function(req, res){

   var username = req.body.username;
   console.log("LOGIN: " + username);
   var data = {
      "error": true,
      "message":""
   };
   connection.query("SELECT * FROM Users WHERE Users.username = ?", [username],function(err, rows, fields){
      if(rows.length > 0){
         data.error = false;
         data.user = rows;
         data.message = "Success";
         res.json(data);
      }
      else {
         res.json(data);
      }
   });
});

//Register endpoint - POST
app.post(endpoint + '/register', function(req, res) {

   var username = req.body.username;
   var name = req.body.name;

   var data = {
   };

   if(!!username && !!name) {

      //Check username using twitter standard
      if(/^@?(\w){1,15}$/.test(username)){

         connection.query("SELECT * FROM Users WHERE Users.username = ?", [username], function(err, rows, fields){

            //Check if it is a unique username
            if(rows.length === 0) {
               connection.query("INSERT INTO Users (Users.username, Users.name) VALUES(?, ?)", [username, name], function(err, rows, fields){
                  if(!!err) {
                     data.message = "Error adding user " + username;
                     data.debug = err;
                     res.json(data);
                  }
                  else {
                     data.error = false;
                     data.message = "User " + username + " added succesfully";
                     res.json(data);
                  }
               });
            }
            else {
               data.message = "Username " + username + " already exists!";
               res.json(data);
            }
         });
      }
      else {
         data.message = "Not a valid username";
         res.json(data);
      }
   }
   else {
      data.message = "Please provide all required data";
      res.json(data);
   }
});

//Tasks endpoint - GET
app.get(endpoint + '/tasks',function(req,res){
    var userId = req.body.user_id;
    var data = {
        "error":1,
        "tasks":""
    };

    connection.query("SELECT * from Tasks, UserTasks WHERE UserTasks.user_id = ?",[userId],function(err, rows, fields){
        if(rows.length !== 0){
            data.error = 0;
            data.tasks = rows;
            res.json(data);
        }else{
            data.tasks = 'No tasks Found..';
            res.json(data);
        }
    });
});

app.post(endpoint + '/task',function(req,res){
    var title = req.body.title;
    var description = req.body.description;
    var createdDate = req.body.createdDate;
    var dueDate = req.body.dueDate;
    var userId = req.body.userId;
    var data = {
        "error":1,
        "tasks":""
    };
    if(!!title && !!description && !!createdDate && !!dueDate && !!userId){
      //   connection.query("INSERT INTO book VALUES('',?,?,?)",[Bookname,Authorname,Price],function(err, rows, fields){
      //       if(!!err){
      //           data["Books"] = "Error Adding data";
      //       }else{
      //           data["error"] = 0;
      //           data["Books"] = "Book Added Successfully";
      //       }
      //       res.json(data);
      //   });
    }else{
      //   data["Books"] = "Please provide all required data (i.e : Bookname, Authorname, Price)";
      //   res.json(data);
    }
});

app.put(endpoint + '/task',function(req,res){
    var id = req.body.id;
    var title = req.body.title;
    var description = req.body.description;
    var createdDate = req.body.createdDate;
    var dueDate = req.body.dueDate;
    var userId = req.body.userId;
    var data = {
        "error":1,
        "tasks":""
    };
    if(!!id && !!title && !!description && !!createdDate && !!dueDate && !!userId){
      //   connection.query("UPDATE book SET BookName=?, AuthorName=?, Price=? WHERE id=?",[Bookname,Authorname,Price,Id],function(err, rows, fields){
      //       if(!!err){
      //           data["Books"] = "Error Updating data";
      //       }else{
      //           data["error"] = 0;
      //           data["Books"] = "Updated Book Successfully";
      //       }
      //       res.json(data);
      //   });
    }else{
      //   data["Books"] = "Please provide all required data (i.e : id, Bookname, Authorname, Price)";
      //   res.json(data);
    }
});

app.delete(endpoint + '/task',function(req,res){
    var id = req.body.id;
    var data = {
        "error":1,
        "tasks":""
    };
    if(!!id){
      //   connection.query("DELETE FROM book WHERE id=?",[Id],function(err, rows, fields){
      //       if(!!err){
      //           data["Books"] = "Error deleting data";
      //       }else{
      //           data["error"] = 0;
      //           data["Books"] = "Delete Book Successfully";
      //       }
      //       res.json(data);
      //   });
    }else{
      //   data["Books"] = "Please provide all required data (i.e : id )";
      //   res.json(data);
    }
});

app.get('/', function(req, res){
   res.send('Underlist - TODO App');
});

app.listen(AppPort, function () {
  console.log('Running server on port: ' + AppPort);
});
