<%@ page pageEncoding="utf-8" %>
<%@ page errorPage="../erreur.jsp" %>
<%@ page import="java.sql.*,javax.sql.*,javax.naming.*"%>
<jsp:include page="../partial/header.jsp"/>
    <article>
      <div class="wrap">
        <h1>Lille Web Market</h1>
        <%
	     Class.forName(getServletContext().getInitParameter("driver"));
	          	Connection con;
    	Class.forName(getServletContext().getInitParameter("driver"));
        Context initCtx = new InitialContext();
    	Context envCtx  = (Context) initCtx.lookup("java:comp/env");
    	DataSource ds   = (DataSource) envCtx.lookup("madb");
    	con = ds.getConnection();
	     Statement state = con.createStatement();
	     String iddemande = request.getParameter("marche");
	     String query = "select u.iduser, u.login, m.idmarche, m.libelle, av.prix, t.description, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and t.description = 'vente' group by u.iduser, u.login, m.idmarche, m.inverse, prix, t.description order by av.prix desc;";
	     PreparedStatement ps = con.prepareStatement(query);
	     ps.setInt(1, Integer.parseInt(iddemande));
	     ResultSet rs = ps.executeQuery();
	     int nbColumn = rs.getMetaData().getColumnCount();
	     %>
		 <div id="jqplot"></div>
		  <h1>Vendeurs : </h1>
		  <table><tr>
	    <%
	    ResultSetMetaData rsmd = rs.getMetaData();
	    for (int i = 1 ; i <= nbColumn ; i++)
	    {
		if (i == 2 | i == 5 | i == 6 | i == 7){
		%> <td> <%= rsmd.getColumnName(i) %> </td> <% 
	    }} %> <tr> <% 
	    while (rs.next())
	    { %> <tr> <% for (int i = 1 ; i <= nbColumn ; i++) { if (i == 2 | i == 5 | i == 6 | i == 7){ %> <td> <%= rs.getString(i) %> </td> <% }} %> </tr><tr> <% } %> </table> <%
	    query = "select u.iduser, u.login, m.idmarche, m.libelle, av.prix, t.description, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and t.description = 'achat' group by u.iduser, u.login, m.idmarche, m.inverse, prix, t.description order by av.prix desc;";
	    ps = con.prepareStatement(query);
	    ps.setInt(1, Integer.parseInt(iddemande));
	    rs = ps.executeQuery();
	    nbColumn = rs.getMetaData().getColumnCount(); %>
		<h1>Acheteurs : </h1>
		<table><tr> <%
	    rsmd = rs.getMetaData();
	    for (int i = 1 ; i <= nbColumn ; i++)
	    { if (i == 2 || i == 5 || i == 6 || i == 7){ %> <td> <%= rsmd.getColumnName(i) %></td> <% }} %></tr> <%
	    while (rs.next())
	    { %> <tr> <% for (int i = 1 ; i <= nbColumn ; i++) { if (i == 2 || i == 5 || i == 6 || i == 7){ %> <td> <%= rs.getString(i) %> </td> <% }} %> </tr><tr> <% } %> </table>
	    <p><a href="selectMarcheInverse.jsp?marche=<%= iddemande %>">Marché inverse</a></p>
	    <p><a href="index.jsp">Retour à la liste des marchés</a></p>
	    </div></article><article><div class="wrap">
		<h3>Ajouter une offre</h3>
		<form method="post" action="../AjouterOffre">
		  <p><label for="quantite">Nombre de bons :</label>
		  <input type="text" name="quantite" id="quantite" /></p>
		  <p><label for="prix">Prix du bon :</label>
		  <input type="text" name="prix" id="prix" /></p>
		  <input type="hidden" name="idmarche" id="idmarche" value= <%= request.getParameter("marche") %> />
		  <p><input type="submit" value="Ajouter l'offre"></p>
		</form> <%
	    con.close();
      %>
      </div>
    </article>
	  <script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-2.1.0.min.js"></script>
		<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.jqplot.min.js"></script>
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/js/jquery.jqplot.min.css" /
<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/js/jqplot.json2.min.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath()%>/js/marche.js"></script>
<jsp:include page="../partial/footer.jsp"/>
