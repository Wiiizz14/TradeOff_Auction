<!-- Auteurs : BondiguelD, JamesT -->
<!-- Description : Page pour annuler une enchère -->

<%request.setAttribute("window_title", "Annuler enchère");%>
<%request.setAttribute("title", "Troc et échanges");%>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Variables -->
<c:if test="${!empty vente}">
	<c:set var="nomArticle" value="${vente.nomArticle}" />
	<c:set var="prixVente" value="${vente.prixVente}" />
	<c:set var="prixInitial" value="${vente.prixInitial}" />
	<c:set var="dateFinEncheres" value="${vente.dateFinEncheres}" />
	<!-- <c:set var="image" value="{vente.image}" /> -->
	
	<c:if test="${!empty vente.utilisateur}">
		<c:set var="vendeur" value="${vente.utilisateur.pseudo}" />
	</c:if>
</c:if>

	<!--Affichage du titre de la page-->
	
	
		<form class="form-horizontal" action="./GestionDesVentes"
			method="post" accept-charset="ISO-8859-1, UTF-8">
		
			<br>
		
			<!-- Affichage du nom de l'article -->	
			<div class="col-xs-10 col-xs-offset-1 conteneur-txt-centre">
				<h2>${nomArticle}</h2>
				<br>
				<br>
			</div>
		
				<br>
				
			<!-- Affichage de l'image de l'article 
			<div class="container">
				<img src="${image}" alt="Photo de l'article"
					class="img-responsive" name="image">
			</div>-->
		
				<br>
				
			<!-- Affichage de l'enchère -->
			<div class="form-group">
				<div class="row">
					<label for="meilleur_enchere" class="col-xs-5 col-xs-offset-1">Meilleur
						offre : </label>
		
					<div class="col-xs-4 ">
						<div id="meilleur_enchere">${prixVente} pts par ${nomEncherisseur}</div>
					</div>
				</div>
			</div>
		
				<br>
		
			<!-- Affichage du prix initial -->
			<div class="form-group">
				<div class="row">
					<label for="prix_initial" class="col-xs-5 col-xs-offset-1">Mise
						à prix : </label>
					<div class="col-xs-4 ">
						<div id="prix_initial">${prixInitial} points</div>
					</div>
				</div>
			</div>
		
				<br>
		
			<!-- Affichage de la date de fin de l'enchère -->
			<div class="form-group">
				<div class="row">
					<label for="date_fin" class="col-xs-5 col-xs-offset-1">Fin de l'enchère : </label>
					<div class="col-xs-4 ">
						<div id="date_fin">${dateFinEncheres}</div>
					</div>
				</div>
			</div>
		
				<br>
				
			<!-- Affichage de l'adresse de retrait -->
			<div class="form-group">
				<div class="row">
					<label for="adresse_retrait" class="col-xs-5 col-xs-offset-1">Retrait : </label>
					<div class="col-xs-4 ">
						<div id="adresse_retrait">${rue}</div>
					</div>
				</div>
		
				<div class="row">
					<div class="row col-xs-4 col-xs-offset-6">
						<div id="adresse_retrait">${codePostal} ${ville}</div>
					</div>
				</div>
			</div>
		
				<br>
			
			<!-- Affichage du nom du vendeur -->
			<div class="form-group">
				<div class="row">
					<label for="pseudo_vendeur" class="col-xs-5 col-xs-offset-1">Vendeur : </label>
					<div class="col-xs-4 ">
						<div>
							<a href=".." id="pseudo_vendeur">${vendeur}</a>
						</div>
					</div>
				</div>
			</div>
		
				<br>
		
			<!-- Affichage du montant enchéri -->
			<div class="row">
				<div class="form-group">
					<label for="proposition_enchere" class="col-xs-3 col-xs-offset-1">Ma proposition : </label>
		
					<div class="col-xs-4">
						<input type="number" id="proposition_enchere" name="prix" min="${prixVente + 1}">
					</div>
		
					<div class="col-xs-3">
						<button type="submit" id="proposition_enchere" value="Encherir" class="bouton-style-1" name="choix_vente">Encherir</button>
					</div>
				</div>
			</div>
		
			<!-- Bouton retour et annuler enchère -->
			<div class="row">
				<div class="col-xs-5 col-xs-offset-1">
					<button type="submit" value="Annuler_ma_derniere_enchere" class="bouton-style-1" name="choix_vente">Annuler ma dernière enchère</button>
				</div>
							
				<div class="col-xs-4 col-xs-offset-1">
					<button type="submit" value="Retour" class="bouton-style-1" name="choix_vente">Retour</button>
				</div>
			</div>
		</form>
	</body>
</html>