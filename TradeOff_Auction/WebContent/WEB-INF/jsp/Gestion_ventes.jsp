<!-- Auteurs : JamesT -->
<!-- Description : Page pour gestion des ventes (Admin)-->

<%request.setAttribute("window_title", "Gestion des ventes Admin");%>
<%request.setAttribute("title", "Troc et échanges");%>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<!-- Titre admin  -->
		<div class="row">
	        <div class="col-xs-11 col-xs-offset-1">    
				<h3>Gestion des ventes</h3> 
	        </div>
        </div>
         
		<hr>
            
        <!-- Titre recherche  -->
        <div class="row">
	        <div class="col-xs-11 style-bloc-texte-1 style-bloc-texte-2">    
	             <p>Rechercher une vente par : </p>
	             <br> 
	        </div>
	    </div>
	    
        <!-- Ici une partie du formulaire -->    
        <form class="form-horizontal" action="./GestionDesVentes" method="post">
	     
	     
     		<!-- Select de la catégorie -->
     		<div class="form-group">
				<div class="row"> 
					<label for="Select_categorie" class="col-xs-3 col-xs-offset-1">Catégorie : </label>
					<div class="col-xs-7">
						<select class="form-control" name="categorie">
							<option value="toutes">Toutes</option>
							<!-- Boucle pour choix categorie -->
							<c:forEach var="categorie" items="${ListeCategorie}">
								<option value="${categorie.noCategorie}">${categorie.libelle}</option>
							</c:forEach>
						</select>   
					</div>
				</div>
			</div>
			   
 			<!-- Nom article -->
            <div class="form-group">              
                <div class="row">       
                    <label for="input_nom" class="col-xs-3 col-xs-offset-1 control-label">Nom article : </label>         
                    <div class="col-xs-7">
                        <input type="text" class="form-control" id="input_nom_article" name="nom_article" placeholder="Nom article">
                    </div>
                </div>
            </div>
            
            <!-- Pseudo -->
            <div class="form-group">        
                <div class="row">              
                    <label for="input_prenom" class="col-xs-3 col-xs-offset-1 control-label conteneur-txt-centre">Pseudo : </label>     
                    <div class="col-xs-7">
                        <input type="text" class="form-control" id="input_prenom" name="pseudo" placeholder="Pseudo">
                    </div>        
                </div>
            </div>
            
			<!-- Date intervalle -->
			<div class="form-group">
				<div class="row">
					<label for="input_fin_enchere" class="col-xs-4 col-xs-offset-1 control-label conteneur-txt-centre">Date comprise entre : </label>
					<div class="col-xs-5">
						<input type="date" id="input_fin_enchere" name="date" value="${now}"> 
					</div>
				</div>
			</div>	
				
			<div class="form-group">	
				<div class="row">
					<label for="input_fin_enchere" class="col-xs-4 col-xs-offset-1 control-label conteneur-txt-centre">et : </label>
					<div class="col-xs-5">
						<input type="date" id="input_fin_enchere" name="date" value="${now}"> 
					</div>
				</div>
			</div>        
        
         
       		<!-- Radio btn pour filtres ventes --> 
       		<div class="form-group">
	       		<div class="row">
	                <div class="col-xs-1 col-xs-offset-1">
	                    <input type="checkbox" name="mes_encheres_encours">
	                </div>
	                    <label for="filtres_enchere_encours" class="col-xs-4">Enchères en cours</label>
	                    
	                <div class="col-xs-1">
	                    <input type="checkbox" name="mes_encheres_terminees">
	                </div>
	                    <label for="filtres_enchere_terminee" class="col-xs-4">Enchères terminées</label>
                </div>
      		</div>
        </form> 
        
       <!-- Bouton rechercher -->
       <div class="col-xs-10 col-xs-offset-1">
               <button type="submit" value="admin_rechercher" class="bouton-style-1" name="choix_vente">Rechercher</button>
       </div>
       
       <!-- Tableau pour affichage de la liste des users selon les critères de recherche --> 
       <div class="col-xs-12">
             
           <div class="row">
               <br>
           </div>               
           
           <table class="col-xs-12">
          	<caption>Résultat de la recherche :</caption>

               <tr>
                   <th>Catégorie</th>
                   <th>Nom article</th>
                   <th>Date de fin</th>
                   <th>Pseudo</th>
               </tr>
               
               <c:forEach var="vente" items="${ListeVente}">
              	    <tr>
	                    <td>${vente.categorie.libelle}</td>
	                    <td><a href="./GestionDesVentes?choix_vente=admin_modification_vente&vente=vente">${vente.nomArticle}</a></td>
	                    <td>${vente.dateFinEncheresFormate}</td>
	                    <td><a href="./GestionDesUtilisateurs?choix_user=admin_modifier_user&client=vente.utilisateur">${vente.utilisateur.pseudo}</a></td>
                	</tr>
               </c:forEach>
               
               <!-- Pour affichage fin de tableau -->
               <tr>
                   <th class="aff_tab"></th>
                   <th class="aff_tab"></th>
                   <th class="aff_tab"></th>
                   <th class="aff_tab"></th>
               </tr>     
           </table>
       </div>  
    </body>        
</html>         