<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="fr">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="icon" type="image/png" href="resources/favicon.png">
		<title>Escalade Communautaire</title>
		
		<link rel="stylesheet" href="webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
		<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link href="resources/css/topos.css" rel="stylesheet">
	</head>
	
	<body>
		<header>
    	<%@ include file="menu.jsp" %>
    </header>
    
    <main role="main" class="container-fluid">
    	  			
	    	<div class="offset-md-1 col-md-10">
	      	<div class="card">
	  				<h6 class="card-header ">Les topos de la communauté</h6>
	  				<div class="card-body col-md-12">
	       			<div class="row">      			      			
		       			<table class="table table-striped">
								  <thead>
								    <tr>
								      <th scope="col">Dép.</th>
								      <th scope="col">Titre</th>
								      <th scope="col">Auteur</th>
								      <th scope="col">Année</th>
								      <th scope="col">Description</th>
								      <th scope="col">Propriétaire</th>
								    </tr>
								  </thead>
								  <tbody>
								  	<c:forEach items="${topos}" var="topo">			       					
									    <tr>
									      <th scope="row"><c:out value="${topo.departement}" /></th>
									      <td><a href="topo?id=${topo.id}"><c:out value="${topo.titre}" /></a></td>
									      <td><c:out value="${topo.auteur}" /></td>
									      <td><c:out value="${topo.anneeEdition}" /></td>
									      <td>
									      	<details>
													  <summary>Voir la description</summary>
													  <p><c:out value="${topo.description}" /></p>
													</details>									      	
									      </td>
									      <td><c:out value="${topo.utilisateur.prenom} ${topo.utilisateur.nom}" /></td>
									    </tr>
								    </c:forEach>							   
								  </tbody>
								</table>
							</div>
	       			
	      	  </div>
	        </div>
	      </div>
    </main>
    
    <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
	  <script src="webjars/popper.js/1.11.1/dist/umd/popper.min.js"></script>
	  <script src="webjars/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>

  </body>
</html>