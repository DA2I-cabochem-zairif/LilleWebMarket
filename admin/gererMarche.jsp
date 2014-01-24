<%@page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" errorPage="../erreur.jsp" %>
<% session.setAttribute("page","Index"); %>
<jsp:include page="../partial/header.jsp"/>
  <head><link rel="stylesheet" type="text/css" href="css/main.css"></head>
    <article>
      <div class="wrap">
        <h1>Lille Web Market</h1>
        <p></p>
        <table>
          <%
	     Class.forName(getServletContext().getInitParameter("driver"));
	     
	     String url = getServletContext().getInitParameter("url");
	     String user = getServletContext().getInitParameter("user");
	     String mdp = getServletContext().getInitParameter("mdp");
	
	     Connection con = DriverManager.getConnection(url,user, mdp);
	     Statement stmt= con.createStatement();
             PreparedStatement ps = con.prepareStatement("select * from marche where statut = 'EN COURS';");
             ResultSet rs = ps.executeQuery();
	     %><center><h3>Liste des marchés :</h3></center><%
        while (rs.next())
        {
	    %>
	  <form method="post" action='<%= request.getContextPath() %>/FermerMarche'>
	  <p>
	    <a href='<%= request.getContextPath() %>/SelectInfoMarche?marche=<%= rs.getString("idmarche")%>' > <%= rs.getString("libelle") %></a>
	      <input type="hidden" id="idmarche" name="idmarche" value='<%= rs.getString("idmarche")%>' />
	      <input type="hidden" id="resultat" name="resultat" value="gagne" />
	      <input type="submit" value="Fermer ce marché" />
	  </p>
	  </form>
	    <%
	}
	con.close();
      %>
        </table>
	<p><a href="formMarche.jsp">Ajouter un marché</a>  <a href="../users/index.jsp">Retour à la liste des marchés</a></p>
      </div>
    </article>
<jsp:include page="../partial/footer.jsp"/>
