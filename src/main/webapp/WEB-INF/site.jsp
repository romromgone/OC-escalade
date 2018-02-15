<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="fr">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="icon" type="image/png" href="resources/favicon.png">
		<title>Escalade Communautaire</title>
		
		<link rel="stylesheet" href="webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
		<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
    <link href="resources/css/site.css" rel="stylesheet">
	</head>
	
	<body>
		<header>
    	<%@ include file="menu.jsp" %>
    </header>
    
    <main role="main" class="container">
    
    	<div class="offset-md-1 col-md-10">
	      	<div class="card" id="card" name="card">
	  				<h6 class="card-header">Fiche du site</h6>
	  				<div class="card-body col-md-12">
	  				
	  					<h6 class="card-title">${site.nom}</h6>
	  					<form id="form1">
	  						<div class="row">
		  						<div class="col-md-6">
		  							<div class="form-group">
					  					<label for="commune">Commune</label>
				    					<input type="text" readonly class="form-control-plaintext" id="commune" name="commune" value="${site.commune} (${site.codePostal})">	    					
				    				</div>
				    				<div class="form-group">
					    				<label for="altitude">Altitude</label>
					    				<input type="text" readonly class="form-control-plaintext" id="altitude" name="altitude" value="${site.altitude}">	    					
										</div>
				    				<div class="form-group">
					    				<label for="orientation">Orientation</label>
					    				<input type="text" readonly class="form-control-plaintext" id="orientation" name="orientation" value="${site.orientation}">	    					
										</div>
										<div class="form-group">
					    				<label for="rocher">Rocher</label>
					    				<input type="text" readonly class="form-control-plaintext" id="rocher" name="rocher" value="${site.rocher}">	    					
										</div>
									</div>
									<div class="col-md-6">
										<p>${site.description}</p>
									</div>
								</div>							
		    				<label for="acces">Accès</label>
		    				<p>${site.acces}</p>    														
							</form>

		  				<p class="lead">
		  					Ce site est composé des secteurs suivants : 
							</p>															
							
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