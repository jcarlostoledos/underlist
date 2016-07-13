<!doctype html>
<html class="no-js" lang="">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/pnotify.custom.min.css">

    <script src="js/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
  </head>

  <body>
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Underlist</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="./index.php">Ingresar</a>
            </li>
          </u>
        </div><!--/.navbar-collapse -->
      </div>
    </nav>

    <div class="container">    
      <div class="panel panel-default">
          <div class="panel-body">
            <h3 class=" form-title text-center">
              <img src="img/underlist-icon.png" height="100" width="100"><br>
              Underlist</h3>
            <form id="frm-register" class="login">
              <input id="inp-usernamereg" type="text" placeholder="Usuario" />
              <input id="inp-namereg" type="text" placeholder="Nombre" />
            </form>
          </div>
          <p class="text-center">
            <button id="btn-ok" type="button" class="btn btn-circle btn-lg" onclick="register(); return false"><i class="glyphicon glyphicon-ok"></i></button>
          </p>
      </div>
    </div>    

    <script src="js/jquery.min.js"></script>
    
    <script src="js/vendor/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/pnotify.custom.min.js"></script>
  </body>
</html>
