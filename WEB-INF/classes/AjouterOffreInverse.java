import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/AjouterOffreInverse")
public class AjouterOffreInverse extends HttpServlet
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
	    int prix = Integer.parseInt(req.getParameter("prix"));
	    int quantite = Integer.parseInt(req.getParameter("quantite"));
	    int apayer = prix * quantite;
	    int iduser = Integer.parseInt((String)session.getAttribute("iduser"));
	    int cash = 0;
	    PreparedStatement pscash = con.prepareStatement("select cash from utilisateur where iduser = ?;");
	    pscash.setInt(1, iduser);
	    ResultSet rscash = pscash.executeQuery();
	    rscash.next();
	    cash = rscash.getInt("cash");
	    int idmarche = Integer.parseInt(req.getParameter("idmarche"));
	    boolean riche = apayer <= cash;
	    if (req.getParameter("quantite").equals("") || req.getParameter("prix").equals("") || !riche)
	    {
		res.sendRedirect("SelectInfoMarcheInverse?marche="+req.getParameter("idmarche"));
	    }
	    else
	    {
		String offresAchetables = "select t.idtitre, tr.idtransaction, av.idachatvente, u.iduser, u.login, m.idmarche, m.libelle, av.prix, av.quantite, t.description from utilisateur u, titre t, transactions tr, achatvente av, marche m where t.iduser = u.iduser and tr.idtitre = t.idtitre and av.idachatvente = tr.idachatvente and av.idmarche = m.idmarche and av.idmarche = ? and ? >= 100 - av.prix and description = 'achat' order by av.prix desc;";
		PreparedStatement psachetable = con.prepareStatement(offresAchetables);
		psachetable.setInt(1, idmarche);
		psachetable.setInt(2, prix);
		ResultSet rsachetables = psachetable.executeQuery();
		while (rsachetables.next() && quantite > 0)
		{
		    int idtransaction = rsachetables.getInt("idtransaction");
		    int disponible = rsachetables.getInt("quantite");
		    int idachatvente = rsachetables.getInt("idachatvente");
		    int idpossesseur = rsachetables.getInt("iduser");
		    int idtitre = rsachetables.getInt("idtitre");
		    int cashpossesseur = 0;
		    PreparedStatement pscashvendeur = con.prepareStatement("select cash from utilisateur where iduser = "+idpossesseur);
		    ResultSet rscashvendeur = pscashvendeur.executeQuery();
		    rscashvendeur.next();
		    cashpossesseur = rscashvendeur.getInt("cash");
		    int prixunitaire = 100 - rsachetables.getInt("prix");
		    int prixAPayer = quantite * prixunitaire;
		    String reqVente = "delete from achatvente where idachatvente = ? ;";
		    String reqCash = "update utilisateur set cash = ? where iduser = ?";
		    PreparedStatement psCash = con.prepareStatement(reqCash);
		    if (quantite < disponible)
		    {
			int achete = quantite;
			disponible -= quantite;
			quantite = 0;
			reqVente = "update achatvente set quantite = ? where idachatvente = ? ;";
			int recu = cashpossesseur + prixAPayer;
			psCash.setInt(1, recu);
			psCash.setInt(2, idpossesseur);
			psCash.executeUpdate();
			int du = cash - prixAPayer;
			psCash.setInt(1, du);
			psCash.setInt(2, iduser);
			psCash.executeUpdate();
			session.setAttribute("cash", du);
			PreparedStatement vendre = con.prepareStatement(reqVente);
			vendre.setInt(1, disponible);
			vendre.setInt(2, idachatvente);
			vendre.executeUpdate();
			out.println("1");
			con.prepareStatement("insert into titre values (default, "+iduser+", 'vendu') ;").executeUpdate();
			ResultSet rsTitre = con.prepareStatement("select max(idtitre) from titre ;").executeQuery();
			rsTitre.next();
			int lastIdTitre = rsTitre.getInt("max");
			con.prepareStatement("insert into achatvente values (default, "+prixunitaire+", "+achete+", "+idmarche+");").executeUpdate();
			ResultSet rsAchatVente = con.prepareStatement("select max(idachatvente) from achatvente ;").executeQuery();
			rsAchatVente.next();
			int lastIdAchatVente = rsAchatVente.getInt("max");
			con.prepareStatement("insert into transactions values (default, "+lastIdTitre+", "+lastIdAchatVente+");").executeUpdate();
		    }
		    else if (quantite == disponible)
		    {
			quantite = 0;
			disponible = 0;
			int recu = cashpossesseur + prixAPayer;
			psCash.setInt(1, recu);
			psCash.setInt(2, idpossesseur);
			psCash.executeUpdate();
			int du = cash - prixAPayer;
			psCash.setInt(1, du);
			psCash.setInt(2, iduser);
			psCash.executeUpdate();
			session.setAttribute("cash", du);
			out.println("2");
			con.prepareStatement("update titre set iduser = "+iduser+", description = 'vendu' where idtitre = "+idtitre+" ;").executeUpdate();
		    }
		    else
		    {
			int restant = disponible;
			prixAPayer = disponible * prixunitaire;
			quantite -= disponible;
			disponible = 0;
			int recu = cashpossesseur + prixAPayer;
			psCash.setInt(1, recu);
			psCash.setInt(2, idpossesseur);
			psCash.executeUpdate();
			session.setAttribute("cash", recu);
			int du = cash - prixAPayer;
			psCash.setInt(1, du);
			psCash.setInt(2, iduser);
			psCash.executeUpdate();
			out.println("3");
			con.prepareStatement("update achatvente set quantite = "+restant+" where idachatvente = "+idachatvente+" ;").executeUpdate();
			con.prepareStatement("update titre set iduser = "+iduser+", description = 'vendu' where idtitre = "+idtitre+" ;").executeUpdate();
		    }
		}
		if (quantite > 0)
		{
		    String query = "insert into titre values (default, ?, 'vente');";
		    PreparedStatement ps = con.prepareStatement(query);
		    ps.setInt(1, iduser);
		    ps.executeUpdate();
		    String query2 = "insert into achatvente values (default, ?, ?, ?);";
		    PreparedStatement ps2 = con.prepareStatement(query2);
		    ps2.setInt(1, 100 - Integer.parseInt(req.getParameter("prix")));
		    ps2.setInt(2, quantite);
		    ps2.setInt(3, idmarche);
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
	    }
	    res.sendRedirect(req.getContextPath()+"/users/selectMarcheInverse.jsp?marche="+req.getParameter("idmarche"));
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