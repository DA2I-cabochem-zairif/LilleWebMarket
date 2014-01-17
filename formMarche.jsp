<%@page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" errorPage="erreur.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% session.setAttribute("page","Index"); %>
<jsp:include page="partial/header.jsp"/>
  <head><link rel="stylesheet" type="text/css" href="css/main.css"></head>
    <article>
      <div class="wrap">
        <h1>Ajout d'un marché</h1>
        <form method="post" action="AjouterMarche">
	  <p><label for="libelle">Nom du marché :</label>
	  <input type="text" name="libelle" id="libelle" /></p>

	  <p><label for="inverse">Nom du marché inverse :</label>
	  <input type="text" name="inverse" id="inverse" /></p>
	  
	  <p><label for="dateFin">Date de fin :</label>
	  <input type="text" name="dateFin" id="dateFin" /> (Sous la forme jj/mm/aaaa)</p>
	  <p><input type="submit" value="Ajouter ce marché"></p>
	  <p><a href="index.jsp">Retour à la liste des marchés</a></p>
	</form>
      </div>
    </article>
<jsp:include page="partial/footer.jsp"/>
