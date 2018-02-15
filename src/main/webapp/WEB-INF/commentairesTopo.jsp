<link href="resources/css/commentaires.css" rel="stylesheet">

<div class="col-md-10">
	<form method="post" action="topo" id="formComm" name="formComm" novalidate> 
		<div class="form-group">
			<h5 class="text-info">Commentaires</h5>
			<textarea class="form-control" id="commentaire" name="commentaire" rows="2"></textarea>
			<input type="hidden" id="idTopo" name="idTopo" value="${topo.id}">		
		</div>	
		<button type="submit" class="btn btn-info btn-sm offset-md-10 col-md-2" id="valider">Envoyer</button>
	</form>	
	<c:forEach items="${commentairesTopo}" var="commentaireTopo">	
		<p class="headerComm"><mark>${commentaireTopo.utilisateur.prenom} ${commentaireTopo.utilisateur.nom}</mark> -- <em>${commentaireTopo.dateCT}</em></p>			  	      					
  	<p class="comm"><c:out value="${commentaireTopo.texteCT}" /></p>	
	</c:forEach>	
</div>									    
	
