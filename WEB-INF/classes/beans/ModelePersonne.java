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
    protected ResultSet rs;
    
    public ModelePersonne()
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
    
    public void execute(String query)
    {
	try
	{
	    rs = con.prepareStatement(query).executeQuery();
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
		p.setLogin(login);
		p.setMdp(mdp);
		p.setCash(cash);
		list.add(p);
	    }
	    rs.close();
	    con.close();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public Personne get(int i)
    {
	return list.get(i);
    }
    /*
    public static void main (String [] args)
    {
	ModelePersonne mp = new ModelePersonne();
	mp.execute("select * from utilisateur;");
	System.out.println(mp.get(0));
    }
    */
}