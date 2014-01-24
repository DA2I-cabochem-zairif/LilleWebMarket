import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/AjouterMarche")
public class AjouterMarche extends HttpServlet
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
	    out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\""+req.getContextPath()+"/css/main.css\"> </head><body>");
	    out.println("<article><div class=\"wrap\">");
	    if (req.getParameter("libelle").equals("") || req.getParameter("inverse").equals("") || req.getParameter("dateFin").equals(""))
	    {
		out.println("<p>Go kill yourself</p>");
	    }
	    else
	    {
		String query = "insert into marche values (default, ?, ?, '"+req.getParameter("dateFin")+"');";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, req.getParameter("libelle"));
		ps.setString(2, req.getParameter("inverse"));
		ps.executeUpdate();
		PreparedStatement ps2 = con.prepareStatement("select max(idmarche) from marche;");
		ResultSet rs2 = ps2.executeQuery();
		rs2.next();
		String lastId = rs2.getString("max");
		out.println("<p><a href=\"SelectInfoMarche?marche="+lastId+"\">Voir ce marché</a></p>");
	    }
	    out.println("<p><a href=\"users/index.jsp\">Retour à la liste des marchés</a></p>");
	    out.println("<p><a href=\"admin/formMarche.jsp\">Ajouter un nouveau marché</a></p>");
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