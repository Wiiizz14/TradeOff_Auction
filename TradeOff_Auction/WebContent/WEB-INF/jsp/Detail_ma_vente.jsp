<!-- Auteur : Alexis Rebiere -->
<!-- Description : Page pour afficher le détail d'une vente-->

<% request.setAttribute("window_title", "Detail de ma vente"); %>
<% request.setAttribute("title", "Troc et échanges"); %>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="prixInitial" value="${vente.prixInitial}" />
<c:set var="nomArticle" value="${vente.nomArticle}" />
<c:set var="prixVente" value="${vente.prixVente}" />
<c:set var="dateFin" value="${vente.dateFinEncheresFormate}" />
<c:set var="vendeur" value="${vente.utilisateur.pseudo}" />
<c:set var="rue" value="${vente.rueRetrait}" />
<c:set var="ville" value="${vente.villeRetrait}" />
<c:set var="codePostal" value="${vente.codePostalRetrait}" />

<form class="form-horizontal" action="./GestionDesVentes" method="post">
			<div class="row">
				<div class="col-xs-offset-2 col-xs-8 conteneur-titre">
				<h1>Détails de votre vente :</h1>
					</div>
			</div>
			<!-- Affichage du nom de l'article -->
			<div class="nom_article col-xs-10 conteneur-txt-centre">    
	            <h2>${nomArticle}</h2>
	            <br>
				<br>
	        </div>
	
			<!-- Affichage de l'image de l'article 
	        <div class="container">
	             <img class="img-responsive" src="image_article.png" alt="image de l'article" >    
	        </div>  -->
	       
	        <br>
            <!-- Affichage de l'enchère --> 
            <div class="form-group">
                <div class="row">
                    <label class="col-xs-4 col-xs-offset-1">Meilleure offre :</label> 
                    <div class="col-xs-6">${prixVente}</div>         
                </div>
            </div>
             <!-- Affichage du prix initial -->   
            <div class="form-group">
                <div class="row">
                    <label class="col-xs-4 col-xs-offset-1">Mise à prix :</label> 
                    <div class="col-xs-6">${prixInitial}</div>    
                </div>
            </div>
             <!-- Affichage de la date de fin de l'enchère --> 
            <div class="form-group">    
                <div class="row">
                    <label class="col-xs-4 col-xs-offset-1">Fin de l'enchère :</label> 
                    <div class="col-xs-6">${dateFin}</div>    
                </div>
            </div>
             <!-- Affichage de l'adresse de retrait -->   
            <div class="form-group">    
                <div class="row">
                    <label class="col-xs-4 col-xs-offset-1">Retrait :</label> 
                    <div class="col-xs-6">
                    
                    <div class="row">
                    ${rue}
                    </div>
                    
                    <div class="row">
                    ${ville}, ${codePostal}
                    </div>
                    
                    </div>    
                </div>
            </div>
            <!-- Affichage du nom du vendeur -->
            <div class="form-group">    
                <div class="row">
                    <label class="col-xs-4 col-xs-offset-1">Vendeur :</label> 
                    <div class="col-xs-6">${vendeur }</div>    
                </div>
            </div>
            
            <!-- Boutons retour et annuler enchère -->
            <div class ="row">
                <div class="col-xs-5 col-xs-offset-1">
                    <input type="submit" value="Annuler la vente" name="choix_vente" class="bouton-style-1">
                </div>

                <div class="col-xs-4 col-xs-offset-1">
                    <input type="submit" value="retour" name="choix_vente" class="bouton-style-1">
                </div>             
            </div>
        </form>
	</body>
</html>