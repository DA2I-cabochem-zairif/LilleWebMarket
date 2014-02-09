
<jsp:include page="partial/header.jsp"/>
<article>
	<div class="wrap">
    <form action="AjouterUtilisateur" method="post">
		<p>Nom : <INPUT TYPE="text" SIZE="20" NAME="nom" VALUE=""> <br /></p>
	<p>Prénom : <INPUT TYPE="text" SIZE="20" NAME="prenom" VALUE=""><br /></p>
	<p>Mot de passe : <INPUT TYPE="password" SIZE="20" NAME="mdp" VALUE=""><br /></p>
	<p>Retapez votre Mot de passe : <INPUT TYPE="password" SIZE="20" NAME="remdp" VALUE=""><br /></p>
	<br />
	<p><input type="submit" value="Créer">
	<input type="reset"></p>
	<p><a href="users/index.jsp">Revenir au formulaire de connexion</a></p>
      </form>
	</div>
</article>
<jsp:include page="partial/footer.jsp"/>