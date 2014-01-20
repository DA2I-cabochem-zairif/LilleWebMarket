import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/AjouterOffre")
public class AjouterOffre extends HttpServlet
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
	    int prix = Integer.parseInt(req.getParameter("prix"));
	    int quantite = Integer.parseInt(req.getParameter("quantite"));
	    int apayer = prix * quantite;
	    int iduser = Integer.parseInt((String)session.getAttribute("iduser"));
	    /*String getcash = "select cash from utilisateur where iduser = ?";
	    PreparedStatement gc = con.preparedStatement(getcash);
	    gc.setInt(1, iduser);
	    ResultSet c = gc.executeQuery();
	    c.next();
	    boolean riche = apayer <= c.getInt("cash");*/
	    int cash = Integer.parseInt((String)session.getAttribute("cash"));
	    boolean riche = apayer <= cash;
	    if (req.getParameter("quantite").equals("") || req.getParameter("prix").equals("") || !riche)
	    {
		out.println("<p>Go kill yourself</p>");
	    }
	    else
	    {
		String query = "insert into titre values (default, ?, 'achat');";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, iduser);
		ps.executeUpdate();
		String query2 = "insert into achatvente values (default, ?, ?, ?);";
		PreparedStatement ps2 = con.prepareStatement(query2);
		ps2.setInt(1, prix);
		ps2.setInt(2, quantite);
		ps2.setInt(3, Integer.parseInt(req.getParameter("idmarche")));
		ps2.executeUpdate();
		String query3 = "insert into transactions values (default, ?, ?);";
		PreparedStatement ps3 = con.prepareStatement(query3);
		PreparedStatement ps4 = con.prepareStatement("select max(idtitre) from titre;");
		ResultSet rs2 = ps4.executeQuery();
		rs2.next();
		String idtitre = rs2.getString("max");
		PreparedStatement ps5 = con.prepareStatement("select max(idachatvente) from achatvente;");
		ResultSet rs3 = ps5.executeQuery();
		rs3.next();
		String idachatvente = rs3.getString("max");
		ps3.setInt(1, Integer.parseInt(idtitre));
		ps3.setInt(2, Integer.parseInt(idachatvente));
		ps3.executeUpdate();
		int somme = cash - apayer;
		String query6 = "update utilisateur set cash = ? where iduser = ? ;";
		PreparedStatement ps6 = con.prepareStatement(query6);
		ps6.setInt(1, somme);
		ps6.setInt(2, iduser);
		ps6.executeUpdate();
		session.setAttribute("cash", somme);
	    }
	    res.sendRedirect("SelectInfoMarche?marche="+req.getParameter("idmarche"));
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