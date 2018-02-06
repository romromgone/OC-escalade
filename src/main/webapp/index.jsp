<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="fr">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" type="image/png" href="resources/favicon.png">
    <title>Escalade Communautaire</title>

    <link rel="stylesheet" href="webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link href="resources/css/accueil.css" rel="stylesheet">
  </head>

  <body>
    <%@ include file="WEB-INF/menu.jsp" %>
    
    <main role="main" class="container">
      <div>
      	<img src="resources/bandeau.jpg" alt="bandeau" >
        <h1>Bienvenue aux grimpeurs !</h1>
        <p class="lead">Site communautaire autour de l'escalade.</p>
      </div>
    </main>
    
    <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
	  <script src="webjars/popper.js/1.11.1/dist/umd/popper.min.js"></script>
	  <script src="webjars/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
	  <script src="webjars/jquery-validation/1.17.0/jquery.validate.min.js"></script>
	  <script src="webjars/jquery-validation/1.17.0/additional-methods.min.js"></script>
    
  </body>
</html>