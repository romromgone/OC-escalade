<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="fr">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="icon" type="image/png" href="resources/favicon.png">
		<title>Escalade Communautaire</title>
		
		<link rel="stylesheet" href="webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
    <link href="resources/css/infospersos.css" rel="stylesheet">
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
		    			<a class="nav-link bg-info text-white" href="moncompte">Infos persos</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link text-info" href="mestopos">Mes topos</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link text-info" href="#">Réservations</a>
		 				</li>  		
					</ul>
				</div>
			
	    	<div class="offset-md-1 col-md-7">
	      	<div class="card">
	  				<div class="card-header ">Infos Persos</div>
	  				<div class="card-body col-md-12">
		  				<form>
			  				<div class="form-group row">
			    				<label class="col-md-4 col-form-label" for="prenom">Prénom</label>
			    				<div class="col-md-8">
			    					<input type="text" readonly class="form-control-plaintext" id="prenom" name="prenom" value="${sessionScope.sessionUtilisateur.prenom}">			
			    				</div>
			  				</div>
			  				<div class="form-group row">
			    				<label class="col-md-4 col-form-label" for="nom">Nom</label>
			    				<div class="col-md-8">
			    					<input type="text" readonly class="form-control-plaintext" id="nom" name="nom" value="${sessionScope.sessionUtilisateur.nom}">		    					
			    				</div>
			  				</div>
			  				<div class="form-group row">
			    				<label class="col-md-4 col-form-label" for="mail">Email</label>
			    				<div class="col-md-8">
			    					<input type="text" readonly class="form-control-plaintext" id="mail" name="mail" value="${sessionScope.sessionUtilisateur.mail}">	    					
			    				</div>
			  				</div>
			  				<div class="form-group row">
			    				<label class="col-md-4 col-form-label" for="cp">Code postal</label>
			    				<div class="col-md-8">
			    					<input type="text" readonly class="form-control-plaintext" id="cp" name="cp" value="${sessionScope.sessionUtilisateur.codePostal}">	    					
			    				</div>
			  				</div>		  			
							</form>
	       			
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