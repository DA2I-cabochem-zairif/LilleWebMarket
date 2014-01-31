
<jsp:include page="partial/header.jsp"/>
    <body>
      <center>
      <form action="AjouterUtilisateur" method="post">
	<p>Nom : <INPUT TYPE="text" SIZE="20" NAME="nom" VALUE=""> <br /></p>
	<p>Prénom : <INPUT TYPE="text" SIZE="20" NAME="prenom" VALUE=""><br /></p>
	<p>Mot de passe : <INPUT TYPE="text" SIZE="20" NAME="mdp" VALUE=""><br /></p>
	<p>Retapez votre Mot de passe : <INPUT TYPE="text" SIZE="20" NAME="remdp" VALUE=""><br /></p>
	<br />
	<input type="submit" value="Créer">
	<input type="reset">
	<p><a href="login.jsp">Revenir au formulaire de connexion</a></p>
      </form>
    </body>
</html>

<jsp:include page="partial/footer.jsp"/>