<link href="resources/css/menu.css" rel="stylesheet">
    
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
  <div class="container-fluid">
  
		<img class="navbar-brand mb-0" src="resources/favicon.png" width="38" height="45" alt="">
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		  <span class="navbar-toggler-icon"></span>
		</button>
		
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
		  <ul class="navbar-nav mr-auto">
		    <li class="nav-item">
		      <a class="nav-link" id="navAccueil" href="index.jsp">Accueil</a>
		    </li>
		    <li class="nav-item">
		      <a class="${empty sessionScope.sessionUtilisateur ? 'nav-link disabled' : 'nav-link'}" id="navSites" href="${empty sessionScope.sessionUtilisateur ? '#' : 'sites'}">Sites de grimpe</a>
		    </li>
		    <li class="nav-item">
		      <a class="${empty sessionScope.sessionUtilisateur ? 'nav-link disabled' : 'nav-link'}" id="navTopos" href="${empty sessionScope.sessionUtilisateur ? '#' : 'topos'}">Nos topos</a>      
		    </li>
		  </ul>
		  <ul class="navbar-nav ml-auto">
		    <li class="dropdown">
		      <button class="btn btn-outline-info dropdown-toggle" id="btnUser" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="fas fa-user" aria-hidden="true"></span></button>
		      <div class="dropdown-menu dropdown-menu-right">
		      
	      		<%-- Vérification de la présence d'un objet utilisateur en session --%>
	            <c:choose>
	  						<c:when test="${empty sessionScope.sessionUtilisateur}">
	  						  						
	  							<form class="px-4 py-2" method="post" action="connexion" id="form" name="form" novalidate>                        
		          			<h5>Se connecter</h5>
		          			<div class="form-group" id="form-group">				      				     
		            			<input type="email" class="form-control form-control-sm" id="email" name="email" placeholder="Adresse mail" style="margin-top:2px" value="<c:out value="${utilisateur.mail}"/>"> 
		           			 	<small class="form-text erreur">${connexionForm.erreurs['email']}</small> 					  
		            			<input type="password" class="form-control form-control-sm" id="password" name="password" placeholder="Mot de passe" style="margin-top:2px">
		          				<small class="form-text erreur">${connexionForm.erreurs['password']}</small> 
		          			</div>		    
		          			<button type="submit" class="btn btn-info btn-block btn-sm" id="valider">Valider</button>
		          			
		          			<small class="${empty connexionForm.erreurs ? 'succes' : 'erreur'}">${connexionForm.resultat}</small>             
		        			</form>
		        			<div class="dropdown-divider"></div>
		        			<a class="dropdown-item" href="inscription" id="lienInscription">Pas de compte ? S'inscrire</a>		 							
	 							
	 							</c:when>
	 							
	 							<c:when test="${!empty sessionScope.sessionUtilisateur}">	
	 							 							
									<div class="card">
									  <div class="card-header ">Boujour ${sessionScope.sessionUtilisateur.prenom}</div> 								  					  									
	 									<div class="list-group list-group-flush">
										  <a href="infospersos" class="list-group-item list-group-item-action">Infos persos</a>
										  <a href="mestopos" class="list-group-item list-group-item-action">Mes topos</a>
										  <a href="reservations" class="list-group-item list-group-item-action">Réservations</a>
										  <a href="deconnexion" class="list-group-item list-group-item-action">Déconnexion</a>
										</div>
	 								</div>	 							
	 							
	 							</c:when>	 				
						</c:choose>
		        	         
		      </div>
		    </li>
		  </ul>		
		</div>
	  
	</div> 
</nav>