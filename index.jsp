<%@ page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" errorPage="erreur.jsp" %>
      <% session.setAttribute("page","Index");
session.setAttribute("nom","Anonyme");
session.setAttribute("prenom","");
session.setAttribute("titre","");
%>
    <jsp:include page="partial/header.jsp"/>
<article>
<div class="wrap">
<h1>Lille Web Market</h1>
	 <p>Bienvenue sur LilleWebMarket. Premiere bourse d'informations de Lille</p>
</div>
</article>
<jsp:include page="partial/footer.jsp"/>
