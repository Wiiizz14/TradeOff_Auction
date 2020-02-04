<!-- Auteur : RebièreA & JamesT-->
<!-- Description : Page pour vendre un aricle-->

<% request.setAttribute("window_title", "Vendre un article"); %>
<% request.setAttribute("title", "Troc et échanges"); %>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="codePostal" value="${utilisateur.codePostal}"/>
<c:set var="rue" value="${utilisateur.rue}"/>
<c:set var="ville" value="${utilisateur.ville}"/>
	
		<!--Affichage d'un message d'erreur si tous les champs ne sont pas remplis-->
		<c:if test="${champsVides}">
	    	<div class="row">
		    	<div class="col-xs-9 col-xs-offset-2">
		        	<p class="erreur">Merci de remplir tous les champs</p>
		        </div>
		    </div>
	    </c:if>
	      <form class="form-horizontal" action="./GestionDesVentes" method="post" accept-charset="ISO-8859-1, UTF-8">
	        
	        <div class="form-group">
	        
				<div class="row">
	            
	            	<label for="Select_categorie" class="col-xs-3 col-xs-offset-1">Catégorie : </label>
	            
	            <div class="col-xs-7">
	            <select class="form-control" name="categorie">
					<!-- Boucle pour choix categorie -->
					<c:forEach var="categorie" items="${ListeCategorie}">
						<option value="${categorie.noCategorie}">${categorie.libelle}</option>
					</c:forEach>
	            </select>   
	            </div>
	          
	           </div>        
	        <br>
	           <div class="row">
		            <!-- Champ pour rentrer le nom de l'article-->
		            <label for="input_article" class="col-xs-3 col-xs-offset-1">Article : </label>
		            
		            <div class="col-xs-7">
		            <input type="text" class="form-control" id="input_article" name="article" placeholder="Nom de l'article" value="${Article}">
		            </div>
	           </div>
	       </div>
	            
	        <div class="form-group">
	        
	           <div class="row">
		            <!-- Champ pour rentrer la description de l'article-->
		            <label for="input_description" class="col-xs-3 col-xs-offset-1 style-bloc-texte-1">Description : </label>
		            
		            <div class="col-xs-7">
		                <textarea class="form-control" id="input_description" name="description" placeholder="Description de l'article" rows="3">${Description}</textarea>
		            </div>
	           </div>
	       </div>
	            
	       <!-- <div class="form-group">
	        
	          <div class="row">
	            
	            <label for="input_photo" class="col-xs-5 col-xs-offset-1" >Photo de l'article : </label>
	            
	            <div class="col-xs-4 ">
	                <button type="submit" id="input_photo" value="uploader" class="bouton-style-1" name="choix_vente"> UPLOADER</button>
	            </div>
	          
	          </div>
	       </div>
	    <br>        
	         <div class="container">
	             
	            <img src="photo_test.png"  alt="Photo de l'article" class="img-responsive" name="image" >
	            
	        </div> -->
	    <br>
	            
	         <div class="form-group">
	            <div class="row">
		             <label for="input_prix" class="col-xs-3 col-xs-offset-1">Prix initial : </label>
		             <div class="col-xs-4 ">
		             <!-- Champ pour rentrer le prix de l'article-->
		                 <input type="number" id="input_prix"  name="prix_initial" min="1" value="1">
		             </div>
	         	</div>
	         </div>   
	         
	         <div class="form-group">
		           	<!-- Affichage d'un message d'erreur si la date de fin de l'enchère est anterieure à la date du jour-->
			            <c:if test="${datePasse}">
					    	<div class="row">
						    	<div class="col-xs-9 col-xs-offset-2">
						        	<p class="erreur">Saisir une date ultérieure à celle d'aujourd'hui</p>
						        </div>
						    </div>
					    </c:if>
		        
		         <div class="row">
		          <!-- Champ pour rentrer la date de fin de l'enchère-->
		          <label for="input_fin_enchere" class="col-xs-3 col-xs-offset-1">Fin de l'enchère : </label>		          
		          <div class="col-xs-4 ">
		              <input type="date" id="input_fin_enchere"  name="date" value="${now}"> 
		          </div>
		        
		         </div>
	         </div>  
	            
	         <div class="form-group">
	        
	            <div class="row">
	            <!-- Champ pour rentrer l'adresse de retrait(par défaut: adresse du vendeur-->
	            <label for="adresse_retrait" class="col-xs-3 col-xs-offset-1">Retrait : </label>
	            
		            <div class="col-xs-6">
		            
		                <div class="row">
			                <label for="input_article" class="col-xs-3">Rue : </label>
			            
				            <div class="col-xs-7">
				            	<input type="text" class="form-control" id="input_rue" name="rue" value="${rue}">
				            </div>
		                </div>
		                
		                
		                <div class="row">
		                <label for="input_article" class="col-xs-3">Ville : </label>
		            
				            <div class="col-xs-7">
				            	<input type="text" class="form-control" id="input_ville" name="ville" value="${ville }">
				            </div>
		                </div>
		                 
		                 <div class="row">
		                 <label for="input_article" class="col-xs-3">Code postal : </label>
		            
				            <div class="col-xs-7">
				            	<input type="text" class="form-control" id="input_cp" name="cp" value="${codePostal }">
				            </div>
		                 </div>
	            	</div>
	            </div>
	         </div> 
	            
	       <br>     
	                      
	       <!-- Boutons publier et annuler -->
	       <div class="col-xs-4 col-xs-offset-1">
	       		<button type="submit" value="Publier" class="bouton-style-1" name="choix_vente">Publier</button>
	       </div>
	                   
	        <div class="col-xs- col-xs-offset-4">
	            <button type="submit" value="Annuler" class="bouton-style-1" name="choix_vente">Annuler</button>
	        </div>    
	    </form>         
	</body>
</html>