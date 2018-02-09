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
	  				<h6 class="card-header">Fiche du topo</h6>
	  				<div class="card-body col-md-12">
	  				
	  					<h6 class="card-title">${topo.titre}</h6>
	  					<form id="form1">
	  						<div class="row">
		  						<div class="col-md-6">
		  							<div class="form-group">
					  					<label for="dep">Département</label>
				    					<input type="text" readonly class="form-control-plaintext" id="dep" name="dep" value="${topo.departement}">	    					
				    				</div>
				    				<div class="form-group">
					    				<label for="auteur">Auteur(s)</label>
					    				<input type="text" readonly class="form-control-plaintext" id="auteur" name="auteur" value="${topo.auteur}">	    					
										</div>
				    				<div class="form-group">
					    				<label for="anneeEdition">Année d'édition</label>
					    				<input type="text" readonly class="form-control-plaintext" id="anneeEdition" name="anneeEdition" value="${topo.anneeEdition}">	    					
										</div>
									</div>
									<div class="col-md-6">
										<p>${topo.description}</p>
									</div>
								</div>
							</form>
							<form method="post" action="topo" id="formCouvre" name="formCouvre" novalidate>				
		  					<div class="form-row">
		  						<div class="col-auto">
							  		<p class="lead">Ce topo couvre les sites suivant :</p>
							  	</div>
							  	<div class="col-auto">
										<button class="btn btn-info btn-sm" id="btnAdd" data-toggle="modal" data-target="#modal">
											<span class="fas fa-plus-circle" aria-hidden="true"></span>
										</button>
									</div>
								</div>
							</form>
							
							<br />
		  				<p class="lead">
		  					Pour faire une demande de réservation, envoyer un mail à <mark>${topo.utilisateur.prenom} ${topo.utilisateur.nom} (${topo.utilisateur.codePostal}) : ${topo.utilisateur.mail}</mark>
								<br />(Les dates doivent prendre en compte les éventuels délais d'envois/de retours)
								<br />Réservations en cours et/ou à venir :
							</p>
						
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
							
	  				</div>
	  			</div>
	  			
	  			<%@ include file="commentairesTopo.jsp" %>
	  			
	  	</div>
	  	
    </main>
    
    <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
	  <script src="webjars/popper.js/1.11.1/dist/umd/popper.min.js"></script>
	  <script src="webjars/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>

  </body>
</html>