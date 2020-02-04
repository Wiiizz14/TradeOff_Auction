<!-- Auteurs : JamesT -->
<!-- Description : Page pour lister les enchères-->

<%request.setAttribute("window_title", "Liste des enchères");%>
<%request.setAttribute("title", "Troc et échanges");%>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        
        <!-- Liens -->
        <div class="lien-sans-deco col-xs-offset-1">
            <div>    
            <!-- lien vers la page vendre un article -->
                <a href="./GestionDesVentes?choix_vente=Vendre" class="lien-sans-deco">Vendre un article</a>
            </div>  
            <div>    
            <!-- lien vers la page vendre un article -->
                <a href="./GestionDesUtilisateurs?choix_user=Mon_Profil" class="lien-sans-deco">Mon profil</a>
            </div>    
            <div>    
            <!-- lien vers la page vendre un article -->
                <a href="./GestionDesUtilisateurs?choix_user=deconnexion" class="lien-sans-deco">Déconnexion</a>
            </div> 
        </div>
        <form action="./GestionDesVentes" method="post">
        <!-- Filtres -->
        <div class="col-xs-6 col-xs-offset-1">
            <h3>Filtres :</h3>
        </div>
        
        <div class="col-xs-8 col-xs-offset-1">       
            <!-- Affichage des articles de l'utilisateur qui sont en ventes et ceux qui ont étés vendus -->
            <div class="row">
                <div class="col-xs-1 ">
                    <input type="checkbox" name="mes_ventes">
                </div>
                <div class="col-xs-8 style-bloc-texte-2">
                    <p>Mes ventes</p>
                </div>
            </div>             
           
            <!-- Affichage des articles où l'utilisateur a posté une enchère --> 
            <div class="row">
                <div class="col-xs-1 ">
                    <input type="checkbox" name="mes_encheres_encours">
                </div>
                <div class="col-xs-8 style-bloc-texte-2">
                    <p>Mes enchères en cours</p>
                </div>
            </div>
           
           <!-- Affichage des enchères remportées par l'utilisateur --> 
           <!--  <div class="row">
                <div class="col-xs-1 ">
                    <input type="checkbox" name="mes_acquisitions">
                </div>
                <div class="col-xs-8 style-bloc-texte-2">
                    <p>Mes acquisitions</p>
                </div>
            </div> -->
            
            <!-- Toutes les enchères autres que celles précédemment citées -->
            <div class="row">
                <div class="col-xs-1 ">
                    <input type="checkbox" name="autres_encheres">
                </div>
                <div class="col-xs-8 style-bloc-texte-2">
                    <p>Autres enchères</p>
                </div>
            </div>  
            
            <div class="row">
                <br>
            </div>    
        </div>       

        <!-- Choix catégorie -->
        <div class="col-xs-12 col-xs-offset-1">          
            <div class="row">   
                <div class="col-xs-3" >
                    <p>Catégories</p>
                </div>
                 
                <div class="col-xs-6">
                    <select class="form-control" name="categorie">
                    	<option value="0">Toutes</option>
                    	<!-- Boucle pour choix categorie -->
	                    <c:forEach var="categorie" items="${ListeCategorie}">
	 						<option value="${categorie.noCategorie}">${categorie.libelle}</option>
						</c:forEach>
                    </select>               
                </div>
            </div>             
        </div> 
        
        <!-- Barre de recherche -->
        <div class="col-xs-12 col-xs-offset-1">
            <div class="row">
                <br>
            </div> 
            <div class="row">
                <input name="recherche" class="col-xs-8 col-xs-offset-1 style-bloc-texte-1 style-bloc-texte-2" type="text" placeholder="&#128269; Le nom de l'article contient...">
            </div>    
        </div>    

        <div class="col-xs-10 col-xs-offset-1">
            <div class="row">
                <br>
            </div>            
            <input type="submit" name="choix_vente" value="Rechercher" class="bouton-style-1">		                      
        </div>
        </form>
           
        <!-- Affichage des ventes selon les filtres -->
		<!-- Boucle for each pour génerer les fiedset -->
		<c:forEach var="vente" items="${ListeVentes}">

	        <!-- Premier fieldset -->
	        <div class="col-xs-12">
	            <div class="row">
	                <br>
	            </div>  
	            <fieldset class="col-xs-12">
	                <br>
	                
	                
	                <div class="row">  
	                <!-- Image de l'article -->          
	                   <!--  <div class="col-xs-4">
	                        <img class="img-responsive" src="image_article.png" alt="image de l'article" >    
	                    </div> -->
	
						<!-- Lien meilleure offre -->
	                    <%-- <div>    
	                        <a href="./GestionDesVentes?choix_vente=vers_encherir&vente=${vente.noVente}" id="meilleure_Offre" class="lien-sans-deco col-xs-7 style-bloc-texte-2">casMeilleureOffre</a>
	                    </div> --%>
	
	                    <div class="form-group col-xs-7">
	                        <div class="row conteneur-txt-centre">
	                        	<!-- Lien nom article -->    
	                            <a href="./GestionDesVentes?choix_vente=vers_encherir&vente=${vente.noVente}" id="nom_article" class="lien-sans-deco col-xs-9 style-bloc-texte-2 style-bloc-texte-1"><h2>${vente.nomArticle}</h2></a>
								<%-- <!-- Lien étape 2 -->	
	                            <a href="./GestionDesVentes?choix_vente=vers_encherir&client=${vente.noVente}" id="etape_2" class="lien-sans-deco col-xs-3 style-bloc-texte-1 style-bloc-texte-2">étape 2</a> --%>
	                        </div>
	                    </div>
	                    
	                    <!-- Prix et classement -->	
	                    <div class="form-group col-xs-8 col-xs-offset-2">
	                        <div class="row">
	                            <label class="col-xs-2 style-bloc-texte-1 style-bloc-texte-2">Prix : </label> 
	                            <label class="col-xs-4 col-xs-offset-2 style-bloc-texte-1 style-bloc-texte-2">${vente.prixVente} points</label>        
	                        </div>
	                    </div>
	       
	       				<!-- Date de fin -->
	                    <div class="form-group col-xs-8 col-xs-offset-2">
	                        <div class="row">
	                            <label class="col-xs-2 style-bloc-texte-1 style-bloc-texte-2">Fin de l'enchère : </label> 
	                            <label class="col-xs-4 col-xs-offset-2 style-bloc-texte-1 style-bloc-texte-2">${vente.dateFinEncheresFormate}</label>
	                        </div>
	                    </div>
	                  
						<!-- Retrait -->	                  
	                    <div class="form-group col-xs-8 col-xs-offset-2">
	                        <div class="row">
	                            <label class="col-xs-2 style-bloc-texte-1 style-bloc-texte-2">Retrait : </label> 
	                            <label class="col-xs-4 col-xs-offset-2 style-bloc-texte-1 style-bloc-texte-2">${vente.rueRetrait}</label>
	                        </div>
	                    </div>    
	                       
	                    <div class="form-group col-xs-8 col-xs-offset-2">
	                        <div class="row">
	                            <label class="col-xs-4 col-xs-offset-4 style-bloc-texte-1 style-bloc-texte-2">${vente.codePostalRetrait} ${vente.villeRetrait}</label>
	                        </div>
	                    </div>   
	                   
						<!-- Vendeur -->	                   
	                    <div class="form-group col-xs-8 col-xs-offset-2">
	                        <div class="row">
	                            <label class="col-xs-4 style-bloc-texte-1 style-bloc-texte-2">Vendeur : </label> 
	                            <div>    
	                                <a href="./GestionDesUtilisateurs?choix_user=vue_profil&client=${vente.utilisateur.noUtilisateur }" id="vendeur" class="lien-sans-deco">${vente.utilisateur.pseudo}</a>
	                            </div> 
	                        </div>
	                    </div>                   
	                </div>       
	            </fieldset>
	        </div>
       	</c:forEach> 
	</body>
</html>