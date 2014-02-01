<%@page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" errorPage="../erreur.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% session.setAttribute("page","Index"); %>
<jsp:include page="../partial/header.jsp"/>
  <head><link rel="stylesheet" type="text/css" href="css/main.css"></head>
    <article>
      <div class="wrap">
        <h1>Vente d'un titre</h1>
	<form method="post" action="../Revendre">
		  <p><label for="quantite">Nombre de bons :</label>
		  <input type="text" name="quantite" id="quantite" /></p>
		  <p><label for="prix">Prix du bon :</label>
		  <input type="text" name="prix" id="prix" /></p>
		  <input type="hidden" name="idmarche" id="idmarche" value= <%= request.getParameter("idmarche") %> />
		  <input type="hidden" name="max" id="max" value='<%= request.getParameter("max")%>' />
		  <input type="hidden" name="user" id="user" value='<%= request.getParameter("user") %>' />
		  <p><input type="submit" value="Vendre les titres"></p>
	</form>
      </div>
    </article>
<jsp:include page="../partial/footer.jsp"/>
