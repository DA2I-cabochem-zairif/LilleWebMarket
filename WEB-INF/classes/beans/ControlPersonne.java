import javax.servlet.*;
import javax.servlet.*;
import beans.*;

public class ControlPersonne extends HttpServlet
{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
	PrintWriter out = res.getWriter();
	res.setContentType("text/html");
	Connection con = null;
	HttpSession session = req.getSession();
	try
	{
	    ModelePersonne liste = new ModelePersonne();
	    liste.init();
	    liste.execute("select * from utilisateur;");
	    req.setAttribute("liste", liste);
	    
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