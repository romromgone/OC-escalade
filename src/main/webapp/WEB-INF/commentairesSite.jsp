<link href="resources/css/commentaires.css" rel="stylesheet">

<div class="col-md-10">
	<form method="post" action="site" id="formComm" name="formComm" novalidate> 
		<div class="form-group">
			<h5 class="text-info">Commentaires</h5>
			<textarea class="form-control" id="commentaire" name="commentaire" rows="2"></textarea>
			<input type="hidden" id="idSite" name="idSite" value="${site.id}">		
		</div>	
		<button type="submit" class="btn btn-info btn-sm offset-md-10 col-md-2" id="valider">Envoyer</button>
	</form>	
	<c:forEach items="${commentairesSite}" var="commentaireSite">	
		<p class="headerComm"><mark>${commentaireSite.utilisateur.prenom} ${commentaireSite.utilisateur.nom}</mark> -- <em>${commentaireTopo.dateCT}</em></p>			  	      					
  	<p class="comm"><c:out value="${commentaireSite.texteCSi}" /></p>	
	</c:forEach>	
</div>									    
	
