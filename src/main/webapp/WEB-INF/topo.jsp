<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="fr">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="icon" type="image/png" href="resources/favicon.png">
		<title>Escalade Communautaire</title>
		
		<link rel="stylesheet" href="webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
		<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link href="resources/css/topo.css" rel="stylesheet">
	</head>
	
	<body>
		<header>
    	<%@ include file="menu.jsp" %>
    </header>
    
    <main role="main" class="container">
    
    	<div class="offset-md-1 col-md-10">
	      	<div class="card">
	  				<h6 class="card-header ">Fiche du topo</h6>
	  				<div class="card-body col-md-12">
		  				<p><c:out value="${topo.titre}" /></p>
		  				<p><c:out value="${topo.auteur}" /></p>
		  				<p><c:out value="${topo.anneeEdition}" /></p>
		  				<p><c:out value="${topo.departement}" /></p>
		  				<p><c:out value="${topo.description}" /></p>
		  				<p><c:out value="${topo.utilisateur.prenom}" /></p>
		  				<p><c:out value="${topo.utilisateur.nom}" /></p>
		  				<p><c:out value="${topo.utilisateur.codePostal}" /></p>
		  				<p><c:out value="${topo.utilisateur.mail}" /></p>
		  				<div class="row">      			      			
		       			<table class="table table-striped">
								  <thead>
								    <tr>
								      <th scope="col">Bénéficiaire</th>
								      <th scope="col">Du</th>
								      <th scope="col">Au</th>
								    </tr>
								  </thead>
								  <tbody>
								  	<c:forEach items="${reservations}" var="reservation">		  	      					
									    <tr>
									      <td>
									      	<details>
													  <summary><c:out value="${reservation.utilisateur.prenom} ${reservation.utilisateur.nom}" /> (<c:out value="${reservation.utilisateur.codePostal}" />)</summary>
													  <p><c:out value="${reservation.utilisateur.mail}" /></p>
													</details>
												</td>
									      <td><c:out value="${reservation.dateDeb}" /></td>
									      <td><c:out value="${reservation.dateFin}" /></td>
									    </tr>
								    </c:forEach>							   
								  </tbody>
								</table>
							</div>
							
							<%@ include file="commentaires.jsp" %>
							
	  				</div>
	  			</div>
	  	</div>
	  	
    </main>
    
    <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
	  <script src="webjars/popper.js/1.11.1/dist/umd/popper.min.js"></script>
	  <script src="webjars/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>

  </body>
</html>