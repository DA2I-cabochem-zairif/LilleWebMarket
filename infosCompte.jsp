<html>
  <%@ page import="java.sql.*" errorPage="erreur.jsp" %>
<title></title>
    <body>
      <center>
	<%
	   Class.forName(getServletContext().getInitParameter("driver"));
	
	   String url = getServletContext().getInitParameter("url");
	   String user = getServletContext().getInitParameter("user");
	   String mdp = getServletContext().getInitParameter("mdp");
	   
	   Connection con = DriverManager.getConnection(url,user, mdp);
	   Statement stmt= con.createStatement();
           PreparedStatement ps = con.prepareStatement("select max(iduser) from utilisateur ;");
           ResultSet rs = ps.executeQuery();
	   rs.next();
	   int lastId = rs.getInt("max");
	   ResultSet rsLastUser = con.prepareStatement("select nom, prenom, login, mdp from utilisateur where iduser = '"+lastId+"' ;").executeQuery();
	   rsLastUser.next();
	   %><h1>Félicitations <% rsLastUser.getString("prenom") rsLastUser.getString("nom") %></h1><p>Vous venez de vous inscrire et pouvez désormais jouer à LilleWebMarket</p><p>Votre Login : <% rsLastUser.getString("login") %></p><p>Votre Mot de Passe : <% rsLastUser.getString("mdp") %></p>
	 <%
	   con.close();
	%>
    </body>
</html>
