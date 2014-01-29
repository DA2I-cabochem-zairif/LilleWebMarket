import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/api/InfoMarche")
public class JsonInfoMarche extends HttpServlet
{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
	PrintWriter out = res.getWriter();
	res.setContentType("application/json");
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
	    String query =  "select  avg(av.prix) from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = "+iddemande+" and t.description = 'achat' group by  t.jourachat order by t.jourachat asc;";

	    ResultSet rs = state.executeQuery(query);
	    int nbColumn = rs.getMetaData().getColumnCount();
	    String ret   = "";
	    int tmp = 0;
	    ret +="[[";
	   
	    while(rs.next()){
		if(tmp == 0){
		    tmp++;
		    ret+=rs.getString(1);
		}else{
		    ret+=","+rs.getString(1);
		}
	    }
	    ret +="]]";
	    out.print(ret);
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