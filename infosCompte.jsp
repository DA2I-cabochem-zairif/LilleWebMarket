<%@ page pageEncoding="utf-8" %>
<%@ page errorPage="../erreur.jsp" %>
<%@ page import="java.sql.*,javax.sql.*,javax.naming.*"%>
<% session.setAttribute("page","Index"); %>

<jsp:include page="../partial/header.jsp"/>
      <center>
	<%
	   Class.forName(getServletContext().getInitParameter("driver"));
	   Context initCtx = new InitialContext();
	   Context envCtx  = (Context) initCtx.lookup("java:comp/env");
	   DataSource ds   = (DataSource) envCtx.lookup("madb");
	   con = ds.getConnection();
	   /*String url = getServletContext().getInitParameter("url");
	   String user = getServletContext().getInitParameter("user");
	   String mdp = getServletContext().getInitParameter("mdp");
	   Connection con = DriverManager.getConnection(url,user, mdp);*/
	   Statement stmt= con.createStatement();
           PreparedStatement ps = con.prepareStatement("select max(iduser) from utilisateur ;");
           ResultSet rs = ps.executeQuery();
	   rs.next();
	   int lastId = rs.getInt("max");
	   ResultSet rsLastUser = con.prepareStatement("select nom, prenom, login, mdp from utilisateur where iduser = "+lastId+" ;").executeQuery();
	   rsLastUser.next(); %>
	   <h1>Félicitations <%= rsLastUser.getString("prenom") %> <%= rsLastUser.getString("nom") %>, votre compte a bien été créé.</h1>
	   <p>Votre Login : <%= rsLastUser.getString("login") %></p>
	   <p>Votre Mot de passe : <%= rsLastUser.getString("mdp") %></p>
	   <p><a href="login.jsp">Revenir au formulaire de connexion</a></p>
	   <% con.close();
	%>
    </body>
</html>
