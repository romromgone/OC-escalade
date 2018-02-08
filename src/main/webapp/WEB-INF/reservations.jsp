<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="fr">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="icon" type="image/png" href="resources/favicon.png">
		<title>Escalade Communautaire</title>
		
		<link rel="stylesheet" href="webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
		<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link href="resources/css/reservations.css" rel="stylesheet">
	</head>
	
	<body>
		<header>
    	<%@ include file="menu.jsp" %>
    </header>
    
    <main role="main" class="container-fluid">
    	<div class="row">
    	
	    	<div class="col-md-1">
		    	<ul class="nav nav-pills flex-column">
		  			<li class="nav-item">
		    			<a class="nav-link text-info" href="infospersos">Infos persos</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link text-info" href="mestopos">Mes topos</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link bg-info text-white" href="reservations">Réservations</a>
		 				</li>  		
					</ul>
				</div>
			
	    	<div class="offset-md-1 col-md-9">
	      	<div class="card">
	  				<div class="card-header ">Mes prêts/réservations</div>
	  				<div class="card-body col-md-12">
	  					<h5 class="card-title">Prêts de mes topos</h5>
	  					<form method="post" action="reservations" id="form" name="form" novalidate>
		  					<div class="form-row">
		  						<div class="col-md-3">
		  							<label for="inputTopo">Topo</label>
	  							</div>
	  							<div class="col-md-3">
		  							<label for="inputMail">Email</label>
		  						</div>
		  						<div class="col-md-2">
		  							<label for="inputDateDeb">Du</label>
		  						</div>
		  						<div class="col-md-2">
		  							<label for="inputDateFin">Au</label>
		  						</div>
		  					</div>
		  					<div class="form-row">
		  						<div class="col-md-3"> 			
			  						<select class="form-control mr-md-2" id="inputTopo" name="inputTopo">
										  <option selected></option>
										  <c:forEach items="${topos}" var="topo">
										  	<option><c:out value="${topo.titre}" /></option>
										  </c:forEach>							  
										</select>
										<small class="form-text">${reservationsForm.erreurs['inputTopo']}</small>
									</div>
									<div class="col-md-3"> 
										<input type="text" class="form-control mr-md-2" id="inputMail" name="inputMail" placeholder="Email du demandeur de prêt">
										<small class="form-text">${reservationsForm.erreurs['inputMail']}</small>
									</div>
									<div class="col-md-2">
										<input type="date" class="form-control mr-md-2" id="inputDateDeb" name="inputDateDeb">
										<small class="form-text">${reservationsForm.erreurs['inputDateDeb']}</small>
									</div>
									<div class="col-md-2">
										<input type="date" class="form-control mr-md-2" id="inputDateFin" name="inputDateFin">
										<small class="form-text">${reservationsForm.erreurs['inputDateFin']}</small>
									</div>
									<div class="col-md-2">
										<button type="submit" class="btn btn-info"><span class="fas fa-plus-circle" aria-hidden="true"></span> Ajouter</button>
										<p class="${empty reservationsForm.erreurs ? 'succes' : 'erreur'}">${reservationsForm.resultat}</p>
									</div>
								</div>
							</form>
							<div class="row">      			      			
		       			<table class="table table-striped">
								  <thead>
								    <tr>
								      <th scope="col">Topo</th>
								      <th scope="col">Bénéficiaire</th>
								      <th scope="col">Du</th>
								      <th scope="col">Au</th>
								      <th scope="col"></th>
								    </tr>
								  </thead>
								  <tbody>
								  	<c:forEach items="${prets}" var="pret">		  	      					
									    <tr>
									      <td><a href="topo?id=${pret.topo.id}"><c:out value="${pret.topo.titre}" /></a></td>
									      <td>
									      	<details>
													  <summary><c:out value="${pret.utilisateur.prenom} ${pret.utilisateur.nom}" /> (<c:out value="${pret.utilisateur.codePostal}" />)</summary>
													  <p><c:out value="${pret.utilisateur.mail}" /></p>
													</details>
												</td>
									      <td><c:out value="${pret.dateDeb}" /></td>
									      <td><c:out value="${pret.dateFin}" /></td>
									      <td>
									      	<form method="post" action="reservations" id="formSuppression" name="formSuppression" novalidate>	 
									      		<input type="hidden" id="resaDateDeb" name="resaDateDeb" value="${pret.dateDeb}">
									      		<input type="hidden" id="resaIdUser" name="resaIdUser" value="${pret.idUser}">
									      		<input type="hidden" id="resaIdTopo" name="resaIdTopo" value="${pret.idTopo}">
									      		<button type="submit" class="btn btn-info" name="suppr" value="suppr"><span class="fas fa-trash-alt" aria-hidden="true"></span></button>
     									    </form>
									      </td>
									    </tr>
								    </c:forEach>							   
								  </tbody>
								</table>
							</div>
							
							<h5 class="card-title" >Mes réservations</h5>
							<div class="row">      			      			
		       			<table class="table table-striped">
								  <thead>
								    <tr>
								      <th scope="col">Topo</th>
								      <th scope="col">Propriétaire</th>
								      <th scope="col">Du</th>
								      <th scope="col">Au</th>
								    </tr>
								  </thead>
								  <tbody>
								  	<c:forEach items="${reservations}" var="reservation">		  	      					
									    <tr>
									      <td><a href="topo?id=${reservation.topo.id}"><c:out value="${reservation.topo.titre}" /></a></td>
									      <td>
									      	<details>
													  <summary><c:out value="${reservation.topo.utilisateur.prenom} ${reservation.topo.utilisateur.nom}" /> (<c:out value="${reservation.topo.utilisateur.codePostal}" />)</summary>
													  <p><c:out value="${reservation.topo.utilisateur.mail}" /></p>
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
        </div>
        
      </div>
    </main>
    
    <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
	  <script src="webjars/popper.js/1.11.1/dist/umd/popper.min.js"></script>
	  <script src="webjars/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
 
  </body>
</html>