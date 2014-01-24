<%@page pageEncoding="utf-8" %>
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
        <div class="infouser">
          <img src="http://lorempixel.com/90/90/" alt="photo">
          <div class="user">
            <p>Bienvenue</p>
            <p><em>${prenom} ${nom}</em></p>
            <p><a href="<%= request.getContextPath() %>/deconnexion.jsp">Se deconnecter</a></p>
            <div class="raiting">
              <ul><li></li><li></li><li></li><li></li><li></li></ul>
            </div>
          </div>
        </div>
      </div>
    </header>
