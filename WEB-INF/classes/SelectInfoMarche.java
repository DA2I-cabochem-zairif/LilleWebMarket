import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/Select")
public class Select extends HttpServlet
{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	out.println("test");
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
	    
	    String query = "select * from utilisateur";
	    // Requête qui récupère les marchés d'un utilisateur dont l'id est iddemande
	    // select m.libelle from marche m join situation s on m.idmarche = s.idmarche join utilisateur u on u.iduser = s.iduser where u.iduser = iddemande;
	    ResultSet rs = state.executeQuery(query);
	    int nbColumn = rs.getMetaData().getColumnCount();
	    out.println(" <center> ");
	    out.println("Nombre de colonnes : "+nbColumn);
	    out.println("<h1>Ma table : </h1>");
	    out.println("<table>");
	    out.println("<form action='#' methode='post'>");
	    while (rs.next())
	    {
		out.println("<tr>");
		for (int i = 1 ; i <= nbColumn ; i++)
		    out.println("<td>"+rs.getString(i)+"</td>");
		out.println("<td><a href='Update?table="+req.getParameter("table")+"&cle="+rs.getString(1)+"'>mod</a>");
		out.println("</td><td><a href='Delete?table="+req.getParameter("table")+"&cle="+rs.getString(1)+"'>del</a></td></tr>");
		out.println("<tr>");
	    }
	    out.println("<tr>");
	    for(int i = 1 ; i <= nbColumn; i++)
		out.println("<td><input type='text' name="+rs.getMetaData().getColumnName(i)+"></td>");
	    out.println("</tr>");
	    out.println("</table>");
	    out.println(" </center> ");
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