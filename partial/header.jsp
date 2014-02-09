<%@page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<jsp:useBean id="p" class="beans.Personne" scope="request" />
<% p.rechUser(request.getRemoteUser(), session); %>
<!DOCTYPE html>
  <HTML lang="fr">
  <head>
    <title>${page} - LilleWebMarket</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<%out.print(request.getContextPath());%>/css/reset.css">
    <link rel="stylesheet" type="text/css" href="<%out.print(request.getContextPath());%>/css/font.css">
    <link rel="stylesheet" type="text/css" href="<%out.print(request.getContextPath());%>/css/main.css">
  </head>
  <body>
    <header>
      <div class="wrap">
       	<a href="<%= request.getContextPath() %>/users/index.jsp" alt="Accueil"><img src="<%= request.getContextPath() %>/img/logo.png" alt="Logo-univ-lille1"></a>
        <h1>Lille Web Market</h1>
        
      </div>
    </header>
    <aside>
      <div class="infouser">
          <!-- <img src="http://lorempixel.com/90/90/" alt="photo"> -->
          <div class="user">
            <p>Bienvenue</p>
            <p><em>${prenom} ${nom}</em></p>            
            <div class="raiting">
              <ul><li></li><li></li><li></li><li></li><li></li></ul>
            </div>
            <ul>
              <li>Solde : ${cash}</li>
              <li><a href="<%= request.getContextPath() %>/users/etatTitres.jsp?user=${iduser}">Mes titres</a></li>
            </ul> 
            <p><a class="button" href="<%= request.getContextPath() %>/deconnexion.jsp">Se deconnecter</a></p>
	    <!--<p><a href="<%= request.getContextPath() %>/users/etatTitres.jsp?user=${iduser}">Espace personnel</a></p>-->
          </div>
        </div>
    </aside>
