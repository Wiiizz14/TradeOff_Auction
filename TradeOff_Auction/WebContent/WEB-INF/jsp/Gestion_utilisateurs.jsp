<!-- Auteurs : JamesT -->
<!-- Description : Page pour gestion des utilisateurs (Admin)-->

<%request.setAttribute("window_title", "Gestion des utilisateurs Admin");%>
<%request.setAttribute("title", "Troc et échanges");%>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<div class="row">
			<!-- Titre admin  -->
	        <div class="col-xs-11 col-xs-offset-1">    
				<h3>Gestion des utilisateurs</h3> 
	        </div>
        </div>
         
		<hr>
            
        <!-- Titre recherche  -->
        <div class="row">
	        <div class="col-xs-11 style-bloc-texte-1 style-bloc-texte-2">    
	             <p>Rechercher un utilisateur par : </p>
	             <br> 
	        </div>
	    </div>                   
           
        <!-- Ici une partie du formulaire -->    
	     <form class="form-horizontal" action="./GestionDesUtilisateurs" method="post">
	
	            <div class="form-group">
	                
	                <div class="row">
	                    <label for="input_pseudo" class="col-xs-3 col-xs-offset-1 control-label">Pseudo : </label>    
	                    <div class="col-xs-7">
	                        <input type="text" class="form-control" id="input_pseudo" name="pseudo" placeholder="Pseudo">
	                    </div>
	                </div>
	            </div>
	            
	            <div class="form-group">              
	                <div class="row">       
	                    <label for="input_nom" class="col-xs-3 col-xs-offset-1 control-label">Nom : </label>         
	                    <div class="col-xs-7">
	                        <input type="text" class="form-control" id="input_nom" name="nom" placeholder="Nom">
	                    </div>
	                </div>
	            </div>
	            
	            <div class="form-group">        
	                <div class="row">              
	                    <label for="input_prenom" class="col-xs-3 col-xs-offset-1 control-label conteneur-txt-centre">Prénom : </label>     
	                    <div class="col-xs-7">
	                        <input type="text" class="form-control" id="input_prenom" name="prenom" placeholder="Prénom">
	                    </div>        
	                </div>
	            </div>
	            
	            <div class="form-group">
	                <div class="row">               
	                    <label for="input_email" class="col-xs-3 col-xs-offset-1 control-label">E-mail : </label>           
	                    <div class="col-xs-7">
	                        <input type="email" class="form-control" id="input_email" name="email" placeholder="E-mail">
	                    </div>                  
	                </div>
	            </div>
	            
	            <div class="form-group">
	                <div class="row">                   
	                    <label for="input_telephone" class="col-xs-3 col-xs-offset-1 control-label">N° de téléphone : </label>   
	                    <div class="col-xs-7">
	                        <input type="tel" class="form-control" id="input_telephone" name="telephone" pattern="[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}" placeholder="Téléphone  (format : **-**-**-**-**)">
	                    </div>
	                </div>
	            </div>
	
	            <div class="form-group">              
	                <div class="row">                  
	                    <label for="input_Rue" class="col-xs-3 col-xs-offset-1 control-label">Rue : </label>                   
	                    <div class="col-xs-7">
	                        <input type="text" class="form-control" id="input_rue" name="rue" placeholder="Rue">
	                    </div>
	                </div>
	            </div>
	            
	            
	            <div class="form-group">         
	                <div class="row">
	                    <label for="input_code_postal" class="col-xs-3 col-xs-offset-1 control-label style-bloc-texte-1">Code postal :</label>                    
	                    <div class="col-xs-7">
	                        <input type="text" class="form-control" id="input_code_postal" name="code_postal" placeholder="Code postal">
	                    </div>              
	                </div>
	            </div>
	            
	            <div class="form-group">
	                <div class="row">                  
	                    <label for="input_ville" class="col-xs-3 col-xs-offset-1 control-label">Ville : </label>                      
	                    <div class="col-xs-7">                        
	                        <input type="text" class="form-control" id="input_ville" name="ville" placeholder="Ville">
	                    </div>          
	                </div>
	            </div>
	     </form>       
          
        <!-- Bouton rechercher -->
        <div class="col-xs-10 col-xs-offset-1">
                <button type="submit" value="admin_rechercher" class="bouton-style-1" name="choix_user">Rechercher</button>
        </div>
        
        <!-- Tableau pour affichage de la liste des users selon les critères de recherche --> 
        <div class="col-xs-12">
              
            <div class="row">
                <br>
            </div>               
            
            <table class="col-xs-12">
           	<caption>Résultat de la recherche :</caption>

                <tr>
                    <th>Pseudo</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>E-mail</th>
                    <th>Ville</th>
                </tr>
                
                <c:forEach var="user" items="${ListeUtilisateur}">
               	    <tr>
	                    <td><a href="./GestionDesUtilisateurs?choix_user=modifier_user_admin">${user.pseudo}</a></td>
	                    <td>${user.nom}</td>
	                    <td>${user.prenom}</td>
	                    <td>${user.email}</td>
	                    <td>${user.ville}</td>
	                </tr>
                </c:forEach>
                
                <!-- Pour affichage fin de tableau -->
                <tr>
                    <th class="aff_tab"></th>
                    <th class="aff_tab"></th>
                    <th class="aff_tab"></th>
                    <th class="aff_tab"></th>
                    <th class="aff_tab"></th>
                </tr>     
            </table>
        </div>  
    </body>        
</html>         