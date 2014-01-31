import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

@WebServlet("/Login")
public class Login extends HttpServlet
{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	Connection con = null;
	
	try
	{
	    Class.forName(getServletContext().getInitParameter("driver"));
	    Context initCtx = new InitialContext();
	    Context envCtx  = (Context) initCtx.lookup("java:comp/env");
	    DataSource ds   = (DataSource) envCtx.lookup("madb");
	    con = ds.getConnection();
	    Statement stmt = con.createStatement();
	    
	    PreparedStatement ps = con.prepareStatement("select * from utilisateur where login = ? and mdp = ?");
	    ps.setString(1, req.getParameter("login"));
	    ps.setString(2, req.getParameter("mdp"));
	    ResultSet rs = ps.executeQuery();
	    if (rs.next())
	    {
		HttpSession session = req.getSession(true);
		session.setAttribute("iduser", rs.getString("iduser"));
		session.setAttribute("login", rs.getString("login"));
		session.setAttribute("nom", rs.getString("nom"));
		session.setAttribute("prenom", rs.getString("prenom"));
		session.setAttribute("cash", rs.getString("cash"));
		res.sendRedirect(req.getContextPath()+"/users/index.jsp");
	    }
	    else
	    {
	        res.sendRedirect(req.getContextPath()+"/login.jsp");
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