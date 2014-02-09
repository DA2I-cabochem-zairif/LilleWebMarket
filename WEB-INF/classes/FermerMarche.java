import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.*;

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
	    String descTitre = "gagné";
	    if (!gagne)
	    {
		descTitre = "perdu";
	    }
	    String lesTitres = "(";
	    boolean premierPassage = true;
	    String ReqGagnants = "select u.iduser, t.idtitre, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and (t.description = 'achat' or t.description = 'vendu') group by u.iduser, t.idtitre";
	    PreparedStatement psGagnants = con.prepareStatement(ReqGagnants);
	    psGagnants.setInt(1, idmarche);
	    ResultSet rsGagnants = psGagnants.executeQuery();
	    while (rsGagnants.next())
	    {
		int iduser = rsGagnants.getInt("iduser");
		if (premierPassage)
		{
		    lesTitres += ""+rsGagnants.getInt("idtitre");
		    premierPassage = false;
	       	}
		else
		{
		    lesTitres += ", "+rsGagnants.getInt("idtitre");
	       	}
		if (gagne)
		{
		    int quantite = rsGagnants.getInt("quantite");
		    int gain = quantite * 100;
		    ResultSet rsCashUser = con.prepareStatement("select cash from utilisateur where iduser = "+iduser+" ;").executeQuery();
		    rsCashUser.next();
		    int cashUser = rsCashUser.getInt("cash") + gain;
		    con.prepareStatement("update utilisateur set cash = "+cashUser+" where iduser = "+iduser+" ;").executeUpdate();
		    session.setAttribute("cash", cashUser);
		}
	    }
	    lesTitres += ")";
	    String reqUpdateTitres = "";
	    if (!premierPassage)
	    {
		reqUpdateTitres = "update titre set description = '"+descTitre+"' where idtitre in "+lesTitres+" ;";
		con.prepareStatement(reqUpdateTitres).executeUpdate();
	    }
	    String ReqOffrants = "select u.iduser, t.idtitre, 100 - av.prix as prixinverse, sum(av.quantite) as quantite from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and t.description = 'vente' group by u.iduser, t.idtitre, prixinverse;";
	    lesTitres = "(";
	    premierPassage = true;
	    PreparedStatement psOffrants = con.prepareStatement(ReqOffrants);
	    psOffrants.setInt(1, idmarche);
	    ResultSet rsOffrants = psOffrants.executeQuery();
	    while (rsOffrants.next())
	    {
		int iduser = rsOffrants.getInt("iduser");
		if (premierPassage)
		{
		    lesTitres += ""+rsOffrants.getInt("idtitre");
		    premierPassage = false;
		}
		else
		{
		    lesTitres += ", "+rsGagnants.getInt("idtitre");
		}
		int quantite = rsOffrants.getInt("quantite");
		int prixunitaire = rsOffrants.getInt("prixinverse");
		int gain = quantite * prixunitaire;
		ResultSet rsCashUser = con.prepareStatement("select cash from utilisateur where iduser = "+iduser+" ;").executeQuery();
		rsCashUser.next();
		int cashUser = rsCashUser.getInt("cash") + gain;
		con.prepareStatement("update utilisateur set cash = "+cashUser+" where iduser = "+iduser+" ;").executeUpdate();
		session.setAttribute("cash", cashUser);
	    }
	    lesTitres += ")";
	    if (!premierPassage)
	    {
		reqUpdateTitres = "update titre set description = 'rendu' where idtitre in "+lesTitres+" ;";
		con.prepareStatement(reqUpdateTitres).executeUpdate();
	    }
	    con.prepareStatement("update marche set statut = '"+descTitre+"' where idmarche = "+idmarche+" ;").executeUpdate();
	    /*String gagnants = "select u.iduser from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and m.statut = 'gagné' group by u.iduser, m.idmarche;";
	    ResultSet rsGg = con.createStatement().executeQuery(gagnants);
	    HashMap<Integer, Integer> ratioGagnants = new HashMap<Integer, Integer>();
	    while (rsGg.next())
	    {
		int idusergg = rsGg.getInt("iduser");
		int val = ratioGagnants.get(idusergg);
		out.println(ratioGagnants.get(idusergg));
		if (ratioGagnants.get(idusergg) == null)
		{
		    val = 0;
		    out.println(val);
		}
		else
		{
		    val++;
		    out.println(val);
		}
		ratioGagnants.put(idusergg, val);
	    }
	    String perdants = "select u.iduser from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and m.statut = 'perdu' group by u.iduser, m.idmarche;";
	    ResultSet rsPerdants = con.createStatement().executeQuery(perdants);
	    HashMap<Integer, Integer> ratioPerdants = new HashMap<Integer, Integer>();
	    while (rsPerdants.next())
	    {
		int iduserlose = rsPerdants.getInt("iduser");
		int val = ratioPerdants.get(iduserlose);
		if (ratioPerdants.get(iduserlose) == null)
		    val = 0;
		else
		    val++;
		ratioPerdants.put(iduserlose, val);
	    }
	    ArrayList<Integer> admins = new ArrayList<Integer>();
	    for (Integer clegg : ratioGagnants.keySet())
	    {
		if (ratioPerdants.containsKey(clegg))
		{
		    for (Integer clelose : ratioPerdants.keySet())
		    {
			if (ratioGagnants.get(clegg) / ratioPerdants.get(clelose) > 1)
			{
			    admins.add(clegg);
			}
		    }
		}
		else
		{
		    admins.add(clegg);
		}
	    }
	    Iterator it = admins.iterator();
	    while(it.hasNext())
	    {
		con.createStatement().executeUpdate("update utilisateur set droit = 'administrateur' where iduser = '"+it.next()+"' ;");
	    }
	    */
	    res.sendRedirect(req.getContextPath()+"/admin/gererMarche.jsp");
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