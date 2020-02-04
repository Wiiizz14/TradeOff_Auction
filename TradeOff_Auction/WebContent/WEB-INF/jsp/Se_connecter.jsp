<!-- Auteurs : RebièreA -->
<!-- Description : Page pour se connecter à un profil-->

<% request.setAttribute("window_title", "Se connecter"); %>
<% request.setAttribute("title", "Troc et échanges"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

        <!-- Référencement des pages CSS venant en surcouche du bootstrap et s'adaptant à la résolution
            de l'écran -->
        
        <link href="././css/styles_petits_mobiles.css" rel="stylesheet" media="screen and (max-width: 380px)">
        <link href="././css/styles_mobile.css" rel="stylesheet" media="screen and (max-width: 992px)">
        <link href="././css/styles_desktop.css" rel="stylesheet" media="screen and (min-width: 992px)">


        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- On définit le nom de l'onglet en cours via la balise <title></title> -->
        <title>${window_title}</title>
        
    </head>


        <body>

        <img src="././images/logoProjetfinal.png" class="accueil">
		<div class="bandeau"></div>
		
	        <form class="form-horizontal" action="./GestionDesUtilisateurs" method="post" accept-charset="ISO-8859-1, UTF-8">
	            <div class="form-group">
	                <br>
	                <!-- Affichage si le compte a été supprimé -->
	                <c:if test="${compteSupprimer}">
	                 	<div class="row">
		                	<div class="col-xs-9 col-xs-offset-2">
		                   		<p class="erreur">Désolé, ce compte a été supprimé</p>
		                   	</div>
		                </div>
	                </c:if>
	                
	                 <!-- Affichage si l'utilisateur n'a pas saisi tous les champs -->
	                <c:if test="${champsVides}">
	                 	<div class="row">
		                	<div class="col-xs-9 col-xs-offset-2">
		                   		<p class="erreur">Merci de renseigner tous les champs</p>
		                   	</div>
		                </div>
	                </c:if>
	                
	                 <!-- Affichage si le pseudo ou l'email ne sont pas connus -->
	                <c:if test="${erreurPseudo}">
	                 	<div class="row">
		                	<div class="col-xs-9 col-xs-offset-2">
		                   		<p class="erreur">L'identifiant est incorrect</p>
		                   	</div>
		                </div>
	                </c:if>
	                
	                 <!-- Champ de saisi pour l'identifiant (pseudo ou email) -->
	                <div class="row">
	                    
	                    <label for="input_identifiant" class="col-xs-3 col-xs-offset-1 control-label">Identifiant : </label>    
	                    
	                    <div class="col-xs-7">
	                        <input type="text" class="form-control" id="input_identifiant" name="identifiant" placeholder="Identifiant">
	                        </div>
	                    </div>
	                </div>
	            
	            <div class="form-group">
	                <br>
	                
	                 <!-- Affichage si le mot de passe saisi est incorrect -->
	                <c:if test="${erreurMotDePasse}">
	                 	<div class="row">
		                	<div class="col-xs-9 col-xs-offset-2">
		                   		<p class="erreur">Le mot de passe est incorrect</p>
		                   	</div>
		                </div>
	                </c:if>
	                
	                <!-- champ de saisi pour le mot de passe -->
	                <div class="row">
	                    <label for="input_mot_de_passe" class="col-xs-3 col-xs-offset-1 control-label">Mot de passe : </label>
	                    <div class="col-xs-7">
	                        <input type="password" class="form-control" id="input_mot_de_passe" name="mot_de_passe" placeholder="Mot de passe">
	                    </div>
	                </div>
	            </div>
	            
	            <div class="row">
	                <br>
	                <br>
	            </div>
	            
	            <div class="form-group">
	                <div class="row">
	                    <!-- bouton pour se connecter -->
	                    <div class="col-xs-4 col-xs-offset-2">
	                        <input type="submit" value="Connexion" class="bouton-style-1" name="choix_user">
	                        </div>
	
	                   <!--  <div class="col-xs-6">
	
	                        <div class="row">
							<!-- checkbox permettant de mémoriser l'identifiant
	                            <div class="col-xs-1 ">
	                            <input type="checkbox" value="Se souvenir de moi">
	                                </div>
	                            
	                            On utilise ici le style-bloc-texte-2 pour avoir un rendu de texte 
	                                 visuellement plus agréable 
	                            <div class="col-xs-8 style-bloc-texte-2">
	                            <p>Se souvenir de moi</p>
	                                </div>
	
	                            </div>
	
	                        <div class="row">
	                            <div class="col-xs-1">
	                                </div>
	                            cf ligne 86
	                            <div class="col-xs-8 style-bloc-texte-2">
	                            <!-- lien permettant de récupérer le mot de passe 
	                            On utilise la classe lien-sans-deco pour afficher le texte du lien
	                                 dans la couleur de police normale et sans qu'il soit souligné
	                            <a href="" id="mot_de_passe_oublie" class="lien-sans-deco">Mot de passe oublié ?</a>
	                                </div>
	
	                            </div>
	                        </div> -->
	            	</div>
	            
	            <div>
	            	<br><br><br><br>
	            </div>
	            
	                
	                <div class="col-xs-8 col-xs-offset-2">
	                <!-- bouton pour créer un compte -->
	                    <button type="submit" value="Lien_creation" class="bouton-style-1" name="choix_user">Créer un compte</button>
	                </div>
	           </div>
	        </form>
	    </header>
	</body>
</html>