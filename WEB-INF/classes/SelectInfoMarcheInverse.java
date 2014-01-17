import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/SelectInfoMarcheInverse")
public class SelectInfoMarcheInverse extends HttpServlet
{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	Connection con = null;
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
	    String query = "select u.iduser, u.login, m.idmarche, m.inverse, 100 - av.prix as prixinverse, av.quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = "+iddemande+" order by prixinverse desc;";
	    
	    ResultSet rs = state.executeQuery(query);
	    int nbColumn = rs.getMetaData().getColumnCount();
	    out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\""+req.getContextPath()+"/css/main.css\"> </head><body>");
	    out.println("<article><div class=\"wrap\">");
	    out.println("<h1>Ma table : </h1>");
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
	    out.println("<p><a href=\"SelectInfoMarche?marche="+iddemande+"\">Marché inverse</a></p>");
	    out.println("<p><a href=\"index.jsp\">Retour à la liste des marchés</a></p>");
	    out.println("</div></article>");
	    out.println("</body></html>");
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
}