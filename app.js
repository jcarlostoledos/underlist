//Developed by Juan Carlos Toledo
//Underlist v0.1

// Resources index:
// 1. POST    - User (register)     /register
// 2. POST    - User (login)        /login
// 3. GET     - Lista               /lista
// 4. POST    - Lista               /lista
// 5. PUT     - Lista               /lista/:id
// 6. DELETE  - Lista               /lista/:id
// 7. GET     - Task                /user/:user_id/task
// 8. GET     - Task (within list)  /user/:user_id/list/:list_id/task
// 9. POST    - Task                /task
// 10.PUT     - Task                /task/:id
// 11.DELETE  - Task                /task/:id
// 12.PUT     - Task (check)        /task/:id/done
// 13.PUT     - Task (uncheck)      /task/:id/undone

//Server response includes an "error" key with a boolean value, which
//represents if a request was successful (false being success)

//Server error response:
//    "error":    true
//    "message":  Error message
//    "debug":    MySQL connection error

//server configuration
var AppBuild =    "v0.1";
var AppPort =     8088; //port selected for testing purposes
var DBHost =      'localhost';
var DBUser =      'root';
var DBPassword =  '';
var DBName =      'underlist';

//routing namespace
var endpoint =    "/api/" + AppBuild;

var app =         require('express')();
var http =        require('http').Server(app);
var mysql =       require('mysql');
var bodyParser =  require("body-parser");

//DB connection
var connection = mysql.createConnection({
    host :     DBHost,
    user :     DBUser,
    password : DBPassword,
    database : DBName,
});

//App configuration
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

//1. Login endpoint - POST
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

//2. Register endpoint - POST
app.post(endpoint + '/register', function(req, res) {

   var username = req.body.username;
   var name =     req.body.name;

   var data = {
      "error": true
   };

   if(!!username && !!name) {

      //Check username using twitter standard
      // 1. Start of the line
      // 2. Match characters and symbols in the list, a-z, 0-9, underscore, hyphen
      // 3. Length at least 3 characters and maximum length of 15
      // 4. End of the line
      if(/^[a-z0-9_-]{3,15}$/.test(username)){

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

//3. Lists endpoint - GET
app.get(endpoint + '/user/:id/list/',function(req,res){

    var userId = req.params.id;
    var data = {
        "error": true
    };

    connection.query("SELECT * FROM Lists WHERE Lists.user_id = ?",
                     [userId],
                     function(err, rows, fields) {

        if(rows.length > 0){
            data.error = false;
            data.lists = rows;
            res.json(data);
        }
        else{
            data.message = 'No lists found';
            data.lists = [];
            res.json(data);
        }
    });
});

//4. Lists endpoint - POST
app.post(endpoint + '/list',function(req, res) {

    var title =       req.body.title;
    var description = req.body.description;
    var createdDate = req.body.createdDate;
    var userId =      req.body.userId;

    var data = {
      "error": true
    };

    //Validating required fields
    if(!!title && !!description && !!createdDate && !!userId) {
        connection.query(
                        "INSERT INTO Lists (Lists.title, Lists.description, Lists.created_date, Lists.user_id) VALUES(?, ?, ?, ?)",
                        [title, description, createdDate, userId],
                        function(err, result) {
            if(!!err) {
                data.message = "Error adding list " + title;
                data.debug = err;
                res.json(data);
            }
            else{
                data.error = false;
                data.message = "Added list " + result.insertId + " successfully";
                res.json(data);
            }
        });
    }
    else {
        data.message = "Please provide all required data";
        res.json(data);
    }
});

//5. Lists endpoint - PUT
app.put(endpoint + '/list/:id',function(req, res) {

    var id =            req.params.id;
    var title =         req.body.title;
    var description =   req.body.description;

    var data = {
      "error": true,
    };

    //Validating required fields
    if(!!id && !!title && !!description){
        connection.query("UPDATE Lists SET Lists.title=?, Lists.description=? WHERE Lists.id=?",
                         [title, description, id],
                         function(err, result){
            if(!!err) {
                data.message = "Error updating list " + id + " " + title + " " + description;
                data.debug = err;
                res.json(data);
            }
            else if(result.affectedRows === 0) {
                data.message = "Error updating list " + id + " " + title + " " + description + ", NO ROWS have been affected";
                res.json(data);
            }
            else{
                data.error = false;
                data.message = "Updated list " + id + " successfully";
                res.json(data);
            }
        });
    }
    else {
        data.message = "Please provide all required data";
        res.json(data);
    }
});

//6. Lists endpoint - DELETE
app.delete(endpoint + '/list/:id', function(req, res){
    var id = req.params.id;

    var data = {
        "error": true,
    };

    //Validating required fields
    if(!!id){
        connection.query("DELETE FROM Lists WHERE Lists.id=?",[id] ,function(err, rows) {
            if(!!err) {
                data.message = "Error deleting list " + id;
                data.debug = err;
                res.json(data);
            }
            else {
               data.error = false;
               data.message = "Deleted list " + id + " successfully";
               res.json(data);
            }
        });
    }
    else {
        data.message = "Please provide all required data (i.e : id )";
        res.json(data);
    }
});

//7. Tasks endpoint - GET
app.get(endpoint + '/user/:id/task/',function(req,res){

    var userId = req.params.id;
    var data = {
        "error": true
    };

    connection.query("SELECT * from Tasks, UserTasks WHERE UserTasks.user_id = ? AND UserTasks.task_id = Tasks.id",
                     [userId],
                     function(err, rows, fields) {

        if(rows.length > 0) {
            data.error = false;
            data.tasks = rows;
            res.json(data);
        }
        else {
            data.message = "No tasks found";
            data.tasks = [];
            res.json(data);
        }
    });
});

//8. Tasks endpoint with List filter - GET
app.get(endpoint + '/user/:id/list/:listId/task/',function(req,res){

    var userId = req.params.id;
    var listId = req.params.listId;

    var data = {
        "error": true
    };

    connection.query("SELECT * from Tasks, UserTasks WHERE UserTasks.user_id = ? AND UserTasks.task_id = Tasks.id AND Tasks.list_id = ?",
                     [userId, listId],
                     function(err, rows, fields) {

        if(rows.length > 0){
            data.error = false;
            data.tasks = rows;
            res.json(data);
        }
        else {
            data.message = "No tasks found";
            data.tasks = [];
            res.json(data);
        }
    });
});

//9. Tasks endpoint - POST
app.post(endpoint + '/task',function(req, res) {

    var title =       req.body.title;
    var description = req.body.description;
    var createdDate = req.body.createdDate;
    var dueDate =     req.body.dueDate;
    var userId =      req.body.userId;
    var listId =      req.body.listId;

    var data = {
      "error": true
    };

    //Validating required fields
    if(!!title && !!description && !!createdDate && !!dueDate && !!userId && !!listId) {
        connection.query(
                        "INSERT INTO Tasks (Tasks.title, Tasks.description, Tasks.created_date, Tasks.due_date, Tasks.list_id, Tasks.done) VALUES(?, ?, ?, ?, ?, 0)",
                        [title, description, createdDate, dueDate, listId],
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

//10. Tasks endpoint - PUT
app.put(endpoint + '/task/:id',function(req, res) {

    var id =            req.params.id;
    var title =         req.body.title;
    var description =   req.body.description;
    var dueDate =       req.body.dueDate;

    var data = {
      "error": true,
    };

    //Validating required fields
    if(!!id && !!title && !!description && !!dueDate){
        connection.query("UPDATE Tasks SET Tasks.title=?, Tasks.description=?, Tasks.due_date=? WHERE Tasks.id=?",
                         [title, description, dueDate, id],
                         function(err, result){
            if(!!err) {
                data.message = "Error updating task " + id + " " + title + " " + description + " " + dueDate;
                data.debug = err;
                res.json(data);
            }
            else if(result.affectedRows === 0) {
                data.message = "Error updating task " + id + " " + title + " " + description + " " + dueDate + ", NO ROWS have been affected";
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

//11. Tasks endpoint - DELETE
app.delete(endpoint + '/task/:id', function(req, res){
    var id = req.params.id;

    var data = {
        "error": true,
    };

    //Validating required fields
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

//12. Tasks activate endpoint - PUT
app.put(endpoint + '/task/:id/done', function(req, res) {

      var id = req.params.id;

      var data = {
          "error": true,
      };

      //Validating required fields
      if(!!id){
          connection.query("UPDATE Tasks SET Tasks.done=1 WHERE Tasks.id=?",
                           [id],
                           function(err, result){
              if(!!err) {
                  data.message = "Error updating task " + id;
                  data.debug = err;
                  res.json(data);
              }
              else if(result.affectedRows === 0) {
                  data.message = "Error updating task " + id + ", NO ROWS have been affected";
                  res.json(data);
              }
              else {
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

//13. Tasks deactivate endpoint - PUT
app.put(endpoint + '/task/:id/undone', function(req, res) {

      var id = req.params.id;

      var data = {
          "error": true,
      };

      //Validating required fields
      if(!!id){
          connection.query("UPDATE Tasks SET Tasks.done=0 WHERE Tasks.id=?",
                           [id],
                           function(err, result){
              if(!!err) {
                  data.message = "Error updating task " + id;
                  data.debug = err;
                  res.json(data);
              }
              else if(result.affectedRows === 0) {
                  data.message = "Error updating task " + id + ", NO ROWS have been affected";
                  res.json(data);
              }
              else {
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
  console.log('Running underlist('+ AppBuild +') on port: ' + AppPort);
});
