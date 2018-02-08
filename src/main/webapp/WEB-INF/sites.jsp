<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="fr">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="icon" type="image/png" href="resources/favicon.png">
		<title>Escalade Communautaire</title>
		
		<link rel="stylesheet" href="webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
		<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link href="resources/css/sites.css" rel="stylesheet">
	</head>
	
	<body>
		<header>
    	<%@ include file="menu.jsp" %>
    </header>
    
    <main role="main" class="container-fluid">
			
	   	<div class="offset-md-1 col-md-10">
	     	<div class="card">
	 				<div class="card-header ">Les sites référencés par la communauté</div>
	 				<div class="card-body col-md-12">
	 					<div class="row">
	 						<div class="col-md-2">
								<button class="btn btn-info btn-sm" id="btnAdd" data-toggle="modal" data-target="#modal">
									<span class="fas fa-plus-circle" aria-hidden="true"></span> 
									Ajouter un site
								</button>
							</div>
							<div class="col-md-5">
								<p class="${empty sitesForm.erreurs ? 'succes' : 'erreur'}">${sitesForm.resultat}</p>	
							</div>
						</div>
						<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="titreModal" aria-hidden="true">
						  <div class="modal-dialog modal-dialog-centered" role="document">
						    <div class="modal-content">						    
						      <div class="modal-header">
						        <h5 class="modal-title" id="titreModal">Ajout d'un site</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          <span aria-hidden="true">&times;</span>
						        </button>
						      </div>
						      <div class="modal-body">
						      	<form method="post" action="sites" id="form" name="form" novalidate> 
						  				<div class="form-group row">
						    				<label class="col-md-4 col-form-label" for="nom">Nom du site*</label>
						    				<div class="col-md-8">
						    					<input type="text" class="form-control" id="nom" name="nom" value="<c:out value="${site.nom}"/>">
						    					<small class="form-text">${sitesForm.erreurs['nom']}</small>
						    				</div>
						  				</div>
						  				<div class="form-group row">
						    				<label class="col-md-4 col-form-label" for="commune">Commune*</label>
						    				<div class="col-md-8">
						    					<input type="text" class="form-control" id="commune" name="commune" value="<c:out value="${site.commune}"/>">
						    					<small class="form-text">${sitesForm.erreurs['commune']}</small>
						    				</div>
						  				</div>
						  				<div class="form-group row">
						    				<label class="col-md-4 col-form-label" for="codePostal">Code postal*</label>
						    				<div class="col-md-8">
						    					<input type="text" class="form-control" id="codePostal" name="codePostal" maxlength="5" value="<c:out value="${site.codePostal}"/>">
						    					<small class="form-text">${sitesForm.erreurs['codePostal']}</small>
						    				</div>
						  				</div>
						  				<div class="form-group row">
						    				<label class="col-md-4 col-form-label" for="altitude">Altitude</label>
						    				<div class="col-md-8">
						    					<input type="text" class="form-control" id="altitude" name="altitude" maxlength="4" placeholder="1458" value="<c:out value="${site.altitude}"/>">
						    					<small class="form-text">${sitesForm.erreurs['altitude']}</small>
						    				</div>
						  				</div>
						  				<div class="form-group row">
						    				<label class="col-md-4 col-form-label" for="orientation">Orientation</label>
						    				<div class="col-md-8">
							    				<input  type="text" class="form-control" id="orientation" name="orientation" maxlength="8" placeholder="N/S/O/E/NO/SE/Toutes..." value="<c:out value="${site.orientation}"/>">
						    				</div>
						  				</div>
						  				<div class="form-group row">
						    				<label class="col-md-4 col-form-label" for="rocher">Rocher</label>
						    				<div class="col-md-8">
							    				<input  type="text" class="form-control" id="rocher" name="rocher" placeholder="Calcaire, granite..." value="<c:out value="${site.rocher}"/>">
						    				</div>
						  				</div>
						  				<div class="form-group row">
						    				<label class="col-md-4 col-form-label" for="acces">Accès au site</label>
						    				<div class="col-md-8">
													<textarea class="form-control" id="acces" name="acces" rows="2" ><c:out value="${site.acces}"/></textarea>							    				
						    				</div>
						  				</div>
						  				<div class="form-group row">
						    				<label class="col-md-4 col-form-label" for="description">Description</label>
						    				<div class="col-md-8">
													<textarea class="form-control" id="description" name="description" rows="2" ><c:out value="${site.description}"/></textarea>							    				
						    				</div>
						  				</div>
						  				<button type="submit" class="col-md-2 offset-md-10 btn btn-info" id="valider">Valider</button>	
						  				<p class="${empty sitesForm.erreurs ? 'succes' : 'erreur'}">${sitesForm.resultat}</p>						  				
										</form>
						      </div>						      					      
						    </div>
						  </div>
						</div>	
						<p></p>
	      			<div class="row">      			      			
	       			<table class="table table-striped">
							  <thead>
							    <tr>
							      <th scope="col">CP</th>
							      <th scope="col">Nom</th>
							      <th scope="col">Commune</th>
							      <th scope="col">Voies du</th>
							      <th scope="col">au</th>
							      <th scope="col">Description</th>
							    </tr>
							  </thead>
							  <tbody>
							  	<c:forEach items="${sites}" var="site">			       					
								    <tr>
								      <th scope="row"><c:out value="${site.codePostal}" /></th>
								      <td><a href="site?id=${site.id}"><c:out value="${site.nom}" /></a></td>
								      <td><c:out value="${site.commune}" /></td>
								      <td><c:out value="" /></td>
								      <td><c:out value="" /></td>
								      <td>
								      	<details>
												  <summary>Voir la description</summary>
												  <p><c:out value="${site.description}" /></p>
												</details>									      	
								      </td>
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