import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/FermerMarche")
public class FermerMarche extends HttpServlet
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
	    
	    Statement state = con.createStatement();
	    int idmarche = Integer.parseInt(req.getParameter("idmarche"));
	    boolean gagne = req.getParameter("resultat").equals("gagne");
	    String ReqGagnants = "select u.iduser, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and (t.description = 'achat' or t.description = 'vendu') group by u.iduser;";
	    PreparedStatement psGagnants = con.prepareStatement(ReqGagnants);
	    psGagnants.setInt(1, idmarche);
	    ResultSet rsGagnants = psGagnants.executeQuery();
	    while (rsGagnants.next())
	    {
		int iduser = rsGagnants.getInt("iduser");
		int quantite = rsGagnants.getInt("quantite");
		int gain = quantite * 100;
		ResultSet rsCashUser = con.prepareStatement("select cash from utilisateur where iduser = "+iduser+" ;").executeQuery();
		rsCashUser.next();
		int cashUser = rsCashUser.getInt("cash") + gain;
		con.prepareStatement("update utilisateur set cash = "+cashUser+" where iduser = "+iduser+" ;").executeUpdate();
	    }
	    String ReqOffrants = "select u.iduser, 100 - av.prix as prixinverse, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and t.description = 'vente' group by u.iduser, prixinverse;";
	    PreparedStatement psOffrants = con.prepareStatement(ReqOffrants);
	    psOffrants.setInt(1, idmarche);
	    ResultSet rsOffrants = psOffrants.executeQuery();
	    while (rsOffrants.next())
	    {
		int iduser = rsOffrants.getInt("iduser");
		int quantite = rsOffrants.getInt("quantite");
		int prixunitaire = rsOffrants.getInt("prixinverse");
		int gain = quantite * prixunitaire;
		ResultSet rsCashUser = con.prepareStatement("select cash from utilisateur where iduser = "+iduser+" ;").executeQuery();
		rsCashUser.next();
		int cashUser = rsCashUser.getInt("cash") + gain;
		con.prepareStatement("update utilisateur set cash = "+cashUser+" where iduser = "+iduser+" ;").executeUpdate();
	    }
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