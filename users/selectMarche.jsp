<%@page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" errorPage="../erreur.jsp" %>
<% session.setAttribute("page","Index"); %>
<jsp:include page="../partial/header.jsp"/>
  <head><link rel="stylesheet" type="text/css" href="css/main.css"></head>
    <article>
      <div class="wrap">
        <h1>Lille Web Market</h1>
	<table>
          <%
	     Class.forName(getServletContext().getInitParameter("driver"));
	     
	     String url = getServletContext().getInitParameter("url");
	     String user = getServletContext().getInitParameter("user");
	     String mdp = getServletContext().getInitParameter("mdp");
	     Connection con = DriverManager.getConnection(url,user, mdp);
	     Statement state = con.createStatement();
	     String iddemande = request.getParameter("marche");
	     String query = "select u.iduser, u.login, m.idmarche, m.libelle, av.prix, t.description, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and t.description = 'vente' group by u.iduser, u.login, m.idmarche, m.inverse, prix, t.description order by av.prix desc;";
	     PreparedStatement ps = con.prepareStatement(query);
	     ps.setInt(1, Integer.parseInt(iddemande));
	     ResultSet rs = ps.executeQuery();
	     int nbColumn = rs.getMetaData().getColumnCount();
	     %><html><head><link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/main.css\"> </head><body>
	      <article><div class="wrap">
		  <h1>Vendeurs : </h1>
		  <p>Cash : <%= session.getAttribute("cash") %></p>
		  <table><tr>
	    <%
	    ResultSetMetaData rsmd = rs.getMetaData();
	    for (int i = 1 ; i <= nbColumn ; i++)
	    { %> <td> <%= rsmd.getColumnName(i) %> </td><% } %> <tr> <% 
	    while (rs.next())
	    { %> <tr> <% for (int i = 1 ; i <= nbColumn ; i++) { %> <td> <%= rs.getString(i) %> </td> <% } %> </tr><tr> <% } %> </table> <%
	    query = "select u.iduser, u.login, m.idmarche, m.libelle, av.prix, t.description, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and t.description = 'achat' group by u.iduser, u.login, m.idmarche, m.inverse, prix, t.description order by av.prix desc;";
	    ps = con.prepareStatement(query);
	    ps.setInt(1, Integer.parseInt(iddemande));
	    rs = ps.executeQuery();
	    nbColumn = rs.getMetaData().getColumnCount(); %>
	    <article><div class="wrap">
		<h1>Acheteurs : </h1>
		<table><tr> <%
	    rsmd = rs.getMetaData();
	    for (int i = 1 ; i <= nbColumn ; i++)
	    { %> <td> <%= rsmd.getColumnName(i) %></td> <% } %></tr> <%
	    while (rs.next()) { %> <tr> <% for (int i = 1 ; i <= nbColumn ; i++) { %> <td> <%= rs.getString(i) %> </td> <% } %> </tr><tr> <% } %> </table>
	    <p><a href="selectMarcheInverse.jsp?marche=<%= iddemande %>">Marché inverse</a></p>
	    <p><a href="index.jsp">Retour à la liste des marchés</a></p>
	    </div></article><article><div class="wrap">
		<h3>Ajouter une offre</h3>
		<form method="post" action="AjouterOffre">
		  <p><label for="quantite">Nombre de bons :</label>
		  <input type="text" name="quantite" id="quantite" /></p>
		  <p><label for="prix">Prix du bon :</label>
		  <input type="text" name="prix" id="prix" /></p>
		  <input type="hidden" name="idmarche" id="idmarche" value= <%= request.getParameter("marche") %> />
		  <p><input type="submit" value="Ajouter l'offre"></p>
		</form>
	    </div></article>
	  </body></html> <%
	    con.close();
      %>
        </table>
      </div>
    </article>
<jsp:include page="../partial/footer.jsp"/>
