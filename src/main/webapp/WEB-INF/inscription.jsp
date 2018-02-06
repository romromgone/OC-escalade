<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="fr">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="icon" type="image/png" href="resources/favicon.png">
		<title>Escalade Communautaire</title>
		
		<link rel="stylesheet" href="webjars/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
    <link href="resources/css/inscription.css" rel="stylesheet">
	</head>
	
	<body>
    <%@ include file="menu.jsp" %>
    
    <main role="main" class="container">
    	<div class="offset-md-2 col-md-8">
      	<div class="card">
  				<div class="card-header">Formulaire d'inscription</div>
  				<div class="card-body col-md-12">
       			<form method="post" action="inscription" id="form" name="form" novalidate>
		  				<div class="form-group row">
		    				<label class="col-md-4 col-form-label" for="prenom">Prénom</label>
		    				<div class="col-md-8">
		    					<input type="text" class="form-control" id="prenom" name="prenom" value="<c:out value="${utilisateur.prenom}"/>">
		    					<small class="form-text">${inscriptionForm.erreurs['prenom']}</small>
		    				</div>
		  				</div>
		  				<div class="form-group row">
		    				<label class="col-md-4 col-form-label" for="nom">Nom</label>
		    				<div class="col-md-8">
		    					<input type="text" class="form-control" id="nom" name="nom" value="<c:out value="${utilisateur.nom}"/>">
		    					<small class="form-text">${inscriptionForm.erreurs['nom']}</small>
		    				</div>
		  				</div>
		  				<div class="form-group row">
		    				<label class="col-md-4 col-form-label" for="mail">Email</label>
		    				<div class="col-md-8">
		    					<input type="email" class="form-control" id="mail" name="mail" value="<c:out value="${utilisateur.mail}"/>">
		    					<small class="form-text">${inscriptionForm.erreurs['mail']}</small>
		    				</div>
		  				</div>
		  				<div class="form-group row">
		    				<label class="col-md-4 col-form-label" for="cp">Code postal</label>
		    				<div class="col-md-8">
		    					<input type="text" class="form-control" id="cp" name="cp" maxlength="5" value="<c:out value="${utilisateur.codePostal}"/>">
		    					<small class="form-text">${inscriptionForm.erreurs['cp']}</small>
		    				</div>
		  				</div>
		  				<div class="form-group row">
		    				<label class="col-md-4 col-form-label" for="mdp">Mot de passe</label>
		    				<div class="col-md-8">
			    				<input type="password" class="form-control" id="mdp" name="mdp" aria-describedby="passwordHelpBlock" maxlength="30">
			    				<small class="form-text">${inscriptionForm.erreurs['mdp']}</small>
		    				</div>
		  				</div>
		  				<div class="form-group row">
		    				<label class="col-md-4 col-form-label" for="mdp2">Confirmer mot de passe</label>
		    				<div class="col-md-8 is-invalid">
		    					<input type="password" class="form-control" id="mdp2" name="mdp2" maxlength="30">
		    				</div>
		  				</div>
		  				<button type="submit" class="col-md-2 offset-md-10 btn btn-info" id="valider">Valider</button>	
		  				<p class="${empty inscriptionForm.erreurs ? 'succes' : 'erreur'}">${inscriptionForm.resultat}</p>
						</form>
      	  </div>
        </div>
      </div>
    </main>
    
    <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
	  <script src="webjars/popper.js/1.11.1/dist/umd/popper.min.js"></script>
	  <script src="webjars/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
	  <script src="webjars/jquery-validation/1.17.0/jquery.validate.min.js"></script>
	  <script src="webjars/jquery-validation/1.17.0/additional-methods.min.js"></script>
    <script src="https://use.fontawesome.com/c448120b09.js"></script>
    <script>
			$(btnUser).mouseover(function() {
			  $(this).dropdown('toogle');
			});
		</script>
		<!-- <script> 
			$("#form").validate( {
				rules: {
					prenom: "required",
					nom: "required",
					mail: {
						required: true,
						email: true
					},
					mdp: {
						required: true,
						minlength: 5,
						maxlength: 30
					},
					mdp2: {		
						required: true,
						equalTo: "#mdp"
					},				
				},
				messages: {
					prenom: "Ce champ est obligatoire.",
					nom: "Ce champ est obligatoire.",
					mail: "Veuillez saisir une adresse électronique valide.",
					mdp: {
						required: "Ce champ est obligatoire.",
						minlength: "Doit comporter au moins 5 caractères.",
						maxlength: "Doit comporter au plus 30 caractères."
					},
					mdp2: {
						required: "Ce champ est obligatoire.",
						equalTo: "Le mot de passe n'est pas identique."
					},	
				},
				errorElement: "small",
				errorPlacement: function (error, element) {				
					error.addClass("form-text");
					error.insertAfter(element);
				},
				highlight: function ( element, errorClass, validClass ) {
					$(element).addClass("is-invalid").removeClass("is-valid");
				},
				unhighlight: function (element, errorClass, validClass) {
					$(element).addClass("is-valid").removeClass("is-invalid");
				}
			});
		</script> -->
    
  </body>
</html>