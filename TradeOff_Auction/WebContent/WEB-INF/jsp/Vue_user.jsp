<!-- Auteur : LeDougetY  RebièreA -->
<!-- Description : Page pour vue d'un profil utilisateur-->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("window_title", "Créer un compte"); %>
<% request.setAttribute("title", "Troc et échanges"); %>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>
<%@ include file="/WEB-INF/fragments/infosUtilisateur.jspf"%>

		<br>
		<br>
               
        <!-- Boutons -->        
		<form class="form-horizontal" action="./GestionDesUtilisateurs" method="post">
			<div class="row">
		<c:if test="${!compact}">
				<div class="col-xs-4 col-xs-offset-1">
                    <button type="submit" value="Vers_Modifier_user" class="bouton-style-1" name="choix_user">Modifier mon profil</button>
                </div>
           <div class="col-xs-4 col-xs-offset-2">
		</c:if>
		<c:if test="${compact}">
           <div class="col-xs-6 col-xs-offset-3">
       </c:if>
                    <button type="submit" value="Retour_liste_encheres" class="bouton-style-1" name="choix_user">Retour</button>
                </div>
            </div>
            <div class="row">
            	<br>
                   <c:if test="${utilisateur.administrateur}">
	                    <div class="col-xs-4 col-xs-offset-1">
	                    	<button type="submit" value="Gestion_utilisateur" class="bouton-style-1" name="choix_user">Gestion des utilisateurs</button>
	                    </div>
	                    <div class="col-xs-4 col-xs-offset-2">
	                    	<button type="submit" value="Gestion_ventes" class="bouton-style-1" name="choix_user">Gestion des ventes</button>
	                    </div>
                   </c:if>
            </div>
        </form>
    </body>
</html>