import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/SelectInfoMarche")
public class SelectInfoMarche extends HttpServlet
{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	Connection con = null;
	HttpSession session = req.getSession();
	try
	{
	    Class.forName(getServletContext().getInitParameter("driver"));
	    
	    String url = getServletContext().getInitParameter("url");
	    String user = getServletContext().getInitParameter("user");
	    String mdp = getServletContext().getInitParameter("mdp");
	    
	    con = DriverManager.getConnection(url, user, mdp);
	    
	    // Création de l'état
	    Statement state = con.createStatement();
	    
	    // Requête qui récupère l'identité de chaque vendeur sur le marche dont l'id est iddemande :
	    String iddemande = req.getParameter("marche");
	    String query = "select u.iduser, u.login, m.idmarche, m.libelle, av.prix, t.description, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = "+iddemande+" and t.description = 'vente' group by u.iduser, u.login, m.idmarche, m.inverse, prix, t.description order by av.prix desc;";
	    
	    ResultSet rs = state.executeQuery(query);
	    int nbColumn = rs.getMetaData().getColumnCount();
	    //this.loadJSP("/partial/header.jsp",req,res);
	   	out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\""+req.getContextPath()+"/css/main.css\"> </head><body>");
	    out.println("<article><div class=\"wrap\">");
	    out.println("<h1>Vendeurs : </h1>");
	    out.println("<p>Cash : "+session.getAttribute("cash")+"</p>");
	    out.println("<table>");
	    out.println("<tr>");
	    ResultSetMetaData rsmd = rs.getMetaData();
	    for (int i = 1 ; i <= nbColumn ; i++)
	    {
		out.println("<td>"+rsmd.getColumnName(i)+"</td>");
	    }
	    out.println("</tr>");
	    while (rs.next())
	    {
		out.println("<tr>");
		for (int i = 1 ; i <= nbColumn ; i++)
		    out.println("<td>"+rs.getString(i)+"</td>");
		out.println("</tr><tr>");
	    }
	    out.println("</table>");
	    
	    query = "select u.iduser, u.login, m.idmarche, m.libelle, av.prix, t.description, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = "+iddemande+" and t.description = 'achat' group by u.iduser, u.login, m.idmarche, m.inverse, prix, t.description order by av.prix desc;";
	    rs = state.executeQuery(query);
	    nbColumn = rs.getMetaData().getColumnCount();
	    out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\""+req.getContextPath()+"/css/main.css\"> </head><body>");
	    out.println("<article><div class=\"wrap\">");
	    out.println("<h1>Acheteurs : </h1>");
	    out.println("<table>");
	    out.println("<tr>");
	    rsmd = rs.getMetaData();
	    for (int i = 1 ; i <= nbColumn ; i++)
	    {
		out.println("<td>"+rsmd.getColumnName(i)+"</td>");
	    }
	    out.println("</tr>");
	    while (rs.next())
	    {
		out.println("<tr>");
		for (int i = 1 ; i <= nbColumn ; i++)
		    out.println("<td>"+rs.getString(i)+"</td>");
		out.println("</tr><tr>");
	    }
	    out.println("</table>");
	    
	    out.println("<p><a href=\"SelectInfoMarcheInverse?marche="+iddemande+"\">Marché inverse</a></p>");
	    out.println("<p><a href=\"users/index.jsp\">Retour à la liste des marchés</a></p>");
	    out.println("</div></article><article><div class=\"wrap\">");
	    out.println("<h3>Ajouter une offre</h3>");
	    out.println("<form method=\"post\" action=\"AjouterOffre\">");
	    out.println("<p><label for=\"quantite\">Nombre de bons :</label>");
	    out.println("<input type=\"text\" name=\"quantite\" id=\"quantite\" /></p>");
	    out.println("<p><label for=\"prix\">Prix du bon :</label>");
	    out.println("<input type=\"text\" name=\"prix\" id=\"prix\" /></p>");
	    out.println("<input type=\"hidden\" name=\"idmarche\" id=\"idmarche\" value=\""+req.getParameter("marche")+"\" />");
	    out.println("<p><input type=\"submit\" value=\"Ajouter l'offre\"></p>");
	    out.println("</form>");
	    out.println("</div></article>");
	   
	    //this.loadJSP("/partial/footer.jsp",req,res);
	}
	catch (Exception e)
	{
	    out.println(e.getMessage());
	}
	finally
	{
	    if (con != null)
	    {
		try
		{
		    con.close();
		}
		catch (SQLException e)
		{
			out.println(e.getMessage());
		}
	    }
	}
    }

    public void loadJSP(String url,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
    	if (url == null) { return; }
     	if (url.charAt(0) != '/') { url = '/' + url; }
        
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(req,resp);
	}	
}