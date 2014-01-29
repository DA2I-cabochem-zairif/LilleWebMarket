<%@page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" errorPage="../erreur.jsp" %>
<% session.setAttribute("page","Index"); %>
<jsp:include page="../partial/header.jsp"/>
    <article>
      <div class="wrap">
          <%
	     Class.forName(getServletContext().getInitParameter("driver"));
	     
	     String url = getServletContext().getInitParameter("url");
	     String user = getServletContext().getInitParameter("user");
	     String mdp = getServletContext().getInitParameter("mdp");
	     Connection con = DriverManager.getConnection(url,user, mdp);
	     Statement state = con.createStatement();
	     String iddemande = request.getParameter("marche");
	     String query = "select t.description as État, m.libelle as Marché, m.statut, m.dateFin, sum(av.quantite) as Quantité, av.prix as Prix from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and u.iduser = ? group by t.description, m.libelle, m.statut, m.dateFin, av.prix order by m.libelle, m.statut, m.dateFin, t.description, av.prix, Quantité;";
	     PreparedStatement ps = con.prepareStatement(query);
	     ps.setInt(1, Integer.parseInt(request.getParameter("user")));
	     ResultSet rs = ps.executeQuery();
	     int nbColumn = rs.getMetaData().getColumnCount();
	     %>
		  <h1>Résumés des titres de ${prenom}</h1>
		  <table><tr>
	    <%
	    ResultSetMetaData rsmd = rs.getMetaData();
	    for (int i = 1 ; i <= nbColumn ; i++)
	    { %> <td> <%= rsmd.getColumnName(i) %> </td><% } %> <tr> <% 
	    while (rs.next())
	    { %> <tr> <% for (int i = 1 ; i <= nbColumn ; i++) { %> <td> <%= rs.getString(i) %> </td> <% } %> </tr><tr> <% } %> </table> <%
	    con.close();
      %>
      <p><a href="index.jsp">Revenir à la liste des marchés</a></p>
      </div>
    </article>
<jsp:include page="../partial/footer.jsp"/>
