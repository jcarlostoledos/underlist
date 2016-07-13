function login() {

  var username = document.getElementById("inp-username").value;

  $.ajax({
    type: "POST",
    url: "http://192.168.11.17:8088/api/v0.1/login",
    data: {"username" : username},
    dataType: "json",
    success: function (data, status, jqXHR) {
      if(data.error == false){
        var datos = data.user[0];
        document.getElementById("frm-login").reset();
        new PNotify({
            title: 'Bienvenido',
            text: datos.name,
            type: 'success'
        });     
      }
      else{
        document.getElementById("frm-login").reset();
        new PNotify({
            text: "El usuario "+ username+" no existe.",
            type: 'error'
        });
      }
    }
  });
}

function register() {

  var usernamereg = document.getElementById("inp-usernamereg").value;
  var namereg = document.getElementById("inp-namereg").value;

  $.ajax({
    type: "POST",
    url: "http://192.168.11.17:8088/api/v0.1/register",
    data: {"username" : usernamereg, "name" : namereg},
    dataType: "json",
    success: function (data, status, jqXHR) {
      console.log(data);
      if(data.error == false){
        document.getElementById("frm-register").reset();
        new PNotify({
            text: data.message,
            type: 'success'
        });
      }
      else{
        document.getElementById("frm-register").reset();
        new PNotify({
            text: data.message,
            type: 'error'
        });
      }
    }
  });
}

function getList() {

  $.ajax({
    type: "GET",
    url: "http://192.168.11.17:8088/api/v0.1/user/1/list",
    data: {},
    dataType: "json",
    success: function (data, status, jqXHR) {
      var datos = data.lists;
      $.each(datos, function (ind, elem) {
        $("#lists").append("<li class='list-group-item' data-toggle='modal' data-target='#myModal'>"
          +"<strong>Titulo: </strong>"+datos[ind].title+
          "<br><strong>Descripción: </strong>"+datos[ind].description+
          "</li>"); 
        console.log(datos[ind].title); 
      }); 
      
      
    }
  });
}

function addList() {

  var titlelist = document.getElementById("inp-title").value;
  var descriplist = document.getElementById("inp-descrip").value;

  $.ajax({
    type: "POST",
    url: "http://192.168.11.17:8088/api/v0.1/register",
    data: {"title" : titlelist, "description" : descriplist, "createdDate" : createdate, "userId" : userid},
    dataType: "json",
    success: function (data, status, jqXHR) {
      console.log(data);
    }
  });
}