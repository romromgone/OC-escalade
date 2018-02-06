<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="fr">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="icon" type="image/png" href="resources/favicon.png">
		<title>Escalade Communautaire</title>
		
		<link rel="stylesheet" href="webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
    <link href="resources/css/mestopos.css" rel="stylesheet">
	</head>
	
	<body>
		<header>
    	<%@ include file="menu.jsp" %>
    </header>
    
    <main role="main" class="container">
    	<div class="row">
    	
	    	<div class="col-md-2">
		    	<ul class="nav nav-pills flex-column">
		  			<li class="nav-item">
		    			<a class="nav-link text-info" href="moncompte">Infos persos</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link bg-info text-white" href="mestopos">Mes topos</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link text-info" href="#">R�servations</a>
		 				</li>  		
					</ul>
				</div>
			
	    	<div class="offset-md-1 col-md-7">
	      	<div class="card">
	  				<div class="card-header ">Mes topos</div>
	  				<div class="card-body col-md-12">
	  					<div class="row">
	  						<div class="col-md-2">
									<button class="btn btn-info btn-sm" id="btnAdd" data-toggle="modal" data-target="#modal">
										<span class="fa fa-plus-circle fa-lg" aria-hidden="true"></span> 
										Ajouter
									</button>
								</div>
								<div class="col-md-5">
									<p class="${empty mesToposForm.erreurs ? 'succes' : 'erreur'}">${mesToposForm.resultat}</p>	
								</div>
							</div>
							<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="titreModal" aria-hidden="true">
							  <div class="modal-dialog modal-dialog-centered" role="document">
							    <div class="modal-content">						    
							      <div class="modal-header">
							        <h5 class="modal-title" id="titreModal">Ajout d'un topo</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-body">
							      	<form method="post" action="mestopos" id="form" name="form" novalidate> 
							  				<div class="form-group row">
							    				<label class="col-md-4 col-form-label" for="titre">Titre</label>
							    				<div class="col-md-8">
							    					<input type="text" class="form-control" id="titre" name="titre" value="<c:out value="${topo.titre}"/>">
							    					<small class="form-text">${mesToposForm.erreurs['titre']}</small>
							    				</div>
							  				</div>
							  				<div class="form-group row">
							    				<label class="col-md-4 col-form-label" for="auteur">Auteur</label>
							    				<div class="col-md-8">
							    					<input type="text" class="form-control" id="auteur" name="auteur" value="<c:out value="${topo.auteur}"/>">
							    					<small class="form-text">${mesToposForm.erreurs['auteur']}</small>
							    				</div>
							  				</div>
							  				<div class="form-group row">
							    				<label class="col-md-4 col-form-label" for="anneeEdition">Ann�e d'�dition</label>
							    				<div class="col-md-8">
							    					<input type="text" class="form-control" id="anneeEdition" name="anneeEdition" maxlength="4" value="<c:out value="${topo.anneeEdition}"/>">
							    					<small class="form-text">${mesToposForm.erreurs['anneeEdition']}</small>
							    				</div>
							  				</div>
							  				<div class="form-group row">
							    				<label class="col-md-4 col-form-label" for="departement">D�partement</label>
							    				<div class="col-md-8">
							    					<input type="text" class="form-control" id="departement" name="departement" maxlength="3" value="<c:out value="${topo.departement}"/>">
							    					<small class="form-text">${mesToposForm.erreurs['departement']}</small>
							    				</div>
							  				</div>
							  				<div class="form-group row">
							    				<label class="col-md-4 col-form-label" for="description">Description</label>
							    				<div class="col-md-8">
								    				<textarea class="form-control" id="description" name="description" rows="2"></textarea>
								    				<small class="form-text">${mesToposForm.erreurs['description']}</small>
							    				</div>
							  				</div>
							  				<button type="submit" class="col-md-2 offset-md-10 btn btn-info" id="valider">Valider</button>	
							  				<p class="${empty mesToposForm.erreurs ? 'succes' : 'erreur'}">${mesToposForm.resultat}</p>						  				
											</form>
							      </div>						      					      
							    </div>
							  </div>
							</div>	
	       			
	      	  </div>
	        </div>
	      </div>
      </div>
    </main>
    
    <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
	  <script src="webjars/popper.js/1.11.1/dist/umd/popper.min.js"></script>
	  <script src="webjars/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <script src="https://use.fontawesome.com/c448120b09.js"></script>
    <script>
			$(btnUser).mouseover(function() {
			  $(this).dropdown('toogle');
			});
		</script>
    
  </body>
</html>