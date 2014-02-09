<%@ page pageEncoding="utf-8" %>
<%@ page errorPage="erreur.jsp" %>
<%@ page import="java.sql.*,javax.sql.*,javax.naming.*"%>
<% session.setAttribute("page","Index"); %>

<jsp:include page="partial/header.jsp"/>

      <article>
      <div class="wrap">
      <form action="j_security_check" method="post">
	<p>Login : <INPUT TYPE="text" SIZE="20" NAME="j_username" VALUE=""> <br /></p>
	<p>Mot de passe : <INPUT TYPE="text" SIZE="20" NAME="j_password" VALUE=""><br /></p>
	<br />
	<p><input type="submit" value="Connexion">
	<input type="reset"></p>
	<p>Vous n'avez pas de compte ? <a href="../creerLogin.jsp">Inscrivez-vous</a></p>
      </form>
      </div>
    </article>

<jsp:include page="partial/footer.jsp"/>