<%@page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" errorPage="../erreur.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% session.setAttribute("page","Index"); %>
<jsp:include page="../partial/header.jsp"/>
  <head><link rel="stylesheet" type="text/css" href="css/main.css"></head>
    <article>
      <div class="wrap">
	<p><a href='../users/selectMarche.jsp?marche=<%= request.getParameter("marche") %>'>Voir ce marché</a></p>
	<p><a href="formMarche.jsp">Ajouter un autre marché</a></p>
	<p><a href="../users/index.jsp">Retour à la liste des marchés</a></p>
      </div>
    </article>
<jsp:include page="../partial/footer.jsp"/>
