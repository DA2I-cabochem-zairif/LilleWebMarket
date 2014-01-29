<%@page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" errorPage="../erreur.jsp" %>
<% session.setAttribute("page","Index"); %>
<jsp:include page="../partial/header.jsp"/>
    <article>
      <div class="wrap">
        <h1>Lille Web Market</h1>
          <%
	     Class.forName(getServletContext().getInitParameter("driver"));
	     
	     String url = getServletContext().getInitParameter("url");
	     String user = getServletContext().getInitParameter("user");
	     String mdp = getServletContext().getInitParameter("mdp");
	     Connection con = DriverManager.getConnection(url,user, mdp);
	     Statement state = con.createStatement();
	     String iddemande = request.getParameter("marche");
	     String query = "select u.iduser, u.login, m.idmarche, m.inverse, 100 - av.prix as prixinverse, t.description, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and t.description = 'achat' group by u.iduser, u.login, m.idmarche, m.inverse, prixinverse, t.description order by prixinverse desc;";
	     PreparedStatement ps = con.prepareStatement(query);
	     ps.setInt(1, Integer.parseInt(iddemande));
	     ResultSet rs = ps.executeQuery();
	     int nbColumn = rs.getMetaData().getColumnCount();
	     %>
		  <h1>Vendeurs : </h1>
		  <p>Cash : <%= session.getAttribute("cash") %></p>
		  <table><tr>
	    <%
	    ResultSetMetaData rsmd = rs.getMetaData();
	    for (int i = 1 ; i <= nbColumn ; i++)
	    { %> <td> <%= rsmd.getColumnName(i) %> </td><% } %> <tr> <% 
	    while (rs.next())
	    { %> <tr> <% for (int i = 1 ; i <= nbColumn ; i++) { %> <td> <%= rs.getString(i) %> </td> <% } %> </tr><tr> <% } %> </table> <%
	    query = "select u.iduser, u.login, m.idmarche, m.inverse, 100 - av.prix as prixinverse, t.description, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and t.description = 'vente' group by u.iduser, u.login, m.idmarche, m.inverse, prixinverse, t.description order by prixinverse desc;";
	    ps = con.prepareStatement(query);
	    ps.setInt(1, Integer.parseInt(iddemande));
	    rs = ps.executeQuery();
	    nbColumn = rs.getMetaData().getColumnCount(); %>
		<h1>Acheteurs : </h1>
		<table><tr> <%
	    rsmd = rs.getMetaData();
	    for (int i = 1 ; i <= nbColumn ; i++)
	    { %> <td> <%= rsmd.getColumnName(i) %></td> <% } %></tr> <%
	    while (rs.next()) { %> <tr> <% for (int i = 1 ; i <= nbColumn ; i++) { %> <td> <%= rs.getString(i) %> </td> <% } %> </tr><tr> <% } %> </table>
	    <p><a href="selectMarche.jsp?marche=<%= iddemande %>">Marché droit</a></p>
	    <p><a href="index.jsp">Retour à la liste des marchés</a></p>
	    </div></article><article><div class="wrap">
		<h3>Ajouter une offre</h3>
		<form method="post" action="../AjouterOffreInverse">
		  <p><label for="quantite">Nombre de bons :</label>
		  <input type="text" name="quantite" id="quantite" /></p>
		  <p><label for="prix">Prix du bon :</label>
		  <input type="text" name="prix" id="prix" /></p>
		  <input type="hidden" name="idmarche" id="idmarche" value= <%= request.getParameter("marche") %> />
		  <p><input type="submit" value="Ajouter l'offre"></p>
		</form> <%
	    con.close();
      %>
        </table>
      </div>
    </article>
<jsp:include page="../partial/footer.jsp"/>
