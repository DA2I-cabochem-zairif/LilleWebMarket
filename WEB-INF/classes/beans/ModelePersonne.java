package beans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.*;

public class ModelePersonne
{
    protected ArrayList<Personne> list = new ArrayList<Personne>();
    protected Connection con = null;
    
    public Tool()
    {
	this.initialize();
    }
    
    public void initialize()
    {
	try
	{
	    Class.forName("org.postgresql.Driver");
	
	    String url = "jdbc:postgresql://sqlserv/da2i";
	    String user = "zairif";
	    String mdp = "moi";
	    this.con = DriverManager.getConnection(url, user, mdp);
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public ArrayList<Personne> rechPersonne(String recherche)
    {
	try
	{
	    String query = "select * from utilisateur where nom like ? ;";
	    PreparedStatement ps = this.con.prepareStatement(query);
	    String sub = recherche+"%";
	    ps.setString(1, sub);
	    ResultSet rs = ps.executeQuery();
	    while (rs.next())
            {
		Personne p = new Personne();
		int iduser = rs.getInt("iduser");
		String nom = rs.getString("nom");
		String prenom = rs.getString("prenom");
		String login = rs.getString("login");
		String mdp = rs.getString("mdp");
		int cash = rs.getInt("cash");
		p.setIdUser(iduser);
		p.setNom(nom);
		p.setPrenom(prenom);
		p.setSexe(sexe);
		p.setLogin(login);
		p.setMdp(mdp);
		p.setCash(cash);
		list.add(p);
	    }
	    con.close();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	
	return this.list;
    }
    
    public String lister(String rech)
    {
	String chaine = "";
	ArrayList<Personne> list = this.rechPersonne(rech);
	for (Personne p : this.list)
	{
	    chaine += p.getHtml()+"\n";
	}
	return chaine;
    }
    
    /*public static void main (String [] args)
    {
	Tool t = new Tool();
	System.out.println(t.lister("S"));
    }
    */
}