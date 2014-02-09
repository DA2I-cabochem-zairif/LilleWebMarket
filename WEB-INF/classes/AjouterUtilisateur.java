import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/AjouterUtilisateur")
public class AjouterUtilisateur extends HttpServlet
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
	    String nom = req.getParameter("nom");
	    String prenom = req.getParameter("prenom");
	    String passwd = req.getParameter("mdp");
	    String remdp = req.getParameter("remdp");
	    if (nom.equals("") || prenom.equals("") || passwd.equals("") || remdp.equals("") || !passwd.equals(remdp))
	    {
		res.sendRedirect(req.getContextPath()+"/creerLogin.jsp");
	    }
	    else
	    {
		String nomDansLogin = nom;
		if (nomDansLogin.length() > 7)
		{
		    nomDansLogin = "";
		    for (int i = 0 ; i < 7 ; i++)
		    {
			nomDansLogin += nom.charAt(i);
		    }
		}
		String login = nomDansLogin+""+prenom.charAt(0);
		login = login.toLowerCase();
		String tmp = login;
		int incr = 2;
		int count = 1;
		while (count != 0)
		{
		    ResultSet rsVerif = con.prepareStatement("select count(*) from utilisateur where login = '"+login+"' ;").executeQuery();
		    rsVerif.next();
		    count = rsVerif.getInt("count");
		    if (count != 0)
		    {
			login = tmp;
			login += ""+incr;
		    }
		    incr++;
		}
		String query = "insert into utilisateur values (default, ?, ?, ?, ?, default);";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, nom);
		ps.setString(2, prenom);
		ps.setString(3, login);
		ps.setString(4, passwd);
		ps.executeUpdate();
	    }
	    res.sendRedirect(req.getContextPath()+"/infosCompte.jsp");
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