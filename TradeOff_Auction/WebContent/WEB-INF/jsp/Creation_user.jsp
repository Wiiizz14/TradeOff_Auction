<!-- Auteurs : RebièreA -->
<!-- Description : Page pour création d'un utilisateur-->

<% request.setAttribute("window_title", "Créer un compte"); %>
<% request.setAttribute("title", "Troc et échanges"); %>
<%@ include file="/WEB-INF/fragments/entete.jspf"%>
<%@ include file="/WEB-INF/fragments/formulaireUtilisateur.jspf"%>
            
                <br>
                <br>
                
                <div class="col-xs-4 col-xs-offset-1">
                    <button type="submit" value="Creer_user" class="bouton-style-1" name="choix_user">Créer</button>
                    </div>
                    
                <div class="col-xs-4 col-xs-offset-2">
                    <button type="submit" value="Annuler" class="bouton-style-1" name="choix_user">Annuler
                    </button>
                    </div>
        </form>
    </body>
</html>