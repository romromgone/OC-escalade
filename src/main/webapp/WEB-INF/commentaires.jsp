<form method="post" action="topo" id="form" name="form" novalidate> 
	<div class="form-group row">
		<label class="col-md-4 col-form-label" for="commentaire">Commentaire</label>
		<div class="col-md-8">
			<textarea class="form-control" id="commentaire" name="commentaire" rows="2"></textarea>
		</div>
		<input type="hidden" id="idTopo" name="idTopo" value="${topo.id}">
		<button type="submit" class="btn btn-info" id="valider">Valider</button>	
		
		<c:forEach items="${commentairesTopo}" var="commentaireTopo">		  	      					
	  	<p><c:out value="${commentaireTopo.dateCT}" /></p>
	  	<p><c:out value="${commentaireTopo.texteCT}" /></p>	
	  	<p><c:out value="${commentaireTopo.idUser}" /></p>	
	  	<p><c:out value="${commentaireTopo.idTopo}" /></p>				    
		</c:forEach>	
								    
	</div>
</form>