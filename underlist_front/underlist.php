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

    <!-- Custom Fonts -->
    <link href="css/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

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
          <ul class="nav navbar-nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-tasks fa-2x" aria-hidden="true"></i> <span class="caret"></span></a>
                <ul class="dropdown-menu">
                  <li><a data-toggle="tab" href="#getlist">Mis listas</a></li>
                  <li><a data-toggle="tab" href="#addlist">Agregar</a></li>
                </ul>
              </li>
            </ul>
          <ul class="nav navbar-nav navbar-right">
            <li>
               <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Usuario<span class="caret"></span></a>
               <ul class="dropdown-menu">
                  <li><a href="#">Salir</a></li>
                </ul>
            </li>
          </ul>
        </div><!--/.navbar-collapse -->
      </div>
    </nav>

    <div class="container">
      

      

      <!-- Modal -->
      <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">
        
          <!-- Modal content-->
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal">&times;</button>
              <h4 class="modal-title">Nombre</h4>
            </div>
            <div class="modal-body">
              <form role="form">
                <div class="form-group">
                  <label for="disabledTextInput">Nombre</label>
                  <input type="text"  class="form-control" 
                         placeholder="Nombre de la lista">
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" data-dismiss="modal">Cambiar</button>
            </div>
          </div>
          
        </div>
      </div>

      <div class="tab tab-content">
          <div id="getlist" class="tab-pane fade in active">
            <div class="container">
              <ul id="lists" class="list-group"></ul>
            </div>
            <button type="button" class="btn btn-primary" onclick="getList();return false">lista</button>
          </div>

          <div id="addlist" class="tab-pane fade">
            <h2>hola</h2>
            <form action="" class="form">
                <div class="form-group">
                  <label>Título<i class="text-danger">*</i></label>
                  <input type="text" class="form-control" id="inp-title" placeholder="Introduce el título">
                </div>
                <div class="form-group">
                  <label>Descripción<i class="text-danger">*</i></label>
                  <textarea class="form-control" rows="6" id="inp-descrip" placeholder="Introduce una descripción"></textarea>
                </div>                <div class="form-group">
                  <button type="submit" class="btn btn-action" id="btn-addlist" onclick="addList(); return false">Agregar</button>
                </div>
              </form>
          </div>

          <div id="3" class="tab-pane fade">
          </div>

          <div id="4" class="tab-pane fade" style="padding-bottom: 300px;">
          </div>
        </div>
    </div>    

    <script src="js/jquery.min.js"></script>
    
    <script src="js/vendor/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/pnotify.custom.min.js"></script>
  </body>
</html>
