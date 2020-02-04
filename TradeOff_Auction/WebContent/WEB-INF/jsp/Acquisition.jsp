<!-- Auteurs : BondiguelD, JamesT -->
<!-- Description : Page enchère remportée -->

<%request.setAttribute("window_title", "Acquisition");%>
<%request.setAttribute("title", "Troc et échanges");%>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Variables -->
<c:if test="${!empty vente}">
	<c:set var="nomArticle" value="${vente.nomArticle}" />
	<c:set var="prixVente" value="${vente.prixVente}" />
	<c:set var="prixInitial" value="${vente.prixInitial}" />
	
	<c:if test="${!empty vente.utilisateur}">
		<c:set var="vendeur" value="${vente.utilisateur.pseudo}" />
		<c:set var="telephone" value="${vente.utilisateur.telephone}" />
		<c:set var="rue" value="${vente.utilisateur.rue}" />
		<c:set var="codePostal" value="${vente.utilisateur.codePostal}" />
		<c:set var="ville" value="${vente.utilisateur.ville}" />
	</c:if>
</c:if>

		<!--Affichage du titre de la page-->
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2">
				<h2>Vous avez remporté l'enchère</h2>
			</div>
		</div>
		
			<form class="form-horizontal" action="./GestionDesVentes" method="post">
		
				<!-- Affichage du nom de l'article -->
				<div class="col-xs-10 col-xs-offset-1">
					<h2>${nomArticle}</h2>
					<br>
					<br>
				</div>
				
				
				<!-- Affichage de l'image de l'article 
				<div class="container">
					<img src="${image}"	alt="image de l'article" class="img-responsive" name="image">
				</div>-->	
				
	
				<!-- Affichage de l'enchère -->
				<div class="col-xs-offset-1 col-xs-10 affichage">
				<div class="form-group">
					<div class="row">
						<label class="col-xs-5 col-xs-offset-1">Meilleure offre : </label> 
						<label class="col-xs-6">${prixVente} points</label>
					</div>
				</div>
		
				<!-- Affichage du prix initial -->
				<div class="form-group">
					<div class="row">
						<label class="col-xs-5 col-xs-offset-1">Mise à prix : </label> <label
							class="col-xs-6">${prixInitial} points</label>
					</div>
				</div>
				</div>
					<br>
		
				<!-- Affichage des informations du vendeur -->
				<fieldset class="col-xs-10 col-xs-offset-1 sans-bordure">
					<legend class="test">Retrait</legend>
		
						<div class="row">
							<div class="col-xs-12">${rue}</div>
						</div>
		
						<div class="row">
							<div class="col-xs-6">${codePostal} </div>
							<div class="col-xs-6">${ville}</div>
							<br>
						</div>
						<br>
						<div class="row">
							
							<label for="pseudo_vendeur" class="col-xs-6">Vendeur : 
								</label>
							<div class="col-xs-6">
								<div>
									<a href=".." id="pseudo_vendeur">${vendeur}</a>									
								</div>
								
							</div>
							
						</div>
						
						<br>
						
						<div class="row">
							<label for="num_tel" class="col-xs-6">Tel :
								</label>
							<div class="col-xs-6" id="num_tel">${telephone}
								</div>
							</div>
						<br>
						<br>
				</fieldset>
				
			<!--  Affichage du bouton retour -->	
			<div class="col-xs-4 col-xs-offset-1">
				
				<input type="submit" value="Retour" class="bouton-style-1" name="choix_vente">
			</div>
		</form>
	</body>
</html>