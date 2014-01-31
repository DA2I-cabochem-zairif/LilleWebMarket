<%@page pageEncoding="utf-8" %>
<%@ page import="java.sql.*" errorPage="../erreur.jsp" %>
<% session.setAttribute("page","Index"); %>
<jsp:include page="../partial/header.jsp"/>
  <head><link rel="stylesheet" type="text/css" href="css/main.css"></head>
    <article>
      <div class="wrap">
        <h1>Lille Web Market</h1>
        <p><a href='<%= request.getContextPath() %>/admin/gererMarche.jsp'>Gérer les marchés</a></p>
        <table>
          <%
          	Connection con;
	    	Class.forName(getServletContext().getInitParameter("driver"));
	    		    Context initCtx = new InitialContext();
	    	Context envCtx  = (Context) initCtx.lookup("java:comp/env");
	    	DataSource ds   = (DataSource) envCtx.lookup("madb");
	    	con = ds.getConnection();
	    
            PreparedStatement ps = con.prepareStatement("select * from marche where statut = 'EN COURS';");
            ResultSet rs = ps.executeQuery();
	     %><h3>Liste des marchés :</h3><%
        while (rs.next())
        {
	    %>
	  <p>
	    <a class="titre" href="selectMarche.jsp?marche=<%= rs.getInt("idmarche") %>" > <%= rs.getString("libelle") %></a>
	  </p>
	    <%
	}
	con.close();
      %>
        </table>
      </div>
    </article>
<jsp:include page="../partial/footer.jsp"/>
