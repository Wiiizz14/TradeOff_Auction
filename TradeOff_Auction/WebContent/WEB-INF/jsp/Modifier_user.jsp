<!-- Auteurs : LedouguetY -->
<!-- Description : Page pour modifier un utilisateur-->

<% request.setAttribute("window_title", "Cr�er un compte"); %>
<% request.setAttribute("title", "Troc et �changes"); %>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${!empty utilisateur}" >
	<c:set var="credit" value="${utilisateur.credit}" />
</c:if>

<%@ include file="/WEB-INF/fragments/formulaireUtilisateur.jspf"%>

	<!-- Cr�dits -->
	<div class="form-group">
		<div class="row">
	    	<label for="cr�dit" class="col-xs-3 col-xs-offset-1 control-label style-bloc-texte-1">Cr�dits : </label>    
		        <div class="col-xs-7">
	            	<div id="cr�dit">${credit}</div>
	            </div>
	    </div>
	</div>

	    <br>
	    <br>
             
             <!-- Boutons d'action -->   
			<div class="col-xs-3 col-xs-offset-1">
		        <button type="submit" value="Modifier_user" class="bouton-style-1" name="choix_user">Enregistrer</button>
		    </div>
		
		    <div class="col-xs-4">
		        <button type="submit" value="Supprimer_user" class="bouton-style-1" name="choix_user">Supprimer mon compte</button>
		    </div>
		                 
		    <div class="col-xs-3">
		        <button type="submit" value="retour" class="bouton-style-1" name="choix_user">Retour</button>
		    </div>
		    
    	</form>
    	
    	<div class="row">
    	<hr>
    	<hr>	
		    </div>
    </body>
</html>