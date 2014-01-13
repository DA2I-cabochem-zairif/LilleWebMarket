<!DOCTYPE html>
  <HTML lang="fr">
  <head>
    <title>LilleWebMarket</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<%out.print(request.getContextPath());%>/css/reset.css">
    <link rel="stylesheet" type="text/css" href="<%out.print(request.getContextPath());%>/css/font.css">
    <link rel="stylesheet" type="text/css" href="<%out.print(request.getContextPath());%>/css/main.css">
  </head>
  <body>
    <header>
      <div class="wrap">
        <img src="img/logo.png">
        <h1>Lille Web Market</h1>
        <div class="infouser">
          <img src="http://lorempixel.com/90/90/" alt="photo">
          <div class="user">
            <p>Bienvenue</p>
            <p><em>Maxime Caboche</em></p>
            <p><a href="#">Se deconnecter</a></p>
            <div class="raiting">
              <ul><li></li><li></li><li></li><li></li><li></li></ul>
            </div>
          </div>
        </div>
      </div>
    </header>
    <article>
      <div class="wrap">
        <table>
          <tr>
            <th>Informations</th>
            <th>Cote</th>
          </tr>
          <tr>
            <td><a href="#">Fouad respectera W3C avant mars</a></td>
            <td class="plus">+0.5</td>
          </tr>
          <tr>
            <td><a href="#">Fouad respectera W3C avant mars</a></td>
            <td class="moins">-3</td>
          </tr>
          <tr>
            <td><a href="#">Fouad respectera W3C avant mars</a></td>
            <td class="plus">+12</td>
          </tr>
        </table> 
      </div>
    </article>
    <footer>
      <img src="img/DA2I.png" alt="logo DA2I">
      <p><a href="#">Informations</a>&nbsp;|&nbsp;<a href="#">Mentions legales</a>&nbsp;|&nbsp;<a href="#">contact</a></p>
      <p>Cité Scientifique 59655 Villeneuve d'Ascq Cédex Tél. +33 (0) 3.20.43.43.32 | © DA2I</p>
    </footer>
  </body>
</html>
