//server configuration
var AppBuild =    "v0.1";
var AppPort =     8088;
var DBHost =      'localhost';
var DBUser =      'root';
var DBPassword =  '';
var DBName =      'underlist';

//routing namespace
var endpoint =    "api/" + AppBuild + "/";
endpoint = "";

var app =         require('express')();
var http =        require('http').Server(app);
var mysql =       require('mysql');
var bodyParser =  require("body-parser");

//DB configuration
var connection = mysql.createConnection({
    host :     DBHost,
    user :     DBUser,
    password : DBPassword,
    database : DBName,
});

//App configuration
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

//Login endpoint - POST
app.post(endpoint + '/login', function(req, res){

   var username = req.body.username;

   var data = {};

   connection.query("SELECT * FROM Users WHERE Users.username = ?", [username],function(err, rows, fields){

      if(rows.length > 0) {
         data.error =   false;
         data.user =    rows;
         data.message = "Success";
         res.json(data);
      }
      else {

         data.error =   true;
         data.message = "That username is not valid";
         res.json(data);
      }
   });
});

//Register endpoint - POST
app.post(endpoint + '/register', function(req, res) {

   var username = req.body.username;
   var name =     req.body.name;

   var data = {
      "error": true
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
                     data.debug =   err;
                     res.json(data);
                  }
                  else {
                     data.error =   false;
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
app.get(endpoint + '/user/:id/task/',function(req,res){

    var userId = req.params.id;
    var data = {
        "error": true
    };

    connection.query("SELECT * from Tasks, UserTasks WHERE UserTasks.user_id = ? AND UserTasks.task_id = Tasks.id",
                     [userId],
                     function(err, rows, fields) {

        if(rows.length > 0){
            data.error = false;
            data.tasks = rows;
            res.json(data);
        }else{
            data.tasks = 'No tasks found';
            res.json(data);
        }
    });
});

//Tasks endpoint - POST
app.post(endpoint + '/task',function(req, res) {

    var title =       req.body.title;
    var description = req.body.description;
    var createdDate = req.body.createdDate;
    var dueDate =     req.body.dueDate;
    var userId =      req.body.userId;

    var data = {
      "error": true
    };

    if(!!title && !!description && !!createdDate && !!dueDate && !!userId) {
        connection.query(
                        "INSERT INTO Tasks (Tasks.title, Tasks.description, Tasks.created_date, Tasks.due_date, Tasks.done) VALUES(?, ?, ?, ?, 0)",
                        [title, description, createdDate, dueDate, userId],
                        function(err, result) {

             connection.query(
                           "INSERT INTO UserTasks (UserTasks.user_id, UserTasks.task_id) VALUES(?, ?)",
                           [userId, result.insertId],
                           function(err, result) {
                              if(!!err) {
                                  data.message = "Error adding user task relation " + title;
                                  data.debug = err;
                                  res.json(data);
                              }
                              else{
                                  data.error = false;
                                  data.message = "Task " + title + " added succesfully";
                                  res.json(data);
                              }
                           });
            if(!!err) {
                data.message = "Error adding task " + title;
                data.debug = err;
                res.json(data);
            }
        });
    }
    else {
        data.message = "Please provide all required data";
        res.json(data);
    }
});

//Tasks endpoint - PUT
app.put(endpoint + '/task/:id',function(req, res) {

    var id =            req.params.id;
    var title =         req.body.title;
    var description =   req.body.description;
    var dueDate =       req.body.dueDate;

    var data = {
      "error": true,
    };

    if(!!id && !!title && !!description && !!dueDate){
        connection.query("UPDATE Tasks SET Tasks.title=?, Tasks.description=?, Tasks.due_date=? WHERE Tasks.id=?",
                         [title, description, dueDate, id],
                         function(err, result){
            if(!!err) {
                data.message = "Error updating task " + id + " " + title + " " + description + " " + dueDate;
                data.debug = err;
                res.json(data);
            }
            else{
                data.error = false;
                data.message = "Updated task " + id + " successfully";
                res.json(data);
            }
        });
    }
    else {
        data.message = "Please provide all required data";
        res.json(data);
    }
});

//Tasks endpoint - DELETE
app.delete(endpoint + '/task/:id', function(req, res){
    var id = req.params.id;

    var data = {
        "error": true,
    };

    if(!!id){
        connection.query("DELETE FROM UserTasks WHERE UserTasks.id=?",[id] ,function(err, rows) {
            if(!!err) {
                data.message = "Error deleting task " + id;
                data.debug = err;
                res.json(data);
            }
            else {
               connection.query("DELETE FROM Tasks WHERE Tasks.id=?",[id] ,function(err, rows) {
                   if(!!err) {
                       data.message = "Error deleting task " + id;
                       data.debug = err;
                       res.json(data);
                   }
                   else {
                       data.error = false;
                       data.message = "Deleted task " + id + " successfully";
                       res.json(data);
                   }
               });
            }
        });
    }
    else {
        data.message = "Please provide all required data (i.e : id )";
        res.json(data);
    }
});

//Tasks activate endpoint - PUT
app.put(endpoint + '/task/:id/done', function(req, res) {

      var id = req.params.id;

      var data = {
          "error": true,
      };

      if(!!id){
          connection.query("UPDATE Tasks SET Tasks.done=1 WHERE Tasks.id=?",
                           [id],
                           function(err, results){
              if(!!err) {
                  data.message = "Error updating task " + id;
                  data.debug = err;
                  res.json(data);
              }
              else{
                  data.error = false;
                  data.message = "Updated task " + id + " successfully";
                  res.json(data);
              }
          });
      }
      else {
          data.message = "Please provide all required data";
          res.json(data);
      }
});

//Tasks deactivate endpoint - PUT
app.put(endpoint + '/task/:id/undone', function(req, res) {

      var id = req.params.id;

      var data = {
          "error": true,
      };

      if(!!id){
          connection.query("UPDATE Tasks SET Tasks.done=0 WHERE Tasks.id=?",
                           [id],
                           function(err, results){
              if(!!err) {
                  data.message = "Error updating task " + id;
                  data.debug = err;
                  res.json(data);
              }
              else{
                  data.error = false;
                  data.message = "Updated task " + id + " successfully";
                  res.json(data);
              }
          });
      }
      else {
          data.message = "Please provide all required data";
          res.json(data);
      }
});

app.get('/', function(req, res){
   res.send('Underlist - TODO App');
});

app.listen(AppPort, function () {
  console.log('Running server on port: ' + AppPort);
});
